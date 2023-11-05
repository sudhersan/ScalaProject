package catseffect

import cats.data.{Validated, ValidatedNec}
import cats.effect.IO
import cats.implicits.{catsSyntaxApplicativeId, toTraverseOps}

/*
 Simple Banking app for money transfers
 */

class SimpleIOApp {


  object  Validations {
    type Valid[A] = ValidatedNec[String, A]

    def isValidDouble(s: String): Valid[Double] = {
      Validated.fromOption(s.toDoubleOption, s"$s is not a valid Double").toValidatedNec
    }

    def isValidatedAccount(accountNumber: String): Valid[String] = {
      Validated.condNec(
        accountNumber.forall(_.isLetterOrDigit),
        accountNumber,
        s"$accountNumber has to contain only numbers or digits"
      )
    }
  }

  trait DomainError
  case class InsufficientBalanceError(actualBalance: Double, amountToWithdraw: Double) extends DomainError
  case class MaximumBalanceExceededError(actualBalance: Double, amountToDeposit: Double) extends DomainError
  case class AccountNumberNotFound(accountNumber: String) extends DomainError

  object Domain {
    val maxBalance = 10000

    case class Account(accountNumber: String, balance: Double) {

      def withdraw(amount: Double) : Either[DomainError,Account] = {
        if (amount > balance) Left(InsufficientBalanceError(balance, amount))
        else                  Right(this.copy(balance = balance - amount))
      }

      def deposit(amount: Double): Either[DomainError, Account] = {
        if (amount + balance > maxBalance) Left(MaximumBalanceExceededError(balance, amount))
        else                               Right(this.copy(balance = balance + amount))
      }
    }
  }

  object Repository {
    import Domain._

    var accounts: Map[String, Account] = Map()

    def findAccount(accountNumber : String): IO[Option[Account]] = IO(accounts.get(accountNumber))
    def saveAccount(account: Account): IO[Unit] = IO {
      accounts = accounts + (account.accountNumber -> account)
    }
  }

  object Service {
    import Domain._

    def transfer(fromAccountNumber: String, toAccountNumber: String, amount: Double): IO[Either[DomainError, Unit]] = {
      Repository.findAccount(fromAccountNumber).flatMap{ fromAccountOpt =>
        Repository.findAccount(toAccountNumber).flatMap{ toAccountOpt =>
          val accounts: Either[DomainError, (Account, Account)] = for {
            fromAccount <- fromAccountOpt.toRight(AccountNumberNotFound(fromAccountNumber))
            toAccount <- toAccountOpt.toRight(AccountNumberNotFound(toAccountNumber))
            updateFromAccount <- fromAccount.withdraw(amount)
            updatedToAccount <- toAccount.deposit(amount)
          } yield(updateFromAccount, updatedToAccount)

          accounts.traverse {
            case (accountA, accountB) =>
              Repository.saveAccount(accountA) >> Repository.saveAccount(accountB)
          }
        }
      }
    }
  }

}

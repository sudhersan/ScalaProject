package akka

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future}
import scala.util.{Failure, Success}

object PipePattern extends App{

val phoneDir = Map(
  "sam" -> 123,
  "jack" -> 456,
  "phil" -> 789
)

val fixedTp= Executors.newFixedThreadPool(4)
implicit val execContent: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(fixedTp)

trait PhoneProtocol
case class findNumber(name: String) extends PhoneProtocol
case class makeAPhoneCall(number: Int) extends PhoneProtocol
case class logError(error: Throwable) extends PhoneProtocol

object PhoneActor {
  def apply():Behavior[PhoneProtocol] = Behaviors.receive{(context, message) =>
    message match {
      case findNumber(name) =>
      val phoneFuture = Future(phoneDir(name))
      context.pipeToSelf(phoneFuture) {
        case Success(number) => makeAPhoneCall(number)
        case Failure(exception) => logError(exception)
      }
      Behaviors.same
      case makeAPhoneCall(number) =>
      context.log.info(s"$number tring tring...")
       Behaviors.same
      case logError(ex) =>
      context.log.info("error...")
       Behaviors.same
    }
  }
}

val guardianActor = Behaviors.setup[Unit]{ context =>
  val userGuard = context.spawn(PhoneActor(),"PhoneActor")
  userGuard ! findNumber("sam")
  Behaviors.empty
}

  ActorSystem(guardianActor, "DemoPipePattern")

}

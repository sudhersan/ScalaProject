package zioex

import zio.{Task, ULayer, ZIO, ZLayer}

object ServiceModel {

  case class User(name: String, email: String)

  class UserSubscription(emailSerive: EmailService, userDatabase: UserDatabase) {

  }

  class EmailService {
    def email(user: User): Task[Unit] = {
      ZIO.succeed(println(s"$user.name subscribed"))
    }
  }

  object EmailService {
    def create() = new EmailService
    def live: ZLayer[Any, Nothing, EmailService] = ZLayer.succeed(create())
  }

  class UserDatabase(connectionPool: ConnectionPool) {
    def insert(user: User): ZIO[Any, Throwable, Unit] = for {
      conn <- connectionPool.get
      _ <- conn.runQuery("insert into subscribers(name, email) values (${user.name}, ${user.email})")
    } yield ()
  }

  object UserDatabase {
    def create(connectionPool: ConnectionPool) = new UserDatabase(connectionPool)
    def live(connectionPool: ConnectionPool): ZLayer[ConnectionPool, Nothing, UserDatabase] =
      ZLayer.fromFunction(create _)
  }

  class ConnectionPool(nconnections: Int) {
    def get: Task[Connection] = {
      ZIO.succeed(println("Acquiring connection")) *> ZIO.succeed(Connection())
    }
  }

  object ConnectionPool {
    def create(nconnections: Int) = new ConnectionPool(nconnections)
    def live(nconnections: Int): ULayer[ConnectionPool] = ZLayer.succeed(create(nconnections))
  }

  case class Connection() {
    def runQuery(query: String): Task[Unit] = {
      ZIO.succeed(println(s"Executing $query"))
    }
  }

}

package akka

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object AskPattern extends App {

  trait WorkerProtocol
  case class ComputationalTask(text: String, replyTo:ActorRef[WorkerProtocol]) extends WorkerProtocol
  case class ComputationalResult(count: Int) extends WorkerProtocol

  object Worker {
    def apply(): Behavior[WorkerProtocol] = Behaviors.receive{(context, message) =>
      message match {
        case ComputationalTask(message, destination) =>
          destination ! ComputationalResult(message.split(" ").length)
          Behaviors.same
        case _ => Behaviors.same
      }
    }
  }
}

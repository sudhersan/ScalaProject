package akka


import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

final case class Message(txt: String, seqn: Int)

final case class CountMessage(txt: String, actorRef: ActorRef[Message])

object WordCounter {

  def apply(): Behavior[CountMessage] = Behaviors.receive{

  }
}

object ActorEx {

  def apply(): Behavior[Message] = Behaviors.receive{ (context, message) =>
    println(message.txt)
    val childActor = context.spawn(WordCounter,"wordCounter")

  }

}

object MainClass extends App {

  val ref = ActorSystem(ActorEx(), "actorEx")
  ref ! Message("abc", 123)

}

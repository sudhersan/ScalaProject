package AkkaEx

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}

class CreateChildActors extends App{

  object Parent {
    trait Command
    case class CreateChild(name: String) extends Command
    case class TellChild(name: String, message: String) extends Command

    def apply(): Behavior[Command] = active(Map())

    def active(actorMap: Map[String, ActorRef[String]]): Behavior[Command] = Behaviors.receive{ (context, message) =>
      message match {
        case CreateChild(name) =>
        val actorRef: ActorRef[String] = context.spawn(Child(), name)
        active(actorMap + (name -> actorRef))
        case TellChild(name, message) =>
        actorMap.get(name).fold(context.log.info("Child Actor not found"))(child => child ! message)
        Behaviors.same
      }
    }
  }

  object Child {
    def apply(): Behavior[String] = Behaviors.receive{ (context, message) =>
      context.log.info(s"${context.self.path.name} received $message")
      Behaviors.same
    }
  }

  def demoParentChild(): Unit = {

    import Parent._
    val userGuardian: Behavior[Unit] = Behaviors.setup{ context =>
      val parent = context.spawn(Parent(), "parent")
      parent ! CreateChild("Alice")
      parent ! CreateChild("Bob")
      parent ! TellChild("Alice", "Hello")
      parent ! TellChild("Bob", "Hi")
      Behaviors.empty
    }

    val system = ActorSystem(userGuardian, "actor system")
    Thread.sleep(1000)
    system.terminate()
  }

}

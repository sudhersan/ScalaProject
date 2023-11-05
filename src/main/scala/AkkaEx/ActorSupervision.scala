package AkkaEx

import akka.actor.typed.{ActorSystem, Behavior, SupervisorStrategy, Terminated}
import akka.actor.typed.scaladsl.Behaviors

object ActorSupervision {

  object WorkCounter {
    //
    //    def apply(): Behavior[String] = apply(0)
    //
    //    def active(count: Int): Behavior[String] = Behaviors.receive {(context, message) =>
    //      if (message.startsWith("R")) throw new RuntimeException
    //      if (message.startsWith("N")) throw new NullPointerException
    //      val wordLength = message.split(" ").length
    //      context.log.info(s"Received ${wordLength}")
    //      active(count + wordLength)
    //      Behaviors.same
    //
    //    }
    //  }
    //
    //  def demoSupervisionWithRestart(): Unit = {
    //    val parentBehavior: Behavior[String] = Behaviors.setup{ context =>
    //      val childBehavior = Behaviors.supervise(WorkCounter()).onFailure[NullPointerException](SupervisorStrategy.resume)
    //
    //      val child = context.spawn(childBehavior, "FuzzyChildActor")
    //      context.watch(child)
    //
    //      Behaviors.receiveMessage[String]{ (message) =>
    //        child ! message
    //        Behaviors.same
    //      }.receiveSignal{
    //        case (context, Terminated(actorRef)) =>
    //          context.log.warn(s"Chils failed ${actorRef.path.name} ")
    //          Behaviors.same
    //      }
    //    }
    //
    //   val guardian: Behavior[Unit] = Behaviors.setup{ context =>
    //     val fuzzyCounter = context.spawn(parentBehavior, "Guardian Actor")
    //
    //     fuzzyCounter ! "Null pointer Exception"
    //
    //     Behaviors.same
    //
    //   }
    //
    //    val system = ActorSystem(guardian, "DemoCrashWithParent")
    //    Thread.sleep(1000)
    //    system.terminate()
    //  }
  }
  }

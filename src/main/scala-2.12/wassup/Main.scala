package wassup

import java.util.{Date, UUID}

import akka.actor.{ActorSystem, Props}
import wassup.Commands.SendMessage


object Main extends App {

  val system = ActorSystem("wassup")

  val Alice = system.actorOf(Props[User], "Alice")
  val Bob = system.actorOf(Props[User], "Bob")

  Alice ! SendMessage(Message(UUID.randomUUID(), "wassup", from="Alice", to="Bob", new Date()))

}

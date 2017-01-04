package wassup

import java.util.{Date, UUID}

import akka.actor.{ActorSystem, Props}


object Main {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("wassup")

    val Alice = system.actorOf(Props[User], "Alice")
    val Bob = system.actorOf(Props[User], "Bob")

    val date = new Date()
    Alice ! SendMessage(Message(UUID.randomUUID(), "wassup", from="Alice", to="Bob", date), Bob)
    Thread.sleep(5000)
    Bob ! CheckMessages(date, date)

    Thread.sleep(20000)
    system.terminate()
  }
}

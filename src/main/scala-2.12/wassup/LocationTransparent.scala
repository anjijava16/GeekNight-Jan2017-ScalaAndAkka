package wassup

import java.util.{Date, UUID}

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory


object LocationTransparent {
    def main(args: Array[String]): Unit = {

      val config1 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=2551").withFallback(ConfigFactory.defaultApplication())
      val config2 = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=2552").withFallback(ConfigFactory.defaultApplication())

      val system1 = ActorSystem("wassup", config1)
      val system2 = ActorSystem("wassup", config2)

      val Alice = system1.actorOf(Props[User], "Alice")
      val Bob = system2.actorOf(Props[User], "Bob")

      Thread.sleep(10000)

      val date = new Date()
      Alice ! SendMessage(Message(UUID.randomUUID(), "wassup", from="Alice", to="Bob", date), Bob)
      Thread.sleep(5000)
      Bob ! CheckMessages(date, date)

      Thread.sleep(20000)
      system1.terminate()
      system2.terminate()
    }
}

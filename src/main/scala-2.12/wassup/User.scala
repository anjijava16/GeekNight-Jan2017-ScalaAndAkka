package wassup

import java.util.Date

import akka.actor.Actor
import akka.actor.Actor.Receive

case class SendMessage(msg: String)
case class ReceiveMessage(msg: String)
case class LoadMessages(from: Date, to: Date)

class User extends Actor {
  override def receive: Receive = {
    case SendMessage => ???
  }
}

object User {

}


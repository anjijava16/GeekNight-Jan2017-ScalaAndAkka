package wassup

import java.util.{Date, UUID}

import akka.actor.{Actor, ActorContext, ActorLogging, ActorSelection}
import Events._
import Commands._

object Commands {
  type MsgId = UUID
  case class SendMessage(msg: Message)
  case class ReceiveMessage(msg: Message)
  case class CheckMessages(from: Date, to: Date)
}

case class Message(msgId: MsgId, text: String, from: String, to: String, date: Date) {
  private def getUserActor(name: String)(implicit context: ActorContext) = context.actorSelection("/user/" + name)

  def sender(implicit context: ActorContext): ActorSelection = getUserActor(from)
  def recipient(implicit context: ActorContext): ActorSelection = getUserActor(to)
}

object Events {
  case class MessageSent(msg: Message)
  case class MessageDelivered(msg: Message)
  case class MessageSeen(msg: Message)
}

class User extends Actor with ActorLogging {
  import User._

  // Actor state (can be mutable)
  var sentMessages: List[Message] = Nil
  var receivedMessages: List[Message] = Nil

  override def receive: Receive = {
    case SendMessage(msg) =>
      sentMessages = msg :: sentMessages
      msg.recipient ! ReceiveMessage(msg)
      msg.sender ! MessageSent(msg)

    case ReceiveMessage(msg) =>
      receivedMessages = msg :: receivedMessages
      msg.sender ! MessageDelivered(msg)

    case CheckMessages(startDate, endDate) =>
      val messages = receivedMessages.filter(msg => isDateInRange(msg, startDate, endDate))
      messages.foreach(msg => msg.sender ! MessageSeen(msg))
      messages

    case MessageSent(msg) =>
      log.info("Message sent: ✓")

    case MessageDelivered(msg) =>
      log.info("Message delivered to recipient: ✓✓")

    case MessageSeen(msg) =>
      log.info("Message seen by recipient: ✓✓✓")

  }
}

object User {

  def isDateInRange(msg: Message, start: Date, end: Date): Boolean = {
    val date = msg.date
    (date.after(start) || date.equals(start)) && (date.before(end) || date.equals(end))
  }

}


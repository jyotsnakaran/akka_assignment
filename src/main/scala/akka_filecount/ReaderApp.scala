import scala.io.Source
import scala.util.Failure
import scala.util.Success
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern._
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global

case class File(name: String)
class FileProcess extends Actor {
  var fileSender: Option[ActorRef] = None
  val actor = context.actorOf(Props[StringCounter], "actor")

  var totalCount = 0
  def receive = {
    case File(file) =>
      {
        fileSender = Some(sender)
        for (line <- Source.fromFile(file).getLines()) {

          actor ! line
        }
        actor ! "EOF"
      }
    case counter: Int => {
      totalCount = totalCount + counter
    }

    case "EOF" =>

      fileSender.map(_ ! totalCount)
  }
}

class StringCounter extends Actor {
  var count = 0
  def receive = {
    case "EOF" =>
      sender ! "EOF"
    case readline: String =>
      {
        count = readline.split(" ").size
        sender ! count
      }
  }
}

object ReaderApp extends App {
  val system = ActorSystem("FileReader")
  val actor = system.actorOf(Props(new FileProcess), "actor")
  implicit val time = Timeout(25000)
  val filename = "abc.txt"
  val future = actor ? File(filename)

  future onComplete {
    case Success(result) => {
      println(result)
      system.shutdown
    }
    case Failure(msg) => println("Failure Message : " + msg)
  }
}
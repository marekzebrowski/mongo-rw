import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.{ ExecutionContext, Promise }

/**
 * Created with IntelliJ IDEA.
 * User: mar
 * Date: 02.10.2013
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */

case class Op(k:String, v:String)
case object GetCounter
case class Counter(v :Integer )
case class OK() {
  def print:String = "<its me>"
}

class MongoActor(op:MongoOps) extends Actor {
  var counter = 0

  def receive = {
    case Op(k,v) => {
      op.write(k,v)
      op.read(k)
      counter = counter +1
      sender ! OK()
    }
    case GetCounter => {
      val c = counter
      sender ! Counter(c)
    }

  }
}

object AkkaSingleActor extends App {
  implicit val ec = ExecutionContext.global
  implicit val timeout = Timeout(5 seconds)

  val ops = new MongoOps()
  val system = ActorSystem("mongo")
  val ma = system.actorOf(Props(new MongoActor(ops)))
  val retVal = (ma ? Op("k","v")).mapTo[OK]
  retVal.map(r => println(r.print))
  system.shutdown()
}



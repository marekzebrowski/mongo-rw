/**
 * Created with IntelliJ IDEA.
 * User: mar
 * Date: 02.10.2013
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
object StraightRunner extends App {
  val ops = new MongoOps

  val t1 = System.currentTimeMillis()
  val t2 = t1 + 10*1000L
  var counter = 0
  while(System.currentTimeMillis() < t2) {
    val key = nextKey
    ops.write(key,"data blob")
    ops.read(key)
    counter = counter+1
  }

  println("Straight mongo ops = "+ counter/10.0 + "/s" )
  var cnt = 0
  def nextKey():String = {
    cnt = cnt+1
     ""+cnt
  }


}

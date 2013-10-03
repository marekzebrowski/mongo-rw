object Thread10 extends App {
  class TRunner(prefix:String, ops: MongoOps) extends Thread {
    var counter = 0
    override def run(){
      while(work) {
        val k = nextKey()
        ops.write(k,"blob")
        ops.read(k)
        counter=counter+1
      }
    }
    var cnt = 0
    def nextKey():String = {
      cnt = cnt+1
      prefix+cnt
    }
  }


  @volatile var work = true
  val ops = new MongoOps
  val t1 = System.currentTimeMillis()
  val t2 = t1 + 10*1000L

  val n = 10
  val r1 = 0 until n
  //create Threads
  val threads = r1.map(i => new TRunner("T"+i+"-", ops))
  threads.map(_.start())
  Thread.sleep(10*1000)
  work=false
  val te = System.currentTimeMillis()
  val counter = threads.foldLeft(0)( (a,b) => a+b.counter)
  println("Straight mongo ops = "+ counter*1000.0/((te-t1)) + "/s" )
}

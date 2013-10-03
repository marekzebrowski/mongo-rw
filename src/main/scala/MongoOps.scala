import com.mongodb.casbah.Imports._
/**
 * Created with IntelliJ IDEA.
 * User: mar
 * Date: 02.10.2013
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
class MongoOps {
  val mongo = MongoClient("localhost")
  val db = mongo("testdb")
  val col = db("myc")

  def read(id:String) = {

    col.findOne(MongoDBObject("_id"->id))
    //()
  }

  def write(id:String, v:String): Unit = {
    col.insert( MongoDBObject("_id" ->id, "v"->v))
  }

}

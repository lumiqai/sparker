package DataStructures
import scala.collection.mutable.MutableList;

/**
 * Created by gio
 * on 07/12/16.
 */
case class Profile(val id: Long, val attributes : MutableList[KeyValue] = new MutableList(), val originalID : String = "") extends ProfileTrait with Serializable{

  /**
    * Add an attribute to the list of attributes
    *
    * @param a attribute to add
    * */
  def addAttribute(a: KeyValue): Unit = {
    attributes += a
  }
  // todo If we have no attributes (e.g. a single doc), we have a single element in the list
}

package DataStructures

import WeightingMethods.WeightMethodTrait
import org.apache.spark.broadcast.Broadcast

/**
 * Created by gio
 * on 07/12/16.
 */
case class BlockDirty(val blockID : Long, val profiles: (List[Long], List[Long]), var entropy : Double = -1) extends BlockAbstract with Serializable{
  override def getComparisonSize(): Double = profiles._1.size * (profiles._1.size - 1) / 2
  //override def getComparisons(): Iterator = null
  override def isBilateral(): Boolean = false

  def getComparisons(): List[UnweightedEdge] = {
    getAllProfiles.combinations(2).map(x =>
      if(x(0) < x(1)) UnweightedEdge(x(0), x(1))
      else UnweightedEdge(x(1), x(0))
    ).toList
  }

  def getWeightedComparisons(profileBlocks: Broadcast[Map[Long,ProfileBlocks]], weightMethod: WeightMethodTrait): List[WeightedEdge] = {

    for(
      p <- getAllProfiles.combinations(2).map(x =>
      if(x(0) < x(1)) (x(0), x(1))
      else (x(1), x(0))).toList;
      edge = getComparison(profileBlocks.value(p._1.toInt), profileBlocks.value(p._2.toInt), weightMethod)
      if(edge != null)
    )
      yield edge
  }
}

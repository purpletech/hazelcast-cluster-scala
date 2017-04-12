package nodes.worker

import scala.io.Source
import com.hazelcast.core.Hazelcast

import scala.collection.JavaConverters._
import com.typesafe.config.Config
import common.HazelcastConfig
import java.util.concurrent.BlockingQueue;

class WorkerNode(config: com.hazelcast.config.Config) {
  val instance = Hazelcast.newHazelcastInstance(config)
  val jobQueue = instance.getQueue[String]("jobs")
  val job: String = jobQueue.take() 
  println("=========We are started=============")
  
}

object WorkerNode {
  import common.Configurations._

  def main(args: Array[String]) = {
    val config = getConfig(args(0))
    val hzConfig = HazelcastConfig.nodeConfig(config.getConfig("hazelcast"))
    val worker = new WorkerNode(hzConfig)
  }
}
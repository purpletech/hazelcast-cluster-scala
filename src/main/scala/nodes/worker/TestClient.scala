package nodes.worker

import scala.io.Source
import com.hazelcast.core.Hazelcast
import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig

import scala.collection.JavaConverters._
import com.typesafe.config.Config
import common.HazelcastConfig
import java.util.concurrent.BlockingQueue;

class TestClient(config: com.hazelcast.client.config.ClientConfig) {
  val instance = HazelcastClient.newHazelcastClient(config)
  val jobQueue = instance.getQueue[String]( "jobs" )
  jobQueue.put("We are started") 
  println("Added new job to queue")
}

object TestClient {
  import common.Configurations._
  def main(args: Array[String]) = {
    val config = getConfig(args(0))
    val hzConfig = HazelcastConfig.clientConfig(config.getConfig("hazelcast"))
    val client = new TestClient(hzConfig)
    System.exit(0)
  }
}


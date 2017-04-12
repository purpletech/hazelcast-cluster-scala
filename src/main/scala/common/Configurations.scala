package common
import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config
import java.io.File
import scala.collection.JavaConverters._

object Configurations {
  def getConfig(name: String): Config = {
    val configFile = getClass.getClassLoader.getResource(name).getFile
    ConfigFactory.parseFile(new File(configFile))
  }
}

object HazelcastConfig {

  def nodeConfig(config: Config): com.hazelcast.config.Config = {
    val hzConfig = new com.hazelcast.config.Config
    hzConfig.getGroupConfig
      .setName(config.getString("name"))
      .setPassword(config.getString("password"))
    hzConfig.setProperty("hazelcast.rest.enabled", "true")

    if (config.hasPath("manCenter")) {
      val manCenterConfig = hzConfig.getManagementCenterConfig
      val manCenterAddress = config.getString("manCenter")
      println("Mancenter address: " + manCenterAddress)
      manCenterConfig.setUrl(manCenterAddress).setEnabled(true)
    }

    val networkConfig = hzConfig.getNetworkConfig
    networkConfig.setPort(config.getInt("port")).setPortAutoIncrement(false)
    if (config.hasPath("interface")) {
      val interface = config.getString("interface")
      networkConfig.getInterfaces.setEnabled(true).addInterface(interface)
    }
    val joinConfig = networkConfig.getJoin
    joinConfig.getMulticastConfig.setEnabled(false)
    val tcpIpConfig = joinConfig.getTcpIpConfig;
    tcpIpConfig.setEnabled(true)
    val members = config.getStringList("cluster").asScala
    members.foreach { node => tcpIpConfig.addMember(node) }

    hzConfig
  }

  def clientConfig(config: Config): com.hazelcast.client.config.ClientConfig = {
    val hzConfig = new com.hazelcast.client.config.ClientConfig
    hzConfig.getGroupConfig
      .setName(config.getString("name"))
      .setPassword(config.getString("password"))
    hzConfig.setProperty("hazelcast.rest.enabled", "true")

    val networkConfig = hzConfig.getNetworkConfig
    val addresses = config.getStringList("addresses").asScala
    addresses.foreach { node => networkConfig.addAddress(node) }

    hzConfig
  }
}
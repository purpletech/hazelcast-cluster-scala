# cluster-print-app
1. Install SBT 0.13
2. Clone project to local and cd to project directory
3. From one terminal session run command `sbt "run-main nodes.worker.WorkerNode worker1.conf" ` to start node 1
4. From one terminal session run command `sbt "run-main nodes.worker.WorkerNode worker2.conf" ` to start node 2
5. From one terminal session run command `sbt "run-main nodes.worker.WorkerNode worker3.conf" ` to start node 3
6. From one terminal session run command `sbt "run-main nodes.worker.TestClient client.conf" ` to send a job to cluster
7. "=======We are started============" will be printed by one of the node


## WorkerNode
All nodes started will form a Hazelcast cluster. 

Nodes can be run on separate hosts. If run on separate host and all nodes use the same port, the configuration files can be shared and only ONE configuration file is needed.
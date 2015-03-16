package kafka.examples;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import scala.Option;
import scala.collection.Map;
import scala.util.parsing.json.JSON;

import java.io.IOException;
import java.util.List;


public class ZK {



    public static String brokersList () throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper("docker:2181", 3000, null);

        List<String> children = zooKeeper.getChildren("/brokers/ids", false);

        boolean first = true;
        String result = "";

        for(String child:children) {
            String childJson = new String(zooKeeper.getData("/brokers/ids/" + child, false, null));
            System.out.println(childJson);

            // USING THE DEPRECATED SCALA JSON PARSER ... BECAUSE ... WELL ... JSON IN JAVA
            Option<Object> objectOption = JSON.parseFull(childJson);

            Map<String, Object> stringObjectMap = (Map<String, Object>) objectOption.get();

            String host = stringObjectMap.get("host").get().toString();

            // WOLOLO : https://www.youtube.com/watch?v=rS4b7KKNHDQ
            Integer port = ((Double) stringObjectMap.get("port").get()).intValue();

            if(first) {
                first = false;
            } else {
                result = result + ",";
            }

            result = result + host+":"+port;



        }


        return result;

    }

    public static void main(String[] args) throws Exception {

        System.out.println(brokersList());
    }
}

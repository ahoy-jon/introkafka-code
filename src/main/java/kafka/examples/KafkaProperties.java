package kafka.examples;



public interface KafkaProperties
{
    final static String zkConnect = "docker:2181";
    final static  String groupId = "group1";
    final static String topic = "_test";
    final static String kafkaServerURL = "kafka0";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64*1024;
    final static int connectionTimeOut = 100000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "_test2";
    final static String topic3 = "_test3";
    final static String clientId = "SimpleConsumerDemoClient";
}
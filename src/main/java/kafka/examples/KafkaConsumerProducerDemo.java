package kafka.examples;

public class KafkaConsumerProducerDemo implements KafkaProperties
{
    public static void main(String[] args) throws Exception {


       MyProducer myProducerThread = new MyProducer(KafkaProperties.topic);
       myProducerThread.start();

       Consumer consumerThread = new Consumer(KafkaProperties.topic);
        consumerThread.start();

    }
}
package kafka.examples;




        import java.util.Date;
        import java.util.Properties;
        import kafka.producer.KeyedMessage;
        import kafka.producer.ProducerConfig;

public class MyProducer extends Thread
{
    private final kafka.javaapi.producer.Producer<String, String> producer;
    private final String topic;
    private final Properties props = new Properties();

    public MyProducer(String topic) throws Exception {


        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", ZK.brokersList());
        props.put("partitioner.class","kafka.producer.DefaultPartitioner");
        // Use random partitioner. Don't need the key type. Just set it to Integer.
        // The message is of type String.
        producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(props));
        this.topic = topic;
    }

    public void run() {
        int messageNo = 1;

        final String dt = (new Date()).toString();

        while(true)
        {
            String messageStr = "Message_" + messageNo + " (" + dt + ")";
            Integer key = messageNo % 10;
            producer.send(new KeyedMessage<String, String>(topic, key.toString() , messageStr));

            messageNo++;
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
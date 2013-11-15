package my.activemq.broker;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedBroker {
  
  private static Logger logger = LoggerFactory.getLogger(EmbeddedBroker.class);
  
  public static void main(String[] args) {
    try {
      BrokerService broker = new BrokerService();
      broker.addConnector("tcp://localhost:61616");
      broker.start();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}

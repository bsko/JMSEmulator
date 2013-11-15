package my.activemq.publisher;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessagePublisher implements Runnable {
  
  private static Logger logger = LoggerFactory.getLogger(MessagePublisher.class);
  
  public static void main(String[] args) {
    MessagePublisher publisher = new MessagePublisher();
    new Thread(publisher).start();
  }

  public void run() {
    Connection connection = null;
    Session session = null;
    try {
      logger.info("Publisher started");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
      logger.info("Factory created");
      connection = connectionFactory.createConnection(); 
      connection.start();
      logger.info("Connection started");
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      logger.info("Session created");
      Destination destination = session.createQueue("TEST.FOO");
      logger.info("Queue created");
      MessageProducer producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
      for(int i = 0; i < 10; i++) {
        String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode() + ". This is a message with Numnber# " + i;
        TextMessage message = session.createTextMessage(text);
        logger.info("***Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        logger.info("***Message ID is: "+ message.getJMSMessageID() + "; CorrelationID is: " + message.getJMSCorrelationID());
        producer.send(message);
      } 
      session.close();
      connection.close();
    } catch(JMSException e) {
      logger.error("Error during jms message publishing", e);
    } finally {
      try {
        if(connection != null) {
          connection.close();
        }
        if(session != null) {
          session.close();
        }
      } catch(Exception e) {/*nothing*/}
    }
  }
}

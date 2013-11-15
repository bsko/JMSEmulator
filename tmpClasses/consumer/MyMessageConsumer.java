package my.activemq.consumer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMessageConsumer  implements Runnable, ExceptionListener {
  
  private static Logger logger = LoggerFactory.getLogger(MyMessageConsumer.class);
  
  public static void main(String[] args) {
    MyMessageConsumer consumer = new MyMessageConsumer();
    new Thread(consumer).start();
  }

  public void onException(JMSException arg0) {
    // TODO Auto-generated method stub
    
  }

  public void run() {
    Connection connection = null;
    Session session = null;
    MessageConsumer consumer = null;
    try {
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
      connection = connectionFactory.createConnection();
      connection.start();
      connection.setExceptionListener(this);
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("TEST.FOO");
      consumer = session.createConsumer(destination, "JMSMessageID='ID:NB-atemnov-51109-1384520112290-1:1:1:1:5'");
      Message message = consumer.receive(1000);
      if (message instanceof TextMessage) {
          TextMessage textMessage = (TextMessage) message;
          String text = textMessage.getText();
          logger.info("*****Received: " + text);
          logger.info("*****Message id is: " + textMessage.getJMSMessageID());
          logger.info("*****Message correlation id is: " + textMessage.getJMSMessageID());
      } else {
        logger.info("*****Received obj: " + message);
      }
    } catch (Exception e) {
      logger.error("Caught: " + e);
    } finally {
      try {
        if(consumer != null) {
          consumer.close();
        }
        if(session != null) {
          session.close();
        }
        if(connection != null) {
          connection.close();
        }
      } catch(JMSException e) {/*nothing*/}
    }
  }
}

package atj;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

public class Consumer 
{
	private JMSContext jmsContext;
	private JMSConsumer jmsConsumer;
	private Queue queue;
	
	public Consumer(String selector, QueueAsynchConsumer queueAC)
	{
		ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
		jmsContext = connectionFactory.createContext();
		try 
		{
			queue = new com.sun.messaging.Queue("ATJQueue");
			jmsConsumer = jmsContext.createConsumer(queue, "SELECTOR <> '" + selector + "'");
			jmsConsumer.setMessageListener(queueAC);
		}
		catch(JMSException e) { e.printStackTrace(); }
	}
	
	public void close() 
	{
		jmsConsumer.close();
		jmsContext.close();
		
	}

}

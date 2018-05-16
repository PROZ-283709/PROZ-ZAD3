package atj;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import javafx.application.Platform;

public class QueueAsynchConsumer implements MessageListener
{
	TTTController ttt;
	
	public QueueAsynchConsumer(TTTController ttt)
	{
		this.ttt = ttt;
	}
	
	@Override
	public void onMessage(Message message)
	{
		Platform.runLater(() ->
		{
			try 
			{
				String msg = message.getStringProperty("MESSAGE");
				
				if(msg.length() > 7 && msg.substring(0, 7).equals("waiting")) 
				{
					ttt.setSecond(Integer.parseInt(msg.substring(7, msg.length())));
					return;
				}
				
				switch(msg)
				{
					case "leftUp":
						ttt.draw("enemy", 0, 0);
						break;
					case "up":
						ttt.draw("enemy", 0, 1);
						break;
					case "rightUp":
						ttt.draw("enemy", 0, 2);
						break;
					case "left":
						ttt.draw("enemy", 1, 0);
						break;
					case "middle":
						ttt.draw("enemy", 1, 1);
						break;
					case "right":
						ttt.draw("enemy", 1, 2);
						break;
					case "leftDown":
						ttt.draw("enemy", 2, 0);
						break;
					case "down":
						ttt.draw("enemy", 2, 1);
						break;
					case "rightDown":
						ttt.draw("enemy", 2, 2);
						break;
				}
			} catch (JMSException e) 
			{
				e.printStackTrace();
			}
		});
	}
}

package atj;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class TTTController 
{
	private String figure;
	private String enemyFigure;
	private boolean myTurn;
	private int selector;
	private Producer producer;
	private Consumer consumer;
	private Button cells[][];
	private int filledCells;
	
	@FXML
	Button buttonLeftUp, buttonUp, buttonRightUp, buttonLeft, buttonMiddle, buttonRight, buttonLeftDown, buttonDown, buttonRightDown;
	
	@FXML
	Text turnText, figureText;
	
	@FXML private void initialize()
	{	
		figure = "X";
		enemyFigure = "O";
		myTurn = false;
		filledCells = 0;
	    selector = new Random().nextInt(500000);
		System.out.println(selector +"");
		producer = new Producer(selector + "");
		consumer = new Consumer(selector + "", new QueueAsynchConsumer(this));
		
		cells = new Button[3][3];
		cells[0][0] = buttonLeftUp;
		cells[0][1] = buttonUp;
		cells[0][2] = buttonRightUp;
		cells[1][0] = buttonLeft;
		cells[1][1] = buttonMiddle;
		cells[1][2] = buttonRight;
		cells[2][0] = buttonLeftDown;
		cells[2][1] = buttonDown;
		cells[2][2] = buttonRightDown;
		
		producer.sendQueueMessage("waiting" + selector + "");
	}
	
	@FXML
	void buttonLeftUp_Click()
	{
		if(buttonLeftUp.getText().equals("") && myTurn)
		{
			draw("me", 0, 0);
			producer.sendQueueMessage("leftUp");
		}
	}
	
	@FXML
	void buttonUp_Click()
	{
		if(buttonUp.getText().equals("") && myTurn)
		{
			draw("me", 0, 1);
			producer.sendQueueMessage("up");
		}
	}
	
	@FXML
	void buttonRightUp_Click()
	{
		if(buttonRightUp.getText().equals("") && myTurn)
		{
			draw("me", 0, 2);
			producer.sendQueueMessage("rightUp");
		}
	}
	
	@FXML
	void buttonLeft_Click()
	{
		if(buttonLeft.getText().equals("") && myTurn)
		{
			draw("me", 1, 0);
			producer.sendQueueMessage("left");
		}
	}
	
	@FXML
	void buttonMiddle_Click()
	{
		if(buttonMiddle.getText().equals("") && myTurn) 
		{
			draw("me", 1, 1);
			producer.sendQueueMessage("middle");
		}
	}
	
	@FXML
	void buttonRight_Click()
	{
		if(buttonRight.getText().equals("") && myTurn)
		{
			draw("me", 1, 2);
			producer.sendQueueMessage("right");
		}
	}
	
	@FXML
	void buttonLeftDown_Click()
	{
		if(buttonLeftDown.getText().equals("") && myTurn)
		{
			draw("me", 2, 0);
			producer.sendQueueMessage("leftDown");
		}
	}
	
	@FXML
	void buttonDown_Click()
	{
		if(buttonDown.getText().equals("") && myTurn) 
		{
			draw("me", 2, 1);
			producer.sendQueueMessage("down");
		}
	}
	
	@FXML
	void buttonRightDown_Click()
	{
		if(buttonRightDown.getText().equals("") && myTurn) 
		{
			draw("me", 2, 2);
			producer.sendQueueMessage("rightDown");
		}
	}
	
	private int checkWin(int row, int column, String drawn)
	{
		//ROW
		if(cells[row][0].getText().equals(drawn) && cells[row][1].getText().equals(drawn) && cells[row][2].getText().equals(drawn))
			return drawn.equals(figure) ? 2 : 1;
		
		//COLUMN
	    if(cells[0][column].getText().equals(drawn) && cells[1][column].getText().equals(drawn) && cells[2][column].getText().equals(drawn))
	    	return drawn.equals(figure) ? 2 : 1;
	    
	    //LEFT TO RIGHT DIAGONALLY
	    if(cells[0][0].getText().equals(drawn) && cells[1][1].getText().equals(drawn) && cells[2][2].getText().equals(drawn))
	    	return drawn.equals(figure) ? 2 : 1;
	    
	    // RIGHT TO LEFT DIAGONALLY
	    if(cells[0][2].getText().equals(drawn) && cells[1][1].getText().equals(drawn) && cells[2][0].getText().equals(drawn))
	    	return drawn.equals(figure) ? 2 : 1;
		
		
		return  filledCells == 9 ? 3 : 0;
	}
	
	public void setSecond(int enemySelector)
	{
		if(enemySelector > selector)
		{
			figure = "O";
			enemyFigure = "X";
			myTurn = false;
			figureText.setText("Your Figure: O");
			turnText.setText("Enemy Turn");
		}
		else
		{
			myTurn = true;
			figureText.setText("Your Figure: X");
			turnText.setText("Your Turn");
		}
	}
	
	public void close()
	{
		consumer.close();
	}

	public void draw(String who, int row, int column)
	{
		String toDraw, turnToPrint;
		
		if(who.equals("me"))
		{
			 toDraw = figure;
			 myTurn = false;
			 turnToPrint = "Enemy Turn";
		}
		else
		{
			toDraw = enemyFigure;
			myTurn = true;
			turnToPrint = "Your Turn";
		}
		
		cells[row][column].setText(toDraw);
		filledCells++;
		
		switch(checkWin(row, column, toDraw))
		{
			case 0:
				turnText.setText(turnToPrint);
				break;
			case 1:
				turnText.setText("You Lost :C");
				myTurn = false;
				break;
			case 2:
				turnText.setText("You Won :D");
				myTurn = false;
				break;
			case 3:
				turnText.setText("Draw :|");
				myTurn = false;
				break;
		}
	}
}

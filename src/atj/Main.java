package atj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TicTacToe.fxml"));
			AnchorPane root = fxmlLoader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("TicTacToe");
			primaryStage.setResizable(false);
			TTTController tttController = fxmlLoader.getController();
			primaryStage.setOnHiding(e -> tttController.close());
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

}
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class winningController implements Initializable{
	
	@FXML
    private ImageView wView;
	
	private char winner;
	
	@FXML
	Button resetButton;
	
	@FXML
	public void restart() throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

		Scene scene = new Scene(root);
		
		scene.getStylesheets().setAll(resetButton.getScene().getStylesheets());
		
		home.globalStage.setScene(scene);

		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		winner = Controller.winner;
		
		if(winner == 'x') {
			
			wView.setImage(HomeController.xImg);
			
		}
		else if(winner == 'o'){
			
			wView.setImage(HomeController.oImg);
			
		}
		else {
			
			System.out.println("Ni");
			
		}
		
	}
	
}

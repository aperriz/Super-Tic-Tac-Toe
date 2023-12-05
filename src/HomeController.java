
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HomeController implements Initializable{

	@FXML
	private ToggleGroup difficultyRadioButtonGroup;

	@FXML
	private RadioButton easyRadioButton;

	@FXML
	private RadioButton hardRadioButton;

	@FXML
	private RadioButton medRadioButton;

	@FXML
	private RadioButton multiRadioButton;

	@FXML
	private ToggleGroup playerRadioButtonGroup;

	@FXML
	private RadioButton singleRadioButton;

	@FXML
	private Button startButton;

	public static boolean darkTheme = false;
	public static int gridSize = 3;
	public static Image xImg = new Image("x.png");
	public static Image oImg = new Image("o.png");



	@FXML
	void startGame(ActionEvent event) throws IOException {

		if(singleRadioButton.isSelected()) {

			Controller.player = 's';

			if(easyRadioButton.isSelected()) {

				Controller.difficulty = 'e';

			}
			else if(medRadioButton.isSelected()) {

				Controller.difficulty = 'm';

			}
			else if(hardRadioButton.isSelected()) {

				Controller.difficulty = 'h';

			}

		}
		else if(multiRadioButton.isSelected()) {

			Controller.player = 'm';

		}

		Parent root = FXMLLoader.load(getClass().getResource("SuperTicTacToe.fxml"));

		Scene scene = new Scene(root);

		if(darkTheme) {

			scene.getStylesheets().add("darkmode.css");

		}

		home.globalStage.setResizable(false);
		home.globalStage.setScene(scene);

	}

	@FXML
	public void toggleDifficultyButtons() {

		if(easyRadioButton.isDisable()) {

			easyRadioButton.setDisable(false);
			medRadioButton.setDisable(false);
			hardRadioButton.setDisable(false);

		}
		else {

			easyRadioButton.setDisable(true);
			medRadioButton.setDisable(true);
			hardRadioButton.setDisable(true);

		}

	}

	@FXML
	public void settings() throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));

		Scene scene = new Scene(root);

		if(darkTheme) {

			scene.getStylesheets().add("darkmode.css");

		}

		home.globalStage.setResizable(false);
		home.globalStage.setScene(scene);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}

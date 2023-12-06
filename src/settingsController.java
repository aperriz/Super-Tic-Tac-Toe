import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class settingsController implements Initializable {

	JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "\\Downloads");

	FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Images", ImageIO.getReaderFileSuffixes()
			);

	@FXML
	private SplitPane mainView;
	
	@FXML
	private Button backButton;

	@FXML
	private Button changeOButton;

	@FXML
	private Button changeXButton;

	@FXML
	private RadioButton darkRadio;

	@FXML
	private RadioButton lightRadio;

	@FXML
	private ImageView oImgView;

	@FXML
	private ToggleGroup themeGroup;

	@FXML
	private ImageView xImgView;

	@FXML
	void back(ActionEvent event) throws IOException{

		HomeController.xImg = xImgView.getImage();
		HomeController.oImg = oImgView.getImage();

		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

		Scene scene = new Scene(root);
		
		if(themeGroup.getSelectedToggle().equals(darkRadio)){
			HomeController.darkTheme = true;
			scene.getStylesheets().add("darkmode.css");
		}
		else {
			
			HomeController.darkTheme = false;
			
		}

		home.globalStage.setScene(scene);
		
	}

	@FXML
	public void changeX() {

		fileChooser.setFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(null);

		if(returnValue == JFileChooser.APPROVE_OPTION) {

			xImgView.setImage(new Image("file:///" + fileChooser.getSelectedFile().getAbsolutePath()));

		}

	}

	@FXML
	public void changeO() {

		fileChooser.setFileFilter(filter);
		
		int returnValue = fileChooser.showOpenDialog(null);

		if(returnValue == JFileChooser.APPROVE_OPTION) {


			oImgView.setImage(new Image("file:///" + fileChooser.getSelectedFile().getAbsolutePath()));


		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		xImgView.setImage(HomeController.xImg);
		oImgView.setImage(HomeController.oImg);
		
		themeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle oldValue, Toggle newValue) {
				// TODO Auto-generated method stub
				
				if(themeGroup.getSelectedToggle().equals(darkRadio)) {
					
					darkRadio.getScene().getStylesheets().add("darkmode.css");
					
				}
				else {
					if(darkRadio.getScene().getStylesheets().contains("darkmode.css")) {
						
						darkRadio.getScene().getStylesheets().remove("darkmode.css");
						
					}
				}
				
			}
			
		});	
		
	}

}

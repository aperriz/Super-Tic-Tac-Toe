
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Controller implements Initializable{

	public ArrayList<Button> allButtons, topLeftButtons, topCenterButtons, 
	topRightButtons, middleLeftButtons, middleCenterButtons, middleRightButtons, 
	bottomLeftButtons, bottomCenterButtons, bottomRightButtons;

	JFileChooser fileChooser = new JFileChooser();

	FileNameExtensionFilter filter = new FileNameExtensionFilter(
			".txt", "txt", "text"
			);

	private int delay = 100;

	private int turn = 0;

	private int tc;

	public static char winner = 'n';

	@FXML
	private Text turnCounter;

	@FXML
	private Button resetButton;

	@FXML
	private ImageView turnImage;

	private Image xImg = HomeController.xImg;

	private Image oImg = HomeController.oImg;

	GridPane gridPane;

	@FXML
	private AnchorPane centerPane;

	@FXML
	private BorderPane borderPane;

	public ArrayList<Character> gridList;
	public ArrayList<ArrayList<Button>> allArrays;

	public static char player = 'x';
	public static char difficulty = 'x';

	public boolean gameWon = false;

	int gridSize = HomeController.gridSize;

	int sizeVar;

	@FXML
	//Resets all grids and turn counter; returns to main menu
	void resetGame(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

		Scene scene = new Scene(root);
		scene.getStylesheets().setAll(resetButton.getScene().getStylesheets());

		home.globalStage.setScene(scene);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//System.out.println(player);
		//System.out.println(difficulty);

		sizeVar=74;

		turnImage.setImage(xImg);

		topLeftButtons = new ArrayList<Button>();
		topCenterButtons = new ArrayList<Button>();
		topRightButtons = new ArrayList<Button>();
		middleLeftButtons = new ArrayList<Button>();
		middleCenterButtons = new ArrayList<Button>();
		middleRightButtons = new ArrayList<Button>();
		bottomLeftButtons = new ArrayList<Button>();
		bottomCenterButtons = new ArrayList<Button>();
		bottomRightButtons = new ArrayList<Button>();

		allArrays = new ArrayList<ArrayList<Button>>(Arrays.asList(
				topLeftButtons, topCenterButtons, topRightButtons,
				middleLeftButtons, middleCenterButtons, middleRightButtons,
				bottomLeftButtons, bottomCenterButtons, bottomRightButtons));

		gridPane = new GridPane();

		gridPane.setStyle("-fx-grid-lines-visible: true");

		//System.out.println(gridPane);

		centerPane.getChildren().add(gridPane);

		borderPane.setCenter(centerPane);

		int rows = (int) Math.round(Math.pow(gridSize, 2));
		int cols = (int) Math.round(Math.pow(gridSize, 2));

		for(int r  = 0; r < rows; r++) {

			for(int c = 0; c < cols; c++) {

				AnchorPane aPane = new AnchorPane();

				aPane.setPrefHeight(sizeVar);
				aPane.setPrefWidth(sizeVar);

				Button b = new Button();
				b.setPrefWidth(sizeVar);
				b.setPrefHeight(sizeVar);

				b.setMaxWidth(sizeVar);
				b.setMaxHeight(sizeVar);

				b.setViewOrder(0);

				b.getStyleClass().add("enabled-button");

				b.setId("button" + r + c);


				b.setStyle("-fx-color: lightgreen; -fx-text-fill: transparent; -fx-font-size: 24px");

				if(c == 2 || c == 5){

					if(r == 2 | r == 5) {

						aPane.setStyle("-fx-border-width: 0 2 2 0; -fx-border-color: black; -fx-border-type: solid");

					}
					else {

						aPane.setStyle("-fx-border-width: 0 0 2 0; -fx-border-color: black; -fx-border-type: solid");

					}

				}
				else if(r == 2 || r == 5) {

					aPane.setStyle("-fx-border-width: 0 2 0 0; -fx-border-color: black; -fx-border-type: solid");

				}

				aPane.getChildren().add(b);

				gridPane.add(aPane, r, c);

				if(r < gridSize) {

					if(c < gridSize) {

						topLeftButtons.add(b);

					}
					else if(c > gridSize - 1 && c < gridSize * 2) {

						topCenterButtons.add(b);

					}
					else if (c > gridSize * 2 - 1) {

						topRightButtons.add(b);

					}

				}
				else if(r > gridSize - 1 && r < gridSize * 2) {

					if(c < gridSize) {

						middleLeftButtons.add(b);

					}
					else if(c > gridSize - 1 && c < gridSize * 2) {

						middleCenterButtons.add(b);

					}
					else if (c > gridSize * 2 - 1) {

						middleRightButtons.add(b);

					}

				}
				else if (r > 5) {

					if(c < gridSize) {

						bottomLeftButtons.add(b);

					}
					else if(c > gridSize - 1 && c < gridSize * 2) {

						bottomCenterButtons.add(b);

					}
					else if (c > gridSize * 2 - 1) {

						bottomRightButtons.add(b);

					}

				}

			}

		}

		//Used for setup
		allButtons = new ArrayList<>();

		for(ArrayList<Button> bList : allArrays) {

			allButtons.addAll(bList);

		}



		//Runs setup on all buttons
		allButtons.forEach(button ->{

			setupButton(button);
			button.setFocusTraversable(false);

		});

	}

	//Adds event listener to all buttons; runs on launch
	public void setupButton(Button button){

		button.setOnMouseClicked(mouseEvent ->{

			ArrayList<Button> next;

			ArrayList<Button> currentList = findArray(button);
			//Changes turn and disables button on button clicked

			button.setDisable(true);

			for (int i = 0; i < 9; i++) {

				if(currentList.get(i) == button) {

					next = allArrays.get(i);



					if(gameOver()) {

						gameWon = true;

						//System.out.println("Game Over!");
						//System.out.println("Winner: " + checkGameOver(gridList));
						break;

					}

					if(player == 'm') {

						setTurn(button);
						//Locks grids except for corresponding
						lockGrids(next, currentList);

					}
					else if(player == 's') {

						setTurn(button);

						//System.out.println(button.getId());

						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}



						if(checkGridOver(next) == 'n') {

							lockGrids(next, currentList);

							if(difficulty == 'e') {
								easyAi(next, currentList);
							}
							else if (difficulty == 'h') {
								//System.out.println('h');
								hardAi(next, currentList);
							}
							else {
								int c = new Random().nextInt(1,2);

								if(c==1) {
									hardAi(next, currentList);
								}
								else {
									easyAi(next, currentList);
								}
							}

						}
						else if (checkGridOver(currentList) == 'n') {

							lockGrids(next, currentList);

							if(difficulty == 'e') {
								easyAi(findArray(button), currentList);
							}
							else if (difficulty == 'h') {
								hardAi(currentList, currentList);
							}
							else {
								int c = new Random().nextInt(1,2);

								if(c==1) {
									hardAi(currentList, currentList);
								}
								else {
									easyAi(currentList, currentList);
								}
							}

						}
						else {

							boolean loop = true;

							while(loop) {

								int r = new Random().nextInt(0, 8);

								//System.out.println("else " + r);

								if(checkGridOver(allArrays.get(r)) == 'n') {

									unlockAll();
									easyAi(allArrays.get(r), currentList);
									loop = false;

								}

								try {
									Thread.sleep(delay);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}

						}

					}

					//System.out.println(i);
					//System.out.println(checkGridOver(findArray(button)));

				}

			}



			//System.out.println(checkGameOver(gridList));

		});

	}

	//Turn decider
	public void setTurn(Button button){

		if(turn % 2 == 0) {

			int r = Integer.parseInt(String.valueOf(button.getId().charAt(button.getId().length() - 2)));
			int c = Integer.parseInt(String.valueOf(button.getId().charAt(button.getId().length() - 1)));

			ImageView img = new ImageView(xImg);

			img.setPreserveRatio(false);
			img.setFitWidth(sizeVar);
			img.setFitHeight(sizeVar);

			gridPane.add(img, r, c);

			button.setText("X");
			turn++;
			turnImage.setImage(oImg);

		}
		else {

			int r = Integer.parseInt(String.valueOf(button.getId().charAt(button.getId().length() - 2)));
			int c = Integer.parseInt(String.valueOf(button.getId().charAt(button.getId().length() - 1)));

			ImageView img = new ImageView(oImg);

			img.setPreserveRatio(false);
			img.setFitWidth(sizeVar);
			img.setFitHeight(sizeVar);

			gridPane.add(img, r, c);

			button.setText("O");
			turn++;
			turnImage.setImage(xImg);

		}

		button.setDisable(true);

		tc = Math.round(turn/2) + 1;

		//sets turn counter
		turnCounter.setText(String.valueOf(tc));

		if(gameOver()) {

			gameWon = true;

		}

	}

	//Checks if game is over, returns boolean for use later
	public boolean gameOver() {

		if(checkGameOver(gridList) != 'n') {

			Parent root;
			try {
				winner = checkGameOver(gridList);
				
				//System.out.println("Winner: " + winner);

				for(ArrayList<Button> bl : allArrays) {
					for(Button b: bl){
						b.setDisable(true);
					}
				}
				
				root = FXMLLoader.load(getClass().getResource("gameOver.fxml"));

				Scene scene = new Scene(root);	

				scene.getStylesheets().setAll(resetButton.getScene().getStylesheets());

				home.globalStage.setScene(scene);


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;

		}

		return false;

	}

	//Checks if grid is won by either player; returns char
	public char checkGridOver(ArrayList<Button> nextList) {
		String line = "";

		String xString = "XXX";

		String oString = "OOO";



		for(int i = 0; i < 8; i++) {

			switch(i) {

			case 0 -> line = nextList.get(0).getText().toString() + nextList.get(1).getText().toString() + nextList.get(2).getText().toString();
			case 1 -> line = nextList.get(3).getText().toString() + nextList.get(4).getText().toString() + nextList.get(5).getText().toString();
			case 2 -> line = nextList.get(6).getText().toString() + nextList.get(7).getText().toString() + nextList.get(8).getText().toString();
			case 3 -> line = nextList.get(0).getText().toString() + nextList.get(4).getText().toString() + nextList.get(8).getText().toString();
			case 4 -> line = nextList.get(2).getText().toString() + nextList.get(4).getText().toString() + nextList.get(6).getText().toString();
			case 5 -> line = nextList.get(0).getText().toString() + nextList.get(3).getText().toString() + nextList.get(6).getText().toString();
			case 6 -> line = nextList.get(1).getText().toString() + nextList.get(4).getText().toString() + nextList.get(7).getText().toString();
			case 7 -> line = nextList.get(2).getText().toString() + nextList.get(5).getText().toString() + nextList.get(8).getText().toString();
			default -> line = null;

			}

			if(line.equalsIgnoreCase(xString)) {

				return 'x';

			}
			else if (line.equalsIgnoreCase(oString)) {

				return('o');
			}

		}

		return 'n';

	}

	//Checks if game is over; returns char
	public char checkGameOver(ArrayList<Character> nextList) {
		String line = "";
		for(int i = 0; i < 8; i++) {


			switch(i) {
			//Checks every possible set of win conditions
			case 0 -> line = String.format("%s%s%s", checkGridOver(topLeftButtons),checkGridOver(topCenterButtons),checkGridOver(topRightButtons));
			case 1 -> line = String.format("%s%s%s", checkGridOver(middleLeftButtons),checkGridOver(middleCenterButtons),checkGridOver(middleRightButtons));
			case 2 -> line = String.format("%s%s%s", checkGridOver(bottomLeftButtons),checkGridOver(bottomCenterButtons),checkGridOver(bottomRightButtons));
			case 3 -> line = String.format("%s%s%s", checkGridOver(topLeftButtons),checkGridOver(middleLeftButtons),checkGridOver(bottomLeftButtons));
			case 4 -> line = String.format("%s%s%s", checkGridOver(topCenterButtons),checkGridOver(middleCenterButtons),checkGridOver(bottomCenterButtons));
			case 5 -> line = String.format("%s%s%s", checkGridOver(topRightButtons),checkGridOver(middleRightButtons),checkGridOver(bottomRightButtons));
			case 6 -> line = String.format("%s%s%s", checkGridOver(topLeftButtons),checkGridOver(middleCenterButtons),checkGridOver(bottomRightButtons));
			case 7 -> line = String.format("%s%s%s", checkGridOver(topRightButtons),checkGridOver(middleCenterButtons),checkGridOver(bottomLeftButtons));
			default -> line = null;

			}

			//System.out.println(line);
			if(line.equalsIgnoreCase("XXX")) {

				return 'x';

			}
			else if (line.equalsIgnoreCase("OOO")) {
				return('o');
			}
		}


		return 'n';

	}

	//Finds array corresponding to provided button
	public ArrayList<Button> findArray(Button button){

		if(topLeftButtons.indexOf(button) != -1) {
			return topLeftButtons;
		}
		else if(topCenterButtons.indexOf(button) != -1) {
			return topCenterButtons;
		}
		else if(topRightButtons.indexOf(button) != -1) {
			return topRightButtons;
		}
		else if(middleLeftButtons.indexOf(button) != -1) {
			return middleLeftButtons;
		}
		else if(middleCenterButtons.indexOf(button) != -1) {
			return middleCenterButtons;
		}
		else if(middleRightButtons.indexOf(button) != -1) {
			return middleRightButtons;
		}
		else if(bottomLeftButtons.indexOf(button) != -1) {
			return bottomLeftButtons;
		}
		else if(bottomCenterButtons.indexOf(button) != -1) {
			return bottomCenterButtons;
		}
		else if(bottomRightButtons.indexOf(button) != -1) {
			return bottomRightButtons;
		}


		return null;
	}

	//Finds corresponding grid for button, locks the rest. Only unlocks if game and appropriate grid aren't over
	public void lockGrids(ArrayList<Button> enabledList, ArrayList<Button> currentList) {

		//System.out.println(checkGridOver(enabledList));

		for(ArrayList<Button> g : allArrays) {

			boolean unlock = false;

			for(Button b : enabledList) {

				if(b.getText() == ""  && checkGridOver(enabledList) == 'n') {

					unlock = true;

				}
				else if(checkGridOver(currentList) != 'n' && checkGridOver(enabledList) != 'n') {

					//System.out.println("Current: " + checkGridOver(currentList));
					//System.out.println("Next: " + checkGridOver(enabledList));
					unlockAll();

				}

			}

			if(unlock && !gameOver()) {

				if(g != enabledList) {

					//Not next list

					for(Button b : g) {

						b.setDisable(true);
						b.setStyle("-fx-color: black; -fx-text-fill: transparent; -fx-font-size: 24px");
					}

				}
				else {

					for(Button b : g) {

						//If next list
						b.setStyle("-fx-color: lightgreen; -fx-text-fill: transparent; -fx-font-size: 24px");
						if(b.getText()== "") {

							b.setDisable(false);

							//System.out.println(b.getId());
						}
						else {

							b.setStyle("-fx-color: black; -fx-text-fill: transparent; -fx-font-size: 24px");
						}
					}

				}

			}
			else if(gameOver()) {
				gameWon = true;

				for(Button b : g) {

					b.setDisable(true);

				}

			}

		}

	}

	public void easyAi(ArrayList<Button> nextList, ArrayList<Button> currentList) {


		boolean loop = true;

		if(gameOver()) {

			gameWon = true;

			//System.out.println("Game Over!");
			//System.out.println("Winner: " + checkGameOver(gridList));

		}
		else {

			while(loop && turn % 2 == 1) {

				int r = new Random().nextInt(0, 8);
				Button button = nextList.get(r);

				//System.out.println(r);
				//System.out.println(button.isDisabled());

				if(!nextList.get(r).isDisabled()) {

					setTurn(button);
					button.setDisable(true);

					for (int i = 0; i < 9; i++) {

						if(findArray(button).get(i) == button) {

							//Locks grids except for corresponding
							lockGrids(allArrays.get(i), currentList);

							//Checks if current grid is over
							checkGridOver(findArray(button));

							//System.out.println(i);
							//System.out.println(checkGridOver(findArray(button)));

						}

					}

					loop = false;

				}
				else if(checkGridOver(nextList) != 'n' && checkGridOver(currentList) != 'n') {

					unlockAll();

				}
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public void hardAi(ArrayList<Button> nextList, ArrayList<Button> currentList) {

		if(gameOver()) {

			gameWon = true;

			//System.out.println("Game Over!");
			//System.out.println("Winner: " + checkGameOver(gridList));

		}
		else {

			boolean loop = true;

			int required = 9;

			ArrayList<ArrayList<Button>> possibleArrays = new ArrayList<ArrayList<Button>>();


			while(loop) {

				//Finds arrays that have the most x
				for(ArrayList<Button> bList : allArrays) {

					int count = 0;

					//Counts x in each array
					for(Button b : bList) {

						if(b.getText() == "x") {

							count++;

						}

					}

					//Checks if count is more than required amount of X
					if(count > required) {

						possibleArrays.add(bList);

					}

				}

				if(!possibleArrays.isEmpty()) {

					loop = false;
					//System.out.println(possibleArrays);
				}
				else {

					required--;

				}

			}


			boolean possible = false;
			int r = 0;


			//Print possible arrays
			//System.out.println(possibleArrays);

			//Checks if any possible grids are not over
			for(ArrayList<Button> b : possibleArrays) {

				if(gameOver()) {

					gameWon = true;

					//System.out.println("Game Over!");
					//System.out.println("Winner: " + checkGameOver(gridList));
					break;

				}

				else if(checkGridOver(b) == 'n') {

					while(!possible) {

						//Gets random array from possible list
						r = new Random().nextInt(0,possibleArrays.size()-1);


						//Checks if grid is over
						if(checkGridOver(possibleArrays.get(r)) == 'n' && !nextList.get(r).isDisabled() && checkGridOver(nextList) == 'n') {

							Button button = nextList.get(r);

							//Sets turn, breaks loop
							setTurn(nextList.get(r));

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							for (int i = 0; i < 9; i++) {

								if(findArray(button).get(i) == button) {

									//Locks grids except for corresponding
									lockGrids(allArrays.get(i), currentList);

									//Checks if current grid is over
									if(checkGridOver(findArray(button)) != 'n') {

										unlockAll();

									}

									//System.out.println(allArrays.get(i));



									//System.out.println(i);
									//System.out.println(checkGridOver(findArray(button)));

								}

							}

							possible = true;
							break;

						}

					}
					break;

				}


			}

			//If still ai turn, easyAi
			if(turn % 2 == 0) {

				//System.out.println("hard -> easy");

				//if for loop fully completes, runs easyAi
				easyAi(nextList, currentList);

			}


		}

	}

	public void unlockAll() {

		for(ArrayList<Button> bList : allArrays) {

			if(checkGridOver(bList) == 'n') {

				for(Button b : bList) {

					if(b.getText() == "") {

						b.setStyle("-fx-color: lightgreen; -fx-text-fill: transparent; -fx-font-size: 24px");
						b.setDisable(false);

					}
					else {

						b.setStyle("-fx-color: black; -fx-text-fill: transparent; -fx-font-size: 24px");
					}

				}

			}

		}

	}

	public void save() {

		ArrayList<Button> currentArray = null;

		fileChooser.setFileFilter(filter);

		int returnValue = fileChooser.showSaveDialog(null);

		if(returnValue == JFileChooser.APPROVE_OPTION) {	

			try {

				String buttonString = "";

				for(Button b : allButtons) {
					//System.out.println("h");

					if(b.getText().equals("")) {

						buttonString = buttonString.concat("n");

					}
					else {
						//System.out.println("else:" + b.getText());
						buttonString = buttonString.concat(b.getText());

					}

				}

				boolean find = true;

				while(find) {

					for(ArrayList<Button> bList : allArrays) {

						for(Button b : bList) {

							//System.out.println(b);

							if(!b.isDisabled()) {

								currentArray = bList;
								find = false;
								break;

							}

						}

					}

				}

				//System.out.println(buttonString);

				try (FileWriter fw = new FileWriter(fileChooser.getSelectedFile().getAbsolutePath())) {


					fw.write(String.format("%s%n%s%n%s", buttonString, turnCounter.getText(), currentArray));;

				}


			}
			catch(Exception e) {

				e.printStackTrace();

			}


		}
		else {

			JOptionPane.showMessageDialog(null, "No File Selected!");

		}


	}

	public void load() {

		fileChooser.setFileFilter(filter);

		int returnValue = fileChooser.showOpenDialog(null);

		String fileName = "";

		if(returnValue == JFileChooser.APPROVE_OPTION) {

			fileName = fileChooser.getSelectedFile().getName();

			try(Scanner input = new Scanner(Paths.get(fileName))) {

				String inp = input.next();

				for(var i = 0; i < allButtons.size(); i++) {

					if(inp.charAt(i) != 'n') {

						allButtons.get(i).setText(Character.toString(inp.charAt(i)));

					}

					else {

						allButtons.get(i).setText("");

					}

				}

				tc = input.nextInt();

				if(tc%2 == 0) {turnImage.setImage(oImg);}
				else {turnImage.setImage(xImg);}

				turnCounter.setText(String.valueOf(tc));

				input.nextLine();

				String ca = input.nextLine();

				//System.out.println(ca);

				for(ArrayList<Button> bList : allArrays) {

					if(bList.toString().equals(ca)) {

						//System.out.println(bList.toString());

						for(Button b : bList) {

							if(b.getText() != "") {

								b.setDisable(true);

							}

						}

					}
					else {

						for(Button b : bList) {

							b.setDisable(true);

						}



					}

				}

			} 
			catch(Exception e) {

				e.printStackTrace();

			}

		}
		else {

			JOptionPane.showMessageDialog(null, "No File Selected!");

		}

	}

}
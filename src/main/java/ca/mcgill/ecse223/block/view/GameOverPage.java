package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameOverPage implements IPage{

	Stage stage;

	public GameOverPage(Stage stage) {
		this.stage = stage;

	}
	
	public void display() {
	    // Create the login grid pane
        GridPane gridPane = createGridPane();
        
        //Create an HBox to hold the back button at the top of the screen
        HBox hbButtons = new HBox();
        
        // Add the UI components to the grid pane
        try {
			addUIComponents(gridPane);
		} catch (Exception e) {
			Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error loading page:", e.getMessage());
		}
        // Create the scene with gridPane as the root node
        Scene scene = new Scene(gridPane, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT, Color.WHITE);
        // Set the scene and display it
        stage.setScene(scene);
        stage.show();
        
        //Add Back Button, requires catch for file not found
        try {
        	FileInputStream backImageInput = new FileInputStream("Images/next.png");
        	Image backImage = new Image(backImageInput); 
            ImageView backImageView = new ImageView(backImage); 
            backImageView.setFitHeight(60);
            backImageView.setFitWidth(60);
            
            Button backButton = new Button("", backImageView);
            backButton.setStyle("-fx-base: #92D3FC;");	// Styles the default background color of the button
            hbButtons.getChildren().add(backButton);
            hbButtons.setAlignment(Pos.CENTER_LEFT);
            
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                	Block223Controller.logout();
                	IPage login = Block223Application.getPage(Pages.AvaliableGamesPlayer);
                	login.display();
                }
            });
            
        } catch (FileNotFoundException e) {
        	System.out.println("File not found");
        }
        
		// border pane to display
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(gridPane);

		// setting border plane dimensions and specifications
		stage.setTitle("Game Over");
		stage.setScene(scene); 
		stage.show(); 
	}
	
	private GridPane createGridPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and horizontally
        gridPane.setAlignment(Pos.CENTER);
        
        return gridPane;
    }
	
    private void addUIComponents(GridPane gridPane) throws FileNotFoundException, Exception {
    	
    	TOHallOfFameEntry hof = null;
    	
		// hall of fame entries
		TOHallOfFame toHof = hof.getTOHallOfFame();
		
		// putting entries into a list
		ObservableList hallOfFame = FXCollections.observableArrayList();

		// getting List of hof entries
		List<TOHallOfFameEntry> entries = toHof.getEntries();
		
		// adding entries to an ObservableList
		for(TOHallOfFameEntry entry: entries) {
			hallOfFame.add(entry);
		}
		
		for (int i = 0; i < entries.size(); i++) {

			// player names
			String players = entries.get(i).getPlayername();

			// scores
			String scores = Integer.toString(entries.get(i).getScore());

			// creating grid pane for display
			GridPane pane = new GridPane();
			pane.setHgap(5);
			pane.setVgap(5);
			pane.setPadding(new Insets(25,25,25,25));

			// Player name label
			Label playerLabel = new Label("Player");
	        playerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
			TextField textField1 = new TextField(players);
			pane.add(playerLabel, 0, 1);
			pane.add(textField1, 1, 1);

			// Score label
			Label scoreLabel = new Label("Score");
	        scoreLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
			TextField textField2 = new TextField(scores);
			pane.add(scoreLabel, 0, 2);
			pane.add(textField2, 1, 2);
		}
        
		// setting index restrictions for navigating the hof list
		IntegerProperty index = new SimpleIntegerProperty();
		IntegerBinding displayIndex = index.add(10);
		SimpleListProperty<HallOfFameEntry> listSize = new SimpleListProperty<>(FXCollections.observableArrayList());
		IntegerProperty size = new SimpleIntegerProperty();
		size.bind(listSize.sizeProperty());

		// managing the size of the list
		size.addListener((o, oldValue, newValue) -> {
			// keep index valid when entries are added
			int newSize = newValue.intValue();
			int i = index.get();
			if (i+1 >= newSize) {
				index.set(Math.max(0, newSize + 1));
			}
		});
		
		// button for viewing 10 previous entries
		Button previous = new Button("<");
		previous.setOnAction(evt -> {
			index.set(index.get() - 10);
		});
		previous.disableProperty().bind(index.lessThanOrEqualTo(0));
		
		// button for viewing 10 following entries
		Button next = new Button(">");
		if(index.get() + 10 < hallOfFame.size()) {
			next.setOnAction(evt -> {
				index.set(index.get() + 10);
			});
		}else {
			next.setOnAction(evt -> {
				index.set(index.get() + (hallOfFame.size()-index.get()));
			});
			next.disableProperty().bind(displayIndex.greaterThanOrEqualTo(size));
		}



		HBox navigation = new HBox(10, previous, next);
		navigation.setFillHeight(true);		

        
    }

}
package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.model.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.ColorPicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

public class CreateBlockPage implements IPage{
	Stage stage;

	public CreateBlockPage(Stage stage) {
		this.stage = stage;
	}

	public void display() {

		// Create grid pane for the page
		GridPane gridPane = new GridPane();
		//createGridPane();

		// Add the UI components to the grid pane
		addUIComponents(gridPane);


		//Create an HBox to hold the back button at the top of the screen
		HBox hbButtons = new HBox();

		//Add Back Button, requires catch for file not found
		try {
			FileInputStream backImageInput = new FileInputStream("Images/next.png");
			Image backImage = new Image(backImageInput); 
			ImageView backImageView = new ImageView(backImage); 
			backImageView.setFitHeight(60);
			backImageView.setFitWidth(60);

			Button backButton = new Button("", backImageView);
			backButton.setStyle("-fx-base: #92D3FC;");	//Styles the default background color of the button
			hbButtons.getChildren().add(backButton);
			hbButtons.setAlignment(Pos.CENTER_LEFT);

			backButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					IPage availableBlocks = Block223Application.getPage(Pages.AvailableBlocks);
					availableBlocks.display();
				}
			});
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		
		
        BorderPane root = new BorderPane();
	    root.setPadding(new Insets(20)); // space between elements and window border
	    root.setCenter(gridPane);
	    root.setTop(hbButtons);
	    
	    // Create the scene with gridPane as the root node
	    Scene scene = new Scene(root, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT);
		
		// Set the title scene and display it
		stage.setTitle("Create Block");
		stage.setScene(scene);
		stage.show();
	}


	private GridPane createGridPane() {
		// Instantiate a new Grid Pane
		GridPane gridPane = new GridPane();

		// Position the pane at the center of the screen, both vertically and horizontally
		gridPane.setAlignment(Pos.CENTER);

		// Set a padding of 20px on each side
		gridPane.setPadding(new Insets(40, 40, 40, 40)); 

		// Set the horizontal gap between columns
		gridPane.setHgap(10);

		// Set the vertical gap between rows
		gridPane.setVgap(10);

		// Add Column Constraints

		// columnOneConstraints will be applied to all the nodes placed in column one.
		ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
		columnOneConstraints.setHalignment(HPos.LEFT);

		// columnTwoConstraints will be applied to all the nodes placed in column two.
		ColumnConstraints columnTwoConstraints = new ColumnConstraints(200,200, Double.MAX_VALUE);
		columnTwoConstraints.setHalignment(HPos.CENTER);

		//Column Three
		ColumnConstraints columnThreeConstraints = new ColumnConstraints(10,100, Double.MAX_VALUE);
		columnThreeConstraints.setHalignment(HPos.CENTER);

		//Column Four
		ColumnConstraints columnFourConstraints = new ColumnConstraints(200,200, Double.MAX_VALUE);
		columnFourConstraints.setHalignment(HPos.CENTER);

		gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints, columnFourConstraints);
		return gridPane;
	}

	private void addUIComponents(GridPane gridPane) {
		// Add Header
		Label headerLabel = new Label("Create Block");
		headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
		gridPane.add(headerLabel, 0,0,2,1);
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

		// Add Done Button
		Button create = new Button("Create");
		create.setPrefHeight(40);
		create.setDefaultButton(true);
		create.setPrefWidth(100);
		gridPane.add(create, 0, 4, 2, 1);
		GridPane.setHalignment(create, HPos.CENTER);
		GridPane.setMargin(create, new Insets(20, 0,20,0));

		// Add Value label for number of points for the block being updated
		Label valueLabel = new Label("Value  ");
		gridPane.add(valueLabel, 0, 3);
		
		// Add Value text field
		TextField valueField = new TextField();
		valueField.setPrefHeight(30);
		valueField.setText("0");
		gridPane.add(valueField, 1, 3);

		
		// Add color picker button 
		final ColorPicker colorPicker = new ColorPicker();
		colorPicker.setValue(Color.WHITE);

		final Rectangle colorSquare = new Rectangle();
		colorSquare.setX(20);
		colorSquare.setY(20);
		colorSquare.setFill(colorPicker.getValue());
		gridPane.add(colorSquare, 3, 1, 2, 1);
		gridPane.add(colorPicker, 3, 1, 1, 1);
		GridPane.setMargin(colorPicker, new Insets(20, 0,20,0));
		GridPane.setMargin(colorSquare, new Insets(20, 0,20,0));
		// Sets color-chooser button interface similar SplitMenuButton
		//colorPicker.getStyleClass().add("split-button");
		// Sets color-chooser button interface similar MenuButton
		//colorPicker.getStyleClass().add("button");

		// Implementing done button function
		create.setOnAction(new EventHandler<ActionEvent>(){
			@Override

			public void handle(ActionEvent event) {
				if(valueField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Value can't be empty", "Please select a value");
				}
				
				int points = Integer.parseInt(valueField.getText());
				
				Color color = colorPicker.getValue();
	            int r = (int)( color.getRed() * 255 );
	            int g = (int)( color.getGreen() * 255 );
	            int b = (int)( color.getBlue() * 255 );
				try {
					Block223Controller.addBlock(r, g, b, points);
					
					IPage availableBlocks = Block223Application.getPage(Pages.AvailableBlocks);
					availableBlocks.display();
				} catch (InvalidInputException e) {
					Components.showAlert(AlertType.ERROR, stage.getOwner(), "ERROR", "Unable to update block:\n" + e.getMessage());
				}
				
				return;
			}});     	

		// Not accepting anything other than numbers in valueField
		DecimalFormat format = new DecimalFormat("#.0");

		valueField.setTextFormatter(new TextFormatter<>(valueControl ->
		{
			if (valueControl.getControlNewText().isEmpty())
			{
				return valueControl;
			}

			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(valueControl.getControlNewText(), parsePosition);

			if (object == null || parsePosition.getIndex() < valueControl.getControlNewText().length())
			{
				return null;
			}
			else
			{
				return valueControl;
			}			
			// implementing error restriction on value of blocks in points
			//try {
			//	int points = Integer.parseInt(valueField.getText());
			//} catch (NumberFormatException n) {
			//	showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Create Game Error", "Please enter only numbers in the numeric fields\n and ensure no fields are empty.");
			//} 
		}));

		// Changing color of Block
		colorPicker.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colorPicker.setValue(colorPicker.getValue());
				Color color = (Color) colorSquare.getFill();
				String hex = String.format( "#%02X" + " " + "#%02X" + " " + "#%02X",
						(int)( color.getRed() * 255 ),
						(int)( color.getGreen() * 255 ),
						(int)( color.getBlue() * 255 ) );
			}
		});
	}

	private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}

	// method for instantiating spinner to toggle through points for block
	@SuppressWarnings("incomplete-switch")
	public  static void initializeSpinner(final Spinner<Integer> spinner, final int minValue, final int maxValue, final int initialValue) {
		spinner.getEditor().setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case UP:
				spinner.increment(1);
				break;
			case DOWN:
				spinner.decrement(1);
				break;
			}
		});
		spinner.setOnScroll(e -> {
			spinner.increment((int) (e.getDeltaY() / e.getMultiplierY()));
		});

		SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, initialValue);
		spinner.setValueFactory(factory);
		spinner.setEditable(true);

		TextFormatter<Integer> formatter = new TextFormatter<>(factory.getConverter(), factory.getValue());
		spinner.getEditor().setTextFormatter(formatter);
		factory.valueProperty().bindBidirectional(formatter.valueProperty());

	}


}

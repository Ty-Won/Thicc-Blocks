package ca.mcgill.ecse223.block.view;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.paint.Color;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

public class UpdateBlockPage {
	Stage stage;
	
	public UpdateBlockPage(Stage stage) {
		this.stage = stage;
		
	}
	
	public void display() {
		
        // Create grid pane for the page
        GridPane gridPane = new GridPane();
        		//createGridPane();
        
        // Add the UI components to the grid pane
        addUIComponents(gridPane);
        
        // Create the scene with gridPane as the root node
        Scene scene = new Scene(gridPane, 1000, 600);
        
        // Set the title scene and display it
        stage.setTitle("Update Block");
        stage.setScene(scene);
        stage.show();
	}
	
    private void addUIComponents(GridPane gridPane) {
        // Add Header
        Label headerLabel = new Label("Edit Block");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        gridPane.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));
        
        // Add Done Button
        Button createUserButton = new Button("Done");
        createUserButton.setPrefHeight(40);
        createUserButton.setDefaultButton(true);
        createUserButton.setPrefWidth(100);
        gridPane.add(createUserButton, 0, 4, 2, 1);
        GridPane.setHalignment(createUserButton, HPos.CENTER);
        GridPane.setMargin(createUserButton, new Insets(20, 0,20,0));
        
        // Add Color label
        Label colorLabel = new Label("Color  ");
        gridPane.add(colorLabel, 0, 1);
        
        // Add Color text field
        TextField colorField = new TextField();
        colorField.setPrefHeight(30);
        gridPane.add(colorField, 1, 1);
        
        // Add Value label for number of points for the block being updated
        Label valueLabel = new Label("Value  ");
        gridPane.add(valueLabel, 0, 3);
        
        // Add Value text field
        TextField valueField = new TextField();
        valueField.setPrefHeight(30);
        gridPane.add(valueField, 1, 3);
        
        // Add spinner component for incrementing/decrementing points for a block
        final Spinner<Integer> upDown = new Spinner<Integer>();
        upDown.setPrefHeight(30);
        gridPane.add(upDown, 2, 3);
        
        initializeSpinner(upDown, 1, 1000, 1);

        // Add color pick button 
        Button colorPickButton = new Button("");
        colorPickButton.setPrefHeight(30);
        colorPickButton.setDefaultButton(true);
        colorPickButton.setPrefWidth(30);
        gridPane.add(colorPickButton, 2, 1, 1, 1);
        GridPane.setMargin(colorPickButton, new Insets(20, 0,20,0));
        
        // Implementing colorPickButton's function
        colorPickButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	
        	public void handle(ActionEvent event) {
        		if(colorField.getText().isEmpty()) {
        			showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Color can't be empty", "Please select a color");
        			return;
        		}
        		if(valueField.getText().isEmpty()) {
        			showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Value can't be empty", "Please select a value");
        		}
        		//TODO: try/catch 
              
            }        	
        	
        });
        
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
    		//TODO: try/catch        
        }));
      
        
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

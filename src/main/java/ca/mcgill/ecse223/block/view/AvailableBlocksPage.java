package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.application.Block223Application.Pages;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AvailableBlocksPage implements IPage {

    Stage stage;
    private HashMap<HBox, Integer> hboxMapping = new HashMap<HBox, Integer>();

    

    public AvailableBlocksPage(Stage stage) {
        this.stage = stage;
    }

    public void display() {

        // Create the Available Levels grid pane
        GridPane gridPane = createGridPane();
        // Add the UI components to the grid pane
        try {
            addUIComponents(gridPane);
        } catch (FileNotFoundException e) {
            Components.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Error loading page:",
                    e.getMessage());
        }

        BorderPane root = new BorderPane();

        Label headerLabel = new Label("Available Blocks");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        
        // // Add Login Button
        // Button doneButton = new Button("DONE");
        // doneButton.setPrefHeight(40);
        // doneButton.setDefaultButton(true);
        // doneButton.setPrefWidth(130);
        // doneButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        // doneButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        
        // root.setAlignment(doneButton, Pos.CENTER);
        // root.setMargin(doneButton, new Insets(20, 0,20,0));

        

        root.setCenter(gridPane);
        root.setAlignment(headerLabel, Pos.CENTER);
        root.setTop(headerLabel);
        // root.setBottom(doneButton);


        Scene scene = new Scene(root, Block223Application.APPLICATION_WIDTH, Block223Application.APPLICATION_HEIGHT,
                Color.WHITE);
        // Set the title scene and display it
        stage.setTitle("Available Blocks");
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createGridPane() {
        // Instantiate a new Grid Pane
        GridPane gridPane = new GridPane();

        // Position the pane at the center of the screen, both vertically and
        // horizontally
        gridPane.setAlignment(Pos.CENTER);

        // Set a padding of 20px on each side
        gridPane.setPadding(new Insets(40, 40, 40, 40));

        // Set the horizontal gap between columns
        gridPane.setHgap(10);

        // Set the vertical gap between rows
        gridPane.setVgap(10);

        // // Add Column Constraints

        // // columnOneConstraints will be applied to all the nodes placed in column one.
        // ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        // columnOneConstraints.setHalignment(HPos.LEFT);

        // // columnTwoConstraints will be applied to all the nodes placed in column two.
        // ColumnConstraints columnTwoConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        // columnTwoConstraints.setHalignment(HPos.CENTER);

        // // Column Three
        // ColumnConstraints columnThreeConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        // columnThreeConstraints.setHalignment(HPos.CENTER);

        // // Column Four
        // ColumnConstraints columnFourConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        // columnFourConstraints.setHalignment(HPos.CENTER);

        // gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints,
        //         columnFourConstraints);
        return gridPane;
    }

    private void addUIComponents(GridPane gridPane) throws FileNotFoundException {
        List<TOBlock> toBlocks = null;
        int numberOfBlocks = 0;
        VBox blockContainer = addVBox();
        blockContainer.setPadding(new Insets(10));
        blockContainer.setSpacing(8);

        try {
            toBlocks = Block223Controller.getBlocksOfCurrentDesignableGame();
            numberOfBlocks = toBlocks.size();
        } catch (InvalidInputException e) {
            Components.showAlert(AlertType.INFORMATION, stage.getOwner(), "",
                    "Error getting available game blocks:\n" + e.getMessage());
        }
        if (toBlocks != null) {
            for (TOBlock blockType : toBlocks) {
                int id = blockType.getId();
                int red = blockType.getRed();
                int blue = blockType.getBlue();
                int green = blockType.getGreen();
                int points = blockType.getPoints();

                HBox blockRow = addHBox(id, red, green, blue, points);
                hboxMapping.put(blockRow,id);
                VBox.setMargin(blockRow, new Insets(0, 0, 0, 8));
                blockContainer.getChildren().add(blockRow);
            }
        }
        gridPane.add(blockContainer, 5, 5, 2, 5);


        // Add Add Block
        Button addButton = new Button("Add Block");
        addButton.setPrefHeight(40);
        addButton.setDefaultButton(true);
        addButton.setPrefWidth(130);
        addButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        addButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        
        GridPane.setHalignment(addButton, HPos.CENTER);
        gridPane.add(addButton,7,10,2,1);

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                IPage createBlockPage = Block223Application.getPage(Pages.CreateBlock);
                createBlockPage.display();
            }
        });





        // Add Done Button
        Button doneButton = new Button("DONE");
        doneButton.setPrefHeight(40);
        doneButton.setDefaultButton(true);
        doneButton.setPrefWidth(130);
        doneButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        doneButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        
        GridPane.setHalignment(doneButton, HPos.CENTER);
        gridPane.add(doneButton,5,10,2,1);

        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
                IPage updateGamePage = Block223Application.getPage(Pages.UpdateGame);
                updateGamePage.display();
            }
        });





    }

    public HBox addHBox(int id, int red, int green, int blue, int points) {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #fff;"+"-fx-padding: 10;" + "-fx-border-style: solid inside;"
        + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
         + "-fx-border-color: black;");


        Button buttonEdit = new Button("Edit");
        buttonEdit.setPrefSize(100, 20);

        Button buttonDelete = new Button("Delete");
        buttonDelete.setPrefSize(100, 20);

        addBlockColor(hbox, red, green, blue);
        hbox.getChildren().addAll(buttonEdit, buttonDelete);

        buttonEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {       
            
                HBox parentNode = (HBox)buttonEdit.getParent();
                int id = hboxMapping.get(parentNode);
                UpdateBlockPage updateBlockpage = (UpdateBlockPage)Block223Application.getPage(Pages.UpdateBlock);
                updateBlockpage.setBlockID(id);
            	updateBlockpage.display();

            }
        });


        buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {       
            
                HBox parentNode = (HBox)buttonEdit.getParent();
                int id = hboxMapping.get(parentNode);
                try{
                    Block223Controller.deleteBlock(id);
                } catch (InvalidInputException e) {
                    Components.showAlert(AlertType.INFORMATION, stage.getOwner(), "",
                    "Error deleting game blocks:\n" + e.getMessage());
                }

                IPage AvailableBlocksPage = Block223Application.getPage(Pages.AvailableBlocks);
                AvailableBlocksPage.display();

            }
        });

        return hbox;
    }

    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        // Text title = new Text("Data");
        // title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        // vbox.getChildren().add(title);

        // Hyperlink options[] = new Hyperlink[] { new Hyperlink("Sales"), new
        // Hyperlink("Marketing"),
        // new Hyperlink("Distribution"), new Hyperlink("Costs") };

        // for (int i = 0; i < 4; i++) {
        // VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
        // vbox.getChildren().add(options[i]);
        // }

        return vbox;
    }

    public void addBlockColor(HBox hb, int red, int green, int blue) {
        StackPane stack = new StackPane();
        Rectangle colorIcon = new Rectangle(30.0, 25.0);
        colorIcon.setFill(Color.rgb(red, green, blue));
        colorIcon.setStroke(Color.web("#000"));
        colorIcon.setArcHeight(3.5);
        colorIcon.setArcWidth(3.5);

        stack.getChildren().addAll(colorIcon);
        stack.setAlignment(Pos.CENTER_RIGHT); // Right-justify nodes in stack
        hb.getChildren().add(stack); // Add to HBox from Example 1-2
        HBox.setHgrow(stack, Priority.ALWAYS); // Give stack any extra space
    }

}

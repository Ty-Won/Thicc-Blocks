package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import javafx.util.Callback;
import javafx.scene.shape.Rectangle;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;


public class EditLevelPage implements IPage {
	
    Stage stage;
    
    /**
     * Root border pain
     * REGIONS:
     *  1. RIGHT  -> Block selection (List of blocks to drag and drop)
     *  2. LEFT   -> EMPTY
     *  3. CENTER -> GridPane (Represents level)
     *  4. TOP    -> HBox (Information)
     */
    BorderPane root;

    /**
     * Blocks associated with the current game
     */
    ObservableList<TOBlock> blocks;

    /**
     * ID of the current level being modified
     */
    private int levelID;

    /** 
     * Defines the type of a block drag, either
     * movement or positioning
    */
    private enum BlockDragType {
        POSITION_BLOCK,
        MOVE_BLOCK
    }

    /**
     * Source rectangle for a drage type MOVE_BLOCK
     */
    private Rectangle sourceRectangle;

    /**
     * Define the type of drag to be default POSITION_BLOCK
     */
    private BlockDragType dragType = BlockDragType.POSITION_BLOCK;

	public EditLevelPage(Stage stage) {
        this.stage = stage;
        
        // Get the current game's block
        try {
            this.blocks = FXCollections.observableList(Block223Controller.getBlocksOfCurrentDesignableGame());
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }
    }
	
	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}
	
	public void display() {

        // Get the current game's block
        try {
            this.blocks = FXCollections.observableList(Block223Controller.getBlocksOfCurrentDesignableGame());
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }

        // Create the main border pain.        
        this.root = new BorderPane();

        // Create right vbox
        VBox rightPane = new VBox();
        rightPane.setPadding(new Insets(10));
        rightPane.setSpacing(8);

        // Create top HBOX
        HBox topPane = new HBox();

        // ----- UI CREATION ----

        // Create block list view
        ListView<TOBlock> lview = createBlockSelectionUI();

        // Create block placement grid pane
        GridPane gridPane = createBlockPlacementUI();

        // Add done button
        Button doneButton = new Button("Done");
        doneButton.setPrefHeight(40);
        doneButton.setDefaultButton(true);
        doneButton.setPrefWidth(130);
        doneButton.setStyle("-fx-background-color: #000;-fx-text-fill: #fff;");
        doneButton.setFont(Font.font("Arial", FontWeight.MEDIUM, 20));
        GridPane.setHalignment(doneButton, HPos.CENTER);
        GridPane.setMargin(doneButton, new Insets(20, 0,20,0));

        // Add Header
        Label headerLabel = new Label("THICC BLOCKS");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        // ---------------------


        rightPane.getChildren().add(lview);
        rightPane.getChildren().add(doneButton);

        topPane.getChildren().add(headerLabel);

        // Set BorderPane elements
        root.setRight(rightPane);
        root.setCenter(gridPane);
        root.setTop(topPane);

        Scene scene = new Scene(root, 600, 450);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Creates the block selection UI
     * 
     * @param ListView<TOBlock> - ListView containing a set of Block TOs
     */
    private ListView<TOBlock> createBlockSelectionUI() {        
        ListView<TOBlock> list = new ListView<>();
        list.setPrefWidth(100);
        list.setItems(blocks);

        // Define the listView's cell factory
        list.setCellFactory(new Callback<ListView<TOBlock>, 
            ListCell<TOBlock>>() {
                @Override 
                public ListCell<TOBlock> call(ListView<TOBlock> list) {
                    BlockListCell cell = new BlockListCell();
                    
                    cell.setOnDragDetected(new EventHandler <MouseEvent>() {
                        public void handle(MouseEvent event) {
                            /* drag was detected, start drag-and-drop gesture*/
                            System.out.println("onDragDetected");
                            /* allow any transfer mode */
                            Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                            
                            /* put a string on dragboard */
                            ClipboardContent content = new ClipboardContent();

                            // Set drag type
                            dragType = BlockDragType.POSITION_BLOCK;
                            content.putString(Integer.toString(cell.getItem().getId()));
                            db.setContent(content);                           
                            event.consume();
                        }
                    });
              
                    return cell;
                }
            }
        );

        return list;
    }
    
    /**
     * Creates the block placement UI
     */
    private GridPane createBlockPlacementUI() {
        GridPane grid = new GridPane();
        grid.setPrefWidth(390);
        grid.setPrefHeight(390);
        grid.setHgap(5);
        grid.setVgap(2);
        grid.setPadding(new Insets(0, 10, 0, 10));
        //grid.setGridLinesVisible(true);

        int[] cap = Block223Controller.getMaxBlockCapacity();

        for (int x = 0; x < cap[0]-1; x++) {
            for (int y = 0; y < cap[1]-1; y++) {

                // Allocate each cell a white rectangle
                Rectangle rect = new Rectangle(20, 20, Color.WHITE);
                
                // ---- TODO :: ADD BLOCK ASSIGNMENT LOADING -----

                // Set the X & Y coordinates to +1 of actual.
                // gridHorizontalPosition & gridVerticlePosition are indexed from 1
                int gridHorizontalPosition = x + 1;
                int gridVerticalPosition = y + 1;
                rect.setX(gridHorizontalPosition);
                rect.setY(gridVerticalPosition);

                setupTargetDragAndDrop(rect);
                grid.add(rect, x, y);
            }
        }

        return grid;
    }

    private void setupTargetDragAndDrop(Rectangle target) {
        
        // ---------- INTERMEDIATE ----------
        target.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");
                
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });

        // ---------- MOVE BLOCK ASSIGNMENT OR POSITION BLOCK ------------
        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {

                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasString()) {

                    String content = db.getString();

                    if (dragType == BlockDragType.POSITION_BLOCK) {
                        handlePositionBlock(target, content);
                    } else {
                        handleMoveBlock(sourceRectangle, target, content);
                    }

                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });

        // ---------- MOVE BLOCK ASSIGNMENT --------------
        target.setOnDragDetected(new EventHandler <MouseEvent>() {
			public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                /* allow any transfer mode */
                Dragboard db = target.startDragAndDrop(TransferMode.ANY);
                dragType = BlockDragType.MOVE_BLOCK;
                
                // Set the source rectangle to this
                sourceRectangle = target;
                
                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();

                // Get oldGridHorizontalPosition and oldGridVerticalPosition from current rectangle
                int oldGridHorizontalPosition = (int) target.getX();
                int oldGridVerticalPosition   = (int) target.getY();

                // Add "[oldGridHorizontalPosition, oldGridVerticlePosition]" to clipboard
                content.putString(Arrays.toString(new int[]{oldGridHorizontalPosition, oldGridVerticalPosition}));
                db.setContent(content);
                event.consume();
            }
        });
    }

    /**
     * Helper method to handle block positioning
     * 
     * @param target - the target rectangle to place the block
     * @param content - the content from the clipboard
     */
    private void handlePositionBlock(Rectangle target, String content) {
        int blockID = Integer.parseInt(content);

        try {
            TOBlock block = Block223Controller.getBlockOfCurrentDesignableGame(blockID);

            int gridHorizontalPosition = (int) target.getX();
            int gridVerticalPosition = (int) target.getY();

            // Position block
            Block223Controller.positionBlock(blockID, levelID, gridHorizontalPosition, gridVerticalPosition);

            System.out.println("X: " + gridHorizontalPosition + " | Y: " + gridVerticalPosition);

            target.setFill(Color.rgb(block.getRed(), block.getGreen(), block.getBlue()));
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }
    }

    /**
     * Helper method to handle block movement
     * 
     * @param source - source rectangle
     * @param target - destination rectangle
     * @param content - the content from the clipboard
     */
    private void handleMoveBlock(Rectangle source, Rectangle target, String content) {
        
        // Arrays.toString() will convert an integer array to: "[a, b, c, d, ..., y, z]"
        // We thus want to remove the first and last characters (I.E '[' & ']') from the string
        // before parsing
        content = content.substring(1, content.length()-1); 
        
        // Parse content into array of two integers 
        int[] oldGridPositions = Arrays.stream(content.split(", ")).mapToInt(Integer::parseInt).toArray();

        // Get grid positions
        int oldGridHorizontalPosition = oldGridPositions[0];
        int oldGridVerticalPosition   = oldGridPositions[1];
        int newGridHorizontalPosition = (int) target.getX();
        int newGridVerticalPosition = (int) target.getY();

        try {
            // Move block assignment
            Block223Controller.moveBlock(
                levelID, 
                oldGridHorizontalPosition, 
                oldGridVerticalPosition,
                newGridHorizontalPosition,
                newGridVerticalPosition);

            
            // Set the colors for the rectangles
            Color color = (Color) source.getFill();
            target.setFill(color);
            source.setFill(Color.WHITE);
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }
    }

    /**
     * Helper method for showing alert
     */
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    /**
     * Represents a TOBlock entry in a ListView
     */
    static class BlockListCell extends ListCell<TOBlock> {
        
        @Override
        public void updateItem(TOBlock item, boolean empty) {

            super.updateItem(item, empty);

            if (item != null) {
                // Get block RGB components
                int red = item.getRed();
                int green = item.getGreen();
                int blue = item.getBlue();

                Rectangle rect = new Rectangle(50, 50);
                rect.setFill(Color.rgb(red, green, blue));

                Text text = new Text(Integer.toString(item.getPoints()));
                StackPane stack = new StackPane();
                stack.getChildren().addAll(rect, text);      
                
                setGraphic(stack);
            }
        }
    }

}

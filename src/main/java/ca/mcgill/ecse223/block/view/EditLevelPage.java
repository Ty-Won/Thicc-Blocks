package ca.mcgill.ecse223.block.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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


public class EditLevelPage {
	
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
	
	public EditLevelPage(Stage stage, int levelID) {
        this.stage = stage;
        this.levelID = levelID;
        
        // Get the current game's block
        try {
            this.blocks = FXCollections.observableList(Block223Controller.getBlocksOfCurrentDesignableGame());
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }
    }
    
    /**
     * Display the page
     */
	public void display() {

        // Create the main border pain.        
        this.root = new BorderPane();
        
        ListView<TOBlock> lview = createBlockSelectionUI();
        root.setRight(lview);

        GridPane gridPane = createBlockPlacementUI();
        root.setCenter(gridPane);


        Scene scene = new Scene(root, 490, 390);
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
        grid.setGridLinesVisible(true);

        int[] cap = Block223Controller.getMaxBlockCapacity();

        for (int x = 0; x < cap[0]; x++) {
            for (int y = 0; y < cap[1]; y++) {

                // Allocate each cell a white rectangle
                Rectangle rect = new Rectangle(20, 20, Color.WHITE);
                rect.setX(x);
                rect.setY(y);
                setupTargetDragAndDrop(rect);
                grid.add(rect, x, y);
            }
        }

        return grid;
    }

    private void setupTargetDragAndDrop(Rectangle target) {
        
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

        target.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    int blockID = Integer.parseInt(db.getString());
                    try {
                        TOBlock block = Block223Controller.getBlockOfCurrentDesignableGame(blockID);

                        int x = (int) target.getX();
                        int y = (int) target.getY();

                        // Position block
                        Block223Controller.positionBlock(blockID, levelID, x, y);

                        System.out.println("X: " + x + " | Y: " + y);

                        target.setFill(Color.rgb(block.getRed(), block.getGreen(), block.getBlue()));
                    } catch (InvalidInputException e) {
                        showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
                    }


                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
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

    /**
     * Represents a TOBlock entry in a ListView
     */
    static class BlockListCell extends ListCell<TOBlock> {
        
        @Override
        public void updateItem(TOBlock item, boolean empty) {

            super.updateItem(item, empty);

            Rectangle rect = new Rectangle(50, 50);
            if (item != null) {
                int red = item.getRed();
                int green = item.getGreen();
                int blue = item.getBlue();

                rect.setFill(Color.rgb(red, green, blue));
                setGraphic(rect);
            }
        }
    }

}

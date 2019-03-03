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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Group;
import javafx.scene.paint.Color;
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
        
        try {
            
            this.blocks = FXCollections.observableList(Block223Controller.getBlocksOfCurrentDesignableGame());
        } catch (InvalidInputException e) {
            showAlert(Alert.AlertType.ERROR, null, "Error", e.getMessage());
        }

        //System.out.println(blocks.size());
    }
	
	public void display() {

        // Create the main border pain.        
        this.root = new BorderPane();

        final Text source = new Text(50, 100, "DRAG ME");
        source.setScaleX(2.0);
        source.setScaleY(2.0);

        final Text target = new Text(250, 100, "DROP HERE");
        target.setScaleX(2.0);
        target.setScaleY(2.0);

        Image image = new Image("http://www.sohme.com/wp-content/uploads/2015/07/red-180x180.png", true);

        source.setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                
                /* allow any transfer mode */
                Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                
                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                content.putImage(image);
                db.setContent(content);
                
                event.consume();
            }
        });

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

        target.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    target.setFill(Color.GREEN);
                }
                
                event.consume();
            }
        });

        target.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                target.setFill(Color.BLACK);
                
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
                    target.setText(db.getString());
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                event.consume();
            }
        });

        source.setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                    source.setText("");
                }
                
                event.consume();
            }
        });


        Rectangle rect = new Rectangle(200, 200, Color.RED);
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(120, 120);
        s1.setContent(rect);
        
        ListView<TOBlock> lview = createBlockSelectionUI();
        root.setRight(lview);


        Scene scene = new Scene(root, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }
    
    private ListView<TOBlock> createBlockSelectionUI() {
        ListView<TOBlock> list = new ListView<>();
        ObservableList<String> data = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");

        list.setItems(blocks);

        list.setCellFactory(new Callback<ListView<TOBlock>, 
            ListCell<TOBlock>>() {
                @Override 
                public ListCell<TOBlock> call(ListView<TOBlock> list) {
                    return new BlockListCell();
                }
            }
        );

        return list;
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

        // ------ Add Column Constraints -------

        // columnOneConstraints will be applied to all the nodes placed in column one.
        ColumnConstraints columnOneConstraints = new ColumnConstraints(150, 150, Double.MAX_VALUE);
        columnOneConstraints.setHalignment(HPos.LEFT);

        // columnTwoConstraints will be applied to all the nodes placed in column two.
        ColumnConstraints columnTwoConstraints = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnTwoConstraints.setHalignment(HPos.CENTER);
        
        //Column Three
        ColumnConstraints columnThreeConstraints = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnThreeConstraints.setHalignment(HPos.CENTER);
        
        //Column Four
        ColumnConstraints columnFourConstraints = new ColumnConstraints(150,150, Double.MAX_VALUE);
        columnFourConstraints.setHalignment(HPos.CENTER);

        gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstraints, columnThreeConstraints, columnFourConstraints);
        return gridPane;
    }

    private void addUIComponents(HBox hbox) {
        Button b = new Button("add");
        b.setOnAction(ev -> hbox.getChildren().add(new Label("Test")));

        ScrollPane scrollPane = new ScrollPane(hbox);
        scrollPane.setFitToHeight(true);

        BorderPane root = new BorderPane(scrollPane);
        root.setPadding(new Insets(15));
        root.setTop(b);


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
     * 
     */
    static class BlockListCell extends ListCell<TOBlock> {
        
        /**
         * 
         */
        @Override
        public void updateItem(TOBlock item, boolean empty) {

            super.updateItem(item, empty);

            int red = item.getRed();
            int green = item.getGreen();
            int blue = item.getBlue();

            Rectangle rect = new Rectangle(100, 20);
            if (item != null) {
                rect.setFill(Color.rgb(red, green, blue));
                setGraphic(rect);
            }
            System.out.println("Here!");
        }
    }

}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import ca.mcgill.ecse223.block.model.*;
import ca.mcgill.ecse223.block.persistence.*;

public class Block223Application extends Application {

    @Override
    public void start(Stage stage) {
        Label l = new Label("Thicc Blocks");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {    	
    	Block223 block223 = Block223Persistence.load();
    	Block223Persistence.save(block223);
    	
        launch();
    }

}
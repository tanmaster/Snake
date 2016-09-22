/**
 * Created by Tan on 22.09.2016.
 */


import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake by Tan");
        button = new Button();
        button.setText("hisss");

        //using lambdas to handle events
        button.setOnAction(event -> System.out.println("lol nice try taylor"));


        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}

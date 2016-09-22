/**
 * Created by Tan on 22.09.2016.
 */


import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    Button button;
    String a;
    TextField textField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake by Tan");
        button = new Button();
        button.setText("hisss");


        textField = new TextField();
        textField.setVisible(true);
        textField.getText();
        textField.setOnAction(event -> {
            a = textField.getText();
                System.out.println(a);

        });

        AlertBox alertBox = new AlertBox();
        //using lambdas to handle events
        //button.setOnAction(event -> System.out.println("lol nice try taylor"));
        button.setOnAction(event ->  AlertBox.display("Credits", "Snake By Tan"));
        button.setMinSize(100,30);

        VBox layout = new VBox(5);
        layout.getChildren().addAll(textField, button);

        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}

/**
 * Created by Tan on 22.09.2016.
 */


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    Button creditsButton, fieldSize;
    int x, y;
    TextField xField, yField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Snake by Tan");
        creditsButton = new Button("Credits");


        xField = new TextField();
        xField.setVisible(true);
        xField.setMaxWidth(150);
        yField = new TextField();
        yField.setVisible(true);
        yField.setMaxWidth(150);

        fieldSize = new Button("Set Size");
        fieldSize.setMinSize(50, 50);
        fieldSize.setOnAction(event -> {
            if (isNumeric(xField.getText()) && isNumeric(yField.getText())) {
                x = Integer.valueOf(xField.getText());
                y = Integer.valueOf(yField.getText());
                System.out.println(x + " " + y);
            }else System.out.println("Please enter a numerical value in both thext fields");
        });

        AlertBox alertBox = new AlertBox();
        //using lambdas to handle events
        //creditsButton.setOnAction(event -> System.out.println("lol nice try taylor"));
        creditsButton.setOnAction(event -> AlertBox.display("Credits", "Snake By Tan\nThanks 4 playing"));
        creditsButton.setMinSize(100, 30);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(xField, yField, fieldSize, creditsButton);
        layout.setAlignment(Pos.TOP_CENTER);



        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}

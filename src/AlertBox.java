import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;

/**
 * Created by Tan on 22.09.2016.
 */
public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);
        Button closebutton = new Button();
        closebutton.setText("Close");
        closebutton.setOnAction(event -> window.close());

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, closebutton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();



    }
}

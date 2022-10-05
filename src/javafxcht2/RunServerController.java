package javafxcht2;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author kurd
 */
public class RunServerController implements Initializable {

    @FXML
    private Label server_titel;
    @FXML
    private ToggleButton buttone_start;

    /**
     * Initializes the controller class.
     */
    private boolean state;
    @FXML
    private HBox hboxshow;
    @FXML
    private TextField textfieldUsername;
    @FXML
    private TextField textFieldIPortNumber;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        hboxshow.setVisible(false);
        buttone_start.setOnAction((e) -> {
            boolean selected = buttone_start.isSelected();
            if (selected) {
                System.out.println("running");
                buttone_start.setText("Stop");
                server_titel.setText("Server running");
                hboxshow.setVisible(true);
                showServer();

            } else {
                System.out.println("stoped");
                buttone_start.setText("Start");
                server_titel.setText("Server Stoped");
                //  stopServer();
                hboxshow.setVisible(false);
            }
        });
    }

    public void showServer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("server.fxml"));
            Parent root = loader.load();
            ((ServerController) loader.getController())
                    .setServerInfo(new UserInfo(textfieldUsername.getText(), "", textFieldIPortNumber.getText()));

            stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            IconsSFX.setStageIcon(stage);
            stage.show();
         
        } catch (IOException ex) {

        }
    }

    public void stopServer() {
        stage.close();
    }

}

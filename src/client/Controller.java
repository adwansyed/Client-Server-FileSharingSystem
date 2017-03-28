package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class Controller {

    @FXML
    private Button downloadButton;

    @FXML
    private Button uploadButton;

    @FXML
    private MenuItem exitMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private ListView<String> clientList;

    @FXML
    private ListView<String> serverList;

    @FXML
    void download(ActionEvent event) {

    }

    @FXML
    void upload(ActionEvent event) {

    }

}


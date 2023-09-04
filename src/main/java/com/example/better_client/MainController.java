package com.example.better_client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {
    @FXML
    private MenuItem help;

    @FXML
    private MenuItem close;

    @FXML
    void onCloseClick(ActionEvent event) {
        System.out.println(" this is close ");
        Platform.exit();
    }

    @FXML
    void onHelpClick(ActionEvent event) {

    }

}

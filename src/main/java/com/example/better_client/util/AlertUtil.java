package com.example.better_client.util;

import javafx.scene.control.Alert;

public class AlertUtil {


    public static Alert showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("提示");
        alert.setHeaderText("");
        // 将TextFlow或Label设置为Alert的内容
        alert.setContentText(content);
        // 或者使用Label:
        // alert.getDialogPane().setContent(label);
        alert.showAndWait();
        return alert;
    }
}

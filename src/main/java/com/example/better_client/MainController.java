package com.example.better_client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class MainController {
    @FXML
    private MenuItem help;

    @FXML
    private MenuItem close;

    @FXML
    private ListView<String> pdfFileList;
    @FXML
    private Button selectFile;
    @FXML
    void onCloseClick(ActionEvent event) {
        System.out.println(" this is close ");
        Platform.exit();
    }

    @FXML
    void onHelpClick(ActionEvent event) {
        String message = "1:合成功能实现了PDF合成，校验等功能\n" +
                "2:背图实现台历等产品自动插入背面图片功能\n" +
                "3:开发者--iskylei";

        // 创建一个TextFlow或Label来包装文本内容
        TextFlow textFlow = new TextFlow();
        // 或者使用Label:
        // Label label = new Label();

        // 设置TextFlow的宽度限制，以便自动换行
        textFlow.setMaxWidth(300);

        // 在TextFlow或Label中添加包含换行的文本
        textFlow.getChildren().add(new Label(message));
        // 或者使用Label:
        // label.setText(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于我们");
        alert.setHeaderText("");
        // 将TextFlow或Label设置为Alert的内容
        alert.getDialogPane().setContent(textFlow);
        // 或者使用Label:
        // alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    @FXML
    void onSelectFileClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("所有文件", "*.*")
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("文本文档", "*.txt")
        );

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        ObservableList<String> items = FXCollections.observableArrayList();
        if (selectedFiles != null) {
            // 处理选中的文件
            for (File file : selectedFiles) {
                System.out.println("已选中文件: " + file.getAbsolutePath());
                items.add(file.getName());
            }
        }
        pdfFileList.setItems(items);
    }

}

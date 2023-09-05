package com.example.better_client;

import com.example.better_client.util.AlertUtil;
import com.example.better_client.util.PdfMerger;
import com.example.better_client.util.PdfUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainController {
    @FXML
    private ListView<String> byPdf;

    @FXML
    private Button clearBtn;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem help;

    @FXML
    private Button mergeBtn;

    @FXML
    private ListView<String> pdfFileList;

    @FXML
    private ListView<String> pdfList;

    @FXML
    private Button pdfSelect;

    @FXML
    private Button selectBYBtn;

    @FXML
    private Button selectFile;

    @FXML
    private Button clearByBtn;

    @FXML
    private Button clearPdfBtn;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button selectFolder;

    public void startLoading() {
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
    }

    public void stopLoading() {
        progressBar.setProgress(0);
    }

    /**
     * 退出程序
     *
     * @param event
     */
    @FXML
    void onCloseClick(ActionEvent event) {
        System.out.println(" this is close ");
        Platform.exit();
    }

    /**
     * 关于我们
     *
     * @param event
     */
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

    /**
     * 文件选择
     *
     * @param event
     */
    @FXML
    void onSelectFileClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.setInitialDirectory(new File("D:\\iskylei\\pdf"));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF文件", "*.pdf")
        );
        ObservableList<String> items = pdfFileList.getItems();
        if (items == null) {
            items = FXCollections.observableArrayList();
        }
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
//        ObservableList<String> items = FXCollections.observableArrayList();
        if (selectedFiles != null) {
            // 处理选中的文件
            for (File file : selectedFiles) {
                System.out.println("已选中文件: " + file.getAbsolutePath());
                items.add(file.getAbsolutePath());
            }
        }
        pdfFileList.setItems(items);
    }

    /**
     * 清空文件列表
     *
     * @param event
     */
    @FXML
    void onClearClick(ActionEvent event) {
        pdfFileList.setItems(null);
    }

    /**
     * 开始合成
     *
     * @param event
     */
    @FXML
    void onMergeClick(ActionEvent event) {
        progressBar.setVisible(true);
        startLoading();
        if (pdfFileList.getItems().isEmpty()) {
            stopLoading();
            progressBar.setVisible(false);
            AlertUtil.showWarningAlert("您还没选择文件，请先选择文件！");
            return;
        }
        // 验证PDF尺寸是否都一致
        ObservableList<String> observableList = pdfFileList.getItems();
        Set set = new HashSet();
        for (String str : observableList) {
            System.out.println(str);
            Map<String, Float> m = PdfUtil.getPdfWH(str);
            set.add(m);
        }

        if (set.size() != 1) {
            stopLoading();
            progressBar.setVisible(false);
            AlertUtil.showWarningAlert("您选择的文件尺寸不一致！！！");
            return;
        }
        String files[] = new String[observableList.size()];
        observableList.toArray(files);
        PdfMerger.mergePdf(files, "D:\\iskylei\\pdf\\merged.pdf");
        AlertUtil.showSuccessAlert("合成成功！");
        stopLoading();
        progressBar.setVisible(false);
    }


    @FXML
    void onPdfSelect(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("所有文件", "*.pdf")
        );
        ObservableList<String> items = pdfList.getItems();
        if (items == null) {
            items = FXCollections.observableArrayList();
        }
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
//        ObservableList<String> items = FXCollections.observableArrayList();
        if (selectedFiles != null) {
            // 处理选中的文件
            for (File file : selectedFiles) {
                System.out.println("已选中文件: " + file.getAbsolutePath());
                items.add(file.getName());
            }
        }
        pdfList.setItems(items);
    }


    @FXML
    void onselectByClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        Stage stage = (Stage) selectBYBtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        ObservableList<String> items = FXCollections.observableArrayList();
        if (selectedFile != null) {
            // 处理选择的文件
            System.out.println("选择的文件路径: " + selectedFile.getAbsolutePath());
            items.add(selectedFile.getName());
        }
        byPdf.setItems(items);
    }

    @FXML
    void onClearByClick(ActionEvent event) {
        byPdf.setItems(null);
    }


    @FXML
    void onClearPdfClick(ActionEvent event) {
        pdfList.setItems(null);
    }


    @FXML
    void onSelectFolderClick(ActionEvent event){

    }
}

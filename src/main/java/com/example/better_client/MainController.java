package com.example.better_client;

import com.example.better_client.util.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

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
    private Button selectFolderBtn;

    @FXML
    private Label selectFolder;

    @FXML
    private Button byMergeBtn;

    @FXML
    private Button selectByFolderBtn;

    @FXML
    private Label selectByFolder;

    @FXML
    private Label defFolder;

    @FXML
    private MenuItem def_directory;

    @FXML
    private Button pdfFolderBtn;

    @FXML
    private Button selCreateFolder;

    @FXML
    private Label selPdfFolderLabel;

    @FXML
    private Label createFolderLabel;


    @FXML
    private ListView<String> zmPdfList;

    @FXML
    private ListView<String> bmPdfList;


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


    @FXML
    void onDefClick(ActionEvent event) {
        Stage stage = new Stage();
        // 创建新的Stage对象
        // 设置窗口标题
        stage.setTitle("新窗口");
        stage.setWidth(300);
        stage.setHeight(200);

        // 创建文本标签
        TextField field = new TextField(defFolder.getText());

        // 创建按钮
        Button button = new Button("保存");

        // 创建垂直布局容器
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(field, button);

        // 创建场景并将容器添加到场景中
        Scene scene = new Scene(vbox, 300, 200);

        // 将场景设置到舞台
        stage.setScene(scene);
        // 显示窗口
        stage.show();

        button.setOnAction(e -> {
            String path = field.getText();
            File file = new File(path);
            if (!file.exists()) {
                AlertUtil.showWarningAlert("保存失败,路径不存在！");
                return;
            }
            defFolder.setText(field.getText());
            AlertUtil.showSuccessAlert("保存成功!");
        });
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
        if (!StringUtils.isEmpty(defFolder.getText())) {
            fileChooser.setInitialDirectory(new File(defFolder.getText()));
        }
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
                items.add(file.getAbsolutePath());
            }
        }
        pdfFileList.setItems(items);
    }

    /**
     * 选择文件夹
     *
     * @param event
     */
    @FXML
    void onSelectFolderClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selectFolderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            // 可以在这里处理选择的文件夹
            selectFolder.setText(selectedDirectory.getAbsolutePath());
        }
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
        /**
         * 验证
         */
        String validateStr = MyUtil.volidate(observableList);
        if (!StringUtils.isEmpty(validateStr)) {
            stopLoading();
            progressBar.setVisible(false);
            AlertUtil.showWarningAlert("选择的PDF文件尺寸不一致!");
            AlertUtil.showAreaAlert(validateStr);
            return;
        }
//        Set set = new HashSet();
//        for (String str : observableList) {
//            Map<String, Float> m = PdfUtil.getPdfWH(str);
//            set.add(m);
//        }
//        // 如果尺寸有多个，不能合成
//        if (set.size() != 1) {
//            stopLoading();
//            progressBar.setVisible(false);
//            AlertUtil.showWarningAlert("您选择的文件尺寸不一致！！！");
//            return;
//        }
        // list转数组
        String files[] = new String[observableList.size()];
        observableList.toArray(files);
        // 根据份数重置数组
        String newFiles[] = MyUtil.resetArr(files);
        // 文件路径
        String filePath = selectFolder.getText();
        if (StringUtils.isEmpty(filePath)) {
            stopLoading();
            progressBar.setVisible(false);
            AlertUtil.showWarningAlert("请先选择文件目录！");
            return;
        }
        // 文件名
        String resultPdfName = System.currentTimeMillis() + ".pdf";
        String resultPdf = filePath + File.separator + resultPdfName;
        // 合并
        PdfMerger.mergePdf(newFiles, resultPdf);
        AlertUtil.showSuccessAlert("合成成功！合成文件：" + resultPdf);
        stopLoading();
        progressBar.setVisible(false);
    }

    /**
     * 选择要插入背图的PDF
     *
     * @param event
     */
    @FXML
    void onPdfSelect(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择pdf文件");
        if (!StringUtils.isEmpty(defFolder.getText())) {
            fileChooser.setInitialDirectory(new File(defFolder.getText()));
        }
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF文件", "*.pdf")
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
                items.add(file.getAbsolutePath());
            }
        }
        pdfList.setItems(items);
    }

    /**
     * 选择背面PDF
     *
     * @param event
     */
    @FXML
    void onselectByClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择背面PDF文件");
//        fileChooser.setInitialDirectory(new File("D:\\iskylei\\pdf"));
        if (!StringUtils.isEmpty(defFolder.getText())) {
            fileChooser.setInitialDirectory(new File(defFolder.getText()));
        }
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF文件", "*.pdf")
        );
        Stage stage = (Stage) selectBYBtn.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        ObservableList<String> items = FXCollections.observableArrayList();
        if (selectedFile != null) {
            // 处理选择的文件
            items.add(selectedFile.getAbsolutePath());
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
    void onSelectByFolderClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selectByFolderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            // 可以在这里处理选择的文件夹
            selectByFolder.setText(selectedDirectory.getAbsolutePath());
        }
    }

    /**
     * PDF出入背面图
     *
     * @param event
     */
    @FXML
    void byMergeClick(ActionEvent event) {
        if (pdfList.getItems().isEmpty()) {
            AlertUtil.showWarningAlert("请先选择PDF文件!!!");
            return;
        }

        if (byPdf.getItems().isEmpty()) {
            AlertUtil.showWarningAlert("请先选择背面PDF文件!!!");
            return;
        }
        ObservableList<String> observableList = pdfList.getItems();// 原始PDF
        /**
         * 验证
         */
        String validateStr = MyUtil.volidate(observableList);
        if (!StringUtils.isEmpty(validateStr)) {
            AlertUtil.showWarningAlert("选择的PDF文件尺寸不一致!");
            AlertUtil.showAreaAlert(validateStr);
            return;
        }


        Set set = new HashSet();
        for (String str : observableList) {
            Map<String, Float> m = PdfUtil.getPdfWH(str);
            set.add(m);
        }
//        if (set.size() != 1) {
//            AlertUtil.showWarningAlert("选择的PDF文件尺寸不一致!");
//            return;
//        }

        for (String str : byPdf.getItems()) {
            Map<String, Float> m = PdfUtil.getPdfWH(str);
            set.add(m);
        }
        if (set.size() != 1) {
            AlertUtil.showWarningAlert("选择的背面PDF文件尺寸不一致!");
            return;
        }
        // list转数组
        String files[] = new String[observableList.size()];
        observableList.toArray(files);

        // 根据份数重置数组
        String newFiles[] = MyUtil.resetArr(files);

        // 插入的PDF文件路径
        String insertFile = byPdf.getItems().get(0);

        String filePath = selectByFolder.getText();
        if (StringUtils.isEmpty(filePath)) {
            AlertUtil.showWarningAlert("请先选择文件目录！");
            return;
        }
        String resultFileName = "by" + System.currentTimeMillis() + ".pdf";
        String outputFile = filePath + File.separator + resultFileName;
        PdfMerger.insertPdf(newFiles, insertFile, outputFile);

        AlertUtil.showSuccessAlert("操作成功,文件名：" + outputFile);
    }

    Map<String, String> m;

    @FXML
    void pdfFolderBtnClick(ActionEvent event) {
        m = null;
        m = new HashMap<>();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) pdfFolderBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            // 可以在这里处理选择的文件夹
            String folderPath = selectedDirectory.getAbsolutePath();
            selPdfFolderLabel.setText(folderPath);
            // 选择正面背面PDF

            List<String> list = FileUtil.readPDFFiles(folderPath);

            // 背面PDF
            List<String> bmList = FileUtil.readBmPDFFiles(folderPath);
            ObservableList<String> observableBmList = FXCollections.observableArrayList(bmList);
            bmPdfList.setItems(observableBmList);
            //
            List<String> zmList = new ArrayList<>();
            for (String str : bmList) {
                String[] fileArr = str.split("--");
                String firstStr = null;
                String endStr = null;
                if (fileArr.length > 1) {
                    firstStr = fileArr[0];
                    endStr = fileArr[fileArr.length - 2];
                }
                for (String zmStr : list) {
                    if (zmStr.indexOf(firstStr) != -1 && zmStr.indexOf(endStr) != -1) {
                        zmList.add(zmStr);
                        m.put(zmStr, str);
                    }
                }
            }
            ObservableList<String> observableList = FXCollections.observableArrayList(zmList);
            zmPdfList.setItems(observableList);
        }
    }

    @FXML
    void selCreateFolderBtnClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selCreateFolder.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            // 可以在这里处理选择的文件夹
            createFolderLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    void byHcBtnClick(ActionEvent event) {
        String createFolderText = createFolderLabel.getText();
        if (StringUtils.isEmpty(createFolderText)) {
            AlertUtil.showWarningAlert("请选择生成PDF的目录");
            return;
        }

        String selText = selPdfFolderLabel.getText();
        if (StringUtils.isEmpty(selText)) {
            AlertUtil.showWarningAlert("请选择PDF文件夹目录");
            return;
        }

        if (zmPdfList.getItems().size() == 0) {
            AlertUtil.showWarningAlert("文件夹没有PDF文件");
            return;
        }

        if (zmPdfList.getItems().size() != bmPdfList.getItems().size()) {
            AlertUtil.showWarningAlert("正面PDF文件数量和背面数量不一致");
            return;
        }

        for (Map.Entry<String, String> entry : m.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
            System.out.println("selText:" + selText);
            int count = MyUtil.getPdfCount(key);
            List<String> l = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                l.add(selText + File.separator + key);
            }

            String files[] = new String[l.size()];
            l.toArray(files);

            // 根据份数重置数组
            String newFiles[] = MyUtil.resetArr(files);

            // 插入的PDF文件路径
            String insertFile = selText + File.separator + value;

//            String resultFileName = "byNew" + System.currentTimeMillis() + ".pdf";
            String resultFileName = key;
            String outputFile = createFolderText + File.separator + resultFileName;
            PdfMerger.insertPdf(newFiles, insertFile, outputFile);
//            AlertUtil.showSuccessAlert("操作成功,文件名：" + outputFile);
        }
    }


    @FXML
    private Button pdfFolderXlsBtn;

    @FXML
    private Label selPdfFolderXlsLabel;

    @FXML
    private Button selCreateXlsFolder;


    @FXML
    private Label createFolderXlsLabel;

    @FXML
    private Button byXlsBtn;

    @FXML
    void pdfFolderXlsBtnClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) pdfFolderXlsBtn.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            // 可以在这里处理选择的文件夹
            selPdfFolderXlsLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    void selCreateXlsFolderClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择文件夹");
        Stage stage = (Stage) selCreateXlsFolder.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            // 可以在这里处理选择的文件夹
            createFolderXlsLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }


    @FXML
    void byXlsBtnClick(ActionEvent event) {

    }

}

package com.example.better_client.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述
 *
 * @author: sunlei
 * @date: 2023年10月05日 14:11
 */
public class FileUtil {


    public static List<String> readPDFFiles(String folderPath) {
        List<String> pdfFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".pdf")) {
                        String pdfFileName = fileName.toUpperCase();
                        int count = MyUtil.getPdfCount(pdfFileName);
                        if (count > 0) {
                            pdfFiles.add(fileName);
                        }
                    }
                }
            }
        }
        return pdfFiles;
    }


    public static List<String> readBmPDFFiles(String folderPath) {
        List<String> pdfFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".pdf")) {
                        String pdfFileName = fileName.toUpperCase();
                        String[] strArr = pdfFileName.split("--");
                        int count = MyUtil.getPdfCount(pdfFileName);
                        if (count == 0) {
                            pdfFiles.add(fileName);
                        }
                    }
                }
            }
        }
        return pdfFiles;
    }
}

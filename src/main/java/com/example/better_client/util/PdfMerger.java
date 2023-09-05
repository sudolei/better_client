package com.example.better_client.util;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileOutputStream;
import java.io.IOException;

public class PdfMerger {
    public static void mergePdf(String[] files, String result) {
        Document document = new Document();

        try {
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(result));
            document.open();

            for (String file : files) {
                PdfReader reader = new PdfReader(file);
                int numPages = reader.getNumberOfPages();

                for (int i = 1; i <= numPages; i++) {
                    copy.addPage(copy.getImportedPage(reader, i));
                }

                reader.close();
            }

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] files = {"D:\\iskylei\\pdf\\SMXPBMLB--ZG--K--1fen--tb94139432--2.pdf", "D:\\iskylei\\pdf\\SMXPBMLB--ZG--K--1fen--tb6303371558--2.pdf"};
        mergePdf(files, "D:\\iskylei\\pdf\\merged.pdf");
    }
}
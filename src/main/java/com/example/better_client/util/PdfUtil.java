package com.example.better_client.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;

import java.util.HashMap;
import java.util.Map;

public class PdfUtil {

    /**
     * 获取PDF宽高
     * @param pdfPath
     * @return
     */
    public static Map<String, Float> getPdfWH(String pdfPath) {
        // 读取 PDF 文件
        PdfReader reader = null;
        Map<String, Float> result = new HashMap<>();
        try {
            // 读取 PDF 文件
            reader = new PdfReader(pdfPath);
            // 获取第一页的文档对象
            Document document = new Document(reader.getPageSize(1));
            // 获取页面的长宽
            Rectangle pageSize = document.getPageSize();
            float width = pageSize.getWidth();
            float height = pageSize.getHeight();
            // 打印长宽
            System.out.println("PDF 文件的宽度：" + width);
            System.out.println("PDF 文件的高度：" + height);
            result.put("width", width);
            result.put("height", height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭读取器
            reader.close();
        }
        return result;
    }

    /**
     * 获取宽高
     * @param pdfPath
     * @return
     */
    public static String getWhMapData(String pdfPath) {
        // 读取 PDF 文件
        PdfReader reader = null;
        String result = null;
        try {
            // 读取 PDF 文件
            reader = new PdfReader(pdfPath);
            // 获取第一页的文档对象
            Document document = new Document(reader.getPageSize(1));
            // 获取页面的长宽
            Rectangle pageSize = document.getPageSize();
            float width = pageSize.getWidth();
            float height = pageSize.getHeight();
            result = width + "|" + height;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭读取器
            reader.close();
        }
        return result;
    }
}

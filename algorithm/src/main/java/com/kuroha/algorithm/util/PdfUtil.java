package com.kuroha.algorithm.util;

import com.itextpdf.text.pdf.BaseFont;
import lombok.extern.slf4j.Slf4j;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

/**
 * @author kuroha
 */
@Slf4j
public class PdfUtil {

    private static final String pdfDestPath = "/tmp/pdf/";
    private static final String fontDir = "/tmp/msyh.ttf";

    public static void getPdf3(String content,String dest) throws Exception {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = (ITextFontResolver) renderer.getSharedContext().getFontResolver();
            fontResolver.addFont(fontDir, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(content);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();
            byte[] buff = os.toByteArray();
            //保存到磁盘上
            FileUtil.byte2File(buff,dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPdf(String templateName, String fileName, Map<String, Object> map) throws Exception {
        String htmlPath = pdfDestPath + fileName + ".html";
        String pdfPath = pdfDestPath + fileName + ".pdf";
        htmlPath = FreeMarkerUtil.getInstance().createHtml(templateName, htmlPath, map);
        BufferedReader br = new BufferedReader(new FileReader(htmlPath));
        StringBuilder builder = new StringBuilder();
        while (br.ready()) {
            builder.append(br.readLine());
        }
        getPdf3(builder.toString(),pdfPath);
        if (new File(htmlPath).delete()){
            log.debug("删除成功");
        } else {
            log.debug("删除失败");
        }
        return pdfPath;
    }

}

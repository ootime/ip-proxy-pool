package io.github.ootime.common.pool.resource;

import junit.framework.TestCase;
import net.coobird.thumbnailator.Thumbnails;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MimvpResourceTest extends TestCase {

    public void testLoadResource() throws IOException, TesseractException {
//        MimvpResource mimvpResource = new MimvpResource(1,10);
//        System.out.println(mimvpResource.getResource());
//        System.out.println(mimvpResource.getResource());
//        System.out.println(mimvpResource.getResource());
//        System.out.println(mimvpResource.getResource());
//        System.out.println(mimvpResource.getResource());
//        ITesseract ins = new Tesseract();
//        ins.setDatapath("tessdata");
//        ins.setLanguage("chi_sim");
////        ins.setTessVariable("user_defined_dpi", "20");
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        Thumbnails.of(new URL("https://proxy.mimvp.com/common/ygrandimg?id=1&port=MmjiVmtvapW12cDExMAO0OO0O"))
//                .size(70,100)
//                .toOutputStream(byteArrayOutputStream);
//        String fff = ins.doOCR(ImageIO.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
//        System.out.println(fff);
//        Thumbnails.of(new File("C:\\Users\\PT\\Downloads\\ygrandimg.png")).size(300,200).toFile("C:\\Users\\PT\\Downloads\\ygrandimg_da.png");
    }
}
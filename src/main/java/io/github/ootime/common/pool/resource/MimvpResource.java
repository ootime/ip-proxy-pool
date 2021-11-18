package io.github.ootime.common.pool.resource;

import io.github.ootime.common.pool.IpProxy;
import net.coobird.thumbnailator.Thumbnails;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MimvpResource extends ProxyResource{


   private static ITesseract ins = new Tesseract();

   static {
       ins.setDatapath("tessdata");
       ins.setLanguage("chi_sim");
       ins.setTessVariable("user_defined_dpi", "70");
   }


    public long lastTime;
    private int  initSize;

    public MimvpResource( ){
    }


    @Override
    public Set<IpProxy> loadResource(){
        Set<IpProxy> proxies = new HashSet<>();
        try {
            Connection.Response response = Jsoup.connect("https://proxy.mimvp.com/freeopen?proxy=in_hp&sort=&page=1")
                    .ignoreContentType(true)
                    .timeout(3000)
                    .method(Connection.Method.GET).execute();
            Document document = response.parse();
            Element list = document.getElementById("mimvp-body");

            Elements trs = list.select("table tbody tr");
            Iterator<Element> trIterator =  trs.iterator();
            while (trIterator.hasNext()){
                Element tr = trIterator.next();
                Elements td = tr.select("td");
                String ip = td.get(1).text();
                String src = td.get(2).getElementsByTag("img").get(0).attr("src");
                String port =  getPort("https://proxy.mimvp.com/"+src);
                if(StringUtils.isNotBlank(port)){
                    IpProxy ipProxy = new IpProxy();
                    ipProxy.setIp(ip);
                    ipProxy.setOrigin("proxy.mimvp.com");
                    ipProxy.setPort(port.trim());
                    ipProxy.setType(IpProxy.IpType.ip_v4);
                    ipProxy.setHighConcealment(true);
                    proxies.add(ipProxy);
                }
            }
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
        this.lastTime = System.currentTimeMillis();
        return proxies;
    }


    public String getPort(String url) throws IOException, TesseractException {
//        ins.setTessVariable("user_defined_dpi", "20");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Thumbnails.of(new URL(url))
                .size(70,100)
                .toOutputStream(byteArrayOutputStream);
        String port = ins.doOCR(ImageIO.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
        byteArrayOutputStream.close();
        return  port;
    }
}

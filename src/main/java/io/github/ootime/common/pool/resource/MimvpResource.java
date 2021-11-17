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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MimvpResource extends ProxyResource{

    private long turnPageTime;


    private AtomicInteger atomicPage;

   private static ITesseract ins = new Tesseract();

   static {
       ins.setDatapath("tessdata");
       ins.setLanguage("chi_sim");
       ins.setTessVariable("user_defined_dpi", "70");
   }


    public long lastTime;
    private int  initSize;

    public MimvpResource( int page,int initSize){
        this.atomicPage = new AtomicInteger(page);
        TimeUnit unit = TimeUnit.SECONDS;
        this.turnPageTime = unit.toMillis(500);
        this.initSize = initSize;
    }

    @Override
    public List<IpProxy> getResource() {
        List<IpProxy> list =  super.getResource();

        while (list.size()<initSize){
            List<IpProxy> ips = super.getResource();
            if(ips.size()==0){
                break;
            }
            list.addAll(ips);
        }
        return list;
    }

    @Override
    public List<IpProxy> loadResource(){
        List<IpProxy> proxies = new ArrayList<>();
        int page = atomicPage.get();
        if(lastTime > 0){
            long end = System.currentTimeMillis()-lastTime;
            if(turnPageTime>end){
                page = atomicPage.incrementAndGet();
            }else{
                atomicPage.set(1);
                page = atomicPage.get();
            }
        }
        System.out.println("当前页面:"+page);
        try {
            Connection.Response response = Jsoup.connect("https://proxy.mimvp.com/freeopen?proxy=in_hp&sort=&page="+page)
                    .ignoreContentType(true)
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

//package io.github.ootime.common.pool.resource;
//
//import io.github.ootime.common.pool.IpProxy;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//@Deprecated
//public class Ip3366Resource extends ProxyResource{
//
//
//    private long turnPageTime;
//
//    private int type;
//
//    private AtomicInteger atomicPage;  //线程不安全
//
//    private int initSize;
//
//    /**
//     * 在指定秒数内重复访问会自动翻页，第二次调用超出指定时间会默认为第一页
//     * @param type
//     * @param page
//     */
//    public Ip3366Resource(int type, int page,int initSize){
//        this.type = type;
//        this.atomicPage = new AtomicInteger(page);
//        TimeUnit unit = TimeUnit.SECONDS;
//        this.turnPageTime = unit.toMillis(50);
//        this.initSize = initSize;
//    }
//
//    @Override
//    public List<IpProxy> getResource() {
//        List<IpProxy> list = super.getResource();
//        while (list.size()<initSize){
//            List<IpProxy> ips = super.getResource();
//            if(ips.size()==0){
//                break;
//            }
//            list.addAll(ips);
//        }
//        return list;
//    }
//
//    public long lastTime;
//    @Override
//    public List<IpProxy> loadResource(){
//        List<IpProxy> proxies = new ArrayList<>();
//        int page = atomicPage.get();
//        if(lastTime > 0){
//            long end = System.currentTimeMillis()-lastTime;
//            if(turnPageTime>end){
//                page = atomicPage.incrementAndGet();
//            }else{
//                atomicPage.set(1);
//                page = atomicPage.get();
//            }
//        }
//        System.out.println("当前页面:"+page);
//        try {
//            Connection.Response response = Jsoup.connect("http://www.ip3366.net/?stype="+type+"&page="+page)
//                    .ignoreContentType(true)
//                    .method(Connection.Method.GET).execute();
//            Document document = response.parse();
//            Element list = document.getElementById("list");
//
//            Elements trs = list.select("table tbody tr");
//            Iterator<Element> trIterator =  trs.iterator();
//            while (trIterator.hasNext()){
//                Element tr = trIterator.next();
//                Elements td = tr.select("td");
//                String ip = td.get(0).text();
//                String port = td.get(1).text();
//                IpProxy ipProxy = new IpProxy();
//                ipProxy.setIp(ip);
//                ipProxy.setOrigin("ip3366.net");
//                ipProxy.setPort(port);
//                ipProxy.setType(IpProxy.IpType.ip_v4);
//                ipProxy.setHighConcealment(true);
//                proxies.add(ipProxy);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        this.lastTime = System.currentTimeMillis();
//        return proxies;
//    }
//}

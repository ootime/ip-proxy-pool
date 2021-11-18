package io.github.ootime.common.pool.resource;

import io.github.ootime.common.pool.IpProxy;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Ip3366ResourceTest extends ProxyResource {

    @Override
    public Set<IpProxy> loadResource() {
        Set<IpProxy> proxies = new HashSet<>();
        try {
            Connection.Response response = Jsoup.connect("http://www.ip3366.net/?stype=1&page=1")
                    .ignoreContentType(true)
                    .timeout(3000)
                    .method(Connection.Method.GET).execute();
            Document document = response.parse();
            Element list = document.getElementById("list");

            Elements trs = list.select("table tbody tr");
            Iterator<Element> trIterator =  trs.iterator();
            while (trIterator.hasNext()){
                Element tr = trIterator.next();
                Elements td = tr.select("td");
                String ip = td.get(0).text();
                String port = td.get(1).text();
                IpProxy ipProxy = new IpProxy();
                ipProxy.setIp(ip);
                ipProxy.setOrigin("ip3366.net");
                ipProxy.setPort(port);
                ipProxy.setType(IpProxy.IpType.ip_v4);
                ipProxy.setHighConcealment(true);
                proxies.add(ipProxy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxies;
    }
}

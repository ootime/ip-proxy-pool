package io.github.ootime.common.pool.resource;

import io.github.ootime.common.pool.IpProxy;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SeofangfaResource extends ProxyResource{

    static SSLSocketFactory sslSocketFactory;
    static {
        try{
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            sslSocketFactory= context.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }
    @Override
    public Set<IpProxy> loadResource() {
        Set<IpProxy> proxies = new HashSet<>();
        try {
            Connection.Response response = Jsoup.connect("https://proxy.seofangfa.com/").ignoreHttpErrors(true)
                    .sslSocketFactory(sslSocketFactory)
                    .ignoreContentType(true)
                    .timeout(3000)
                    .method(Connection.Method.GET).execute();
            Document document = response.parse();
            Elements trs = document.select("table tbody tr");
            Iterator<Element> trIterator =  trs.iterator();
            int i = 0;
            while (trIterator.hasNext()&&i!=5){
                Element tr = trIterator.next();
                Elements td = tr.select("td");
                String ip = td.get(0).text();
                String port = td.get(1).text();
                IpProxy ipProxy = new IpProxy();
                ipProxy.setIp(ip);
                ipProxy.setOrigin("proxy.seofangfa.com");
                ipProxy.setPort(port);
                ipProxy.setType(IpProxy.IpType.ip_v4);
                ipProxy.setHighConcealment(true);
                proxies.add(ipProxy);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxies;
    }
}

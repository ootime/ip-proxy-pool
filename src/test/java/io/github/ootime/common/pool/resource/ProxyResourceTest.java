package io.github.ootime.common.pool.resource;


import io.github.ootime.common.pool.IpProxy;
import io.github.ootime.common.pool.util.NetStateUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProxyResourceTest {

   @Test
   public  void generateResource() throws InterruptedException, IOException {
        Ip3366Resource resource = new Ip3366Resource(1,1,5);//50秒内重复请求默认翻页
        List<IpProxy>  proxies = resource.getResource();
        System.out.println(Arrays.toString(proxies.toArray()));
   }
}
package io.github.ootime.common.pool;

import io.github.ootime.common.pool.resource.Ip3366ResourceTest;
import io.github.ootime.common.pool.resource.IpResourceData;
import junit.framework.TestCase;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.*;

public class IpProxyPoolTest extends TestCase {

    public void testPool() throws Exception {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
//        config.setMaxTotal(10);
//        config.setTestWhileIdle(true);
//        config.setTestOnBorrow(true);
        IpProxyPool ipProxyPool = new IpProxyPool(config);
//        ipProxyPool.addObject();
//        ipProxyPool.borrowObject();
//        for(int i=0;i<7;i++){
//            System.out.println(ipProxyPool.borrowObject());
//        }
//        Collection a = new LinkedHashSet();
//        CollectionBlockingQueue<Integer> v = CollectionBlockingQueue.create(10);
//        System.out.println(v);
//        Collections.synchronizedList(new ArrayList<>());
//                synchronizedList

//       HashSet<Integer> hashSet = new LinkedHashSet();
//        hashSet.add(1);
//        hashSet.add(3);
//        hashSet.add(2);
//        hashSet.add(8);
//        hashSet.add(4);
//        hashSet.add(9);
//        hashSet.add(6);
    }

    public void testGetRes(){
        Ip3366ResourceTest ip3366ResourceTest = new Ip3366ResourceTest();
        Set<IpProxy> ipProxies = ip3366ResourceTest.getResource();
        System.out.println(Arrays.toString(ipProxies.toArray()));
    }
    public void testpoll() throws Exception {
        IpResourceData ipResourceData = new IpResourceData();
        Thread.sleep(10000000);
    }

}
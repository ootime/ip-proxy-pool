package io.github.ootime.common.pool;

import junit.framework.TestCase;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class IpProxyPoolTest extends TestCase {

    public void testPool() throws Exception {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(10);
        config.setTestWhileIdle(true);
        config.setTestOnBorrow(true);
        IpProxyPool ipProxyPool = new IpProxyPool(config);
        ipProxyPool.borrowObject();
        for(int i=0;i<7;i++){
            System.out.println(ipProxyPool.borrowObject());
        }

    }

}
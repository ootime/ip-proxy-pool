package io.github.ootime.common.pool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class IpProxyPool extends GenericObjectPool<IpProxy> {

    public IpProxyPool(GenericObjectPoolConfig<IpProxy> config){
        this(new IpProxyFactory(),config);
    }


    public IpProxyPool(PooledObjectFactory<IpProxy> factory) {
        super(factory);
    }

    public IpProxyPool(PooledObjectFactory<IpProxy> factory, GenericObjectPoolConfig<IpProxy> config) {
        super(factory, config);
    }

    public IpProxyPool(PooledObjectFactory<IpProxy> factory, GenericObjectPoolConfig<IpProxy> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

    public IpProxy getIp(boolean holdOnIP) throws Exception {
        IpProxy ipProxy = this.borrowObject();
        if(holdOnIP){
            this.returnObject(ipProxy);
        }
        return ipProxy;
    }


}

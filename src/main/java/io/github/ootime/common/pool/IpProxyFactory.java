package io.github.ootime.common.pool;

import io.github.ootime.common.pool.resource.IpResourceData;
import io.github.ootime.common.pool.util.NetStateUtil;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class IpProxyFactory extends IpResourceData implements PooledObjectFactory<IpProxy> {

    public IpProxy create(){
        try {
            return this.getIP();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new IpProxy();
    }

    public  PooledObject<IpProxy> wrap(IpProxy var1){
        return new DefaultPooledObject<>(var1);
    }

    @Override
    public void activateObject(PooledObject<IpProxy> pooledObject) throws Exception {
       IpProxy proxy =  pooledObject.getObject();
       if(proxy!=null){
           proxy.setFinallyUsedTime(LocalDateTime.now());
       }
    }

    @Override
    public void destroyObject(PooledObject<IpProxy> pooledObject) throws Exception {
       IpProxy ipProxy = pooledObject.getObject();
       if(ipProxy!=null){
           this.release(ipProxy);
       }
    }

    @Override
    public void destroyObject(PooledObject<IpProxy> p, DestroyMode destroyMode) throws Exception {
        PooledObjectFactory.super.destroyObject(p, destroyMode);
    }

    @Override
    public PooledObject<IpProxy> makeObject() throws Exception {
        return this.wrap(this.create());
    }

    @Override
    public void passivateObject(PooledObject<IpProxy> pooledObject) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject<IpProxy> pooledObject) {
        return false;
    }

//    ProxyResource resource = new Ip3366Resource(1,1,10);
//
//    MimvpResource mimvpResource = new MimvpResource(1,10);
}

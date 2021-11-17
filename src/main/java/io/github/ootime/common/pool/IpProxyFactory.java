package io.github.ootime.common.pool;

import io.github.ootime.common.pool.resource.Ip3366Resource;
import io.github.ootime.common.pool.resource.MimvpResource;
import io.github.ootime.common.pool.resource.ProxyResource;
import io.github.ootime.common.pool.util.NetStateUtil;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 问题需要做资源池
 */
public class IpProxyFactory  extends BasePooledObjectFactory<IpProxy> {

    ProxyResource resource = new Ip3366Resource(1,1,10);

    MimvpResource mimvpResource = new MimvpResource(1,10);

    BlockingQueue<IpProxy> ipQueue = new ArrayBlockingQueue(50);

    private GenericObjectPoolConfig config;

    public IpProxyFactory(GenericObjectPoolConfig config){
        this.config = config;
        loadResource();
    }


    public  synchronized  void loadResource(){
        List<IpProxy> proxies = resource.getResource();
        proxies.addAll(mimvpResource.getResource());
        for(IpProxy ip:proxies){
            ipQueue.offer(ip);
        }
    }

    @Override
    public IpProxy create() throws Exception {
        if(ipQueue.size()==0){
            loadResource();
        }
        return ipQueue.poll();
    }

    @Override
    public PooledObject<IpProxy> wrap(IpProxy ipProxy) {
        return new DefaultPooledObject<>(ipProxy);
    }

    //销毁
    @Override
    public void destroyObject(PooledObject<IpProxy> p, DestroyMode destroyMode) throws Exception {
//        ();
//        superp.destroyObject(p, destroyMode);
//        p.invalidate();
    }

    @Override
    public boolean validateObject(PooledObject<IpProxy> p) {
        return NetStateUtil.connecting( p.getObject(),500,TimeUnit.MILLISECONDS);
    }

    //激活
    @Override
    public void activateObject(PooledObject<IpProxy> p) throws Exception {
        p.getObject().setFinallyUsedTime(LocalDateTime.now());
        super.activateObject(p);
    }

    //产生一个连接对象 初始化
    @Override
    public PooledObject<IpProxy> makeObject() throws Exception {
        return super.makeObject();
    }

    public class ResourcePull implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    if(ipQueue.size()==0){
                        loadResource();
                    }else{
                        TimeUnit.SECONDS.sleep(5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

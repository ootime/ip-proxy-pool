package io.github.ootime.common.pool.resource;

import io.github.ootime.common.pool.IpProxy;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class IpResourceData {

    private List<ProxyResource> resourceList = new ArrayList<>();

    private BlockingQueue<IpProxy> queue = new LinkedBlockingDeque();

    Set<Object> checkSet = Collections.synchronizedSet(new HashSet<>());


    public IpResourceData(){
        resourceList.add(new Ip3366ResourceTest());
        resourceList.add(new MimvpResource());
        resourceList.add(new SeofangfaResource());
        load();
        Thread thread = new Thread(new RefreshResource(this));
        thread.setDaemon(true);
        thread.start();
    }

    private  void load(){
        for(ProxyResource resource:resourceList){
            Set<IpProxy> set = resource.getResource();
            for(IpProxy data:set){
                if(checkSet.add(data)){
                    queue.offer(data);
                }
            }
        }
    }

    public IpProxy getIP() throws InterruptedException {
        System.out.println("正在获取"+queue.size());
        return queue.poll(10, TimeUnit.SECONDS);
    }

    public int ipSzie(){
        return queue.size();
    }

    public void release(IpProxy ipProxy){
        this.checkSet.remove(ipProxy);
    }


    class RefreshResource implements Runnable{

        private IpResourceData data;
        RefreshResource(IpResourceData data){
            this.data = data;
        }
        @Override
        public void run() {
            while (true){
                try {
                    if(data.ipSzie()==0){
                        data.load();
                        System.out.println("检测");
                    }else{
                         TimeUnit.SECONDS.sleep(3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

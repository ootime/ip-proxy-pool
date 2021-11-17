package io.github.ootime.common.pool.resource;

import io.github.ootime.common.pool.IpProxy;
import io.github.ootime.common.pool.util.NetStateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class ProxyResource {

    public  List<IpProxy> getResource(){
        List<IpProxy>  ipProxies =   loadResource();
        ipProxies = this.verification(ipProxies);
        ipProxies.sort((a,b)-> (int) (a.getRespondingSpeed()-b.getRespondingSpeed()));
        return ipProxies;
    }

    public abstract  List<IpProxy>  loadResource();

    public List<IpProxy> verification(List<IpProxy> ips){
        return  ips.stream().filter(ip->NetStateUtil.connecting(ip,1000,TimeUnit.MILLISECONDS)).collect(Collectors.toList());
    }
}

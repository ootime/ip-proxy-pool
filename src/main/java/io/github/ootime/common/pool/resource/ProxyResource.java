package io.github.ootime.common.pool.resource;

import io.github.ootime.common.pool.IpProxy;
import io.github.ootime.common.pool.util.NetStateUtil;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class ProxyResource {

    public Set<IpProxy> getResource(){
        Set<IpProxy>  ipProxies =   loadResource();
        ipProxies = this.verification(ipProxies);
//        ipProxies.so((a,b)-> (int) (a.getRespondingSpeed()-b.getRespondingSpeed()));
        return ipProxies;
    }

    public abstract  Set<IpProxy>  loadResource();

    public Set<IpProxy> verification(Set<IpProxy> ips){
        return  ips.stream().filter(ip->NetStateUtil.connecting(ip,500,TimeUnit.MILLISECONDS)).collect(Collectors.toSet());
    }
}

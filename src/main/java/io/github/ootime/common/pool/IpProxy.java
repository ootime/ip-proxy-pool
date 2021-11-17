package io.github.ootime.common.pool;

import io.github.ootime.common.pool.util.NetStateUtil;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class IpProxy {

    private String ip; // ip地址

    private String port; //端口

    private IpType type; // ipv4/ipv6

    private boolean highConcealment;  //是否高匿

    private String origin; //来源

    private Protocol protocol; //协议

    private long respondingSpeed; //响应速度

    private LocalDateTime finallyUsedTime; //最后使用时间

    private String httpMethodSupport; //http协议method支持

    private String location; //位置


    static  public enum IpType{
        ip_v4,
        ip_v6
    }
    static  public enum Protocol{
        http,
        https
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public IpType getType() {
        return type;
    }

    public void setType(IpType type) {
        this.type = type;
    }

    public boolean isHighConcealment() {
        return highConcealment;
    }

    public void setHighConcealment(boolean highConcealment) {
        this.highConcealment = highConcealment;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public long getRespondingSpeed() {
        return respondingSpeed;
    }

    public void setRespondingSpeed(long respondingSpeed) {
        this.respondingSpeed = respondingSpeed;
    }

    public LocalDateTime getFinallyUsedTime() {
        return finallyUsedTime;
    }

    public void setFinallyUsedTime(LocalDateTime finallyUsedTime) {
        this.finallyUsedTime = finallyUsedTime;
    }

    public String getHttpMethodSupport() {
        return httpMethodSupport;
    }

    public void setHttpMethodSupport(String httpMethodSupport) {
        this.httpMethodSupport = httpMethodSupport;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void verification(long timeOut, TimeUnit unit){
        NetStateUtil.connecting(this,timeOut,unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        IpProxy ipProxy = (IpProxy) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder().append(ip, ipProxy.ip).append(port, ipProxy.port).isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37).append(ip).append(port).toHashCode();
    }

    @Override
    public String toString() {
        return "IpProxy{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", respondingSpeed=" + respondingSpeed +
                '}';
    }
}

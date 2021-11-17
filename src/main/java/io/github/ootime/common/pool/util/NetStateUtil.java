package io.github.ootime.common.pool.util;

import io.github.ootime.common.pool.IpProxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class NetStateUtil {

    public  static boolean connecting(IpProxy ip, long timeOut, TimeUnit unit)  {
        try {
            Socket socket = new Socket();
            long start = System.currentTimeMillis();
            socket.connect(new InetSocketAddress(ip.getIp(),Integer.valueOf(ip.getPort())), (int) unit.toMillis(timeOut));
            long end = System.currentTimeMillis();
            socket.close();
            ip.setRespondingSpeed(end-start);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

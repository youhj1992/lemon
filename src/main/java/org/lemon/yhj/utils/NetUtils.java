package org.lemon.yhj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Created by wenqi.huang on 2017/6/19.
 */
public class NetUtils {

    private static final Logger log = LoggerFactory.getLogger(NetUtils.class);

    public static final String ANYHOST = "0.0.0.0";
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static volatile String LocalIP = null;

    /**
     * 获取本机内网IP
     *
     * @return
     */
    public static String getLocalIp() {
        if (LocalIP != null) {
            return LocalIP;
        }
        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            if (isValidLocalAddress(localAddress)) {
                return localAddress.getHostAddress();
            }
        } catch (Throwable e) {
            log.warn("Failed to retriving ip address, " + e.getMessage(), e);
        }
        Enumeration netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            log.error(" NetworkInterface.getNetworkInterfaces error", e);
            return null;
        }
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
            Enumeration address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                InetAddress ip = (InetAddress) address.nextElement();
                if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {//内网IP
                    LocalIP = ip.getHostAddress();
                    return LocalIP;
                }
            }
        }
        return LocalIP;
    }

    /**
     * 判断是否合法的本机内网ip
     * @return
     */
    private static boolean isValidLocalAddress(InetAddress address){
        if (address == null || address.isLoopbackAddress() || !address.isSiteLocalAddress()) {
            return false;
        }
        if(address instanceof Inet6Address){
            return false;
        }
        String name = address.getHostAddress();
        return (name != null
                && ! ANYHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

}

package com.jdxl.seedcourse.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerMachineInfoMsg {

    public static String getSelfMachineIp() {
        String ipAddress = "127.0.0.1";
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipAddress;
    }
}

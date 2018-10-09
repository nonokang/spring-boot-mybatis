package com.spring.boot;

import java.io.*;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;


public class Graphite01 {
    public static Queue<String> queue = new LinkedList<>();//队列

    private static final String host = "127.0.0.1";
    private static final int port = 2003;
    private static final InetSocketAddress address = new InetSocketAddress(host, port);

    public static void main(String[] arg) throws Exception{
        Date dNow=new Date(1538028943 * 1000L);
        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sDateFormat.format(dNow);
        System.out.println(str);
        System.out.println(System.currentTimeMillis() / 1000L);
        test1();
    }

    public static void test1() throws Exception {

        final Graphite graphite = new Graphite(address);
        graphite.connect();
        boolean b = graphite.isConnected();
        if(b){
            System.out.println("=====连接成功=====");
            //graphite.send("wpk.windows.cpu", "60", 150);
            //graphite.flush();
            while(true){
                cpu(graphite);
                Thread.sleep(2000);
            }
        }else{
            System.out.println("=====连接失败=====");
        }
        graphite.close();
    }

    private static void cpu(Graphite graphite) throws SigarException, IOException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            //String str = String.format("wpk.windows.cpu_%s %s %s",(i+1),CpuPerc.format(cpuList[i].getCombined()),System.currentTimeMillis());
            String str = String.format("wpk.windows.cpu_Combined.cpu_%s",(i+1));
            String cpuPer = CpuPerc.format(cpuList[i].getCombined()).replace("%","");
            graphite.send(str, cpuPer, System.currentTimeMillis() / 1000L);
            graphite.flush();
        }
    }
}

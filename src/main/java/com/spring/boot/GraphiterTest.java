package com.spring.boot;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;

public class GraphiterTest {

    private static final String host = "127.0.0.1";
    private static final int port = 2003;
    private static final InetSocketAddress address = new InetSocketAddress(host, port);

    public static void main(String[] args) throws InterruptedException, SigarException, IOException {
        final MetricRegistry registry = new MetricRegistry();
        final Graphite graphite = new Graphite(address);
        graphite.connect();
        boolean b = graphite.isConnected();
        if(b){
            /*while(true){
                cpu();
                Thread.sleep(3000);
            }*/
            System.out.println("=====连接成功=====");
            //graphite.send("carbon agents name", "value", 100);
            //graphite.send("system.agents", "50", 100);
            graphite.send("wpk.user", "50", 100);
            graphite.flush();
        }else{
            System.out.println("=====连接失败=====");
        }
        graphite.close();
    }

    private static void memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 内存总量
        System.out.println("内存总量:    " + mem.getTotal() / 1024L + "K av");
        // 当前内存使用量
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        // 当前内存剩余量
        System.out.println("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
        Swap swap = sigar.getSwap();
        // 交换区总量
        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
    }

    private static void cpu() throws SigarException, IOException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            /*CpuInfo info = infos[i];
            System.out.println("第" + (i + 1) + "块CPU信息");
            System.out.println("CPU的总量MHz:    " + info.getMhz());// CPU的总量MHz
            System.out.println("CPU生产商:    " + info.getVendor());// 获得CPU的卖主，如：Intel
            System.out.println("CPU类别:    " + info.getModel());// 获得CPU的类别，如：Celeron
            System.out.println("CPU缓存数量:    " + info.getCacheSize());// 缓冲存储器数量
            printCpuPerc(cpuList[i]);*/
            String str = String.format("win.cpu_%s %s %s",(i+1),CpuPerc.format(cpuList[i].getCombined()),System.currentTimeMillis());
            sb.append(str);
        }
        sb.append("\n");
        Socket socket = new Socket("localhost", 2003);
        OutputStream outputStream = socket.getOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        byte[] b = sb.toString().getBytes();
        ps.write(b);
        ps.flush();
        //千万不能忘记关闭输入输出流!!否则不会出结果!!
        socket.shutdownOutput();
        InputStream inputStream = socket.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String a = "";
        while((a = bf.readLine()) != null){
            System.out.println("服务器说:"+a);
        }
        socket.shutdownInput();

        bf.close();
        inputStream.close();
        ps.close();
        outputStream.close();
        socket.close();
        /*System.out.println("========发送结束=======");*/
    }

    private static void printCpuPerc(CpuPerc cpu) {
        System.out.println("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
        System.out.println("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
        System.out.println("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
        System.out.println("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
        System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
        System.out.println("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率
    }

}

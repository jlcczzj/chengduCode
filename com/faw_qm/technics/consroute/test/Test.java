package com.faw_qm.technics.consroute.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class Test
{
    public Test()
    {}

    private static Test test = null;
    private int i = 0;

    public static void main(String[] args) throws Exception
    {
        HashMap map = new HashMap();
        Vector vec = new Vector();
        map.put("1", vec);
        vec.add("success");
        System.out.println(((Vector)map.get("1")));

    }

    public static void copyFile() throws Exception
    {
        File input = new File("C:\\test");
        File output = new File("C:\\test_new");
        if(input.isDirectory() || output.isDirectory())
        {
            throw new Exception("±\u00D8\u00D0\u00EB\u00CA\u00C7\u00CE\u00C4\u00BC\u00FE");
        }
        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(output);
            FileInputStream fileinputstream = new FileInputStream(input);
            byte abyte0[] = new byte[8192];
            int i = 0;
            do
            {
                fileoutputstream.write(abyte0, 0, i);
                i = fileinputstream.read(abyte0, 0, abyte0.length);
                System.out.println("##################" + i);
            }while(i != -1);
            fileinputstream.close();
            fileoutputstream.close();
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void test20() throws Exception
    {
        Vector vec = new Vector();
        TechnicsRouteListIfc listInfo1 = new TechnicsRouteListInfo();
        listInfo1.setRouteListNumber("1111");
        vec.add(listInfo1);
        Object[] obj = vec.toArray();
        //TechnicsRouteListIfc obj = (TechnicsRouteListIfc)obj[0];
        System.out.println(((TechnicsRouteListIfc)vec.elementAt(0)).getRouteListNumber());
        System.out.println(((TechnicsRouteListIfc)obj[0]).getRouteListNumber());
        ((TechnicsRouteListIfc)vec.elementAt(0)).setRouteListNumber("5555");
        System.out.println(((TechnicsRouteListIfc)vec.elementAt(0)).getRouteListNumber());
        System.out.println(((TechnicsRouteListIfc)obj[0]).getRouteListNumber());
    }

    public static Test newTest()
    {
        if(test == null)
        {
            System.out.println("第一次生成实例");
            test = new Test();
            return test;
        }else
        {
            System.out.println("第二次获得同一实例");
            return test;
        }
    }

    public synchronized void test13(String s, int j) throws Exception
    {
        i = j;
        System.out.println("i = " + i + ", s= " + s);
        if(i == 1)
        {
            Thread.sleep(3000);
        }
        if(i == 2)
        {
            Thread.sleep(3500);
        }
        System.out.println("i = " + i + ", s= " + s);
    }

    private static void test12() throws Exception
    {
        //int i = DateFormat.getInstance().getCalendar().;
        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
    }

    private static void test11() throws Exception
    {
        //PrintWriter pw = new PrintWriter(new ByteArrayOutputStream());
        ByteArrayOutputStream pw = new ByteArrayOutputStream();
        String s = "hello,,,,";
        String enter = "\n";
        String s2 = "how are you?";
        byte[] byte1 = s.getBytes();
        byte[] byte2 = enter.getBytes();
        byte[] byte3 = s2.getBytes();
        pw.write(byte1);
        pw.write(byte1);
        pw.write(byte2);
        pw.write(byte3);
        pw.close();
        //FileWriter fw = new FileWriter("c:\\tt.txt");
        FileOutputStream fw = new FileOutputStream("c:\\tt.csv");
        pw.writeTo(fw);

        fw.close();
        System.out.println("打印结束。");
    }

    private static void test10()
    {
        java.util.Date date1 = new java.util.Date();

        String time = "< " + DateFormat.getInstance().format(date1) + " > ";
        //String time = "< " + (System.currentTimeMillis()) + " > ";
        System.out.println(time + date1.toString());
    }

    private static void test3()
    {
        String createTime = "2004/3/5";
        String new1 = createTime.replace('/', '-');
        System.out.println(createTime + ", " + new1 + " 1111");
    }

    private static void test5()
    {
        //String s = "2004-mm-dd hh:mm:ss.fffffffff";
        String s1 = "    2004-4-2 00:00:00.000  ";
        String s2 = "2004-4-2 24:00:00.000";
        Timestamp time1 = Timestamp.valueOf(s1);
        Timestamp time2 = Timestamp.valueOf(s2);
        System.out.println(time1.toString() + " , " + time1.getTime());
        System.out.println(time2.toString() + " , " + time2.getTime());

    }

    //System.out.println(DateFormat.getInstance().format(new Date()));
    private static void test2() throws Exception
    {
        Collection vec = new Vector();
        /*
         * String s1 = "bso_111"; String s2 = "bso_222"; String s3 = "bso_333"; String s4 = "bso_444"; String s5 = "bso_555"; TechnicsRouteListInfo listInfo1 = new TechnicsRouteListInfo();
         * listInfo1.setBsoID(s1); //listInfo1.setCreateTime(time); TechnicsRouteListInfo listInfo2 = new TechnicsRouteListInfo(); listInfo2.setBsoID(s2); TechnicsRouteListInfo listInfo3 = new
         * TechnicsRouteListInfo(); listInfo3.setBsoID(s3); TechnicsRouteListInfo listInfo4 = new TechnicsRouteListInfo(); listInfo4.setBsoID(s4); TechnicsRouteListInfo listInfo5 = new
         * TechnicsRouteListInfo(); listInfo5.setBsoID(s5); vec.add(listInfo3); vec.add(listInfo1); vec.add(listInfo2); vec.add(listInfo5); vec.add(listInfo4);
         */
        Timestamp time = new Timestamp(44444);
        Timestamp time2 = new Timestamp(55555);
        TechnicsRouteListInfo listInfo1 = new TechnicsRouteListInfo();
        listInfo1.setBsoID("111");
        listInfo1.setModifyTime(time);
        TechnicsRouteListInfo listInfo2 = new TechnicsRouteListInfo();
        listInfo2.setBsoID("222");
        listInfo2.setModifyTime(time2);
        TechnicsRouteListInfo listInfo3 = new TechnicsRouteListInfo();
        listInfo2.setBsoID("333");
        listInfo2.setModifyTime(time2);

        vec.add(listInfo2);
        vec.add(listInfo1);
        vec.add(listInfo3);
        Collection sortedVec = RouteHelper.sortedInfos(vec, "getModifyTime", true);
        System.out.println("sortedVec===============" + sortedVec);
    }

    private static void test1()
    {
        Collection vec = new Vector();
        String s1 = "bso_111";
        String s2 = "bso_222";
        String s3 = "bso_333";
        TechnicsRouteListInfo listInfo1 = new TechnicsRouteListInfo();
        listInfo1.setBsoID("bso_111");
        TechnicsRouteListInfo listInfo2 = new TechnicsRouteListInfo();
        listInfo2.setBsoID("bso_222");
        TechnicsRouteListInfo listInfo3 = new TechnicsRouteListInfo();
        listInfo3.setBsoID("bso_333");
        vec.add(listInfo3);
        vec.add(listInfo1);
        vec.add(listInfo2);
        for(Iterator iter = vec.iterator();iter.hasNext();)
        {
            System.out.println(iter.next());
        }
        System.out.println("###################");
        Object[] listInfo = vec.toArray();
        //排序
        for(int j = listInfo.length;j > 0;j--)
        {
            System.out.println("j= " + j);
            Object temp = null;
            for(int i = 0;i < j - 1;i++)
            {
                System.out.println("i= " + i);
                System.out.println("listInfo[" + i + "]= " + ((TechnicsRouteListInfo)listInfo[i]).getBsoID());
                System.out.println("listInfo[" + (i + 1) + "]= " + ((TechnicsRouteListInfo)listInfo[i + 1]).getBsoID());
                if(((TechnicsRouteListInfo)listInfo[i]).getBsoID().compareTo(((TechnicsRouteListInfo)listInfo[i + 1]).getBsoID()) > 0)
                {
                    temp = listInfo[i];
                    listInfo[i] = listInfo[i + 1];
                    listInfo[i + 1] = temp;
                    System.out.println("进行替换");
                }
            }
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        for(int i = 0;i < listInfo.length;i++)
        {
            System.out.println(listInfo[i]);
        }
    }
}
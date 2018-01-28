package com.faw_qm.cderp.util;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.util.EJBServiceHelper;
import javax.naming.CommunicationException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.domain.util.DomainHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.queue.exception.QueueException;
/**
 *erp回传数据加入现生产基线服务类，定时扫描路径下文件
 * @author 刘家坤
 * @version 1.0
 */ 
public class BaseLineService extends Thread
    implements Runnable{
	  private static String queueName = null;
	    private static String factoryName = null;
	    private static long intervalTime = 1000L;
	    private static long sleepTime = 60000L;
	    private static boolean runFlag = false;
	    private static boolean Debug = RemoteProperty.getProperty("com.faw_qm.framework.verbose", "true").equals("true");
	   // private static String hostname = RemoteProperty.getProperty("qm.ServerName","172.16.2.107");
	    private static String adminPassword = "thinkpad2012";

	    public BaseLineService()
	    {
//	    	System.out.println("1--"+Debug+"2--"+hostname+"3--"+adminPassword);
	        setName("BaseLineService");
	    }

	    public static void init()
	    {
	        try
	        {
	            intervalTime = Long.parseLong(RemoteProperty.getProperty("com.faw_qm.framework.baselineinterval", "10800000"));
	            sleepTime = Long.parseLong(RemoteProperty.getProperty("com.faw_qm.framework.psleepinterval", "300000"));
	        }
	        catch(NumberFormatException numberformatexception)
	        {
	            System.out.println("时间间隔数字格式不对" + numberformatexception.toString());
	        }
	     
	    }

	    public static void start1()
	    {
	        if(runFlag)
	        {
	            System.out.println("time service already start");
	            return;
	        }
	        if(Debug)
	            System.out.println("time service begin start");
	        init();
	        exec();
	    }

	    public static void start1(long l)
	    {
	        if(runFlag)
	        {
	            System.out.println("time service already start");
	            return;
	        }
	        if(Debug)
	            System.out.println("time service begin start");
	        intervalTime = l;
	        exec();
	    }

	    public static void main(String args[])
	    {
	        System.out.println("----------------");
	        if(args.length > 1)
	        {
	            System.out.println("不可以有超过一个以上的参数");
	            System.exit(1);
	        }
	        init();
	        if(args.length == 1)
	        {
	            try
	            {
	                long l = Long.parseLong(args[0]);
	                start1(l);
	                return;
	            }
	            catch(Exception exception)
	            {
	                System.out.println("参数应该是数字格式");
	            }
	            System.exit(1);
	        } else
	        {
	            start1();
	        }
	    }

	    public static void exec()
	    {
	        try
	        {
	            runFlag = true;
	            Thread thread = new Thread(new ThreadGroup("publish"), new BaseLineService());
	            thread.setName("BaseLineService");
	            thread.start();
	        }
	        catch(Exception exception)
	        {
	            runFlag = false;
	            exception.printStackTrace();
	        }
	    }

	    public void run()
	    {
	        try
	        {
	            while(runFlag)
	            {
//	            	System.out.println("runFlag======="+runFlag);
	                EJBServiceHelper.sendMessage("BaseLineService");
	                Thread.sleep(intervalTime);
	                if(Debug)
	                    System.out.println("BaseLineService service sendMessage");
	            }
	        }
	        catch(Exception exception)
	        {
	            boolean flag = false;
	            if(exception instanceof ServiceLocatorException)
	            {
	                Exception exception1 = ((ServiceLocatorException)exception).getNestedException();
	                if(exception1 != null && (exception1 instanceof CommunicationException))
	                {
	                    flag = true;
	                    try
	                    {
	                        Thread thread = Thread.currentThread();
	                        if(thread.getName().equals("BaseLineService"))
	                        {
	                            if(Debug)
	                                System.out.println("Current Thread is PublishService, it is to sleep for one minute because the JMSSession hasn't created.");
	                            Thread _tmp = thread;
	                            Thread.sleep(sleepTime);
	                            thread.run();
	                        } else
	                        if(Debug)
	                            System.out.println("Current Thread is not PublishService ??????????? ");
	                    }
	                    catch(InterruptedException interruptedexception)
	                    {
	                        interruptedexception.printStackTrace();
	                    }
	                }
	            }
	            if(!flag)
	                exception.printStackTrace();
	        }
	    }

	    public static boolean isRun()
	    {
	        return runFlag;
	    }

	    public static void stop1()
	    {
	        runFlag = false;
	    }
	        /**
	     * 获取管理员密码
	     */
	    private static void initPassword()
	    {
	        //管理员用户和密码设置在PasswordTable表中
	        String passwordTable = RemoteProperty.getProperty(
	                "com.faw_qm.queue.passwordTable", "PasswordTable");
	        String sql = "Select password from " + passwordTable +
	                     " where username=?";
	        String adminUser = DomainHelper.ADMINISTRATOR_NAME;
	        java.sql.Connection conn = null;
	        ResultSet result = null;
	        PreparedStatement pm = null;
	        try
	        {
	            conn = PersistUtil.getConnection();
	            pm = conn.prepareStatement(sql);
	            pm.setString(1, adminUser);
	            result = pm.executeQuery();
	            String s = null;
	            if (result.next())
	            {
	                s = result.getString(1);
	        
	            }
	            if (s == null)
	            {
	                throw new QueueException("2", null);
	            }
	            adminPassword = s;
	        }
	        catch (Exception se)
	        {
	            throw new QMRuntimeException(new QueueException(se, "4", null));
	        }
	        finally
	        {
	            try
	            {
	                if (result != null)
	                {
	                    result.close();
	                }
	                if (pm != null)
	                {
	                    pm.close();
	                }
	                if (conn != null)
	                {
	                    conn.close();
	                }
	            }
	            catch (SQLException ex)
	            {
	                ex.printStackTrace();
	            }
	        }

	    }
	}

/**
 * 生成程序BaseDataPublisher.java	1.0              2006-11-6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 时间带空格 刘家坤 2014-9-2
 * SS2 在工艺bom里给零件添加生产版本，如果有生产版本则获取生产版本 刘家坤 2014-10-08
 * SS3 在工艺bom里获取零部件下级结构按，单级bom方式获取 2014-11-22
 * SS4 文件名称出现“前准”ftp无法识别，由于解放服务器有问题引起，所以将“前准”替换为“QZ” 2015-12-01
 */
package com.faw_qm.jferp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
//import com.faw_qm.adoptnotice.model.AdoptNoticeIfc;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.jferp.ejb.service.PromulgateNotifyService;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.FileNameLinkIfc;
import com.faw_qm.jferp.model.FileNameLinkInfo;
import com.faw_qm.jferp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.part.client.main.controller.PartRequestServer;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import java.util.Vector;

/**
 * <p>Title: 发布基本信息类。</p>
 * <p>Description: 至少要发布零部件及其结构信息。</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public class BaseDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(BaseDataPublisher.class);

    /**
     * 属性配置文件名称。
     */
    private static String propertyFileName = "publish_data.xml";

    /**
     * 
     * 发布文件存放在本地的文件夹名称。
     * 
     */
    private static String publishFathName = "";

    /**
     * 发布文件存放到FTP服务器的文件夹名称。
     */
    private static String publishFTPPathName = "";

    /**
     * 数据文件中的XML数据元素集合。
     */
    protected static List dataList = new ArrayList();

    /**
     * 数据文件中的“description”XML元素。
     */
    private static QMXMLDesc descData = new QMXMLDesc();

    /**
     * 采用通知书或更改采用通知书。
     */
    protected static BaseValueIfc publishSourseObject;

    /**
     * 过滤后零部件结果集合。
     */
    protected static List xmlPartList;

    /**
     * 文件夹的结束标志。
     */
    public static String endString = "/";

    /**
     * XML文件夹的发布标志。
     */
    private static boolean publishFlag = true;
    
    /**
     * 青汽ERP数据发布的xml文件的文件名
     */
    private static String xmlName="";

    //private static FtpClient ftp = new FtpClient();
    
    /**
     * 发布是选择的零部件集合
     */
    protected static  Collection coll;
    
//	CCBegin SS2
	//生产版本集合
	public static HashMap scbb = new HashMap();
//	CCEnd SS2
//	CCBegin SS3
	public static Collection djbom = null;
//	CCEnd SS3
    /**
     * added by dikefeng 20100422，为了使得在发布的时候知道本次新发的物料都有哪些，需要在这里把过滤后的零件清单返回一个
     */
    protected static Vector filterParts;
    /*static{
        //  2获取erp服务信息
        final String server = (String) RemoteProperty.getProperty("erpServer");
        final String port0 = (String) RemoteProperty.getProperty("erpPort");
        final int port = Integer.valueOf(port0).intValue();
        //final String user = (String) RemoteProperty.getProperty("erpUser");
        //final String pass = (String) RemoteProperty.getProperty("erpPassword");

		try {
			ftp.openServer(server, port);
			//ftp.login(user, pass);
		} catch (IOException e) {
			e.printStackTrace();
            logger.fatal(e);
            logger.fatal("");
		}
    }*/

    /**
     * 缺省构造函数。
     */
    public BaseDataPublisher()
    {
        super();
    }
    
    public static void setColleciton(Collection co)
    {
    	coll=co;
    }
//	CCBegin SS2
	//生产版本集合
    public static void setScbb(HashMap scbbMap)
    {
    	scbbMap=scbb;
    }
    public static HashMap getScbb()
    {
    	return scbb;
    }
//	CCEnd SS2
//	CCBegin SS3
    public static void setBom(Collection vec)
    {
    	djbom=vec;
    }
    public static Collection getBom()
    {
    	return djbom;
    }
//	CCEnd SS2
    /**
     * added by dikefeng  20100423,，为了使得在发布的时候知道本次新发的物料都有哪些
     * @param co
     */
    public static void setFilterParts(Vector co)
    {
    	filterParts=co;
    }
    public static Collection getCollection()
    {
    	return coll;
    }
    
    /**
     * 获取XML文件是否发布的标识。
     * @return
     */
    public static boolean getPublishFlag()
    {
        return publishFlag;
    }

    /**
     * 设置XML文件是否发布的标识。
     * @param publishFlag
     */
    public static void setPublishFlag(boolean publishFlag)
    {
        BaseDataPublisher.publishFlag = publishFlag;
    }

    /**
     * 本方法用于客户端远程调用服务端方法
     *
     * @param serviceName
     *            String 要调用的服务类名称
     * @param methodName
     *            String 要调用的服务方法名称
     * @param paraClass
     *            Class[] 要调用的服务方法的参数的类型
     * @param paraObject
     *            Object[] 要调用的服务方法的参数值
     * @return Object 获得的值对象
     * @throws QMRemoteException
     */
    private static Object useServiceMethod(String serviceName,
                                           String methodName, Class<?>[] paraClass,
                                           Object[] paraObject)
            throws QMRemoteException
    {
        RequestServer server = RequestServerFactory.getRequestServer();
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName(serviceName);
        info1.setMethodName(methodName);
        Class<?>[] paraClass1 = paraClass;
        info1.setParaClasses(paraClass1);
        Object[] objs1 = paraObject;
        info1.setParaValues(objs1);
        Object obj = null;
        obj = server.request(info1);

        return obj;
    }
    
    /**
     * 组织发布过程，执行数据准备和发布。
     * @param publishSourseObject 采用通知书或更改采用通知书。
     * @return String XML文件名称。
     * @throws QMException
     */
    public static synchronized String publish(BaseValueIfc publishSourseObject)
            throws Exception
    {
    	try{
        final SimpleDateFormat simple = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss ");
        String publishFileName = "";
        String typeName = "";
        PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        //判断要发布的数据是采用通知书还是更改通知单，或者是更改采用通知单，或者是工艺数据
        if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
        {
            publishFileName = ((GYBomAdoptNoticeIfc) publishSourseObject)
                    .getAdoptnoticenumber();
            typeName = "工艺BOM通知单";
            propertyFileName = "publish_data.xml";
            publishFathName=(String) RemoteProperty.getProperty("pathName");
            publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        
        else if(publishSourseObject instanceof ManagedBaselineIfc)
        {
        	 publishFileName = ((ManagedBaselineIfc) publishSourseObject).getBaselineNumber();
        	 typeName = "基线";
        	 propertyFileName = "publish_data.xml";
        	 publishFathName=(String) RemoteProperty.getProperty("pathName");
        	 publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
            publishFileName = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
            typeName = "路线";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("routePathName");
            publishFTPPathName = RemoteProperty.getProperty("erpPath");
        }else{
        	publishFileName = "";
            typeName = "导入虚拟件";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("pathName");
            publishFTPPathName = RemoteProperty.getProperty("erpPath");
        }
        //"开始组织发布数据，数据源为：**。"
        logger.fatal(simple.format(new Date())
                + Messages.getString("Util.26", new Object[]{typeName,
                        publishFileName}));
        dataList = new ArrayList();
        String fileName = "";
        try
        {
            BaseDataPublisher.publishSourseObject = publishSourseObject;
            if(publishSourseObject != null)
            {
                initDescData(publishSourseObject);
                dataList.add(descData);
                dataList = QMXMLParser.parse(propertyFileName, dataList);
                final List publisherList = initPublishClass();
                logger.debug("publisherList.size==" + publisherList.size());
                //发布零部件基本信息及结构信息或其它相关信息。
                if(publisherList != null && publisherList.size() > 0)
                {
                    for (int i = 0; i < publisherList.size(); i++)
                    {
                        final BaseDataPublisher publisher = (BaseDataPublisher) publisherList
                                .get(i);
                        publisher.invoke();
                    }

                    	  if(xmlPartList != null && xmlPartList.size() == 0)
                          {
                              //"没有需要发布的零部件数据！"
                              logger.fatal(Messages.getString("Util.50")); //$NON-NLS-1$
                          }
                    
                  
                    if(publishFlag)
                    {
                        fileName = createXMLFile(publishSourseObject);
                    }
                    for (int i = 0; i < publisherList.size(); i++)
                    {
                        final BaseDataPublisher publisher = (BaseDataPublisher) publisherList
                                .get(i);
                        //去掉saveFilterPart（）解放此方法没用
                       // publisher.saveFilterPart();
                    }
                }
                else
                {
                    //"发布器为空，发布取消！"
                    logger.fatal(Messages.getString("Util.10")); //$NON-NLS-1$
                    throw new QMException(Messages.getString("Util.10"));
                }
            }
            else
            {
                //"发布源为空，发布取消！"
                logger.fatal(Messages.getString("Util.11")); //$NON-NLS-1$
                throw new QMException(Messages.getString("Util.11"));
            }
        }
        catch (QMException e)
        {
            //"发布失败！"
            logger.error(simple.format(new Date())
                    + Messages.getString("Util.0"), e); //$NON-NLS-1$
            logger.fatal(simple.format(new Date())
                    + Messages.getString("Util.0"), e); //$NON-NLS-1$
            throw new QMException(e);
        }
        //"完成组织发布数据！"
        logger.fatal(simple.format(new Date()) + Messages.getString("Util.27")); //$NON-NLS-1$
        if(publishFlag)
        {
            //设置文件名称。为上传时获取对应名称的XML文件做准备。
            FileNameLinkIfc link = new FileNameLinkInfo();
           // System.out.println("link="+link.getClass());
            link.setFileName(fileName);
            link.setNotice(publishSourseObject.getBsoID());
            pservice.saveValueInfo(link);
            //System.out.println("link="+link);
            //上传文件到服务器
            //transmit(fileName, publishSourseObject);
        }
        else
        {
            //"没有需要发布的零部件数据，发布取消！"
            logger.fatal(simple.format(new Date())
                    + Messages.getString("Util.86")); //$NON-NLS-1$
            publishFlag = true;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("publish(BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return fileName;
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    }

    /**
     * 由具体子类实现。
     * @throws QMXMLException
     */
    protected void saveFilterPart() throws QMXMLException
    {
    }

    /**
     * 由具体子类实现。对于具体的发布类型由该类型的发布器的该方法实现。
     * @throws QMXMLException
     */
    protected void invoke() throws Exception
    {
    }
    
    public static void setXmlName(String name)
    {
    	xmlName=name;
    }
    
    public static String getXmlName()
    {
    	return xmlName;
    }
    
    private static String getXMLFileName( BaseValueIfc publishSourseObject){
    	 SimpleDateFormat   simple = new SimpleDateFormat("yyyyMMdd-HHmmss "); //发布时间
    	 String fileNumber = "";
    	 if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
         {
            
             
              String stime=simple.format(new Date());
              fileNumber = stime.trim() + "-" + ((GYBomAdoptNoticeIfc)publishSourseObject).getAdoptnoticenumber();
    
         }
         else if(publishSourseObject instanceof TechnicsRouteListIfc)
         {
         	 
            
              String stime=simple.format(new Date());
              //CCBegin SS1
              String lx = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
              fileNumber = stime.trim() + "-" +lx.trim();
              fileNumber=fileNumber.trim();
//              System.out.println("stime***"+stime+"***");
//              System.out.println("stime.trim() + ***"+stime.trim() + "-"+"***");
              //CCEnd SS1
         }

         else 
         {
        	 String stime=simple.format(new Date());
        	 fileNumber = stime.trim() ;
            
         }
    	 return fileNumber;
    }

    /**
     * 初始化数据文件中的“description”XML元素。
     * @param publishSourseObject 采用通知书或更改采用通知书。
     */
    private static final void initDescData(
            final BaseValueIfc publishSourseObject)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("initDescData(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        descData = new QMXMLDesc();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd"); //发布时间
        String publishTime = simple.format(new Date());
        String fileNumber = "";
       // System.out.println("publishSourseObject********************"+publishSourseObject);
     if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
        {
        	 String nu = getXmlName();
           //  descData.setType(nu);
             descData.setType("工艺BOM更改采用单");
             //文件名称：时间+路线编号   刘家坤
             simple = new SimpleDateFormat("yyyyMMdd-HHmmss "); //发布时间
             String stime=simple.format(new Date());
             fileNumber = stime + "-" + ((GYBomAdoptNoticeIfc)publishSourseObject).getAdoptnoticenumber();
            // System.out.println("fileNumber********************"+fileNumber);
         descData.setFileNumber(fileNumber);
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
        	 String nu = getXmlName();  
             descData.setType(nu);
             //文件名称：时间+路线编号   刘家坤
             simple = new SimpleDateFormat("yyyyMMdd-HHmmss "); //发布时间
             String stime=simple.format(new Date());
             fileNumber = stime + "-" +((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
           //  System.out.println("fileNumber111********************"+fileNumber);
         descData.setFileNumber(fileNumber);
        }

        else 
        {
        	String nu = getXmlName();
             
             
             descData.setType(nu);
             //文件名称：时间+路线编号   刘家坤
             fileNumber = nu ;
        // System.out.println("nu********************"+nu);
         descData.setFileNumber(fileNumber);
        }
        simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss "); //发布时间
        descData.setDate(simple.format(new Date()));
        if(RemoteProperty.getProperty("qm.ServerName") == null)
            descData.setSourcetag("");
        else
            descData.setSourcetag(RemoteProperty.getProperty("qm.ServerName"));
        if(logger.isDebugEnabled())
        {
            logger.debug("initDescData(BaseValueIfc) - end"); //$NON-NLS-1$
        }
    }
    

    /**
     * 初始化数据发布器类对象集合。
     * @return List 数据发布器类集合。
     */
    private static final List initPublishClass() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("initPublishClass() - start"); //$NON-NLS-1$
        }
        final List publisherList = new ArrayList();
        for (int i = 0; i < dataList.size(); i++)
        {
            final QMXMLData data = (QMXMLData) dataList.get(i);
            final String publisher = data.getPublisher();
            if(publisher != null && !publisher.equals(""))
            {
                try
                {
                    final Object publisherClass = Class.forName(publisher)
                            .newInstance();
                    publisherList.add(publisherClass);
                }
                catch (final InstantiationException e)
                {
                    //"实例化名为*的类时出错！"
                    logger.error(Messages.getString(
                            "Util.1", new Object[]{publisher}), e); //$NON-NLS-1$
                    throw new QMXMLException(e);
                }
                catch (final IllegalAccessException e)
                {
                    //"访问名为*的类时出错！"
                    logger.error(Messages.getString(
                            "Util.2", new Object[]{publisher}), e); //$NON-NLS-1$
                    throw new QMXMLException(e);
                }
                catch (final ClassNotFoundException e)
                {
                    //"名为*的类在系统中不存在！"
                    logger.error(Messages.getString(
                            "Util.3", new Object[]{publisher}), e); //$NON-NLS-1$
                    throw new QMXMLException(e);
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("initPublishClass(HashMap) - end"); //$NON-NLS-1$
        }
        return publisherList;
    }

    /**
     * 正式创建数据文件。
     * @param publishSourseObject 采用通知书或更改采用通知书。
     * @return String XML文件名称。
     * @throws QMXMLException
     */
    private static final String createXMLFile(
            final BaseValueIfc publishSourseObject) throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("createXMLFile() - start"); //$NON-NLS-1$
        }
        final QMXMLCreator creator = new QMXMLCreator();
        //生成文件的名称
        String fileName = "";
        try
        {
            //"开始创建XML文档。"
            logger.fatal(Messages.getString("Util.24")); //$NON-NLS-1$
            //String publishFathName = (String) RemoteProperty.getProperty("pathName");
            String newPublishFathName=changePublishPath(publishFathName);
            if(!newPublishFathName.endsWith(endString))
            {
                newPublishFathName += endString;
            }
            for (int j = 0; j < dataList.size(); j++)
            {
                final QMXMLData data = (QMXMLData) dataList.get(j);
                if(data instanceof QMXMLDesc)
                {
                	//fileName = ((QMXMLDesc) data).getFilenumber() + ".xml";
                	fileName = getXMLFileName(publishSourseObject) + ".xml";
                	fileName=replaceStr(fileName);
                
                }
            }

            File file=new File(newPublishFathName+fileName);
            //首先判断文件是否存在，如果存在则不创建并且不发布，否则创建一个．
            if(file.exists()){
                //"该文件*已经存在，创建失败！"
                logger.fatal(Messages.getString("Util.53", new Object[]{fileName})); //$NON-NLS-1$
                publishFlag=false;
            }
            else{
            	fileName = creator.createDoc(publishSourseObject, dataList,newPublishFathName);
            	fileName=replaceStr(fileName);
                file=new File(newPublishFathName+fileName);
                String len = Long.toString(file.length());
                //"成功创建名称为*的XML文档，文件大小为* 字节，保存在应用服务器*路径下！"
                logger.fatal(Messages.getString("Util.25", new Object[]{fileName,
                        len, newPublishFathName}));
            }
        }
        catch (IOException e)
        {
            //"创建XML文件失败！"
            logger.fatal(Messages.getString("Util.4"), e); //$NON-NLS-1$
            throw new QMXMLException(e);
        }
        catch (QMException e)
        {
            //"创建XML文件失败！"
            logger.fatal(Messages.getString("Util.4"), e); //$NON-NLS-1$
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("createXMLFile() - end"); //$NON-NLS-1$
        }
        
        return fileName;
       
    }
//  CCBegin SS3
    /**
     * 传输数据。
     * @param filename XML文件名称。
     * @param publishSourseObject 采用通知书。
     * @return boolean 成功标识。
     * @throws QMException
     */
    public static  String replaceStr(String fileName ) { 
    System.out.println("fileName1==="+fileName);
    String aa ="";
    if(fileName.contains("前准")){
    	System.out.println("fileName3==="+fileName);
    	 aa = fileName.replace("前准", "QZ");
    }else{
    	aa = fileName;
    }
    System.out.println("fileName2==="+aa);
    return aa;
   
    }
    //CCEnd SS3

    /**
     * 传输数据。
     * @param filename XML文件名称。
     * @param publishSourseObject 采用通知书。
     * @return boolean 成功标识。
     * @throws QMException
     */
    /*public final static boolean transmit(String filename,
            BaseValueIfc publishSourseObject ) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("transmit(String, BaseValueIfc) - start"); //$NON-NLS-1$
        }
        final SimpleDateFormat simple = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss ");
        //开始上传时间。
        String publishTime = simple.format(new Date());
        //"*开始上传文件。"
        logger.fatal(Messages.getString("Util.36", new Object[]{publishTime})); //$NON-NLS-1$
        //1 获取发布的xml数据文件
        //  2获取erp服务信息
        final String server = (String) RemoteProperty.getProperty("erpServer");
        //"目标FTP服务器为*。"
        logger.fatal(Messages.getString("Util.37", new Object[]{server})); //$NON-NLS-1$
        //final String port0 = (String) RemoteProperty.getProperty("erpPort");
        //final int port = Integer.valueOf(port0).intValue();
        final String user = (String) RemoteProperty.getProperty("erpUser");
        final String pass = (String) RemoteProperty.getProperty("erpPassword");
        //20071113 zhangq add for erp begin:配制上传的ＦＴＰ服务器文件夹
        //final String publishFTPPathName = (String) RemoteProperty.getProperty("erpPath");
        //20071113 zhangq add for erp end
        //3 连接ftp服务器
        //20080219 zhangq begin
//        final FtpClient ftp = new FtpClient();
        try
        {
//            ftp.openServer(server, port);
            //4登陆方法，通过用户名密码登陆到指定的FTP服务器上去，并返回欢迎信息
            ftp.login(user, pass);
            //20080219 zhangq end
            //5 获得xml本地路径和文件名称
            //properties
            //String publishFathName = (String) RemoteProperty.getProperty("pathName");
            if(!publishFathName.endsWith(endString))
            {
                publishFathName += endString;
            }
            PromulgateNotifyService pnserivce=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
            //用ftp上传后的文件名与原文件名相同，同为filename变量内容
            String name = pnserivce.getFileNameByNotice(publishSourseObject);
            logger.fatal(Messages.getString("Util.41", new Object[]{name})); //$NON-NLS-1$
            if(!ftp.serverIsOpen())
            {
                //"服务器连接不可用，取消上传文件操作！"
                logger.fatal(Messages.getString("Util.32")); //$NON-NLS-1$
                return false;
            }
            //20071113 zhangq add for erp begin:配制上传的ＦＴＰ服务器文件夹
            if (publishFTPPathName!=null && publishFTPPathName.length()>0) {
            	ftp.cd(publishFTPPathName);
            }
            //20071113 zhangq add for erp end
            ftp.binary();
            TelnetOutputStream os = null;
            FileInputStream is = null;
            File file_in = null;
            long len = 0L;
            try
            {
                os = ftp.put(name);
                file_in = new File(publishFathName + name);
                if(file_in != null && file_in.length() == 0)
                {
                    //"上传文件为空，取消上传文件操作！"
                    logger.fatal(Messages.getString("Util.33")); //$NON-NLS-1$
                    return false;
                }
                is = new FileInputStream(file_in);
                final byte[] bytes = new byte[1024];
                int c;
                while ((c = is.read(bytes)) != -1)
                {
                    os.write(bytes, 0, c);
                    len += c;
                }
            }
            catch (IOException ex)
            {
                throw ex;
            }
            finally
            {
                if(is != null)
                {
                    is.close();
                }
                if(os != null)
                {
                    os.close();
                }
            }
            ftp.ascii();
            publishTime = simple.format(new Date());
            //"上传文件大小为*。"
            logger.fatal(Messages.getString(
                    "Util.52", new Object[]{Long.toString(len)})); //$NON-NLS-1$
            //"*完成上传文件。"
            logger.fatal(Messages.getString(
                    "Util.43", new Object[]{publishTime})); //$NON-NLS-1$
            logger.fatal("");
            //"完成上传文件！"
            //                logger.info(Messages.getString("Util.30") + newline); //$NON-NLS-1$
        }
        catch (IOException ex)
        {
            //"上传文件失败！"
            logger.fatal(Messages.getString("Util.31"), ex);
            logger.fatal("");
            throw new QMException(ex);
        }
        //last 更新采用标识
        if(publishSourseObject instanceof PromulgateNotifyIfc)
        {
            ((PromulgateNotifyIfc) publishSourseObject).setHasPromulgate("1");
            try
            {
            	PersistService pserivce=(PersistService)EJBServiceHelper.getService("PersistService");
            	pserivce.updateValueInfo(publishSourseObject);
            }
            catch (QMException e)
            {
                //"更新发布标识出错！"
                logger.error(Messages.getString("Util.42"), e);
                throw new QMException(e);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("transmit(String, BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return true;
    }*/

    /**
     * 生成变更请求的附件，即变更通知单.html。
     * @param changerequest 变更请求。
     * @throws QMException
     */
    public static final void obtainDadaForChange(BaseValueIfc changerequest)
            throws QMException
    {
        if(logger.isInfoEnabled())
        {
            //"开始附加变更通知单.html！"
            logger.info(Messages.getString("Util.47")); //$NON-NLS-1$
        }
        ChangeDataPublisher publisher = new ChangeDataPublisher();
        if(changerequest instanceof QMChangeRequestIfc)
            publisher.setCompareData((QMChangeRequestIfc) changerequest);
        if(logger.isInfoEnabled())
        {
            //"完成附加变更通知单.html！"
            logger.info(Messages.getString("Util.48")); //$NON-NLS-1$
        }
    }

    /**
     * 设置说明元素的注释信息。
     * @param note 注释信息。
     */
    protected final void setDescNote(final String note)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setDescNote(String) - start"); //$NON-NLS-1$
        }
        for (int j = 0; j < dataList.size(); j++)
        {
            final QMXMLData data = (QMXMLData) dataList.get(j);
            if(data instanceof QMXMLDesc)
            {
                ((QMXMLDesc) data).setNotes(((QMXMLDesc) data).getNotes()
                        + note);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("setDescNote(String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("main(String[]) - start");
        }
        //      测试数据。
        //            descData.setFileNumber(notifyIfc.getPromulgateNotifyNumber());
        //            descData.setType(notifyIfc.getPromulgateNotifyType());
        //            descData.setSourcetag("无");
        //            descData.setDate("无");
        //            descData.setNotes(notifyIfc.getPromulgateNotifyDescription());
        if(args != null && args.length > 0)
        {
            try
            {
                PartRequestServer partServer = new PartRequestServer(args[0],
                        args[1], args[2]);
                RequestServerFactory.setRequestServer(partServer);
            }
            catch (Exception e)
            {
                logger.error("main(String[])", e); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("main(String[]) - end"); //$NON-NLS-1$
                }
                return;
            }
        }
        else
        {
            try
            {
                String sid = PartRequestServer.getSessionID("192.168.0.110",
                        "7001", "Administrator", "administrator");
                PartRequestServer server = new PartRequestServer(
                        "192.168.0.110", "7001", sid);
                RequestServerFactory.setRequestServer(server);
            }
            catch (Exception ex)
            {
                logger.error("main(String[])", ex); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("main(String[]) - end"); //$NON-NLS-1$
                }
                return;
            }
        }
        try
        {
        	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        	BaseValueIfc valueIfc=(BaseValueIfc)pservice.refreshInfo("QMChangeRequest_971046");
            BaseDataPublisher.obtainDadaForChange(valueIfc);
        }
        catch (QMException e)
        {
            logger.error("main(String[])", e); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("main(String[]) - end");
        }
    }

    /**
     * 传输数据。
     * @param filename XML文件名称。
     * @param publishSourseObject 采用通知书。
     * @return boolean 成功标识。
     * @throws QMException
     */
    /*public final static boolean transmit(BaseValueIfc publishSourseObject)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("transmit(String, BaseValueIfc) - start"); //$NON-NLS-1$
        }
        //"开始上传文件！"
        logger.info(Messages.getString("Util.35")); //$NON-NLS-1$
        final SimpleDateFormat simple = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.S z");
        //开始上传时间。
        String publishTime = simple.format(new Date());
        //"开始上传文件时间为*。"
        logger.info(Messages.getString("Util.36", new Object[]{publishTime})); //$NON-NLS-1$
        //1 获取发布的xml数据文件
        //  2获取erp服务信息
        final String server = (String) RemoteProperty.getProperty("erpServer");
        //"FTP服务器名称或IP地址为*。"
        logger.info(Messages.getString("Util.37", new Object[]{server})); //$NON-NLS-1$
        final String port0 = (String) RemoteProperty.getProperty("erpPort");
        final int port = Integer.valueOf(port0).intValue();
        //"FTP服务器端口号为*。"
        logger.info(Messages.getString(
                "Util.38", new Object[]{Integer.toString(port)})); //$NON-NLS-1$
        final String user = (String) RemoteProperty.getProperty("erpUser");
        //"FTP用户名为*。"
        logger.info(Messages.getString("Util.39", new Object[]{user})); //$NON-NLS-1$
        final String pass = (String) RemoteProperty.getProperty("erpPassword");
        //"FTP密码为*。"
        logger.info(Messages.getString("Util.40", new Object[]{pass})); //$NON-NLS-1$
        //3 连接ftp服务器
        //20080219 zhangq begin
//        final FtpClient ftp = new FtpClient();
        try
        {
//            ftp.openServer(server, port);
            //4登陆方法，通过用户名密码登陆到指定的FTP服务器上去，并返回欢迎信息
            ftp.login(user, pass);
            //20080219 zhangq end
            //5 获得xml本地路径和文件名称
            //properties
            final String pathname = (String) RemoteProperty
                    .getProperty("pathName");
            //"上传文件的本地路径为*。"
            logger.info(Messages.getString("Util.45", new Object[]{pathname})); //$NON-NLS-1$
            //"上传文件名称为*。"
           if(!ftp.serverIsOpen())
            {
                //"服务器连接不可用，取消上传文件操作！"
                logger.warn(Messages.getString("Util.32") + "\n"); //$NON-NLS-1$
                return false;
            }
            ftp.binary();
            TelnetOutputStream os = null;
            FileInputStream is = null;
            File file_in = null;
            try
            {
                //用ftp上传后的文件名与原文件名相同，同为filename变量内容
            	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
            	String name=(String)pnservice.getFileNameByNotice(publishSourseObject);
                os = ftp.put(name);
                file_in = new File(pathname + name);
                if(file_in != null && file_in.length() == 0)
                {
                    //"上传文件为空，取消上传文件操作！"
                    logger.warn(Messages.getString("Util.33") + "\n"); //$NON-NLS-1$
                    return false;
                }
                is = new FileInputStream(file_in);
                final byte[] bytes = new byte[1024];
                int c;
                while ((c = is.read(bytes)) != -1)
                {
                    os.write(bytes, 0, c);
                }
            }
            catch (IOException ex)
            {
                throw ex;
            }
            finally
            {
                if(is != null)
                {
                    is.close();
                }
                if(os != null)
                {
                    os.close();
                }
            }
            ftp.ascii();
            if(logger.isInfoEnabled())
            {
                publishTime = simple.format(new Date());
                //"完成上传文件时间为*。"
                logger.info(Messages.getString(
                        "Util.43", new Object[]{publishTime})); //$NON-NLS-1$
                //"完成上传文件！"
                logger.info(Messages.getString("Util.30") + "\n"); //$NON-NLS-1$
            }
        }
        catch (IOException ex)
        {
            //"上传文件失败！"
            logger.error(Messages.getString("Util.31"), ex);
            throw new QMException(ex);
        }
        //last 更新采用标识
        if(publishSourseObject instanceof PromulgateNotifyIfc)
        {
            ((PromulgateNotifyIfc) publishSourseObject).setHasPromulgate("1");
            try
            {
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
                if(publishSourseObject instanceof PromulgateNotifyIfc)
                {
                	pservice.updateValueInfo(publishSourseObject);
                }
            }
            catch (QMException e)
            {
                //"更新发布标识出错！"
                logger.error(Messages.getString("Util.42"), e);
                throw new QMException(e);
            }
        }
        else if(publishSourseObject instanceof QMChangeRequestIfc)
        {
            //空方法。
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("transmit(String, BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return true;
    }*/

    /**
     * 转换字符串，将字符串中的'/'或者'\\'转换为'-'。
     * @param str 字符串。
     * @return String 转换后的字符串。
     * @throws QMException
     */
    private static String checkName(String str)
    {
        String resultStr = str;
        if(resultStr.indexOf("/") > 0 || resultStr.indexOf("\\") > 0)
        {
        	resultStr = resultStr.replace('/', '-');
        	resultStr = resultStr.replace('\\', '-');
        }
        return resultStr;
    }

    /**
     *改变发布文件的文件夹的路径。
     */
    private static String changePublishPath(String publishFathName)
            throws QMException
    {
        //ERP用户要求将日志文件存放到当前用户的文件夹下
        //RequestHelper requestHelper = new RequestHelper();
        //Class[] theClass = {};
        //Object[] theObjs = {};
        // 获得当前用户ID
        //UserIfc curUserIfc = (UserIfc) requestHelper.request("SessionService",
        //        "getCurUserInfo", theClass, theObjs);
        //return publishFathName + "/" + curUserIfc.getUsersName();
        return publishFathName;
    }
}

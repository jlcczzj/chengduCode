/**
 * 生成程序BaseDataPublisher.java	1.0              2006-11-6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 property中路径属性名发生变化 刘家坤 2016-03-11
 * SS2 文件名称出现“前准”ftp无法识别，由于解放服务器有问题引起，所以将“前准”替换为“QZ” 2016-03-21
 */
package com.faw_qm.erp.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.FileNameLinkIfc;
import com.faw_qm.erp.model.FileNameLinkInfo;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.client.main.controller.PartRequestServer;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
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
    		System.out.println("start********************publish");
        final SimpleDateFormat simple = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss ");
        String publishFileName = "";
        String typeName = "";
        PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        //判断要发布的数据是采用通知书还是更改通知单，或者是更改采用通知单，或者是工艺数据
        if(publishSourseObject instanceof PromulgateNotifyIfc)
        {
            publishFileName = ((PromulgateNotifyIfc) publishSourseObject)
                    .getPromulgateNotifyNumber();
            typeName = "采用通知书";
            propertyFileName = "publish_data.xml";
            //CCBegin SS1
            //publishFathName=(String) RemoteProperty.getProperty("pathName");
            publishFathName=(String) RemoteProperty.getProperty("erppathName");
            //CCEnd SS1
            publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
        	
            publishFileName = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
            typeName = "路线";
            propertyFileName = "publish_data.xml";
            //CCBegin SS1
            //publishFathName = RemoteProperty.getProperty("pathName");
            publishFathName = RemoteProperty.getProperty("erppathName");
            //CCEnd SS1
       
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
            	 //初始化数据文件中的“description”XML元素。
                initDescData(publishSourseObject);
                dataList.add(descData);
               // System.out.println("descData********************"+descData);
                dataList = QMXMLParser.parse(propertyFileName, dataList);
               // System.out.println("dataList********************"+dataList);
                // 初始化数据发布器类对象集合。
                final List publisherList = initPublishClass();
                logger.debug("publisherList.size==" + publisherList.size());
 
               // System.out.println("xmlPartList1111********************"+xmlPartList);

                //发布零部件基本信息及结构信息或其它相关信息。
                if(publisherList != null && publisherList.size() > 0)
                {
                    for (int i = 0; i < publisherList.size(); i++)
                    {
                        final BaseDataPublisher publisher = (BaseDataPublisher) publisherList
                                .get(i);
                        publisher.invoke();
                    }
                    System.out.println("7********************");
                    if(xmlPartList != null && xmlPartList.size() == 0)
                    {
                        //"没有需要发布的零部件数据！"
                        logger.fatal(Messages.getString("Util.50")); //$NON-NLS-1$
                    }
                  //  System.out.println("8********************");
                    if(publishFlag)
                    {	
                        fileName = createXMLFile(publishSourseObject);
                    }
                    for (int i = 0; i < publisherList.size(); i++)
                    {
                        final BaseDataPublisher publisher = (BaseDataPublisher) publisherList
                                .get(i);
                        publisher.saveFilterPart();
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
            link.setFileName(fileName);
            link.setNotice(publishSourseObject.getBsoID());
            pservice.saveValueInfo(link);
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
//        boolean a=false;
//        if(!a)
//        throw new Exception("dddddddddddddddd");
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
        if(publishSourseObject instanceof PromulgateNotifyIfc)
        {
            fileNumber = "AN-"
                    + ((PromulgateNotifyIfc) publishSourseObject)
                            .getPromulgateNotifyNumber() + "-" + publishTime;
            descData.setFileNumber(fileNumber);
            descData.setType("采用");
        }
       
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
            String nu = getXmlName();
       
            
                descData.setType(nu);
//                fileNumber =nu+ "路线："
//                	+ ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber()+"_"+((TechnicsRouteListIfc)publishSourseObject).getRouteListName()
//                	+ "-" + publishTime;
                //文件名称：时间+路线编号   刘家坤
                fileNumber = nu +"-" + ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
            descData.setFileNumber(fileNumber);
        }

       // System.out.println("descData11111111111********************"+descData);
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
//        int a=0;
//        if(a==1){
//        	try {
//				throw new Exception("ddd");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
       // System.out.println("descData22222222********************"+descData);
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
           // System.out.println("publishFathName********************"+publishFathName);
            String newPublishFathName=changePublishPath(publishFathName);
            System.out.println("newPublishFathName********************"+newPublishFathName);
            if(!newPublishFathName.endsWith(endString))
            {
                newPublishFathName += endString;
            }
            for (int j = 0; j < dataList.size(); j++)
            {
                final QMXMLData data = (QMXMLData) dataList.get(j);
                if(data instanceof QMXMLDesc)
                {
                	fileName = ((QMXMLDesc) data).getFilenumber() + ".xml";
                	 
                }
            }
         //System.out.println("fileName1********************"+fileName);
       	 String nu = getXmlName();
       	
            File file=new File(newPublishFathName+fileName);
            //首先判断文件是否存在，如果存在则不创建并且不发布，否则创建一个．
            if(file.exists()){
                //"该文件*已经存在，创建失败！"
                logger.fatal(Messages.getString("Util.53", new Object[]{fileName})); //$NON-NLS-1$
                publishFlag=false;
            }
            else{
            	fileName = creator.createDoc(publishSourseObject, dataList,newPublishFathName);
            	 //System.out.println("fileName2********************"+fileName);
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
    
//  CCBegin SS2
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
    //CCEnd SS2
}

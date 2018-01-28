/**
 * 生成程序BaseDataPublisher.java	1.0              2006-11-6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 时间带空格 刘家坤 2014-9-2
 * SS4 文件名称出现“前准”ftp无法识别，由于解放服务器有问题引起，所以将“前”替换为“QZ” 2015-12-01
 * SS5 给文件授权 刘家坤 2016-10-11
 * SS6 增加添加替换前数量、替换前零件和子组功能 刘家坤 2017-3-15
 * SS7 外购件和自制件分堆处理 刘家坤 2018-1-8
 */
package com.faw_qm.cderp.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.FileNameLinkIfc;
import com.faw_qm.cderp.model.FileNameLinkInfo;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.ejb.service.GYBomNoticeService;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkIfc;
import com.faw_qm.part.client.main.controller.PartRequestServer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
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
    //CCBegin SS6
   public static HashMap partMapnew = new HashMap();
   public static Vector cxvec = new Vector();
    //CCEnd SS6
    /**
     * 发布是选择的零部件集合
     */
    protected static  Collection coll;
    

	public static Collection djbom = null;
	
	public static int lsh = 0;
	//是否分堆
	public static int fd = 0;
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

    public static void setBom(Collection vec)
    {
    	djbom=vec;
    }
    public static Collection getBom()
    {
    	return djbom;
    }
    public static void setxmlid(int ii)
    {
    	lsh=ii;
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
            publishFathName=(String) RemoteProperty.getProperty("cdpathName");
            publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
            partMapnew = getAdoptPartlink((GYBomAdoptNoticeIfc)publishSourseObject);
        }
        
        else if(publishSourseObject instanceof ManagedBaselineIfc)
        {
        	 publishFileName = ((ManagedBaselineIfc) publishSourseObject).getBaselineNumber();
        	 typeName = "基线";
        	 propertyFileName = "publish_data.xml";
        	 publishFathName=(String) RemoteProperty.getProperty("cdpathName");
        	 publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
            publishFileName = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
            typeName = "路线";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("cdpathName");
            publishFTPPathName = RemoteProperty.getProperty("erpPath");
        }else{
        	publishFileName = "";
            typeName = "导入虚拟件";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("cdpathName");
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
                 //  System.out.println("dataList----------------------"+dataList);
                 //  System.out.println("xmlPartList----------------------"+xmlPartList);
                //  System.out.println("xmlPartListresult----------------------"+xmlPartList);
                    

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
             
              String lx = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
              fileNumber = stime.trim() + "-" +lx.trim();
              fileNumber=fileNumber.trim();
//              System.out.println("stime***"+stime+"***");
//              System.out.println("stime.trim() + ***"+stime.trim() + "-"+"***");
           
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
             //如果流水大于0，说明是批量变更功能，则.才找到。原来编号最终是在这个地方生成
             System.out.println("lsh********************"+lsh);
             if(lsh>0){
            	 fileNumber = fileNumber + "-" + lsh;
             }
            // System.out.println("fileNumber********************"+fileNumber);
             fd=1;
         descData.setFileNumber(fileNumber);
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
        	fd=0;
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
          //  System.out.println("publishFathName==="+publishFathName);
            //CCBegin SS7
            System.out.println("fdfd==="+fd);
            if(fd==1){
            	publishFathName="/pdm/productfactory/phosphor/cpdm/support/loadFiles/xml/erpFiles/ZZ/";
            }//CCEnd SS7
            String newPublishFathName=changePublishPath(publishFathName);
            if(!newPublishFathName.endsWith(endString))
            {
                newPublishFathName += endString;
            }
            //流水号不好使。生成不了
            for (int j = 0; j < dataList.size(); j++)
            {
                final QMXMLData data = (QMXMLData) dataList.get(j);
                if(data instanceof QMXMLDesc)
                {
                	//fileName = ((QMXMLDesc) data).getFilenumber() + ".xml";
                	fileName = getXMLFileName(publishSourseObject) + ".xml";
                	fileName=replaceStr(fileName);
                //	System.out.println("fileName======="+fileName);
                
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
            	fileName = creator.createDoc(publishSourseObject, dataList,newPublishFathName,lsh);
            	fileName=replaceStr(fileName);

                file=new File(newPublishFathName+fileName);
                //CCBegin SS5
               // System.out.println("给文件"+publishFathName+fileName+"授权");
             
                //第一个布尔代表自己是否有权限，第二个代表是否只有自己有权限，false是其他用户都有权限
                file.setExecutable(true,false);//设置可执行权限  
                file.setReadable(true,false);//设置可读权限  
                file.setWritable(true,false);//设置可写权限
                //CCEnd SS5
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
        fd=0;
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
   // System.out.println("fileName1==="+fileName);
    String aa ="";
    if(fileName.contains("前")){
    	System.out.println("fileName3==="+fileName);
    	 aa = fileName.replace("前", "QZ");
    }else{
    	aa = fileName;
    }
    aa = aa.replaceAll(" ", "");
   // System.out.println("fileName2==="+aa);
    return aa;
   
    }
    //CCEnd SS3

  

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
//  CCBegin SS6
    /**
     * 获取采用件及替换前数量，组件号等信息
     * @param GYBomAdoptNoticeIfc bomAdoptNoticeIfc
     * @return HashMap
     */
    private static HashMap getAdoptPartlink(GYBomAdoptNoticeIfc bomAdoptNoticeIfc)
    {
    	GYBomNoticeService bservice = null;
    	PersistService service = null;
    	Vector adoptVec = new Vector();
    	PartHelper partHelper = new PartHelper();
    	Collection col = null;
    	HashMap adoptMap =  new HashMap();
		try {
			//System.out.println("bomAdoptNoticeIfc==="+bomAdoptNoticeIfc);
			
			 service = (PersistService) EJBServiceHelper.getService("PersistService");
			 bservice = (GYBomNoticeService) EJBServiceHelper.getService("GYBomNoticeService");
			 col = bservice.getBomPartFromBomAdoptNotice(bomAdoptNoticeIfc);
//	    	获取车型零件
	  	    String partID = bomAdoptNoticeIfc.getTopPart();
	  	
	  		Vector vec = new Vector();
	  	    vec = partHelper.getBatchUpdateCM(partID,"W34");
        	for(int ii=0;ii<vec.size();ii++){
        		QMPartIfc partIfc = (QMPartIfc)vec.elementAt(ii);
        		String version = partHelper.getPartVersion(partIfc);
        		String bpartNumber = partHelper.getMaterialNumber(partIfc,version);
        		cxvec.add(bpartNumber);
        	}
    	if(col!=null&&col.size()>0){
    		//获取不采用数据
    	
    	    HashMap bmap =  new HashMap();
    		for(Iterator ite= col.iterator();ite.hasNext();){
    			GYBomAdoptNoticePartLinkIfc plinks = (GYBomAdoptNoticePartLinkIfc)ite.next();
    			//System.out.println("plinks==="+plinks);
    			if(plinks.getAdoptBs().equals("不采用")){    	
    				String[] badoptParts = new String[5];
    			    
    				String bbsoid = plinks.getPartID();//替换前bsoid
    				badoptParts[0]  = plinks.getSl();//数量
    				badoptParts[1]  = plinks.getPartNumber();
    				badoptParts[2]  = plinks.getVersionValue();
    				badoptParts[3]  = plinks.getBz3();//子组
    				badoptParts[4]  = plinks.getBz2();//父件
    				bmap.put(bbsoid, badoptParts);
//    				System.out.println("1111111111bbsoid==="+bbsoid);
//    				System.out.println("badoptParts[0]==="+badoptParts[0]);
//    				System.out.println("1111111111badoptParts[0]==="+badoptParts[0]);
    			}
    		}
    	//获取采用数据
    		for(Iterator itee= col.iterator();itee.hasNext();){
    			GYBomAdoptNoticePartLinkIfc links = (GYBomAdoptNoticePartLinkIfc)itee.next();
    			
    			if(links.getAdoptBs().equals("采用")){
    				String[] adoptParts = new String[10];
    				String partid = links.getPartID();
    				adoptParts[0] = links.getPartNumber();
    				adoptParts[1] = links.getVersionValue();
    				adoptParts[2] = links.getSl();
    
     
    				if(adoptParts[2]==null){
    					adoptParts[2]="0";
    				}
    			
    				if(links.getLinkPart()!=null&&!links.getLinkPart().equals("")){
    					QMPartIfc linkPart = null;
						try {
							if(links.getLinkPart()!=null&&!links.getLinkPart().equals("")){
								linkPart = (QMPartIfc)service.refreshInfo(links.getLinkPart());
							}
						} catch (QMException e) {
							// TODO 自动生成 catch 块
							e.printStackTrace();
						}
    					if(linkPart!=null){
    						String bpartNumber = partHelper.getMaterialNumber(linkPart,linkPart.getVersionID());
    						adoptParts[3] = bpartNumber;//不采用件编号
    						adoptParts[4] = linkPart.getVersionID();//不采用件版本
    					}else{
    						adoptParts[3] = "";
    						adoptParts[4] = "";
    					}
    				}else{
    					adoptParts[3] ="";
    					adoptParts[4] = "";
    				}
    				String parentId= links.getBz2();//父件bsoid
    				String parentNumber = "";
    				if(parentId!=null&&!parentId.equals("")){
    					QMPartIfc pPart =(QMPartIfc)service.refreshInfo(parentId);
    					parentNumber = partHelper.getMaterialNumber(pPart,pPart.getVersionID());
    				}
    				adoptParts[5] = parentNumber;
    				String subgroup = links.getBz3();//子组
    				if(subgroup==null||subgroup.equals("null")){
    					adoptParts[6] = "";
    				}else{
    					adoptParts[6] = subgroup;
    				}
    				
    				String bbsion = links.getLinkPart();//替换前不采用行向bsoid
    				//通过替换对获取原bsoid
    				String[] badopt= (String[])bmap.get(bbsion);
//    				System.out.println("1111111111bmap==="+bmap);
//    				System.out.println("1111111111bbsion==="+bbsion);
//    				System.out.println("1111111111badopt==="+badopt);
    				
    				if(badopt!=null){
    					adoptParts[7] = badopt[0];//替换前数量
    				}else{
    					adoptParts[7] = "0";
    				}
    				
//    				System.out.println("替换前数量==="+adoptParts[7]);
//    				System.out.println("替换后数量==="+adoptParts[2]);
    				//获取采用关联零件
    				String partnumber = "";
    				
    				if(partid!=null&&!partid.equals("")){
    					QMPartIfc qPart =(QMPartIfc)service.refreshInfo(partid);
    					String version = partHelper.getPartVersion(qPart);
    					 partnumber = partHelper.getMaterialNumber(qPart,version);
    				}
    				
    				adoptMap.put(parentNumber+"-"+partnumber+"-"+adoptParts[2], adoptParts);
    		
    				//System.out.println("partnumber+adoptParts[2]666==="+parentNumber+"-"+partnumber+"-"+adoptParts[2]);
    			}
    		}
		    	
           }
		} catch (QMException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
    	return adoptMap;
    	}

    //CCEnd SS6
  
}

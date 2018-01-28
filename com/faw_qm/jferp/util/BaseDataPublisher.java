/**
 * ���ɳ���BaseDataPublisher.java	1.0              2006-11-6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ʱ����ո� ������ 2014-9-2
 * SS2 �ڹ���bom��������������汾������������汾���ȡ�����汾 ������ 2014-10-08
 * SS3 �ڹ���bom���ȡ�㲿���¼��ṹ��������bom��ʽ��ȡ 2014-11-22
 * SS4 �ļ����Ƴ��֡�ǰ׼��ftp�޷�ʶ�����ڽ�ŷ������������������Խ���ǰ׼���滻Ϊ��QZ�� 2015-12-01
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
 * <p>Title: ����������Ϣ�ࡣ</p>
 * <p>Description: ����Ҫ�����㲿������ṹ��Ϣ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author л��
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
     * ���������ļ����ơ�
     */
    private static String propertyFileName = "publish_data.xml";

    /**
     * 
     * �����ļ�����ڱ��ص��ļ������ơ�
     * 
     */
    private static String publishFathName = "";

    /**
     * �����ļ���ŵ�FTP���������ļ������ơ�
     */
    private static String publishFTPPathName = "";

    /**
     * �����ļ��е�XML����Ԫ�ؼ��ϡ�
     */
    protected static List dataList = new ArrayList();

    /**
     * �����ļ��еġ�description��XMLԪ�ء�
     */
    private static QMXMLDesc descData = new QMXMLDesc();

    /**
     * ����֪ͨ�����Ĳ���֪ͨ�顣
     */
    protected static BaseValueIfc publishSourseObject;

    /**
     * ���˺��㲿��������ϡ�
     */
    protected static List xmlPartList;

    /**
     * �ļ��еĽ�����־��
     */
    public static String endString = "/";

    /**
     * XML�ļ��еķ�����־��
     */
    private static boolean publishFlag = true;
    
    /**
     * ����ERP���ݷ�����xml�ļ����ļ���
     */
    private static String xmlName="";

    //private static FtpClient ftp = new FtpClient();
    
    /**
     * ������ѡ����㲿������
     */
    protected static  Collection coll;
    
//	CCBegin SS2
	//�����汾����
	public static HashMap scbb = new HashMap();
//	CCEnd SS2
//	CCBegin SS3
	public static Collection djbom = null;
//	CCEnd SS3
    /**
     * added by dikefeng 20100422��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
     */
    protected static Vector filterParts;
    /*static{
        //  2��ȡerp������Ϣ
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
     * ȱʡ���캯����
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
	//�����汾����
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
     * added by dikefeng  20100423,��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ
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
     * ��ȡXML�ļ��Ƿ񷢲��ı�ʶ��
     * @return
     */
    public static boolean getPublishFlag()
    {
        return publishFlag;
    }

    /**
     * ����XML�ļ��Ƿ񷢲��ı�ʶ��
     * @param publishFlag
     */
    public static void setPublishFlag(boolean publishFlag)
    {
        BaseDataPublisher.publishFlag = publishFlag;
    }

    /**
     * ���������ڿͻ���Զ�̵��÷���˷���
     *
     * @param serviceName
     *            String Ҫ���õķ���������
     * @param methodName
     *            String Ҫ���õķ��񷽷�����
     * @param paraClass
     *            Class[] Ҫ���õķ��񷽷��Ĳ���������
     * @param paraObject
     *            Object[] Ҫ���õķ��񷽷��Ĳ���ֵ
     * @return Object ��õ�ֵ����
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
     * ��֯�������̣�ִ������׼���ͷ�����
     * @param publishSourseObject ����֪ͨ�����Ĳ���֪ͨ�顣
     * @return String XML�ļ����ơ�
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
        //�ж�Ҫ�����������ǲ���֪ͨ�黹�Ǹ���֪ͨ���������Ǹ��Ĳ���֪ͨ���������ǹ�������
        if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
        {
            publishFileName = ((GYBomAdoptNoticeIfc) publishSourseObject)
                    .getAdoptnoticenumber();
            typeName = "����BOM֪ͨ��";
            propertyFileName = "publish_data.xml";
            publishFathName=(String) RemoteProperty.getProperty("pathName");
            publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        
        else if(publishSourseObject instanceof ManagedBaselineIfc)
        {
        	 publishFileName = ((ManagedBaselineIfc) publishSourseObject).getBaselineNumber();
        	 typeName = "����";
        	 propertyFileName = "publish_data.xml";
        	 publishFathName=(String) RemoteProperty.getProperty("pathName");
        	 publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
            publishFileName = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
            typeName = "·��";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("routePathName");
            publishFTPPathName = RemoteProperty.getProperty("erpPath");
        }else{
        	publishFileName = "";
            typeName = "���������";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("pathName");
            publishFTPPathName = RemoteProperty.getProperty("erpPath");
        }
        //"��ʼ��֯�������ݣ�����ԴΪ��**��"
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
                //�����㲿��������Ϣ���ṹ��Ϣ�����������Ϣ��
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
                              //"û����Ҫ�������㲿�����ݣ�"
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
                        //ȥ��saveFilterPart������Ŵ˷���û��
                       // publisher.saveFilterPart();
                    }
                }
                else
                {
                    //"������Ϊ�գ�����ȡ����"
                    logger.fatal(Messages.getString("Util.10")); //$NON-NLS-1$
                    throw new QMException(Messages.getString("Util.10"));
                }
            }
            else
            {
                //"����ԴΪ�գ�����ȡ����"
                logger.fatal(Messages.getString("Util.11")); //$NON-NLS-1$
                throw new QMException(Messages.getString("Util.11"));
            }
        }
        catch (QMException e)
        {
            //"����ʧ�ܣ�"
            logger.error(simple.format(new Date())
                    + Messages.getString("Util.0"), e); //$NON-NLS-1$
            logger.fatal(simple.format(new Date())
                    + Messages.getString("Util.0"), e); //$NON-NLS-1$
            throw new QMException(e);
        }
        //"�����֯�������ݣ�"
        logger.fatal(simple.format(new Date()) + Messages.getString("Util.27")); //$NON-NLS-1$
        if(publishFlag)
        {
            //�����ļ����ơ�Ϊ�ϴ�ʱ��ȡ��Ӧ���Ƶ�XML�ļ���׼����
            FileNameLinkIfc link = new FileNameLinkInfo();
           // System.out.println("link="+link.getClass());
            link.setFileName(fileName);
            link.setNotice(publishSourseObject.getBsoID());
            pservice.saveValueInfo(link);
            //System.out.println("link="+link);
            //�ϴ��ļ���������
            //transmit(fileName, publishSourseObject);
        }
        else
        {
            //"û����Ҫ�������㲿�����ݣ�����ȡ����"
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
     * �ɾ�������ʵ�֡�
     * @throws QMXMLException
     */
    protected void saveFilterPart() throws QMXMLException
    {
    }

    /**
     * �ɾ�������ʵ�֡����ھ���ķ��������ɸ����͵ķ������ĸ÷���ʵ�֡�
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
    	 SimpleDateFormat   simple = new SimpleDateFormat("yyyyMMdd-HHmmss "); //����ʱ��
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
     * ��ʼ�������ļ��еġ�description��XMLԪ�ء�
     * @param publishSourseObject ����֪ͨ�����Ĳ���֪ͨ�顣
     */
    private static final void initDescData(
            final BaseValueIfc publishSourseObject)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("initDescData(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        descData = new QMXMLDesc();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd"); //����ʱ��
        String publishTime = simple.format(new Date());
        String fileNumber = "";
       // System.out.println("publishSourseObject********************"+publishSourseObject);
     if(publishSourseObject instanceof GYBomAdoptNoticeIfc)
        {
        	 String nu = getXmlName();
           //  descData.setType(nu);
             descData.setType("����BOM���Ĳ��õ�");
             //�ļ����ƣ�ʱ��+·�߱��   ������
             simple = new SimpleDateFormat("yyyyMMdd-HHmmss "); //����ʱ��
             String stime=simple.format(new Date());
             fileNumber = stime + "-" + ((GYBomAdoptNoticeIfc)publishSourseObject).getAdoptnoticenumber();
            // System.out.println("fileNumber********************"+fileNumber);
         descData.setFileNumber(fileNumber);
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
        	 String nu = getXmlName();  
             descData.setType(nu);
             //�ļ����ƣ�ʱ��+·�߱��   ������
             simple = new SimpleDateFormat("yyyyMMdd-HHmmss "); //����ʱ��
             String stime=simple.format(new Date());
             fileNumber = stime + "-" +((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
           //  System.out.println("fileNumber111********************"+fileNumber);
         descData.setFileNumber(fileNumber);
        }

        else 
        {
        	String nu = getXmlName();
             
             
             descData.setType(nu);
             //�ļ����ƣ�ʱ��+·�߱��   ������
             fileNumber = nu ;
        // System.out.println("nu********************"+nu);
         descData.setFileNumber(fileNumber);
        }
        simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss "); //����ʱ��
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
     * ��ʼ�����ݷ���������󼯺ϡ�
     * @return List ���ݷ������༯�ϡ�
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
                    //"ʵ������Ϊ*����ʱ����"
                    logger.error(Messages.getString(
                            "Util.1", new Object[]{publisher}), e); //$NON-NLS-1$
                    throw new QMXMLException(e);
                }
                catch (final IllegalAccessException e)
                {
                    //"������Ϊ*����ʱ����"
                    logger.error(Messages.getString(
                            "Util.2", new Object[]{publisher}), e); //$NON-NLS-1$
                    throw new QMXMLException(e);
                }
                catch (final ClassNotFoundException e)
                {
                    //"��Ϊ*������ϵͳ�в����ڣ�"
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
     * ��ʽ���������ļ���
     * @param publishSourseObject ����֪ͨ�����Ĳ���֪ͨ�顣
     * @return String XML�ļ����ơ�
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
        //�����ļ�������
        String fileName = "";
        try
        {
            //"��ʼ����XML�ĵ���"
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
            //�����ж��ļ��Ƿ���ڣ���������򲻴������Ҳ����������򴴽�һ����
            if(file.exists()){
                //"���ļ�*�Ѿ����ڣ�����ʧ�ܣ�"
                logger.fatal(Messages.getString("Util.53", new Object[]{fileName})); //$NON-NLS-1$
                publishFlag=false;
            }
            else{
            	fileName = creator.createDoc(publishSourseObject, dataList,newPublishFathName);
            	fileName=replaceStr(fileName);
                file=new File(newPublishFathName+fileName);
                String len = Long.toString(file.length());
                //"�ɹ���������Ϊ*��XML�ĵ����ļ���СΪ* �ֽڣ�������Ӧ�÷�����*·���£�"
                logger.fatal(Messages.getString("Util.25", new Object[]{fileName,
                        len, newPublishFathName}));
            }
        }
        catch (IOException e)
        {
            //"����XML�ļ�ʧ�ܣ�"
            logger.fatal(Messages.getString("Util.4"), e); //$NON-NLS-1$
            throw new QMXMLException(e);
        }
        catch (QMException e)
        {
            //"����XML�ļ�ʧ�ܣ�"
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
     * �������ݡ�
     * @param filename XML�ļ����ơ�
     * @param publishSourseObject ����֪ͨ�顣
     * @return boolean �ɹ���ʶ��
     * @throws QMException
     */
    public static  String replaceStr(String fileName ) { 
    System.out.println("fileName1==="+fileName);
    String aa ="";
    if(fileName.contains("ǰ׼")){
    	System.out.println("fileName3==="+fileName);
    	 aa = fileName.replace("ǰ׼", "QZ");
    }else{
    	aa = fileName;
    }
    System.out.println("fileName2==="+aa);
    return aa;
   
    }
    //CCEnd SS3

    /**
     * �������ݡ�
     * @param filename XML�ļ����ơ�
     * @param publishSourseObject ����֪ͨ�顣
     * @return boolean �ɹ���ʶ��
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
        //��ʼ�ϴ�ʱ�䡣
        String publishTime = simple.format(new Date());
        //"*��ʼ�ϴ��ļ���"
        logger.fatal(Messages.getString("Util.36", new Object[]{publishTime})); //$NON-NLS-1$
        //1 ��ȡ������xml�����ļ�
        //  2��ȡerp������Ϣ
        final String server = (String) RemoteProperty.getProperty("erpServer");
        //"Ŀ��FTP������Ϊ*��"
        logger.fatal(Messages.getString("Util.37", new Object[]{server})); //$NON-NLS-1$
        //final String port0 = (String) RemoteProperty.getProperty("erpPort");
        //final int port = Integer.valueOf(port0).intValue();
        final String user = (String) RemoteProperty.getProperty("erpUser");
        final String pass = (String) RemoteProperty.getProperty("erpPassword");
        //20071113 zhangq add for erp begin:�����ϴ��ģƣԣз������ļ���
        //final String publishFTPPathName = (String) RemoteProperty.getProperty("erpPath");
        //20071113 zhangq add for erp end
        //3 ����ftp������
        //20080219 zhangq begin
//        final FtpClient ftp = new FtpClient();
        try
        {
//            ftp.openServer(server, port);
            //4��½������ͨ���û��������½��ָ����FTP��������ȥ�������ػ�ӭ��Ϣ
            ftp.login(user, pass);
            //20080219 zhangq end
            //5 ���xml����·�����ļ�����
            //properties
            //String publishFathName = (String) RemoteProperty.getProperty("pathName");
            if(!publishFathName.endsWith(endString))
            {
                publishFathName += endString;
            }
            PromulgateNotifyService pnserivce=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
            //��ftp�ϴ�����ļ�����ԭ�ļ�����ͬ��ͬΪfilename��������
            String name = pnserivce.getFileNameByNotice(publishSourseObject);
            logger.fatal(Messages.getString("Util.41", new Object[]{name})); //$NON-NLS-1$
            if(!ftp.serverIsOpen())
            {
                //"���������Ӳ����ã�ȡ���ϴ��ļ�������"
                logger.fatal(Messages.getString("Util.32")); //$NON-NLS-1$
                return false;
            }
            //20071113 zhangq add for erp begin:�����ϴ��ģƣԣз������ļ���
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
                    //"�ϴ��ļ�Ϊ�գ�ȡ���ϴ��ļ�������"
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
            //"�ϴ��ļ���СΪ*��"
            logger.fatal(Messages.getString(
                    "Util.52", new Object[]{Long.toString(len)})); //$NON-NLS-1$
            //"*����ϴ��ļ���"
            logger.fatal(Messages.getString(
                    "Util.43", new Object[]{publishTime})); //$NON-NLS-1$
            logger.fatal("");
            //"����ϴ��ļ���"
            //                logger.info(Messages.getString("Util.30") + newline); //$NON-NLS-1$
        }
        catch (IOException ex)
        {
            //"�ϴ��ļ�ʧ�ܣ�"
            logger.fatal(Messages.getString("Util.31"), ex);
            logger.fatal("");
            throw new QMException(ex);
        }
        //last ���²��ñ�ʶ
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
                //"���·�����ʶ����"
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
     * ���ɱ������ĸ����������֪ͨ��.html��
     * @param changerequest �������
     * @throws QMException
     */
    public static final void obtainDadaForChange(BaseValueIfc changerequest)
            throws QMException
    {
        if(logger.isInfoEnabled())
        {
            //"��ʼ���ӱ��֪ͨ��.html��"
            logger.info(Messages.getString("Util.47")); //$NON-NLS-1$
        }
        ChangeDataPublisher publisher = new ChangeDataPublisher();
        if(changerequest instanceof QMChangeRequestIfc)
            publisher.setCompareData((QMChangeRequestIfc) changerequest);
        if(logger.isInfoEnabled())
        {
            //"��ɸ��ӱ��֪ͨ��.html��"
            logger.info(Messages.getString("Util.48")); //$NON-NLS-1$
        }
    }

    /**
     * ����˵��Ԫ�ص�ע����Ϣ��
     * @param note ע����Ϣ��
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
        //      �������ݡ�
        //            descData.setFileNumber(notifyIfc.getPromulgateNotifyNumber());
        //            descData.setType(notifyIfc.getPromulgateNotifyType());
        //            descData.setSourcetag("��");
        //            descData.setDate("��");
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
     * �������ݡ�
     * @param filename XML�ļ����ơ�
     * @param publishSourseObject ����֪ͨ�顣
     * @return boolean �ɹ���ʶ��
     * @throws QMException
     */
    /*public final static boolean transmit(BaseValueIfc publishSourseObject)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("transmit(String, BaseValueIfc) - start"); //$NON-NLS-1$
        }
        //"��ʼ�ϴ��ļ���"
        logger.info(Messages.getString("Util.35")); //$NON-NLS-1$
        final SimpleDateFormat simple = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.S z");
        //��ʼ�ϴ�ʱ�䡣
        String publishTime = simple.format(new Date());
        //"��ʼ�ϴ��ļ�ʱ��Ϊ*��"
        logger.info(Messages.getString("Util.36", new Object[]{publishTime})); //$NON-NLS-1$
        //1 ��ȡ������xml�����ļ�
        //  2��ȡerp������Ϣ
        final String server = (String) RemoteProperty.getProperty("erpServer");
        //"FTP���������ƻ�IP��ַΪ*��"
        logger.info(Messages.getString("Util.37", new Object[]{server})); //$NON-NLS-1$
        final String port0 = (String) RemoteProperty.getProperty("erpPort");
        final int port = Integer.valueOf(port0).intValue();
        //"FTP�������˿ں�Ϊ*��"
        logger.info(Messages.getString(
                "Util.38", new Object[]{Integer.toString(port)})); //$NON-NLS-1$
        final String user = (String) RemoteProperty.getProperty("erpUser");
        //"FTP�û���Ϊ*��"
        logger.info(Messages.getString("Util.39", new Object[]{user})); //$NON-NLS-1$
        final String pass = (String) RemoteProperty.getProperty("erpPassword");
        //"FTP����Ϊ*��"
        logger.info(Messages.getString("Util.40", new Object[]{pass})); //$NON-NLS-1$
        //3 ����ftp������
        //20080219 zhangq begin
//        final FtpClient ftp = new FtpClient();
        try
        {
//            ftp.openServer(server, port);
            //4��½������ͨ���û��������½��ָ����FTP��������ȥ�������ػ�ӭ��Ϣ
            ftp.login(user, pass);
            //20080219 zhangq end
            //5 ���xml����·�����ļ�����
            //properties
            final String pathname = (String) RemoteProperty
                    .getProperty("pathName");
            //"�ϴ��ļ��ı���·��Ϊ*��"
            logger.info(Messages.getString("Util.45", new Object[]{pathname})); //$NON-NLS-1$
            //"�ϴ��ļ�����Ϊ*��"
           if(!ftp.serverIsOpen())
            {
                //"���������Ӳ����ã�ȡ���ϴ��ļ�������"
                logger.warn(Messages.getString("Util.32") + "\n"); //$NON-NLS-1$
                return false;
            }
            ftp.binary();
            TelnetOutputStream os = null;
            FileInputStream is = null;
            File file_in = null;
            try
            {
                //��ftp�ϴ�����ļ�����ԭ�ļ�����ͬ��ͬΪfilename��������
            	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
            	String name=(String)pnservice.getFileNameByNotice(publishSourseObject);
                os = ftp.put(name);
                file_in = new File(pathname + name);
                if(file_in != null && file_in.length() == 0)
                {
                    //"�ϴ��ļ�Ϊ�գ�ȡ���ϴ��ļ�������"
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
                //"����ϴ��ļ�ʱ��Ϊ*��"
                logger.info(Messages.getString(
                        "Util.43", new Object[]{publishTime})); //$NON-NLS-1$
                //"����ϴ��ļ���"
                logger.info(Messages.getString("Util.30") + "\n"); //$NON-NLS-1$
            }
        }
        catch (IOException ex)
        {
            //"�ϴ��ļ�ʧ�ܣ�"
            logger.error(Messages.getString("Util.31"), ex);
            throw new QMException(ex);
        }
        //last ���²��ñ�ʶ
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
                //"���·�����ʶ����"
                logger.error(Messages.getString("Util.42"), e);
                throw new QMException(e);
            }
        }
        else if(publishSourseObject instanceof QMChangeRequestIfc)
        {
            //�շ�����
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("transmit(String, BaseValueIfc) - end"); //$NON-NLS-1$
        }
        return true;
    }*/

    /**
     * ת���ַ��������ַ����е�'/'����'\\'ת��Ϊ'-'��
     * @param str �ַ�����
     * @return String ת������ַ�����
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
     *�ı䷢���ļ����ļ��е�·����
     */
    private static String changePublishPath(String publishFathName)
            throws QMException
    {
        //ERP�û�Ҫ����־�ļ���ŵ���ǰ�û����ļ�����
        //RequestHelper requestHelper = new RequestHelper();
        //Class[] theClass = {};
        //Object[] theObjs = {};
        // ��õ�ǰ�û�ID
        //UserIfc curUserIfc = (UserIfc) requestHelper.request("SessionService",
        //        "getCurUserInfo", theClass, theObjs);
        //return publishFathName + "/" + curUserIfc.getUsersName();
        return publishFathName;
    }
}

/**
 * ���ɳ���BaseDataPublisher.java	1.0              2006-11-6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ʱ����ո� ������ 2014-9-2
 * SS4 �ļ����Ƴ��֡�ǰ׼��ftp�޷�ʶ�����ڽ�ŷ������������������Խ���ǰ���滻Ϊ��QZ�� 2015-12-01
 * SS5 ���ļ���Ȩ ������ 2016-10-11
 * SS6 ��������滻ǰ�������滻ǰ��������鹦�� ������ 2017-3-15
 * SS7 �⹺�������Ƽ��ֶѴ��� ������ 2018-1-8
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
    //CCBegin SS6
   public static HashMap partMapnew = new HashMap();
   public static Vector cxvec = new Vector();
    //CCEnd SS6
    /**
     * ������ѡ����㲿������
     */
    protected static  Collection coll;
    

	public static Collection djbom = null;
	
	public static int lsh = 0;
	//�Ƿ�ֶ�
	public static int fd = 0;
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
            publishFathName=(String) RemoteProperty.getProperty("cdpathName");
            publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
            partMapnew = getAdoptPartlink((GYBomAdoptNoticeIfc)publishSourseObject);
        }
        
        else if(publishSourseObject instanceof ManagedBaselineIfc)
        {
        	 publishFileName = ((ManagedBaselineIfc) publishSourseObject).getBaselineNumber();
        	 typeName = "����";
        	 propertyFileName = "publish_data.xml";
        	 publishFathName=(String) RemoteProperty.getProperty("cdpathName");
        	 publishFTPPathName=(String) RemoteProperty.getProperty("erpPath");
        }
        else if(publishSourseObject instanceof TechnicsRouteListIfc)
        {
            publishFileName = ((TechnicsRouteListIfc)publishSourseObject).getRouteListNumber();
            typeName = "·��";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("cdpathName");
            publishFTPPathName = RemoteProperty.getProperty("erpPath");
        }else{
        	publishFileName = "";
            typeName = "���������";
            propertyFileName = "publish_data.xml";
            publishFathName = RemoteProperty.getProperty("cdpathName");
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
                 //  System.out.println("dataList----------------------"+dataList);
                 //  System.out.println("xmlPartList----------------------"+xmlPartList);
                //  System.out.println("xmlPartListresult----------------------"+xmlPartList);
                    

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
             //�����ˮ����0��˵��������������ܣ���.���ҵ���ԭ�����������������ط�����
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
            //��ˮ�Ų���ʹ�����ɲ���
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
            //�����ж��ļ��Ƿ���ڣ���������򲻴������Ҳ����������򴴽�һ����
            if(file.exists()){
                //"���ļ�*�Ѿ����ڣ�����ʧ�ܣ�"
                logger.fatal(Messages.getString("Util.53", new Object[]{fileName})); //$NON-NLS-1$
                publishFlag=false;
            }
            else{
            	fileName = creator.createDoc(publishSourseObject, dataList,newPublishFathName,lsh);
            	fileName=replaceStr(fileName);

                file=new File(newPublishFathName+fileName);
                //CCBegin SS5
               // System.out.println("���ļ�"+publishFathName+fileName+"��Ȩ");
             
                //��һ�����������Լ��Ƿ���Ȩ�ޣ��ڶ��������Ƿ�ֻ���Լ���Ȩ�ޣ�false�������û�����Ȩ��
                file.setExecutable(true,false);//���ÿ�ִ��Ȩ��  
                file.setReadable(true,false);//���ÿɶ�Ȩ��  
                file.setWritable(true,false);//���ÿ�дȨ��
                //CCEnd SS5
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
        fd=0;
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
   // System.out.println("fileName1==="+fileName);
    String aa ="";
    if(fileName.contains("ǰ")){
    	System.out.println("fileName3==="+fileName);
    	 aa = fileName.replace("ǰ", "QZ");
    }else{
    	aa = fileName;
    }
    aa = aa.replaceAll(" ", "");
   // System.out.println("fileName2==="+aa);
    return aa;
   
    }
    //CCEnd SS3

  

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
//  CCBegin SS6
    /**
     * ��ȡ���ü����滻ǰ����������ŵ���Ϣ
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
//	    	��ȡ�������
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
    		//��ȡ����������
    	
    	    HashMap bmap =  new HashMap();
    		for(Iterator ite= col.iterator();ite.hasNext();){
    			GYBomAdoptNoticePartLinkIfc plinks = (GYBomAdoptNoticePartLinkIfc)ite.next();
    			//System.out.println("plinks==="+plinks);
    			if(plinks.getAdoptBs().equals("������")){    	
    				String[] badoptParts = new String[5];
    			    
    				String bbsoid = plinks.getPartID();//�滻ǰbsoid
    				badoptParts[0]  = plinks.getSl();//����
    				badoptParts[1]  = plinks.getPartNumber();
    				badoptParts[2]  = plinks.getVersionValue();
    				badoptParts[3]  = plinks.getBz3();//����
    				badoptParts[4]  = plinks.getBz2();//����
    				bmap.put(bbsoid, badoptParts);
//    				System.out.println("1111111111bbsoid==="+bbsoid);
//    				System.out.println("badoptParts[0]==="+badoptParts[0]);
//    				System.out.println("1111111111badoptParts[0]==="+badoptParts[0]);
    			}
    		}
    	//��ȡ��������
    		for(Iterator itee= col.iterator();itee.hasNext();){
    			GYBomAdoptNoticePartLinkIfc links = (GYBomAdoptNoticePartLinkIfc)itee.next();
    			
    			if(links.getAdoptBs().equals("����")){
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
							// TODO �Զ����� catch ��
							e.printStackTrace();
						}
    					if(linkPart!=null){
    						String bpartNumber = partHelper.getMaterialNumber(linkPart,linkPart.getVersionID());
    						adoptParts[3] = bpartNumber;//�����ü����
    						adoptParts[4] = linkPart.getVersionID();//�����ü��汾
    					}else{
    						adoptParts[3] = "";
    						adoptParts[4] = "";
    					}
    				}else{
    					adoptParts[3] ="";
    					adoptParts[4] = "";
    				}
    				String parentId= links.getBz2();//����bsoid
    				String parentNumber = "";
    				if(parentId!=null&&!parentId.equals("")){
    					QMPartIfc pPart =(QMPartIfc)service.refreshInfo(parentId);
    					parentNumber = partHelper.getMaterialNumber(pPart,pPart.getVersionID());
    				}
    				adoptParts[5] = parentNumber;
    				String subgroup = links.getBz3();//����
    				if(subgroup==null||subgroup.equals("null")){
    					adoptParts[6] = "";
    				}else{
    					adoptParts[6] = subgroup;
    				}
    				
    				String bbsion = links.getLinkPart();//�滻ǰ����������bsoid
    				//ͨ���滻�Ի�ȡԭbsoid
    				String[] badopt= (String[])bmap.get(bbsion);
//    				System.out.println("1111111111bmap==="+bmap);
//    				System.out.println("1111111111bbsion==="+bbsion);
//    				System.out.println("1111111111badopt==="+badopt);
    				
    				if(badopt!=null){
    					adoptParts[7] = badopt[0];//�滻ǰ����
    				}else{
    					adoptParts[7] = "0";
    				}
    				
//    				System.out.println("�滻ǰ����==="+adoptParts[7]);
//    				System.out.println("�滻������==="+adoptParts[2]);
    				//��ȡ���ù������
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
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
    	return adoptMap;
    	}

    //CCEnd SS6
  
}

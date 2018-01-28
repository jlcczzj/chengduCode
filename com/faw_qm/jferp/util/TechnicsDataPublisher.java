/**
 * ���ɳ���TechnicsDataPublisher.java	1.0              2007-10-31
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;

/**
 * <p>Title: �������ݷ�������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class TechnicsDataPublisher extends BaseDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(TechnicsDataPublisher.class);

    /**
     * 
     */
    public TechnicsDataPublisher()
    {
    }

    /**
     * ʵ�ָ��෽�������ھ���ķ��������ɸ����͵ķ������ĸ÷���ʵ�֡�
     * @throws QMXMLException
     */
    protected void invoke() throws QMXMLException
    {
        logger.debug("publishSourseObject is " + publishSourseObject);
        if(publishSourseObject instanceof QMFawTechnicsIfc)
        {
            QMFawTechnicsIfc techIfc = (QMFawTechnicsIfc) publishSourseObject;
            //���չ����Ĺ�����Ϣ��
            List resultList = new ArrayList();
            //���չ����Ĺ�����Ϣ��
            List xmlProcessList = new ArrayList();
            //���չ������㲿����������Ϣ��
            List xmlProPartList = new ArrayList();
            //����������㲿����������Ϣ��
            List xmlStepPartList = new ArrayList();
            //����������豸��Ϣ��
            List xmlStepEquipList = new ArrayList();
            //��������Ĺ�װ��Ϣ��
            List xmlStepToolList = new ArrayList();
            //���չ�������Ҫ������Ϣ��
            List xmlRawMatList = new ArrayList();
            //��������ĸ���������Ϣ��
            List xmlAstMatList = new ArrayList();
            boolean hasCycle = false;
            try
            {
                resultList = (List) RequestHelper.request("TechnicDataService",
                        "getTechnicsData", new Class[]{String.class},
                        new Object[]{techIfc.getBsoID()});
                //��ȡ���չ����Ĺ�����Ϣ��
                xmlProcessList = (List) resultList.get(0);
                //��ȡ���չ������㲿����������Ϣ��
                xmlProPartList = (List) resultList.get(1);
                //��ȡ����������㲿����������Ϣ��
                xmlStepPartList = (List) resultList.get(2);
                //��ȡ����������豸��Ϣ��
                xmlStepEquipList = (List) resultList.get(3);
                //��ȡ��������Ĺ�װ��Ϣ��
                xmlStepToolList = (List) resultList.get(4);
                //��ȡ���չ�������Ҫ������Ϣ��
                xmlRawMatList = (List) resultList.get(5);
                //��ȡ��������ĸ���������Ϣ��
                xmlAstMatList = (List) resultList.get(6);
                hasCycle = ((Boolean) resultList.get(7)).booleanValue();
            }
            catch (QMException e)
            {
                //"��ȡ������ص���Ϣʱ����"
                logger.error(Messages.getString("Util.82"), e);
                throw new QMXMLException(e);
            }
            if(xmlProPartList.size() <= 0)
            {
                BaseDataPublisher.setPublishFlag(false);
            }
            else
            {
                //�����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
                setDataRecord(xmlProcessList, xmlProPartList, xmlStepPartList,
                        xmlStepEquipList, xmlStepToolList, xmlRawMatList,
                        xmlAstMatList);
                for (int j = 0; j < dataList.size(); j++)
                {
                    final QMXMLData data = (QMXMLData) dataList.get(j);
                    if(data instanceof QMXMLDesc)
                    {
                        if(hasCycle)
                        {
                            //"�л�ͷ��"
                            ((QMXMLDesc) data).setType(Messages
                                    .getString("Util.83"));
                        }
                        else
                        {
                            //"û�л�ͷ��"
                            ((QMXMLDesc) data).setType(Messages
                                    .getString("Util.84"));
                        }
                    }
                }
            }
        }
    }

    /**
     * �����������Ϣ�ֱ�����Ӧ��QMXMLData�У������õ�dataList�С�
     * @param xmlMaterialSplitList ���˺�xmlPartList��
     * @param xmlStructureList ���˺�xmlStructureList��
     * @throws QMXMLException 
     */
    private final void setDataRecord(final List xmlProcessList,
            final List xmlProPartList, final List xmlStepPartList,
            final List xmlStepEquipList, final List xmlStepToolList,
            final List xmlRawMatList, final List xmlAstMatList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - start"); //$NON-NLS-1$
            logger.debug("xmlProcessList is " + xmlProcessList.size());
            logger.debug("xmlProPartList is " + xmlProPartList.size());
            logger.debug("xmlStepPartList is " + xmlStepPartList.size());
            logger.debug("xmlStepEquipList is " + xmlStepEquipList.size());
            logger.debug("xmlStepToolList is " + xmlStepToolList.size());
            logger.debug("xmlRawMatList is " + xmlRawMatList.size());
            logger.debug("xmlAstMatList is " + xmlAstMatList.size());
        }
        if(publishSourseObject instanceof QMFawTechnicsIfc)
        {
        	String timeString="";
        	String workCenString="";
            for (int j = 0; j < dataList.size(); j++)
            {
                final QMXMLData data = (QMXMLData) dataList.get(j);
                if(logger.isDebugEnabled())
                {
                    logger.debug("data.getName==" + data.getName());
                }
                List tempList = new ArrayList();
                if(data.getName().equals("process"))
                {
                    for (int i = 0; i < xmlProcessList.size(); i++)
                    {
                        QMXMLProcess xmlProcess = (QMXMLProcess) xmlProcessList
                                .get(i);
                        xmlProcess.setPropertyList(data.getPropertyList());
                        tempList.add(xmlProcess.getRecord());
                        //20080129 begin
                        if((xmlProcess.getAssistant()+xmlProcess.getCureTime())<=0){
                        	timeString+="\""+((QMFawTechnicsIfc)publishSourseObject).getTechnicsNumber()+"\"������\""+
                        	xmlProcess.getStepName()+"\"����Ĺ�ʱΪ�㡣\n";
                        }
                        if(xmlProcess.getWorkCenter()==null||xmlProcess.getWorkCenter().length()<=0){
                        	workCenString+="\""+((QMFawTechnicsIfc)publishSourseObject).getTechnicsNumber()+"\"������\""+
                        	xmlProcess.getStepName()+"\"����û�й������ġ�\n";
                        }
                        //20080129 end
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
                else if(data.getName().equals("part"))
                {
                    for (int i = 0; i < xmlProPartList.size(); i++)
                    {
                        QMXMLProcessPart xmlProPart = (QMXMLProcessPart) xmlProPartList
                                .get(i);
                        xmlProPart.setPropertyList(data.getPropertyList());
                        tempList.add(xmlProPart.getRecord());
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
                else if(data.getName().equals("subpart"))
                {
                    for (int i = 0; i < xmlStepPartList.size(); i++)
                    {
                        QMXMLStepPart xmlStepPart = (QMXMLStepPart) xmlStepPartList
                                .get(i);
                        xmlStepPart.setPropertyList(data.getPropertyList());
                        tempList.add(xmlStepPart.getRecord());
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
                else if(data.getName().equals("equipment"))
                {
                    for (int i = 0; i < xmlStepEquipList.size(); i++)
                    {
                        QMXMLStepEquip xmlStepEqui = (QMXMLStepEquip) xmlStepEquipList
                                .get(i);
                        xmlStepEqui.setPropertyList(data.getPropertyList());
                        tempList.add(xmlStepEqui.getRecord());
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
                else if(data.getName().equals("tool"))
                {
                    for (int i = 0; i < xmlStepToolList.size(); i++)
                    {
                        QMXMLStepTool xmlStepTool = (QMXMLStepTool) xmlStepToolList
                                .get(i);
                        xmlStepTool.setPropertyList(data.getPropertyList());
                        tempList.add(xmlStepTool.getRecord());
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
                else if(data.getName().equals("rawMaterial"))
                {
                    for (int i = 0; i < xmlRawMatList.size(); i++)
                    {
                        QMXMLRawMaterial xmlRawMat = (QMXMLRawMaterial) xmlRawMatList
                                .get(i);
                        xmlRawMat.setPropertyList(data.getPropertyList());
                        tempList.add(xmlRawMat.getRecord());
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
                else if(data.getName().equals("assistantMaterial"))
                {
                    for (int i = 0; i < xmlAstMatList.size(); i++)
                    {
                        QMXMLAstMaterial xmlAstMat = (QMXMLAstMaterial) xmlAstMatList
                                .get(i);
                        xmlAstMat.setPropertyList(data.getPropertyList());
                        tempList.add(xmlAstMat.getRecord());
                    }
                    data.setRecordList(tempList);
                    setDescNote(Messages.getString("Util.34", new Object[]{
                            Integer.toString(tempList.size()), data.getName()}));
                    continue;
                }
            }
            if(timeString.length()>0){
    	        this.createTechnicsLogFile("techTimeInfo",timeString);
            }
            if(workCenString.length()>0){
       	        this.createTechnicsLogFile("techWorkCenterInfo",workCenString);
               }
            if(logger.isDebugEnabled())
            {
                logger.debug("setDataRecord(List, List) - end"); //$NON-NLS-1$
            }
        }
    }
    
	//20080129 begin
	/**
	 * ������techPathֵ���ļ����´�����Ӧ����־�ļ���
	 * @param fileDescName ���ļ�������Ϣ�����ļ����Ƶ�һ���֡�
	 * @param fileContent :�ļ����ݡ�
	 */
	public static void createTechnicsLogFile(String fileDescName,String fileContent){
  		 //String techPath = RemoteProperty.getProperty("techPath");
   		 try
   	        {
   		        String techPath = getTechPath();
   	            File f = new File(techPath);
   	            if(!f.exists())
   	            {
   	                f.mkdir();
   	            }
   	            Timestamp time = new Timestamp(System.currentTimeMillis());
   	            String timestr = time.toString();
   	            int r = timestr.indexOf(" ");
   	            timestr = timestr.substring(0, r);
   	            File f1 = new File(techPath + "/"+fileDescName+"-" + timestr + ".log");
   	            if(!f1.exists() || !f1.isFile())
   	            {
   	                f1.createNewFile();
   	            }
   	            PrintWriter out = new PrintWriter(new FileWriter(f1, true), true);
   	            StringBuffer buffer = new StringBuffer();
   	            final SimpleDateFormat simple = new SimpleDateFormat(
                   "yyyy-MM-dd HH:mm:ss ");
   	            buffer.append(simple.format(new Date()));
   	            buffer.append(fileContent);
   	            out.print(buffer);
   	            out.flush();
   	            out.close();
   	        }
   	        catch (Exception e)
   	        {
   	            logger.error("createTechnicsLogFile(String,String)", e); //$NON-NLS-1$
   	            e.printStackTrace();
   	        }
	}
	//20080129 end
	/**
	 * ��ȡ���շ�����־�ļ���·����
	 */
	private static String getTechPath() throws QMException
    {
        String techPath = RemoteProperty.getProperty("techPath");
        //	    //ERP�û�Ҫ����־�ļ���ŵ���ǰ�û����ļ�����
        //        RequestHelper requestHelper = new RequestHelper();
        //        Class[] theClass = {};
        //        Object[] theObjs = {};
        //        // ��õ�ǰ�û�ID
        //        UserIfc curUserIfc = (UserIfc) requestHelper.request("SessionService",
        //                "getCurUserInfo", theClass, theObjs);
        //        techPath=techPath+"/"+curUserIfc.getUsersName();
        return techPath;
    }
}

/**
 * 生成程序TechnicsDataPublisher.java	1.0              2007-10-31
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title: 工艺数据发布器。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
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
     * 实现父类方法。对于具体的发布类型由该类型的发布器的该方法实现。
     * @throws QMXMLException
     */
    protected void invoke() throws QMXMLException
    {
        logger.debug("publishSourseObject is " + publishSourseObject);
        if(publishSourseObject instanceof QMFawTechnicsIfc)
        {
            QMFawTechnicsIfc techIfc = (QMFawTechnicsIfc) publishSourseObject;
            //工艺关联的工序信息。
            List resultList = new ArrayList();
            //工艺关联的工序信息。
            List xmlProcessList = new ArrayList();
            //工艺关联的零部件的物料信息。
            List xmlProPartList = new ArrayList();
            //工序关联的零部件的物料信息。
            List xmlStepPartList = new ArrayList();
            //工序关联的设备信息。
            List xmlStepEquipList = new ArrayList();
            //工序关联的工装信息。
            List xmlStepToolList = new ArrayList();
            //工艺关联的主要材料信息。
            List xmlRawMatList = new ArrayList();
            //工序关联的辅助材料信息。
            List xmlAstMatList = new ArrayList();
            boolean hasCycle = false;
            try
            {
                resultList = (List) RequestHelper.request("TechnicDataService",
                        "getTechnicsData", new Class[]{String.class},
                        new Object[]{techIfc.getBsoID()});
                //获取工艺关联的工序信息。
                xmlProcessList = (List) resultList.get(0);
                //获取工艺关联的零部件的物料信息。
                xmlProPartList = (List) resultList.get(1);
                //获取工序关联的零部件的物料信息。
                xmlStepPartList = (List) resultList.get(2);
                //获取工序关联的设备信息。
                xmlStepEquipList = (List) resultList.get(3);
                //获取工序关联的工装信息。
                xmlStepToolList = (List) resultList.get(4);
                //获取工艺关联的主要材料信息。
                xmlRawMatList = (List) resultList.get(5);
                //获取工序关联的辅助材料信息。
                xmlAstMatList = (List) resultList.get(6);
                hasCycle = ((Boolean) resultList.get(7)).booleanValue();
            }
            catch (QMException e)
            {
                //"获取工艺相关的信息时出错！"
                logger.error(Messages.getString("Util.82"), e);
                throw new QMXMLException(e);
            }
            if(xmlProPartList.size() <= 0)
            {
                BaseDataPublisher.setPublishFlag(false);
            }
            else
            {
                //将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
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
                            //"有回头件"
                            ((QMXMLDesc) data).setType(Messages
                                    .getString("Util.83"));
                        }
                        else
                        {
                            //"没有回头件"
                            ((QMXMLDesc) data).setType(Messages
                                    .getString("Util.84"));
                        }
                    }
                }
            }
        }
    }

    /**
     * 将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
     * @param xmlMaterialSplitList 过滤后xmlPartList。
     * @param xmlStructureList 过滤后xmlStructureList。
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
                        	timeString+="\""+((QMFawTechnicsIfc)publishSourseObject).getTechnicsNumber()+"\"工艺下\""+
                        	xmlProcess.getStepName()+"\"工序的工时为零。\n";
                        }
                        if(xmlProcess.getWorkCenter()==null||xmlProcess.getWorkCenter().length()<=0){
                        	workCenString+="\""+((QMFawTechnicsIfc)publishSourseObject).getTechnicsNumber()+"\"工艺下\""+
                        	xmlProcess.getStepName()+"\"工序没有工作中心。\n";
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
	 * 在属性techPath值的文件夹下创建相应的日志文件。
	 * @param fileDescName ：文件描述信息，是文件名称的一部分。
	 * @param fileContent :文件内容。
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
	 * 获取工艺发布日志文件的路径。
	 */
	private static String getTechPath() throws QMException
    {
        String techPath = RemoteProperty.getProperty("techPath");
        //	    //ERP用户要求将日志文件存放到当前用户的文件夹下
        //        RequestHelper requestHelper = new RequestHelper();
        //        Class[] theClass = {};
        //        Object[] theObjs = {};
        //        // 获得当前用户ID
        //        UserIfc curUserIfc = (UserIfc) requestHelper.request("SessionService",
        //                "getCurUserInfo", theClass, theObjs);
        //        techPath=techPath+"/"+curUserIfc.getUsersName();
        return techPath;
    }
}

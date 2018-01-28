/**
 * 生成程序TechnicDataServiceEJB.java	1.0              2007-10-31
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.ejb.service;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureQMEquipmentLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMMaterialLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMPartLinkIfc;
import com.faw_qm.capp.model.QMProcedureQMToolLinkIfc;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.erp.exception.TechnicDataException;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.util.QMXMLAstMaterial;
import com.faw_qm.erp.util.QMXMLProcess;
import com.faw_qm.erp.util.QMXMLProcessPart;
import com.faw_qm.erp.util.QMXMLRawMaterial;
import com.faw_qm.erp.util.QMXMLStepEquip;
import com.faw_qm.erp.util.QMXMLStepPart;
import com.faw_qm.erp.util.QMXMLStepTool;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.resource.support.model.QMEquipmentIfc;
import com.faw_qm.resource.support.model.QMMaterialIfc;
import com.faw_qm.resource.support.model.QMToolIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttGroup;
import com.faw_qm.extend.util.ExtendAttModel;
import java.util.Vector;
import java.util.StringTokenizer;
import com.faw_qm.capp.model.PartUsageQMTechnicsLinkIfc;
import com.faw_qm.erp.util.RouteCodeIBAName;
import java.util.HashMap;


/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 徐春英
 * @version 1.0
 */
public class TechnicDataServiceEJB extends BaseServiceImp
{
    private static final long serialVersionUID = 1L;
    
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TechnicDataServiceEJB.class);
	

    public TechnicDataServiceEJB()
    {
    }


    /**获取服务名。
     * @return String
     */
    public String getServiceName()
    {
        return "TechnicDataService";
    }


    /**
     * 通过工艺值对象获得该工艺使用的零部件集合
     * @param technic 工艺值对象
     * @throws QMException
     * @return Collection
     */
    public Collection getPartsByTechnics(QMTechnicsIfc technic)
            throws QMException
    {
        Collection partCol = null;
        try
        {
            StandardCappService cappService = (StandardCappService)
                                              EJBServiceHelper
                                              .getService("StandardCappService");
            partCol = cappService.getPartsByTechnics(technic);
        }
        catch (QMException e)
        {
            throw new TechnicDataException(e);
        }
        return partCol;
    }


    /**获得零部件工艺关联集合
     * @param bsoID String 工艺卡id
     * @throws QMException
     * @return Collection  零部件工艺关联集合
     */
    public Collection getPartTechLinks(String bsoID)
            throws QMException
    {
        if (bsoID == null || bsoID.length() == 0)
        {
            throw new IllegalArgumentException("bsoID is null");
        }
        Collection resultCol = null;
        //判断传进来的对象是工艺卡还是工序卡/工步卡创建不同QMQuery进行查询
        PersistService service = (PersistService) EJBServiceHelper.
                                 getPersistService();
        QMQuery query = new QMQuery("PartUsageQMTechnicsLink");
        query.addCondition(new QueryCondition("rightBsoID",
                                              QueryCondition.EQUAL,
                                              bsoID));
        resultCol = service.findValueInfo(query);
        return resultCol;

    }


    /**
     * 根据工艺ID获得所使用的工序集合
     * @param bsoID String
     * @throws QMException
     * @return Collection
     */
    public Collection browseProceduresByTechnics(String bsoID)
            throws QMException
    {
        Collection col = null;
        try
        {
            StandardCappService cappService = (StandardCappService)
                                              EJBServiceHelper
                                              .getService("StandardCappService");
            //col = cappService.browseProceduresByTechnics(bsoID, false);
        }
        catch (QMException e)
        {
            throw new TechnicDataException(e);
        }
        return col;
    }


    /**
     * 由工艺卡id找到工艺使用材料的关联
     * @param tech techBsoID 工艺id
     * @throws CappException
     * @return Collection 工艺使用材料的关联的集合
     */
    public Collection findTechnicsMaterialsLinkByTech(String techBsoID)
            throws QMException
    {
        if (techBsoID == null)
        {
            throw new IllegalArgumentException("tech is null");
        }
        Collection resultCol = null;
        try
        {
            //获得持久化对象
            PersistService service = (PersistService) EJBServiceHelper
                                     .getPersistService();
            QMQuery query = new QMQuery("QMTechnicsQMMaterialLink");
            query.addCondition(new QueryCondition("leftBsoID",
                                                  QueryCondition.EQUAL,
                                                  techBsoID));
            resultCol = service.findValueInfo(query);
        }
        catch (QMException e)
        {
            throw new TechnicDataException(e);
        }
        return resultCol;
    }


    /**
     * 根据扩展属性名,获得业务对象的扩展属性值
     * @param extendattriedifc 可使用扩展属性的业务对象
     * @param attname 扩展属性名称
     * @return Object 指定扩展属性的属性值(对于成组的属性,返回主要数据值组的数据值)
     */
    protected Object getExtendAttValue(ExtendAttContainer container,
                                       String attname)
    {
        Object obj = null;
        ExtendAttModel model = null;
        //获得业务对象的扩展属性容器
        //ExtendAttContainer container = extendattriedifc.getExtendAttributes();
        if (container == null)
        {
            return null;
        }
        //从容器中获得指定的属性模型
        model = container.findExtendAttModel(attname);
        //如果非成组属性没有
        if (model == null)
        {
            //判断容器是否有成组属性
            if (container.isGroup())
            {
                ExtendAttGroup group = null;
                Iterator names = container.getAttGroupNames().iterator();
                Vector vec = new Vector();
                Vector groups;
                while (names.hasNext())
                {
                    groups = container.getAttGroups((String) names.next());
                    if (groups != null)
                    {
                        for (int i = 0; i < groups.size(); i++)
                        {
                            group = (ExtendAttGroup) groups.elementAt(i);
                            model = group.findExtendAttModel(attname);
                            if (model != null)
                            {
                                vec.add(wipeSemicolon(model.getAttValue()));
                            }
                        } //end for
                    }
                }
                if (vec.size() > 0)
                {
                    obj = vec;
                }
            } //end if

            return obj;
        } //end if
        if (model != null)
        {
            //obj = model.getAttValue();
            obj = wipeSemicolon(model.getAttValue());
        }
        return obj;
    }


    /**
     * 删除字符串之间的分号
     * @param s Object
     * @return String
     */
    protected Object wipeSemicolon(Object s)
    {
        String str = (String) s;
        if(str==null) {
            str="";
        }
        if (str.indexOf(";") == -1)
        {
            return s;
        }
        StringTokenizer st = new StringTokenizer(str, ";");
        StringBuffer sb = new StringBuffer();
        while (st.hasMoreTokens())
        {
            sb.append(st.nextToken());
        }
        return sb.toString();
    }

    private QMXMLProcess getXMLProcess(int stepNum, QMProcedureIfc procedure)
            throws QMException
    {
        QMXMLProcess processXml = new QMXMLProcess();
        try
        {
            PersistService service = (PersistService) EJBServiceHelper
                                     .getPersistService();
            processXml.setStepSerNumber(stepNum);
            processXml.setStepNumber(procedure.
                                     getDescStepNumber());
            processXml.setStepName(procedure.getStepName());
//            processXml.setRouteCode(procedure.getWorkShop().getCodeContent());
         //   processXml.setRouteCode(procedure.getWorkShop().getSearchWord());
            processXml.setStepCategory(procedure.
                                       getTechnicsType()
                                       .getCode());
            processXml.setImportantLevel(procedure
                                         .getStepClassification().
                                         getCode());
            processXml
                    .setStepType(procedure.getProcessType().
                                 getCode());
            processXml.setTimeRation(procedure.getStepHour());
            processXml.setCureTime(procedure.getMachineHour());
            processXml.setAssistant(procedure.getAidTime());
            ExtendAttContainer con = procedure.
                                     getProcessControl();
            String somebody;
            //somebody为扩展属性XML里配的定员属性名
            String str = (String) getExtendAttValue(con,
                    "somebody");
            if (str == null || str.equals("") || str.equals("0"))
            {
                somebody = "1";
            }
            else
            {
                somebody = str;
            }
            processXml.setOperatorNum(somebody);
            String workCenter = procedure.getName();
            String centerName = null;
            if (workCenter != null)
            {
                CodingIfc centerIfc = (CodingIfc) service.
                                      refreshInfo(
                        workCenter);
                centerName = centerIfc.getShorten();
            }
            processXml.setWorkCenter(centerName);
        }
        catch (QMException e)
        {
            throw new TechnicDataException(e);
        }

        return processXml;

    }


    /**
     * 将工艺下的工序信息写入到QMXMLProcess中
     * @param bsoID String
     * @throws QMException
     * @return ArrayList
     */
    public ArrayList getTechnicsData(String technicbsoID)
            throws QMException
    {
        //存放工艺相关信息的集合，为了生成XML时用
        ArrayList technicList = new ArrayList();
        //存放工艺关联的主要材料列表
        ArrayList mainMatList = new ArrayList();
        //存放工序信息的列表
        ArrayList processList = new ArrayList();
        //存放工序关联的设备信息列表
        ArrayList procedureEquipList = new ArrayList();
        //存放工序关联的工装信息列表
        ArrayList procedureToolList = new ArrayList();
        //存放工序关联的材料信息的列表
        ArrayList procedureMatList = new ArrayList();
        //存放工序关联的零部件信息的列表
        ArrayList procedurePartList = new ArrayList();
        //存放工艺关联的零部件的信息列表
        ArrayList processPartList = new ArrayList();
        //零部件是否有回头件的标识
        boolean flag = false;
        try
        {
            PersistService service = (PersistService) EJBServiceHelper
                                     .getPersistService();
            StandardCappService cappService = (StandardCappService)
                                              EJBServiceHelper
                                              .getService("StandardCappService");
            MaterialSplitService matSplitService = (MaterialSplitService)
                    EJBServiceHelper
                    .getService("MaterialSplitService");
            QMTechnicsIfc technic = (QMTechnicsIfc) service
                                    .refreshInfo(technicbsoID);
            //存放工序部门代码的集合
            ArrayList stepShops = new ArrayList();
            //存放工序编号的集合
            ArrayList stepDescNum = new ArrayList();
            //获得工艺关联的主要材料集合
            Collection mainMatCol = findTechnicsMaterialsLinkByTech(
                    technicbsoID);
            if (mainMatCol != null && mainMatCol.size() > 0)
            {
                for (Iterator mainMatIt = mainMatCol.iterator(); mainMatIt
                                          .hasNext(); )
                {
                    QMTechnicsQMMaterialLinkIfc mainLinkIfc = (
                            QMTechnicsQMMaterialLinkIfc) mainMatIt
                            .next();
                    String rightBsoID = ((QMTechnicsQMMaterialLinkIfc)
                                         mainLinkIfc)
                                        .getRightBsoID();
                    QMMaterialIfc matIfc = (QMMaterialIfc) service
                                           .refreshInfo(rightBsoID);
                    QMXMLRawMaterial mainMatXML = new QMXMLRawMaterial();
                    mainMatXML.setMatNumber(matIfc.getMaterialNumber());
                    mainMatXML.setMatName(matIfc.getMaterialName());
                    //20080429 begin
//                    mainMatXML.setMatBrand(matIfc.getMaterialCrision());
//                    mainMatXML.setMatSpecs(matIfc.getMaterialSpecial());
                    mainMatXML.setMatBrand(matIfc.getMaterialSpecial());
                    mainMatXML.setMatSpecs(matIfc.getMaterialState());
                    mainMatXML.setMaterialFunction(matIfc.getMaterialFunction());
                    mainMatXML.setMaterialCrision(matIfc.getMaterialCrision());
                    //20080429 end
                    
                    mainMatXML
                            .setMainCode(((QMTechnicsQMMaterialLinkIfc)
                                          mainLinkIfc)
                                         .getMmMark());
                    mainMatXML
                            .setRation(((QMTechnicsQMMaterialLinkIfc)
                                        mainLinkIfc)
                                       .getMRation());
                    mainMatList.add(mainMatXML);
                }
            }
            //获得工艺使用的工序集合
            Collection procedureCol = browseProceduresByTechnics(technicbsoID);
            //重新生成的工序号
            int stepNum = 0;
            HashMap map = new HashMap();
            //用于存放某个工艺卡下的没有关联工艺的工序和关联工艺下的工序集合
            ArrayList list = new ArrayList();
            if (procedureCol != null && procedureCol.size() > 0)
            {
                for (Iterator iterator = procedureCol.iterator(); iterator
                                         .hasNext(); )
                {
                    QMProcedureIfc procedure = (QMProcedureIfc) iterator.next();
                    //获得工序关联工艺卡的ID
                    String linkTechnicsiID = procedure.getRelationCardBsoID();
                    //20071204 zhangq add begin:将本身的工序加上,不管是否关联工序，且工序号排在关联工序前.
                    //不管有没有关联工艺，都要原来的工序
                    stepNum++;
                    map.put(procedure.getBsoID(), new Integer(stepNum));
                    QMXMLProcess processXml = new QMXMLProcess();
                    processXml = this.getXMLProcess(stepNum, procedure);
                    //stepShops.add(procedure.getWorkShop().getSearchWord());
                    stepDescNum.add(procedure.getDescStepNumber());
                    list.add(procedure);
                    //20071204 zhangq add end
                    //20080104 zhangq add for erp begin
                    double cureTime=0;
                    //获得关联工艺下的工序集合
                    Collection procedures = browseProceduresByTechnics(
                            linkTechnicsiID);
                    //如果关联工艺下的有工序则只要关联工艺下的工装、设备、主材、辅材等信息，不要该工序
                    if (procedures != null && procedures.size() > 0)
                    {
                        for (Iterator it = procedures.iterator(); it
                                           .hasNext(); )
                        {
                            stepNum++;
                            QMProcedureIfc linkProcedure = (QMProcedureIfc)it.next();
                            map.put(linkProcedure.getBsoID(), new Integer(stepNum));
                            QMXMLProcess linkProcessXml = new QMXMLProcess();
                            linkProcessXml = this.getXMLProcess(stepNum,
                                    linkProcedure);
                            cureTime+=linkProcessXml.getCureTime();
                            //processList.add(linkProcessXml);
//                            stepShops.add(linkProcedure.getWorkShop().
//                            		getSearchWord());
                            stepDescNum.add(linkProcedure.getDescStepNumber());
                            list.add(linkProcedure);
                        }
                    }
                    if(processXml.getCureTime()<=0){
                    	processXml.setCureTime(cureTime);
                    }
                    processList.add(processXml);
                    //20080104 zhangq add for erp end
                }
            }
            logger.debug("getTechnicsData list.size() is "+list.size());
            //获得工序关联的各种资源集合，如part,设备、工装、材料等
          //  for (int j = 0, k = list.size(); j < k; j++)
//            {
//                QMProcedureIfc procedure = (QMProcedureIfc) list.get(j);
//                Collection linkCol = cappService.getUsageResources(
//                        procedure.getBsoID(), false);
//                if (linkCol != null && linkCol.size() > 0)
//                {
//                    for (Iterator linkIt = linkCol.iterator(); linkIt
//                                           .hasNext(); )
//                    {
//                        BinaryLinkIfc link = (BinaryLinkIfc) linkIt.next();
//                        if (link instanceof QMProcedureQMPartLinkIfc)
//                        {
//                        	
//                            String rightBsoID = ((QMProcedureQMPartLinkIfc)
//                                                 link)
//                                                .getRightBsoID();
//                            QMPartIfc partIfc = (QMPartIfc) service
//                                                .refreshInfo(rightBsoID);
////                            List matList = matSplitService
////                                           .getMaterialByStep(partIfc
////                                    .getPartNumber(), 
////                                    procedure.getWorkShop().getSearchWord(),procedure.getTechnicsType().getCode());
//                            int size = matList.size();
//                            if (matList != null && size > 0)
//                            {
//                                if (!flag
//                                    && ((Boolean) matList.get(size - 1))
//                                    .booleanValue())
//                                {
//                                    flag = true;
//                                }
//                                for (int i = 0; i < size - 1; i++)
//                                {
//                                    MaterialSplitIfc matSplit = (
//                                            MaterialSplitIfc) matList
//                                            .get(i);
//                                    QMXMLStepPart stepPartXML = new
//                                            QMXMLStepPart();
//                                    Integer ii = (Integer)map.get(procedure.getBsoID());
//                                    stepPartXML.setStepSerNumber(ii.intValue());
//                                    stepPartXML.setMatNumber(matSplit
//                                            .getMaterialNumber());
//                                    stepPartXML.setRouteCode(procedure
//                                            .getWorkShop().getSearchWord());
//                                    stepPartXML.setPartNumber(partIfc
//                                            .getPartNumber());
//                                    stepPartXML.setPartName(partIfc
//                                            .getPartName());
//                                    stepPartXML
//                                            .setUseQuantity(((
//                                            QMProcedureQMPartLinkIfc) link)
//                                            .getUsageCount());
//                                    stepPartXML.setSplited(matSplit
//                                            .getSplited());
//                                    procedurePartList.add(stepPartXML);
//                                }
//                            }
//                        }
//                        else if (link instanceof
//                                 QMProcedureQMEquipmentLinkIfc)
//                        {
//                            String rightBsoID = ((
//                                    QMProcedureQMEquipmentLinkIfc) link)
//                                                .getRightBsoID();
//                            QMEquipmentIfc equipIfc = (QMEquipmentIfc)
//                                    service
//                                    .refreshInfo(rightBsoID);
//                            QMXMLStepEquip equipXML = new QMXMLStepEquip();
//                            Integer ii = (Integer)map.get(procedure.getBsoID());
//                            equipXML.setStepSerNumber(ii.intValue());
//                            equipXML.setRouteCode(procedure.getWorkShop()
//                                                  .getSearchWord());
//                            equipXML.setEquipNumber(equipIfc.getEqNum());
//                            equipXML.setEquipName(equipIfc.getEqName());
//                            equipXML.setModelNumber(equipIfc.getEqModel());
//                            equipXML.setPosNumber(equipIfc.getPlaneNum());
//                            equipXML
//                                    .setUseQuantity(((
//                                    QMProcedureQMEquipmentLinkIfc) link)
//                                    .getUsageCount());
//                            procedureEquipList.add(equipXML);
//                        }
//                        else if (link instanceof QMProcedureQMToolLinkIfc)
//                        {
//                            String rightBsoID = ((QMProcedureQMToolLinkIfc)
//                                                 link)
//                                                .getRightBsoID();
//                            QMToolIfc toolIfc = (QMToolIfc) service
//                                                .refreshInfo(rightBsoID);
//                            QMXMLStepTool toolXML = new QMXMLStepTool();
//                            Integer ii = (Integer)map.get(procedure.getBsoID());
//                            toolXML.setStepSerNumber(ii.intValue());
//                            toolXML.setRouteCode(procedure.getWorkShop()
//                                                 .getSearchWord());
//                            toolXML.setToolNumber(toolIfc.getToolNum());
//                            toolXML.setToolName(toolIfc.getToolName());
//                            toolXML
//                                    .setUseQuantity(((
//                                    QMProcedureQMToolLinkIfc) link)
//                                    .getUsageCount());
//                            procedureToolList.add(toolXML);
//                        }
//                        else if (link instanceof
//                                 QMProcedureQMMaterialLinkIfc)
//                        {
//                            String rightBsoID = ((
//                                    QMProcedureQMMaterialLinkIfc) link)
//                                                .getRightBsoID();
//                            QMMaterialIfc matIfc = (QMMaterialIfc) service
//                                    .refreshInfo(rightBsoID);
//                            QMXMLAstMaterial astMatXML = new
//                                    QMXMLAstMaterial();
//                            Integer ii = (Integer)map.get(procedure.getBsoID());
//                            astMatXML.setStepSerNumber(ii.intValue());
//                            astMatXML.setRouteCode(procedure.getWorkShop()
//                                    .getSearchWord());
//                            astMatXML.setMatNumber(matIfc
//                                    .getMaterialNumber());
//                            astMatXML.setMatName(matIfc.getMaterialName());
//                            //20080429 begin
////                            astMatXML.setMatBrand(matIfc
////                                                  .getMaterialCrision());
////                            astMatXML.setMatSpecs(matIfc
////                                                  .getMaterialSpecial());
//                            astMatXML.setMatBrand(matIfc.getMaterialSpecial());
//                            astMatXML.setMatSpecs(matIfc.getMaterialState());
//                            astMatXML.setMaterialFunction(matIfc.getMaterialFunction());
//                            astMatXML.setMaterialCrision(matIfc.getMaterialCrision());
//                            //20080429 end
//                            astMatXML
//                                    .setRation(((
//                                    QMProcedureQMMaterialLinkIfc) link)
//                                               .getUsageCount());
//                            astMatXML
//                                    .setUnit(((QMProcedureQMMaterialLinkIfc)
//                                              link)
//                                             .getMeasureUnit()
//                                             .getCodeContent());
//                            procedureMatList.add(astMatXML);
//                        }
//                    }
//                }
//            }
            // }
            //}
            //获得工艺零部件关联的集合
            Collection partTechLinkCol = getPartTechLinks(technicbsoID);
            BaseValueInfo info = technic.getWorkShop();
            //工艺的部门代码
            String technicShop = "";
            if (info instanceof CodingInfo)
            {
                technicShop = ((CodingInfo) info).getSearchWord();
            }
            if (partTechLinkCol != null && partTechLinkCol.size() > 0)
            {
                for (Iterator partTechLink = partTechLinkCol.iterator();
                                             partTechLink.hasNext(); )
                {
                    PartUsageQMTechnicsLinkIfc partLink=(PartUsageQMTechnicsLinkIfc)
                    partTechLink.next();
                    String leftBsoID = partLink.getLeftBsoID();
                    QMPartIfc partIfc = (QMPartIfc) service.refreshInfo(
                            leftBsoID);
                    //QMXMLProcessPart  processPartXML = new QMXMLProcessPart();
                    List materialList = matSplitService.getMaterialByPro(
                            technicShop,
                            partIfc, stepDescNum, stepShops);
                    int size = materialList.size();
                    if (materialList != null && size > 0)
                    {
                        if (!flag
                            && ((Boolean) materialList.get(size - 1))
                            .booleanValue())
                        {
                            flag = true;
                        }
                        for (int i = 0; i < size - 1; i++)
                        {
                            MaterialSplitIfc matSplit = (MaterialSplitIfc)
                                    materialList
                                    .get(i);
                            QMXMLProcessPart processPartXML = new
                                    QMXMLProcessPart();
                            processPartXML.setMatNumber(matSplit
                                    .getMaterialNumber());
                            processPartXML
                                    .setRouteCode(matSplit.getRouteCode());
                            processPartXML.setPartVersion(partIfc
                                    .getVersionID());
                            processPartXML.setPartNumber(partIfc
                                    .getPartNumber());
                            processPartXML.setPartName(partIfc.getPartName());
                            processPartXML.setSplited(matSplit.getSplited());
                            RouteCodeIBAName routeCodeIBAName =
                                    RouteCodeIBAName
                                    .toRouteCodeIBAName(technicShop);
                            String processCode;
                            if(routeCodeIBAName!=null) {
                                String display = routeCodeIBAName.getDisplay();
                                processCode = matSplitService.getPartIBA(
                                        partIfc, display);     
                            }
                            else{
                                processCode="";
                            }
                            processPartXML.setProcessCode(processCode);
                            processPartXML.setMainPartFlag(partLink.
                                    getMajorpartMark());
                            processPartXML.setMainProcessFlag(partLink.
                                    getMajortechnicsMark());
                            processPartList.add(processPartXML);
                        }
                    }
                    //20080107 begin
                    //如果工艺为特殊的工艺,则工序关联的零部件采用工艺中关联的零部件.
//                    StringTokenizer stringToken = new StringTokenizer(useProcessPartRouteCode, delimiter);
//                    //是否有特殊工艺的标志．
//                    boolean flag2=false;
//                    String processName;
//                    while(stringToken.hasMoreTokens()){
//                    	processName=stringToken.nextToken();
//                    	 logger.debug("processName is "+processName);
//                    	if(technicShop != null
//                    			&& processName != null	&& technicShop.equalsIgnoreCase(processName)){
//                    		flag2=true;
//                    		   logger.debug("99999 technicShop.equalsIgnoreCase(processName) ");
//                    		break;
//                    	}
//                    }
//                    logger.debug("flag2 is "+flag2);
//               	    logger.debug("technicShop is "+technicShop);
//                    if (flag2) {
//						for (int j = 0, k = list.size(); j < k; j++) {
//							QMProcedureIfc procedure = (QMProcedureIfc) list
//									.get(j);
//
//							List matList = matSplitService.getMaterialByStep(
//									partIfc.getPartNumber(), procedure
//											.getWorkShop().getSearchWord());
//
//							int matSize = matList.size();
//							for(int u=0;u<matSize;u++){
//								 logger.debug("matList.get(u) is "+matList.get(u));
//								 logger.debug("matList.get(u).getClass() is "+matList.get(u).getClass());
//							}
//							logger.debug("matList.get(matSize-1).getClass() is "+matList.get(matSize-1).getClass());
//							if (matList != null && matSize > 0) {
//								if (!flag
//										&& ((Boolean) matList.get(matSize - 1))
//												.booleanValue()) {
//									flag = true;
//								}
//								for (int i = 0; i < matSize - 1; i++) {
//									MaterialSplitIfc matSplit = (MaterialSplitIfc) matList
//											.get(i);
//									QMXMLStepPart stepPartXML = new QMXMLStepPart();
//									Integer ii = (Integer) map.get(procedure
//											.getBsoID());
//									stepPartXML.setStepSerNumber(ii.intValue());
//									stepPartXML.setMatNumber(matSplit
//											.getMaterialNumber());
//									stepPartXML.setRouteCode(procedure
//											.getWorkShop().getSearchWord());
//									stepPartXML.setPartNumber(partIfc
//											.getPartNumber());
//									stepPartXML.setPartName(partIfc
//											.getPartName());
//									//数量暂时为1.正确的工序关联的零部件的数量是通过取得工艺关联零部件的数量,
//									//如果为空或者0,则设置为1,否则设置为工艺关联零部件的数量.
//									stepPartXML.setUseQuantity(1);
//									stepPartXML.setSplited(matSplit
//											.getSplited());
//									procedurePartList.add(stepPartXML);
//								}
//							}
//						}
//					}
                    //20080107 end
                }
            }
        }
        catch (QMException e)
        {
            throw new TechnicDataException(e);
        }
        technicList.add(processList);
        technicList.add(processPartList);
        technicList.add(procedurePartList);
        technicList.add(procedureEquipList);
        technicList.add(procedureToolList);
        technicList.add(mainMatList);
        technicList.add(procedureMatList);
        technicList.add(Boolean.valueOf(flag));
        return technicList;
    }
}

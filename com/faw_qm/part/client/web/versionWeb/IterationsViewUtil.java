/** 生成程序IterationsViewUtil.java	  1.0  2003/05/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/12/25 王亮 修改原因：TD2741，在查看父件的使用结构时，点击一个其不符合配置规范的子件的编号链接，页面错误。
 */
package com.faw_qm.part.client.web.versionWeb;

import java.util.Collection;
import java.util.Vector;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;


/**
 *
 * <p>Title: 查看版序历史，此类都是静态方法，供瘦客户端显示用。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author wawa
 * @version 1.0
 */
public class IterationsViewUtil
{

    /**
     * 构造函数。
     */
    public IterationsViewUtil()
    {
    }


    /**
     * 得到瘦客户端头部的必要显示信息。
     *
     * @param Bso 对象的BsoID。
     * @return String
     */
    public static String getVersionHead(String Bso)
    {
        String id = Bso.trim();
        String partNum;
        String partName;
        BaseValueIfc pb;
        String rStr;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService("PersistService");
            pb = pService.refreshInfo(id);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
            pb = null;
        }
        if (pb != null)
        {
            if (pb instanceof QMPartIfc)
            {
                partNum = ((QMPartIfc) pb).getPartNumber();
                partName = ((QMPartIfc) pb).getPartName();
                rStr = "编号： " + partNum + "         " + "名称： " + partName;
                return rStr;
            }
            else
            {
                partNum = ((QMPartMasterIfc) pb).getPartNumber();
                partName = ((QMPartMasterIfc) pb).getPartName();
                String[] vv = new String[2];
                rStr = "编号： " + partNum + "         " + "名称： " + partName;
                return rStr;
            }
        }
        else
        {
            rStr = "编号：          " + "名称： ";
            return rStr;
        }
    }


    /**
     * 通过得到对象，获得该对象的版序历史对象的集合。
     * @param BsoID Part对象的BsoID。
     * @return Collection 通过得到对象，获得该对象的版本历史对象的集合。
     * @deprecated replace with getAllPredecessor(String partBsoID)
     */
    //通过得到的part对象获得版本信息,得到所有的小版本的集合
    public static Collection getVersionsCollection(String BsoID)
    {
        QMPartMasterIfc myMasterIfc;
        Collection myCollection = new Vector();
        try
        {
            PersistService service = (PersistService) EJBServiceHelper.
                                     getService("PersistService");
            myMasterIfc = (QMPartMasterIfc) service.refreshInfo(BsoID);

            VersionControlService service1 = (VersionControlService)
                                             EJBServiceHelper.getService(
                    "VersionControlService");
            myCollection = service1.allIterationsOf((MasteredIfc) myMasterIfc);
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
        }
        return myCollection;
    }
    
    //获得版本小对象中的信息并且写到新的集合中来
    /**
     * 通过得到对象，获得该对象的版序历史对象的集合，得到符合瘦客户端显示必须的内容的集合。
     * @param BsoID String Part对象的BsoID。
     * @return Collection String数组的对象集合，数组内容为版本对象的BsoID,版本,对象状态,项目名称,创建者,创建时间,版序注释。
     */
    public static Collection getMyCollection(String BsoID)
    {
        Collection myCo = getAllPredecessor(BsoID);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMPartIfc myIfc;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        Collection myCollection = new Vector();
        String versionBsoID;
        String iterationNote;

        for (int i = 0; i < myCo.size(); i++)
        {
            //得到小版本
            myIfc = (QMPartIfc) (aa[i]);
            //得到需要的值

            versionID = myIfc.getVersionValue();
            if (myIfc.getLifeCycleState() != null)
            {
                objectState = (myIfc.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
            }
            projectName = myIfc.getProjectName();
            try
            {
                objectCreator = PartServiceHelper.getUserNameByID(myIfc.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            createTime = (myIfc.getCreateTime()).toString();
            iterationNote = myIfc.getIterationNote();
            versionBsoID = myIfc.getBsoID();
            //构造数组
            String[] myVersionArray1 =
                                       {versionBsoID, versionID, objectState,
                                       projectName, objectCreator, createTime, iterationNote};
            //加到集合中
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }
    
    /**
     * 通过得到对象，获得该对象的版序历史对象的集合，得到符合瘦客户端显示必须的内容的集合。
     * @param BsoID String PartMaster对象的BsoID。
     * @return Collection String数组的对象集合，数组内容为版本对象的BsoID,版本,对象状态,项目名称,创建者,创建时间,版序注释。
     */
    public static Collection getAllVersionCollection(String masterBsoId)//CR1
    {
        Collection myCo =getVersionsCollection(masterBsoId);
        Object[] aa = new Object[myCo.size()];
        myCo.toArray(aa);

        QMPartIfc myIfc;
        String versionID;
        String objectState;
        String projectName;
        String objectCreator;
        String createTime;
        String modifyTime;//liunan 2011-09-30 查看版序时显示更新时间。
        Collection myCollection = new Vector();
        String versionBsoID;
        String iterationNote;

        for (int i = 0; i < myCo.size(); i++)
        {
            //得到小版本
            myIfc = (QMPartIfc) (aa[i]);
            //得到需要的值

            versionID = myIfc.getVersionValue();
            if (myIfc.getLifeCycleState() != null)
            {
                objectState = (myIfc.getLifeCycleState()).getDisplay();
            }
            else
            {
                objectState = "";
            }
            projectName = myIfc.getProjectName();
            try
            {
                objectCreator = PartServiceHelper.getUserNameByID(myIfc.
                        getIterationCreator());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                objectCreator = "";
            }
            createTime = (myIfc.getCreateTime()).toString();
            modifyTime = (myIfc.getModifyTime()).toString();//liunan 2011-09-30 查看版序时显示更新时间。
            iterationNote = myIfc.getIterationNote();
            versionBsoID = myIfc.getBsoID();
            //构造数组
            String[] myVersionArray1 =
                                       {versionBsoID, versionID, objectState,
                                       //projectName, objectCreator, createTime, iterationNote};
                                       projectName, objectCreator, createTime, modifyTime, iterationNote};//liunan 2011-09-30 查看版序时显示更新时间。
            //加到集合中
            myCollection.add(myVersionArray1);
        }
        return myCollection;
    }

    /**
     * 给定零部件的id,获得其iba属性值等。
     * @param partBsoID String
     * @return Vector 其中的元素为二维字符0串数组,第一维存放该iba属性值的属性定义的名称,
     * 第二维存放iba属性的值。
     */
    public Vector getIBANameAndValue(String partBsoID)
    {
        IBAHolderIfc part = null;
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper.
                                      getService(
                    "PersistService");
            part = (IBAHolderIfc) pService.refreshInfo(partBsoID);
            IBAValueService ibaService = (IBAValueService) EJBServiceHelper.
                                         getService("IBAValueService");
            part = (IBAHolderIfc) ibaService.refreshAttributeContainer(part, null, null, null);
        }
        catch (ServiceLocatorException ex)
        {
            ex.printStackTrace();
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        DefaultAttributeContainer container = (DefaultAttributeContainer) part.
                                              getAttributeContainer();
        if (container == null)
        {
            container = new DefaultAttributeContainer();
        }
        AbstractValueView[] values = container.getAttributeValues();
        Vector retVal = new Vector();
        for (int i = 0; i < values.length; i++)
        {
            String[] nameAndValue = new String[2];
            AbstractValueView value = values[i];
            AttributeDefDefaultView definition = value.getDefinition();
            nameAndValue[0] = definition.getName();
            if (value instanceof AbstractContextualValueDefaultView)
            {
                nameAndValue[1] = ((AbstractContextualValueDefaultView) value).
                                  getValueAsString();
            }
            else
            {
                nameAndValue[1] = ((ReferenceValueDefaultView) value).
                                  getLocalizedDisplayString();
            }
            retVal.add(nameAndValue);
            nameAndValue = null;
        }
        return retVal;
    }
    
    /**
     * 获取当前版序对象的一条前驱分支。为版序历史用例使用。如当前为A.3，则返回集合为A.3,A.2,A.1。
     * @param partBsoID 版序对象BsoID。
     * @return 版序集合。
     * @throws QMException
     */
    public static Collection getAllPredecessor(String partBsoID)
    {
        Collection coll = null;
        try
        {
            PersistService persistService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            QMPartIfc partIfc = (QMPartIfc) persistService.refreshInfo(partBsoID);
            VersionControlService vcService = (VersionControlService) EJBServiceHelper
                    .getService("VersionControlService");
            coll = vcService.getAllPredecessor(partIfc);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return coll;
    }
}

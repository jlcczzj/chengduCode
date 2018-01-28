/** 生成程序ChooseOption_zh_CN.java    1.0  2003/02/06
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *  CR1 2009/04/21 刘志城    原因：工艺资源搜索界面同一
 *                          方案：把设备、工装、简图、工艺术语的搜索界面名称与编号对调。
 *                          备注：功能测试用例名称：“资源管理模块”。
 */

package com.faw_qm.cappclients.beans.query;


// Referenced classes of package com.faw_qm.clients.beans.query:
//            ChooserOptions

/**
 * <p>Title:RB文件</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FAW_QM Co Ltd</p>
 * @author 杨晓辉
 * @version 1.0
 */

public class CappChooserOptions_zh_CN extends CappChooserOptions
{

    public Object[][] getContents()
    {
        return contents;
    }

    public CappChooserOptions_zh_CN()
    {
    }

    static final Object contents[][] =
            {
            {
            "DocSCM", "C:Doc; G:\u641C\u7D22\u6761\u4EF6; A:docNum; A:docName;  A:versionID; A:lifeCycleState; A:projectId; G:\u5176\u5B83\u641C\u7D22\u6761\u4EF6; A:location; A:dataFormatID;A:owner; A:modifyTime; " //A:docType;
    }
            ,
            {
            "DocMasterSCM",
            "C:DocMaster; G:\u641C\u7D22\u6761\u4EF6; A:docName; A:docNum;"
    }
            ,
            {
            "PartSCM", "C:QMPart; G:\u641C\u7D22\u6761\u4EF6; A:partNumber; A:partName; A:viewName; A:versionID; A:partTypeStr; A:producedByStr; G:\u5176\u5B83\u641C\u7D22\u6761\u4EF6; A:lifeCycleState; A:projectId;A:owner; A:location; A:modifyTime; "
    }
            ,
            {
            "PartMasterSCM",
            "C:QMPartMaster; G:\u641C\u7D22\u6761\u4EF6; A:partNumber; A:partName "
    }
            ,
            {
            "1", "Doc QMPart"
    }
            ,
            {
            "2", "Doc"
    }
            ,
            {
            "BaselineSCM", "C:ManagedBaseline; G:\u641C\u7D22\u6761\u4EF6; A:baselineNumber; A:baselineName; A:lifeCycleState; A:projectId; G:\u5176\u5B83\u641C\u7D22\u6761\u4EF6; A:location; A:modifyTime; A:baselineDescription"
    }
            ,
            {
            "4", "DocMaster"
    }
            ,
            {
            "ProjectSCM",
            "C:Project; G:\u641C\u7D22\u6761\u4EF6; A:name; A:location; A:description"
    }
            ,
            {
            "LifeCycleTemplateMasterSCM", "C:LifeCycleTemplateMaster; G:\u641C\u7D22\u6761\u4EF6;   A:lifeCycleName; A:description"
    }
            ,
            {
            "5", "Doc"
    }
            ,
            {
            //Begin CR1:设备搜索界面编号与名称对调。
            "QMEquipmentSCM",
            "C:QMEquipment; G:\u641C\u7D22\u6761\u4EF6;   A:eqNum;A:eqName;A:eqCf;A:location;A:eqManu;A:eqType;A:eqModel;A:planeNum;A:workShop;G:高级搜索;A:createTime;" +
            "A:modifyTime;E:eqType"
    }       //End CR1
            ,
            {
            //Begin CR1:工装搜索界面编号与名称对调。
            "QMToolSCM",
            "C:QMTool; G:\u641C\u7D22\u6761\u4EF6;   A:toolNum;A:toolName;A:toolCf;A:toolReNumber;A:workShop;A:dftNum;A:toolState;A:toolStdNum;A:toolWareNum;A:location;G:高级搜索;A:createTime;" +
            "A:modifyTime"
    }       //End CR1
            ,
            {
            //Begin CR1:工艺术语搜索界面编号与名称对调。
            "QMTermSCM",
            "C:QMTerm; G:\u641C\u7D22\u6761\u4EF6;   A:termNumber;A:termName;A:location;A:termType;G:高级搜索;A:createTime;" +
            "A:modifyTime"
    }       //End CR1
            ,
            {
            	 //CCBegin by leixiao 2009-12-10材料属性调整
            "QMMaterialSCM",
            "C:QMMaterial; G:\u641C\u7D22\u6761\u4EF6;   A:materialNumber;A:materialName;A:materialState;A:materialSpecial;A:materialCrision;A:materialCf;G:高级搜索;A:createTime;" +
            "A:modifyTime"
             //CCEnd by leixiao 2009-12-10
    }
            ,  
            {
            //Begin CR1:资源简图搜索界面编号与名称对调。
            "DrawingSCM",
            "C:Drawing; G:\u641C\u7D22\u6761\u4EF6;   A:drawingNumber;A:drawingName;A:drawingType;A:location;G:高级搜索;A:createTime;" +
            "A:modifyTime"
    }       //End CR1
            ,
            {
            "TotalSchemaItemSCM", "C:TotalSchemaItem; G:\u641C\u7D22\u6761\u4EF6;   A:tecTotalNumber;A:tecTotalName;A:totalType;A:location;A:lifeCycleState"
    }
            ,
            {
            "FilterSchemaItemSCM", "C:FilterSchemaItem; G:\u641C\u7D22\u6761\u4EF6;   A:filterNumber;A:filterName;A:totalTypeStr"
    }
            ,
            {
            "QMFawTechnicsSCM",
            "C:QMFawTechnics; G:\u641C\u7D22\u6761\u4EF6;  A:location"
    }
            ,
            {
            "QMFawTechnicsMasterSCM",
            "C:QMFawTechnicsMaster; G:搜索条件;A:technicsNumber;A:technicsName;A:technicsType;"
    }
            ,
            {
            "SpecialCharSCM",
            "C:SpecialChar; G:\u641C\u7D22\u6761\u4EF6;   A:drawingNumber;A:drawingType"
    }
            ,
            {
            "QMExpressionsSCM",
            "C:QMExpressions;G:搜索条件;A:expression;A:expressSort"
    }
            ,
            {"browsButton", "浏览"
    }
            ,
            {"queryResult", "找到"
    }

    };

}

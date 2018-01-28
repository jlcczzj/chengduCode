/** ���ɳ���ChooseOption_zh_CN.java    1.0  2003/02/06
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  CR1 2009/04/21 ��־��    ԭ�򣺹�����Դ��������ͬһ
 *                          ���������豸����װ����ͼ��������������������������ŶԵ���
 *                          ��ע�����ܲ����������ƣ�����Դ����ģ�顱��
 */

package com.faw_qm.cappclients.beans.query;


// Referenced classes of package com.faw_qm.clients.beans.query:
//            ChooserOptions

/**
 * <p>Title:RB�ļ�</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FAW_QM Co Ltd</p>
 * @author ������
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
            //Begin CR1:�豸���������������ƶԵ���
            "QMEquipmentSCM",
            "C:QMEquipment; G:\u641C\u7D22\u6761\u4EF6;   A:eqNum;A:eqName;A:eqCf;A:location;A:eqManu;A:eqType;A:eqModel;A:planeNum;A:workShop;G:�߼�����;A:createTime;" +
            "A:modifyTime;E:eqType"
    }       //End CR1
            ,
            {
            //Begin CR1:��װ���������������ƶԵ���
            "QMToolSCM",
            "C:QMTool; G:\u641C\u7D22\u6761\u4EF6;   A:toolNum;A:toolName;A:toolCf;A:toolReNumber;A:workShop;A:dftNum;A:toolState;A:toolStdNum;A:toolWareNum;A:location;G:�߼�����;A:createTime;" +
            "A:modifyTime"
    }       //End CR1
            ,
            {
            //Begin CR1:�����������������������ƶԵ���
            "QMTermSCM",
            "C:QMTerm; G:\u641C\u7D22\u6761\u4EF6;   A:termNumber;A:termName;A:location;A:termType;G:�߼�����;A:createTime;" +
            "A:modifyTime"
    }       //End CR1
            ,
            {
            	 //CCBegin by leixiao 2009-12-10�������Ե���
            "QMMaterialSCM",
            "C:QMMaterial; G:\u641C\u7D22\u6761\u4EF6;   A:materialNumber;A:materialName;A:materialState;A:materialSpecial;A:materialCrision;A:materialCf;G:�߼�����;A:createTime;" +
            "A:modifyTime"
             //CCEnd by leixiao 2009-12-10
    }
            ,  
            {
            //Begin CR1:��Դ��ͼ���������������ƶԵ���
            "DrawingSCM",
            "C:Drawing; G:\u641C\u7D22\u6761\u4EF6;   A:drawingNumber;A:drawingName;A:drawingType;A:location;G:�߼�����;A:createTime;" +
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
            "C:QMFawTechnicsMaster; G:��������;A:technicsNumber;A:technicsName;A:technicsType;"
    }
            ,
            {
            "SpecialCharSCM",
            "C:SpecialChar; G:\u641C\u7D22\u6761\u4EF6;   A:drawingNumber;A:drawingType"
    }
            ,
            {
            "QMExpressionsSCM",
            "C:QMExpressions;G:��������;A:expression;A:expressSort"
    }
            ,
            {"browsButton", "���"
    }
            ,
            {"queryResult", "�ҵ�"
    }

    };

}

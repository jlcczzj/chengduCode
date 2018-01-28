/**
 * ���ɳ���MaterialSplitMap.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import java.sql.Timestamp;
import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class MaterialSplitMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean colorFlag;

    private String defaultUnit;

    private String materialNumber;

    private String optionCode;

    private String partName;

    private String partNumber;
    
    private String rCode;
    private String materialSplitType;
    private String partType;
    
    private Timestamp partModifyTime;

    private String partVersion;

    private String producedBy;

    private String route;

    private String routeCode;

    private boolean splited;

    private String state;

    private int status;
    
    private boolean rootFlag;
    
    private boolean virtualFlag=false;
    
    private boolean isMoreRoute;
    
    public boolean getColorFlag()
    {
        return colorFlag;
    }

    public String getDefaultUnit()
    {
        return defaultUnit;
    }

    public String getMaterialNumber()
    {
        return materialNumber;
    }

    public String getOptionCode()
    {
        return optionCode;
    }

    public String getPartName()
    {
        return partName;
    }

    public String getPartNumber()
    {
        return partNumber;
    }
    public String getRCode()
    {
        return rCode;
    }
    public String getMaterialSplitType()
    {
        return materialSplitType;
    }
    public String getPartType()
    {
        return partType;
    }
    
    /**
     * ��ȡ�������ʱ�䡣
     * @return �������ʱ�䡣
     */
    public Timestamp getPartModifyTime()
    {
        return partModifyTime;
    }

    public String getPartVersion()
    {
        return partVersion;
    }

    public String getProducedBy()
    {
        return producedBy;
    }

    public String getRoute()
    {
        return route;
    }

    public String getRouteCode()
    {
        return routeCode;
    }

    public boolean getSplited()
    {
        return splited;
    }

    public String getState()
    {
        return state;
    }

    public int getStatus()
    {
        return status;
    }

    public void setColorFlag(boolean colorFlag)
    {
        this.colorFlag = colorFlag;
    }

    public void setDefaultUnit(String defaultUnit)
    {
        this.defaultUnit = defaultUnit;
    }

    public void setMaterialNumber(String materialNumber)
    {
        this.materialNumber = materialNumber;
    }

    public void setOptionCode(String optionCode)
    {
        this.optionCode = optionCode;
    }

    public void setPartName(String partName)
    {
        this.partName = partName;
    }

    public void setPartNumber(String partNumber)
    {
        this.partNumber = partNumber;
    }
    public void setRCode(String rCode)
    {
        this.rCode = rCode;
    }
    public void setMaterialSplitType(String materialSplitType)
    {
        this.materialSplitType = materialSplitType;
    }
    public void setPartType(String partType)
    {
        this.partType = partType;
    }
    
    /**
     * �����������ʱ�䡣
     * @param partModifyTime �������ʱ�䡣
     */
    public void setPartModifyTime(Timestamp partModifyTime)
    {
        this.partModifyTime = partModifyTime;
    }

    public void setPartVersion(String partVersion)
    {
        this.partVersion = partVersion;
    }

    public void setProducedBy(String producedBy)
    {
        this.producedBy = producedBy;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }

    public void setRouteCode(String routeCode)
    {
        this.routeCode = routeCode;
    }

    public void setSplited(boolean splited)
    {
        this.splited = splited;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
    
    /**
     * ���ö������ϱ�ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @param colorFlag �������ϱ�ʶ��
     */
    public void setRootFlag(boolean rootFlag)
    {
        this.rootFlag = rootFlag;
    }

    /**
     * ��ȡ�������ϱ�ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return �������ϱ�ʶ��
     */
    public boolean getRootFlag()
    {
        return rootFlag;
    }
    
    /**
     * �����������ϱ�ʶ
     * @param flag �������ϱ�ʶ
     */
    public void setVirtualFlag(boolean flag)
    {
    	this.virtualFlag=flag;
    }
    
    /**
     * ��ȡ�������ϱ�ʶ
     * @return �������ϱ�ʶ
     */
    public boolean getVirtualFlag()
    {
    	return this.virtualFlag;
    }
    
    /**
     * ��ȡ��·�߱�ʶ
     * @return ��·�߱�ʶ
     */
    public boolean getIsMoreRoute()
    {
    	return this.isMoreRoute;
    }
    
    /**
     * ���ö�·�߱�ʶ
     * @param flag ��·�߱�ʶ
     */
    public void setIsMoreRoute(boolean flag)
    {
    	this.isMoreRoute=flag;
    }
}
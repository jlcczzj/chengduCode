/**
 * 生成程序CompositiveInfo.java	1.0              2007-9-24
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

import java.sql.Timestamp;
import com.faw_qm.enterprise.model.ItemInfo;
import com.faw_qm.jferp.util.IntePackSourceType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.util.QMMessage;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class IntePackInfo extends ItemInfo implements IntePackIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String RESOURCE = "com.faw_qm.jferp.util.ERPResource";

    String name;

    IntePackSourceType sourceType;

    String source;

    int state;

    String compositiveDomain;

    String publisher;

    Timestamp publishTime;

    String domain;

    String creator;
    



    /**
     * 获取唯一标识。
     * @return String 唯一标识。
     */
    public String getIdentity()
    {
        return this.getBsoID();
    }

    /**
     * 获取业务对象名。
     * @return "IntePack"
     */
    public String getBsoName()
    {
        return "JFIntePack";
    }

    /**
     * 设置集成包名称。
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 设置集成包来源类型。
     * @param name IntePackSourceType
     */
    public void setSourceType(IntePackSourceType compositiveType)
    {
        this.sourceType = compositiveType;
    }

    /**
     * 设置集成包来源。
     * @param name String
     */
    public void setSource(String source)
    {
        this.source = source;
    }

    /**
     * 设置集成包状态。
     * @param name int
     */
    public void setState(int state)
    {
        this.state = state;
    }

    /**
     * 设置集成包创建者。
     * @param name String
     */
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    /**
     * 设置集成包发布者。
     * @param name String
     */
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    /**
     * 设置集成包发布时间。
     * @param name String
     */
    public void setPublishTime(Timestamp promulgationTime)
    {
        this.publishTime = promulgationTime;
    }

    /**
     * 获得集成包名称。
     * @return String
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * 获得集成包来源类型。
     * @return String
     */
    public IntePackSourceType getSourceType()
    {
        return this.sourceType;
    }

    /**
     * 获得集成包来源。
     * @return String
     */
    public String getSource()
    {
        return this.source;
    }

    /**
     * 获得集成包状态。
     * @return String
     */
    public int getState()
    {
        return this.state;
    }

    /**
     * 获得集成包发布者。
     * @return String
     */
    public String getPublisher()
    {
        return this.publisher;
    }

    /**
     * 获得集成包发布者。
     * @return String
     */
    public Timestamp getPublishTime()
    {
        return this.publishTime;
    }

    /**
     * 获得集成包创建者。
     * @return String
     */
    public String getCreator()
    {
        return this.creator;
    }

    /**
     * 获得集成包状态的显示名称。
     * @return String
     */
    public String getStateDisplayName()
    {
        String display = "";
        if(this.getState() == 0)
        {
            display = "已创建";
        }
        else if(this.getState() == 9)
        {
            display = "已发布";
        }
        return display;
    }

    /**
     * 由IntePackEJB的CreateByValueInfo和UpdateByValueInfo方法回调，验证属性的有效性。
     * @exception QMException
     */
    public void attrValidate() throws QMException
    {
        intePackNameValidate(getName());
    }

    /**
     * 按照intePackName的验证规则，验证传入的intePackName的有效性。
     *
     * @param intePackName 集成包名称。
     * @throws QMException
     */
    public static void intePackNameValidate(String intePackName)
            throws QMException
    {
        if(intePackName != null && intePackName.length() > 200)
        {
            //"集成包名称"
            String str = QMMessage.getLocalizedMessage(RESOURCE,
                    "IJFntePack.intePackName", null);
            Object[] obj = {str};
            //"*长度不符合要求！"
            throw new QMException(RESOURCE, "IntePack.attrValidate", obj);
        }
    }
    
    
}

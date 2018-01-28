/** 生成程序 UsageMasterItem.java    1.0    2003/02/22
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.client.design.model;

import java.awt.Image;
import java.sql.Timestamp;

import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.Unit;
import org.apache.log4j.Logger;


/**
 * UsageMasterItem是一个用来处理零部件使用关系的模型类。
 * @author 吴先超
 * @version 1.0
 *
 */
// 问题(1)20080226 张强修改 修改原因:在粘贴不符合配置规范的零部件时，无法显示使用单位。
public class UsageMasterItem implements Explorable
{
    /**异常信息提示*/
    private final static Logger logger = Logger.getLogger(UsageMasterItem.class);


    /**零部件和零部件主信息的关联关系值对象*/
    private transient PartUsageLinkIfc usageLinkIfc = null;


    /**是否被修改的标识*/
    private boolean modified = false;


    /**使用关联的bsoID*/
    private transient String linkID = null;


    /**使用数量*/
    private float quantity = 1.0f;


    /**使用数量的单位*/
    private transient Unit quantityUnit = null;


    /**使用者零部件*/
    private PartMasterItem uses = null;


    /**
     * 构造函数，创建一个空的使用关系。
     */
    public UsageMasterItem()
    {
        super();
        modified = false;
    }


    /**
     * 构造函数，使用指定的使用联接来在指定的零部件项目之间创建一个使用项目。
     * @param partmasteritem PartMasterItem 使用者零部件。
     * @param usageLinkIfc1 PartUsageLinkIfc 使用关联。
     * @see PartMasterItem
     * @see PartUsageLinkInfo
     */
    public UsageMasterItem(PartMasterItem partmasteritem,
                           PartUsageLinkIfc usageLinkIfc1)
    {
        super();
        PartDebug.debug("UsageMasterItem()begin ....", this,
                        PartDebug.PART_CLIENT);

        usageLinkIfc = usageLinkIfc1;
        if (null == usageLinkIfc1)
        {
            PartDebug.debug("UsageMasterItem() end....return is null", this,
                            PartDebug.PART_CLIENT);
            return;
        }
        try
        {
            linkID = usageLinkIfc1.getBsoID();
        }
        catch (Exception exception)
        {
            logger.error(exception);
        }
        quantity = usageLinkIfc.getQuantity();
        //问题(1)20080226 zhangq begin:在粘贴不符合配置规范的零部件时，无法显示使用单位。
        //quantityUnit = (usageLinkIfc.getQMQuantity()).getDefaultUnit();
        quantityUnit = usageLinkIfc.getDefaultUnit();
      //CCBegin SS1
        subUnitNumber = usageLinkIfc.getSubUnitNumber();
        bomItem = usageLinkIfc.getBomItem();
        //CCEnd SS1
      //CCBegin SS2
        proVersion = usageLinkIfc.getProVersion();
        //CCEnd SS2
        //问题(1)20080226 zhangq end
        uses = partmasteritem;
        PartDebug.debug("UsageMasterItem() end....return is UsageMasterItem", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 设置修改标识。
     * @param flag boolean 是否被修改标识。
     */
    public void setModified(final boolean flag)
    {
        modified = flag;
    }


    /**
     * 返回是否被修改的标识。
     * @return boolean 是否被修改标识。
     */
    public boolean isModified()
    {
        return modified;
    }


    /**
     * 设置使用关系属性:usageLinkIfc。
     * @param partUsageLinkIfc PartUsageLinkIfc 使用关联。
     * @see PartUsageLinkIfc
     */
    public void setPartUsageLink(final PartUsageLinkIfc partUsageLinkIfc)
    {
        usageLinkIfc = partUsageLinkIfc;
    }


    /**
     * 获取使用关系属性:usageLinkIfc。
     * @return PartUsageLinkIfc 使用关联。
     * @see PartUsageLinkIfc
     */
    public PartUsageLinkIfc getPartUsageLink()
    {
        return usageLinkIfc;
    }


    /**
     * 设置使用零部件项目。
     * @param partmasteritem PartMasterItem 使用者零部件。
     * @see PartMasterItem
     */
    public void setUses(final PartMasterItem partmasteritem)
    {
        uses = partmasteritem;
    }


    /**
     * 返回这个使用零部件项目使用的对象数组。
     * @return Explorable[] 被使用者使用的对象数组。
     * @throws QMRemoteException
     * @see Explorable
     */
    public Explorable[] getUses()
            throws QMRemoteException
    {
        Explorable explorable[] = null;
        try
        {
//          性能瓶颈。
            explorable = uses.getUses();
        }
        catch (Exception ex)
        {
            logger.error(ex);
        }
        return explorable;
    }


    /**
     * 返回使用零部件项目。
     * @return PartMasterItem 使用者零部件。
     * @see PartMasterItem
     */
    public PartMasterItem getUsesPart()
    {
        return uses;
    }


    /**
     * 获取对这个对象的标识。
     * @return String 标识符。
     */
    public String getId()
    {
        return linkID;
    }


    /**
     * 设置在使用关系中数量。
     * @param quantity1 :float 总数量。
     */
    public void setQuantity(final float quantity1)
    {
        quantity = quantity1;
        setModified(true);
    }


    /**
     * 获取在使用联接中的数量。
     * @return double 总数量。
     */
    public float getQuantity()
    {

        return quantity;
    }


    /**
     * 获取数量显示的字符串。
     * @return String 总数量的字符串表示。
     */
    public String getQuantityString()
    {
        final String quanStr = UsageItem.getDecimalFormat().format(getQuantity());
        return quanStr;
    }


    /**
     * 设置使用数量的单位。
     * @param a_unit Unit 数量的单位。
     * @see Unit
     */
    public void setUnit(final Unit a_unit)
    {
        quantityUnit = a_unit;
        setModified(true);
    }


    /**
     * 获取数量的单位。
     * @return QuantityUnit 数量的单位。
     * @see Unit
     */
    public Unit getUnits()
    {
        return quantityUnit;
    }


    /**
     * 获取单位的字符串的描述。
     * @return String 单位。
     */
    public String getUnitName()
    {
        return quantityUnit.getDisplay();
    }


    /**
     * 返回一个字符串:使用数量+使用单位。
     * @return String 使用数量+使用单位组成的字符串。
     */
    public String toString()
    {
        return quantity + " " + quantityUnit;
    }


    /**
     * 返回使用零部件项目的标准图标。
     * @return Image 标准图标。
     */
    public Image getStandardIcon()
    {
        return uses.getStandardIcon();
    }


    /**
     * 返回使用零部件项目的展开的图标。
     * @return Image 标准图标。
     */
    public Image getOpenIcon()
    {
        return uses.getOpenIcon();
    }


    /**
     * 返回使用零部件项目的标识。
     * @return String 唯一标识。
     */
    public String getIdentity()
    {
        return uses.getNumber() + "-" + uses.getName();
    }


    /**
     * 返回使用零部件项目的唯一标识。
     * @return String 唯一标识。
     */
    public String getUniqueIdentity()
    {
        return uses.getUniqueIdentity();
    }


    /**
     * 返回使用零部件项目的内容。
     * @return Explorable[] 零部件项目的内容数组。
     * @see Explorable
     */
    public Explorable[] getContents()
    {
        Explorable explorable[] = null;
        try
        {
            explorable = uses.getContents();
        }
        catch (Exception ex)
        {
            logger.error(ex);
        }
        return explorable;
    }


    /**
     * 实现 ReferenceHolder接口中的方法。
     * 获取包含的对象。
     * @return Object 使用者包含的对象。
     */
    public Object getObject()
    {
        return uses.getObject();
    }


    /**
     * 调用父类的方法，将传入的参数保存到父类的参数中去。
     * @param obj Object 使用者包含的对象。
     */
    public void setObject(final Object obj)
    {
        uses.setObject(obj);
    }


    /**
     * 返回这个使用零部件项目的名字。
     * @return String 零部件项目名。
     */
    public String getName()
    {
        return uses.getName();
    }


    /**
     * 返回这个使用零部件项目的名字。
     * @return String 零部件项目名。
     */
    public String getObjectName()
    {
        return uses.getName();
    }


    /**
     * 返回这个使用零部件项目的编号。
     * @return String 零部件项目编号。
     */
    public String getNumber()
    {
        return uses.getNumber();
    }


    /**
     * 返回这个使用零部件项目的编号。
     * @return String 零部件项目编号。
     */
    public String getObjectNumber()
    {
        return uses.getNumber();
    }


    //add by 熊代军
    /**
     * 获取创建时间。
     * @return Timestamp 创建时间。
     */
    public Timestamp getObjectCreationDate()
    {
        return uses.getObjectCreationDate();
    }


    /**
     * 获取对象版本。
     * @return String 版本。
     */
    public String getObjectVersion()
    {
        return "A.0";
    }
    //CCBegin　SS1
    private String subUnitNumber = new String(" ");
    private String bomItem = new String(" ");
    
    /**
     * 设置在使用联接中的子组号。
     */
    public void setSubUnitNumber(String a_subUnitNumber)
    {
        subUnitNumber = a_subUnitNumber;
        setModified(true);
    }


    /**
     * 获取在使用联接中的子组号。
     */
    public String getSubUnitNumber()
    {
        return subUnitNumber;
    }

    
    /**
     * 设置在使用联接中的结构备注。
     */
    public void setBomItem(String a_bomItem)
    {
    	bomItem = a_bomItem;
        setModified(true);
    }


    /**
     * 获取在使用联接中的结构备注。
     */
    public String getBomItem()
    {
        return bomItem;
    }
    //CCEnd SS1
    //CCBegin　SS2
    private String proVersion = new String("");
    public void setProVersion(String a_proVersion)
    {
        proVersion = a_proVersion;
        setModified(true);
    }
    public String getProVersion()
    {
        return proVersion;
    }
    //CCEnd SS2
}

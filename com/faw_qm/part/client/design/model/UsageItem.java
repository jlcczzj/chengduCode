/** 生成程序 UsageItem.java    1.0    2003/02/22
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
import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.util.QMCt;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
/**
 * UsageItem 是一个用来处理零部件使用关系的模型类，
 * 为了在浏览器中使用,它实现了Explorable接口。
 * @author 吴先超
 * @see Explorable
 * @version 1.0
 */
public class UsageItem implements Explorable, Updateable
{
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
    private transient PartItem uses = null;


    /**被使用者零部件*/
    private PartItem usedBy = null;


    /**十进制数据*/
    static DecimalFormat decFormat = null;


    /**客户端资源文件路径*/
    private static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";


    /**
     * 创建一个空的使用项目。
     */
    public UsageItem()
    {
        super();
    }


    /**
     * 在指定的PartItem对象间建立一个UsageItem对象。
     * @param partitem PartItem uses 使用者零部件。
     * @param partitem1 PartItem usedBy 被使用者零部件。
     * @see PartItem
     */
    public UsageItem(PartItem partitem, PartItem partitem1)
    {
        super();
        PartDebug.debug("UsageItem(PartItem, PartItem) begin ....", this,
                        PartDebug.PART_CLIENT);
        uses = partitem;
        usedBy = partitem1;
        if(usageLinkIfc!=null) 
        {
            quantityUnit = usageLinkIfc.getDefaultUnit();
        }
        PartDebug.debug("UsageItem(PartItem, PartItem) end....return is null", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * 设置使用者图标。
     */
    public void setIcon()
    {
        uses.setIcon();
    }


    /**
     * 使用指定的使用联接在指定的零件项目之间建立一个使用条款。
     * @param partitem PartItem uses 使用者零部件。
     * @param partitem1 PartItem usedBy 被使用者零部件。
     * @param usageLinkIfc1 QMPartUsageLinkIfc 使用关联值对象。
     */
    public UsageItem(PartItem partitem, PartItem partitem1,
                     PartUsageLinkIfc usageLinkIfc1)
    {
        super();
        PartDebug.debug(
                "UsageItem(PartItem, PartItem, PartUsageLinkIfc) begin ....", this,
                PartDebug.PART_CLIENT);

        if (null == usageLinkIfc1)
        {
            PartDebug.debug(
                    "UsageItem(PartItem, PartItem, PartUsageLinkIfc) end....return is null", this,
                    PartDebug.PART_CLIENT);
            return;
        }

        usageLinkIfc = usageLinkIfc1;
        try
        {
            linkID = usageLinkIfc.getBsoID();
        }
        catch (Exception exception)
        {
        }
        quantity = usageLinkIfc.getQuantity();

        quantityUnit = usageLinkIfc.getDefaultUnit();
        //CCBegin SS1
        subUnitNumber = usageLinkIfc.getSubUnitNumber();
        bomItem = usageLinkIfc.getBomItem();
        //CCEnd SS1
        //CCBegin SS2
        proVersion = usageLinkIfc.getProVersion();
        //CCEnd SS2
        uses = partitem;
        usedBy = partitem1;
        PartDebug.debug(
                "UsageItem(PartItem, PartItem, PartUsageLinkIfc) end....return is null", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * 设置修改的标识。
     * @param flag boolean 修改标识。
     */
    public void setModified(final boolean flag)
    {
        modified = flag;
    }


    /**
     * 返回是否被修改的标识。
     * @return boolean 修改标识。
     */
    public boolean isModified()
    {
        return modified;
    }


    /**
     * 使用包含的信息创建一个新的使用联接,为空。
     * @throws QMRemoteException
     */
    public void create()
            throws QMRemoteException
    {

    }


    /**
     * 更新使用联接。
     * @throws QMRemoteException
     */
    public void update()
            throws QMRemoteException
    {

        PartDebug.debug("update() begin ....", this, PartDebug.PART_CLIENT);
        PartDebug.debug("当前的PartUsageLinkIfc是::");
        if (null != usageLinkIfc)
        {
            PartDebug.debug("   " + usageLinkIfc.getBsoID());
        }
        PartDebug.debug("-----------------------------------");

        if (modified)
        {
            try
            {
                PartDebug.debug("---------------------------------------------");
                PartDebug.debug("更新前的partUsageLinkIfc是::");
                PartDebug.debug("   bsoID:   " + usageLinkIfc.getBsoID() +
                                "   quantity:   " + usageLinkIfc.getQuantity() +
                                "   unit:   "
                                + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("---------------------------------------------");
                final QMQuantity quantity1 = new QMQuantity();

                //重新设置使用数量。
                quantity1.setQuantity(quantity);

                //重新设置使用的数量单位。
                quantity1.setDefaultUnit(quantityUnit);
                usageLinkIfc.setDefaultUnit(quantityUnit);
                usageLinkIfc.setQuantity(quantity);
                usageLinkIfc.setQMQuantity(quantity1);
                //quantity1 = null;
                //CCBegin SS1
                usageLinkIfc.setSubUnitNumber(subUnitNumber);
                usageLinkIfc.setBomItem(bomItem);
                //CCEnd SS1
                //CCBegin SS2
                usageLinkIfc.setProVersion(proVersion);
                //CCEnd SS2
                PartDebug.debug("--------------------------------------------");
                PartDebug.debug("经过更新后的PartUsageLinkIfc是::::::");
                PartDebug.debug("   bsoID:   " + usageLinkIfc.getBsoID() +
                                "   quantity:   " + usageLinkIfc.getQuantity() +
                                "   unit:   "
                                + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("--------------------------------------------");
                PartDebug.debug("下面直接调用PartHelper的saveValueInfo()方法::");

                usageLinkIfc = PartHelper.saveUsageLink(usageLinkIfc);
                final Object aobj[] =
                        {uses.getIdentity(), toString(),
                        usedBy.getIdentity()};

                //ResourceHelper 资源帮助文件。
                final String usage = QMMessage.getLocalizedMessage(RESOURCE,
                        PartDesignViewRB.USAGE, aobj);

                //过程服务类
                ProgressService.setProgressText(usage);
                //usage = null;

                setModified(false);
                PartDebug.debug("update() end....return is null", this,
                                PartDebug.PART_CLIENT);
                return;
            }
            catch (QMRemoteException exception)
            {
                throw exception;
            }
        }
        //end if(modified)
    }


    /**
     * 删除使用联接。
     * @throws QMRemoteException
     */
    public void delete()
            throws QMRemoteException
    {
        PartDebug.debug("delete() begin ....", this, PartDebug.PART_CLIENT);
        try
        {
            PartHelper.deleteUsageLink(usageLinkIfc);
            //usageLinkIfc = null;
            final Object aobj[] =
                    {uses.getIdentity(), usedBy.getIdentity()};
            final String notUsage = QMMessage.getLocalizedMessage(RESOURCE,
                    PartDesignViewRB.NOT_USAGE, aobj);
            ProgressService.setProgressText(notUsage);
            //notUsage = null;
            setModified(false);
            PartDebug.debug("delete() end....return is void ", this,
                            PartDebug.PART_CLIENT);
            return;
        }
        catch (QMRemoteException exception)
        {
            throw exception;
        }
    }


    /**
     * 设置包含的使用联接。
     * @param usageLinkIfc1 PartUsageLinkIfc 使用关联。
     * @see PartUsageLinkInfo
     */
    public void setPartUsageLink(final PartUsageLinkIfc usageLinkIfc1)
    {
        usageLinkIfc = usageLinkIfc1;
    }


    /**
     * 获取包含的使用联接。
     * @return PartUsageLinkIfc 使用关联。
     * @see PartUsageLinkInfo
     */
    public PartUsageLinkIfc getPartUsageLink()
    {
        return usageLinkIfc;
    }


    /**
     * 设置使用零部件项目。
     * @param partitem PartItem 使用者零部件。
     * @see PartItem
     */
    public void setUsesPart(final PartItem partitem)
    {
        uses = partitem;
    }


    /**
     * 获取单前对象使用的零部件项目。
     * @return PartItem 使用者零部件。
     * @see PartItem
     */
    public PartItem getUsesPart()
    {
        return uses;
    }


    /**
     * 设置使用当前对象的零部件项目。
     * @param partitem PartItem 被使用者零部件。
     * @see PartItem
     */
    public void setUsedBy(final PartItem partitem)
    {
        usedBy = partitem;
    }


    /**
     * 获取使用当前对象的零部件项目。
     * @return PartItem 被使用者零部件。
     * @see PartItem
     */
    public PartItem getUsedBy()
    {
        return usedBy;
    }


    /**
     * 获取对这个对象的标识符。
     * @return String  标识符。
     */
    public String getId()
    {
        return linkID;
    }


    /**
     * 设置在使用联接中的数量的总数。
     * @param a_quantity float 总数量。
     */
    public void setQuantity(final float a_quantity)
    {
        quantity = a_quantity;
        setModified(true);
    }


    /**
     * 获取数量显示的字符串。
     * @return String 总数量的字符串表示。
     */
    public String getQuantityString()
    {

        //String quantityStr1 = getDecimalFormat().format(getQuantity());
        String quantityStr =Float.toString(getQuantity());

        if (quantityStr.endsWith(".0"))
        {
            quantityStr = quantityStr.substring(0, quantityStr.length() - 2);

        }

        return quantityStr;
    }


    /**
     * 获取在使用联接中的数量的总数。
     * @return double 总数量。
     */
    public float getQuantity()
    {
        return quantity;
    }


    /**
     * 设置数量的单位。
     * @param a_unit Unit 数量的单位。
     * @see Unit
     */
    public void setUnit(final Unit a_unit)
    {
        quantityUnit = a_unit;
        setModified(true);
    }


    /**
     * 获取单位的字符串的描述，中文信息。
     * @return String 单位。
     */
    public String getUnitName()
    {
        return quantityUnit.getDisplay();
    }


    /**
     * 返回一个字符串,为使用数量+使用单位。
     * @return String 使用数量+使用单位组成的字符串。
     */
    public String toString()
    {
        String quantityDisplay = String.valueOf(quantity);
        if (quantityDisplay.endsWith(".0"))
        {
            quantityDisplay = quantityDisplay.substring(0,
                    quantityDisplay.length() - 2);
        }
        return quantityDisplay + " " + quantityUnit.getDisplay();
    }


    /**
     * 返回被使用零部件项目使用的对象数组。
     * @return Explorable[] 被使用者使用的对象(Explorable)数组。
     * @throws QMRemoteException
     * @see Explorable
     */
    public Explorable[] getUses()
            throws QMRemoteException
    {
        //PartItem中的方法getUses()
//      性能瓶颈。
        return uses.getUses();
    }


    /**
     * 返回使用零部件项目的生命周期状态。
     * @return String 使用者的生命周期状态。
     */
    public String getState()
    {
        return uses.getPart().getLifeCycleState().getDisplay();
    }


    /**
     * 返回使用零部件项目的修订版。
     * @return String 使用者的修订版。
     */
    public String getRevision()
    {
        return uses.getRevision();
    }


    /**
     * 获取使用者零部件的大版本号。
     * @return String 使用者的大版本号。
     */
    public String getVersion()
    {
        return uses.getVersion();
    }


    /**
     * 获取使用者零部件的大版本号。
     * @return String 使用者的大版本号。
     */
    public String getObjectVersion()
    {
        return uses.getVersion();
    }


    /**
     * 获取使用者零部件的编号。
     * @return String 使用者的编号。
     */
    public String getNumber()
    {
        return uses.getNumber();
    }


    /**
     * 获取使用者零部件的编号。
     * @return String 使用者的编号。
     */
    public String getObjectNumber()
    {
        return uses.getNumber();
    }


    /**
     * 获取使用者零部件的类型。中文信息。
     * @return String 使用者的类型。
     */
    public String getType()
    {
        //Unit Type 显示为字符串
        return uses.getType().getDisplay();
    }


    /**
     * 获取使用者零部件的名称。
     * @return String 使用者的名称。
     */
    public String getName()
    {
        return uses.getName();
    }


    /**
     * 获取使用者零部件的名称。
     * @return String 使用者的名称。
     */
    public String getObjectName()
    {
        return uses.getName();
    }


    /**
     * 获取使用者零部件的视图对象。
     * @return ViewObjectIfc 使用者的视图对象。
     * @see ViewObjectInfo
     */
    public ViewObjectIfc getView()
    {
        return uses.getView();
    }


    /**
     * 获取使用者零部件的视图名称。
     * @return String 使用者的视图。
     */
    public String getViewName()
    {
        return uses.getViewName();
    }


    /**
     * 获取使用者零部件的标准图标。
     * @return Image 使用者的标准图标。
     */
    public Image getStandardIcon()
    {
        return uses.getStandardIcon();
    }


    /**
     * 获取使用者零部件的打开图标。
     * @return Image 使用者的打开图标。
     */
    public Image getOpenIcon()
    {
        return uses.getOpenIcon();
    }


    /**
     * 获取使用者零部件的标识。
     * @return String 使用者的标识。
     */
    public String getIdentity()
    {
        return uses.getIdentity();
    }


    /**
     * 获取使用者零部件的唯一标识。
     * @return String 使用者的唯一标识。
     */
    public String getUniqueIdentity()
    {
        return uses.getUniqueIdentity();
    }


    /**
     * 返回使用零部件项目的内容。
     * @return Explorable[] 零部件项目的内容。
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
        }
        return explorable;
    }


    /**
     * 设置包含的对象。
     * @param obj Object 使用者包含的对象。
     */
    public void setObject(final Object obj)
    {
        uses.setObject(obj);
    }


    /**
     * 实现 ReferenceHolder 接口。
     * 获取包含的对象；
     * @return Object 使用者包含的对象。
     */
    public Object getObject()
    {
        return uses.getObject();
    }


    /**
     * 获取十进制数据格式。
     * @return DecimalFormat 十进制数据。
     */
    public static DecimalFormat getDecimalFormat()
    {
        if (null == decFormat)
        {
            //QMContext????获取本地化信息
            decFormat = (DecimalFormat) NumberFormat.getInstance(QMCt.getContext().
                    getLocale());
            decFormat.applyPattern("########0.######");
        }
        return decFormat;
    }


    //add by liyf
    /**
     * 获取使用者零部件的类型。
     * @return String 使用者的类型。
     */
    public String getProducedBy()
    {
        // 来源显示为字符串
        return uses.getSource().getDisplay();
    }


    /**
     * 获取零部件完整的版本值(大版本号+小版本号)。
     * @return String 使用者的版本值。
     */
    public String getIterationID()
    {
        return uses.getIterationID();
    }


    //add by 熊代军
    /**
     * 返回创建日期。
     * @return Timestamp 创建时间。
     */
    public Timestamp getObjectCreationDate()
    {
        return uses.getCreationDate();
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

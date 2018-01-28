/** 生成程序 UsageInterfaceItem.java    1.0    2003/02/22
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/02 谢斌 原因:增加构造函数参数，直接使用缓存的信息，不再重新刷新值对象
 *                     方案:优化初始化使用结构数据类
 *                     备注:解放v3r11-展开零部件树节点性能优化
 * SS1 增加BOM行项和子组 liuyang 2014-6-20
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.client.design.model;

import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import org.apache.log4j.Logger;
//CCBegin SS2
import com.faw_qm.auth.RequestHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.version.model.MasteredIfc;
import java.util.Collection;
import java.util.Iterator;
//CCEnd SS2

/**
 * 用来处理零部件使用关系的模型类。
 * @author 吴先超
 * @version 1.0
 */
public class UsageInterfaceItem implements Updateable
{
    /**异常信息提示*/
    private final static Logger logger = Logger.getLogger(UsageInterfaceItem.class);


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


    /**零部件值对象*/
    private PartItem usedBy = null;


    /**零部件主信息值对象*/
    private QMPartMasterIfc uses = null;


    /**客户端资源文件路径*/
    private static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    private static int newUsageCounter = 0;
    /**
     * 构造函数。
     */
    public UsageInterfaceItem()
    {
        super();
    }


    /**
     * 构造函数。
     * @param partItem :PartItem 使用者零部件。
     * @param partMasterIfc :QMPartMasterIfc 被使用者零部件主信息。
     * @see PartItem
     * @see QMPartMasterInfo
     */
    public UsageInterfaceItem(PartItem partItem, QMPartMasterIfc partMasterIfc)
    {
        super();
        PartDebug.debug(
                "UsageInterfaceItem(PartItem, QMPartMasterIfc) begin ....", this,
                PartDebug.PART_CLIENT);
        linkID = "New-" + Integer.toString(newUsageCounter++);
        usedBy = partItem;
        uses = partMasterIfc;
        quantityUnit = Unit.getUnitDefault();
        PartDebug.debug(
                "UsageInterfaceItem(PartItem, QMPartMasterIfc) end....return is null", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * 构造函数。
     * @param usage_link :PartUsageLinkIfc 零部件和零部件主信息关联类对象。
     * @see PartUsageLinkInfo
     */
    public UsageInterfaceItem(PartUsageLinkIfc usage_link)
    {
        super();
        PartDebug.debug("UsageInterfaceItem(PartUsageLinkIfc) begin ....", this,
                        PartDebug.PART_CLIENT);

        usageLinkIfc = usage_link;
        if (null == usage_link)
        {
            PartDebug.debug(
                    "UsageInterfaceItem(PartUsageLinkIfc) end....return is null ", this,
                    PartDebug.PART_CLIENT);
            return;
        }
        try
        {
            linkID = usageLinkIfc.getBsoID();
        }
        catch (Exception exception)
        {
            logger.error(exception);
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
        usedBy = new PartItem((QMPartIfc) usageLinkIfc.getUsedBy());
        uses = (QMPartMasterIfc) usageLinkIfc.getUses();
        
        PartDebug.debug(
                "UsageInterfaceItem(PartUsageLinkIfc) end....return is null", this,
                PartDebug.PART_CLIENT);
    }

    /**
     * 构造函数。三个参数。
     * @param partUsageLinkIfc 零部件和零部件主信息关联类对象。
     * @param partItem PartItem 使用者零部件。
     * @param partMasterIfc QMPartMasterIfc 被使用者零部件主信息。
     */
    public UsageInterfaceItem(PartUsageLinkIfc partUsageLinkIfc, PartItem partItem, QMPartMasterIfc partMasterIfc) {//Begin CR1
        super();
        if (null == partUsageLinkIfc) {
            return;
        }
        usageLinkIfc = partUsageLinkIfc;
        linkID = usageLinkIfc.getBsoID();
        quantity = usageLinkIfc.getQuantity();
        quantityUnit = usageLinkIfc.getDefaultUnit();
        //CCBegin SS1
        subUnitNumber = usageLinkIfc.getSubUnitNumber();
        bomItem = usageLinkIfc.getBomItem();
        //CCEnd SS1
        //CCBegin SS2
        proVersion = usageLinkIfc.getProVersion();
        //CCEnd SS2
        usedBy = partItem;
        uses = partMasterIfc;
    }//End CR1
    
    /**
     * 设置修改的标识。
     * @param flag boolean 是否修改的标识。
     */
    public void setModified(boolean flag)
    {
        modified = flag;
    }


    /**
     * 返回是否被修改的标识。
     * @return boolean 是否修改的标识。
     */
    public boolean isModified()
    {
        return modified;
    }


    /**
     * 创建对象。
     * @throws QMRemoteException
     */
    public void create()
            throws QMRemoteException
    {
        PartDebug.debug("create() begin ....", this, PartDebug.PART_CLIENT);
        try
        {
            //调用构造函数。
            usageLinkIfc = new PartUsageLinkInfo(uses.getBsoID(),
                                                 usedBy.getPart().getBsoID());
            QMQuantity quantity1 = new QMQuantity();
            quantity1.setQuantity(quantity);
//            if(isUnit)
//            {
            	quantity1.setDefaultUnit(quantityUnit);
//            }
//            else
//            {
//            	quantity1.setDefaultUnit(Unit.getUnitDefault());
//            	usageLinkIfc.setDefaultUnit(Unit.getUnitDefault());
//            }
//            if(isQuantity)
//            {
            	usageLinkIfc.setQMQuantity(quantity1);
                usageLinkIfc.setQuantity(quantity);
            	usageLinkIfc.setDefaultUnit(quantityUnit);
            	 //CCBegin SS1
            	  usageLinkIfc.setSubUnitNumber(subUnitNumber);
                  usageLinkIfc.setBomItem(bomItem);
                //CCEnd SS1
				        //CCBegin SS2
        				try
        				{
									Class[] theClass = { String.class };
									Object[] theObjs = { usageLinkIfc.getLeftBsoID() };
									RequestHelper requestHelper = new RequestHelper();
									QMPartMasterIfc master = (QMPartMasterIfc) requestHelper.request("PersistService", "refreshInfo", theClass, theObjs);
	                boolean flag=false;
	                if (proVersion==null || proVersion.equals(""))
	                {
	                	flag=true;
	                }
	                else
	                {
	                	System.out.println("--------"+master.getPartNumber()+"++++"+proVersion+"||||");
										Class[] theClass1 = { MasteredIfc.class };
										Object[] theObjs1 = { master };
										RequestHelper requestHelper1 = new RequestHelper();
										Collection partInfoVector = (Collection) requestHelper1.request("VersionControlService", "allIterationsOf", theClass1, theObjs1);
        						for (Iterator queryresult1 =partInfoVector.iterator(); queryresult1.hasNext(); )
        						{
            					QMPartIfc partIfc = (QMPartIfc) queryresult1.next();
	                	System.out.println("==========="+partIfc.getVersionID());
		                	if ( partIfc.getVersionID().equals(proVersion))
		                	{
		                		flag=true;
		                		break;
		                	}
		                }
	              	}
	                if  (!flag)
	                {
	                	setErrorNumber(master.getPartNumber());
	                	return;
	                }
	                else       
	                	usageLinkIfc.setProVersion(proVersion);
      					}catch(QMException ex)
        				{
        					ex.printStackTrace();
				        }
				        //CCEnd SS2
//            }
//            else
//            {
//            	quantity1.setQuantity(1.0f);
//            	usageLinkIfc.setQMQuantity(quantity1);
//            	 usageLinkIfc.setQuantity(1.0f);       
//            }
            usageLinkIfc = (PartUsageLinkIfc) PartHelper.saveUsageLink(
                    usageLinkIfc);
            final Object aobj[] =
                    {usedBy.getIdentity(), toString(), uses.getIdentity()};
            final String usage = QMMessage.getLocalizedMessage(RESOURCE,
                    PartDesignViewRB.USAGE, aobj);
            ProgressService.setProgressText(usage);
            setModified(false);
            PartDebug.debug("create() end....return is null", this,
                            PartDebug.PART_CLIENT);
            return;
        }
        catch (QMRemoteException exception)
        {
            throw exception;
        }
    }


    /**
     * 更新使用关联。
     * @throws QMRemoteException
     */
    public void update()
            throws QMRemoteException
    {
        PartDebug.debug("update() begin ....", this, PartDebug.PART_CLIENT);
        if (modified)
        {
            try
            {
                final QMQuantity quantity1 = new QMQuantity();
                quantity1.setQuantity(quantity);
//                if(isUnit)
//                {
                	quantity1.setDefaultUnit(quantityUnit);
//                }
//                if(isQuantity)
//                {
                
                
                usageLinkIfc.setQMQuantity(quantity1);
                usageLinkIfc.setQuantity(quantity);
            	usageLinkIfc.setDefaultUnit(quantityUnit);
            	 //CCBegin SS1
                 usageLinkIfc.setSubUnitNumber(subUnitNumber);
                 usageLinkIfc.setBomItem(bomItem);
                //CCEnd SS1
            	 //CCBegin SS2
        				try
        				{
									Class[] theClass = { String.class };
									Object[] theObjs = { usageLinkIfc.getLeftBsoID() };
									RequestHelper requestHelper = new RequestHelper();
									QMPartMasterIfc master = (QMPartMasterIfc) requestHelper.request("PersistService", "refreshInfo", theClass, theObjs);
	/*                 //获得当前的筛选条件项
	                ConfigSpecItem configSpecItem = usedBy
	                        .getConfigSpecItem();
	                //如果筛选条件项为空，获得当前客户端的筛选条件
	                if(configSpecItem == null)
	                {
	                    PartConfigSpecIfc partConfigSpecIfc = null;
	                    // 获得最近保存的筛选条件
	                    try
	                    {
	                        partConfigSpecIfc = (PartConfigSpecIfc) PartHelper
	                                .getConfigSpec();
	                    }
	                    catch (QMRemoteException qre)
	                    {
	                        //String title = QMMessage.getLocalizedMessage(RESOURCE,
	                        //        "errorTitle", null);
	                        //JOptionPane.showMessageDialog(getParentFrame(), qre
	                        //        .getClientMessage(), title,
	                        //        JOptionPane.ERROR_MESSAGE);
	                    }
	                    //如果筛选条件不为空，用筛选条件构造筛选条件项，否则新建筛选条件项
	                    if(partConfigSpecIfc != null)
	                    {
	                        configSpecItem = new ConfigSpecItem(partConfigSpecIfc);
	                    }
	                    else
	                    {
	                        configSpecItem = new ConfigSpecItem();
	                    }
	                }
	                //根据零部件主信息获取该零部件的所有符合配置规范的版本
	                Vector partInfoVector = PartHelper.getAllVersions(master,
	                        configSpecItem.getConfigSpecInfo());
*/
	                boolean flag=false;
	                if (proVersion==null || proVersion.equals(""))
	                {
	                	flag=true;
	                }
	                else
	                {
										Class[] theClass1 = { MasteredIfc.class };
										Object[] theObjs1 = { master };
										RequestHelper requestHelper1 = new RequestHelper();
										Collection partInfoVector = (Collection) requestHelper1.request("VersionControlService", "allIterationsOf", theClass1, theObjs1);
        						for (Iterator queryresult1 =partInfoVector.iterator(); queryresult1.hasNext(); )
        						{
            					QMPartIfc partIfc = (QMPartIfc) queryresult1.next();
	//	                for (int j=0; j<partInfoVector.size(); j++)
	//	                {
	//	                	Object[] obj=(Object[])partInfoVector.elementAt(j);
	//	                	QMPartIfc partIfc=(QMPartIfc)obj[0];
		                	if ( partIfc.getVersionID().equals(proVersion))
		                	{
		                		flag=true;
		                		break;
		                	}
		                }
	              	}
	                if  (!flag)
	                {
	                	setErrorNumber(master.getPartNumber());
	                	return;
//	                	JOptionPane.showMessageDialog(null, "xlx添加的身份编号不存在！");  
	                }
	                else       
	                	usageLinkIfc.setProVersion(proVersion);
      					}catch(QMException ex)
        				{
        					ex.printStackTrace();
				        }
                //CCEnd SS2
//                }
                //quantity1 = null;
                PartDebug.debug("---------------------------------------------");
                PartDebug.debug("经过修改后的,还没有保存的。PartUsageLinkIfc是:::");
                PartDebug.debug("    " + usageLinkIfc.getLeftBsoID() + "    " +
                                usageLinkIfc.getRightBsoID()
                                + "  quantity: " + usageLinkIfc.getQuantity() +
                                "   Unit:" + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("---------------------------------------------");                
                usageLinkIfc = (PartUsageLinkIfc) PartHelper.saveUsageLink(
                        usageLinkIfc);

                PartDebug.debug("--------------------------------------------");
                PartDebug.debug("保存后的  PartUsageLinkIfc是:::");
                PartDebug.debug("   " + usageLinkIfc.getLeftBsoID() + "   " +
                                usageLinkIfc.getRightBsoID()
                                + "  quantity: " + usageLinkIfc.getQuantity() +
                                "   Unit:" + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("--------------------------------------------");

                final Object aobj[] =
                        {usedBy.getIdentity(), toString(),
                        uses.getIdentity()};

                final String usage = QMMessage.getLocalizedMessage(RESOURCE,
                        PartDesignViewRB.USAGE, aobj);
                ProgressService.setProgressText(usage);
                //usage = null;
                setModified(false);
                PartDebug.debug("update() end....return is null", this,
                                PartDebug.PART_CLIENT);
            }
            catch (QMRemoteException exception)
            {
                throw exception;
            }
        }
        else
        {
            PartDebug.debug("update() end....return is null", this,
                            PartDebug.PART_CLIENT);
        }
    }


    /**
     * 删除关联关系值对象。
     * @exception QMRemoteException
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
                    {usedBy.getIdentity(), uses.getIdentity()};
            final String notUsage = QMMessage.getLocalizedMessage(RESOURCE,
                    PartDesignViewRB.NOT_USAGE, aobj);
            ProgressService.setProgressText(notUsage);
            //notUsage = null;
            setModified(false);
            PartDebug.debug("delete() end....return is null", this,
                            PartDebug.PART_CLIENT);
            return;
        }
        catch (QMRemoteException exception)
        {
            logger.error(exception);
            throw exception;
        }
    }


    /**
     * 设置包含的使用联接。
     * @param usage_link PartUsageLinkIfc 使用关联值对象。
     * @see PartUsageLinkInfo
     */
    public void setPartUsageLink(final PartUsageLinkIfc usage_link)
    {
        usageLinkIfc = usage_link;
    }


    /**
     * 获取包含的使用联接。
     * @return PartUsageLinkIfc 使用关联值对象。
     * @see PartUsageLinkInfo
     */
    public PartUsageLinkIfc getPartUsageLink()
    {
        return usageLinkIfc;
    }


    /**
     * 设置使用零部件项目。
     * @param partItem PartItem 零部件值对象。
     * @see PartItem
     */
    public void setUses(final PartItem partItem)
    {
        usedBy = partItem;
    }


    /**
     * 返回被使用零部件项目使用的对象数组。
     * @return PartItem 零部件值对象。
     * @see PartItem
     */
    public PartItem getUses()
    {
        return usedBy;
    }


    /**
     * 设置使用当前对象的零部件项目。
     * @param partMasterIfc QMPartMasterIfc 零部件主信息。
     * @see QMPartMasterInfo
     */
    public void setUsedBy(final QMPartMasterIfc partMasterIfc)
    {
        uses = partMasterIfc;
    }


    /**
     * 获取使用当前对象的零部件项目。
     * @return QMPartMasterIfc 零部件主信息。
     * @see QMPartMasterInfo
     */
    public QMPartMasterIfc getUsedBy()
    {
        return uses;
    }


    /**
     * 获取标识。
     * @return String 标识。
     */
    public String getId()
    {
        return linkID;
    }


    /**
     * 设置在使用联接中的数量的总数。
     * @param a_quantity float 总数量。
     */
    public void setQuantity(float a_quantity)
    {
        quantity = a_quantity;
        setModified(true);
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
     * 获取数量显示的字符串。
     * @return String 显示的数量。
     */
    public String getQuantityString()
    {
        //默认精度取小数点后四位,第五位四舍五入
        final String QuanStr = String.valueOf(getQuantity());
        return QuanStr;
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
     * 获取数量的单位。
     * @return QuantityUnit 数量的单位。
     * @see Unit
     */
    public Unit getUnits()
    {
        return quantityUnit;
    }


    /**
     * 返回一个字符串,为使用数量+使用单位。
     * @return String 使用数量+使用单位组成的字符串。
     */
    public String toString()
    {
        String quantityDisplay ="";
        String unit="";
//        if(isQuantity)
//        {
        quantityDisplay=String.valueOf(quantity);
        if (quantityDisplay.endsWith(".0"))
        {
            quantityDisplay = quantityDisplay.substring(0,
                    quantityDisplay.length() - 2);
        }
//        }
//        if(isUnit&&quantityUnit!=null)
//        {
        	unit=quantityUnit.getDisplay();
//        }
        return quantityDisplay + " " +unit ;
    }


    /**
     * 获取使用者零部件的标识。
     * @return String 零部件的标识。
     */
    public String getIdentity()
    {
        return usedBy.getIdentity();
    }
//    boolean isUnit=false;
//    boolean isQuantity=false;
//   public void setUnitExist(boolean flag)
//   {
//	   isUnit=flag;
//   }
//   public void setQuantityExist(boolean flag)
//   {
//	   isQuantity=flag;
//   }
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

    private String errorNumber=null;
    public void setErrorNumber(String a_errorNumber)
    {
        errorNumber = a_errorNumber;
    }
    public String getErrorNumber()
    {
        return errorNumber;
    }
    //CCEnd SS2
}

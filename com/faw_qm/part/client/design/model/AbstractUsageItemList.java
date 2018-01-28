/** 生成程序 UsageItemList.java    1.0    2003/02/22
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *  SS2 增加生产版本 xianglx 2014-8-12
 */

package com.faw_qm.part.client.design.model;

import java.util.ArrayList;
import java.util.Vector;

import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.part.util.PartDebug;
import org.apache.log4j.Logger;


/**
 * 由主零部件的使用关系组成的集合类。
 * @author 吴先超
 * @version 1.0
 */
public abstract class AbstractUsageItemList extends Vector
{
    /**异常信息提示*/
    private final static Logger logger = Logger.getLogger(AbstractUsageItemList.class);


    /**框架的开始标识*/
    protected static boolean frameStarted = false;


    /**删除使用关系集合*/
    protected ArrayList removedUsage = new ArrayList();


    /**新增加的零部件使用关系集合*/
    protected ArrayList addedUsage = new ArrayList();


    /**封装的零部件封装类对象*/
    protected PartItem part = null;


    /**
     * 构造函数。
     */
    public AbstractUsageItemList()
    {
        super();
    }


    /**
     * 构造函数。
     * @param partitem PartItem 封装的零部件类对象。
     */
    public AbstractUsageItemList(PartItem partitem)
    {
        super();
        part = partitem;
    }


    /**
     * 保存从最近开始的框架的所有已经成功的变化。
     * @return boolean 修改的标识。
     * @throws QMRemoteException
     */
    public boolean saveFrame()
            throws QMRemoteException
    {
        PartDebug.debug("saveFrame() begin ....", this, PartDebug.PART_CLIENT);
        boolean flag = false;
        int addFlag = 0;
//CCBegin SS2
		 		String errorInfo="";
//CCEnd SS2
        try
        {
            final int removeSize = removedUsage.size();
            for (int k = 0; k < removeSize; k++)
            {
                ((Updateable) removedUsage.get(k)).delete();
                flag = true;
            }

            final int addSize = addedUsage.size();
            for (int l = 0; l < addSize; l++)
            {
//CCBegin SS2
                Updateable updateable=(Updateable) addedUsage.get(l);
                updateable.create();
                if (updateable instanceof UsageInterfaceItem)
                {
                	String errorNumber=((UsageInterfaceItem)updateable).getErrorNumber();
                	if (errorNumber!=null)
                		errorInfo=errorInfo+errorNumber+",";
              	}
                //((Updateable) addedUsage.get(l)).create();
//CCEnd SS2
                addFlag++;
                flag = true;
            }

            final int thisSize = size();
//CCBegin SS2
            if (thisSize>0)
            	errorInfo="";
//CCEnd SS2
            for (int m = 0; m < thisSize; m++)
            {
//            	if( elementAt(m) instanceof UsageInterfaceItem)
//            	{
//            		((UsageInterfaceItem) elementAt(m)).setUnitExist(isUnit);
//            		((UsageInterfaceItem) elementAt(m)).setQuantityExist(isQuantity);
//            	}
//CCBegin SS2
                Updateable updateable=(Updateable) elementAt(m);
                updateable.update();
                if (updateable instanceof UsageInterfaceItem)
                {
                	String errorNumber=((UsageInterfaceItem)updateable).getErrorNumber();
                	if (errorNumber!=null)
                		errorInfo=errorInfo+errorNumber+",";
              	}
                //((Updateable) elementAt(m)).update();
//CCEnd SS2
                flag = true;
            }
//CCBegin SS2
	          if (errorInfo!=null && errorInfo.length()>0)
	          	javax.swing.JOptionPane.showMessageDialog(null, "子零件："+errorInfo+" 生产版本设置错误！");  
//CCEnd SS2
            startFrame();
        }
        catch (QMRemoteException exception)
        {
            final int addedSize = addedUsage.size();
            if ((null != addedUsage) && (addFlag < addedSize))
            {
                ArrayList arrayList = new ArrayList();
                arrayList.add(addedUsage.get(addFlag));
                addedUsage = arrayList;
            }
            logger.error(exception);
            throw exception;
        }
        PartDebug.debug("saveFrame() end....return is boolean ", this,
                        PartDebug.PART_CLIENT);
        return flag;
    }


    /**
     * 框架开始,对几个属性进行初始化。
     */
    public void startFrame()
    {
        removedUsage = new ArrayList();
        addedUsage = new ArrayList();
        frameStarted = true;
    }


    /**
     * 框架结束。
     */
    public void endFrame()
    {
        removedUsage = new ArrayList();
        addedUsage = new ArrayList();
        frameStarted = false;
    }


    /**
     * 抽象方法。
     * @param para String 指定元素。
     * @return UsageItem 被使用者零部件。
     */
    public abstract UsageItem elementAt(String para);


    /**
     * 移除元素。
     * @param para String 移除的元素。
     */
    public void removeElementAt(final String para)
    {
        final UsageItem usageitem = elementAt(para);
        if (frameStarted && (!addedUsage.remove(usageitem)))
        {
            removedUsage.add(usageitem);
        }
        removeElement(usageitem);
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
}

/** ���ɳ��� UsageItemList.java    1.0    2003/02/22
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *  SS2 ���������汾 xianglx 2014-8-12
 */

package com.faw_qm.part.client.design.model;

import java.util.ArrayList;
import java.util.Vector;

import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.part.util.PartDebug;
import org.apache.log4j.Logger;


/**
 * �����㲿����ʹ�ù�ϵ��ɵļ����ࡣ
 * @author ���ȳ�
 * @version 1.0
 */
public abstract class AbstractUsageItemList extends Vector
{
    /**�쳣��Ϣ��ʾ*/
    private final static Logger logger = Logger.getLogger(AbstractUsageItemList.class);


    /**��ܵĿ�ʼ��ʶ*/
    protected static boolean frameStarted = false;


    /**ɾ��ʹ�ù�ϵ����*/
    protected ArrayList removedUsage = new ArrayList();


    /**�����ӵ��㲿��ʹ�ù�ϵ����*/
    protected ArrayList addedUsage = new ArrayList();


    /**��װ���㲿����װ�����*/
    protected PartItem part = null;


    /**
     * ���캯����
     */
    public AbstractUsageItemList()
    {
        super();
    }


    /**
     * ���캯����
     * @param partitem PartItem ��װ���㲿�������
     */
    public AbstractUsageItemList(PartItem partitem)
    {
        super();
        part = partitem;
    }


    /**
     * ����������ʼ�Ŀ�ܵ������Ѿ��ɹ��ı仯��
     * @return boolean �޸ĵı�ʶ��
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
	          	javax.swing.JOptionPane.showMessageDialog(null, "�������"+errorInfo+" �����汾���ô���");  
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
     * ��ܿ�ʼ,�Լ������Խ��г�ʼ����
     */
    public void startFrame()
    {
        removedUsage = new ArrayList();
        addedUsage = new ArrayList();
        frameStarted = true;
    }


    /**
     * ��ܽ�����
     */
    public void endFrame()
    {
        removedUsage = new ArrayList();
        addedUsage = new ArrayList();
        frameStarted = false;
    }


    /**
     * ���󷽷���
     * @param para String ָ��Ԫ�ء�
     * @return UsageItem ��ʹ�����㲿����
     */
    public abstract UsageItem elementAt(String para);


    /**
     * �Ƴ�Ԫ�ء�
     * @param para String �Ƴ���Ԫ�ء�
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

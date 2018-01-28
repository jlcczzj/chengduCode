/** ���ɳ��� UsageMasterItem.java    1.0    2003/02/22
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
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
 * UsageMasterItem��һ�����������㲿��ʹ�ù�ϵ��ģ���ࡣ
 * @author ���ȳ�
 * @version 1.0
 *
 */
// ����(1)20080226 ��ǿ�޸� �޸�ԭ��:��ճ�����������ù淶���㲿��ʱ���޷���ʾʹ�õ�λ��
public class UsageMasterItem implements Explorable
{
    /**�쳣��Ϣ��ʾ*/
    private final static Logger logger = Logger.getLogger(UsageMasterItem.class);


    /**�㲿�����㲿������Ϣ�Ĺ�����ϵֵ����*/
    private transient PartUsageLinkIfc usageLinkIfc = null;


    /**�Ƿ��޸ĵı�ʶ*/
    private boolean modified = false;


    /**ʹ�ù�����bsoID*/
    private transient String linkID = null;


    /**ʹ������*/
    private float quantity = 1.0f;


    /**ʹ�������ĵ�λ*/
    private transient Unit quantityUnit = null;


    /**ʹ�����㲿��*/
    private PartMasterItem uses = null;


    /**
     * ���캯��������һ���յ�ʹ�ù�ϵ��
     */
    public UsageMasterItem()
    {
        super();
        modified = false;
    }


    /**
     * ���캯����ʹ��ָ����ʹ����������ָ�����㲿����Ŀ֮�䴴��һ��ʹ����Ŀ��
     * @param partmasteritem PartMasterItem ʹ�����㲿����
     * @param usageLinkIfc1 PartUsageLinkIfc ʹ�ù�����
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
        //����(1)20080226 zhangq begin:��ճ�����������ù淶���㲿��ʱ���޷���ʾʹ�õ�λ��
        //quantityUnit = (usageLinkIfc.getQMQuantity()).getDefaultUnit();
        quantityUnit = usageLinkIfc.getDefaultUnit();
      //CCBegin SS1
        subUnitNumber = usageLinkIfc.getSubUnitNumber();
        bomItem = usageLinkIfc.getBomItem();
        //CCEnd SS1
      //CCBegin SS2
        proVersion = usageLinkIfc.getProVersion();
        //CCEnd SS2
        //����(1)20080226 zhangq end
        uses = partmasteritem;
        PartDebug.debug("UsageMasterItem() end....return is UsageMasterItem", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * �����޸ı�ʶ��
     * @param flag boolean �Ƿ��޸ı�ʶ��
     */
    public void setModified(final boolean flag)
    {
        modified = flag;
    }


    /**
     * �����Ƿ��޸ĵı�ʶ��
     * @return boolean �Ƿ��޸ı�ʶ��
     */
    public boolean isModified()
    {
        return modified;
    }


    /**
     * ����ʹ�ù�ϵ����:usageLinkIfc��
     * @param partUsageLinkIfc PartUsageLinkIfc ʹ�ù�����
     * @see PartUsageLinkIfc
     */
    public void setPartUsageLink(final PartUsageLinkIfc partUsageLinkIfc)
    {
        usageLinkIfc = partUsageLinkIfc;
    }


    /**
     * ��ȡʹ�ù�ϵ����:usageLinkIfc��
     * @return PartUsageLinkIfc ʹ�ù�����
     * @see PartUsageLinkIfc
     */
    public PartUsageLinkIfc getPartUsageLink()
    {
        return usageLinkIfc;
    }


    /**
     * ����ʹ���㲿����Ŀ��
     * @param partmasteritem PartMasterItem ʹ�����㲿����
     * @see PartMasterItem
     */
    public void setUses(final PartMasterItem partmasteritem)
    {
        uses = partmasteritem;
    }


    /**
     * �������ʹ���㲿����Ŀʹ�õĶ������顣
     * @return Explorable[] ��ʹ����ʹ�õĶ������顣
     * @throws QMRemoteException
     * @see Explorable
     */
    public Explorable[] getUses()
            throws QMRemoteException
    {
        Explorable explorable[] = null;
        try
        {
//          ����ƿ����
            explorable = uses.getUses();
        }
        catch (Exception ex)
        {
            logger.error(ex);
        }
        return explorable;
    }


    /**
     * ����ʹ���㲿����Ŀ��
     * @return PartMasterItem ʹ�����㲿����
     * @see PartMasterItem
     */
    public PartMasterItem getUsesPart()
    {
        return uses;
    }


    /**
     * ��ȡ���������ı�ʶ��
     * @return String ��ʶ����
     */
    public String getId()
    {
        return linkID;
    }


    /**
     * ������ʹ�ù�ϵ��������
     * @param quantity1 :float ��������
     */
    public void setQuantity(final float quantity1)
    {
        quantity = quantity1;
        setModified(true);
    }


    /**
     * ��ȡ��ʹ�������е�������
     * @return double ��������
     */
    public float getQuantity()
    {

        return quantity;
    }


    /**
     * ��ȡ������ʾ���ַ�����
     * @return String ���������ַ�����ʾ��
     */
    public String getQuantityString()
    {
        final String quanStr = UsageItem.getDecimalFormat().format(getQuantity());
        return quanStr;
    }


    /**
     * ����ʹ�������ĵ�λ��
     * @param a_unit Unit �����ĵ�λ��
     * @see Unit
     */
    public void setUnit(final Unit a_unit)
    {
        quantityUnit = a_unit;
        setModified(true);
    }


    /**
     * ��ȡ�����ĵ�λ��
     * @return QuantityUnit �����ĵ�λ��
     * @see Unit
     */
    public Unit getUnits()
    {
        return quantityUnit;
    }


    /**
     * ��ȡ��λ���ַ�����������
     * @return String ��λ��
     */
    public String getUnitName()
    {
        return quantityUnit.getDisplay();
    }


    /**
     * ����һ���ַ���:ʹ������+ʹ�õ�λ��
     * @return String ʹ������+ʹ�õ�λ��ɵ��ַ�����
     */
    public String toString()
    {
        return quantity + " " + quantityUnit;
    }


    /**
     * ����ʹ���㲿����Ŀ�ı�׼ͼ�ꡣ
     * @return Image ��׼ͼ�ꡣ
     */
    public Image getStandardIcon()
    {
        return uses.getStandardIcon();
    }


    /**
     * ����ʹ���㲿����Ŀ��չ����ͼ�ꡣ
     * @return Image ��׼ͼ�ꡣ
     */
    public Image getOpenIcon()
    {
        return uses.getOpenIcon();
    }


    /**
     * ����ʹ���㲿����Ŀ�ı�ʶ��
     * @return String Ψһ��ʶ��
     */
    public String getIdentity()
    {
        return uses.getNumber() + "-" + uses.getName();
    }


    /**
     * ����ʹ���㲿����Ŀ��Ψһ��ʶ��
     * @return String Ψһ��ʶ��
     */
    public String getUniqueIdentity()
    {
        return uses.getUniqueIdentity();
    }


    /**
     * ����ʹ���㲿����Ŀ�����ݡ�
     * @return Explorable[] �㲿����Ŀ���������顣
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
     * ʵ�� ReferenceHolder�ӿ��еķ�����
     * ��ȡ�����Ķ���
     * @return Object ʹ���߰����Ķ���
     */
    public Object getObject()
    {
        return uses.getObject();
    }


    /**
     * ���ø���ķ�����������Ĳ������浽����Ĳ�����ȥ��
     * @param obj Object ʹ���߰����Ķ���
     */
    public void setObject(final Object obj)
    {
        uses.setObject(obj);
    }


    /**
     * �������ʹ���㲿����Ŀ�����֡�
     * @return String �㲿����Ŀ����
     */
    public String getName()
    {
        return uses.getName();
    }


    /**
     * �������ʹ���㲿����Ŀ�����֡�
     * @return String �㲿����Ŀ����
     */
    public String getObjectName()
    {
        return uses.getName();
    }


    /**
     * �������ʹ���㲿����Ŀ�ı�š�
     * @return String �㲿����Ŀ��š�
     */
    public String getNumber()
    {
        return uses.getNumber();
    }


    /**
     * �������ʹ���㲿����Ŀ�ı�š�
     * @return String �㲿����Ŀ��š�
     */
    public String getObjectNumber()
    {
        return uses.getNumber();
    }


    //add by �ܴ���
    /**
     * ��ȡ����ʱ�䡣
     * @return Timestamp ����ʱ�䡣
     */
    public Timestamp getObjectCreationDate()
    {
        return uses.getObjectCreationDate();
    }


    /**
     * ��ȡ����汾��
     * @return String �汾��
     */
    public String getObjectVersion()
    {
        return "A.0";
    }
    //CCBegin��SS1
    private String subUnitNumber = new String(" ");
    private String bomItem = new String(" ");
    
    /**
     * ������ʹ�������е�����š�
     */
    public void setSubUnitNumber(String a_subUnitNumber)
    {
        subUnitNumber = a_subUnitNumber;
        setModified(true);
    }


    /**
     * ��ȡ��ʹ�������е�����š�
     */
    public String getSubUnitNumber()
    {
        return subUnitNumber;
    }

    
    /**
     * ������ʹ�������еĽṹ��ע��
     */
    public void setBomItem(String a_bomItem)
    {
    	bomItem = a_bomItem;
        setModified(true);
    }


    /**
     * ��ȡ��ʹ�������еĽṹ��ע��
     */
    public String getBomItem()
    {
        return bomItem;
    }
    //CCEnd SS1
    //CCBegin��SS2
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

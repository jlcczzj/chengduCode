/** ���ɳ��� UsageItem.java    1.0    2003/02/22
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
 * UsageItem ��һ�����������㲿��ʹ�ù�ϵ��ģ���࣬
 * Ϊ�����������ʹ��,��ʵ����Explorable�ӿڡ�
 * @author ���ȳ�
 * @see Explorable
 * @version 1.0
 */
public class UsageItem implements Explorable, Updateable
{
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
    private transient PartItem uses = null;


    /**��ʹ�����㲿��*/
    private PartItem usedBy = null;


    /**ʮ��������*/
    static DecimalFormat decFormat = null;


    /**�ͻ�����Դ�ļ�·��*/
    private static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";


    /**
     * ����һ���յ�ʹ����Ŀ��
     */
    public UsageItem()
    {
        super();
    }


    /**
     * ��ָ����PartItem����佨��һ��UsageItem����
     * @param partitem PartItem uses ʹ�����㲿����
     * @param partitem1 PartItem usedBy ��ʹ�����㲿����
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
     * ����ʹ����ͼ�ꡣ
     */
    public void setIcon()
    {
        uses.setIcon();
    }


    /**
     * ʹ��ָ����ʹ��������ָ���������Ŀ֮�佨��һ��ʹ�����
     * @param partitem PartItem uses ʹ�����㲿����
     * @param partitem1 PartItem usedBy ��ʹ�����㲿����
     * @param usageLinkIfc1 QMPartUsageLinkIfc ʹ�ù���ֵ����
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
     * �����޸ĵı�ʶ��
     * @param flag boolean �޸ı�ʶ��
     */
    public void setModified(final boolean flag)
    {
        modified = flag;
    }


    /**
     * �����Ƿ��޸ĵı�ʶ��
     * @return boolean �޸ı�ʶ��
     */
    public boolean isModified()
    {
        return modified;
    }


    /**
     * ʹ�ð�������Ϣ����һ���µ�ʹ������,Ϊ�ա�
     * @throws QMRemoteException
     */
    public void create()
            throws QMRemoteException
    {

    }


    /**
     * ����ʹ�����ӡ�
     * @throws QMRemoteException
     */
    public void update()
            throws QMRemoteException
    {

        PartDebug.debug("update() begin ....", this, PartDebug.PART_CLIENT);
        PartDebug.debug("��ǰ��PartUsageLinkIfc��::");
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
                PartDebug.debug("����ǰ��partUsageLinkIfc��::");
                PartDebug.debug("   bsoID:   " + usageLinkIfc.getBsoID() +
                                "   quantity:   " + usageLinkIfc.getQuantity() +
                                "   unit:   "
                                + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("---------------------------------------------");
                final QMQuantity quantity1 = new QMQuantity();

                //��������ʹ��������
                quantity1.setQuantity(quantity);

                //��������ʹ�õ�������λ��
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
                PartDebug.debug("�������º��PartUsageLinkIfc��::::::");
                PartDebug.debug("   bsoID:   " + usageLinkIfc.getBsoID() +
                                "   quantity:   " + usageLinkIfc.getQuantity() +
                                "   unit:   "
                                + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("--------------------------------------------");
                PartDebug.debug("����ֱ�ӵ���PartHelper��saveValueInfo()����::");

                usageLinkIfc = PartHelper.saveUsageLink(usageLinkIfc);
                final Object aobj[] =
                        {uses.getIdentity(), toString(),
                        usedBy.getIdentity()};

                //ResourceHelper ��Դ�����ļ���
                final String usage = QMMessage.getLocalizedMessage(RESOURCE,
                        PartDesignViewRB.USAGE, aobj);

                //���̷�����
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
     * ɾ��ʹ�����ӡ�
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
     * ���ð�����ʹ�����ӡ�
     * @param usageLinkIfc1 PartUsageLinkIfc ʹ�ù�����
     * @see PartUsageLinkInfo
     */
    public void setPartUsageLink(final PartUsageLinkIfc usageLinkIfc1)
    {
        usageLinkIfc = usageLinkIfc1;
    }


    /**
     * ��ȡ������ʹ�����ӡ�
     * @return PartUsageLinkIfc ʹ�ù�����
     * @see PartUsageLinkInfo
     */
    public PartUsageLinkIfc getPartUsageLink()
    {
        return usageLinkIfc;
    }


    /**
     * ����ʹ���㲿����Ŀ��
     * @param partitem PartItem ʹ�����㲿����
     * @see PartItem
     */
    public void setUsesPart(final PartItem partitem)
    {
        uses = partitem;
    }


    /**
     * ��ȡ��ǰ����ʹ�õ��㲿����Ŀ��
     * @return PartItem ʹ�����㲿����
     * @see PartItem
     */
    public PartItem getUsesPart()
    {
        return uses;
    }


    /**
     * ����ʹ�õ�ǰ������㲿����Ŀ��
     * @param partitem PartItem ��ʹ�����㲿����
     * @see PartItem
     */
    public void setUsedBy(final PartItem partitem)
    {
        usedBy = partitem;
    }


    /**
     * ��ȡʹ�õ�ǰ������㲿����Ŀ��
     * @return PartItem ��ʹ�����㲿����
     * @see PartItem
     */
    public PartItem getUsedBy()
    {
        return usedBy;
    }


    /**
     * ��ȡ���������ı�ʶ����
     * @return String  ��ʶ����
     */
    public String getId()
    {
        return linkID;
    }


    /**
     * ������ʹ�������е�������������
     * @param a_quantity float ��������
     */
    public void setQuantity(final float a_quantity)
    {
        quantity = a_quantity;
        setModified(true);
    }


    /**
     * ��ȡ������ʾ���ַ�����
     * @return String ���������ַ�����ʾ��
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
     * ��ȡ��ʹ�������е�������������
     * @return double ��������
     */
    public float getQuantity()
    {
        return quantity;
    }


    /**
     * ���������ĵ�λ��
     * @param a_unit Unit �����ĵ�λ��
     * @see Unit
     */
    public void setUnit(final Unit a_unit)
    {
        quantityUnit = a_unit;
        setModified(true);
    }


    /**
     * ��ȡ��λ���ַ�����������������Ϣ��
     * @return String ��λ��
     */
    public String getUnitName()
    {
        return quantityUnit.getDisplay();
    }


    /**
     * ����һ���ַ���,Ϊʹ������+ʹ�õ�λ��
     * @return String ʹ������+ʹ�õ�λ��ɵ��ַ�����
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
     * ���ر�ʹ���㲿����Ŀʹ�õĶ������顣
     * @return Explorable[] ��ʹ����ʹ�õĶ���(Explorable)���顣
     * @throws QMRemoteException
     * @see Explorable
     */
    public Explorable[] getUses()
            throws QMRemoteException
    {
        //PartItem�еķ���getUses()
//      ����ƿ����
        return uses.getUses();
    }


    /**
     * ����ʹ���㲿����Ŀ����������״̬��
     * @return String ʹ���ߵ���������״̬��
     */
    public String getState()
    {
        return uses.getPart().getLifeCycleState().getDisplay();
    }


    /**
     * ����ʹ���㲿����Ŀ���޶��档
     * @return String ʹ���ߵ��޶��档
     */
    public String getRevision()
    {
        return uses.getRevision();
    }


    /**
     * ��ȡʹ�����㲿���Ĵ�汾�š�
     * @return String ʹ���ߵĴ�汾�š�
     */
    public String getVersion()
    {
        return uses.getVersion();
    }


    /**
     * ��ȡʹ�����㲿���Ĵ�汾�š�
     * @return String ʹ���ߵĴ�汾�š�
     */
    public String getObjectVersion()
    {
        return uses.getVersion();
    }


    /**
     * ��ȡʹ�����㲿���ı�š�
     * @return String ʹ���ߵı�š�
     */
    public String getNumber()
    {
        return uses.getNumber();
    }


    /**
     * ��ȡʹ�����㲿���ı�š�
     * @return String ʹ���ߵı�š�
     */
    public String getObjectNumber()
    {
        return uses.getNumber();
    }


    /**
     * ��ȡʹ�����㲿�������͡�������Ϣ��
     * @return String ʹ���ߵ����͡�
     */
    public String getType()
    {
        //Unit Type ��ʾΪ�ַ���
        return uses.getType().getDisplay();
    }


    /**
     * ��ȡʹ�����㲿�������ơ�
     * @return String ʹ���ߵ����ơ�
     */
    public String getName()
    {
        return uses.getName();
    }


    /**
     * ��ȡʹ�����㲿�������ơ�
     * @return String ʹ���ߵ����ơ�
     */
    public String getObjectName()
    {
        return uses.getName();
    }


    /**
     * ��ȡʹ�����㲿������ͼ����
     * @return ViewObjectIfc ʹ���ߵ���ͼ����
     * @see ViewObjectInfo
     */
    public ViewObjectIfc getView()
    {
        return uses.getView();
    }


    /**
     * ��ȡʹ�����㲿������ͼ���ơ�
     * @return String ʹ���ߵ���ͼ��
     */
    public String getViewName()
    {
        return uses.getViewName();
    }


    /**
     * ��ȡʹ�����㲿���ı�׼ͼ�ꡣ
     * @return Image ʹ���ߵı�׼ͼ�ꡣ
     */
    public Image getStandardIcon()
    {
        return uses.getStandardIcon();
    }


    /**
     * ��ȡʹ�����㲿���Ĵ�ͼ�ꡣ
     * @return Image ʹ���ߵĴ�ͼ�ꡣ
     */
    public Image getOpenIcon()
    {
        return uses.getOpenIcon();
    }


    /**
     * ��ȡʹ�����㲿���ı�ʶ��
     * @return String ʹ���ߵı�ʶ��
     */
    public String getIdentity()
    {
        return uses.getIdentity();
    }


    /**
     * ��ȡʹ�����㲿����Ψһ��ʶ��
     * @return String ʹ���ߵ�Ψһ��ʶ��
     */
    public String getUniqueIdentity()
    {
        return uses.getUniqueIdentity();
    }


    /**
     * ����ʹ���㲿����Ŀ�����ݡ�
     * @return Explorable[] �㲿����Ŀ�����ݡ�
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
     * ���ð����Ķ���
     * @param obj Object ʹ���߰����Ķ���
     */
    public void setObject(final Object obj)
    {
        uses.setObject(obj);
    }


    /**
     * ʵ�� ReferenceHolder �ӿڡ�
     * ��ȡ�����Ķ���
     * @return Object ʹ���߰����Ķ���
     */
    public Object getObject()
    {
        return uses.getObject();
    }


    /**
     * ��ȡʮ�������ݸ�ʽ��
     * @return DecimalFormat ʮ�������ݡ�
     */
    public static DecimalFormat getDecimalFormat()
    {
        if (null == decFormat)
        {
            //QMContext????��ȡ���ػ���Ϣ
            decFormat = (DecimalFormat) NumberFormat.getInstance(QMCt.getContext().
                    getLocale());
            decFormat.applyPattern("########0.######");
        }
        return decFormat;
    }


    //add by liyf
    /**
     * ��ȡʹ�����㲿�������͡�
     * @return String ʹ���ߵ����͡�
     */
    public String getProducedBy()
    {
        // ��Դ��ʾΪ�ַ���
        return uses.getSource().getDisplay();
    }


    /**
     * ��ȡ�㲿�������İ汾ֵ(��汾��+С�汾��)��
     * @return String ʹ���ߵİ汾ֵ��
     */
    public String getIterationID()
    {
        return uses.getIterationID();
    }


    //add by �ܴ���
    /**
     * ���ش������ڡ�
     * @return Timestamp ����ʱ�䡣
     */
    public Timestamp getObjectCreationDate()
    {
        return uses.getCreationDate();
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

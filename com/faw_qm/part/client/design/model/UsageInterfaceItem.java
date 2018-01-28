/** ���ɳ��� UsageInterfaceItem.java    1.0    2003/02/22
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/04/02 л�� ԭ��:���ӹ��캯��������ֱ��ʹ�û������Ϣ����������ˢ��ֵ����
 *                     ����:�Ż���ʼ��ʹ�ýṹ������
 *                     ��ע:���v3r11-չ���㲿�����ڵ������Ż�
 * SS1 ����BOM��������� liuyang 2014-6-20
 *  SS2 ���������汾 xianglx 2014-8-12
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
 * ���������㲿��ʹ�ù�ϵ��ģ���ࡣ
 * @author ���ȳ�
 * @version 1.0
 */
public class UsageInterfaceItem implements Updateable
{
    /**�쳣��Ϣ��ʾ*/
    private final static Logger logger = Logger.getLogger(UsageInterfaceItem.class);


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


    /**�㲿��ֵ����*/
    private PartItem usedBy = null;


    /**�㲿������Ϣֵ����*/
    private QMPartMasterIfc uses = null;


    /**�ͻ�����Դ�ļ�·��*/
    private static final String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";

    private static int newUsageCounter = 0;
    /**
     * ���캯����
     */
    public UsageInterfaceItem()
    {
        super();
    }


    /**
     * ���캯����
     * @param partItem :PartItem ʹ�����㲿����
     * @param partMasterIfc :QMPartMasterIfc ��ʹ�����㲿������Ϣ��
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
     * ���캯����
     * @param usage_link :PartUsageLinkIfc �㲿�����㲿������Ϣ���������
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
     * ���캯��������������
     * @param partUsageLinkIfc �㲿�����㲿������Ϣ���������
     * @param partItem PartItem ʹ�����㲿����
     * @param partMasterIfc QMPartMasterIfc ��ʹ�����㲿������Ϣ��
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
     * �����޸ĵı�ʶ��
     * @param flag boolean �Ƿ��޸ĵı�ʶ��
     */
    public void setModified(boolean flag)
    {
        modified = flag;
    }


    /**
     * �����Ƿ��޸ĵı�ʶ��
     * @return boolean �Ƿ��޸ĵı�ʶ��
     */
    public boolean isModified()
    {
        return modified;
    }


    /**
     * ��������
     * @throws QMRemoteException
     */
    public void create()
            throws QMRemoteException
    {
        PartDebug.debug("create() begin ....", this, PartDebug.PART_CLIENT);
        try
        {
            //���ù��캯����
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
     * ����ʹ�ù�����
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
	/*                 //��õ�ǰ��ɸѡ������
	                ConfigSpecItem configSpecItem = usedBy
	                        .getConfigSpecItem();
	                //���ɸѡ������Ϊ�գ���õ�ǰ�ͻ��˵�ɸѡ����
	                if(configSpecItem == null)
	                {
	                    PartConfigSpecIfc partConfigSpecIfc = null;
	                    // �����������ɸѡ����
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
	                    //���ɸѡ������Ϊ�գ���ɸѡ��������ɸѡ����������½�ɸѡ������
	                    if(partConfigSpecIfc != null)
	                    {
	                        configSpecItem = new ConfigSpecItem(partConfigSpecIfc);
	                    }
	                    else
	                    {
	                        configSpecItem = new ConfigSpecItem();
	                    }
	                }
	                //�����㲿������Ϣ��ȡ���㲿�������з������ù淶�İ汾
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
//	                	JOptionPane.showMessageDialog(null, "xlx��ӵ���ݱ�Ų����ڣ�");  
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
                PartDebug.debug("�����޸ĺ��,��û�б���ġ�PartUsageLinkIfc��:::");
                PartDebug.debug("    " + usageLinkIfc.getLeftBsoID() + "    " +
                                usageLinkIfc.getRightBsoID()
                                + "  quantity: " + usageLinkIfc.getQuantity() +
                                "   Unit:" + usageLinkIfc.getDefaultUnit(), this,
                                PartDebug.PART_CLIENT);
                PartDebug.debug("---------------------------------------------");                
                usageLinkIfc = (PartUsageLinkIfc) PartHelper.saveUsageLink(
                        usageLinkIfc);

                PartDebug.debug("--------------------------------------------");
                PartDebug.debug("������  PartUsageLinkIfc��:::");
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
     * ɾ��������ϵֵ����
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
     * ���ð�����ʹ�����ӡ�
     * @param usage_link PartUsageLinkIfc ʹ�ù���ֵ����
     * @see PartUsageLinkInfo
     */
    public void setPartUsageLink(final PartUsageLinkIfc usage_link)
    {
        usageLinkIfc = usage_link;
    }


    /**
     * ��ȡ������ʹ�����ӡ�
     * @return PartUsageLinkIfc ʹ�ù���ֵ����
     * @see PartUsageLinkInfo
     */
    public PartUsageLinkIfc getPartUsageLink()
    {
        return usageLinkIfc;
    }


    /**
     * ����ʹ���㲿����Ŀ��
     * @param partItem PartItem �㲿��ֵ����
     * @see PartItem
     */
    public void setUses(final PartItem partItem)
    {
        usedBy = partItem;
    }


    /**
     * ���ر�ʹ���㲿����Ŀʹ�õĶ������顣
     * @return PartItem �㲿��ֵ����
     * @see PartItem
     */
    public PartItem getUses()
    {
        return usedBy;
    }


    /**
     * ����ʹ�õ�ǰ������㲿����Ŀ��
     * @param partMasterIfc QMPartMasterIfc �㲿������Ϣ��
     * @see QMPartMasterInfo
     */
    public void setUsedBy(final QMPartMasterIfc partMasterIfc)
    {
        uses = partMasterIfc;
    }


    /**
     * ��ȡʹ�õ�ǰ������㲿����Ŀ��
     * @return QMPartMasterIfc �㲿������Ϣ��
     * @see QMPartMasterInfo
     */
    public QMPartMasterIfc getUsedBy()
    {
        return uses;
    }


    /**
     * ��ȡ��ʶ��
     * @return String ��ʶ��
     */
    public String getId()
    {
        return linkID;
    }


    /**
     * ������ʹ�������е�������������
     * @param a_quantity float ��������
     */
    public void setQuantity(float a_quantity)
    {
        quantity = a_quantity;
        setModified(true);
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
     * ��ȡ������ʾ���ַ�����
     * @return String ��ʾ��������
     */
    public String getQuantityString()
    {
        //Ĭ�Ͼ���ȡС�������λ,����λ��������
        final String QuanStr = String.valueOf(getQuantity());
        return QuanStr;
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
     * ��ȡ�����ĵ�λ��
     * @return QuantityUnit �����ĵ�λ��
     * @see Unit
     */
    public Unit getUnits()
    {
        return quantityUnit;
    }


    /**
     * ����һ���ַ���,Ϊʹ������+ʹ�õ�λ��
     * @return String ʹ������+ʹ�õ�λ��ɵ��ַ�����
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
     * ��ȡʹ�����㲿���ı�ʶ��
     * @return String �㲿���ı�ʶ��
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

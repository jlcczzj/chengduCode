/** ���ɳ��� PART_REMOVE_Listener.java    1.0    2003/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 A004-2015-3272 ����㲿���Ѿ����ɹ���������ô������ɾ���� liunan 2016-3-25
 */

package com.faw_qm.part.ejb.standardService;

import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
//CCBegin SS1
import com.faw_qm.pcfg.doc.model.*;
import com.faw_qm.part.model.QMPartIfc;
//CCEnd SS1


/**
 * �㲿��ɾ���źż����ࡣ
 * @author ���ȳ�
 * @version 1.0
 */

public class PART_REMOVE_Listener implements SignalListenerIfc
{
    private String RESOURCE = "com.faw_qm.part.util.PartResource";


    /**
     * �����źš�
     * @param signal Signal
     * @param service BaseServiceImp
     * @throws ServiceHandleSignalException
     */
    public void handleSignal(Signal signal, BaseServiceImp service)
            throws ServiceHandleSignalException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "handleSignal() begin ....");
        //���ر����Ƶ�Ŀ�����obj.
        Object obj = signal.getSignalTarget();
        if (obj instanceof BaselineIfc)
        {
            try
            {
                checkConfigSpec((BaselineIfc) obj);
            }
            catch (QMException ex)
            {
                throw new ServiceHandleSignalException("������:" +
                        service.getServiceName() + ex);
            }
        }
        //CCBegin SS1
        if(obj instanceof QMPartIfc)
        {
            try
            {
        	PersistService ps = (PersistService) EJBServiceHelper.
                                  getPersistService();
            String gpID = ((QMPartIfc)obj).getBsoID();
                QMQuery query = new QMQuery("VariantSpecVariantLink");
                query.addCondition(new QueryCondition("genericPartID", "=", gpID));
                Collection queryresult = ps.findValueInfo(query);
                String specNum = "";
                if (queryresult != null && queryresult.size() != 0)
                {
                	Iterator iter = queryresult.iterator();
                	while (iter.hasNext())
                	{
                		VariantSpecVariantLinkIfc link = (VariantSpecVariantLinkIfc)iter.next();
                		VariantSpecIfc variantspec = (VariantSpecIfc)ps.refreshInfo(link.getRightBsoID());
                		specNum = variantspec.getDocNum()+","+specNum;
                	}
                }
                if(!specNum.equals(""))
                {
                	throw new PartException("��ǰ�㲿����"+((QMPartIfc)obj).getPartNumber()+"���Ѿ������˶�����"+specNum+"������ɾ����������ɾ�����㲿����");
                }
            }
            catch (QMException ex)
            {
                throw new ServiceHandleSignalException("������:" +
                        service.getServiceName() + ex);
            }
        }
        //CCEnd SS1
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "handleSignal() end....return is void");
    }


    /**
     * ���鵱ǰ��ɸѡ���������Ҫɾ���Ļ�׼�߱���Ϊ��ǰ��ɸѡ���������ܱ�ɾ����
     * @param obj :BaselineҪ��ɾ���Ļ�׼�ߡ�
     * @throws QMException
     */
    protected void checkConfigSpec(BaselineIfc obj)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "checkConfigSpec() begin ....");
        QMQuery query = new QMQuery("PartConfigSpec");
        QueryCondition condition = new QueryCondition("baselineID", "=",
                obj.getBsoID());
        query.addCondition(condition);
        PersistService pservice = (PersistService) EJBServiceHelper.
                                  getPersistService();
        Collection collection = pservice.findValueInfo(query);
        if (collection.size() == 1)
        {
            //��������������׳�PartException�쳣
            //"�û�׼�߲��ܱ�ɾ����������Ϊ��ǰ��ɸѡ������"
            throw new PartException(RESOURCE, "3", null);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "checkConfigSpec() end....return is void");
    }
}

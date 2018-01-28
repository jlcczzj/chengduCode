/**
 * ���ɳ���MaterialStructureHomeDelegate.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class MaterialStructureHomeDelegate implements BsoHomeDelegate
{
    /**
     * 
     */
    public MaterialStructureHomeDelegate()
    {
        super();
    }

    private MaterialStructureHome home = null;

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BsoHomeDelegate#create(com.faw_qm.framework.service.BaseValueIfc)
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BsoHomeDelegate#create(com.faw_qm.framework.service.BaseValueIfc, java.sql.Timestamp, java.sql.Timestamp)
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws CreateException
    {
        return home.create(info, ct, mt);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BsoHomeDelegate#findByPrimaryKey(java.lang.String)
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.HomeDelegate#init(java.lang.Object)
     */
    public void init(Object obj)
    {
        if(!(obj instanceof MaterialStructureHome))
        {
            Object[] objs = {obj.getClass(), "JFMaterialStructureHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (MaterialStructureHome) obj;
    }
}

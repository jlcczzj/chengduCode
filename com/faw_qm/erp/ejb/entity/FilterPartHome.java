package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * <p>Title: ����������ӿ�</p>
 * <p>Description: ����������ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface FilterPartHome extends BsoReferenceHome
{
    public FilterPart create(BaseValueIfc basevalueifc) throws CreateException;

    public FilterPart create(BaseValueIfc basevalueifc, Timestamp timestamp,
            Timestamp timestamp1) throws CreateException;

    public FilterPart findByPrimaryKey(String s) throws FinderException;
}

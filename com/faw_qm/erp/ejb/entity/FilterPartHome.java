package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * <p>Title: 发布的零件接口</p>
 * <p>Description: 发布的零件接口</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public interface FilterPartHome extends BsoReferenceHome
{
    public FilterPart create(BaseValueIfc basevalueifc) throws CreateException;

    public FilterPart create(BaseValueIfc basevalueifc, Timestamp timestamp,
            Timestamp timestamp1) throws CreateException;

    public FilterPart findByPrimaryKey(String s) throws FinderException;
}

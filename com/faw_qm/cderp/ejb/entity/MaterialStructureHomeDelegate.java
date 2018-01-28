/**
 * 生成程序MaterialStructureHomeDelegate.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
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

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BsoHomeDelegate#create(com.faw_qm.framework.service.BaseValueIfc)
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BsoHomeDelegate#create(com.faw_qm.framework.service.BaseValueIfc, java.sql.Timestamp, java.sql.Timestamp)
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws CreateException
    {
        return home.create(info, ct, mt);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BsoHomeDelegate#findByPrimaryKey(java.lang.String)
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /* （非 Javadoc）
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

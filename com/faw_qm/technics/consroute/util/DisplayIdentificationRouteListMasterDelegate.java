/**
 * 生成程序 DisplayIdentificationRouteListMasterDelegate.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.identity.DisplayIdentificationStandardDelegate;
import com.faw_qm.util.JNDIUtil;

/**
 * <p>Title:DisplayIdentificationRouteListMasterDelegate.java</p> <p>Description:工艺路线Master标识代理类 </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c)
 * 2005</p> <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class DisplayIdentificationRouteListMasterDelegate extends DisplayIdentificationStandardDelegate
{
    protected static final String JNDIUtil_CLASSNAME = "com.faw_qm.util.JNDIUtil";
    protected static final String FEATURE_VALUE = "getFeatureValue";
    protected static final String DISPLAYNAME = "DisplayName";
    static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public DisplayIdentificationRouteListMasterDelegate()
    {

    }

    /**
     * @see com.faw_qm.identity.DisplayIdentificationStandardDelegate#initializeIdentifier(java.lang.Object)
     */
    protected void initializeIdentifier(Object obj)
    {
        if(DisplayIdentificationStandardDelegate.VERBOSE)
        {
            System.out.println("DIQMDocumentMasterDelegate: initializeIdentifier");
        }
        try {
			setIdentifier(new RouteListNumberedNamedIdentifier(obj));
		} catch (QMException e) {
			e.printStackTrace();
			return;
		}
    }

    /**
     * @see com.faw_qm.identity.DisplayIdentificationStandardDelegate#initializeType(java.lang.Object)
     */
    protected void initializeType(Object obj)
    {
        if(DisplayIdentificationStandardDelegate.VERBOSE)
        {
            System.out.println("DIQMDocumentMasterDelegate: initializeType");
            //setType(((DocMasterInfo)obj).getDocType());
        }
        Object obj1 = null;
        String bsoname = ((BaseValueIfc)obj).getBsoName();
        obj1 = getFeatureDisplayName(bsoname);
        if(obj1 == null)
        {
            obj1 = bsoname;
        }
        setType(obj1);
    }

    /**
     * 取得特征
     * @param bsoname
     * @return
     */
    protected Object getFeatureDisplayName(String bsoname)
    {
        Object obj1 = null;
        try
        {
            obj1 = JNDIUtil.getFeatureValue(bsoname, DISPLAYNAME);
        }catch(Exception e)
        {
            obj1 = null;
        }
        return obj1;
    }
}

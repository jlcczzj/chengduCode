/**
 * 生成程序 DisplayIdentificationRouteListDelegate.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.identity.DisplayIdentificationStandardDelegate;
import com.faw_qm.identity.DisplayIdentificationStandardVersionedDelegate;
import com.faw_qm.identity.LocaleIndependentMessage;
import com.faw_qm.identity.StandardRevisionDisplayIdentity;
import com.faw_qm.version.model.VersionedIfc;

/**
 * <p>Title:DisplayIdentificationRouteListDelegate.java</p> <p>Description: 工艺路线标识代理类</p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public class DisplayIdentificationRouteListDelegate extends DisplayIdentificationStandardVersionedDelegate
{
    /**
     * @see com.faw_qm.identity.DisplayIdentificationVersionedDelegate#initializeVersionIdentity(java.lang.Object)
     */
    protected void initializeVersionIdentity(Object obj)
    {
        LocaleIndependentMessage localeindependentmessage = null;
        if(DisplayIdentificationStandardDelegate.VERBOSE)
        {
            System.out.println("obj" + obj);
        }
        if(DisplayIdentificationStandardDelegate.VERBOSE)
        {
            System.out.println("getvi" + ((VersionedIfc)obj).getVersionID());
        }
        String versionidentifier = ((VersionedIfc)obj).getVersionID();
        if(versionidentifier != null)
        {
            localeindependentmessage = new LocaleIndependentMessage(versionidentifier);
        }
        if(DisplayIdentificationStandardDelegate.VERBOSE)
        {
            System.out.println("-4-");
        }
        if(DisplayIdentificationStandardDelegate.VERBOSE)
        {
            System.out.println("-5-");
        }
        setVersionIdentity(new StandardRevisionDisplayIdentity(localeindependentmessage));
    }

    /**
     * 构造函数
     */
    public DisplayIdentificationRouteListDelegate()
    {}

    static final long serialVersionUID = 1L;

}

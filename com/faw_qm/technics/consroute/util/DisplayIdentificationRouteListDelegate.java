/**
 * ���ɳ��� DisplayIdentificationRouteListDelegate.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.identity.DisplayIdentificationStandardDelegate;
import com.faw_qm.identity.DisplayIdentificationStandardVersionedDelegate;
import com.faw_qm.identity.LocaleIndependentMessage;
import com.faw_qm.identity.StandardRevisionDisplayIdentity;
import com.faw_qm.version.model.VersionedIfc;

/**
 * <p>Title:DisplayIdentificationRouteListDelegate.java</p> <p>Description: ����·�߱�ʶ������</p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
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
     * ���캯��
     */
    public DisplayIdentificationRouteListDelegate()
    {}

    static final long serialVersionUID = 1L;

}

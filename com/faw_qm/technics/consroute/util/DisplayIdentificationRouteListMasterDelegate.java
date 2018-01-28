/**
 * ���ɳ��� DisplayIdentificationRouteListMasterDelegate.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.identity.DisplayIdentificationStandardDelegate;
import com.faw_qm.util.JNDIUtil;

/**
 * <p>Title:DisplayIdentificationRouteListMasterDelegate.java</p> <p>Description:����·��Master��ʶ������ </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c)
 * 2005</p> <p>Company:һ������</p>
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
     * ���캯��
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
     * ȡ������
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

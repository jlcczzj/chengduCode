/** ���ɳ���RouteCompletTypeRB.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * <p>Copyright: Copyright (c) 2004.2</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */


package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * ���Ϸ�ʽ��Դ�ļ�
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class RouteCompletTypeRB
    extends ListResourceBundle
{


    public RouteCompletTypeRB()
    {

    }

    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents =
        {
        {
        "PART", "PART,���,���,10,true,true"
    }
        ,
        {
        "ROUTE", "ROUTE,��׼,��׼,20,false,true"
    }
    };
}

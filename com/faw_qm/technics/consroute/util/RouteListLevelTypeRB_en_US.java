/**
 * ���ɳ��� RouteListLevelTypeRB_en_US.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteListLevelTypeRB_en_US.java</p> <p>Description:·�߼�����Դ�ļ�(Ӣ��) </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListLevelTypeRB_en_US extends ListResourceBundle
{

    /**
     * ���캯��
     */
    public RouteListLevelTypeRB_en_US()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"firstRoute", "FIRSTROUTE,firstRoute,firstRoute,10,true,true"}, {"secondRoute", "SECONDROUTE,firstRoute,firstRoute,20,false,true"}};

}

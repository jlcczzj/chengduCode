/**
 * ���ɳ��� RouteListLevelTypeRB_zh_CN.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteListLevelTypeRB_zh_CN.java</p> <p>Description:·�߼�����Դ�ļ�(����) </p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListLevelTypeRB_zh_CN extends ListResourceBundle
{

    /**
     * ���캯��
     */
    public RouteListLevelTypeRB_zh_CN()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"firstRoute", "FIRSTROUTE,һ��·��,һ��·��,10,true,true"}, {"secondRoute", "SECONDROUTE,����·��,����·��,20,false,true"}};

}

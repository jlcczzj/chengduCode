/**
 * ���ɳ��� RouteCategoryTypeRB.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteCategoryTypeRB.java</p> <p>Description: ·��������Դ</p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteCategoryTypeRB extends ListResourceBundle
{

    /**
     * ���캯��
     */
    public RouteCategoryTypeRB()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"manufactureRoute", "MANUFACTUREROUTE,����·��,����·��,10,true,true"}, {"assemblyRoute", "ASSEMBLYROUTE,װ��·��,װ��·��,20,false,true"}};
}

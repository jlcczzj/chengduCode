/**
 * ���ɳ��� RouteCategoryTypeRB_zh_CN.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title:RouteCategoryTypeRB_zh_CN.java</p> <p>Description: ·��������Դ(����)</p> <p>Package:com.faw_qm.technics.consroute.util</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteCategoryTypeRB_zh_CN extends ListResourceBundle
{

    /**
     * ���캯��
     */
    public RouteCategoryTypeRB_zh_CN()
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

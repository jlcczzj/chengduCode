/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * <p>Title: ����·�߷������</p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */

public class RouteAdoptedTypeRB_zh_CN extends ListResourceBundle
{
    /**
     * ���캯��
     */
    public RouteAdoptedTypeRB_zh_CN()
    {}

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"adopt", "ADOPT,����,����,10,true,true"}, {"cancel", "CANCEL,ȡ��,ȡ��,20,false,true"}, {"nothing", "NOTHING,��,��,30,false,true"},
            {"exist", "EXIST,�Ѵ���,�Ѵ���,40,false,true"}

    };

}

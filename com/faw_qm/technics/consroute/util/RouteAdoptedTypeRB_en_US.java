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
 * <p>Title:����·�߷������ </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */

public class RouteAdoptedTypeRB_en_US extends ListResourceBundle
{
    /**
     * ���캯��
     */
    public RouteAdoptedTypeRB_en_US()
    {}

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object[][] contents = {{"adopt", "ADOPT,adopt,adopt,10,true,true"}, {"cancel", "CANCEL,cancel,cancel,20,false,true"}, {"nothing", "NOTHING,nothing,nothing,30,false,true"},
            {"exist", "EXIST,Exist,Exist,40,false,true"}

    };

}

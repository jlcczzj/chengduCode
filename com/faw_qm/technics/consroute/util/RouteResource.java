/**
 * ���ɳ��� RouteResource.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.util.ListResourceBundle;

/**
 * �ο��ĵ��� 1.��Ϣ��V1.0.xls��(1--12) 2.Phos-REQ-CAPP-BR02(����·��ҵ�����)V2.0.doc
 * <p>
 * Title:RouteResource.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Package:com.faw_qm.technics.consroute.util
 * </p>
 * <p>
 * ProjectName:CAPP
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:һ������
 * </p>
 *
 * @author unascribed
 * @version 1.0
 * <code>ListResourceBundle</code> is an abstract subclass of
 * <code>ResourceBundle</code> that manages resources for a locale
 * in a convenient and easy to use list. See <code>ResourceBundle</code> for
 * more information about resource bundles in general.
 *
 * <P>
 * Subclasses must override <code>getContents</code> and provide an array,
 * where each item in the array is a pair of objects.
 * The first element of each pair is the key, which must be a
 * <code>String</code>, and the second element is the value associated with
 * that key.
 */

/**
 *CR1 2011/4/21 ������ �μ���������:product;TD��2386
 */
public class RouteResource extends ListResourceBundle
{

    /**
     * ���캯��
     */
    public RouteResource()
    {

    }

    /**
     * @see java.util.ListResourceBundle#getContents()
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {{"1", "����·�߱����Ʋ���Ϊ�ա�"}, {"2", "����·�߱��Ų���Ϊ�ա�"}, {"3", "����·�߱�\"*\"�ı��\"*\"�Ѿ����ڣ����������ñ�š�"}, {"4", "���������·�ߡ����븴������·�ߣ�����ɾ������·�ߡ�"}, {"5", "��Ʒ��:\"*\"����ȷ�������������Ʒ�š�"},
            {"6", "*(*)�Ĺ���·�߱���"}, {"7", "���ڲ�Ʒ��* *"}, {"8", "�������ڣ�*��*��*��"}, {"9", "*: *(*)�Ķ�������·�߱���"}, {"10", "�������ݴ�����Ϊ��δ����޶�����·�߱�*����ɡ�"}, {"11", "�������ݴ�����Ϊ��δ�����������·�߱�С�汾����ɡ�"},
            {"12", "�������·�ߣ����ܱ�ɾ����"}//CR1
    };
}
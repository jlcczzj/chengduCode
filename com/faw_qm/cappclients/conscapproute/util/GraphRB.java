/** 
 * ���ɳ��� GraphRB.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * <p> Title:·��ͼ�༭����ר����Դ </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class GraphRB extends ListResourceBundle
{
    //��������
    public static final String ALREADY_LOCALIZE = "0";

    public static final String CAN_NOT_USE_CURRENT_MODEL = "1";

    public static final String CAN_NOT_USE_SELECTIONMODEL = "2";

    public static final String LINK_IS_NOT_EXIST = "3";

    public static final String LINK_IS_ALREADY_EXIST = "4";

    public static final String NODE_IS_NOT_EXIST = "5";

    public static final String NODE_IS_ALREADY_EXIST = "6";

    public static final String NODE_INVALIDATE = "7";

    public static final String LINK_INVALIDATE = "8";

    public static final String ASSEM_DEPARTMENT_MUST_BE_LASTED = "9";

    public static final String A_BRANCH_CAN_HAVE_ONLYONE_ASSEM = "10";

    public static final String CONFIRM_STOP_EDIT_GRAPH = "11";

    public static final String PROPERTY_VALUE_IS_EMPTY = "12";

    public static final String DEPARTMENT_IS_RECYCLE = "13";

    public static final String PROPERTY_VALUE_IS_LARGER = "14";

    public static final String NO_SELECT_DEPARTMENT = "15";

    public static final String CLOSED_LOOP = "16";

    /**
     * ������Դ��
     * @roseuid 4031A6750373
     */
    public GraphRB()
    {}

    /**
     * �����Դ����
     * @return Object[][] ����2ά����
     * @roseuid 4031A67503CD
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {
            //Titles
            {"exception", "�쳣��Ϣ"}, {"information", "��ʾ"}, {"error", "����"}, {"warning", "����"}, {"notice", "֪ͨ"}, {"editNodeTitle", "�༭·�߽ڵ�"}, {"viewNodeTitle", "�鿴·�߽ڵ�"},
            {"editGraphTitle", "�༭·��ͼ"},
            {"viewGraphTitle", "�鿴·��ͼ"},
            //Button
            {"okJButton", "ȷ��"}, {"cancelJButton", "ȡ��"}, {"helpJButton", "����"},
            {"quitJButton", "�˳�"},
            //Label
            {"department", "·�ߵ�λ"}, {"departmentType", "·�����"}, {"describe", "˵��"},
            {"routeGraph", "·��ͼ"},
            //other
            {"node", "��λ�ڵ�"}, {"link", "����"}, {"editNode", "�༭�ڵ�"}, {"deleteNodeOrLink", "ɾ���ڵ������"},

            {"0", "..�ѱ��ػ�.."}, {"1", "����ʹ�����ṩ��ģ�ͣ����ڴ���һ��Ĭ��ģ�͡�"}, {"2", "����ʹ�����ṩ��ѡ��ģ�ͣ����ڴ���һ��Ĭ��ѡ��ģ�͡�"}, {"3", "ָ�������Ӳ����ڣ�"}, {"4", "ָ���������Ѵ��ڣ�"}, {"5", "ָ���Ľڵ㲻���ڣ�"}, {"6", "ָ���Ľڵ��Ѵ��ڣ�"}, {"7", "ָ���Ľڵ���Ч��"},
            {"8", "ָ����������Ч��"}, {"9", "װ�䵥λ������·�ߴ��е����һ����λ��"}, {"10", "һ��·�ߴ���ֻ�ܴ���һ��װ���͵�λ��"}, {"11", "��ȷʵҪ������ǰ·��ͼ���ѷ��������и�����"}, {"12", "�����е����ԣ�����ֵ��������Ϊ�ա�"}, {"13", "ͬһ�ӹ���λ������ͬһλ���ظ����֣�"},
            {"14", "����ֵ�Ѿ��������������ޡ�"}, {"15", "��ѡ����Ч��·�ߵ�λ��"}, {"16", "����·�ߴ�ʧ�ܣ���Ϊ���ڱջ���"},

    };

}
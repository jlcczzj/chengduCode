/** 
 * ���ɳ��� GraphRB_zh_CN.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * <p> Title:·��ͼ�༭����ר����Դ���������ģ� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class GraphRB_zh_CN extends ListResourceBundle
{

    /**
     * ������Դ��
     * @roseuid 4031A67600AE
     */
    public GraphRB_zh_CN()
    {

    }

    /**
     * �����Դ����
     * @return Object[][] ����2ά����
     * @roseuid 4031A67600E0
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
            {"14", "����ֵ�Ѿ��������������ޡ�"}, {"15", "��ѡ����Ч��·�ߵ�λ��"}, {"16", "����·�ߴ�ʧ�ܣ���Ϊ���ڱջ���"},};

}
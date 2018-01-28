/** ���ɳ���CappSortingSelectedPanel.java      1.1  2005/3/7
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��ݹ����Զ���� 2014-1-13 ����
 */
package com.faw_qm.cappclients.capp.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;


/**
 * <p>Title: �̳���SortingSelectedPanel�ķ����齨</p>
 * <p>Description: ����ϸ��������˻�ȡĬ��ֵ,���Ĭ��ֵ�Ĺ���(setDefaultCoding()����)
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 */

public class CappSortingSelectedPanel extends SortingSelectedPanel
{

    /**
     * ���Ĭ��ֵ.��:�������� ֵ:Ĭ��ֵ
     */
    HashMap table = new HashMap();
    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";

    /**
     * ������������
     * @param rootName ���������
     * @param bsoName ҵ������Bso��
     * @param propertyName �����ֶ���
     */
    public CappSortingSelectedPanel(String rootName, String bsoName,
                                    String propertyName)
    {
        super(rootName, bsoName, propertyName);
    }
//CCBegin SS1
    /**
     * ������������
     * @param rootName ���������
     * @param bsoName ҵ������Bso��
     * @param propertyName �����ֶ���
     * @param parentPanel �ش���panel
     */
    public CappSortingSelectedPanel(String rootName, String bsoName,
                                    String propertyName,TechnicsMasterJPanel parentPanel)
    {
        super(rootName, bsoName, propertyName,parentPanel);
    }  
//CCEnd SS1

    /**
     * ������������
     * @param codingClassificationIfc CodingClassificationIfc ���ڵ�
     */
    public CappSortingSelectedPanel(CodingClassificationIfc
                                    codingClassificationIfc)
    {
        super(codingClassificationIfc);
    }


    /**
     * ����Ĭ��ֵ.���ݴ���Ĳ����������ļ��л�ȡ��Ϣ,������Ϣ�õ�Ĭ��ֵ,�赽���齨��,
     * Ĭ��ֵ�������ļ�������,�繤������Ĭ��ֵ:�������_��ѹ���Ϲ���=�ؼ�����
     * @param name String ������ڵ���,��:�������
     * @param Type String ������������������ʾ��
     */
    public void setDefaultCoding(String name, String type)
    {
        //���ݹ��������ȡ�����Ĭ��ֵ
        BaseValueIfc coding = (BaseValueIfc) table.get(type);
        if (coding != null)
        {
            setCoding(coding);
            //����Ĭ��ֵΪnull,�ж�table���Ƿ������type,�������,��˵��û��Ĭ��ֵ
            //�����ȡĬ��ֵ,��Ĭ��ֵ����,���������Ĭ��ֵΪ��,�򻺴�null
        }
        else if (!table.containsKey(type))
        {
            String key = name + "_" + type;
            String s = RemoteProperty.getProperty(key);
            if (s != null)
            {
                StringTokenizer toker = new StringTokenizer(s.trim(), ",");
                //��ô�����
                if (toker.countTokens() == 2)
                {
                    Class[] paraclass =
                            {String.class, String.class, String.class};
                    Object[] paraobj =
                            {toker.nextElement(), toker.nextElement(), null};

                    try
                    {
                        coding = (BaseValueIfc) CappClientHelper.
                                 useServiceMethod(
                                "CodingManageService"
                                , "findCodingByContent", paraclass, paraobj);
                    }
                    catch (QMRemoteException ex)
                    {
                        ex.printStackTrace();
                    }

                }
                //��ô������
                if (toker.countTokens() == 1)
                {
                    Class[] paraclass =
                            {String.class};
                    Object[] paraobj =
                            {toker.nextElement()};

                    try
                    {
                        Collection c = (Collection) CappClientHelper.
                                       useServiceMethod(
                                "CodingManageService"
                                , "findClassificationByName", paraclass,
                                paraobj);
                        if (c != null && c.size() != 0)
                        {
                            coding = (BaseValueIfc) c.iterator().next();
                        }
                    }
                    catch (QMRemoteException ex)
                    {
                        ex.printStackTrace();
                    }

                }

                table.put(type, coding);
                if (coding != null)
                {
                    setCoding(coding);
                }
            }
        }

    }

    public static void main(String[] args)
    {

        CappSortingSelectedPanel test2 = new CappSortingSelectedPanel("�������",
                "QMProcedure", "stepClassification");
        test2.setDefaultCoding("�������", "��ѹ���Ϲ���");
    }

}

/**
 * SS1 �����乤�����ڵ㣬��ʾ�������ƣ�����ʾ���ݡ� liunan 2014-5-20
 */

package com.faw_qm.cappclients.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.resource.technics.model.ModelProcedureIfc;


/**
 * <p>Title:�õ����ڵ����ʾ���� </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: һ������</p>
 *
 * @author not attributable
 * @version 1.0
 * ���⣨1��20081125 �촺Ӣ�޸�   �޸�ԭ�򣺹������ڵ���ʾ��ȫ
 */
public class GetCappTreeObjectNoteText
{
    static Hashtable table = new Hashtable();


    private static void translateNoteText(BaseValueInfo object)
    {
        Vector noteTextVector = new Vector();
        String key = null;
        if (object instanceof QMProcedureIfc)
        {
            if (((QMProcedureIfc) object).getIsProcedure())
            {
                key = "QMProcedureStep";
            }
            else
            {
                key = "QMProcedurePace";
                //CCBegin SS1
                if(((QMProcedureIfc) object).getBsoID().startsWith("cons"))
                {
                	key = key + "_bsx";
                }
                //CCEnd SS1
            }
        }
        else if (object instanceof ModelProcedureIfc)
        {
            if (((ModelProcedureIfc) object).getUpper() == null)
            {
                if (((ModelProcedureIfc) object).getStepName() != null)
                {
                    key = "ModelProcedureStep1";
                }
                else
                {
                    key = "ModelProcedureStep2";
                }
            }
            else
            {
                key = "ModelProcedurePace";

            }
        }
        else
        {
            key = object.getBsoName();

        }
        System.out.println("key===="+key);
        
        String noteTextInformation = RemoteProperty.getProperty(
                key + "_treeobj").trim();
        if (noteTextInformation == null)
        {
            JOptionPane.showMessageDialog(null,
                                          "���������ļ����Ƿ��������ڵ���Ϣ��" + key +
                                          "_treeobj",
                                          "��ʾ��Ϣ",
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        StringTokenizer tokens = new StringTokenizer(noteTextInformation, ";");
        while (tokens.hasMoreTokens())
        {
            String token = tokens.nextToken();
            StringTokenizer tokens1 = new StringTokenizer(token, ",");
            Object[] obj = new Object[tokens1.countTokens()];
            int i = 0;
            while (tokens1.hasMoreTokens())
            {
                obj[i++] = tokens1.nextElement();
            }
            noteTextVector.add(obj);
        }
        table.put(key, noteTextVector);

    }


    /**
     * �õ��ڵ���Ϣ
     * @param info BaseValueInfo ���ڵ�ֵ����
     * @return Object[] ����Ϊ3���ڵ���ʾ��Ϣ���ڵ縡����Ϣ���ڵ�������Ϣ���򳤶�Ϊ2���ڵ���ʾ��Ϣ���ڵ�������Ϣ��
     * �����нڵ�������ϢΪ����Ϊ3�����飬���δ�����������������͡�����ֵ
     * ���⣨1��20081125 �촺Ӣ�޸�   �޸�ԭ�򣺹������ڵ���ʾ��ȫ
     */
    public static Object[] getNoteText(BaseValueInfo info)
    {

        String key = null;
        if (info instanceof QMProcedureIfc)
        {
            if (((QMProcedureIfc) info).getIsProcedure())
            {
                key = "QMProcedureStep";
            }
            else
            {
                key = "QMProcedurePace";
                //CCBegin SS1
                if(((QMProcedureIfc) info).getBsoID().startsWith("cons"))
                {
                	key = key + "_bsx";
                }
                //CCEnd SS1
            }
        }
        else if (info instanceof ModelProcedureIfc)
        {
            if (((ModelProcedureIfc) info).getUpper() == null)
            {
                if (((ModelProcedureIfc) info).getStepName() != null)
                {
                    key = "ModelProcedureStep1";
                }
                else
                {
                    key = "ModelProcedureStep2";
                }
            }
            else
            {
                key = "ModelProcedurePace";
            }
        }
        else
        {
            key = info.getBsoName();
        }
        if (table.get(key) == null)
        {
            translateNoteText(info);
        }
        Vector noteTextVector = (Vector) table.get(key);
        //������Ϣ�����������������͡�����ֵ
        Object[] orderNote = new Object[2];
        Object[] returnobj = null;
        String noteText = new String("");
        String tipText = null;
        try
        {
            for (int j = 0; j < noteTextVector.size(); j++)
            {
                //����ֵ
                Object value = null;
                //����ֵ��ʾ
                String dispValue = null;
                //��������
                String tipValue = null;
                Object[] obj = (Object[]) noteTextVector.elementAt(j);
                PropertyDescriptor propertydescriptor = null;
                try
                {
                    propertydescriptor = new PropertyDescriptor((String) obj[0],
                            info.getClass());
                    Method method = propertydescriptor.getReadMethod();
                    value = method.invoke(info, null);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                int lenth = Integer.parseInt((String) obj[2]);
                boolean isTipText = ((String) obj[3]).equals("1");
                String type = propertydescriptor.getPropertyType().getName();

                if (type.equals("java.lang.String"))
                {
                    if (obj[1].equals("1"))
                    {
                        dispValue = DataDisplay.translate((String) value);
                    }
                    else
                    {
                        dispValue = (String) value;
                    }
                    if (isTipText)
                    {
                        tipValue = dispValue;
                    }
                }
                else
                if (type.equals("int"))
                {
                    dispValue = String.valueOf(value);
                    if (isTipText)
                    {
                        tipValue = dispValue;
                    }
                }
                //�����������
                else
                if (type.equals("java.util.Vector"))
                {
                    dispValue = DataDisplay.translate((Vector) value);
                    if (isTipText)
                    {
                        StringBuffer sb = new StringBuffer();
                        StringTokenizer ston = new StringTokenizer(dispValue,
                                "\n");
                        while (ston.hasMoreTokens())
                        {
                            sb.append(ston.nextToken());
                        }
                        tipValue = sb.toString();

                    }

                }
                //����
                else
                if (type.equals("com.faw_qm.codemanage.model.CodingIfc"))
                {
                    dispValue = value.toString();
                    if (isTipText)
                    {
                        tipValue = dispValue;
                    }
                }
                else
                if (type.equals("com.faw_qm.framework.service.BaseValueInfo"))
                {
                    dispValue = value.toString();
                    if (isTipText)
                    {
                        tipValue = dispValue;
                    }
                }
                //���⣨1��20081125 �촺Ӣ�޸�   �޸�ԭ�򣺹������ڵ���ʾ��ȫ
                /*if (lenth > 0 && ((String) dispValue).length() > lenth)
                {
                    dispValue = ((String) dispValue).substring(0, 10) + "...";
                }*/
                if (noteText.equals(""))
                {
                    noteText = dispValue;
                }
                else
                {
                    noteText = noteText + "_" + dispValue;
                }
                if (tipValue != null)
                {
                    if (tipText == null)
                    {
                        tipText = tipValue;
                    }
                    else
                    {
                        tipText = tipText + "_" + tipValue;
                    }
                }
                if (j == 0)
                {
                    orderNote[0] = type;
                    orderNote[1] = value;
                }
            }
        }
        catch (Exception ex1)
        {
            ex1.printStackTrace();
        }
        if (tipText != null)
        {
            returnobj = new Object[3];
            returnobj[0] = noteText;
            returnobj[1] = tipText;
            returnobj[2] = orderNote;

        }
        else
        {
            returnobj = new Object[2];
            returnobj[0] = noteText;
            returnobj[1] = orderNote;

        }
        return returnobj;
    }


}

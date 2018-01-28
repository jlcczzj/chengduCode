/**
 * SS1 变速箱工步树节点，显示工步名称，不显示内容。 liunan 2014-5-20
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
 * <p>Title:得到树节电的显示内容 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: 一汽启明</p>
 *
 * @author not attributable
 * @version 1.0
 * 问题（1）20081125 徐春英修改   修改原因：工艺树节点显示不全
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
                                          "请检查属性文件中是否配置树节点信息：" + key +
                                          "_treeobj",
                                          "提示信息",
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
     * 得到节电信息
     * @param info BaseValueInfo 树节点值对象
     * @return Object[] 长度为3（节点显示信息，节电浮动信息，节电排序信息）或长度为2（节点显示信息，节点排序信息）
     * ，其中节点排序信息为长度为3的数组，依次存放属性名、属性类型、属性值
     * 问题（1）20081125 徐春英修改   修改原因：工艺树节点显示不全
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
        //排序信息，属性名、属性类型、属性值
        Object[] orderNote = new Object[2];
        Object[] returnobj = null;
        String noteText = new String("");
        String tipText = null;
        try
        {
            for (int j = 0; j < noteTextVector.size(); j++)
            {
                //属性值
                Object value = null;
                //属性值显示
                String dispValue = null;
                //浮动文字
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
                //含有特殊符号
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
                //代码
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
                //问题（1）20081125 徐春英修改   修改原因：工艺树节点显示不全
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

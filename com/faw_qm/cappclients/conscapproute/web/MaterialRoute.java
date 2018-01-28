package com.faw_qm.cappclients.conscapproute.web;

import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.util.EJBServiceHelper;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;

import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;

import com.faw_qm.util.EJBServiceHelper;

import com.faw_qm.part.util.PartServiceRequest;

public class MaterialRoute
{
    public MaterialRoute()
    {}

    /**
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿���������嵥��Ϣ�� ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ� 0����ǰpart��BsoID�� 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ� 2����ǰpart�ı�ţ� 3����ǰpart�����ƣ� 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ�� ��������������ԣ��������ж��Ƶ����Լӵ���������С�
     * @param partID String ָ���㲿����bsoID
     * @param attrNames String ���Ƶ����ԣ�����Ϊ��
     * @throws QMException
     * @return Vector
     */
    public Vector getMaterialList(String partID, String attrNames) throws QMException
    {
        String[] attrNames1 = null;
        String[] affixAttrNames1 = null;
        // String[] routeList = null;//zz
        Vector allAttrNames = new Vector();
        if(attrNames != null && attrNames.length() > 0)
        {

            allAttrNames = getTableHeader(attrNames);

            if(allAttrNames != null && allAttrNames.size() > 0)
            {
                attrNames1 = (String[])allAttrNames.elementAt(0);
                //        for(int i=0;i<attrNames1.length;i++){
                //          System.out.println("attrNames1========="+attrNames1[i]);
                //        }
                // affixAttrNames1 = (String[])allAttrNames.elementAt(1);
                //        for(int i=0;i<affixAttrNames1.length;i++){
                //          System.out.println("affixAttrNames1========="+attrNames1[i]);
                //        }
                /*
                 * routeList = (String[]) allAttrNames.elementAt(2);//zz for(int i=0;i<routeList.length;i++){ System.out.println("routeList========="+attrNames1[i]); }
                 */
            }
        }
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
        // System.out.println("partIfc============"+partID);
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
        TechnicsRouteService routeService = null;
        routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");
        Vector vector = new Vector();
        vector = routeService.setMaterialList(partIfc, attrNames1, affixAttrNames1, partConfigSpecIfc);
        //  System.out.println(" ���˷����setMaterialList ���ص�vector " +vector );
        for(int i = 0;i < vector.size();i++)
        {
            // System.out.println(" xxxxxxxxxxxxxxxxxx" +  vector.elementAt(i));
            Object[] aa = (Object[])vector.elementAt(i);
            for(int j = 0;j < aa.length;j++)
            {
                //   System.out.println("  ��װ�ĸ�jsp��vector�ĵ�" + i +"�������Ԫ��" +aa[j]);
            }

        }
        return vector;
    }

    /**
     * ����ָ���ķָ������������ַ�����Ϊһϵ����ҵ��������ַ����� 2003.08.14�����µ����󣺿��Զ��㲿���ĸ������Խ��ж��ƣ� �������������Ĳ�����Ӧ�ö���ͨ���Ժ���չ���Խ������֡� �����µ�����"+viewName+partName-��չ������1-��չ������2"������ͨ������ǰΪ"+"�� ��չ������ǰΪ"-"��
     * ������Ϊ��������ͨ���Ժ���չ���ԣ���Ҫ�޸ĸ÷����ķ���ֵ����String[]�޸�ΪVector, ����Vector�к�������Ԫ�أ�ÿ��Ԫ�ض���String[]���ͣ���һ��String[]Ԫ�ر������е���ͨ�������� ��һ��String[]������չ��������
     * @param attrs : String
     * @return Vector
     */
    public Vector getTableHeader(String attrs)
    {
        attrs = attrs.trim();
        if(attrs == null || attrs.length() == 0)
        {

            Vector result = new Vector();
            return result;
        }
        //attrs = attrs.trim();
        if(!attrs.substring(0, 1).equals("-"))
        {
            attrs = " " + attrs;
        }
        Vector result = new Vector(); //�������Ľ����
        Vector temp1 = new Vector(); //�������е���ͨ������
        Vector temp2 = new Vector(); //�������е���չ������
        String tempString1 = ""; //��¼һ��������װ����ͨ�������ַ���
        String tempString2 = ""; //��¼һ��������װ����չ�������ַ���
        for(int i = 0;i < attrs.length();i++)
        {
            String nowChar = attrs.substring(i, i + 1);
            //���nowChar��+,-�Ļ�����tempString���شӵ�ǰ��
            if(nowChar.equals(" ") || nowChar.equals("-"))
            {
                //Ѱ����һ��"+", "-"���ڵ�λ��
                String tempAttr = attrs.substring(i + 1, attrs.length());
                int indexadd = tempAttr.indexOf(" ");
                int indexreduce = tempAttr.indexOf("-");
                if(indexadd == -1)
                {
                    if(indexreduce == -1)
                    {
                        if(nowChar.equals(" "))
                        {
                            temp1.addElement(tempAttr);
                        }else
                        {
                            temp2.addElement(tempAttr);
                        }
                    }else
                    {
                        if(nowChar.equals(" "))
                        {
                            temp1.addElement(tempAttr.substring(0, indexreduce));
                        }else
                        {
                            temp2.addElement(tempAttr.substring(0, indexreduce));
                        }
                    }
                }else
                {
                    if(indexreduce == -1)
                    {
                        if(nowChar.equals(" "))
                        {
                            temp1.addElement(tempAttr.substring(0, indexadd));
                        }else
                        {
                            temp2.addElement(tempAttr.substring(0, indexadd));
                        }
                    }else
                    {
                        int index = (indexadd - indexreduce) > 0 ? indexreduce : indexadd;
                        if(nowChar.equals(" "))
                        {
                            temp1.addElement(tempAttr.substring(0, index));
                        }else
                        {
                            temp2.addElement(tempAttr.substring(0, index));
                        }
                    }
                }
            }
        }
        String[] tempArray1 = new String[temp1.size()];
        for(int i = 0;i < temp1.size();i++)
        {
            tempArray1[i] = (temp1.elementAt(i)).toString();
        }
        result.addElement(tempArray1);
        String[] tempArray2 = new String[temp2.size()];
        for(int i = 0;i < temp2.size();i++)
        {
            tempArray2[i] = (temp2.elementAt(i)).toString();
        }
        result.addElement(tempArray2);
        return result;
    }

    /**
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ�� �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ�� 1��������������ԣ� BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true", "false"����������ת��Ϊ�ַ��ͣ����汾����ͼ�� 2������������ԣ� BsoID�����롢���ơ��ǣ��񣩿ɷ�("true",
     * "false")�������������������ԡ�
     * @param partID :String ָ�����㲿����bsoID.
     * @param attrNames :String ����Ҫ��������Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @throws QMException
     * @return Vector
     */
    public Vector getBOMList(String partID, String attrNames, String source, String type) throws QMException
    {
        Vector allAttrNames = new Vector();
        String[] attrNames1 = null;
        String[] affixAttrNames = null;
        if(attrNames != null && attrNames.length() > 0)
        {
            allAttrNames = getTableHeader(attrNames);
            if(allAttrNames != null && allAttrNames.size() > 0)
            {
                attrNames1 = (String[])allAttrNames.elementAt(0);
                affixAttrNames = (String[])allAttrNames.elementAt(1);
            }
        }
        PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
        QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
        TechnicsRouteService routeService = null;
        routeService = (TechnicsRouteService)EJBServiceHelper.getService("consTechnicsRouteService");

        return routeService.setBOMList(partIfc, attrNames1, affixAttrNames, source, type, partConfigSpecIfc);
    }

}

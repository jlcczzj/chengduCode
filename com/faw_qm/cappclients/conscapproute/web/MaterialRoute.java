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
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的物料清单信息。 返回结果是vector,其中vector中的每个元素都是一个集合： 0：当前part的BsoID； 1：当前part所在的级别，转化为字符型； 2：当前part的编号； 3：当前part的名称； 4：当前part被最顶层部件使用的数量，转化为字符型；
     * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图； ：如果定制了属性：按照所有定制的属性加到结果集合中。
     * @param partID String 指定零部件的bsoID
     * @param attrNames String 定制的属性，可以为空
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
        //  System.out.println(" 调了服务的setMaterialList 返回的vector " +vector );
        for(int i = 0;i < vector.size();i++)
        {
            // System.out.println(" xxxxxxxxxxxxxxxxxx" +  vector.elementAt(i));
            Object[] aa = (Object[])vector.elementAt(i);
            for(int j = 0;j < aa.length;j++)
            {
                //   System.out.println("  封装的给jsp的vector的第" + i +"个数组的元素" +aa[j]);
            }

        }
        return vector;
    }

    /**
     * 根据指定的分割符，把输入的字符串截为一系列有业务意义的字符串。 2003.08.14增加新的需求：可以对零部件的附加属性进行定制： 这样，传过来的参数就应该对普通属性和扩展属性进行区分。 做如下的区分"+viewName+partName-扩展属性名1-扩展属性名2"，即普通属性名前为"+"， 扩展属性名前为"-"。
     * 这样，为了区分普通属性和扩展属性，需要修改该方法的返回值，由String[]修改为Vector, 现在Vector中含有两个元素，每个元素都是String[]类型，第一个String[]元素保存所有的普通属性名， 后一个String[]保存扩展属性名。
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
        Vector result = new Vector(); //保存最后的结果：
        Vector temp1 = new Vector(); //保存所有的普通属性名
        Vector temp2 = new Vector(); //保存所有的扩展属性名
        String tempString1 = ""; //记录一个正在组装的普通属性名字符串
        String tempString2 = ""; //记录一个正在组装的扩展属性名字符串
        for(int i = 0;i < attrs.length();i++)
        {
            String nowChar = attrs.substring(i, i + 1);
            //如果nowChar是+,-的话，让tempString记载从当前的
            if(nowChar.equals(" ") || nowChar.equals("-"))
            {
                //寻找下一个"+", "-"所在的位置
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
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息： 1、如果不定制属性： BsoID，号码、名称、是（否）可分（"true", "false"）、数量（转化为字符型）、版本和视图； 2、如果定制属性： BsoID，号码、名称、是（否）可分("true",
     * "false")、数量、其他定制属性。
     * @param partID :String 指定的零部件的bsoID.
     * @param attrNames :String 定制要输出的属性集合；如果为空，则按标准版输出。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
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

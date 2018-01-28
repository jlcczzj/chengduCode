/** 生成程序QMSchemaSClass.java    1.0  2003/02/06
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 变速箱公司属性显示名 liuyang 2013-3-26
 */

package com.faw_qm.cappclients.beans.query;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.util.PropertyDescript;


/**
 * <p>Title:方案 </p>
 * <p>Description:存储欲查询的方案类（查询哪个业务对象的哪些属性）
 * 及相应的显示界面 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 杨晓辉
 * @version 1.0
 */


public class CappSchemaSClass implements ActionListener, ItemListener
{
    private CappBusinessInfo mySCO; //选中的业务对象
    private Vector mySAO; //CappSchemaGroup对象的集合
    private Vector columnHeadings; // 列标题的集合(选中的属性对象的集合)
    private JLabel mySL; //类型提示信息
    private JTextField mySC; //显示类型
    private CappMultiList mySA; //显示属性
    private Panel myGP; //组件容器，控制布局
    private JLabel myGL; //属性组提示信息
    private JTextField myGT; //显示属性组名
    private static boolean verbose = QM.getVerbose(); //debug标识


    /**
     * 默认构造方法
     */
    public CappSchemaSClass()
    {
        mySAO = new Vector();
        columnHeadings = new Vector();
        myGP = new Panel();
    }


    /**
     * 访问mySL
     * @return
     */
    public JLabel getLabelComp()
    {
        return mySL;
    }


    /**
     * 访问mySC
     * @return
     */
    public JTextField getClassComp()
    {
        return mySC;
    }


    /**
     * 访问mySA
     * @return
     */
    public CappMultiList getAttrComp()
    {
        return mySA;
    }


    /**
     * 访问myGP
     */

    public Panel getGroupComp()
    {
        return myGP;
    }


    /**
     * 构建设计时间界面
     */
    public void createDevTime()
    {
        mySA = new CappMultiList(2, false);
        mySC = new JTextField(24);
        myGT = new JTextField(16);
        ResourceBundle resourcebundle = QM.getMessagesResource();
        mySL = new JLabel(resourcebundle.getString("32"));
        myGL = new JLabel(resourcebundle.getString("43"));
        //try
        //{
        mySA.setHeading(resourcebundle.getString("41"), 0);
        //mySA.setColumnAlignment(0, 0);//???
        mySA.setHeading(resourcebundle.getString("42"), 1);
        //mySA.setColumnAlignment(1, 0);//???
        // }
        //catch(PropertyVetoException propertyvetoexception)
        // {
        //     propertyvetoexception.printStackTrace();
        // }
        myGP.setLayout(new BorderLayout(0, 0));
        myGP.add("West", myGL);
        myGP.add("Center", myGT);
        myGT.addActionListener(this);
        mySA.addActionListener(this);
        mySA.addItemListener(this);
    }


    /**
     * 设置检索方案
     * @param Cappschemasclass
     */
    public void setSClazz(CappSchemaSClass qmschemasclass)
    {
        mySCO = qmschemasclass.getBusinessInfo();
        if (mySC != null)
        {
            mySC.setText(getBsoName());
        }
        mySAO.removeAllElements();
        columnHeadings.removeAllElements();
        for (int i = 0; i < qmschemasclass.total(); i++)
        {
            CappSchemaGroup qmschemagroup = new CappSchemaGroup(qmschemasclass.
                    getGroup(i).
                    getName());
            mySAO.addElement(qmschemagroup);
            for (int j = 0; j < qmschemasclass.getGroup(i).total(); j++)
            {
                qmschemagroup.add(qmschemasclass.getGroup(i).get(j));
            }
        }
        showCriteria();
    }


    /**
     * 设置检索方案
     * @param a_type 类型集(C,G,A,D,E)
     * @param a_schema 属性集
     */
    public void setSClazz(String a_type[], String a_schema[])
            throws QMRemoteException
    {
        ResourceBundle resourcebundle = QM.getMessagesResource();
        Object obj = null;
        mySCO = new CappBusinessInfo(a_schema[0]);

        if (mySC != null)
        {
            mySC.setText(getBsoName());
        }

        mySAO.removeAllElements();
        columnHeadings.removeAllElements();
        CappSchemaGroup qmschemagroup = null;
        for (int i = 1; i < a_schema.length; i++)
        {
            if (a_type[i] == CappSchema.GroupTYPE)
            {
                qmschemagroup = new CappSchemaGroup(a_schema[i]);
                mySAO.addElement(qmschemagroup);
                if (verbose)
                {
                    System.out.println("the new QMSchemaGroup from [][] is:::" +
                                       qmschemagroup.getName());
                }
            }
            else
            if (a_type[i] == CappSchema.DISPLAY_ONLY)
            {
                try
                {
                    columnHeadings.addElement(mySCO.getAttribute(a_schema[i]));
                    if (verbose)
                    {
                        System.out.println("add display attribute is:::" +
                                           mySCO.getAttribute(a_schema[i]).
                                           getPropertyName());
                    }
                }
                catch (Exception exception1)
                {
                    exception1.printStackTrace();
                }
            }
            else
            if (qmschemagroup != null &&
                a_type[i].equalsIgnoreCase(CappSchema.AttributeTYPE))
            {
                try
                {
                    if (verbose)
                    {
                        System.out.println("wait to add attribute to" +
                                           qmschemagroup.getName() + " is:::" +
                                           a_schema[i]);
                    }
                    PropertyDescript propertydescript = mySCO.getAttribute(
                            a_schema[i]);
                    if (verbose)
                    {
                        System.out.println(
                                "get propertydescript from QMBusinessInfo" +
                                qmschemagroup.getName() + " is:::" +
                                propertydescript);
                    }
                    qmschemagroup.add(propertydescript);
                    columnHeadings.addElement(propertydescript);
                    if (verbose)
                    {
                        System.out.println("add attribute to" +
                                           qmschemagroup.getName() + " is:::" +
                                           propertydescript.getPropertyName());
                    }
                }
                catch (Exception exception2)
                {
                    exception2.printStackTrace();
                }
            }
            //xuejing add
            else
            if (a_type[i].equalsIgnoreCase(CappSchema.SECOND_CLASSIFI))
            {
                qmschemagroup.setSecondClassfi(a_schema[i]);
            }
            else
            if (a_type[i].equalsIgnoreCase(CappSchema.DEFAULT_VALUE))
            {
                StringTokenizer s = new StringTokenizer(a_schema[i], ",");
                qmschemagroup.setDefaultValues(s.nextToken(), s.nextToken());

            }

        } //end for

        showCriteria();
    }


    /**
     * 重置属性列表头
     */
    protected void resetHeadings()
    {
        columnHeadings.removeAllElements();
        for (int i = 0; i < total(); i++)
        {
            CappSchemaGroup wtschemagroup = (CappSchemaGroup) mySAO.elementAt(i);
            for (int j = 0; j < getGroup(i).total(); j++)
            {
                columnHeadings.addElement(getGroup(i).get(j));
            }
        }
        showCriteria();
    }


    /**
     * 访问检索方案类的bsoName
     * @return
     */
    public String getBsoName()
    {
        if (mySCO == null)
        {
            return null;
        }
        try
        {
            return mySCO.getBsoName();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }


    /**
     * 判断自省信息是否包含master信息
     * @return
     */
    public boolean hasMaster()
    {
        boolean flag = false;
        if (mySCO != null)
        {
            flag = mySCO.hasMaster();
        }
        return flag;
    }


    /**
     * 获得master的BsoName ,不存在master则返回null,建议用hasMaster方法判断
     * @return
     */
    public String getMasterBsoName()
    {
        String masterBsoName = null;
        if (hasMaster())
        {
            masterBsoName = mySCO.getMasterBsoName();
        }
        return masterBsoName;
    }


    /**
     * 判断具体的attrName是否Iterated属性
     * @param attrName
     * @return
     */
    public boolean isIteratedProperty(String attrName)
    {
        return mySCO.isIteratedProperty(attrName);
    }


    /**
     * 判断具体的attrName是否master属性
     * @param attrName
     * @return
     */
    protected boolean isMasterProperty(String attrName)
    {
        return mySCO.isMasterProperty(attrName);
    }


    /**
     * 访问业务类显示名
     * @return
     */
    public String getBusinessClassDisplayName()
    {
        if (mySCO == null)
        {
            return null;
        }
        try
        {
            return mySCO.getBusinessClassDisplayName();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }


    /**
     * 处理ActionEvent事件
     * @param actionevent
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        if (actionevent.getSource() == mySA)
        {
            editLoad();
            return;
        }
        if (actionevent.getSource() == myGT)
        {
            editGroup(actionevent.getActionCommand());
        }
    }


    /**
     * 添加属性
     * @param propertydescript
     */
    public void addAttribute(PropertyDescript propertydescript)
    {
        for (int i = 0; i < total(); i++)
        {
            CappSchemaGroup qmschemagroup = getGroup(i);
            for (int k = 0; k < qmschemagroup.total(); k++)
            {
                if (qmschemagroup.get(k) == propertydescript)
                {
                    return;
                }
            }
        }
        columnHeadings.removeAllElements();
        int j = findGroup();
        CappSchemaGroup qmschemagroup1 = getGroup(0);
        if (j != -1)
        {
            qmschemagroup1 = getGroup(j);
        }
        qmschemagroup1.add(propertydescript);
        showCriteria();
        mySA.setSelectedRow(findIndex(j, -1));
    }


    /**
     * 移出选中的属性
     */
    public void removeCmd()
    {
        int i = findGroup();
        if (i == -1)
        {
            return;
        }
        int j = findAttr();
        columnHeadings.removeAllElements();
        if (j == -1)
        {
            mySAO.removeElementAt(i);
        }
        else
        {
            getGroup(i).remove(j);
        }
        showCriteria();
    }


    /**
     * 上移选中属性
     */
    public void upCmd()
    {
        int i = findGroup();
        if (i == -1)
        {
            return;
        }
        int j = findAttr();
        columnHeadings.removeAllElements();
        if (j == -1)
        {
            if (i == 0)
            {
                return;
            }
            else
            {
                CappSchemaGroup wtschemagroup = getGroup(i);
                mySAO.setElementAt(getGroup(i - 1), i);
                mySAO.setElementAt(wtschemagroup, i - 1);
                showCriteria();
                mySA.setSelectedRow(findIndex(i - 1, -1));
                return;
            }
        }
        if (i == 0 && j == 0)
        {
            return;
        }
        if (j == 0)
        {
            getGroup(i - 1).add(getGroup(i).remove(j));
            showCriteria();
            mySA.setSelectedRow(findIndex(i - 1, getGroup(i - 1).total() - 1));
            return;
        }
        else
        {
            getGroup(i).up(j);
            showCriteria();
            mySA.setSelectedRow(findIndex(i, j - 1));
            return;
        }
    }


    /**
     * 下移选中属性
     */
    public void downCmd()
    {
        int i = findGroup();
        if (i == -1)
        {
            return;
        }
        int j = findAttr();
        columnHeadings.removeAllElements();
        if (j == -1)
        {
            if (i == total() - 1)
            {
                return;
            }
            else
            {
                CappSchemaGroup wtschemagroup = getGroup(i);
                mySAO.setElementAt(getGroup(i + 1), i);
                mySAO.setElementAt(wtschemagroup, i + 1);
                showCriteria();
                mySA.setSelectedRow(findIndex(i + 1, -1));
                return;
            }
        }
        if (i == total() - 1 && j == getGroup(total() - 1).total() - 1)
        {
            return;
        }
        if (j == getGroup(i).total() - 1)
        {
            getGroup(i + 1).insert(getGroup(i).remove(j));
            showCriteria();
            mySA.setSelectedRow(findIndex(i + 1, 0));
            return;
        }
        else
        {
            getGroup(i).down(j);
            showCriteria();
            mySA.setSelectedRow(findIndex(i, j + 1));
            return;
        }
    }


    /**
     * 编辑选中的属性组
     * @param s 属性组名
     */
    public void editGroup(String s)
    {
        if (s == null || s.length() == 0)
        {
            return;
        }
        int i = findGroup();
        int j = findAttr();
        if (i == -1 || i != -1 && j != -1)
        {
            addGroup(s);
        }
        else
        {
            getGroup(i).setName(s);
        }
        showCriteria();
    }


    /**
     * 获得选中属性组的索引
     * @return
     */
    public int findGroup()
    {
        int i = mySA.getSelectedRow();
        if (i == -1)
        {
            return i;
        }
        int j = 0;
        for (int k = 0; k < total(); k++)
        {
            CappSchemaGroup qmschemagroup = getGroup(k);
            j += 1 + qmschemagroup.total();
            if (i < j)
            {
                return k;
            }
        }
        return -1;
    }


    /**
     * 获得选中属性的索引
     * @return
     */
    public int findAttr()
    {
        int i = mySA.getSelectedRow();
        if (i == -1)
        {
            return i;
        }
        int j = 0;
        for (int k = 0; k < total(); k++)
        {
            CappSchemaGroup qmschemagroup = getGroup(k);
            j += 1 + qmschemagroup.total();
            if (i < j)
            {
                return qmschemagroup.total() - (j - i);
            }
        }
        return -1;
    }


    /**
     * 获得具体属性组索引(i)中具体属性索引(j)对应的全局索引
     * @param i
     * @param j
     * @return
     */
    public int findIndex(int i, int j)
    {
        int k = 0;
        for (int l = 0; l < total(); l++)
        {
            CappSchemaGroup qmschemagroup = getGroup(l);
            if (l == i)
            {
                return k + j + 1;
            }
            k += 1 + qmschemagroup.total();
        }

        return 0;
    }


    /**
     * 处理ItemEvent
     * @param itemevent
     */
    public void itemStateChanged(ItemEvent itemevent)
    {
        if (itemevent.getSource() == mySA)
        {
            editLoad();
        }
    }


    /**
     * 构建属性编辑界面
     */
    public void editLoad()
    {
        int i = findGroup();
        int j = findAttr();
        if (i != -1 && j == -1)
        {
            myGT.setText(getGroup(i).getName());
            return;
        }
        else
        {
            myGT.setText("");
            return;
        }
    }


    /**
     * 设置业务类型信息
     * @param qmbusinessinfo
     */
    public void setBusinessInfo(CappBusinessInfo qmbusinessinfo)
    {
        if (mySCO == qmbusinessinfo)
        {
            return;
        }
        mySCO = qmbusinessinfo;
        if (mySC != null)
        {
            mySC.setText(getBsoName()); //getClassname()
        }
        mySAO.removeAllElements();
        columnHeadings.removeAllElements();
        if (mySCO != null)
        {
            addGroup("Criteria");
        }
        showCriteria();
    }


    /**
     * 显示属性约束
     */
    public void showCriteria()
    {
        if (mySA == null)
        {
            return;
        }
        mySA.clear();
        int i = 0;
        for (int j = 0; j < total(); j++)
        {
            CappSchemaGroup qmschemagroup = getGroup(j);
            mySA.addTextCell(i, 0, qmschemagroup.getName());
            mySA.addTextCell(i, 1, " --- ");
            i++;
            for (int k = 0; k < qmschemagroup.total(); k++)
            {
                mySA.addTextCell(i, 1, qmschemagroup.getName(k));
                i++;
            }
        }
    }


    /**
     * 添加属性组
     * @param s 属性组名
     */
    public void addGroup(String s)
    {
        columnHeadings.removeAllElements();
        mySAO.addElement(new CappSchemaGroup(s));
        showCriteria();
    }


    /**
     * 访问业务类型信息
     * @return
     */
    public CappBusinessInfo getBusinessInfo()
    {
        return mySCO;
    }


    /**
     * 访问业务类对应的值对象
     * @return
     */
    public Class getBusinessClass()
    {
        if (mySCO == null)
        {
            return null;
        }
        try
        {

            return mySCO.getValueInfoClass();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }


    /**
     * 访问具体索引对应的属性组
     * @param i
     * @return
     */
    public CappSchemaGroup getGroup(int i)
    {
        return (CappSchemaGroup) mySAO.elementAt(i);
    }


    /**
     * 访问具体索引对应的属性
     * @param i
     * @return
     */
    public PropertyDescript getColumnAttribute(int i)
    {
        return (PropertyDescript) columnHeadings.elementAt(i);
    }


    /**
     * 访问具体索引对应的列标题
     * @param i
     * @return
     */
    public String getHeadingName(int i)
    {
    	//CCBegin SS1
    	Boolean yy = false; 	
       	try {
   	    	Class[] paraClass = {};
   	        Object[] objs = {};
   	        String className = "com.faw_qm.doc.util.DocServiceHelper";
   	        String methodName = "isBSXGroup";
   	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
   	        in.setClassName(className);
   	        in.setMethodName(methodName);
   	        in.setParaClasses(paraClass);
   	        in.setParaValues(objs);
   	        RequestServer server = null;
   	        server = RequestServerFactory.getRequestServer();
   		
   			yy = (Boolean) server.request(in);
   		} catch (QMRemoteException e) {
   			e.printStackTrace();
   		}
   		if(yy){
   			String attrname = CappBusinessInfo.getAttributeName(getColumnAttribute(i));
   			String disname = null;
   			if(attrname.equals("controlPlanNum")){
   			    disname = "质保部文件编号";
   			   return disname;
   			}
   		   if(attrname.equals("femaNum")){
   			   disname = "产品平台";
   			   return disname;
   			}
   		   if(attrname.equals("taskInstructNum")){
   			   disname = "生产线";
   			   return disname;
   		   }
   		return CappBusinessInfo.getAttributeDisplayName(getColumnAttribute(i));
   		}
   		//CCEnd SS1
        return CappBusinessInfo.getAttributeDisplayName(getColumnAttribute(i));
    }


    /**
     * 统计属性组数
     * @return
     */
    public int total()
    {
        return mySAO.size();
    }


    /**
     * 统计显示列数
     * @return
     */
    public int totalHeadings()
    {
        return columnHeadings.size();
    }


    /**
     * 访问属性组集
     * @return
     */
    public Vector getMySAO()
    {
        return mySAO;
    }


    /**
     * 访问组件容器前景色
     * @return
     */
    public Color getCanvasForeground()
    {
        return mySA.getCellFg();
    }


    /**
     * 设置组件容器前景色
     * @param color
     */
    public void setCanvasForeground(Color color)
    {
        //try
        // {
        mySC.setForeground(color);
        mySA.setCellFg(color);
        myGT.setForeground(color);
        repaint();
        return;
        //  }
        //    catch(PropertyVetoException propertyvetoexception)
        //  {
        //        propertyvetoexception.printStackTrace();
        //    }
    }


    /**
     * 访问组件容器背景色
     * @return
     */
    public Color getCanvasBackground()
    {
        return mySA.getCellBg();
    }


    /**
     * 设置组件容器背景色
     * @param color
     */
    public void setCanvasBackground(Color color)
    {
        //try
        // {
        mySC.setBackground(color);
        mySA.setCellBg(color);
        myGT.setBackground(color);
        repaint();
        return;
        // }
        // catch(PropertyVetoException propertyvetoexception)
        //  {
        //       propertyvetoexception.printStackTrace();
        //  }
    }


    /**
     * 访问前景色
     * @return
     */
    public Color getForeground()
    {
        return mySA.getHeadingFg();
    }


    /**
     * 设置前景色
     * @param color
     */
    public void setForeground(Color color)
    {
        //try
        //{
        //mySL.setTextColor(color);
        mySL.setForeground(color);
        mySA.setHeadingFg(color);
        myGP.setForeground(color);
        myGL.setForeground(color);
        // }
        // catch(PropertyVetoException propertyvetoexception)
        //  {
        //      propertyvetoexception.printStackTrace();
        // }
        repaint();
    }


    /**
     * 访问前景色
     * @return
     */
    public Color getBackground()
    {
        return mySA.getHeadingBg();
    }


    /**
     * 设置背景色
     * @param color
     */
    public void setBackground(Color color)
    {
        //try
        // {
        mySL.setBackground(color);
        mySA.setHeadingBg(color);
        myGP.setBackground(color);
        myGL.setBackground(color);
        // }
        // catch(PropertyVetoException propertyvetoexception)
        // {
        //      propertyvetoexception.printStackTrace();
        //  }
        repaint();
    }


    /**
     * 重画组件
     */
    public void repaint()
    {
        mySL.repaint();
        mySC.repaint();
        myGP.repaint();
        myGL.repaint();
        myGT.repaint();
    }

}

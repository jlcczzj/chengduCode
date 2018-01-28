/** ���ɳ���QMSchemaSClass.java    1.0  2003/02/06
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �����乫˾������ʾ�� liuyang 2013-3-26
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
 * <p>Title:���� </p>
 * <p>Description:�洢����ѯ�ķ����ࣨ��ѯ�ĸ�ҵ��������Щ���ԣ�
 * ����Ӧ����ʾ���� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ������
 * @version 1.0
 */


public class CappSchemaSClass implements ActionListener, ItemListener
{
    private CappBusinessInfo mySCO; //ѡ�е�ҵ�����
    private Vector mySAO; //CappSchemaGroup����ļ���
    private Vector columnHeadings; // �б���ļ���(ѡ�е����Զ���ļ���)
    private JLabel mySL; //������ʾ��Ϣ
    private JTextField mySC; //��ʾ����
    private CappMultiList mySA; //��ʾ����
    private Panel myGP; //������������Ʋ���
    private JLabel myGL; //��������ʾ��Ϣ
    private JTextField myGT; //��ʾ��������
    private static boolean verbose = QM.getVerbose(); //debug��ʶ


    /**
     * Ĭ�Ϲ��췽��
     */
    public CappSchemaSClass()
    {
        mySAO = new Vector();
        columnHeadings = new Vector();
        myGP = new Panel();
    }


    /**
     * ����mySL
     * @return
     */
    public JLabel getLabelComp()
    {
        return mySL;
    }


    /**
     * ����mySC
     * @return
     */
    public JTextField getClassComp()
    {
        return mySC;
    }


    /**
     * ����mySA
     * @return
     */
    public CappMultiList getAttrComp()
    {
        return mySA;
    }


    /**
     * ����myGP
     */

    public Panel getGroupComp()
    {
        return myGP;
    }


    /**
     * �������ʱ�����
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
     * ���ü�������
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
     * ���ü�������
     * @param a_type ���ͼ�(C,G,A,D,E)
     * @param a_schema ���Լ�
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
     * ���������б�ͷ
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
     * ���ʼ����������bsoName
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
     * �ж���ʡ��Ϣ�Ƿ����master��Ϣ
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
     * ���master��BsoName ,������master�򷵻�null,������hasMaster�����ж�
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
     * �жϾ����attrName�Ƿ�Iterated����
     * @param attrName
     * @return
     */
    public boolean isIteratedProperty(String attrName)
    {
        return mySCO.isIteratedProperty(attrName);
    }


    /**
     * �жϾ����attrName�Ƿ�master����
     * @param attrName
     * @return
     */
    protected boolean isMasterProperty(String attrName)
    {
        return mySCO.isMasterProperty(attrName);
    }


    /**
     * ����ҵ������ʾ��
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
     * ����ActionEvent�¼�
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
     * �������
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
     * �Ƴ�ѡ�е�����
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
     * ����ѡ������
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
     * ����ѡ������
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
     * �༭ѡ�е�������
     * @param s ��������
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
     * ���ѡ�������������
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
     * ���ѡ�����Ե�����
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
     * ��þ�������������(i)�о�����������(j)��Ӧ��ȫ������
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
     * ����ItemEvent
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
     * �������Ա༭����
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
     * ����ҵ��������Ϣ
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
     * ��ʾ����Լ��
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
     * ���������
     * @param s ��������
     */
    public void addGroup(String s)
    {
        columnHeadings.removeAllElements();
        mySAO.addElement(new CappSchemaGroup(s));
        showCriteria();
    }


    /**
     * ����ҵ��������Ϣ
     * @return
     */
    public CappBusinessInfo getBusinessInfo()
    {
        return mySCO;
    }


    /**
     * ����ҵ�����Ӧ��ֵ����
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
     * ���ʾ���������Ӧ��������
     * @param i
     * @return
     */
    public CappSchemaGroup getGroup(int i)
    {
        return (CappSchemaGroup) mySAO.elementAt(i);
    }


    /**
     * ���ʾ���������Ӧ������
     * @param i
     * @return
     */
    public PropertyDescript getColumnAttribute(int i)
    {
        return (PropertyDescript) columnHeadings.elementAt(i);
    }


    /**
     * ���ʾ���������Ӧ���б���
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
   			    disname = "�ʱ����ļ����";
   			   return disname;
   			}
   		   if(attrname.equals("femaNum")){
   			   disname = "��Ʒƽ̨";
   			   return disname;
   			}
   		   if(attrname.equals("taskInstructNum")){
   			   disname = "������";
   			   return disname;
   		   }
   		return CappBusinessInfo.getAttributeDisplayName(getColumnAttribute(i));
   		}
   		//CCEnd SS1
        return CappBusinessInfo.getAttributeDisplayName(getColumnAttribute(i));
    }


    /**
     * ͳ����������
     * @return
     */
    public int total()
    {
        return mySAO.size();
    }


    /**
     * ͳ����ʾ����
     * @return
     */
    public int totalHeadings()
    {
        return columnHeadings.size();
    }


    /**
     * ���������鼯
     * @return
     */
    public Vector getMySAO()
    {
        return mySAO;
    }


    /**
     * �����������ǰ��ɫ
     * @return
     */
    public Color getCanvasForeground()
    {
        return mySA.getCellFg();
    }


    /**
     * �����������ǰ��ɫ
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
     * ���������������ɫ
     * @return
     */
    public Color getCanvasBackground()
    {
        return mySA.getCellBg();
    }


    /**
     * ���������������ɫ
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
     * ����ǰ��ɫ
     * @return
     */
    public Color getForeground()
    {
        return mySA.getHeadingFg();
    }


    /**
     * ����ǰ��ɫ
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
     * ����ǰ��ɫ
     * @return
     */
    public Color getBackground()
    {
        return mySA.getHeadingBg();
    }


    /**
     * ���ñ���ɫ
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
     * �ػ����
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

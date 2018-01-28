/** ���ɳ��� EquipmentToolMaintainJDialog.java    1.0    2003/08/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/07/13  ������   �μ�DefactID=2556
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.resource.exception.ResourceException;
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.model.QMEquipmentIfc;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMToolInfo;


/**
 * <p>Title:ά���豸�빤װ�����Ľ�����</p>
 * <p>���豸�빤װ�Ĺ������浽�豸���С��豸�б�͹�װ�б���������һ���б�����һ��</p>
 * <p>���ݣ��豸��װ��������豸����װ��ѡ���˶������ݣ�ѡ��������ʱ��������ʾ��Ҫ��</p>
 * <p>����ѡ��</p>
 * <p>����豸�б��е��豸��һ������ϵͳ�����豸�빤װ�б��е����й�װ����������ϵ��</p>
 * <p>����豸�б��е��豸�Ƕ�������Ȼ��װ�б�����һ������ϵͳ�ֱ�ÿ���豸��ù�װ����������ϵ��</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class EquipmentToolMaintainJDialog extends JDialog
{
    private JPanel mainJPanel = new JPanel();
    private JPanel buttonJPanel = new JPanel();
    private JPanel equipmentJPanel = new JPanel();
    private JPanel toolJPanel = new JPanel();
    private TitledBorder titledBorder1;
    private TitledBorder titledBorder2;
    private JLabel statusJLabel = new JLabel();

    private JButton okJButton = new JButton();
    private JButton cancelJButton = new JButton();


    /**�豸�б�*/
    private ComponentMultiList equipMultiList = new ComponentMultiList();


    /**��װ�б�*/
    private ComponentMultiList toolMultiList = new ComponentMultiList();


    /**�豸����*/
    private Vector equipmentVector = new Vector();


    /**��װ����,���ڻ����ύ*/
    private Vector toolVector = new Vector();


    /**���ڻ��������б��е��豸*/
    private HashMap equipmentMap = new HashMap();


    /**���ڱ����Դ�ļ�·��*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();


    /**���ڱ����Դ*/
    protected static ResourceBundle resource = null;


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**
     * ���캯��
     */
    public EquipmentToolMaintainJDialog(JFrame frame)
    {
        super(frame, "", true);
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        setModal(true);
        equipMultiList.setHeadings(new String[]
                                   {"ID", "�豸���", "�豸����", "�豸�ͺ�", "ƽ��ͼ��", "��Ҫ"});
        equipMultiList.setRelColWidth(new int[]
                                      {0, 2, 2, 2, 2, 1});
        equipMultiList.setColsEnabled(new int[]
                                      {5}
                                      , true);
        equipMultiList.setCellEditable(false);

        toolMultiList.setHeadings(new String[]
                                  {"ID", "��װ���", "��װ����"});
        toolMultiList.setRelColWidth(new int[]
                                     {0, 2, 2});
        toolMultiList.setCellEditable(false);

        titledBorder1 = new TitledBorder(
                QMMessage.getLocalizedMessage(RESOURCE, "selectedEquipment", null));
        titledBorder2 = new TitledBorder(
                QMMessage.getLocalizedMessage(RESOURCE, "selectedTool", null));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
        buttonJPanel.setLayout(gridBagLayout1);
        mainJPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainJPanel.setLayout(gridBagLayout4);
        getContentPane().setLayout(gridBagLayout5);
        setSize(650, 500);
        setTitle(QMMessage.getLocalizedMessage(RESOURCE,
                                               "maintainToolEquipmentTitle", null));

        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        equipmentJPanel.setBorder(titledBorder1);
        equipmentJPanel.setLayout(gridBagLayout2);
        toolJPanel.setBorder(titledBorder2);
        toolJPanel.setLayout(gridBagLayout3);
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 32, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        mainJPanel.add(toolJPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 20, 10, 20), 0, 0));
        toolJPanel.add(toolMultiList,
                       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(10, 10, 10, 10), 0, 0));
        mainJPanel.add(equipmentJPanel,
                       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(10, 20, 5, 20), 0, 0));
        equipmentJPanel.add(equipMultiList,
                            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));
        getContentPane().add(buttonJPanel,
                             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 15), 0, 0));
        getContentPane().add(mainJPanel,
                             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 15, 0, 15), 0, 0));
        getContentPane().add(statusJLabel,
                             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 14));
        localize();
    }


    /**
     * ������Ϣ���ػ�
     */
    private void localize()
    {
        initResources();

        try
        {
            equipMultiList.setHeadings(new String[]
                                       {"ID",
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentNumber", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentName", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentType", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "planeNumber", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "necessary", null)});
            toolMultiList.setHeadings(new String[]
                                      {"ID",
                                      QMMessage.getLocalizedMessage(RESOURCE,
                    "toolNumber", null),
                                      QMMessage.getLocalizedMessage(RESOURCE,
                    "toolName", null)});
            okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "OkJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(this, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     *��ʼ����ʹ�õ���Դ����Ϣ��
     */
    protected void initResources()
    {
        try
        {
            if (resource == null)
            {
                resource = ResourceBundle.getBundle(RESOURCE,
                        QMCt.getContext().getLocale());

            }
        }
        catch (MissingResourceException mre)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(null,
                                          CappLMRB.MISSING_RESOURCER,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }


    /**
     * ���ý����Ƿ�ɼ�
     * @param flag boolean �����Ƿ�ɼ�
     */
    public void setVisible(boolean flag)
    {
        setViewLocation();
        super.setVisible(flag);
    }


    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);
    }


    /**
     * ��ѡ����豸��ʾ���б���
     * @param v ѡ����豸����
     */
    public void populateEquipments(Vector v)
    {
        equipmentVector = v;
        // ���豸������ʾ���豸�б���
        equipmentsToMultiList(equipmentVector);
    }


    /**
     * ��ѡ��Ĺ�װ��ʾ���б���
     * @param v ѡ��Ĺ�װ����
     */
    public void populateTools(Vector v)
    {
        toolVector = v;
        toolsToMultiList(toolVector);
    }


    /**
     * ���豸��ʾ���豸�б���
     * @param �豸����
     */
    private void equipmentsToMultiList(Vector equipmentsVector)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.equipmentsToMultiList() begin...");
        }
        equipMultiList.clear();
        for (int i = 0; i < equipmentsVector.size(); i++)
        {
            QMEquipmentInfo relationEquipmentInfo
                    = (QMEquipmentInfo) equipmentsVector.elementAt(i);
            //�б��������飺"�豸BsoID","�豸���","�豸����","�豸�ͺ�","ƽ��ͼ��"
            equipMultiList.addTextCell(i, 0, relationEquipmentInfo.getBsoID());
            equipMultiList.addTextCell(i, 1, relationEquipmentInfo.getEqNum());
            equipMultiList.addTextCell(i, 2, relationEquipmentInfo.getEqName());
            equipMultiList.addTextCell(i, 3,
                                       relationEquipmentInfo.getEqType().
                                       getCodeContent());
            equipMultiList.addTextCell(i, 4, relationEquipmentInfo.getPlaneNum());
            //���б��е�5�����ѡ��������õ�5�в�ѡ��
            //CCBeginby leixiao 2010-11-26 �豸�빤װ������Ҫ����Ĭ��ѡ��
            equipMultiList.addCheckboxCell(i, 5, true);
          //CCEndby leixiao 2010-11-26 �豸�빤װ������Ҫ����Ĭ��ѡ��
            //���豸�������ڻ���Ĺ�����
            equipmentMap.put(relationEquipmentInfo.getBsoID(),
                             relationEquipmentInfo);

        } //end for

        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.equipmentsToMultiList() end...return is void");
        }
    }


    /**
     * ����װ��ʾ���б���
     * @param ��װ����
     */
    private void toolsToMultiList(Vector toolsVector)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.EquipmentToolMaintainJDialog.toolsToMultiList() begin...");
        }
        String as[] = new String[toolsVector.size()];
        for (int i = 0; i < toolsVector.size(); i++)
        {
            QMToolInfo relationToolInfo = (QMToolInfo) toolsVector.elementAt(i);
            //�б��������飺"��װID","��װ���","��װ����"
            as[i] = relationToolInfo.getBsoID() + ";"
                    + relationToolInfo.getToolNum() + ";"
                    + relationToolInfo.getToolName();
        } //end for

        //�ѹ�װ����������б�
        toolMultiList.setListItems(as);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.toolsToMultiList() end...return is void");
        }
    }


    /**
     * �������ѡ���ˡ���Ҫ�����豸
     * @return ѡ���ˡ���Ҫ�����豸�ļ���
     */
    public Vector getNecessaryRelationEquipment()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getNecessaryRelationEquipment() begin...");
        }
        Vector v = new Vector();

        for (int i = 0; i < equipmentVector.size(); i++)
        {
            //���ĳ�豸�ǡ���Ҫ����
            if (equipMultiList.isCheckboxSelected(i, 5))
            {
                //��á���Ҫ���豸��BsoID
                String id = equipMultiList.getCellText(i, 0);
                //��á���Ҫ���豸ֵ����
                QMEquipmentInfo relationEquipmentInfo = (QMEquipmentInfo)
                        equipmentMap.get(id);
                v.add(relationEquipmentInfo);
            } //end if
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getNecessaryRelationEquipment() end...return: " +
                               v);
        }
        return v;
    }


    /**
     * �������δѡ�񡰱�Ҫ�����豸
     * @return ѡ���ˡ���Ҫ�����豸�ļ���
     */
    public Vector getUnnecessaryRelationEquipment()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getUnnecessaryRelationEquipment() begin...");
        }
        Vector v = new Vector();

        for (int i = 0; i < equipmentVector.size(); i++)
        {
            //���ĳ�豸���ǡ���Ҫ����
            if (!equipMultiList.isCheckboxSelected(i, 5))
            {
                //��á���Ҫ���豸��BsoID
                String id = equipMultiList.getCellText(i, 0);
                //��á���Ҫ���豸ֵ����
                QMEquipmentInfo relationEquipmentInfo = (QMEquipmentInfo)
                        equipmentMap.get(id);
                v.add(relationEquipmentInfo);
            } //end if
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getUnnecessaryRelationEquipment() end...return: " +
                               v);
        }
        return v;
    }


    /**
     * ������Դ���񣬽��豸�빤װ�Ĺ�����ӱ��浽��װ��Դ��
     */
    private void save()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.EquipmentToolMaintainJDialog.save() begin...");
            //���ѡ���ˡ���Ҫ�����豸
        }
        Vector equipVector = getNecessaryRelationEquipment();
        QMEquipmentIfc equipmentInfo;
        for (int i = 0; i < equipVector.size(); i++)
        {
            //����豸����
            equipmentInfo = (QMEquipmentIfc) equipVector.
                            elementAt(i);
            try
            {
                CEquipment cequipment = new CEquipment(equipmentInfo);
                QMToolInfo toolInfo;
                //Begin CR1
                if(cequipment.getTools().size() > 0)
                {
                	cequipment.getTools().clear();
                }
                //End CR1
                //�����й�װ������뵱ǰ�豸������
                for (int j = 0; j < toolVector.size(); j++)
                {
                    toolInfo = (QMToolInfo) toolVector.elementAt(j);
                    cequipment.addTool(toolInfo, true);
                }
                //���浱ǰ�豸����
                Class[] paraclass =
                        {CEquipment.class,Boolean.TYPE};
                Object[] obj =
                        {cequipment,Boolean.FALSE};
                try
                {
                    TechnicsAction.useServiceMethod("ResourceService",
                            "saveEquipment", paraclass, obj);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
            }
            catch (ResourceException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        //���δѡ�񡰱�Ҫ�����豸
        Vector equipVector2 = getUnnecessaryRelationEquipment();
        for (int i = 0; i < equipVector2.size(); i++)
        {
            //����豸����
            equipmentInfo = (QMEquipmentIfc) equipVector2.
                            elementAt(i);
            try
            {
                CEquipment cequipment = new CEquipment(equipmentInfo);
                //�����й�װ������뵱ǰ�豸������
                QMToolInfo toolInfo;
                //Begin CR1
                if(cequipment.getTools().size() > 0)
                {
                	cequipment.getTools().clear();
                }
                //End CR1
                for (int j = 0; j < toolVector.size(); j++)
                {
                    toolInfo = (QMToolInfo) toolVector.elementAt(j);
                    cequipment.addTool(toolInfo, false);
                }
                //���浱ǰ�豸���� 
                Class[] paraclass =
                        {CEquipment.class,Boolean.TYPE};
                Object[] obj =
                        {cequipment,Boolean.FALSE};
                try
                {
                    TechnicsAction.useServiceMethod("ResourceService",
                            "saveEquipment", paraclass, obj);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
            }
            catch (ResourceException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.EquipmentToolMaintainJDialog.save() end...return is void");
        }
    }


    /**
     * ִ��ȷ������
     * @param e
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        save();
        setVisible(false);
        setCursor(Cursor.getDefaultCursor());
    }


    /**
     * ִ��ȡ������
     * @param e
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        setVisible(false);
    }


    /**
     * ���ý���Ϊ�ɱ༭
     */
    public void setEditMode()
    {
        equipMultiList.setColsEnabled(new int[]
                                      {5}
                                      , true);
        okJButton.setVisible(true);
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "CancelJButton", null));
    }


    /**
     * ���ý���鿴ģʽ
     */
    public void setViewMode()
    {
        equipMultiList.setColsEnabled(new int[]
                                      {5}
                                      , false);
        okJButton.setVisible(false);
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "QuitJButton", null));
    }


    /**
     * ����
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        EquipmentToolMaintainJDialog equipmentToolMaintainJFrame = new
                EquipmentToolMaintainJDialog(null);
        equipmentToolMaintainJFrame.setVisible(true);
    }


}

package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMFawTechnicsMasterInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.model.WorkableIfc;

public class TechnicsRelationJPanelForCT extends ParentJPanel{
    private ComponentMultiList multiList = new ComponentMultiList();
    private JPanel changeItemJPanel = new JPanel();
    private JButton addJButton = new JButton();
    private JButton deleteJButton = new JButton();
    private JButton upJButton = new JButton();
    private JButton downJButton = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**��ǣ��ϲ����չ�̽����½��Ĺ�������Ϣ*/
    private QMFawTechnicsInfo relatedTechnics;


    /**���ڻ��湤�տ�����*/
    private HashMap map = new HashMap();


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private String technicsType;
    /**������*/
    private JFrame parentJFrame;

    /**
     * ���캯��
     */
    public TechnicsRelationJPanelForCT(JFrame parentframe)
    {
        parentJFrame=parentframe;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout3);
        changeItemJPanel.setLayout(gridBagLayout1);
        addJButton.setMaximumSize(new Dimension(89, 23));
        addJButton.setMinimumSize(new Dimension(89, 23));
        addJButton.setPreferredSize(new Dimension(89, 23));

        addJButton.setMnemonic('A');
        addJButton.setText("���...");

        deleteJButton.setMaximumSize(new Dimension(89, 23));
        deleteJButton.setMinimumSize(new Dimension(89, 23));
        deleteJButton.setPreferredSize(new Dimension(89, 23));
        deleteJButton.setMnemonic('R');
        deleteJButton.setText("ɾ��(R)");
        deleteJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteJButton_actionPerformed(e);
            }
        });

        upJButton.setMaximumSize(new Dimension(89, 23));
        upJButton.setMinimumSize(new Dimension(89, 23));
        upJButton.setPreferredSize(new Dimension(89, 23));
        upJButton.setMnemonic('U');
        upJButton.setText("����");
        upJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                upJButton_actionPerformed(e);
            }
        });
        downJButton.setMaximumSize(new Dimension(89, 23));
        downJButton.setMinimumSize(new Dimension(89, 23));
        downJButton.setPreferredSize(new Dimension(89, 23));
        downJButton.setMnemonic('O');
        downJButton.setText("����");
        downJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                downJButton_actionPerformed(e);
            }
        });

        multiList.setHeadings(new String[]
                              {"���ձ��", "��������", "�汾"});
        //�����п�
        multiList.setRelColWidth(new int[]
                                 {1, 1, 1});
        multiList.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                multiList_itemStateChanged(e);
            }
        });
        multiList.setCellEditable(false);

        add(multiList, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(5, 0, 5, 0), 0, 0));
        changeItemJPanel.add(addJButton,
                             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));
        changeItemJPanel.add(deleteJButton,
                             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));

        changeItemJPanel.add(upJButton,
                             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));
        changeItemJPanel.add(downJButton,
                             new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(5, 5, 0, 5), 0, 0));
        add(changeItemJPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(5, 8, 0, 0), 0, 0));
        localize();
        addJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addJButton_actionPerformed(e);
            }
        });
    }


    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
        addJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "addJButton", null));
        //deleteJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        //         "deleteJButton", null));
        upJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "upJButton", null));
        downJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "downJButton", null));
        multiList.setHeadings(new String[]
                              {
                              QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null),
                              QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null),
                              QMMessage.getLocalizedMessage(RESOURCE, "version", null)});
    }


    /**
     * ���ñ������Ĺ��տ�
     * @param info �ϲ����չ�̽����½��Ĺ��տ�
     */
    public void setRelatedTechnics(QMFawTechnicsInfo info)
    {
    	
        relatedTechnics = info;
    }


    /**
     * ���ñ������Ĺ��տ��Ĺ�������
     * @param type �������Ĺ��տ��Ĺ�������
     */
    public void setTechnicsType(String type)
    {
        technicsType = type;
    }


    /**
     * ��ñ������Ĺ��տ�
     * @return �ϲ����չ�̽����½��Ĺ��տ�
     */
    public QMFawTechnicsInfo getRelatedTechnics()
    {
        return relatedTechnics;
    }


    /**
     * ͨ���������չ�����
     * �����б�����ӵĹ��տ������������ϲ����չ�̽����½��Ĺ��տ��Ĺ���������ͬ��������ʾ����
     * @param e ActionEvent
     */
    void addJButton_actionPerformed(ActionEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:addJButton_actionPerformed(e) begin...");
        }
        String SCHEMA = "C:QMFawTechnics; G:��������;A:technicsNumber;A:technicsName;A:technicsType;E:technicsType;F:technicsType," +
                        technicsType + ";";
        String title = "�������չ��";
        CappChooser cappQuery = new CappChooser("QMFawTechnics", title, this);
        //�����ѯ����
        CappSchema mySchema = null;
        try
        {
            mySchema = new CappSchema(SCHEMA);
        }
        catch (QMRemoteException ex)
        {
            String extitle = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "exception", null);
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
                                          extitle,
                                          JOptionPane.ERROR_MESSAGE);
            return;

        }

        cappQuery.setSchema(mySchema);
        cappQuery.notAcessInPersonalFolder();
        cappQuery.setLastIteration(true);
        cappQuery.addExttrPanel("QMFawTechnics", "technicsType");
        cappQuery.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent e)
            {
                qmQuery_queryEvent(e);
            }
        });

        //���TechnicsSearchJDialog�е�mutilist���е�˫������
        cappQuery.addMultiListActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                myList_actionPerformed(e);
            }
        }
        );
        cappQuery.setVisible(true);

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:addJButton_actionPerformed(e) end...return is void");
        }
    }

   // private TechnicsSearchJDialog d = null;


    /**
     * �������ռ����¼�����
     * @param e ���������¼�
     */
    public void qmQuery_queryEvent(CappQueryEvent e)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel:qmQuery_queryEvent(e) begin...");
        }

        if (e.getType().equals(CappQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(CappQuery.OkCMD))
            {
             //   d.setVisible(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CappQuery c = (CappQuery) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                actionPerformed(bvi);
                setCursor(Cursor.getDefaultCursor());
            }
            /* if (e.getCommand().equals(CappQuery.QuitCMD))
             {
                 d.setVisible(false);
             }*/
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:qmQuery_queryEvent(e) end...return is void");
        }
    }

    private void myList_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CappMultiList c = (CappMultiList) e.getSource();
        Object[] bvi = c.getSelectedObjects();
        actionPerformed(bvi);
        setCursor(Cursor.getDefaultCursor());
    }


    /**
     *�������еĹ��չ�̼����б��У��˷����ṩ��TechnicsSearchJDialog�����������¼�
     */
    private void actionPerformed(Object[] bvi)
    {
//        try
        {
            if (bvi != null)
            {
                //�Ѵӽ����ѡ�е�ҵ���������Ʒ�ṹ����
                for (int i = 0; i < bvi.length; i++)
                {
                    QMFawTechnicsInfo info = (QMFawTechnicsInfo) bvi[i];
//                    if (isTypeSame(info))
                    {
                        if (!isContain(info))
                        {
                            if (!CheckInOutCappTaskLogic.isCheckedOut((
                                    WorkableIfc) info))
                            {
                                technicsVector.addElement(info);
                            }
                            else
                            {
                                String title= QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                                Object[] para =
                                        {getIdentity(info)};

                                String message = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        CappLMRB.
                                        CANNOT_COLLECT_CHECKED_OUT_OBJECT, para);
                               JOptionPane.showMessageDialog(parentJFrame,message,title,JOptionPane.INFORMATION_MESSAGE);

                            }
                        }
                        else
                        {
                           String title= QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                            Object[] para =
                                    {getIdentity(info)};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    CappLMRB.ADD_TECHNICS_REPEAT, para);
                            JOptionPane.showMessageDialog(parentJFrame,message,title,JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    /*else
                    {
                        Object[] para =
                                {getIdentity(info)};
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.NOT_SAME_TECHNICE_TYPE, para);
                        addToMultiList(technicsVector);
                        throw new QMRemoteException(message);
                    }*/
                }

                addToMultiList(technicsVector);
            }
            else
            {
                setCursor(Cursor.getDefaultCursor());
                return;
            }
        }
        /*catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "notice", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.WARNING_MESSAGE);
        }*/
    }

    private Vector technicsVector = new Vector();


    /**
     * ��ѡ���Ĺ��տ�������ӵ��б��С�
     * ͨ���������չ����ӣ���ӹ�������ѡȡ�����ñ�������
     * @param v  ѡ���Ĺ��տ�ֵ���󼯺�
     */
    public void addToMultiList(Vector v)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel.addToMultiList() begin...");
        }
        String as[] = new String[v.size()];
        for (int i = 0; i < v.size(); i++)
        {
            //��ü����еĹ��տ�
            QMFawTechnicsInfo info = (QMFawTechnicsInfo) v.elementAt(i);

            //�б��������飺"���տ�ID","���տ����","���տ�����","�汾"
            as[i] = info.getTechnicsNumber() + ";"
                    + info.getTechnicsName() + ";"
                    + info.getVersionValue();
            //����ѡ����ӵ����տ����󻺴����
            map.put(info.getTechnicsNumber(), info);

        } //end for
        multiList.clear();
        //�ѹ��տ�����������б�
        multiList.setListItems(as);
        multiList.setSelectedRow(0);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.addToMultiList() end...return is void");
        }
    }


    /**
     * �ж���ѡ������ϲ����չ�̽����½��Ĺ��տ��Ĺ��������Ƿ�ƥ��
     * @param ifc ��ѡ����
     * @return �����������ƥ�䣬�򷵻�true�����򷵻�false������ʾ
     */
    private boolean isTypeSame(QMTechnicsIfc ifc)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel.isTypeSame() begin...");
        }
        if (technicsType == null)
        {
            /*String title = QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                     String message = QMMessage.getLocalizedMessage(
                RESOURCE,CappLMRB.NOT_TECHNICSTYPE_ENTERED,null);
             JOptionPane.showMessageDialog(getParentJFrame(),message,title,
                                          JOptionPane.INFORMATION_MESSAGE);
             */
            return false;
        }
        //��ѡ���յĹ�������
        String type1 = ifc.getTechnicsType().getCodeContent();

        if (type1.equals(technicsType))
        {
            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.view.TechnicsRelationJPanel.isTypeSame() end...return: true");
            }
            return true;
        }
        else
        {

            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.view.TechnicsRelationJPanel.isTypeSame() end...return: false");
            }
            return false;
        }
    }


    /**
     * ɾ���б��еĹ��տ�����
     * @param e ActionEvent
     */
    void deleteJButton_actionPerformed(ActionEvent e)
    {
        int selected_row = multiList.getSelectedRow();
        QMTechnicsInfo info = (QMTechnicsInfo) multiList.getSelectedObject();
        if (selected_row != -1)
        {
            //�����ѡ��Ĺ��տ�ID
            String id = multiList.getCellText(selected_row, 0);
            //����ѡ����б���ɾ��
            multiList.removeRow(selected_row);
            //����ѡ��ӹ��տ����󻺴����ɾ��
            map.remove(id);
            technicsVector.remove(selected_row);
            refreshButtonState();
        }
    }


    /**
     * �б����Ŀ�ı�����¼�����
     * @param e ItemEvent
     */
    void multiList_itemStateChanged(ItemEvent e)
    {
        refreshButtonState();
    }


    /**
     * ˢ�°�ť����ʾ״̬
     * <p>���б���ѡ��һ�����տ�ʱ����ȥ��ť��Ч������ʧЧ��</p>
     */
    private void refreshButtonState()
    {
        int row_number = multiList.getSelectedRow();
        if (row_number != -1)
        {
            deleteJButton.setEnabled(true);
        }
        else
        {
            deleteJButton.setEnabled(false);
        }
    }


    /**
     * ����ѡ�������ƶ�һ��
     * @param e ActionEvent
     */
    void upJButton_actionPerformed(ActionEvent e)
    {
        //����б�����ѡ�е������������(��Ϊ�����ѡ)
        int[] selected = multiList.getSelectedRows();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] > 0)
            {
                //����ѡ������һ����λ
                multiList.getTableModel().moveRow(selected[i], selected[i],
                                                  selected[i] - 1);
                /**�����ѡ��λ������һ����λ*/
                multiList.setSelectedRow(selected[i] - 1);
            }
        }
    }


    /**
     * ����ѡ�������ƶ�һ��
     * @param e ActionEvent
     */
    void downJButton_actionPerformed(ActionEvent e)
    {
        //�����������б�����ѡ�е������������(��Ϊ�����ѡ)
        int[] selected = multiList.getSelectedRows();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] + 1 <= multiList.totalObjects() - 1)
            {
                //����ѡ������һ����λ
                multiList.getTableModel().moveRow(selected[i], selected[i],
                                                  selected[i] + 1);
                /**�����ѡ��λ������һ����λ*/
                multiList.setSelectedRow(selected[i] + 1);
            }
        }
    }


    /**
     * ����б������մ��ڵĹ��տ�����
     * @return ���տ����󼯺�
     */
    public Collection commitAddedTechnics()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnics() begin...");
        }
        Collection c = new ArrayList();
        int size = multiList.getNumberOfRows();
        for (int i = 0; i < size; i++)
        {
            //���ĳ��Ĺ��տ�ID
            String id = multiList.getCellText(i, 0);
            QMFawTechnicsInfo info = (QMFawTechnicsInfo) map.get(id);
            c.add(info);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnics() end...return: " +
                               c);
        }
        return c;
    }


    /**
     * ����б������մ��ڵĹ��տ�����
     * @return ���տ������BsoID�ļ���
     */
    public Vector commitAddedTechnicsBsoID()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnicsBsoID() begin...");
        }
        Vector v = new Vector();
        Set set = map.keySet();
        Iterator keys = set.iterator();
        while (keys.hasNext())
        {
            QMTechnicsInfo tech = (QMTechnicsInfo) map.get(keys.next());
            v.add(tech.getBsoID());
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnicsBsoID() end...return: " +
                               v);
        }
        return v;
    }

    private boolean isContain(QMTechnicsInfo info)
    {
        boolean flag = false;
        for (int i = 0; i < technicsVector.size(); i++)
        {
            QMTechnicsInfo thisInfo = (QMTechnicsInfo) technicsVector.elementAt(
                    i);
            String a = thisInfo.getBsoID();
            String b = info.getBsoID();
            if (a.equals(b))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * ������������Ϣ����ù��տ������а汾�е�����С�汾
     * @param masterInfo ��������Ϣ
     * @return ���տ������а汾�е�����С�汾
     */
    private QMFawTechnicsInfo getLastedVersion(QMFawTechnicsMasterInfo
                                               masterInfo)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel:getLastedIterations() begin...");
        }
        QMFawTechnicsInfo technicsInfo = null;
        //���÷��񷽷�����ù��տ�������С�汾��������ͬ��֦��
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        Class[] paraClass =
                {MasteredIfc.class};
        Object[] objs =
                {masterInfo};
        Collection collection = null;
        try
        {
            collection = (Collection) TechnicsAction.useServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            if(verbose)
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //��ù��տ�������С�汾
            technicsInfo = (QMFawTechnicsInfo) iterator.next();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:getLastedIterations() end...return " +
                               technicsInfo);
        }
        return technicsInfo;
    }


    /**
     * ��ָ���Ĺ��տ���ӵ��б���
     * @param info ָ���Ĺ��տ�
     */
    public void addTechReguToTable(QMTechnicsInfo info)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel:addTechReguToTable() begin...");
        }
        try
        {
            if (isTypeSame(info))
            {
                if (!isContain(info))
                {
                    if (!CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)
                            info))
                    {
                        technicsVector.addElement(info);
                    }
                    else
                    {
                        Object[] para =
                                {getIdentity(info)};
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.CANNOT_COLLECT_CHECKED_OUT_OBJECT,
                                para);
                        throw new QMRemoteException(message);
                    }
                }
                else
                {
                    Object[] para =
                            {getIdentity(info)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.ADD_TECHNICS_REPEAT, para);
                    throw new QMRemoteException(message);
                }
            }
            else
            {
                Object[] para =
                        {getIdentity(info)};
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NOT_SAME_TECHNICE_TYPE, para);
                throw new QMRemoteException(message);
            }
            addToMultiList(technicsVector);
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          e.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:addTechReguToTable() end...return is void");
        }
    }


    /**
     * ���������Ϣ
     */
    public void clear()
    {
        multiList.clear();
        map.clear();
        relatedTechnics = null;
        technicsVector.clear();
    }




}

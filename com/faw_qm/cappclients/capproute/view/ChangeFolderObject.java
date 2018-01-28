/** ����ChangeFolderObject.java	1.0  2003/01/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.faw_qm.clients.awt.HorizontalLine;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.view.EffClientViewIfc;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;


/**
 * <p>Title: �������ϼ������档</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 *   //CC by leixiao 2009-9-21 ԭ�򣺽����������·��,����"ת��" 
 * @version 1.0
 */

public class ChangeFolderObject extends JFrame implements EffClientViewIfc
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    private JPanel jPanel4 = new JPanel();

    private JLabel attributeJL = new JLabel();
    private JLabel pathJL = new JLabel();
    private JLabel statusJL = new JLabel();
    private JButton okJB = new JButton();
    private JButton cancelJB = new JButton();
    private EffAction controller;
    private FolderPanel folderPanel1 = new FolderPanel();
    private RevisionControlledIfc revision;
    private JLabel objectTypeJL = new JLabel();
    private JLabel originLocationJL = new JLabel();
    private JLabel nullJL = new JLabel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private static String RESOURCE =
            "com.faw_qm.cappclients.capproute.util.CappRouteRB";
    private HorizontalLine line = new HorizontalLine();
    String text = "";

    /**
     * ���캯����
     * @param c EffAction
     * @param object RevisionControlledIfc
     */
    public ChangeFolderObject(EffAction c, RevisionControlledIfc object)
    {
        try
        {
            revision = object;
            controller = c;
            jbInit();
            initView(null);
            text = QMMessage.getLocalizedMessage(RESOURCE, "moveobject", null);
            objectTypeJL.setText(text);
            String s2 = QMMessage.getLocalizedMessage(RESOURCE, "olderfolder", null);
            originLocationJL.setText(s2);
            setShow();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * ��ʾҵ�����
     */
    public void setShow()
    {
        String attribute_name = ((TechnicsRouteListIfc) revision).getRouteListNumber();
        String attribute_version = ((TechnicsRouteListIfc) revision).getVersionValue();
        String attribute = attribute_name  + " " + attribute_version ;
        objectTypeJL.setText(text);
        attributeJL.setText(attribute);
        pathJL.setText(((TechnicsRouteListIfc) revision).getLocation());

    }


    /**��ʾ����*/
    public void showView()
    {
        setVisible(true);
    }


    /**���ؽ���*/
    public void hideView()
    {
        setVisible(false);
    }


    /**�������*/
    public void activateView()
    {
        setEnabled(true);
        this.okJB.setEnabled(true);
        this.cancelJB.setEnabled(true);
        setVisible(true);
    }


    /**�䶳����*/
    public void passivateView()
    {
        setEnabled(false);
        this.okJB.setEnabled(false);
        this.cancelJB.setEnabled(false);
    }


    /**�رս���*/
    public void closeView()
    {
        dispose();
    }


    /**
     * ���ý���������
     * @return �������Ԫ�صļ��ϡ�
     */
    public Collection getOutput()
    {
        Vector v = new Vector();
        v.add(folderPanel1.getFolderLocation());
        return v;
    }


    /**
     * ���ý���������
     * @return ѡ����ļ��С�
     */
    public SubFolderInfo getOutputText()
    {
        SubFolderInfo folder = null;
        try
        {
            folder = folderPanel1.getSetdFolder();
        }
        catch (QMException e)
        {
            String message = e.getMessage();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
            JOptionPane.showMessageDialog(this, message, title,
                                          JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }
        return folder;
    }


    /**
     * ���½���(ģ�ͷ����仯ʱ֪ͨ���棬�ú���������)��
     * @param observable ���۲��߶���
     * @param obj ���۲�������Ĳ�����
     */
    public void update(Observable observable, Object obj)
    {
        Vector c = (Vector) obj;
        Iterator i = c.iterator();
    }


    /**
     * �����ʼ��������
     * @param params ��������ļ��ϡ�
     */
    public void initView(Collection params)
    {
        //���ð�ť�ļ�����
        okJB.addActionListener(controller);
        cancelJB.addActionListener(controller);
    }

    private void jbInit()
            throws Exception
    {
        this.setResizable(false);
        this.setSize(400, 300);
        this.getContentPane().setLayout(gridBagLayout2);
        String t = QMMessage.getLocalizedMessage(RESOURCE, "changefolder", null);
        this.setTitle(t);
        String iconImage = "routeList";
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
        attributeJL.setFont(new java.awt.Font("Dialog", 0, 12));
        attributeJL.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "attributeJLabel", null));
        attributeJL.addMouseListener(new ChangeFolderObject_attributeJL_mouseAdapter(this));
        pathJL.setFont(new java.awt.Font("Dialog", 0, 12));
        pathJL.setText(QMMessage.getLocalizedMessage(RESOURCE, "pathJLabel", null));
        pathJL.addMouseListener(new ChangeFolderObject_pathJL_mouseAdapter(this));
        statusJL.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJL.setText("   ");
        jPanel4.setLayout(gridBagLayout1);
        okJB.setFont(new java.awt.Font("Dialog", 0, 12));
        okJB.setMaximumSize(new Dimension(75, 23));
        okJB.setMinimumSize(new Dimension(75, 23));
        okJB.setPreferredSize(new Dimension(75, 23));
        okJB.setActionCommand("OK");
        okJB.setMnemonic('Y');
        String ok = QMMessage.getLocalizedMessage(RESOURCE, "OkJButton", null);
        okJB.setText(ok);
        cancelJB.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJB.setMaximumSize(new Dimension(75, 23));
        cancelJB.setMinimumSize(new Dimension(75, 23));
        cancelJB.setPreferredSize(new Dimension(75, 23));
        cancelJB.setActionCommand("CANCEL");
        cancelJB.setMnemonic('C');

        String cancel = QMMessage.getLocalizedMessage(RESOURCE, "CancelJButton", null);
        cancelJB.setText(cancel);
        folderPanel1.setLabelSize(70, 18);
        String goal = QMMessage.getLocalizedMessage(RESOURCE, "goal", null);
        folderPanel1.setFolderJLabelName(goal);
        folderPanel1.setIsPersonalFolder(false);
        folderPanel1.setIsPublicFolders(true);
        folderPanel1.addMouseListener(new ChangeFolderObject_folderPanel1_mouseAdapter(this));
        folderPanel1.setPermission("modify");
        objectTypeJL.setFont(new java.awt.Font("Dialog", 0, 12));
        objectTypeJL.setHorizontalAlignment(SwingConstants.RIGHT);
        objectTypeJL.setHorizontalTextPosition(SwingConstants.RIGHT);
        objectTypeJL.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "objectType", null));
        originLocationJL.setFont(new java.awt.Font("Dialog", 0, 12));
        originLocationJL.setHorizontalAlignment(SwingConstants.RIGHT);
        originLocationJL.setHorizontalTextPosition(SwingConstants.RIGHT);
        String yfolder = QMMessage.getLocalizedMessage(RESOURCE, "olderfolder", null);
        originLocationJL.setText(yfolder);
        nullJL.setText("   ");
        line.setBevelStyle(0);
        this.getContentPane().add(attributeJL,
                                  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.6
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 8, 0, 0), 0, 0));

        this.getContentPane().add(line,
                                  new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(10, 10, 20, 13), 0, 0));

        this.getContentPane().add(jPanel4,
                                  new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(8, 20, 0, 20), 0, 0));
        jPanel4.add(okJB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(cancelJB, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel4.add(nullJL, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, -8, 0, 8), 0, 0));
        this.getContentPane().add(objectTypeJL,
                                  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.6
                , GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(15, 33, 0, 0), 0, 0));
        this.getContentPane().add(originLocationJL,
                                  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.6
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(3, 33, 5, 0), 0, 0));
        this.getContentPane().add(statusJL,
                                  new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0
                , GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(folderPanel1,
                                  new GridBagConstraints(0, 2, 2, 1, 1.0, 0.6
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 10, 0, 20), 0, 0));
        this.getContentPane().add(pathJL,
                                  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.6
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(3, 8, 5, 0), 0, 0));
    }

    void attributeJL_mouseEntered(MouseEvent e)
    {
        statusJL.setText(attributeJL.getText());
    }

    void attributeJL_mouseExited(MouseEvent e)
    {
        statusJL.setText("  ");
    }

    void pathJL_mouseEntered(MouseEvent e)
    {
        statusJL.setText(QMMessage.getLocalizedMessage(RESOURCE, "olderfolder", null) +
                         ":" + pathJL.getText());
    }

    void pathJL_mouseExited(MouseEvent e)
    {
        statusJL.setText("  ");
    }

    void folderPanel1_mouseEntered(MouseEvent e)
    {
        statusJL.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "selectlocation", null));
    }

    void folderPanel1_mouseExited(MouseEvent e)
    {
        statusJL.setText("  ");
    }

}


class ChangeFolderObject_attributeJL_mouseAdapter extends java.awt.event.MouseAdapter
{
    ChangeFolderObject adaptee;

    ChangeFolderObject_attributeJL_mouseAdapter(ChangeFolderObject adaptee)
    {
        this.adaptee = adaptee;
    }

    public void mouseEntered(MouseEvent e)
    {
        adaptee.attributeJL_mouseEntered(e);
    }

    public void mouseExited(MouseEvent e)
    {
        adaptee.attributeJL_mouseExited(e);
    }
}


class ChangeFolderObject_pathJL_mouseAdapter extends java.awt.event.MouseAdapter
{
    ChangeFolderObject adaptee;

    ChangeFolderObject_pathJL_mouseAdapter(ChangeFolderObject adaptee)
    {
        this.adaptee = adaptee;
    }

    public void mouseEntered(MouseEvent e)
    {
        adaptee.pathJL_mouseEntered(e);
    }

    public void mouseExited(MouseEvent e)
    {
        adaptee.pathJL_mouseExited(e);
    }
}


class ChangeFolderObject_folderPanel1_mouseAdapter extends java.awt.event.MouseAdapter
{
    ChangeFolderObject adaptee;

    ChangeFolderObject_folderPanel1_mouseAdapter(ChangeFolderObject adaptee)
    {
        this.adaptee = adaptee;
    }

    public void mouseEntered(MouseEvent e)
    {
        adaptee.folderPanel1_mouseEntered(e);
    }

    public void mouseExited(MouseEvent e)
    {
        adaptee.folderPanel1_mouseExited(e);
    }
}

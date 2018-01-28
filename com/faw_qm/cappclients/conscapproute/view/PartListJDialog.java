/**
 * SS1 ����qmpart������master����Ϊ�㲿������Ҫ����qmpartifc liunan 2013-12-24
 */
 
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.model.QMPartInfo;

/**
 * <p>Title:�Ӳ��ñ�����������������ʾ�б�</p> <p>Description: </p> <p>Copyright: Copyright (c) 2007</p> <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
public class PartListJDialog extends JDialog
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /** �㲿���б� */
    private ComponentMultiList qMMultiList = new ComponentMultiList();
    private JButton selectAllJButton = new JButton();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();
    /** ��Դ�ļ�·�� */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private Vector partlist = new Vector();

    /**
     * ���캯��
     * @param frame ������
     */
    public PartListJDialog(JDialog dialog,Vector v)
    {
          super(dialog);
        try
        {
            partlist = v;
            jbInit();
            showPart(partlist);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void jbInit()
    {
        this.setSize(500, 400);
        this.setViewLocation();
        this.setModal(true);
        this.getContentPane().setLayout(new GridBagLayout());
        panel1.setLayout(new GridBagLayout());
        panel2.setLayout(new GridBagLayout());
        this.setTitle("��ʾ�㲿����");
        selectAllJButton.setMaximumSize(new Dimension(75, 23));
        selectAllJButton.setMinimumSize(new Dimension(75, 23));
        selectAllJButton.setPreferredSize(new Dimension(75, 23));
        selectAllJButton.setToolTipText("Select All");
        selectAllJButton.setText("ȫѡ(A)");
        selectAllJButton.setMnemonic('A');
        selectAllJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                selectAllJButton_actionPerformed(e);
            }
        });
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        this.getContentPane().add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 14, 10, 14), 0, 0));
        this.getContentPane().add(panel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 14, 10, 14), 0, 0));
        this.getContentPane().add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 14, 10, 14), 0, 0));
        panel1.add(cancelJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel2.add(selectAllJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(okJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        qMMultiList.setHeadings(new String[]{"id", "�㲿�����", "�㲿������"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 1});
        qMMultiList.setCellEditable(false);
        qMMultiList.setMultipleMode(true);
    }

    /**
     * ִ��"ȫѡ"����,ѡ���б��е������㲿��
     * @param e ActionEvent
     */
    void selectAllJButton_actionPerformed(ActionEvent e)
    {
        if(qMMultiList.getNumberOfRows() > 0)
            qMMultiList.selectAll();
        else
        {
            JOptionPane.showMessageDialog(this, QMMessage.getLocalizedMessage(RESOURCE, CappRouteRB.NOT_HAVE_PART, null), QMMessage.getLocalizedMessage(RESOURCE, "notice", null),
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * ִ��"ȷ��"����
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        int index []= qMMultiList.getSelectedRows();
        Vector partVec=new Vector();
        for(int i=0;i<index.length;i++)
        {
            String bsoID =qMMultiList.getCellText(index[i],0 );
            //CCBegin SS1
            //QMPartMasterInfo info = (QMPartMasterInfo)RouteClientUtil.refresh(bsoID);
            QMPartInfo info = (QMPartInfo)RouteClientUtil.refresh(bsoID);
            //CCEnd SS1
            partVec.add(info);
        }
        RefreshService.getRefreshService().dispatchRefresh("addSelectedParts", 0, partVec);
        this.setVisible(false);
    }

    /**
     * ִ��"ȡ��"����
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * �������ʾ���б���
     * @param v
     */
    private void showPart(Vector partlist)
    {
        for(int i = 0;i < partlist.size();i++)
        {
            //CCBegin SS1
            //QMPartMasterInfo part = (QMPartMasterInfo)partlist.elementAt(i);
            QMPartInfo part = (QMPartInfo)partlist.elementAt(i);
            //CCEnd SS1
            Image image = this.getStandardIcon(part);
            qMMultiList.addTextCell(i, 0, part.getBsoID());
            qMMultiList.addCell(i, 1, part.getPartNumber(), image);
            qMMultiList.addTextCell(i, 2, part.getPartName());
        }
    }

    /**
     * ���ͼ��
     * @param basevalueinfo
     * @return
     */
    public Image getStandardIcon(BaseValueIfc basevalueinfo)
    {
        Image image = null;
        if(basevalueinfo != null)
        {
            try
            {
                image = QM.getStandardIcon(basevalueinfo.getBsoName());
            }catch(Exception _ex)
            {
                image = null;
            }
        }
        return image;
    }

}

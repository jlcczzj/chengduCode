/**
 * CR1 20120401 ���� ԭ��μ�TD6010
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.faw_qm.cappclients.capp.util.CappClientUtil;
import com.faw_qm.cappclients.conscapproute.util.ParentWrapData;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;

/**
 * <p>Title: </p> <p>�鿴�����Ի���: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: </p>
 * @author not attributable
 * @version 1.0 20120106 �촺Ӣ add
 */
public class ViewParentJDialog extends JDialog implements ActionListener
{
    private QMPartMasterIfc part;
    //private String listID;
    JPanel panel1 = new JPanel();
    JLabel tipLabel = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    QMMultiList multiList = new QMMultiList();
    JButton quitButton = new JButton();
    GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JButton okJButton = new JButton();
    private JPanel buttonJPanel = new JPanel();
    private JLabel statusLabel = new JLabel();
    //�ж��Ƿ���ȷ����ť
    public static boolean isSave = false;
    //ѡ������
    private QMPartIfc theSelectedParentPart;
    //���·�ߵ�ģʽ
    private static final String routeMode = RemoteProperty.getProperty("routeManagerMode", "partRelative");
    private JFrame parentFrame;
    //��Ÿ����Ļ���
    private HashMap parentMap = new HashMap();
    //��Ʒ���
    private String productNum;
    //CR1 begin
    private int mode;

    public ViewParentJDialog(JFrame frame, String productNum, String title, boolean modal,int mode)
    {
        super(frame, title, modal);
        parentFrame = frame;
        this.mode=mode;
        //CR1 end
        this.productNum = productNum;
        try
        {
            jbInit();
            pack();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean setPartMaster(QMPartMasterIfc part)
    {
        boolean flag = false;
        this.part = part;
        //this.listID = listID;
        this.setTitle("�㲿��<" + part.getPartNumber() + "(" + part.getPartName() + ")>���ϼ�������·��");
        this.tipLabel.setText("ʹ���㲿��<" + part.getPartNumber() + "(" + part.getPartName() + ")>�Ĳ���");
        flag = initMultilist();
        return flag;
    }

    //��Ӹ�����Ϣ
    private boolean initMultilist()
    {
        Class[] params = {QMPartMasterIfc.class};
        Object[] values = {part};
        Vector result = null;
        //���÷�����Ҹ���
        try
        {
            result = (Vector)RequestHelper.request("consTechnicsRouteService", "findParentsAndRoutes", params, values);
        }catch(Exception ex)
        {
            //����쳣��Ϣ��
            //JOptionPane.showMessageDialog(this, ex.getClientMessage(), "����", JOptionPane.ERROR_MESSAGE);
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showInformDialog(parentFrame, message);
            return false;
        }
        if(result == null || result.size() == 0)
        {
            DialogFactory.showInformDialog(parentFrame, "�����û�и���");
            setCursor(Cursor.getDefaultCursor());

            return false;
        }
        //��������Ϣ���õ��б���
        if(result != null)
        {
            int size = result.size();
            for(int i = 0;i < size;i++)
            {
                int row = this.multiList.getNumberOfRows();
                Object[] objs = (Object[])result.elementAt(i);
                QMPartInfo parent = (QMPartInfo)objs[0];
                PartUsageLinkIfc link = (PartUsageLinkIfc)objs[1];
                TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)objs[2];
                String branch = (String)objs[3];
                multiList.addTextCell(row, 0, parent.getBsoID());
                multiList.addTextCell(row, 1, "��1");
                multiList.addTextCell(row, 2, parent.getPartNumber());
                multiList.addTextCell(row, 3, parent.getPartName());
                multiList.addTextCell(row, 4, parent.getVersionValue());
                multiList.addTextCell(row, 5, new Float(link.getQuantity()).toString());

                if(routelist != null)
                    multiList.addTextCell(row, 6, routelist.getRouteListNumber() + "(" + routelist.getRouteListName() + ")" + routelist.getVersionValue());
                else
                    multiList.addTextCell(row, 6, "");
                if(branch != null)
                    multiList.addTextCell(row, 7, branch);
                else
                    multiList.addTextCell(row, 7, "");
                //�״���Ӹ���ʱ��Ĭ�϶�δչ����
                multiList.addTextCell(row, 8, "N");
            }
        }
        return true;
    }

    private void jbInit()
    {
        panel1.setLayout(gridBagLayout1);
        tipLabel.setText("jLabel1");
        tipLabel.setFont(new Font("Dialog", 0, 14));
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setText("��ӭ����鿴װ��λ�ý���");
        quitButton.setMaximumSize(new Dimension(75, 23));
        quitButton.setMinimumSize(new Dimension(75, 23));
        quitButton.setPreferredSize(new Dimension(75, 23));
        quitButton.setToolTipText("quit(Q)");
        quitButton.setMnemonic('Q');
        quitButton.setText("�˳�(Q)");
        quitButton.addActionListener(new ViewParentJDialog_quiqButton_actionAdapter(this));
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
        if(routeMode.equals("parentRelative") || routeMode.equals("productAndparentRelative"))
            buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        //CR1 begin
        //�鿴ģʽ��ȷ����ťΪ���ɱ༭
        if(mode==2)
       {
           okJButton.setEnabled(false);
       }
        //CR1 end
        buttonJPanel.add(quitButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        getContentPane().add(panel1);
        panel1.add(tipLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 8, 4, 8), 366, 0));
        panel1.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 193, 71));
        panel1.add(buttonJPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 16, 0));
        panel1.add(statusLabel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(multiList, null);
        //���������������ж��Ƿ�Ҫչ���ڵ�
        multiList.setHeadings(new String[]{"id", "����", "���", "����", "�汾", "����", "·�߱�", "��Ч·��", "չ��"});
        multiList.setRelColWidth(new int[]{0, 2, 2, 2, 1, 1, 4, 5, 0});
        multiList.setCellEditable(false);
        multiList.setMultipleMode(false);
        if(!routeMode.equals("parentRelative"))
            multiList.setEnabled(false);
        multiList.addActionListener(this);
        multiList.setAllowSorting(false);
        //multiList.setColumnAlignment(4, multiList.CENTER);
    }

    void quiqButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getDefaultCursor());
        this.dispose();
       
    }

    /**
     * ��Ӹ���
     * @param e java.awt.event.ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        try
        {
            confirmOperation();
        }catch(Exception ex)
        {
            setCursor(Cursor.getDefaultCursor());
            //String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            //JOptionPane.showMessageDialog(this, ex.getClientMessage(), title, JOptionPane.INFORMATION_MESSAGE);
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showInformDialog(this, message);//xucy 20111213
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * ���ȷ����ťִ�еķ���
     */
    private void confirmOperation()
    {
        //���ѡ�����
        int index = multiList.getSelectedRow();
        if(index != -1)
        {
            String bsoid = multiList.getCellText(index, 0);
            String level = multiList.getCellText(index, 1);
            if("��1".equals(level))
            {
                QMPartIfc parent = (QMPartIfc)RouteClientUtil.refresh(bsoid);
                setSelectedParentPart(parent);
            }else
            {
                DialogFactory.showInformDialog(parentFrame, "��ѡ�������ֱ�Ӹ������������");
                return;
            }
        }else
        {
            DialogFactory.showInformDialog(parentFrame, "��ѡ�������ĸ���");
            return;
        }
        setIsSave(true);
        this.setVisible(false);
    }

    public boolean getIsSave()
    {
        return isSave;
    }

    public void setIsSave(boolean arg)
    {
        isSave = arg;
    }

    /**
     * ���ѡ��ĸ���
     * @return
     */
    public QMPartIfc getSelectedParentPart()
    {
        return theSelectedParentPart;
    }

    /**
     * ����ѡ��ĸ���
     */
    public void setSelectedParentPart(QMPartIfc arg)
    {
        theSelectedParentPart = arg;
    }

    /**
     * ���ݲ㼶��"��"
     * @param level
     * @return
     */
    private String constructLine(int level)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < level;i++)
        {
            sb.append("��");
        }
        return sb.toString();
    }

    /**
     * ʵ������������
     */
    public void actionPerformed(ActionEvent e)
    {
        String name = e.getActionCommand();
        //��������˫�� չ�����߹ر����������Ϣ
        if(name.startsWith("RowSelected:"))
        {
            int index = multiList.getSelectedRow();
            if(index != -1)
            {
                String partID = this.multiList.getCellText(index, 0);
                String aa = multiList.getCellText(index, 1);
                if(aa.indexOf("��") >= 0)
                {
                    aa = aa.substring(aa.lastIndexOf("��") + 1);
                }
                String partNum = multiList.getCellText(index, 2);
                String expand = multiList.getCellText(index, 8);
                QMPartIfc part = (QMPartIfc)RouteClientUtil.refresh(partID);
                //statusLabel.setForeground(Color.black);
                //���ѡ�����δչ������ִ��չ������
                if("N".equals(expand))
                {
                    //չ������ʱ���洢��ѡ��֮�µ�����
                    Vector dataVec = new Vector();
                    int level = new Integer(aa).intValue() + 1;
                    //���·�ߺͲ�Ʒ�йػ���·�����Ʒ�ͽṹ�йأ�����ѡ��չ���Ľڵ�Ϊ��Ʒ�ڵ㣬���ټ����Ҹ�����Ϣ
                    if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
                    {
                        if(part.getPartNumber().equals(productNum))
                        {
                            statusLabel.setText("��ѡ�㲿��Ϊ��Ʒ����չ����");
                            return;
                        }
                    }
                    Class[] params = {QMPartMasterIfc.class};
                    Object[] values = {part.getMaster()};
                    Vector parentResult = null;
                    try
                    {
                        if(parentMap.get(partNum) != null)
                        {
                            parentResult = (Vector)parentMap.get(partNum);
                        }else
                        {
                            parentResult = (Vector)RequestHelper.request("consTechnicsRouteService", "findParentsAndRoutes", params, values);
                        }
                        statusLabel.setText("��ǰչ���ĸ���Ϊ�� " + part.getIdentity());
                    }catch(Exception ex)
                    {
                        //����쳣��Ϣ��
                        //JOptionPane.showMessageDialog(this, ex.getClientMessage(), "����", JOptionPane.ERROR_MESSAGE);
                        String message = QMExceptionHandler.handle(ex);
                        DialogFactory.showInformDialog(parentFrame, message);
                        return;
                    }
                    if(parentResult == null || parentResult.size() == 0)
                    {
                        //DialogFactory.showInformDialog(parentFrame, "�����û�и���");
                        statusLabel.setText("���" + part.getIdentity() + "û�и���");
                        //statusLabel.setForeground(Color.red);
                        setCursor(Cursor.getDefaultCursor());
                        return;
                    }
                    if(parentResult != null)
                    {
                        //��չ�����ڵ����Ϣ���棬��һ����չ���Ǵӻ�����ȡ����
                        parentMap.put(partNum, parentResult);
                        int rowCount = multiList.getNumberOfRows();
                        for(int i = rowCount - 1;i > index;i--)
                        {
                            ParentWrapData parentData = getParentWrapData(i);
                            dataVec.add(parentData);
                            //��ѡ����֮��������ȴӱ�����Ƴ�
                            multiList.removeRow(i);
                        }
                        int size = parentResult.size();
                        //final List listA = new ArrayList();
                        for(int k = 0;k < size;k++)
                        {
                            int row = index + k + 1;
                            Object[] objs = (Object[])parentResult.elementAt(k);
                            QMPartInfo parent = (QMPartInfo)objs[0];
                            PartUsageLinkIfc link = (PartUsageLinkIfc)objs[1];
                            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)objs[2];
                            String branch = (String)objs[3];
                            multiList.addTextCell(row, 0, parent.getBsoID());
                            multiList.addTextCell(row, 1, constructLine(level) + level);
                            multiList.addTextCell(row, 2, parent.getPartNumber());
                            multiList.addTextCell(row, 3, parent.getPartName());
                            multiList.addTextCell(row, 4, parent.getVersionValue());
                            multiList.addTextCell(row, 5, new Float(link.getQuantity()).toString());

                            if(routelist != null)
                                multiList.addTextCell(row, 6, routelist.getRouteListNumber() + "(" + routelist.getRouteListName() + ")" + routelist.getVersionValue());
                            else
                                multiList.addTextCell(row, 6, "");
                            if(branch != null)
                                multiList.addTextCell(row, 7, branch);
                            else
                                multiList.addTextCell(row, 7, "");
                            multiList.addTextCell(row, 8, "N");
                        }
                        if(dataVec != null)
                        {
                            int size1 = dataVec.size();
                            int row1 = this.multiList.getNumberOfRows();
                            for(int m = size1 - 1;m >= 0;m--)
                            {
                                int row = row1 + size1 - 1 - m;
                                ParentWrapData parentData = (ParentWrapData)dataVec.elementAt(m);
                                multiList.addTextCell(row, 0, parentData.getParentID());
                                multiList.addTextCell(row, 1, String.valueOf(parentData.getLevel()));

                                multiList.addTextCell(row, 2, parentData.getParentNum());
                                multiList.addTextCell(row, 3, parentData.getParentName());
                                multiList.addTextCell(row, 4, parentData.getVersionValue());
                                multiList.addTextCell(row, 5, new Float(parentData.getCount()).toString());
                                multiList.addTextCell(row, 6, parentData.getRouteListNum());
                                multiList.addTextCell(row, 7, parentData.getRouteStr());
                                multiList.addTextCell(row, 8, parentData.getExpand());
                            }
                        }
                        multiList.addTextCell(index, 8, "Y");
                    }
                }
                //ִ�йرղ���
                else if("Y".equals(expand))
                {
                    int rowCount = multiList.getNumberOfRows();
                    String currLevel = multiList.getCellText(index, 1);
                    if(currLevel.indexOf("��") >= 0)
                    {
                        currLevel = currLevel.substring(currLevel.lastIndexOf("��") + 1);
                    }
                    //��������������
                    if(index == rowCount)
                        return;
                    String currNextLevel = multiList.getCellText(index + 1, 1);
                    if(currNextLevel.indexOf("��") >= 0)
                    {
                        currNextLevel = currNextLevel.substring(currNextLevel.lastIndexOf("��") + 1);
                    }
                    //���ѡ���к�����һ�У��㼶��ͬ������
                    if(Integer.parseInt(currNextLevel) == Integer.parseInt(currLevel))
                    {
                        return;
                    }

                    for(int i = rowCount - 1;i > index;i--)
                    {
                        ParentWrapData parentData = getParentWrapData(i);
                        String proRowLevel = parentData.getLevel();
                        if(proRowLevel.indexOf("��") >= 0)
                        {
                            proRowLevel = proRowLevel.substring(proRowLevel.lastIndexOf("��") + 1);
                        }
                        //���Ҫ������еĲ㼶����ѡ���еĲ㼶������ӱ�����Ƴ�
                        if(Integer.parseInt(proRowLevel) > Integer.parseInt(currLevel))
                            //��ѡ����֮��������ȴӱ�����Ƴ�
                            multiList.removeRow(i);
                    }
                    multiList.addTextCell(index, 8, "N");
                    statusLabel.setText("��ǰ�ϲ��ĸ���Ϊ�� " + part.getIdentity());
                }
            }
        }
    }

    /**
     * ��÷�װ�ĸ�������
     * @param row
     * @return
     */
    private ParentWrapData getParentWrapData(int row)
    {
        ParentWrapData parentData = new ParentWrapData();
        parentData.setParentID(multiList.getCellText(row, 0));
        parentData.setLevel(multiList.getCellText(row, 1));
        parentData.setParentNum(multiList.getCellText(row, 2));
        parentData.setParentName(multiList.getCellText(row, 3));
        parentData.setVersionValue(multiList.getCellText(row, 4));
        String num = multiList.getCellText(row, 5);
        float count = Float.valueOf(num).floatValue();
        parentData.setCount(count);
        parentData.setRouteListNum(multiList.getCellText(row, 6));
        parentData.setRouteStr(multiList.getCellText(row, 7));
        parentData.setExpand(multiList.getCellText(row, 8));
        return parentData;
    }

}

class ViewParentJDialog_quiqButton_actionAdapter implements java.awt.event.ActionListener
{
    ViewParentJDialog adaptee;

    ViewParentJDialog_quiqButton_actionAdapter(ViewParentJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.quiqButton_actionPerformed(e);
    }
}

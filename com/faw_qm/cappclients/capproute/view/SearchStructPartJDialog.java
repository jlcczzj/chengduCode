/**
 * ���ɳ��� SearchStructPartJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 2009/06/08 ��ѧ��  ԭ�򣺽���ѡ���㲿�����Ľ����ɷ�ģ̬�޸�Ϊģ̬
 */
package com.faw_qm.cappclients.capproute.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.faw_qm.cappclients.beans.processtreepanel.PartTreePanel;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;



/**
 * <p>
 * ���ܳɽṹ������㲿��ʱ,���ñ�����,ѡ���㲿��
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */

public class SearchStructPartJDialog extends JDialog 
{

    /**
     * ��ǣ��Ƿ�ִ���˱������
     */
    public boolean isSave = false;

    /**
     * װ���û�����ѡ����㲿��
     */
    private Vector results = new Vector();



    /**
     * ������
     */
    private Frame parentFrame;

    /**
     * ��Դ�ļ�·��
     */
    private static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";

    /**
     * ����:��ѡ��������㲿��
     */
    private HashMap partsMap = new HashMap();

    /**
     * ���ù���
     */
    private ConfigSpecItem configSpecItem = null;

    /** �㲿���� */

    /**
     * ������Ա���
     */
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");

    private JButton browseJButton = new JButton();

    JButton okJButton = new JButton();

    JButton cancelJButton = new JButton();

    JScrollPane jScrollPane1 = new JScrollPane();

    JScrollPane jScrollPane2 = new JScrollPane();

//    static PartTreePanel partTreePanel = new PartTreePanel(null);
    PartTreePanel partTreePanel=null;

    JLabel productStructureJLabel = new JLabel();

    JLabel selectedPartJLabel = new JLabel();

    JLabel statusJLabel = new JLabel();
    
    JPanel jPanel1 = new JPanel();

    JPanel jPanel2 = new JPanel();

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    JPanel jPanel3 = new JPanel();

    GridBagLayout gridBagLayout2 = new GridBagLayout();


    JPanel jPanel5 = new JPanel();

    GridBagLayout gridBagLayout3 = new GridBagLayout();

    GridBagLayout gridBagLayout4 = new GridBagLayout();

    private QMPartMasterIfc partMasterIfc = null;
//  CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    private String  partid;
//  CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
    QMMultiList qMMultiList1 = new QMMultiList();

    private Vector v = new Vector();
    
    private static final String SCREEN_ID = "com.faw_qm.clients.richtothin."; //ע���ļ��е�����ͷ
    
    private static final String PARAM = "param"; //ע���ļ��е�����β
    
//  CCEnd by leixiao 2008-10-14 ԭ�򣺽������,���Ӳ鿴�㲿������

    /**
     * ���캯��
     *
     * @param frame
     *            ������
     */

    public SearchStructPartJDialog(Frame frame) {
    	
    	//begin CR1
        super(frame, "", true);
        //end CR1
        parentFrame = frame;
        try {
        	partTreePanel = new PartTreePanel(null);
            partTreePanel.getRoot().setLabel("�ṹ");
            jbInit();
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
  
    /**
     * �����ʼ��
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setTitle("ѡ���㲿��");
        this.setSize(800, 600);
        this.getContentPane().setLayout(gridBagLayout4);
        this.addWindowListener(new SearchStructPartJDialog_this_windowAdapter(this));      
        
        localize();

        browseJButton.setMaximumSize(new Dimension(91, 23));
        browseJButton.setMinimumSize(new Dimension(91, 23));
        browseJButton.setPreferredSize(new Dimension(91, 23));
        browseJButton.setMnemonic('R');
        browseJButton.setText("����(R). . .");
        browseJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browseJButton_actionPerformed(e);
            }
        });
        okJButton.setText("ȷ��(Y)");
        okJButton
                .addActionListener(new SearchStructPartJDialog_okJButton_actionAdapter(
                        this));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("ȷ��");
        okJButton.setMnemonic('Y');
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setText("ȡ��(C)");
        cancelJButton
                .addActionListener(new SearchStructPartJDialog_cancelJButton_actionAdapter(
                        this));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        productStructureJLabel.setMaximumSize(new Dimension(160, 18));
        productStructureJLabel.setMinimumSize(new Dimension(50, 18));
        productStructureJLabel.setOpaque(false);
        productStructureJLabel.setPreferredSize(new Dimension(50, 18));
        productStructureJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        productStructureJLabel.setText("������Ҫ��ӵ��ܳ�");
        selectedPartJLabel.setText("ѡ���㲿��");
        selectedPartJLabel.setPreferredSize(new Dimension(100, 18));
        selectedPartJLabel.setOpaque(false);
        selectedPartJLabel.setMinimumSize(new Dimension(65, 18));
        selectedPartJLabel.setMaximumSize(new Dimension(65, 18));
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(200, 23));
        statusJLabel.setMinimumSize(new Dimension(41, 23));
        statusJLabel.setPreferredSize(new Dimension(41, 23));
        statusJLabel.setText("");
//      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
        String items[] = { "��ʶ", "���", "����" ,"�汾","id","״̬"};
        int[] head = { 0, 1, 1, 1, 0 ,1};
//      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
        qMMultiList1.setRelColWidth(head);
        int[] cols = { 1, 2 };
        qMMultiList1.setColsEnabled(cols, false);
        qMMultiList1.setHeadings(items);
        jPanel2.setLayout(gridBagLayout1);
        jPanel3.setLayout(gridBagLayout2);
        jPanel5.setLayout(gridBagLayout3);
        jPanel2.setBorder(null);
        jPanel3.setBorder(null);
        jScrollPane2.setPreferredSize(new Dimension(199, 152));
      
        this.getContentPane().add(
                statusJLabel,
                new GridBagConstraints(0, 3, 3, 1, 1.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                jPanel2,
                new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 8, 0, 0), 0, 0));
        jPanel2.add(productStructureJLabel, new GridBagConstraints(0, 0, 2, 1,
                0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(5, 0, 5, 0), 0, 0));
        jPanel2.add(browseJButton, new GridBagConstraints(1, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(5, 0, 5, 20), 0, 0));
        jPanel2.add(partTreePanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        this.getContentPane().add(
                jPanel3,
                new GridBagConstraints(2, 0, 1, 2, 1.0, 1.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 0, 0, 8), 0, 0));
        jPanel3.add(selectedPartJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(5, 0, 5, 0), 0, 0));
        jPanel3.add(jScrollPane2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        jScrollPane2.getViewport().add(qMMultiList1, null);
       
        this.getContentPane().add(
                jPanel5,
                new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                        GridBagConstraints.EAST, GridBagConstraints.NONE,
                        new Insets(10, 0, 10, 8), 0, 0));
        jPanel5.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        0, 0, 0), 0, 0));
        jPanel5.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
                        8, 0, 0), 0, 0));
        qMMultiList1.getTable().setShowHorizontalLines(true);
        qMMultiList1.getTable().setShowVerticalLines(true);
    }

    /**
     * �������ù���
     *
     * @param configSpecItem
     *            ���ù���
     */
   public void setConfigSpec(ConfigSpecItem configSpecItem) {
        this.configSpecItem = configSpecItem;
    }

    /**
     * ���õ�ǰ��ɸѡ����.
     *
     * @param refresh =
     *            true :�������õ�ɸѡ����ˢ�¹�����ҳ���ڵ��㲿�� =false :��ˢ�¹�����ҳ���ڵ��㲿��
     */
    public void setConfigSpecCommand() {
        try {
            //��ȡ�㲿�����ù淶
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper
                    .getConfigSpec();
            ViewObjectIfc vo = null;
            ConfigSpecItem config = null;


            //���û�����ù淶������Ĭ�ϵġ���׼�����ù淶��
            if (configSpecInfo == null) {

                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                try {
                    dd = (Vector) RequestServerFactory.getRequestServer()
                            .request(info1);

                } catch (QMRemoteException e) {
                    JOptionPane.showMessageDialog(this, e.getClientMessage());
                }
                //dd = null;
                if (dd == null || dd.size() == 0) {
                    return;
                }
                for (int i = 0; i < dd.size(); i++) {
                    if (((ViewObjectIfc) dd.elementAt(i)).getViewName().equals(
                            "������ͼ")) {
                        vo = (ViewObjectIfc) dd.elementAt(i);
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                //��׼������
                PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
                //���ñ�׼ʱ����ͼ
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //���÷������úõ�ɸѡ�������浽���ݿ���
                RequestServer server = RequestServerFactory.getRequestServer();
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("ExtendedPartService");
                info.setMethodName("savePartConfigSpec");
                Class[] paramClass = { PartConfigSpecIfc.class };
                info.setParaClasses(paramClass);
                Object[] paramValue = { configSpecInfo };
                info.setParaValues(paramValue);
                try {
                    configSpecInfo = (PartConfigSpecInfo) server.request(info);
                } catch (QMRemoteException ex) {
                    ex.printStackTrace();
                    return;
                }
                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpec(config);
            } else {

                config = new ConfigSpecItem(configSpecInfo);
                setConfigSpec(config);
            }

        } catch (QMRemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ù���
     */
    private void configureCriterion() {
        //��ʾ���༭�ṹɸѡ������ҳ�沢�����ݿ��д��ڵ�ɸѡ����������ҳ�档
        partTreePanel.configureCriterion();
    }

    /**
     * ���ػ�
     */
    private void localize() {

    }

    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

    }

    /**
     * ���ظ��෽����ʹ������ʾ����Ļ����
     *
     * @param flag
     */
    public void setVisible(boolean flag) {
        this.setViewLocation();
        super.setVisible(flag);
    }

   

    /**
     * ���ò�ƷID added by skybird 2005.3.16
     *
     * @param product
     */
    public void setProductID(QMPartIfc productInfo) {
        try {
            partTreePanel.getRoot().setLabel(
                    productInfo.getPartNumber() + "_"
                            + productInfo.getPartName() + "�Ľṹ");
            this.addProductToTree(productInfo);

        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
//                    QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
//                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * �Ѳ�Ʒ�ṹ�����з����������㲿��������б���
     *
     * @param parts
     *            �㲿��ֵ���󼯺�
     */
    private void addProductToTree(QMPartIfc product) {
        QMNode pNode = (QMNode) partTreePanel.getRoot();
        if (pNode.getChildCount() > 0)
            partTreePanel.removeNode((QMNode) pNode.getChildAt(0));
        partTreePanel.addPart((QMPartInfo)product);       
//                .addPartMasters(new QMPartMasterInfo[] { (QMPartMasterInfo) product });
    }

    /**
     * �Ѳ�Ʒ�ṹ�����з����������㲿��������б���
     *
     * @param parts
     *            �㲿��ֵ���󼯺�
     */
    private void addPartsToList(Vector parts) {
        if (parts != null && parts.size() > 0) {
            String as[] = new String[parts.size()];
            for (int i = 0; i < parts.size(); i++) {
                QMPartMasterInfo info = (QMPartMasterInfo) parts.elementAt(i);
                as[i] = info.getBsoID() + ";" + info.getPartNumber() + ";"
                        + info.getPartName();
                partsMap.put(info.getBsoID(), info);
            }


        }
    }

    /**
     * ���÷���,������з����������㲿��
     *
     * @return ���з����������㲿��
     */
//    private Vector getSubParts() {
//        boolean flag = false;
//        Vector vec = new Vector();
//        Class[] cla = { String.class, QMPartMasterIfc.class };
//        BaseValueIfc part = (BaseValueIfc) partTreePanel.getSelected().getObj()
//                .getObject();
//        if (part instanceof QMPartIfc) {
//            part = ((QMPartIfc) part).getMaster();
//        }
//        Object[] obj = { this.myRouteList.getRouteListDepartment(), part };
//
//        try {
//            flag = ((Boolean) CappRouteAction.useServiceMethod(
//                    "TechnicsRouteService", "getOptionPart", cla, obj))
//                    .booleanValue();
//        } catch (QMRemoteException ex) {
//            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
//                    QMMessage.getLocalizedMessage(RESOURCE, "exception", null),
//                    JOptionPane.INFORMATION_MESSAGE);
//            // return null;
//        }
//        if (flag) {
//            vec.add(part);
//        }
//        return vec;
//    }



    /**
     * �Ѳ�Ʒ�ṹ�����з����������㲿��������б���
     *
     * @param parts
     *            �㲿��ֵ���󼯺�
     */
    /**
     * JOptionPane.INFORMATION_MESSAGE); } } /** ȷ�ϲ���
     *
     * @param e
     *            ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e) {
        //���List��part
        Vector vect = new Vector();
        for (int i = 0; i < qMMultiList1.getNumberOfRows(); i++) {
            String patListBsoId = qMMultiList1.getCellText(i, 0);
//          CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
            partid = qMMultiList1.getCellText(i, 4);
//          CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id         

//            System.out
//                    .println("_____________" + qMMultiList1.getCellText(i, 0));
            //���÷���
            Class[] string = { String.class };
            String[] bsoId = { patListBsoId };
//          CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
            Class[] string1 = { String.class };
            String[] bsoId1 = { partid };
            try {
                partMasterIfc = (QMPartMasterIfc) CappRouteAction
                        .useServiceMethod("PersistService", "refreshInfo",
                                string, bsoId);

                QMPartIfc partIfc = (QMPartIfc) CappRouteAction
                .useServiceMethod("PersistService", "refreshInfo",
                		string1, bsoId1);
                
                //partMasterIfc=(QMPartMasterIfc)part.getMaster();
                Object [] part={partMasterIfc,partIfc};
                vect.addElement(part);
               // vect.addElement(partMasterIfc);
//              CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id


            } catch (QMRemoteException ex) {
                JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                        QMMessage.getLocalizedMessage(RESOURCE, "exception",
                                null), JOptionPane.INFORMATION_MESSAGE);
            }
        }
        this.setVisible(false);
        RefreshService.getRefreshService().dispatchRefresh("addSelectedParts",
                0, vect);
    }

    /***************************************************************************
     * public String[] getPartDetails() { if(part!=null) { String partId =
     * part.getBsoID(); String partNumber = part.getPartNumber(); String
     * partName = part.getPartName(); String partRouteStatus = "��"; String[]
     * partDetails = { partId, partNumber, partName, partRouteStatus};
     * System.out.println("partId=" + partDetails[0] + "=partNumber=" +
     * partDetails[1] + "=partName=" + partDetails[2] + "=partRouteStatus=" +
     * partDetails[3]); return partDetails; } else return null; }
     */
    /**
     * ȡ������
     *
     * @param e
     *            ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }

    public Vector getSelectedParts() {
        return results;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SearchStructPartJDialog frame = new SearchStructPartJDialog(null);
            frame.setViewLocation();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void this_windowClosing(WindowEvent e) {
        this.isSave = false;
        this.setVisible(false);
    }

 

    /**
     * ʹ�����ù淶
     * @param vec
     * @return Vector[]
     * @throws QMRemoteException
     */
    private Vector[] doallfilter(Vector vec) throws QMRemoteException {
        Vector successVec = new Vector();
        Vector failVec = new Vector();
        QMPartIfc part;
        for (int j = 0; j < vec.size(); j++) {
            part = (QMPartIfc) vec.elementAt(j);

                successVec.add(part);

        }
        Vector[] vector = { successVec, failVec };
        return vector;
    }



//leix
    /**
     * ��ӵ��ұ��б�
     * @param e
     */
    void rightAllJButton_actionPerformed(QMPartIfc partinfo ) {

        Vector vec = getChild(partinfo);

        Vector[] vecArray = null;
        try {
            vecArray = doallfilter(vec);

        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(), "�쳣��Ϣ",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        QMPartMasterIfc master = null;
        for (int i = 0; i < vecArray[0].size(); i++) {

            QMPartIfc part = (QMPartIfc) vecArray[0].elementAt(i);
            master = (QMPartMasterIfc) part.getMaster();
            int ii = qMMultiList1.getNumberOfRows();
            boolean isExist = false;
            for (int iii = 0; iii < ii; iii++) {
                String id1 = qMMultiList1.getCellText(iii, 0);
                if (master.getBsoID().equals(id1)) {
                    isExist = true;
                    break;
                }
            }
            if (isExist)
                continue;
            qMMultiList1.addTextCell(ii, 0, master.getBsoID());
            qMMultiList1.addTextCell(ii, 1, master.getPartNumber());
            qMMultiList1.addTextCell(ii, 2, master.getPartName());
            //CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
            qMMultiList1.addTextCell(ii, 3, part.getVersionValue());
            qMMultiList1.addTextCell(ii, 4, part.getBsoID());
            qMMultiList1.addTextCell(ii, 5, part.getLifeCycleState().getDisplay());
            //CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·��

        }
        if (vecArray[1].size() > 0) {
            JOptionPane.showMessageDialog(this, "��" + vecArray[1].size()
                    + "���㲿��������Ҫ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);

        }

    }


    /**
     * �ݹ鷽���ҳ����кϸ���ӽڵ�
     * @return Vector
     */
    private Vector getChild(QMPartIfc partinfo) {
        Vector vec = new Vector();

        Class[] paraclass = { QMPartIfc.class,
                PartConfigSpecIfc.class };
        Object[] paraObj = {
        		(QMPartIfc)partinfo,
                (PartConfigSpecIfc) partTreePanel.getConfigSpecItem()
                        .getConfigSpecInfo() };
        try {
            Vector qmPartInfos = (Vector) RParentJPanel
                    .useServiceMethod("EnterprisePartService",
                            "getAllSubPartsByConfigSpec", paraclass,
                            paraObj);
            QMNode parentNode = (QMNode) partTreePanel.getRoot()
                    .getChildAt(0);
            if (parentNode != null
                    && parentNode.getObj().getObject() instanceof QMPartIfc){
            	QMPartIfc rootpart=(QMPartIfc)parentNode.getObj().getObject();
            	if(rootpart.getPartNumber().length()>5&&rootpart.getPartNumber().substring(4,5).equals("G")){
            	}
            	else{
                vec.add(parentNode.getObj().getObject());
            	}
            }
            if (qmPartInfos != null) {
                for (int j = 0; j < qmPartInfos.size(); j++) {
                	QMPartIfc part=(QMPartIfc)qmPartInfos.get(j);
                	
//                    if (part.getMaster().getBsoID().equals(
//                            this.partMasterIfc.getBsoID())) {
//                        continue;
//                    }
                	if(part.getPartNumber().length()>5&&part.getPartNumber().substring(4,5).equals("G")){
                	}
                	//	System.out.println("----"+part.getPartNumber());
                	else{
                    vec.add(part);
                	}
                }
            }

        } catch (QMRemoteException ex) {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                    "�쳣��Ϣ", JOptionPane.INFORMATION_MESSAGE);
        }

    

        return vec;
    }
    
    /**
     * ���������ڲ�Ʒ�����㲿��
     *
     * @param e
     *            ActionEvent
     */
    void browseJButton_actionPerformed(ActionEvent e) {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "findPartTitle",
                null);
        //����������
        QmChooser qmChooser = new QmChooser("QMPart", title, this.getParent()
                );
//        qmChooser.setRelColWidth(new int[] { 1, 1,1,1,1 });
//      CCBegin by leixiao 2009-1-4 ԭ�򣺽����������·��,
        qmChooser.setChildQuery(true);
//      CCEnd by leixiao 2009-1-4 ԭ�򣺽����������·��, 
        try {
            qmChooser.setMultipleMode(false);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
            return;
        }
        //���ո���������ִ������
        qmChooser.addListener(new QMQueryListener() {

            public void queryEvent(QMQueryEvent e) {
                qmChooser_queryEvent(e);
            }
        });

        qmChooser.setVisible(true);
    }

    /**
     * �����㲿�������¼�����
     *
     * @param e
     *            ���������¼�
     */
    private void qmChooser_queryEvent(QMQueryEvent e) {
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) begin...");
        }
        if (e.getType().equals(QMQueryEvent.COMMAND)) {
            if (e.getCommand().equals(QmQuery.OkCMD)) {
                //��������������������������㲿��
                QmChooser c = (QmChooser) e.getSource();
                BaseValueIfc bvi = c.getSelectedDetail();
                if (bvi != null) {
                    setProductID((QMPartIfc)bvi);
                    rightAllJButton_actionPerformed((QMPartIfc)bvi);
                }
            }
        }
        if (verbose) {
            System.out
                    .println("capproute.view.RouteListTaskJPanel:qmChooser_queryEvent(e) end...return is void");
        }
    }
    
    


    /**
     * ������ť
     * @param e
     */
    void helpJButton_actionPerformed(ActionEvent e) {
        //System.out.println("help");
    }


}

 /**
  * <p>Title:�����¼�������</p>
  * <p>Description: </p>
  */
class SearchStructPartJDialog_this_windowAdapter extends java.awt.event.WindowAdapter {
    private SearchStructPartJDialog adaptee;

    SearchStructPartJDialog_this_windowAdapter(SearchStructPartJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }
}




 /**
  * <p>Title:ȷ����ť������</p>
  * <p>Description: </p>
  */
class SearchStructPartJDialog_okJButton_actionAdapter implements
        java.awt.event.ActionListener {
    SearchStructPartJDialog adaptee;

    SearchStructPartJDialog_okJButton_actionAdapter(SearchStructPartJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.okJButton_actionPerformed(e);
    }
}

 /**
  * <p>Title:ȡ����ť������</p>
  * <p>Description: </p>
  */
class SearchStructPartJDialog_cancelJButton_actionAdapter implements
        java.awt.event.ActionListener {
    SearchStructPartJDialog adaptee;

    SearchStructPartJDialog_cancelJButton_actionAdapter(SearchStructPartJDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.cancelJButton_actionPerformed(e);
    }
}


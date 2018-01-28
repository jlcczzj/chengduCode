/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.part.model.QMPartIfc;



/**
 * <p>Title: ѡ���������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class ProductSelectJDialog extends JDialog
{
    /**ҳ�����*/
    JPanel panel1 = new JPanel();
    /**����ҳ��*/
    JScrollPane jScrollPane1 = new JScrollPane();
    /**ȷ�ϰ�ť*/
    JButton okButton = new JButton();
    /**ȡ����ť*/
    JButton cancelButton = new JButton();
    /**ҳ�沼��*/
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    /**��ҳ��*/
    TechnicsMasterJPanel parentPanel ;
    /** ���б����� */
    public ComponentMultiList qMMultiList = new ComponentMultiList();

    
    /**
     * ���췽��
     * @param TechnicsMasterJPanel ��ҳ��
     * @author wenl
     */
    public ProductSelectJDialog(TechnicsMasterJPanel parentPanel )
    {
    	this.parentPanel = parentPanel;
    	this.setTitle("ѡ�����ڲ�Ʒ��");
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
     * ��ʼ������
     * @author wenl
     */
    private void jbInit()
            throws Exception
    {
        //��ӹ̶���ͷ
        String[] heads = new String[3];
        heads[0] = "partBsoID";
        heads[1] = "������";
        heads[2] = "�������";
        int[] relcolwidth = new int[3];
        relcolwidth[0] = 0;//����bsoID����ʾ
        relcolwidth[1] = 1;
        relcolwidth[2] = 1;
 
        this.setSize(600, 360);
        panel1.setLayout(gridBagLayout1);
        okButton.setToolTipText("");
        okButton.setText("ȷ��");
        okButton.addActionListener(new
                SelectProductDialog_okButton_actionAdapter(this));
        cancelButton.setText("ȡ��");
        cancelButton.addActionListener(new
                SelectProductDialog_cancelButton_actionAdapter(this));
        this.setResizable(false);
        
        //���ñ�ͷ����ʾ��Ϣ��
        qMMultiList.setHeadings(heads);
        qMMultiList.setRelColWidth(relcolwidth);
        //��ñ�ͷ��Ӧ���У��Ժ���ֵȡֵ��
        qMMultiList.setCellEditable(false);
        qMMultiList.setMultipleMode(false);
        qMMultiList.setAllowSorting(true);
        qMMultiList.setSelectedRow(0);
        getContentPane().add(panel1);
        panel1.add(jScrollPane1, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 19, 0, 20), 1, -27));
        panel1.add(cancelButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(16, 7, 21, 20), 16, -2));
        panel1.add(okButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(16, 126, 21, 0), 16, -2));
        jScrollPane1.getViewport().add(qMMultiList, null);
       
    }

    /**
     * ���ñ����Ϣ
     * @param Vector �����ڲ�Ʒ����
     * @author wenl
     */
    public void setPartVec(Vector vec)
    {
    	  qMMultiList.clear();
          if(vec == null || vec.size() == 0)
          {
              return;
          }
          for(int i = 0;i < vec.size();i++)
          {
        	  Object[] objs = (Object[])vec.get(i);
        	  QMPartIfc partIfc = (QMPartIfc)objs[0];
              //��ͷ������Ӧ����
              qMMultiList.addTextCell(i, 0, partIfc.getBsoID());
              qMMultiList.addTextCell(i, 1, partIfc.getPartNumber());
              qMMultiList.addTextCell(i, 2, partIfc.getPartName());

          }
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
     * ���ñ������Ƿ���ʾ
     * @param flag boolean �Ƿ���ʾ������
     */
    public void setVisible(boolean flag)
    {
        if (flag)
        {
            setViewLocation();
            super.setVisible(true);
        }
        else
        {
            super.setVisible(false);
        }
    }
    /**
     * ȡ����ť����
     * @param ActionEvent �����¼�
     * @author wenl
     */
    void cancelButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }
    /**
     * ȷ����ť����
     * @param ActionEvent �����¼�
     * @author wenl
     */
    void okButton_actionPerformed(ActionEvent e)
    {
    	
    	String productPartBsoID = (String)qMMultiList.getCellText(qMMultiList.getSelectedRow(), 0);//��Ʒƽ̨���bsoID
    	String productNumber = (String)qMMultiList.getCellText(qMMultiList.getSelectedRow(), 1);//��Ʒƽ̨������
    	String productName = (String)qMMultiList.getCellText(qMMultiList.getSelectedRow(), 2);//��Ʒƽ̨�������
    	parentPanel.separableName.setText(productName);
    	parentPanel.separableNumber.setText(productNumber);
    	
    	//����ʹ������
    	String mainPart = parentPanel.hideMainPartBsoID.getText();
        RequestServer server = RequestServerFactory.getRequestServer();
		//���PartInfo
		QMPartIfc partIfc = null;
		ServiceRequestInfo info2 = new ServiceRequestInfo();
		info2.setServiceName("PersistService");
		info2.setMethodName("refreshInfo");
		Class[] theClass = { String.class };
		Object[] obj2 = { mainPart };
		info2.setParaClasses(theClass);
		info2.setParaValues(obj2);
		try {
		 partIfc = (QMPartIfc) server.request(info2);
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		
		//��ò�Ʒƽ̨ifc
		QMPartIfc productPartIfc = null;
		ServiceRequestInfo info3 = new ServiceRequestInfo();
		info3.setServiceName("PersistService");
		info3.setMethodName("refreshInfo");
		Class[] theClass3 = { String.class };
		Object[] obj3 = { productPartBsoID };
		info3.setParaClasses(theClass3);
		info3.setParaValues(obj3);
		try {
			productPartIfc = (QMPartIfc) server.request(info3);
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		
		//��õ�ǰ��Ҫ����ڱ����ڲ�Ʒ�е�ʹ�ü���
		String count = "";
		ServiceRequestInfo info4 = new ServiceRequestInfo();
		info4.setServiceName("StandardPartService");
		info4.setMethodName("getPartQuantity");
		Class[] theClass4 = { QMPartIfc.class,QMPartIfc.class };
		Object[] obj4 = { productPartIfc, partIfc};
		info4.setParaClasses(theClass4);
		info4.setParaValues(obj4);
		try {
			count = (String) server.request(info4);
		} catch (QMException ee) {
			ee.printStackTrace();
		}
		parentPanel.separableCount.setText(count);
    	this.setVisible(false);
    }


}

/**
 * <p>Title: ��ť�����ڲ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
class SelectProductDialog_cancelButton_actionAdapter implements java.awt.
        event.ActionListener
{
    ProductSelectJDialog adaptee;

    SelectProductDialog_cancelButton_actionAdapter(ProductSelectJDialog
            adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelButton_actionPerformed(e);
    }
}

/**
 * <p>Title: ��ť�����ڲ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */
class SelectProductDialog_okButton_actionAdapter implements java.awt.event.
        ActionListener
{
	ProductSelectJDialog adaptee;

    SelectProductDialog_okButton_actionAdapter(ProductSelectJDialog
                                                  adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okButton_actionPerformed(e);
    }
}




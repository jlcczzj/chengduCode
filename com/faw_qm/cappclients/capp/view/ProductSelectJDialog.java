/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Title: 选择被用于零件</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
 * @version 1.0
 */

public class ProductSelectJDialog extends JDialog
{
    /**页面面板*/
    JPanel panel1 = new JPanel();
    /**滚动页面*/
    JScrollPane jScrollPane1 = new JScrollPane();
    /**确认按钮*/
    JButton okButton = new JButton();
    /**取消按钮*/
    JButton cancelButton = new JButton();
    /**页面布局*/
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    /**父页面*/
    TechnicsMasterJPanel parentPanel ;
    /** 多行表格变量 */
    public ComponentMultiList qMMultiList = new ComponentMultiList();

    
    /**
     * 构造方法
     * @param TechnicsMasterJPanel 父页面
     * @author wenl
     */
    public ProductSelectJDialog(TechnicsMasterJPanel parentPanel )
    {
    	this.parentPanel = parentPanel;
    	this.setTitle("选择被用于产品！");
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
     * 初始化方法
     * @author wenl
     */
    private void jbInit()
            throws Exception
    {
        //添加固定表头
        String[] heads = new String[3];
        heads[0] = "partBsoID";
        heads[1] = "零件编号";
        heads[2] = "零件名称";
        int[] relcolwidth = new int[3];
        relcolwidth[0] = 0;//设置bsoID不显示
        relcolwidth[1] = 1;
        relcolwidth[2] = 1;
 
        this.setSize(600, 360);
        panel1.setLayout(gridBagLayout1);
        okButton.setToolTipText("");
        okButton.setText("确定");
        okButton.addActionListener(new
                SelectProductDialog_okButton_actionAdapter(this));
        cancelButton.setText("取消");
        cancelButton.addActionListener(new
                SelectProductDialog_cancelButton_actionAdapter(this));
        this.setResizable(false);
        
        //设置表头、显示信息等
        qMMultiList.setHeadings(heads);
        qMMultiList.setRelColWidth(relcolwidth);
        //获得表头对应的列，以后设值取值用
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
     * 设置表格信息
     * @param Vector 被用于产品集合
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
              //表头列名对应的列
              qMMultiList.addTextCell(i, 0, partIfc.getBsoID());
              qMMultiList.addTextCell(i, 1, partIfc.getPartNumber());
              qMMultiList.addTextCell(i, 2, partIfc.getPartName());

          }
    }
    /**
     * 设置界面的显示位置
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
     * 设置本界面是否显示
     * @param flag boolean 是否显示本界面
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
     * 取消按钮监听
     * @param ActionEvent 监听事件
     * @author wenl
     */
    void cancelButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }
    /**
     * 确定按钮监听
     * @param ActionEvent 监听事件
     * @author wenl
     */
    void okButton_actionPerformed(ActionEvent e)
    {
    	
    	String productPartBsoID = (String)qMMultiList.getCellText(qMMultiList.getSelectedRow(), 0);//产品平台零件bsoID
    	String productNumber = (String)qMMultiList.getCellText(qMMultiList.getSelectedRow(), 1);//产品平台零件编号
    	String productName = (String)qMMultiList.getCellText(qMMultiList.getSelectedRow(), 2);//产品平台零件名称
    	parentPanel.separableName.setText(productName);
    	parentPanel.separableNumber.setText(productNumber);
    	
    	//设置使用数量
    	String mainPart = parentPanel.hideMainPartBsoID.getText();
        RequestServer server = RequestServerFactory.getRequestServer();
		//获得PartInfo
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
		
		//获得产品平台ifc
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
		
		//获得当前主要零件在被用于产品中的使用集合
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
 * <p>Title: 按钮监听内部类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
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
 * <p>Title: 按钮监听内部类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 文柳
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




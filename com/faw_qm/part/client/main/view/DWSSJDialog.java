package com.faw_qm.part.client.main.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Vector;

import javax.swing.*;

import com.faw_qm.bomNotice.client.util.ProductTreeNode;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.clients.beans.explorer.QMNode;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.design.model.PartItem;
import com.faw_qm.part.client.design.model.ProgressService;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.controller.QMProductManager;
import com.faw_qm.part.ejb.entity.QMPartMaster;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.version.model.IteratedIfc;

//SS1 原功能只支持选中根节点搜索，现改为选择树上任意节点搜索选中节点下的所有结构 pante 2014-01-15
public class DWSSJDialog extends JDialog
{
	private QMProductManager manager;
	private Frame myframe;
	JPanel jPanel1 = new JPanel();
	JTextField jTextField1 = new JTextField();
	JButton jButton1 = new JButton();
	JPanel jPanel3 = new JPanel();
	JButton jButton3 = new JButton();
	JButton jButton4 = new JButton();
	JLabel jLabel3 = new JLabel();


	GridBagLayout gridBagLayout1 = new GridBagLayout();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	GridBagLayout gridBagLayout4 = new GridBagLayout();

	public DWSSJDialog(QMProductManager m, Frame frame) {
		manager = m;
		myframe = frame; 
		try {
			jbInit();
//			pack();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(gridBagLayout1);
		jPanel1.setBorder(null);
		jPanel1.setLayout(gridBagLayout3);

		jLabel3.setText("零部件编号");
		jPanel1.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE,
				new Insets(5, 5, 5, 5), 0, 0));

		jTextField1.setMaximumSize(new Dimension(80, 22));
		jTextField1.setMinimumSize(new Dimension(80, 22));
		jTextField1.setPreferredSize(new Dimension(80, 22));
		jTextField1.setText("");
		jPanel1.add(jTextField1, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
				, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 5), 0, 0));	    
		jPanel3.setLayout(gridBagLayout1);

		jButton1.setMaximumSize(new Dimension(56, 23));
		jButton1.setMinimumSize(new Dimension(56, 23));
		jButton1.setPreferredSize(new Dimension(56, 23));
		jButton1.setText("搜索");
		jButton1.addActionListener(new DWSSJDialog_jButton1_actionAdapter(this));

		jButton4.setMaximumSize(new Dimension(60, 23));
		jButton4.setMinimumSize(new Dimension(60, 23));
		jButton4.setPreferredSize(new Dimension(60, 23));
		jButton4.setText("取消");
		jButton4.addActionListener(new DWSSJDialog_jButton4_actionAdapter(this));

		this.getContentPane().add(jPanel1,
				new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
						, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 0, 1), 0, 0));

		this.getContentPane().add(jPanel3,
				new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
						, GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
						new Insets(0, 0, 8, 0), 0, 0));
		jPanel3.add(jButton1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER,
				GridBagConstraints.NONE,
				new Insets(8, 0, 1, 8), 16, 0));
		jPanel3.add(jButton4, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.NORTHEAST,
				GridBagConstraints.NONE,
				new Insets(8, 0, 1, 8), 0, 0));
		setViewLocation();
	}

	private void setViewLocation() {
		this.setSize(500, 200);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation( (screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);
		this.setVisible(true);
	}
	
	private void searchPart(QMNode root,QMPartIfc subpart)
	{
		QMExplorer myExplorer = manager.getExplorer();
		QMNode firstChild = root.getC();
		Vector allChildren = new Vector();
		for (QMNode node = firstChild; node != null; node = node.getS())
		{
			myExplorer.processExpand(node);
			Object obj = node.getObj();
			if(obj instanceof UsageItem){
				UsageItem ui = (UsageItem)obj;
				PartItem qp = (PartItem) ui.getUsesPart();
				if(qp.getPart().getPartNumber().equals(subpart.getPartNumber())){
//					flag = true;
					myExplorer.setSelectedNode(node);
					break;
				}
				else{
					allChildren.add(node);
					myExplorer.processExpose(node);
				}
			}
		}
		if(allChildren!=null&&allChildren.size()>0){
    		for(Iterator ites = allChildren.iterator();ites.hasNext();){
    			QMNode subNode = (QMNode)ites.next();
    			searchPart(subNode,subpart);
    		}
    	}
	}

	void jButton1_actionPerformed(ActionEvent e) {
		ProgressService.setProgressText(QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB","working",null));
		ProgressService.showProgress();
		String partnum = jTextField1.getText();
		QMPartIfc subpart = null;
		try{
			ServiceRequestInfo info = new ServiceRequestInfo();
			info.setServiceName("StandardPartService");
			info.setMethodName("findSubPartMaster");
			Class[] theClass = {String.class};
			info.setParaClasses(theClass);
			Object[] objs = {partnum};
			info.setParaValues(objs);
			RequestServer server = RequestServerFactory.getRequestServer();
			subpart = (QMPartIfc) server.request(info);
		} catch (QMRemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		QMExplorer myExplorer = manager.getExplorer();
		QMNode root = myExplorer.getSelectedNode();
		if (root == null)
		{
			//			root = myExplorer.getTree().getRoot();
		}
		Object bvi = root.getObj();
		// 如果是零件节点
		QMPartIfc part = null;
//		CCBegin SS1
		if(bvi instanceof PartItem)
		{
			PartItem pi = (PartItem)bvi;
			part = pi.getPart();
		}
		if(bvi instanceof UsageItem){
			UsageItem ui = (UsageItem)bvi;
			PartItem pi = (PartItem) ui.getUsesPart();
			part = pi.getPart();
		}
//		CCEnd SS1
		boolean flag = false;

		ServiceRequestInfo info1 = new ServiceRequestInfo();
		info1.setServiceName("StandardPartService");
		info1.setMethodName("isSubNode");
		Class[] theClass1 = {QMPartIfc.class,QMPartIfc.class};
		info1.setParaClasses(theClass1);
		Object[] objs1 = {part,subpart};
		info1.setParaValues(objs1);
		RequestServer server1 = RequestServerFactory.getRequestServer();
		try {
			flag = (Boolean) server1.request(info1);
		} catch (QMRemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(flag){
			searchPart(root,subpart);
			this.dispose();
		}
		else
			JOptionPane.showMessageDialog(null,"没有找到目标零部件!!!!");
		ProgressService.hideProgress();
	}

	void jButton4_actionPerformed(ActionEvent e) {
		this.dispose();
	}
}
class DWSSJDialog_jButton4_actionAdapter implements java.awt.event.ActionListener {
	DWSSJDialog adaptee;

	DWSSJDialog_jButton4_actionAdapter(DWSSJDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton4_actionPerformed(e);
	}
}

class DWSSJDialog_jButton1_actionAdapter implements java.awt.event.ActionListener {
	DWSSJDialog adaptee;

	DWSSJDialog_jButton1_actionAdapter(DWSSJDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}

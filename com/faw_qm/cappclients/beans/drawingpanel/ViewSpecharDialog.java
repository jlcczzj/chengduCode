/** 生成程序时间 2010/02/23
 * 程序文件名称 ViewSpecharDialog.java
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.beans.drawingpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;


/**用于解放工艺瘦客户查看工艺内容（特殊符号显示问题）
 * @author liuzc
 *
 */
public class ViewSpecharDialog extends JFrame {

    /**工艺内容*/
    private JLabel contentJLabel = new JLabel();
    private SpeCharaterTextPanel contentPanel;
    private ScrollPane scrollPane = new ScrollPane();
    
	/**缺省构造方法
	 * 
	 */
	public ViewSpecharDialog()
	{
	
	}
	
	
	/**界面初始化
	 * 
	 */   
	public void init()
	{
		{
			this.setTitle("工艺内容");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        //{{注册监听
	        addWindowListener(new RMWindow());
	        contentPanel = new SpeCharaterTextPanel(this);
	        initSpeCharaterTextPanel();
	        contentPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog", 0,
	                18));
	        contentPanel.setMaximumSize(new Dimension(32767, 80));
	        contentPanel.setMinimumSize(new Dimension(10, 10));
	        contentPanel.setPreferredSize(new Dimension(100, 50));
	        getContentPane().add(scrollPane, BorderLayout.CENTER);
	        scrollPane.add(contentPanel);
		}
		   
	}
	
	
	/**工艺内容显示
	 * @param bsoID 工序或工步BsoID
	 */
	public ViewSpecharDialog(String bsoID)
	{
		init();
		QMProcedureInfo info = null;
		try
		{
			info= (QMProcedureInfo)CappClientHelper.refresh(bsoID);
			Vector v =info.getContent();
	        //工艺内容
	        contentPanel.setEditable(false);
	        if (v.size() > 0)
	        {
	            contentPanel.resumeData(v);
	        }
	        contentPanel.speCharaterTextBean.setEditable(false);
		}
		catch(QMRemoteException ee)
		{
			ee.printStackTrace();
		}
	}
	
	
    /**
     * 为工艺内容面板添加特殊符号
     */
    private void initSpeCharaterTextPanel()
    {
    	contentPanel.setDrawInfo(CappClientHelper.getSpechar());

        String path = RemoteProperty.getProperty("spechar.image.path");
        if (path == null)
        {
        	contentPanel.setFilePath("/spechar/");
        }
        else
        {
        	contentPanel.setFilePath(path.trim());
        }
    }
	
	
	
	/**main方法调用
	 * @param args
	 */
	public static void main(String[] args) {
        try
        {
        	System.setProperty("swing.useSystemFontSettings", "0");
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        	if (args != null && args.length == 4) 
        	{
        		CappClientRequestServer server = new CappClientRequestServer(args[0], args[1], args[2]);
        		RequestServerFactory.setRequestServer(server);        	
	        	ViewSpecharDialog viewSpechar= new ViewSpecharDialog(args[3]);
	        	viewSpechar.setVisible(true);
	            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	            Dimension frameSize = viewSpechar.getSize();
	            if (frameSize.height > screenSize.height)
	            {  
	                frameSize.height = screenSize.height;
	            }
	            if (frameSize.width > screenSize.width)
	            {
	                frameSize.width = screenSize.width;
	            }
	            viewSpechar.setSize(screenSize.width - 600, screenSize.height - 600);
	    		viewSpechar.setLocation((screenSize.width - frameSize.width) / 4,
	    				(screenSize.height - frameSize.height) / 4);

        	}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
    /**
     * 查看特殊符号界面关闭窗口监听
     *
     */
    class RMWindow extends java.awt.event.WindowAdapter
    {
        public void windowClosing(java.awt.event.WindowEvent event)
        {
        	System.exit(0);
        }
    }

}

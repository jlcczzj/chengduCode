/** ���ɳ���ʱ�� 2010/02/23
 * �����ļ����� ViewSpecharDialog.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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


/**���ڽ�Ź����ݿͻ��鿴�������ݣ����������ʾ���⣩
 * @author liuzc
 *
 */
public class ViewSpecharDialog extends JFrame {

    /**��������*/
    private JLabel contentJLabel = new JLabel();
    private SpeCharaterTextPanel contentPanel;
    private ScrollPane scrollPane = new ScrollPane();
    
	/**ȱʡ���췽��
	 * 
	 */
	public ViewSpecharDialog()
	{
	
	}
	
	
	/**�����ʼ��
	 * 
	 */   
	public void init()
	{
		{
			this.setTitle("��������");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        //{{ע�����
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
	
	
	/**����������ʾ
	 * @param bsoID ����򹤲�BsoID
	 */
	public ViewSpecharDialog(String bsoID)
	{
		init();
		QMProcedureInfo info = null;
		try
		{
			info= (QMProcedureInfo)CappClientHelper.refresh(bsoID);
			Vector v =info.getContent();
	        //��������
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
     * Ϊ���������������������
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
	
	
	
	/**main��������
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
     * �鿴������Ž���رմ��ڼ���
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

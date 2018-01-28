/** ���ɳ���SelectTechnicsTypeJDialog.java	1.1  2004/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ������������Ϣʱ����������û�ֻ��ʾ������Ĺ���  ���� 2014-4-3
 * SS2 ������������Ϣʱ�������û�ֻ��ʾ������Ĺ���  ���� 2014-12-22
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.technics.view.ModelTechnicsMainJFrame;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.util.QMMessage;


/**
 * <p>Title: ��������ѡ���</p>
 * <p>Description: ��ʾ��������еĹ�������,���½���������Ϣʱʹ��</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 */

public class SelectTechnicsTypeJDialog extends JDialog
{
    JPanel panel1 = new JPanel();
    JList list = new JList();
    JButton okButton = new JButton();
    JButton cancelButton = new JButton();
    CodingIfc code;
    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    JScrollPane jScrollPane1 = new JScrollPane();
    JPanel jPanel1 = new JPanel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JFrame parentFrame;

    /**
     * ���췽��
     * @param frame Frame ������
     */
    public SelectTechnicsTypeJDialog(JFrame frame)
    {
        super(frame, "ѡ��" + QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.TECHNICSTYPE, null), true);
        parentFrame=frame;
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
     * ���췽��
     */
    public SelectTechnicsTypeJDialog()
    {
        this(null);
    }

    private void jbInit()
            throws Exception
    {
        cancelButton.setMaximumSize(new Dimension(75, 23));
        cancelButton.setMinimumSize(new Dimension(75, 23));
        cancelButton.setPreferredSize(new Dimension(75, 23));
        cancelButton.setMnemonic('C');
        okButton.setMaximumSize(new Dimension(75, 23));
        okButton.setMinimumSize(new Dimension(75, 23));
        okButton.setPreferredSize(new Dimension(75, 23));
        okButton.setMnemonic('Y');
        panel1.setLayout(gridBagLayout1);
        okButton.setText("jButton1");
        cancelButton.setText("jButton2");
        panel1.setPreferredSize(new Dimension(300, 180));
        jScrollPane1.setMaximumSize(new Dimension(32767, 32767));
        jScrollPane1.setMinimumSize(new Dimension(250, 150));
        jScrollPane1.setPreferredSize(new Dimension(250, 150));
        getContentPane().add(panel1);
        panel1.add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 0, 10), 0, 0));
        panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(10, 50, 0, 50), 0, 0));
        jPanel1.add(okButton, null);
        jPanel1.add(cancelButton, null);
        jScrollPane1.getViewport().add(list, null);
        localize();
        addCoding();
        okButton.addActionListener(new
                                   SelectTechnicsTypeJDialog_okButton_actionAdapter(this));
        cancelButton.addActionListener(new
                                       SelectTechnicsTypeJDialog_cancelButton_actionAdapter(this));

    }


    /**
     * ���ѡ��Ĺ�������
     * @return CodingIfc ѡ��Ĺ�������
     */
    public CodingIfc getCoding()
    {
        return code;
    }
  	//CCBegin SS1
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
  	//CCEnd SS1

    /**
     * �Ӵ�������л�ȡ��������,�����б���
     */
    void addCoding()
    {
        try
        {

            Class[] paraclass =
                    {
                    String.class, String.class};
            Object[] paraobj =
                    {
                    "QMTechnicsMaster", "technicsType"};
            Collection c = (Collection) CappClientHelper.useServiceMethod(
                    "CodingManageService", "findCoding", paraclass, paraobj);
            
            if (c != null && c.size() != 0)
            {
 //CCBegin SS1
            	try {
					if(getUserFromCompany().equals("zczx")){
						Vector zc = new Vector();
		            	for(Iterator ite = c.iterator();ite.hasNext();){
		            		CodingInfo code = (CodingInfo)ite.next();
		            		if(code.getCodeContent().contains("���")){
		            			zc.add(code);
		            		}
		            	}
		            	  list.setListData((Vector) zc);
					}
					//CCBegin SS2
					else if(getUserFromCompany().equals("ct")){
						Vector zc = new Vector();
		            	for(Iterator ite = c.iterator();ite.hasNext();){
		            		CodingInfo code = (CodingInfo)ite.next();
		            		if(code.getCodeContent().contains("����")){
		            			zc.add(code);
		            		}
		            	}
		            	  list.setListData((Vector) zc);
					}
					//CCEnd SS2
					else{

		                list.setListData((Vector) c);
					}
				} catch (QMException e) {
					e.printStackTrace();
				}

            }
//CCEnd SS1
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }
    }

    public void windowClosing(WindowEvent e)
    {
        code = null;
    }


    /**
     *����Դ�ļ����б��س��Ի�
     */
    protected void localize()
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "OkJButton", null);
        okButton.setText("ȷ��(Y)");
        String str2 = QMMessage.getLocalizedMessage(RESOURCE, "CancelJButton", null);
        cancelButton.setText("ȡ��(C)");
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
     * �����Ƿ���ʾ����
     * @param flag boolean �Ƿ���ʾ����
     */
    public void setVisible(boolean flag)
    {
        if (flag)
        {
            setSize(400, 300);
            setViewLocation();
            super.setVisible(true);
        }
        else
        {
            super.setVisible(false);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            System.setProperty("swing.useSystemFontSettings", "0");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String sid = null;
        try
        {
            sid = RequestServer.getSessionID("localhost", "7001",
                                             "Administrator",
                                             "weblogic");
        }
        catch (QMRemoteException ex1)
        {
            ex1.printStackTrace();
        }

        CappClientRequestServer server = new CappClientRequestServer(
                "localhost",
                "7001", sid);
        RequestServerFactory.setRequestServer(server);

        SelectTechnicsTypeJDialog dialog = new SelectTechnicsTypeJDialog(null);
        dialog.setVisible(true);
    }

    void okButton_actionPerformed(ActionEvent e)
    {
        if (parentFrame instanceof TechnicsRegulationsMainJFrame)
            ((TechnicsRegulationsMainJFrame) parentFrame).closeContentPanel();
        else
        if (parentFrame instanceof ModelTechnicsMainJFrame)
            ((ModelTechnicsMainJFrame) parentFrame).closeContentPanel();
        code = (CodingIfc) list.getSelectedValue();
        setVisible(false);
    }

    void cancelButton_actionPerformed(ActionEvent e)
    {
        code = null;
        setVisible(false);
    }

}


class SelectTechnicsTypeJDialog_okButton_actionAdapter implements java.awt.
        event.ActionListener
{
    SelectTechnicsTypeJDialog adaptee;

    SelectTechnicsTypeJDialog_okButton_actionAdapter(SelectTechnicsTypeJDialog
            adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okButton_actionPerformed(e);
    }
}


class SelectTechnicsTypeJDialog_cancelButton_actionAdapter implements java.awt.
        event.ActionListener
{
    SelectTechnicsTypeJDialog adaptee;

    SelectTechnicsTypeJDialog_cancelButton_actionAdapter(
            SelectTechnicsTypeJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelButton_actionPerformed(e);
    }
}

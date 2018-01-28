/** ���ɳ���ʱ�� 2003/08/11
 * �����ļ����� ToolSelectedDialog.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/05/27  �촺Ӣ  �μ�DefectID=2130
 * CR2 2009/07/15  ��ѧ��  ԭ�򣺴�����ͼ������ѡȡ�����ļ���ͼ�Ի���
 *                               ��ʹ���������뷨����ѡ���ļ�����ʱ�޷���������
 *                         ���������ӶԻ���ĸ�����
 *                         ��ע���μ�DefectID=2569      
 * CCBegin by liunan 2012-04-23 �򲹶�v4r3_p044
 * CR4 2012/03/23 ������ ԭ�򣺲μ�TD����2503
 * CCEnd by liunan 2012-04-23
 * SS1 A004-2015-3193 ���鿴��ͼĬ�����ϼ���C�̸�ΪD�� liunan 2015-9-18
 */
package com.faw_qm.cappclients.beans.drawingpanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;

//CCBegin by liunan 2009-09-14
import com.faw_qm.resource.support.model.DrawingIfc;
import com.faw_qm.capp.model.PDrawingIfc;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.exceptions.QMException;
//CCEnd by liunan 2009-09-14

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.capp.exception.CappException;
import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.capp.util.WorkPathUtil; 

/**
 * <p>Title: ѡ��༭��ͼ���߲��༭��ͼ�Ľ�����</p>
 * <p>Description:ѡ��༭��ͼ���߲��༭��ͼ�Ľ����� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author �ܴ�Ա
 * @version 1.0
 * ��1��20060921Ѧ���޸ģ��޸ķ���setCadPath(),��ѡ���˳������򷵻�
 * ��2��20061128�����޸ģ��޸ķ���setCadPath()�������ж�����if (cadTrace.exists())������trueʱ����ѡ��CAD·����
 * ��3��2008.11.19 ��־���޸� �޸�ԭ���ϴ���ͼʱ����û���ϴ���ͼ�ļ����ͼ����Ϊ0��Ӧ��ʾ��ͼ�ļ������ڡ�
 */

public class ToolSelectedDialog extends JDialog
{
    private JPanel panel1 = new JPanel();
    private JRadioButton cadRadioButton = new JRadioButton();
    private JRadioButton proeRadioButton = new JRadioButton();
    private JRadioButton picRadioButton = new JRadioButton();
    private JLabel selectLabel = new JLabel();
    private JTextField pathField = new JTextField();
    private JButton browseButton = new JButton();
    private JButton OKButton = new JButton();
    private JButton jButton1 = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private ButtonGroup bg = new ButtonGroup();
    private DrawingPanel parentPanel;
    private Vector drawingDate;
    public static String cadExePath;
    private Vector actionListeners = new Vector();
    private static String picPath;


    //bean�Ĺ���ģʽ��ʶ
    private int model = 2;


    //�鿴ģʽ
    public static int VIEW = 1;


    //�༭ģʽ
    public static int EDIT = 2;

    //Begin CR4
    //��鰴ť��Ĭ�ϱ�ʾֵ
    boolean checkButtonFlag=false;
    //End CR4
    
    public static ResourceBundle rb = ResourceBundle.getBundle(
            "com.faw_qm.cappclients.beans.drawingpanel.DrawingPanelRB",
            RemoteProperty.getVersionLocale());
//    private static String setPath=RemoteProperty.getProperty("qmpdm.UserHome");
//    private static String tempPath=RemoteProperty.getProperty("qmpdm.TempDir");

    /**
     * ����cad�״�����·�����ļ�
     */
    public static String saveFilePath =WorkPathUtil.getSetPath(RemoteProperty.getProperty("com.faw_qm.cappclients.beans.drawingpanel.cad"));
    //public static String saveFilePath = WorkPathUtil.getSetPath("\\capp\\capp\\cadTemp.txt");
//    setPath.endsWith(File.separator)?setPath:setPath+File.separator+"capp\\capp\\cadTemp.txt";



    /**
     * ����dxf�ļ�ʱ���ɱ����ļ���ȫ·��
     */
    //public static String tempDxfPath = WorkPathUtil.getTempPath(RemoteProperty.getProperty("com.faw_qm.cappclients.beans.drawingpanel.creatdxf"));



     //2008.05.29 ��־���޸� �޸�ԭ�����ڱ��������ļ���ȡ��Ӧ��Ϊ������Դ�ļ���ȡ��
     //public static String tempDxfPath = "c:\\_te_eo%fs$ie@o$%#.dxf";
     public static String tempDxfPath = rb.getString("tempDxfPath");

    /**
     * �༭dxf��ʽ�ļ�ͼʱ���ɱ����ļ���·��
     */
  //  public static String tempEditDxfPath = "c:\\_te_eo%fs$ie@o$%#edit.dxf";


    /**
     * �鿴dxf��ʽ�ļ�ͼʱ���ɱ����ļ���·��
     */
//    public static String tempViewDxfPath = WorkPathUtil.getTempPath(RemoteProperty.getProperty("com.faw_qm.cappclients.beans.drawingpanel.viewdxf"));

    //2008.05.29 ��־���޸� �޸�ԭ�����ڱ��������ļ���ȡ��Ӧ��Ϊ������Դ�ļ���ȡ��
    //public static String tempViewDxfPath = "c:\\_te_eo%fs$ie@o$%#view.dxf";
    public static String tempViewDxfPath = rb.getString("tempViewDxfPath");


    /**
     * �鿴ͼƬ��ʽ��jpg,gif,bmp���ļ�ͼʱ���ɱ����ļ���·��
     */
//    public static String tempViewPicPath = WorkPathUtil.getTempPath(RemoteProperty.getProperty("com.faw_qm.cappclients.beans.drawingpanel.viewPicture"));

    //2008.05.29 ��־���޸� �޸�ԭ�����ڱ��������ļ���ȡ��Ӧ��Ϊ������Դ�ļ���ȡ��
    //CCBegin SS1
    public static String tempViewPicPath = "D:\\_te_eo%fs$ie@o$%#view";
    //public static String tempViewPicPath = rb.getString("tempViewPicPath");
    //CCEnd SS1


    /**
     * dxf��ʽ�ļ�ͼ�����ͱ�־
     */
   // public static String AUTOCAD_TYPE = "autocad";


    /**
     * ͼƬ��ʽ�ļ�ͼ�����ͱ�־
     */
   // public static String PIC_TYPE = "pic";


    /**
     * ���ص�ǰѡ������������͵ļ�ͼ
     */
   // private String type = AUTOCAD_TYPE;

    public static String AUTOCAD = "Autocad";
  public static String PROE = "Proe";
  //public static String PIC = "Pic";
  public static String GIF = "Gif";
  public static String JPG = "Jpg";
  public static String BMP = "Bmp";

    JButton editButton = new JButton();
    JTextField cadTextField = new JTextField();
//add by wangh 
    JButton checkButton = new JButton();
//add end

    /**
     * ���췽��
     * @param frame Frame ������
     * @param title String ����
     * @param modal boolean �Ƿ�����ģ̬
     */
    public ToolSelectedDialog(JFrame frame, String title, boolean modal)
    {

        super(frame, title, modal);
        //add by wangh on 20070523(�޸�����·�������ɵ���ʱ�ļ����·��)
        //System.out.println((new RemoteProperty()).getClass().getResource("RemoteProperty.class").getFile());
        //System.out.println((new RemoteProperty()).getClass().getResource("RemoteProperty.class").getPath());


        try
        {
            jbInit();
            pack();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ���ü�ͼ������
     * @param vect Vector ��ͼ������
     */
    public void setDrawingData(Vector vect)
    {
        drawingDate = vect;
        if (drawingDate != null)
        {
            String type = (String)drawingDate.elementAt(1);
            if (type.equals(AUTOCAD))
            {
                if (!rb.getString("5").equals(cadTextField.getText()))
                {
                    steTipText(rb.getString("5"));
                }

            }
        }
    }


    /**
     * �õ���ͼ������
     * @return Vector ��ͼ������
     */
    public Vector getDrawingData()
    {
        return drawingDate;
    }


    /**
     * ���췽��
     * @param parentPanel DrawingPanel ��ͼpanel
     */
    public ToolSelectedDialog(DrawingPanel parentPanel)
    {
        this(parentPanel.getParentFrame(), "", false);
        this.parentPanel = parentPanel;
        drawingDate = parentPanel.getDrawingDate();
        model = parentPanel.getModel();
        if (model == DrawingPanel.VIEW)
        {
            selectLabel.setVisible(false);
            pathField.setVisible(false);
            browseButton.setVisible(false);
        }
    }


    /**
     * ���ý���ģʽ
     * @param mode int
     */
   /* public void setMode(int mode)
    {
        model = mode;
        if (mode == VIEW)
        {
            selectLabel.setVisible(false);
            pathField.setVisible(false);
            browseButton.setVisible(false);
        }

    }*/


    /**
     * �õ�����ģʽ
     * @return int ����ģʽ
     */
    public int getMode()
    {
        return model;
    }

    private void jbInit()
            throws Exception
    {
        setFlag(false);
        proeRadioButton.setVisible(false);
        panel1.setLayout(gridBagLayout1);
        if(getTitle()==null||getTitle().equals(""))
        setTitle(rb.getString("7"));
        cadRadioButton.setText("AUTOCAD");
        proeRadioButton.setText("PROE");
        proeRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                proeRadioButton_actionPerformed(e);
            }
        });
        picRadioButton.setText(rb.getString("10"));
        picRadioButton.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                picRadioButton_stateChanged(e);
            }
        });
        picRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                picRadioButton_actionPerformed(e);
            }
        });
        selectLabel.setText(rb.getString("browse file"));
        //begin CR1
        browseButton.setMaximumSize(new Dimension(85, 23));
        browseButton.setMinimumSize(new Dimension(85, 23));
        browseButton.setPreferredSize(new Dimension(85, 23));
        //end CR1
        browseButton.setMnemonic('B');
        browseButton.setText(rb.getString("browse"));
        browseButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                browseButton_actionPerformed(e);
            }
        });
        
        //add by wangh
        checkButton.setMaximumSize(new Dimension(75, 23));
        checkButton.setMinimumSize(new Dimension(75, 23));
        checkButton.setPreferredSize(new Dimension(75, 23));
        checkButton.setMnemonic('C');
        checkButton.setText("���");
        checkButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	//Begin CR4
            	checkButtonFlag=true;
                //End CR4
            	checkButton_actionPerformed(e);
            }
        });
        //add end
        OKButton.setMaximumSize(new Dimension(75, 23));
        OKButton.setMinimumSize(new Dimension(75, 23));
        OKButton.setPreferredSize(new Dimension(75, 23));
        OKButton.setMnemonic('O');
        OKButton.setText("����(o)");
        OKButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                OKButton_actionPerformed(e);
            }
        });
        jButton1.setMaximumSize(new Dimension(75, 23));
        jButton1.setMinimumSize(new Dimension(75, 23));
        jButton1.setPreferredSize(new Dimension(75, 23));
        jButton1.setMnemonic('C');
        jButton1.setText(rb.getString("cancel"));
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                jButton1_actionPerformed(e);
            }
        });
        pathField.setMinimumSize(new Dimension(120, 22));
        pathField.setPreferredSize(new Dimension(140, 22));
        editButton.setMaximumSize(new Dimension(85, 23));
        editButton.setMinimumSize(new Dimension(85, 23));
        editButton.setPreferredSize(new Dimension(85, 23));
        editButton.setMargin(new Insets(2, 14, 2, 14));
        editButton.setMnemonic('E');
        editButton.setText("�༭(E)...");//CR1
        editButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	//Begin  CR4
                checkButtonFlag=false;
                  //End  CR4
                editButton_actionPerformed(e);
            }
        });
        getContentPane().add(panel1, null);
        bg.add(cadRadioButton);
        bg.add(proeRadioButton);
        bg.add(picRadioButton);
        cadRadioButton.setSelected(true);
        cadRadioButton.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                cadRadioButton_stateChanged(e);
            }
        });
        cadTextField.setEditable(false);
        panel1.add(cadRadioButton,      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 20, 20, 0), 21, 0));
        panel1.add(proeRadioButton, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(8, 20, 0, 0), 44, 0));
        panel1.add(picRadioButton, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(8, 20, 0, 0), 5, 0));
        
        panel1.add(pathField,  new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), 111, 0));//CR1
        panel1.add(selectLabel,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 45, 0, 0), 0, 0));
        panel1.add(browseButton,  new GridBagConstraints(2, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 3, 0, 0), 0, 0));//CR1
        panel1.add(jButton1,  new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(30, 8, 20, 8), 0, 0));
        panel1.add(OKButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(30, 8, 20, 8), 0, 0));
        panel1.add(editButton,               new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 8, 0), 0, 0));
        panel1.add(cadTextField,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    //add by wangh
        panel1.add(checkButton,     new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(30, 8, 20, 8), 0, 0));
   //add end
    
    }
    void proeRadioButton_actionPerformed(ActionEvent e)
    {

    }

    public static void main(String[] args)
    {

        try
        {
        	//CCBegin by liunan 2009-09-14
        	System.setProperty("swing.useSystemFontSettings", "0");
        	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        	if (args != null && args.length == 4) 
        	{
        		CappClientRequestServer server = new CappClientRequestServer(args[0], args[1], args[2]);
        		RequestServerFactory.setRequestServer(server);        		
        		ToolSelectedDialog selectDialog = new ToolSelectedDialog(args[3]);
        	}
        	//CCEnd by liunan  
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //CCBegin by liunan 2009-09-14
    public ToolSelectedDialog(String str)
    {
    	try
    	{
    		//CCBegin by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
    		Vector data = new Vector();
    		//CCEnd by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
    		Class[] ccc = {String.class};
    		String[] o = {str};
    		Object obj = (Object) CappRouteAction.useServiceMethod("PersistService", "refreshInfo", ccc, o);
    		System.out.println(str+"============"+obj);
    		if (obj instanceof DrawingIfc)
    		{
    			//CCBegin by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ�룬������Ʒ�˷����޸ĵ�ԭ��
//    			data = ((DrawingIfc) obj).getDrawingData();
    			data.add(((DrawingIfc) obj).getDrawingByte());
    			data.add(((DrawingIfc) obj).getDrawingStreamType());
    			//CCEnd by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
    		}
    		else if (obj instanceof PDrawingIfc)
    		{
    			//CCBegin by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
    			//data = ((PDrawingIfc) obj).getDrawingData();
    			data.add(((PDrawingIfc) obj).getDrawingByte());
    			data.add(((PDrawingIfc) obj).getDrawingType());
    			//CCEnd by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
    		}
    		else
    		{
    			//CCBegin by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
//    			data = (Vector) obj;
    			data.add(obj);
    			//CCEnd by leixiao 2009-1-26 �ݿͻ��鿴��ͼ��getDrawingData()��ָ��
    		}
    		if (data != null&&data.size()>0)
    		{
    			this.setDrawingData(data);
    			this.view();
    		}
    	}
    	catch (QMRemoteException ex) 
    	{
    		ex.printStackTrace();
    	}
    	catch (QMException e) 
    	{
    		e.printStackTrace();
    	}
    }
    //CCEnd by liunan  


    void picRadioButton_actionPerformed(ActionEvent e)
    {

    }


    /**
     * �����Ƿ�ɱ༭
     * @param flag boolean �Ƿ�ɱ༭
     */
    private void setFlag(boolean flag)
    {
        selectLabel.setEnabled(flag);
        pathField.setEditable(flag);
        browseButton.setEnabled(flag);
    }


    /**
     * ��ѡ��״̬�ı�
     * @param e ChangeEvent
     */
    void picRadioButton_stateChanged(ChangeEvent e)
    {
        if (picRadioButton.isSelected())
        {
            setFlag(true);
           // type = PIC_TYPE;
            checkButton.setEnabled(false);//CR1
        }
        else
        {
            if (cadRadioButton.isSelected())
            {
               // type = AUTOCAD_TYPE;
            }
            setFlag(false);
            checkButton.setEnabled(true);//CR1
        }
    }


    /**
     * ��ѡ��״̬�ı�
     * @param e ChangeEvent
     */
    void cadRadioButton_stateChanged(ChangeEvent e)
    {
        if (cadRadioButton.isSelected())
        {
            //type = AUTOCAD_TYPE;
            editButton.setEnabled(true);
            //cadTextField.setEnabled(true);//begin CR1
            checkButton.setEnabled(true);//end CR1
        }
        else
        {
            editButton.setEnabled(false);
            //cadTextField.setEnabled(false);//begin CR1
            checkButton.setEnabled(false);//end CR1
        }
    }

    /**
     * ����cad��·��
     */
    private void setCadPath()
    {
        //if (cadExePath != null && cadExePath.endsWith("acad.exe"))
        //{
          //  return;
        //}
        //20061128�����޸ġ��������û�������CAD�İ�װ·�����Ƿ�ѡ��CAD·�������ж�
        boolean flag=true;
        File f = new File(saveFilePath);
        if (f.exists())
        {
            try
            {
                BufferedReader breader = new BufferedReader(new FileReader(
                        saveFilePath));
                String str = breader.readLine();
                if (str != null)
                {
                    if (str.endsWith("acad.exe"))
                    {
                        cadExePath = str;
                        File cadTrace=new File(cadExePath);
                        if (cadTrace.exists())
                        {
                          flag = false;
                        }
                      System.out.print("path :"+cadExePath);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        if (flag)
        {
        //if (cadExePath == null)
        //{
            JOptionPane.showMessageDialog(this, rb.getString("8"), "cad",
                                          JOptionPane.INFORMATION_MESSAGE);
            //if(result == JOptionPane.NO_OPTION)
            //�ļ�ѡ����
            JFileChooser chooser = new JFileChooser();
            //�����ļ�ѡȡģʽ���ļ���·��
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //���ò��ɶ�ѡ
            chooser.setMultiSelectionEnabled(false);
            chooser.setCurrentDirectory(new File("C:\\"));
            ToolSelectedFilter filter = new ToolSelectedFilter();
            chooser.setFileFilter(filter);
            
           
            //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
            //begin CR2
            int state = chooser.showOpenDialog(this);
            //end CR2
            
            //���ѡ����ļ�
            File file = chooser.getSelectedFile();
            //20060921Ѧ���޸ģ���ѡ���˳������򷵻�
            if(file==null)
            return;
            if (file.getName() != null)
            {

                //���ѡ������׼��ť,������ѡ���ļ���
                if (file != null && file.getName() != null &&
                    state == JFileChooser.APPROVE_OPTION)
                {
                    String temp = file.getPath();
                    if (temp.endsWith("acad.exe"))
                    {

                        try
                        {
                            FileOutputStream myFileOut = new FileOutputStream(
                                    saveFilePath);
                            myFileOut.write(temp.getBytes());

                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        cadExePath = temp;
                    }
                }
            }
        }
    }


    /**
     * ���Ӷ����¼�����
     * @param actionlisener �����¼�����
     */
    public synchronized void addActionListener(ActionListener actionlistener)
    {
        if (actionListeners == null)
        {
            actionListeners = new Vector();
        }
        actionListeners.addElement(actionlistener);
    }


    /**
     * ��ȥ�����¼�����
     * @param actionlisener �����¼�����
     */
    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        if (actionListeners != null)
        {
            actionListeners.removeElement(actionlistener);
        }
    }


    /**
     * ֪ͨ������
     * @param awtevent AWTEvent
     */
    private void notifyActionListeners(ActionEvent awtevent)
    {
        Vector vector;
        synchronized (actionListeners)
        {
            vector = (Vector) actionListeners.clone();
        }
        for (int i = 0; i < vector.size(); i++)
        {

            Object listener = (Object) vector.elementAt(i);
            if (listener instanceof ActionListener)
            {
                //processActionEvent(listener, (ActionEvent)awtevent);
                ((ActionListener) listener).actionPerformed((ActionEvent)
                        awtevent);
            }
        }

    }
//add by wangh on 20070927(���ڽ�tif�ļ�ת��Ϊjpg�ļ�)
    private void changTIFtoJPG(String Path)throws Exception
    {
        //com.sun.jimi.core.Jimi
        java.awt.Image img=com.sun.jimi.core.Jimi.getImage(Path);//Jimi��sun��Jimi�������е��࣬�ô˷����õ���Image�������ֱ����ʾ

        //picPath="c:\\temp.jpg";
        java.io.ByteArrayOutputStream out=new java.io.ByteArrayOutputStream();

        com.sun.jimi.core.Jimi.putImage("image/jpg",img,out);//ת����jpg�ļ�
        out.flush();
        byte[] b=out.toByteArray();
        //System.out.println("size is:"+b.length);
        Vector drawingDate=new Vector();
        drawingDate.add(0, b);

         String suffix="jpg";//DrawingFileFilter.getSuffix(tempDxf);
         drawingDate.add(1, suffix);
         if (parentPanel != null)
         {
             parentPanel.setDrawingDate(drawingDate);
             parentPanel.deleteTempFile();
         }
         //inn.close();
         notifyActionListeners(new ActionEvent(this, 200,
                 "PicEvent"));
         dispose();
         if(drawingDate!=null)
             notifyActionListeners(new ActionEvent(drawingDate,0,"OK") );
           deleteTempFile();


    }

    /**
     * ȷ����ť��ִ���¼�
     * @param e ActionEvent
     * ���⣨3��2008.11.19 ��־���޸� �޸�ԭ���ϴ���ͼʱ����û���ϴ���ͼ�ļ����ͼ����Ϊ0��Ӧ��ʾ��ͼ�ļ������ڡ�
     */
    void OKButton_actionPerformed(ActionEvent e)
    {
    	//Begin CR4
        if(cadRadioButton.isSelected())
        {
            if(!checkButtonFlag)
            {
                JOptionPane.showMessageDialog(ToolSelectedDialog.this,"���ȵ����鰴ť���Ի�����¹��ռ�ͼ����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        //End CR4

        if (proeRadioButton.isSelected())
        {

        }
       else if (cadRadioButton.isSelected())
        {
    	   
        }

        else
        if (picRadioButton.isSelected())
        {

            picPath = pathField.getText();
            if(picPath.endsWith("tif")){
            	try{
            		changTIFtoJPG(picPath);
            	return;
            }
            	catch(Exception ex){
            		ex.printStackTrace();
            	}

            }
            String title = rb.getString("warning");//CR1
            if (picPath == null || picPath.equals(""))
              {
                  String picNull=rb.getString("picNull");//begin CR1
                  JOptionPane.showMessageDialog(this, picNull,title,
                          JOptionPane.WARNING_MESSAGE);//end CR1
                  return;
              }
              File tempDxf = new File(picPath);
            if (tempDxf.exists())
              {
                  //2008.11.19 ��־���޸� �޸�ԭ���ϴ���ͼʱ����û���ϴ���ͼ�ļ����ͼ����Ϊ0��Ӧ��ʾ��ͼ�ļ������ڡ�
                 
                  String drawDataNull=rb.getString("drawDataNull");
                  drawingDate = new Vector();
                  try
                  {
                      FileInputStream inn = new FileInputStream(tempDxf);
                      int fileLength1 = (int) tempDxf.length();
                      byte[] b = new byte[fileLength1];
                      //2008.11.19 ��־���޸� �޸�ԭ���ϴ���ͼʱ����û���ϴ���ͼ�ļ����ͼ����Ϊ0��Ӧ��ʾ��ͼ�ļ������ڡ�
                      if(b==null||b.length==0)
                      {
                    	  JOptionPane.showMessageDialog(null,
                    			  drawDataNull,
                                  title,
                                  JOptionPane.
                                  INFORMATION_MESSAGE);
                    	  return;
                      }
                      inn.read(b);
                      //drawingDate.add(0,null);
                      drawingDate.add(0, b);
                     //20070201Ѧ���޸ģ��޸Ļ�ĺ�׺���ķ�ʽ
                      String suffix=DrawingFileFilter.getSuffix(tempDxf);
                      drawingDate.add(1, suffix);
                      if (parentPanel != null)
                      {
                          parentPanel.setDrawingDate(drawingDate);
                          parentPanel.deleteTempFile();
                      }
                      inn.close();
                      notifyActionListeners(new ActionEvent(this, 200,
                              "PicEvent"));
                      dispose();
                  }
                  catch (IOException ioe)
                  {
                      ioe.printStackTrace();
                  }
              }
            //begin CR1
            else
            {
            	 String picIsExist=rb.getString("picExists");
                 JOptionPane.showMessageDialog(this, picIsExist,title,
                                              JOptionPane.WARNING_MESSAGE);
                 return;
            }
            //end CR1


        }
         dispose();
         if(drawingDate!=null)
           notifyActionListeners(new ActionEvent(drawingDate,0,"OK") );
         deleteTempFile();


    }

     /**
        * ɾ������ʱ�ı����ļ�
        */
       public synchronized void deleteTempFile()
       {

           File dxfFile = new File(ToolSelectedDialog.tempDxfPath);
           if (dxfFile.exists())
           {
               dxfFile.delete();
           }

       }


    /**
     * �鿴��ͼ
     */
    public void view()
    {
    	String drawSeize=rb.getString("drawSeize");
        if (drawingDate != null && drawingDate.size() > 0)
        {
            String drawingType = (String) drawingDate.elementAt(1);

            byte[] by = (byte[]) drawingDate.elementAt(0);
            if (drawingType.equals(AUTOCAD))
            {
                //��cad�鿴��ͼ
                if (by != null)
                {
                    setCadPath();
                    try
                    {
                        File jfile = new File(tempViewDxfPath);
                        FileOutputStream output1 = new FileOutputStream(
                                jfile);
                        output1.write(by);
                        output1.close();
                        //2008.11.19 ��Ϊ��cmd�򿪡�lzc
                        Runtime.getRuntime().exec("cmd /E:ON /c start " +
                                                  tempViewDxfPath);
                    }
                    catch (IOException ioe)
                    {
                    	//2008.11.20 �ٴβ鿴��ͼʱ����ر�һ�򿪵�cad��ͼ��
                        JOptionPane.showMessageDialog(this,
                        		drawSeize, "��ʾ",
                                JOptionPane.
                                INFORMATION_MESSAGE);
                        ioe.printStackTrace();
                        return;
                    }
                }
            }
            else if (drawingType.equalsIgnoreCase(BMP)||drawingType.equalsIgnoreCase(GIF)
                     ||drawingType.equalsIgnoreCase(JPG))
            {

                if (by == null)
                {
                    JOptionPane.showMessageDialog(this,
                                                  "��ͼƬ��ʽ��ͼ", "��ʾ",
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
                else
                {
                    try
                    {
                        String filetype = (String) drawingDate.elementAt(1);
                        String filepath = tempViewPicPath + "." +
                                          filetype;
                        File jfile = new File(filepath);
                        FileOutputStream output1 = new FileOutputStream(
                                jfile);
                        output1.write(by);
                        output1.close();
                        //2008.11.19 ��Ϊ��cmd�򿪡�lzc
                        Runtime.getRuntime().exec(
                        		"cmd /E:ON /c start " +
                                filepath);
                    }
                    catch (IOException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }

            }

        }

    }

    void jButton1_actionPerformed(ActionEvent e)
    {
        dispose();
    }

    void browseButton_actionPerformed(ActionEvent e)
    {
        //�ļ�ѡ����
        JFileChooser chooser = new JFileChooser();
        //�����ļ�ѡȡģʽ���ļ���·��
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //���ò��ɶ�ѡ
        chooser.setMultiSelectionEnabled(false);
        chooser.setCurrentDirectory(new File("C:\\"));

        DrawingFileFilter filter = new DrawingFileFilter();
        chooser.setFileFilter(filter);
        //20070201Ѧ�����ӣ�ȥ����������
        javax.swing.filechooser.FileFilter[]fil=chooser.getChoosableFileFilters();
        for(int i=0;i<fil.length;i++)
        {
            if(fil[i]!=filter)
                chooser.removeChoosableFileFilter(fil[i]);
        }

        //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
        int state = chooser.showOpenDialog(null);
        //���ѡ����ļ�
        File file = chooser.getSelectedFile();

        //���ѡ������׼��ť,������ѡ���ļ���
        if (file != null && state == JFileChooser.APPROVE_OPTION)
        {
            pathField.setText(file.getPath());
        }
    }

    /*public String getType()
    {
        return type;
    }*/

    public void paint(Graphics g)
    {
        super.paint(g);
        if (model == EDIT)
        {
            String s = ToolSelectedDialog.tempDxfPath;
            File tempDxf = new File(s);
            boolean tempISNO = tempDxf.exists();
            if (tempISNO)
            {
                drawingDate = new Vector();
                try
                {
                    FileInputStream inn = new FileInputStream(tempDxf);
                    int fileLength1 = (int) tempDxf.length();
                    byte[] b = new byte[fileLength1];
                    inn.read(b);
                    drawingDate.add(0, b);
                    drawingDate.add(1, AUTOCAD);
                    if (!rb.getString("5").equals(cadTextField.getText()))
                    {
                        steTipText(rb.getString("5"));
                    }
                    inn.close();
               }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void steTipText(String text)
    {
        cadTextField.setText(text);

    }


    void editButton_actionPerformed(ActionEvent e)
    {
        if (cadRadioButton.isSelected())
         {
             setCadPath();
             if (cadExePath == null)
             {
                 JOptionPane.showMessageDialog(this, rb.getString("9"));
                 return;
             }
             if(drawingDate != null)
             {
	             if (drawingDate.elementAt(1).equals(AUTOCAD))
	             {
	                 byte[] by = (byte[]) drawingDate.elementAt(0);
	                 //��cad�鿴��ͼ
	                 if (by != null)
	                 {
	                     try
	                     {
	                         File jfile = new File(tempDxfPath);
	                         FileOutputStream output1 = new FileOutputStream(jfile);
	                         output1.write(by);
	                         output1.close();
	                         Runtime.getRuntime().exec(cadExePath + " " +
	                                                   tempDxfPath);
	                     }
	                     catch (IOException ioe)
	                     {
	                         ioe.printStackTrace();
	                     }
	                 }
	                 //��cad
	                 else
	                 {
	                     try
	                     {
	                         Runtime.getRuntime().exec(cadExePath);
	                     }
	                     catch (IOException ioe)
	                     {
	                         ioe.printStackTrace();
	                     }
	                 }
	             }
	             //��drawingDate��Ϊjpg�����µ�cad.exe��
	             else
	             {
	            	 try
	                 {
	                     Runtime.getRuntime().exec(cadExePath);
	                 }
	                 catch (IOException ioe)
	                 {
	                     ioe.printStackTrace();
	                 }
	             }
             }
             else
             {
             //��cad
	             try
	             {
	            	 Runtime.getRuntime().exec(cadExePath);
	//                     Process p=Runtime.getRuntime().exec(cadExePath);
	//                     p.waitFor();
	//                     this.repaint();
	             }
	             catch (Exception ioe)
	             {
	                 ioe.printStackTrace();
	             }
             }
             //dispose();
         }
    }

    /**
     * ���������Ϣ
     */
    public void clear()
    {
        cadTextField.setText(null);
        pathField.setText(null);
        drawingDate=null;

    }
    public void setVisible(boolean flag)
    {
        if(flag)
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().
                                   getScreenSize();
            this.setSize(500,400);
            setLocation((int) (screenSize.getWidth() - getWidth()) /
                            2,
                            (int) (screenSize.getHeight() - getHeight()) /
                            2);


        }
          super.setVisible(flag);
    }
    void checkButton_actionPerformed(ActionEvent e){
    	repaint();
    }

}
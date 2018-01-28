/** ���ɳ���NewLogicBomFrame.java	1.1  2007-2-8
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * SS1 ������׼˵������׼״̬ liunan 2015-12-3
  */
package com.faw_qm.part.client.other.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;

/**
 *
********�߼��ܳ���������************
***************************************
** ��һ����Ϊ�߼��ܳ�ʱ,
**�Զ��������������һ���߼��ܳ�������˼����ʵ��װ������
**leix 2010-12-21
 * @version 2.0
 */

public class NewLogicBomFrame extends JFrame
{

  /**�����*/
  private JPanel contentPane;
  /**������ʾ����������*/
  private JPanel exportJPanel = new JPanel();
  /**�������*/
  private JPanel actionJPanel = new JPanel(); /**���ư�ť���*/
  private JPanel buttonJPanel = new JPanel(); /**״̬��*/
  private JLabel statusJLabel = new JLabel();
  /**ȷ����ť*/
  private JButton okJButton = new JButton();
  /**ȡ����ť*/
  private JButton cancelJButton = new JButton();

  private TitledBorder titledBorder1;
  /**����������*/
  private ExportAttributesJPanel exportAttributeJPanel ;
  /**��Դ�ļ�·��*/
//CCBegin by leixiao 2009-12-10   ��Դ�ļ���ʽ���ԣ��Ҳ���
//  private static String RESOURCE = "com/faw_qm/part/client/other/util/OtherRB";
  private static String RESOURCE =
          "com.faw_qm.part.client.other.util.OtherRB";
//CCEnd by leixiao 2009-12-10  
  
  /**�㲿������ӿ�*/
  private QMPartIfc partIfc;
  /**���ò����Ĺ�����*/
  private HashMap hashMap = new HashMap();
  /**���ڴ������*/
  private static boolean verbose=(RemoteProperty.getProperty(
                             "com.faw_qm.part.verbose","true")).equals("true");
  /**���沼�ֹ�����*/
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton exportButton = new JButton();
  //CCBegin SS1
  private JCheckBox hasRouteDes; //�Ƿ���ʾ��׼˵������
  //CCEnd SS1

  /**
   * ���캯��
   * @param c  ������
   * @param part  �㲿������ӿ�
   */
  public NewLogicBomFrame(QMPartIfc part,boolean bool)
  {
    try
    {
      this.partIfc = part;
      jbInit();
      //���ý��涯̬����
      if(partIfc!=null)
      {
        this.setTitle("�߼��ܳ���������" + partIfc.getPartNumber()+" "+partIfc.getPartName());
        String iconImage = QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB", QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
        PartScreenParameter.setLocationCenter(this);
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * ��ʼ��
   * @throws Exception
   */
  private void jbInit() throws Exception
  {

	    //Button
	    cancelJButton.setMaximumSize(new Dimension(65, 23));
	    cancelJButton.setMinimumSize(new Dimension(65, 23));
	    cancelJButton.setPreferredSize(new Dimension(65, 23));
	    okJButton.setMaximumSize(new Dimension(65, 23));
	    okJButton.setMinimumSize(new Dimension(65, 23));
	    okJButton.setPreferredSize(new Dimension(65, 23));
	    okJButton.setText("ȷ��");
	    okJButton.addActionListener(new NewLogicBomFrame_okJButton_actionAdapter(this));
	    cancelJButton.setText("ȡ��"); //JLabel
	    cancelJButton.addActionListener(new
	                                    NewLogicBomFrame_cancelJButton_actionAdapter(this));
	    contentPane = (JPanel) this.getContentPane();
//	    titledBorder1 = new TitledBorder(QMMessage.getLocalizedMessage(RESOURCE, "out" , null));
	    titledBorder1 = new TitledBorder("���(��� :���������csv��ʽ  ȷ��:�������ҳ��ʽ)");
	    contentPane.setLayout(gridBagLayout1);
	    this.setSize(new Dimension(400, 200));
	    actionJPanel.setBorder(titledBorder1);
	    
	    //CCBegin SS1
	    hasRouteDes = new JCheckBox("��ʾ��׼˵��");
	    hasRouteDes.setSelected(false);
	    //CCEnd SS1

	    //���沼��
	    actionJPanel.setLayout(gridBagLayout3);
	    buttonJPanel.setLayout(gridBagLayout4);
	    exportJPanel.setLayout(gridBagLayout2);
	    exportButton.setMaximumSize(new Dimension(65, 23));
	    exportButton.setMinimumSize(new Dimension(65, 23));
	    exportButton.setPreferredSize(new Dimension(65, 23));
	    exportButton.setSelectedIcon(null);
	    exportButton.setText("���");
	    exportButton.addActionListener(new NewLogicBomFrame_exportButton_actionAdapter(this));
	    //CCBegin SS1
	    contentPane.add(hasRouteDes,       new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
	    contentPane.add(actionJPanel,       new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
	    //CCEnd SS1
	    actionJPanel.add(buttonJPanel,        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
	    buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
	    buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 8), 0, 0));
	    buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
//	    contentPane.add(statusJLabel,    new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
//	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));


  }

  /**
   * ����Excel
   * @param e ActionEvent
   */
  public void exportButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
//    map.put("attributeName", getAttribute());
//    map.put("attributeName1", getAttribute1());
    //CCBegin by liunan 2011-08-08 �����������״̬
    //map.put("attributeName", "���������������·��װ��·����׼���");
    //map.put("attributeName1", "partNumber-0,partName-0,quantity-0,����·��-2,װ��·��-2,��׼���-2,");
    //CCBegin SS1
    if(hasRouteDes.isSelected())
    {
    	map.put("attributeName", "�������������������״̬����·��װ��·����׼�����׼˵����׼״̬");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,����·��-2,װ��·��-2,��׼���-2,��׼˵��-2,��׼״̬-2,");
    }
    else
    {
    	map.put("attributeName", "�������������������״̬����·��װ��·����׼���");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,����·��-2,װ��·��-2,��׼���-2,");
    }
    //CCEnd SS1
    //CCEnd by liunan 2011-08-08
    writeClassfyProcess(map);
    this.setVisible(false);
  }

    private void writeClassfyProcess(HashMap map) {
      String path = getSelectedPath();
      if (path == null) {
        return;
      }
      try {
        setCursor(Cursor.WAIT_CURSOR);
        Class[] c = {
            HashMap.class};
        Object[] objs = {
            map};
        String returnStr = (String) IBAUtility.invokeServiceMethod(
            "StandardPartService", "getExportBOMClassfiyLogicString", c, objs);
        FileWriter fw = new FileWriter(path, false);
        fw.write(returnStr);
        fw.close();
        setCursor(Cursor.getDefaultCursor());
       
      }
      catch (QMRemoteException ex) {
        setCursor(Cursor.getDefaultCursor());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                                      ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "information", null),
                                      JOptionPane.INFORMATION_MESSAGE);
      }
      catch (IOException ex1) {
        setCursor(Cursor.getDefaultCursor());
        ex1.printStackTrace();
      }
  }

  /**
   * ���ɷּ������嵥��ȥ���߼��ܳɣ�
   * @param e ActionEvent
   */
  public void okJButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
    //CCBegin by liunan 2011-08-08 �����������״̬
    //map.put("attributeName", "���������������·��װ��·����׼���");
    //map.put("attributeName1", "partNumber-0,partName-0,quantity-0,����·��-2,װ��·��-2,��׼���-2,");
    //CCBegin SS1
    if(hasRouteDes.isSelected())
    {
    	map.put("attributeName", "�������������������״̬����·��װ��·����׼�����׼˵����׼״̬");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,����·��-2,װ��·��-2,��׼���-2,��׼˵��-2,��׼״̬-2,");
    }
    else
    {
    	map.put("attributeName", "�������������������״̬����·��װ��·����׼���");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,����·��-2,װ��·��-2,��׼���-2,");
    }
    //CCEnd SS1
    //CCEnd by liunan 2011-08-08
    RichToThinUtil.toWebPage("Part-Other-classifylisting-logic-001.screen", map);
    this.setVisible(false);
  }

  public void cancelJButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
  private String getSelectedPath() {
    FileFilter filter;
    File selectedFile = null;
    setCursor(Cursor.WAIT_CURSOR);
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    chooser.setMultiSelectionEnabled(false);
    filter = new TXTFileFilter();
    if (selectedFile != null) {
      chooser.setSelectedFile(selectedFile);
    }
    chooser.setDialogTitle("����嵥��...");
    chooser.setFileFilter(filter);
    //ɾ��ϵͳ�Դ���AcceptAllFileFilter
    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
    //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
    setCursor(Cursor.getDefaultCursor());
    int state = chooser.showSaveDialog(null);
    if (state == chooser.CANCEL_OPTION) {
      this.setVisible(false);
    }
    //���ѡ����ļ�
    selectedFile = chooser.getSelectedFile();
    //���ѡ������׼��ť,������ѡ���ļ���
    if (selectedFile != null && state == JFileChooser.APPROVE_OPTION) {
      //�ļ���ʽת��
      selectedFile = this.translateFile(selectedFile, filter);

      //�ж� 1 δ�����ļ���,�������ļ�����  2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
      if (!filter.accept(selectedFile)) {
        JOptionPane.showMessageDialog(this,
                                      "·��������!",
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "error", null),
                                      JOptionPane.ERROR_MESSAGE);
        return null;
      }
      if (selectedFile.exists()) {
        JOptionPane.showMessageDialog(this, "�ļ��Ѿ����ڣ�",
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "warning", null),
                                      JOptionPane.WARNING_MESSAGE);
        return null;
      }
    }

    return selectedFile.getPath();
  }

  private File translateFile(File file, FileFilter filter) {
    String path = file.getPath();
    if (!path.endsWith(".csv")) {
      path = path + ".csv";
    }
    return new File(path);
  }

  public class TXTFileFilter
      extends FileFilter {

    /**
     * �����ı��ļ�������
     */
    public TXTFileFilter() {
    }

    /**
     * �ж�ָ�����ļ��Ƿ񱻱�����������
     * @param f �ļ�
     * @return ������ܣ��򷵻�true
     */
    public boolean accept(File f) {
      boolean accept = f.isDirectory();
      if (!accept) {
        String suffix = getSuffix(f);
        if (suffix != null) {
          accept = suffix.equals("csv");
        }
      }
      return accept;
    }

    /**
     * ��ñ���������������Ϣ
     * @return Text Files(*.csv)
     */
    public String getDescription() {
      return "Text Files(*.csv)";
    }

    /**
     * ���ָ���ļ��ĺ�׺
     * @param f File
     * @return �ļ��ĺ�׺
     */
    private String getSuffix(File f) {
      String s = f.getPath(), suffix = null;
      int i = s.lastIndexOf('.');
      if (i > 0 && i < s.length() - 1) {
        suffix = s.substring(i + 1).toLowerCase();
      }
      return suffix;
    }
  }
}

class NewLogicBomFrame_cancelJButton_actionAdapter
    implements ActionListener {
  private NewLogicBomFrame adaptee;
  NewLogicBomFrame_cancelJButton_actionAdapter(NewLogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class NewLogicBomFrame_okJButton_actionAdapter
    implements ActionListener {
  private NewLogicBomFrame adaptee;
  NewLogicBomFrame_okJButton_actionAdapter(NewLogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class NewLogicBomFrame_exportButton_actionAdapter
    implements ActionListener {
  private NewLogicBomFrame adaptee;
  NewLogicBomFrame_exportButton_actionAdapter(NewLogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exportButton_actionPerformed(e);
  }
}

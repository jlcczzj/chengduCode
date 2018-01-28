/** ���ɳ���NewSubCompBomJFrame.java	1.1  2007-2-8
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.part.client.other.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import com.faw_qm.cappclients.capp.view.CappSortingSelectedPanel;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;

/**
 *
********���ӹ�˾�����嵥************
*@author liuyang
 * @version 2.0
 */

public class NewSubCompBomJFrame extends JFrame
{

  /**�����*/
  private JPanel contentPane;
  /**�������*/
  private JPanel actionJPanel = new JPanel(); /**���ư�ť���*/
  private JPanel buttonJPanel = new JPanel(); /**״̬��*/
  private JTextField field1 = new JTextField();
  private JTextField field2 = new JTextField();
  private JButton viewButton1 = new JButton("���");
  private JButton viewButton2 = new JButton("���");
  
  /**ȷ����ť*/
  private JButton okJButton = new JButton();
  /**ȡ����ť*/
  private JButton cancelJButton = new JButton();


  private JLabel makeJLabel = new JLabel();

  private JLabel constructJLabel = new JLabel();
  private CappSortingSelectedPanel makeSelectPanel;

  private CappSortingSelectedPanel contructSelectPanel;
  /**����������*/
  private ExportAttributesJPanel exportAttributeJPanel ;
  /**��Դ�ļ�·��*/
  private static String RESOURCE =
          "com.faw_qm.part.client.other.util.OtherRB";
  
  /**�㲿������ӿ�*/
  private QMPartIfc partIfc;
  /**���ò����Ĺ�����*/
  private HashMap hashMap = new HashMap();
  /**���ڴ������*/
  private static boolean verbose=(RemoteProperty.getProperty(
                             "com.faw_qm.part.verbose","true")).equals("true");

  JButton exportButton = new JButton();

  /**
   * ���캯��
   * @param c  ������
   * @param part  �㲿������ӿ�
   */
  public NewSubCompBomJFrame(QMPartIfc part,boolean bool)
  {
    setAlwaysOnTop(true);
    try
    {
      this.partIfc = part;
      jbInit();
      //���ý��涯̬����
      if(partIfc!=null)
      {
        this.setTitle("��·�ߵ�λ����");
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
	    makeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        makeJLabel.setText("���쵥λ");
        constructJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        constructJLabel.setText("װ�䵥λ");

	    //Button
        viewButton1.setMaximumSize(new Dimension(65, 23));
        viewButton1.setMinimumSize(new Dimension(65, 23));
        viewButton1.setPreferredSize(new Dimension(65, 23));
        viewButton2.setMaximumSize(new Dimension(65, 23));
        viewButton2.setMinimumSize(new Dimension(65, 23));
        viewButton2.setPreferredSize(new Dimension(65, 23));
	    cancelJButton.setMaximumSize(new Dimension(65, 23));
	    cancelJButton.setMinimumSize(new Dimension(65, 23));
	    cancelJButton.setPreferredSize(new Dimension(65, 23));
	    okJButton.setMaximumSize(new Dimension(65, 23));
	    okJButton.setMinimumSize(new Dimension(65, 23));
	    okJButton.setPreferredSize(new Dimension(65, 23));
	    okJButton.setText("ȷ��");
	    okJButton.addActionListener(new NewSubCompBomJFrame_okJButton_actionAdapter(this));
	    cancelJButton.setText("ȡ��"); //JLabel
	    cancelJButton.addActionListener(new
	    		NewSubCompBomJFrame_cancelJButton_actionAdapter(this));
	    contentPane = (JPanel) this.getContentPane();
	    contentPane.setLayout(new GridBagLayout());
	    this.setSize(new Dimension(400, 200));
    
	    //���沼��
	    actionJPanel.setLayout(new GridBagLayout());
	    buttonJPanel.setLayout(new GridBagLayout());
	    exportButton.setMaximumSize(new Dimension(65, 23));
	    exportButton.setMinimumSize(new Dimension(65, 23));
	    exportButton.setPreferredSize(new Dimension(65, 23));
	    exportButton.setSelectedIcon(null);
	    exportButton.setText("���");
	    exportButton.addActionListener(new NewSubCompBomJFrame_exportButton_actionAdapter(this));
	    
	    contentPane.add(actionJPanel,       new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
	    makeSelectPanel = new CappSortingSelectedPanel("��λ", "TechnicsRouteList", "routeListDepartment");
        makeSelectPanel.setDialogTitle("���쵥λ");
        makeSelectPanel.setIsSelectCC(true);
        makeSelectPanel.setSelectBMnemonic('M');
        contructSelectPanel = new CappSortingSelectedPanel("��λ", "TechnicsRouteList", "routeListDepartment");
        contructSelectPanel.setDialogTitle("װ�䵥λ");
        contructSelectPanel.setIsSelectCC(true);
	    actionJPanel.add(makeJLabel,       new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 5), 0, 0));
	    actionJPanel.add(constructJLabel,       new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 5), 0, 0));
	    actionJPanel.add(makeSelectPanel,       new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 5), 0, 0));
	    actionJPanel.add(contructSelectPanel,       new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 5), 0, 0));
	    
	    contentPane.add(buttonJPanel,       new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
	    buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10, 10, 10, 5), 0, 0));
	    buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 10, 5), 0, 0));
	    buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 5, 10, 10), 0, 0));
  }

  /**
   * ����Excel
   * @param e ActionEvent
   */
  public void exportButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
    map.put("attributeName", "������������汾����·��װ��·��");
    map.put("attributeName1", "partNumber-0,partName-0,quantity-0,versionValue-0,����·��-2,װ��·��-2,");
	String  makeV="";
	if( makeSelectPanel.getCoding()!=null){
        makeV = makeSelectPanel.getCoding().getBsoID();  
	}
	String conV=""; 
	if(contructSelectPanel.getCoding()!=null){
       conV = contructSelectPanel.getCoding().getBsoID();  
	}
	map.put("source","");
	map.put("type","");
	map.put("makeV", makeV);
    map.put("conV", conV);
    this.setVisible(false);
    writeClassfyProcess(map);
  
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
            "StandardPartService", "getExportBOMStatisticsString", c, objs);
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
   * ���ɷ��ӹ�˾�����嵥
   * @param e ActionEvent
   */
  public void okJButton_actionPerformed(ActionEvent e) {
	String  makeV="";
	if( makeSelectPanel.getCoding()!=null){
        makeV = makeSelectPanel.getCoding().getBsoID();  
	}
	String conV=""; 
	if(contructSelectPanel.getCoding()!=null){
       conV = contructSelectPanel.getCoding().getBsoID();  
	}
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
    map.put("attributeName", "������������汾����·��װ��·��");
    map.put("attributeName1", "partNumber-0,partName-0,quantity-0,versionValue-0,����·��-2,װ��·��-2,");
    map.put("source","");
    map.put("type","");
    map.put("makeV", makeV);
    map.put("conV", conV);
    RichToThinUtil.toWebPage("Part-Other-SubCompBom.screen", map);
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

class NewSubCompBomJFrame_cancelJButton_actionAdapter
    implements ActionListener {
  private NewSubCompBomJFrame adaptee;
  NewSubCompBomJFrame_cancelJButton_actionAdapter(NewSubCompBomJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class NewSubCompBomJFrame_okJButton_actionAdapter
    implements ActionListener {
  private NewSubCompBomJFrame adaptee;
  NewSubCompBomJFrame_okJButton_actionAdapter(NewSubCompBomJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class NewSubCompBomJFrame_exportButton_actionAdapter
    implements ActionListener {
  private NewSubCompBomJFrame adaptee;
  NewSubCompBomJFrame_exportButton_actionAdapter(NewSubCompBomJFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exportButton_actionPerformed(e);
  }
  
  public static void main(String args[])
  {
	  new NewSubCompBomJFrame(null,false);
  }
}

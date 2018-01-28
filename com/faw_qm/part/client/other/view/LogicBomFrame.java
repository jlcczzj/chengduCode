/** ���ɳ���LogicBomFrame.java	1.1  2007-2-8
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.part.client.other.view;

import javax.swing.border.TitledBorder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Vector;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;

import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.view.EffClientViewIfc;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.clients.widgets.IBAUtility;
import java.io.IOException;
import java.io.FileWriter;
import com.faw_qm.part.client.other.controller.MaterialController;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <p>Title: �޺ϼ����߼��ܳɣ�װ���</p>
 * ���ݽ�Ź�˾������ӹ���
 * ����ϼ�װ���ʱ�����߼��ܳ�ȥ�������߼��ܳɵ��Ӽ��ᵽ�߼��ܳɵ�λ�ã��������ʱ�㲿����
 * Ϊ������׼���㲿������һ�£������ʽ��Ϊ�㲿��+��/��+�汾��
 * <p>�û�������Ҫ��ѡ����Ʒ�򲿼�����BOM���ݺ������ʽ�����BOM��<p>
 * <p>���������嵥ʱ������ǰ��Ʒ�ṹ�����������ù淶ȷ���㲿��ʹ�ýṹ��<p>
 * <p>�Ӽ�ʹ�õİ汾������Ӽ���û�з��ϵ�ǰ���ù淶�İ汾����ü�������<p>
 * <p>�������嵥�С�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 2.0
 */

public class LogicBomFrame extends JFrame
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

  /**
   * ���캯��
   * @param c  ������
   * @param part  �㲿������ӿ�
   */
  public LogicBomFrame(QMPartIfc part,boolean bool)
  {
    try
    {
      this.partIfc = part;
      //ʵ��������������
      exportAttributeJPanel = new ExportAttributesJPanel(bool);//add by liun
      jbInit();
      //���ý��涯̬����
      if(partIfc!=null)
      {
        this.setTitle("�޺ϼ�װ���" + partIfc.getPartNumber()+" "+partIfc.getPartName());
        String iconImage = QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB", QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
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
    okJButton.addActionListener(new LogicBomFrame_okJButton_actionAdapter(this));
    cancelJButton.setText("ȡ��"); //JLabel
    cancelJButton.addActionListener(new
                                    LogicBomFrame_cancelJButton_actionAdapter(this));
    statusJLabel.setText("  ");
    statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    statusJLabel.setDoubleBuffered(true);
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder(QMMessage.getLocalizedMessage(RESOURCE, "out" , null));
    contentPane.setLayout(gridBagLayout1);
    this.setSize(new Dimension(650, 500));
    actionJPanel.setBorder(titledBorder1);

    //���沼��
    actionJPanel.setLayout(gridBagLayout3);
    buttonJPanel.setLayout(gridBagLayout4);
    exportJPanel.setLayout(gridBagLayout2);
    exportButton.setMaximumSize(new Dimension(65, 23));
    exportButton.setMinimumSize(new Dimension(65, 23));
    exportButton.setPreferredSize(new Dimension(65, 23));
    exportButton.setSelectedIcon(null);
    exportButton.setText("���");
    exportButton.addActionListener(new LogicBomFrame_exportButton_actionAdapter(this));
    contentPane.add(exportJPanel,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    exportJPanel.add(exportAttributeJPanel,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    contentPane.add(actionJPanel,       new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 10, 10), 0, 0));
    actionJPanel.add(buttonJPanel,        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 8), 0, 0));
    buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    contentPane.add(statusJLabel,    new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

  }


  /**
   * ��ȡ����������Ե��ַ���,������ʾ
   * @return ����һ���ַ���
   */
  public String getAttribute()
  {
    return exportAttributeJPanel.getAttribute()[0];
  }

  /**
   * ��ȡ����������Ե��ַ���������������
   * @return ����һ���ַ���
   */
  public String getAttribute1()
  {
    if(verbose)
      System.out.println(">>>>>>>>>>>>>>>>> END :"+exportAttributeJPanel.getAttribute()[1]);
    return exportAttributeJPanel.getAttribute()[1];
  }

  /**
   * ����״̬��
   */
  public void setStatusBar()
  {
    String s = exportAttributeJPanel.getAttribute()[1];
    statusJLabel.setText(s);
  }

  /**
   * ����Excel
   * @param e ActionEvent
   */
  public void exportButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
    map.put("attributeName", getAttribute());
    map.put("attributeName1", getAttribute1());
    writeClassfyProcess(map);
  }

  //��������ӣ��Էּ���ʽ���BOM�������ļ�
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
            "StandardPartService", "getExportBOMClassfiyString2", c, objs);
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
    map.put("attributeName", getAttribute());
    map.put("attributeName1", getAttribute1());
    RichToThinUtil.toWebPage("Part-Other-classifylisting-001-2.screen", map);
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

class LogicBomFrame_cancelJButton_actionAdapter
    implements ActionListener {
  private LogicBomFrame adaptee;
  LogicBomFrame_cancelJButton_actionAdapter(LogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class LogicBomFrame_okJButton_actionAdapter
    implements ActionListener {
  private LogicBomFrame adaptee;
  LogicBomFrame_okJButton_actionAdapter(LogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class LogicBomFrame_exportButton_actionAdapter
    implements ActionListener {
  private LogicBomFrame adaptee;
  LogicBomFrame_exportButton_actionAdapter(LogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exportButton_actionPerformed(e);
  }
}

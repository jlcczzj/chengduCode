package com.faw_qm.cappclients.capproute.view;

import javax.swing.JDialog;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.util.Vector;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JApplet;
import java.awt.Cursor;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
import com.faw_qm.cappclients.capproute.util.RouteTreeObject;
import com.faw_qm.cappclients.capproute.util.RouteListTreeObject;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterIfc;
import com.faw_qm.cappclients.capproute.util.RouteTreeNode;
/**
 * <p>Title:���������� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author ����
 * @version 1.0
 */

public class RouteListRenameJDialog extends JDialog {
  /**�����*/
  private JPanel panel = new JPanel();


  /**���������*/
  private RouteListRenameJPanel routeListRenameJPanel;


  /**ȷ����ť*/
  private JButton okJButton = new JButton();


  /**ȡ����ť*/
  private JButton cancelJButton = new JButton();


  /**��ť�����*/
  private JPanel buttonJPanel = new JPanel();

  private GridBagLayout gridBagLayout1 = new GridBagLayout();


  /**��Դ�ļ�·��*/
  private static String RESOURCE =
          "com.faw_qm.cappclients.capproute.util.CappRouteRB";


  /**���ڱ�ǡ�ȷ�����¼�*/
  public static final int OK = 0;


  /**���ڱ�ǡ�ȡ�����¼�*/
  public static final int CANCEL = -1;


  /**���ڱ�ǡ�ȷ����ָ��*/
  public static final String OK_COMMAND = "OK";


  /**���ڱ�ǡ�ȡ����ָ��*/
  public static final String CANCEL_COMMAND = "Cancel";


  /**����·�߱�*/
  private TechnicsRouteListIfc routeListObject;

  /**���ö���������������*/
  private Vector listeners;
  private JLabel statusJLabel = new JLabel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private CappRouteListManageJFrame mainJFrame;

  private String routeNumber;
  /**
   * ���캯��
   * @param frame ����
   * @param title ����
   * @param modal ģʽ
   */
  public RouteListRenameJDialog(Frame frame, String title, boolean modal)
  {
      super(frame, title, modal);
      try
      {
          mainJFrame = (CappRouteListManageJFrame) frame;
          jbInit();

      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }
  }

  /**
   * ���캯��
   */
  public RouteListRenameJDialog()
  {
      this(null, "", true);

  }

  /**
   * �����ʼ��
   * @throws Exception
   */
  private void jbInit()
          throws Exception
  {
      routeListRenameJPanel = new
                                 RouteListRenameJPanel(this);
      setTitle(QMMessage.getLocalizedMessage(RESOURCE,
                                             "renameRouteListTitle", null));
      setResizable(false);
      setSize(400, 300);
      getContentPane().setLayout(gridBagLayout3);
      panel.setLayout(gridBagLayout2);
      okJButton.setMaximumSize(new Dimension(75, 23));
      okJButton.setMinimumSize(new Dimension(75, 23));
      okJButton.setPreferredSize(new Dimension(75, 23));
      okJButton.setActionCommand("OK");
      okJButton.setMnemonic('Y');
      okJButton.setText("ȷ��");
      cancelJButton.setMaximumSize(new Dimension(75, 23));
      cancelJButton.setMinimumSize(new Dimension(75, 23));
      cancelJButton.setPreferredSize(new Dimension(75, 23));
      cancelJButton.setActionCommand("CANCEL");
      cancelJButton.setMnemonic('C');
      cancelJButton.setText("ȡ��");
      buttonJPanel.setLayout(gridBagLayout1);
      statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
      buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
              , GridBagConstraints.EAST, GridBagConstraints.NONE,
              new Insets(0, 0, 0, 0), 0, 0));
      buttonJPanel.add(cancelJButton,
                       new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.EAST,
                                              GridBagConstraints.NONE,
                                              new Insets(0, 8, 0, 0), 0, 0));
      panel.add(routeListRenameJPanel,
                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(41, 0, 0, 0), 0, 0));
      panel.add(statusJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
              , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
              new Insets(81, 0, 0, 0), 0, 14));
      panel.add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
              , GridBagConstraints.EAST, GridBagConstraints.NONE,
              new Insets(0, 0, 0, 39), 0, 0));
      getContentPane().add(panel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
              , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
              new Insets(0, 0, 0, 0), 0, -54));

      //{{ע�����
      RRJWindow cijwindow = new RRJWindow();
      addWindowListener(cijwindow);
      RRJAction cijaction = new RRJAction();
      cancelJButton.addActionListener(cijaction);
      RRJKey cijkey = new RRJKey();
      cancelJButton.addKeyListener(cijkey);
      okJButton.addActionListener(cijaction);
      okJButton.addKeyListener(cijkey);
      localize();

  }

  /**
   * ��Դ��Ϣ���ػ�
   */
  private void localize()
  {
      String ok = QMMessage.getLocalizedMessage(RESOURCE, "OkJButton", null);
      String cancel = QMMessage.getLocalizedMessage(RESOURCE, "CancelJButton", null);
      okJButton.setText(ok);
      cancelJButton.setText(cancel);
  }

  /**
   * ���ý������ʾλ��
   */
  private void showLocation()
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
   * ���ý����Ƿ�ɼ�
   * @param flag boolean �����Ƿ�ɼ�
   */
  public void setVisible(boolean flag)
  {
      showLocation();
      super.setVisible(flag);
  }

  /**
   * ��ť���������࣬���ð�ť�Ķ����¼�����
   */
  class RRJAction implements ActionListener
  {

      public void actionPerformed(ActionEvent actionevent)
      {
          Object obj = actionevent.getSource();
          if (obj == cancelJButton)
          {
              cancelJButton_Action(actionevent); //ȡ��
              return;
          }
          if (obj == okJButton)
          {
              okJButton_Action(actionevent); //ȷ��
          }
      }

      RRJAction()
      {
      }
  }

  /**
   * ��ť�ļ������࣬���ð�ť�ĵ�����¼�����
   */
  class RRJKey extends KeyAdapter
  {

      public void keyPressed(KeyEvent keyevent)
      {
          Object obj = keyevent.getSource();
          if (obj == cancelJButton)
          {
              cancelJButton_KeyPress(keyevent);
              return;
          }
          if (obj == okJButton)
          {
              okJButton_KeyPress(keyevent);
          }
      }

      RRJKey()
      {
      }
  }


  /**
   * ���ڹرս���Ĵ���������������
   */
  class RRJWindow extends WindowAdapter
  {

      public void windowClosing(WindowEvent windowevent)
      {
          Object obj = windowevent.getSource();
          if (obj == RouteListRenameJDialog.this)
          {
              Dialog1_WindowClosing(windowevent);
          }
      }

      RRJWindow()
      {
      }
  }

  /**
   *
   * @param routelist TechnicsRouteListIfc
   */
  public void setTechnicsObject(TechnicsRouteListIfc routelist)
  {
      routeListObject = routelist;
      routeNumber = routelist.getRouteListNumber();
      //����ҵ������Ψһ��ʶ
      routeListRenameJPanel.setTechnics(routeListObject);
  }
  /**
   *
   * @return TechnicsRouteListIfc
   */
  public TechnicsRouteListIfc getTechnicsObject()
  {
      return routeListObject;
  }


  /**
   * �رմ���
   * @param windowevent ���ڼ����¼�
   */
  void Dialog1_WindowClosing(WindowEvent windowevent)
  {
      dispose();
  }


  /**
   * ���ȷ����ť����ɵĶ���
   * @param actionevent ��ť�����¼�
   */
  void okJButton_Action(ActionEvent actionevent)
  {
      processOkCommand();
  }


  /**
   * �ð������ȷ����ť��ɹ���
   * @param keyevent ���������¼�
   */
  void okJButton_KeyPress(KeyEvent keyevent)
  {
      if (keyevent.getKeyCode() == 10)
      {
          processOkCommand();
      }
  }


  /**
   * ִ��ȷ����ť�¼�
   */
  protected void processOkCommand()
  { if(!routeListRenameJPanel.checkText()){
                return ;
              }


       //���������״Ϊ�ȴ�״̬
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      //����㲿���ı�ź������Ƿ�Ϊ��
      boolean isFilled = false;

          isFilled = routeListRenameJPanel.checkRequiredFields();

      if (!isFilled)
      {
          setCursor(Cursor.getDefaultCursor());
          return;
      }
      //ʹȷ����ȡ����ť����δ����״̬
      enableActions(false);
      //����ҵ������Ψһ��ʶ
      try
      {
       TechnicsRouteListMasterIfc   routeListMaster = routeListRenameJPanel.renameRouteList();

       Vector nodesVec = mainJFrame.getTreePanel().findNodesforRename(routeNumber);
        String numberNew = routeListMaster.getRouteListNumber();
        String nameNew = routeListMaster.getRouteListName();
        if (nodesVec!=null ){
          for(int i =0;i<nodesVec.size();i++){
            RouteTreeNode node  = (RouteTreeNode) nodesVec.elementAt(i);
            RouteListTreeObject treelistobjict =(RouteListTreeObject)node.getObject();
            TechnicsRouteListIfc list  = (TechnicsRouteListIfc) treelistobjict.getObject();
            list.setMaster(routeListMaster);
            list.setRouteListNumber(numberNew);
             list.setRouteListName(nameNew);
             treelistobjict.setObject(list);
             node.setObject(treelistobjict);
             mainJFrame.getTreePanel().nodeChanged(node);
          }
        }

          //��������������֪ͨ��ִ�С�ȷ���������¼�
          notifyActionListeners(new ActionEvent(this, 0, "OK"));
          //�رմ���
          dispose();
      }
     catch (QMRemoteException qre)
      {
          setCursor(Cursor.getDefaultCursor());
          String title = QMMessage.getLocalizedMessage(RESOURCE,
                  "information", null);
          JOptionPane.showMessageDialog(this,
                                        qre.getClientMessage(),
                                        title,
                                        JOptionPane.INFORMATION_MESSAGE);
          return;

      }
      catch (TechnicsRouteException qre)
      {
          setCursor(Cursor.getDefaultCursor());
          String title = QMMessage.getLocalizedMessage(RESOURCE,
                  "information", null);
          JOptionPane.showMessageDialog(this,
                                        qre.getClientMessage(),
                                        title,
                                        JOptionPane.INFORMATION_MESSAGE);
          return;

      }

      finally
      {
          //����ȷ����ȡ����ť
          enableActions(true);
      }

  }


  /**
   * ��������������֪ͨ��ִ��ָ���Ķ����¼���
   * @param actionevent �����¼�
   */
  protected void notifyActionListeners(ActionEvent actionevent)
  {

      if (listeners != null)
      {
          Vector vector;
          synchronized (this)
          {
              //�Ѽ����������Ƶ�һ����������
              vector = (Vector) listeners.clone();
          }
          //ִ��ָ���Ķ����¼�
          for (int i = 0; i < vector.size(); i++)
          {
              ActionListener actionlistener = (ActionListener) vector.
                                              elementAt(i);
              actionlistener.actionPerformed(actionevent);
          }
      }

  }


  /**
   * �ð������ȡ����ť��ɹ���
   * @param keyevent ���������¼�
   */
  void cancelJButton_KeyPress(KeyEvent keyevent)
  {
      if (keyevent.getKeyCode() == 10)
      {
          processCancelCommand();
      }
  }


  /**
   * ���ȡ����ťʵ�ֵĹ���
   * @param actionevent ��ť�����¼�
   */
  void cancelJButton_Action(ActionEvent actionevent)
  {
      processCancelCommand();
  }


  /**
   * ִ��ȡ����ť�¼�
   */
  protected void processCancelCommand()
  {
      //��������������֪ͨ��ִ�С�ȡ���������¼�
      notifyActionListeners(new ActionEvent(this, -1, "Cancel"));
      //�رմ���
      dispose();
  }


  /**
   * ȷ���Ƿ񼤻�ȷ����ť��ȡ����ť
   * @param flag ����ֵ
   */
  private void enableActions(boolean flag)
  {
      cancelJButton.setEnabled(flag);
      okJButton.setEnabled(flag);
  }

  /**
   * ���ָ���Ķ�������������������
   * @param actionlistener ��������
   */
  public synchronized void addActionListener(ActionListener actionlistener)
  {
      if (listeners == null)
      {
          listeners = new Vector();
      }
      if (!listeners.contains(actionlistener))
      {
          listeners.addElement(actionlistener);
      }
  }


  /**
   * �Ӽ���������ɾ��ָ���Ķ�������
   * @param actionlistener ��������
   */
  public synchronized void removeActionListener(ActionListener actionlistener)
  {
      if (listeners != null)
      {
          listeners.removeElement(actionlistener);
      }
  }

}

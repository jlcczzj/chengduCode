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
 * <p>Title:重命名界面 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 周茁
 * @version 1.0
 */

public class RouteListRenameJDialog extends JDialog {
  /**主面板*/
  private JPanel panel = new JPanel();


  /**重命名面板*/
  private RouteListRenameJPanel routeListRenameJPanel;


  /**确定按钮*/
  private JButton okJButton = new JButton();


  /**取消按钮*/
  private JButton cancelJButton = new JButton();


  /**按钮组面板*/
  private JPanel buttonJPanel = new JPanel();

  private GridBagLayout gridBagLayout1 = new GridBagLayout();


  /**资源文件路径*/
  private static String RESOURCE =
          "com.faw_qm.cappclients.capproute.util.CappRouteRB";


  /**用于标记“确定”事件*/
  public static final int OK = 0;


  /**用于标记“取消”事件*/
  public static final int CANCEL = -1;


  /**用于标记“确定”指令*/
  public static final String OK_COMMAND = "OK";


  /**用于标记“取消”指令*/
  public static final String CANCEL_COMMAND = "Cancel";


  /**工艺路线表*/
  private TechnicsRouteListIfc routeListObject;

  /**放置动作监听器的容器*/
  private Vector listeners;
  private JLabel statusJLabel = new JLabel();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private CappRouteListManageJFrame mainJFrame;

  private String routeNumber;
  /**
   * 构造函数
   * @param frame 窗口
   * @param title 标题
   * @param modal 模式
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
   * 构造函数
   */
  public RouteListRenameJDialog()
  {
      this(null, "", true);

  }

  /**
   * 界面初始化
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
      okJButton.setText("确定");
      cancelJButton.setMaximumSize(new Dimension(75, 23));
      cancelJButton.setMinimumSize(new Dimension(75, 23));
      cancelJButton.setPreferredSize(new Dimension(75, 23));
      cancelJButton.setActionCommand("CANCEL");
      cancelJButton.setMnemonic('C');
      cancelJButton.setText("取消");
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

      //{{注册监听
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
   * 资源信息本地化
   */
  private void localize()
  {
      String ok = QMMessage.getLocalizedMessage(RESOURCE, "OkJButton", null);
      String cancel = QMMessage.getLocalizedMessage(RESOURCE, "CancelJButton", null);
      okJButton.setText(ok);
      cancelJButton.setText(cancel);
  }

  /**
   * 设置界面的显示位置
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
   * 设置界面是否可见
   * @param flag boolean 界面是否可见
   */
  public void setVisible(boolean flag)
  {
      showLocation();
      super.setVisible(flag);
  }

  /**
   * 按钮动作监听类，调用按钮的动作事件方法
   */
  class RRJAction implements ActionListener
  {

      public void actionPerformed(ActionEvent actionevent)
      {
          Object obj = actionevent.getSource();
          if (obj == cancelJButton)
          {
              cancelJButton_Action(actionevent); //取消
              return;
          }
          if (obj == okJButton)
          {
              okJButton_Action(actionevent); //确定
          }
      }

      RRJAction()
      {
      }
  }

  /**
   * 按钮的键监听类，调用按钮的点击键事件方法
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
   * 用于关闭界面的窗口适配器监听类
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
      //设置业务对象的唯一标识
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
   * 关闭窗口
   * @param windowevent 窗口监听事件
   */
  void Dialog1_WindowClosing(WindowEvent windowevent)
  {
      dispose();
  }


  /**
   * 点击确定按钮所完成的动作
   * @param actionevent 按钮动作事件
   */
  void okJButton_Action(ActionEvent actionevent)
  {
      processOkCommand();
  }


  /**
   * 用按键点击确定按钮完成功能
   * @param keyevent 按键监听事件
   */
  void okJButton_KeyPress(KeyEvent keyevent)
  {
      if (keyevent.getKeyCode() == 10)
      {
          processOkCommand();
      }
  }


  /**
   * 执行确定按钮事件
   */
  protected void processOkCommand()
  { if(!routeListRenameJPanel.checkText()){
                return ;
              }


       //设置鼠标形状为等待状态
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      //检查零部件的编号和名称是否为空
      boolean isFilled = false;

          isFilled = routeListRenameJPanel.checkRequiredFields();

      if (!isFilled)
      {
          setCursor(Cursor.getDefaultCursor());
          return;
      }
      //使确定和取消按钮处于未激活状态
      enableActions(false);
      //更改业务对象的唯一标识
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

          //向动作监听器发出通知，执行“确定”动作事件
          notifyActionListeners(new ActionEvent(this, 0, "OK"));
          //关闭窗口
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
          //激活确定和取消按钮
          enableActions(true);
      }

  }


  /**
   * 向动作监听器发出通知，执行指定的动作事件。
   * @param actionevent 动作事件
   */
  protected void notifyActionListeners(ActionEvent actionevent)
  {

      if (listeners != null)
      {
          Vector vector;
          synchronized (this)
          {
              //把监听容器复制到一个新容器中
              vector = (Vector) listeners.clone();
          }
          //执行指定的动作事件
          for (int i = 0; i < vector.size(); i++)
          {
              ActionListener actionlistener = (ActionListener) vector.
                                              elementAt(i);
              actionlistener.actionPerformed(actionevent);
          }
      }

  }


  /**
   * 用按键点击取消按钮完成功能
   * @param keyevent 按键监听事件
   */
  void cancelJButton_KeyPress(KeyEvent keyevent)
  {
      if (keyevent.getKeyCode() == 10)
      {
          processCancelCommand();
      }
  }


  /**
   * 点击取消按钮实现的功能
   * @param actionevent 按钮动作事件
   */
  void cancelJButton_Action(ActionEvent actionevent)
  {
      processCancelCommand();
  }


  /**
   * 执行取消按钮事件
   */
  protected void processCancelCommand()
  {
      //向动作监听器发出通知，执行“取消”动作事件
      notifyActionListeners(new ActionEvent(this, -1, "Cancel"));
      //关闭窗口
      dispose();
  }


  /**
   * 确定是否激活确定按钮和取消按钮
   * @param flag 布尔值
   */
  private void enableActions(boolean flag)
  {
      cancelJButton.setEnabled(flag);
      okJButton.setEnabled(flag);
  }

  /**
   * 添加指定的动作监听到监听容器中
   * @param actionlistener 动作监听
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
   * 从监听容器中删除指定的动作监听
   * @param actionlistener 动作监听
   */
  public synchronized void removeActionListener(ActionListener actionlistener)
  {
      if (listeners != null)
      {
          listeners.removeElement(actionlistener);
      }
  }

}

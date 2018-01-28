package com.faw_qm.cappclients.capproute.view;


import java.util.Collection;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.faw_qm.technics.route.model.*;
import com.faw_qm.clients.util.RefreshEvent;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
import com.faw_qm.cappclients.capproute.util.CappRouteRB;
import java.awt.*;
import com.faw_qm.util.TextValidCheck;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author 周茁
 * @version 1.0
 */

public class RouteListRenameJPanel extends JPanel{
  public RouteListRenameJPanel() {
  }
  private JLabel numJLabel = new JLabel();
     private JTextField numJTextField;
      private JLabel nameJLabel = new JLabel();
     private JTextField nameJTextField;
      private GridBagLayout gridBagLayout1 = new GridBagLayout();


      /**资源文件路径*/
      protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";


      /**工艺路线表主信息对象*/
     private TechnicsRouteListIfc routeListObject;


      /**用于标记当前名称*/
      private String currentName;


      /**用于标记当前编号*/
      private String currentNumber;


      /**请求服务器*/
      private RequestServer server = RequestServerFactory.getRequestServer();


      /**代码测试变量*/
      private static boolean verbose = (RemoteProperty.getProperty(
              "com.faw_qm.cappclients.verbose", "true")).equals("true");

      /**父窗口*/
      private Component parent;
    TextValidCheck textheck  = new TextValidCheck("工艺路线表",30);
      public RouteListRenameJPanel(Component parent)
      {
          this.parent = parent;
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
       * 界面初始化
       * @throws Exception
       */
      void jbInit()
              throws Exception
      {
          numJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
          numJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
          //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
          numJLabel.setText("*艺准编号");
          //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线
          setLayout(gridBagLayout1);
          setSize(400, 100);
          nameJLabel.setMaximumSize(new Dimension(53, 22));
          nameJLabel.setMinimumSize(new Dimension(53, 22));
          nameJLabel.setPreferredSize(new Dimension(53, 22));
          nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
          nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//        CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
          nameJLabel.setText("*项目名称");
//        CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线      
          add(nameJLabel,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 22, 62, 0), 21, 0));
          add(numJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(57, 34, 0, 0), 0, 0));

            nameJTextField = new JTextField();
            nameJTextField.setMaximumSize(new Dimension(2147483647, 22));
          nameJTextField.setBounds(new Rectangle(65, 13, 63, 22));
          add(nameJTextField,  new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(20, 8, 62, 40), 0, 0));
          numJTextField = new JTextField();
          numJTextField.setMaximumSize(new Dimension(2147483647, 22));
          numJTextField.setBounds(new Rectangle(65, 13, 63, 22));
          add(numJTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                                    , GridBagConstraints.WEST,
                                                    GridBagConstraints.HORIZONTAL,
                                                    new Insets(57, 8, 0, 40), 0,
                                                    0));
          //localize();
      }


      /**
       * 界面信息本地化
       */
//      private void localize()
//      {
//          numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                  "technicsNumberJLabel", null));
//          nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                  "technicsNameJLabel", null));
//      }


      /**
       * 设置工艺路线表业务对象
       * @param masterInfo 工艺路线表主信息
       */
      public void setTechnics(TechnicsRouteListIfc routeListInfo)
      {
          routeListObject = routeListInfo;
          initializeProperties();
      }


      /**
       * 获得工艺路线表业务对象
       * @return  工艺路线表主信息
       */
      public TechnicsRouteListIfc getTechnics()
      {
          return routeListObject;
      }


      /**
       * 初始化业务对象的属性（名称和编号）
       * @throws QMRemoteException
       */
      private void initializeProperties()
      {
          if (verbose)
          {
              System.out.println(
                      "cappclients.capp.view.RouteListRenameJPanel.initializeProperties() begin...");
          }
          if (routeListObject != null)
          {
              //获得当前对象的名称
              currentName = routeListObject.getRouteListName();
              nameJTextField.setText(currentName);
              //获得当前对象的编号
              currentNumber = routeListObject.getRouteListNumber();
              numJTextField.setText(currentNumber);
          }

      }


      /**
       * 改变业务对象的唯一标识
       * @return 更改后的业务对象的唯一标识
       * @throws QMRemoteException
       * @see #setAttribute
       */
      public TechnicsRouteListMasterIfc renameRouteList()
              throws TechnicsRouteException,QMRemoteException
      {

          boolean flag = false;
          //如果工艺的原名称或原编号发生了修改，则获得新输入的工艺名称和编号，
          //并存入数据库
          if (nameJTextField.isShowing() && numJTextField.isShowing())
          {
              if (numJTextField.getText() == null || numJTextField.getText().trim() == null)
              {
            	  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
            	  throw new QMRemoteException("输入的艺准编号为空");
            	  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线
              }
              if (nameJTextField.getText() == null ||
                  nameJTextField.getText().trim() == null)
              {
            	  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
            	  throw new QMRemoteException("输入的项目名称为空");
            	  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线
              }

              //如果编号和名称都没有作任何更改，则抛出异常
              if (currentName.equals(nameJTextField.getText().trim()) &&
                  currentNumber.equals(numJTextField.getText().trim())
                  )
              {

                  //通过标识工厂获得与给定值对象对应的显示标识对象。
                  DisplayIdentity displayidentity = IdentityFactory.
                                                    getDisplayIdentity(
                          routeListObject);
                  String s = displayidentity.getLocalizedMessage(null);
                  Object aobj[] =
                          {s};
                  //CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线
                  throw new QMRemoteException("艺准编号和项目名称都没有更改");
                  //CCEndby leixiao 2008-8-4 原因：解放升级工艺路线
              }
              if (currentName == null ||
                  !currentName.equals(nameJTextField.getText().trim()))
              {
                  flag = false;
              }
              if (currentNumber == null ||
                  !currentNumber.equals(numJTextField.getText().trim()))
              {
                  flag = true;
              }

              TechnicsRouteListMasterIfc      routeListMaster;
              String oldname = routeListObject.getRouteListName();
              String oldnum = routeListObject.getRouteListNumber();
              //设置新输入的工艺名称
              routeListObject.setRouteListName(nameJTextField.getText().trim());
              //设置新输入的零部件编号
              routeListObject.setRouteListNumber(numJTextField.getText().trim());

              //调用服务方法，修改数据库中工艺的编号和名称
              ServiceRequestInfo info1 = new ServiceRequestInfo();
              info1.setServiceName("TechnicsRouteService");
              info1.setMethodName("rename");
              Class[] paraClass1 =
                      {TechnicsRouteListIfc.class};
              info1.setParaClasses(paraClass1);
              Object[] objs1 =
                      {routeListObject};
              info1.setParaValues(objs1);
              try
              {
            routeListMaster = (TechnicsRouteListMasterIfc) server.request(info1);
              }
              catch (QMRemoteException ex)
              {

                  throw ex;
              }

   return routeListMaster;
          }

    else    return null;

      }


      /**
       * 根据具体的事件动作标识和事件目标，发布刷新事件
       * @param obj target事件目标
       * @param i  具体的事件动作标识
       */
      private void dispatchRefresh(Object obj, int i)
      {
          //实例化刷新事件
          RefreshEvent refreshEvent = new RefreshEvent(getParent(), i, obj);
          //指派刷新事件
          RefreshService.getRefreshService().dispatchRefresh(refreshEvent);

      }


      /**
       * 刷新界面
       */
      public void refresh()
      {
          invalidate();
          doLayout();
          repaint();
      }

      /**
       * 检查文本框的数值有效性
       *
       * @return boolean
       */
      private boolean check() {
        if (numJTextField.getText().indexOf("*") != -1 || numJTextField.getText().indexOf("%") != -1 ||
              numJTextField.getText().indexOf("?") != -1)
          {
              String message = "编号" + "含有非法字符eg:*%?";
              String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                    null);
            JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                    JOptionPane.WARNING_MESSAGE);
            this.numJTextField.grabFocus();
              return false;
          }
          if (nameJTextField.getText().indexOf("*") != -1 || nameJTextField.getText().indexOf("%") != -1 ||
             nameJTextField.getText().indexOf("?") != -1)
         {
             String message = "名称" + "含有非法字符eg:*%?";
             String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                   null);
           JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                   JOptionPane.WARNING_MESSAGE);
           this.numJTextField.grabFocus();
             return false;
         }
          if (numJTextField.getText().trim().getBytes().length > 30) {
              String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                      null);
              String message = QMMessage
                      .getLocalizedMessage(RESOURCE, "52", null);
              JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                      JOptionPane.WARNING_MESSAGE);
              this.numJTextField.grabFocus();
              return false;
          }
          if (this.nameJTextField.getText().trim().getBytes().length > 200) {
              String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                      null);
              String message = QMMessage
                      .getLocalizedMessage(RESOURCE, "53", null);
              JOptionPane.showMessageDialog(this.getParentJFrame(), message, title,
                      JOptionPane.WARNING_MESSAGE);
              this.nameJTextField.grabFocus();
              return false;
          }

          return true;
      }
      public boolean checkText (){
           try {
            textheck.check(numJTextField, true);

          }
          catch (QMRemoteException ex) {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                      null);
              JOptionPane.showMessageDialog(this.getParentJFrame(), ex.getClientMessage(), title,
                      JOptionPane.WARNING_MESSAGE);
              this.numJTextField.grabFocus();
                return false;

          }
          try {textheck.setMax(200);
                textheck.check(nameJTextField, true);
              }
              catch (QMRemoteException ex) {
                    String title = QMMessage.getLocalizedMessage(RESOURCE, "warning",
                          null);
                  JOptionPane.showMessageDialog(this.getParentJFrame(), ex.getClientMessage(), title,
                          JOptionPane.WARNING_MESSAGE);
                  this.nameJTextField.grabFocus();
                    return false;

              }



              return true;
         }

      /**
           * 获得父窗口
           *
           * @return javax.swing.JFrame
           * @roseuid 402A11F40212
           */
          public JFrame getParentJFrame() {
              Component parent = getParent();
              while (!(parent instanceof JFrame)) {
                  parent = parent.getParent();
              }
              return (JFrame) parent;
          }

          /**
           * 检验必填区域是否已有有效值
           *
           * @return 如果必填区域已有有效值，则返回为真
           */
          public boolean checkRequiredFields() {
              if (verbose)
                  System.out
                          .println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() begin...");
              boolean isOK = false;
              String message = "fell through ";
              String title = "";

                      //检验编号是否为空
                      if (numJTextField.getText().trim().length() == 0) {
                          message = QMMessage.getLocalizedMessage(RESOURCE,
                                  CappRouteRB.NO_NUMBER_ENTERED, null);
                          this.numJTextField.grabFocus();
                          isOK = false;
                      }
                      //检验名称是否为空
                      else if (nameJTextField.getText().trim().length() == 0) {
                          message = QMMessage.getLocalizedMessage(RESOURCE,
                                  CappRouteRB.NO_NAME_ENTERED, null);
                          this.nameJTextField.grabFocus();
                          isOK = false;
                      }
                      else {
             isOK = true;//
           }

                  if (!isOK) {
                      //显示信息：缺少必需的字段
                      title = QMMessage.getLocalizedMessage(RESOURCE,
                              "information", null);
                      JOptionPane.showMessageDialog(getParentJFrame(), message,
                              title, JOptionPane.INFORMATION_MESSAGE);
                  }


              return isOK;

          }

}

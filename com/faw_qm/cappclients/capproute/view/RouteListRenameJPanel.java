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
 * @author ����
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


      /**��Դ�ļ�·��*/
      protected static String RESOURCE = "com.faw_qm.cappclients.capproute.util.CappRouteRB";


      /**����·�߱�����Ϣ����*/
     private TechnicsRouteListIfc routeListObject;


      /**���ڱ�ǵ�ǰ����*/
      private String currentName;


      /**���ڱ�ǵ�ǰ���*/
      private String currentNumber;


      /**���������*/
      private RequestServer server = RequestServerFactory.getRequestServer();


      /**������Ա���*/
      private static boolean verbose = (RemoteProperty.getProperty(
              "com.faw_qm.cappclients.verbose", "true")).equals("true");

      /**������*/
      private Component parent;
    TextValidCheck textheck  = new TextValidCheck("����·�߱�",30);
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
       * �����ʼ��
       * @throws Exception
       */
      void jbInit()
              throws Exception
      {
          numJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
          numJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
          //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
          numJLabel.setText("*��׼���");
          //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��
          setLayout(gridBagLayout1);
          setSize(400, 100);
          nameJLabel.setMaximumSize(new Dimension(53, 22));
          nameJLabel.setMinimumSize(new Dimension(53, 22));
          nameJLabel.setPreferredSize(new Dimension(53, 22));
          nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
          nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
//        CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
          nameJLabel.setText("*��Ŀ����");
//        CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��      
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
       * ������Ϣ���ػ�
       */
//      private void localize()
//      {
//          numJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                  "technicsNumberJLabel", null));
//          nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
//                  "technicsNameJLabel", null));
//      }


      /**
       * ���ù���·�߱�ҵ�����
       * @param masterInfo ����·�߱�����Ϣ
       */
      public void setTechnics(TechnicsRouteListIfc routeListInfo)
      {
          routeListObject = routeListInfo;
          initializeProperties();
      }


      /**
       * ��ù���·�߱�ҵ�����
       * @return  ����·�߱�����Ϣ
       */
      public TechnicsRouteListIfc getTechnics()
      {
          return routeListObject;
      }


      /**
       * ��ʼ��ҵ���������ԣ����ƺͱ�ţ�
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
              //��õ�ǰ���������
              currentName = routeListObject.getRouteListName();
              nameJTextField.setText(currentName);
              //��õ�ǰ����ı��
              currentNumber = routeListObject.getRouteListNumber();
              numJTextField.setText(currentNumber);
          }

      }


      /**
       * �ı�ҵ������Ψһ��ʶ
       * @return ���ĺ��ҵ������Ψһ��ʶ
       * @throws QMRemoteException
       * @see #setAttribute
       */
      public TechnicsRouteListMasterIfc renameRouteList()
              throws TechnicsRouteException,QMRemoteException
      {

          boolean flag = false;
          //������յ�ԭ���ƻ�ԭ��ŷ������޸ģ�����������Ĺ������ƺͱ�ţ�
          //���������ݿ�
          if (nameJTextField.isShowing() && numJTextField.isShowing())
          {
              if (numJTextField.getText() == null || numJTextField.getText().trim() == null)
              {
            	  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
            	  throw new QMRemoteException("�������׼���Ϊ��");
            	  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��
              }
              if (nameJTextField.getText() == null ||
                  nameJTextField.getText().trim() == null)
              {
            	  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
            	  throw new QMRemoteException("�������Ŀ����Ϊ��");
            	  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��
              }

              //�����ź����ƶ�û�����κθ��ģ����׳��쳣
              if (currentName.equals(nameJTextField.getText().trim()) &&
                  currentNumber.equals(numJTextField.getText().trim())
                  )
              {

                  //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                  DisplayIdentity displayidentity = IdentityFactory.
                                                    getDisplayIdentity(
                          routeListObject);
                  String s = displayidentity.getLocalizedMessage(null);
                  Object aobj[] =
                          {s};
                  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
                  throw new QMRemoteException("��׼��ź���Ŀ���ƶ�û�и���");
                  //CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��
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
              //����������Ĺ�������
              routeListObject.setRouteListName(nameJTextField.getText().trim());
              //������������㲿�����
              routeListObject.setRouteListNumber(numJTextField.getText().trim());

              //���÷��񷽷����޸����ݿ��й��յı�ź�����
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
       * ���ݾ�����¼�������ʶ���¼�Ŀ�꣬����ˢ���¼�
       * @param obj target�¼�Ŀ��
       * @param i  ������¼�������ʶ
       */
      private void dispatchRefresh(Object obj, int i)
      {
          //ʵ����ˢ���¼�
          RefreshEvent refreshEvent = new RefreshEvent(getParent(), i, obj);
          //ָ��ˢ���¼�
          RefreshService.getRefreshService().dispatchRefresh(refreshEvent);

      }


      /**
       * ˢ�½���
       */
      public void refresh()
      {
          invalidate();
          doLayout();
          repaint();
      }

      /**
       * ����ı������ֵ��Ч��
       *
       * @return boolean
       */
      private boolean check() {
        if (numJTextField.getText().indexOf("*") != -1 || numJTextField.getText().indexOf("%") != -1 ||
              numJTextField.getText().indexOf("?") != -1)
          {
              String message = "���" + "���зǷ��ַ�eg:*%?";
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
             String message = "����" + "���зǷ��ַ�eg:*%?";
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
           * ��ø�����
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
           * ������������Ƿ�������Чֵ
           *
           * @return �����������������Чֵ���򷵻�Ϊ��
           */
          public boolean checkRequiredFields() {
              if (verbose)
                  System.out
                          .println("cappclients.capproute.view.RouteListTaskJPanel.checkRequiredFields() begin...");
              boolean isOK = false;
              String message = "fell through ";
              String title = "";

                      //�������Ƿ�Ϊ��
                      if (numJTextField.getText().trim().length() == 0) {
                          message = QMMessage.getLocalizedMessage(RESOURCE,
                                  CappRouteRB.NO_NUMBER_ENTERED, null);
                          this.numJTextField.grabFocus();
                          isOK = false;
                      }
                      //���������Ƿ�Ϊ��
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
                      //��ʾ��Ϣ��ȱ�ٱ�����ֶ�
                      title = QMMessage.getLocalizedMessage(RESOURCE,
                              "information", null);
                      JOptionPane.showMessageDialog(getParentJFrame(), message,
                              title, JOptionPane.INFORMATION_MESSAGE);
                  }


              return isOK;

          }

}

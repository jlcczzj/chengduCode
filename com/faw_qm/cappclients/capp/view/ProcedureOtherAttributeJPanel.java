/** 生成程序ProcedureOtherAttributeJPanel.java	  2007/08/036
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;

/**
 * <p>Title:工序其他属性关联面板</p>
 * <p>Description: 维护工序用设备资源 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 一汽启明</p>
 * @author 唐树涛
 * @version 1.0
 */

  public class ProcedureOtherAttributeJPanel extends ParentJPanel
  {
      private TitledBorder titledBorder1;
      private JPanel buttonJPanel = new JPanel();
      private JButton okJButton = new JButton();
      private JButton cancelJButton = new JButton();
      private GridBagLayout gridBagLayout1 = new GridBagLayout();


      /**业务对象：工序（步）*/
      private BaseValueIfc procedureInfo = null;
      private static String RESOURCE =
              "com.faw_qm.cappclients.capp.util.CappLMRB";


      /**扩展属性维护面板*/
      private CappExAttrPanel cappExAttrPanel;
      private GridBagLayout gridBagLayout2 = new GridBagLayout();


      /**
       * 构造函数
       * @param processType 工序种类的标记
       */
      public ProcedureOtherAttributeJPanel(String bsoName, String processType)
      {
         try
          {
              cappExAttrPanel = new CappExAttrPanel(bsoName, processType, 1);
              jbInit();
          }
          catch (Exception e)
          {
              e.printStackTrace();
          }
      }


      /**
       * 代码测试
       * @param args
       */
      public static void main(String[] args)
      {
          ProcedureOtherAttributeJPanel f = new ProcedureOtherAttributeJPanel("QMAssembleProcedure", "机加");
          f.setVisible(true);

      }


      /**
       * 界面初始化
       * @throws Exception
       */
      private void jbInit()
              throws Exception
      {
//          titledBorder1 = new TitledBorder(
//                  QMMessage.getLocalizedMessage(RESOURCE, "technicsParam", null));

          setSize(650, 500);
          //setModal(true);
          //setTitle(QMMessage.getLocalizedMessage(RESOURCE,
          //                                       "technicsInformationTitle", null));
          setLayout(gridBagLayout2);
          buttonJPanel.setLayout(gridBagLayout1);
          okJButton.setMaximumSize(new Dimension(75, 23));
          okJButton.setMinimumSize(new Dimension(75, 23));
          okJButton.setPreferredSize(new Dimension(75, 23));
          okJButton.setMnemonic('Y');
          okJButton.setText("确定");
          okJButton.addActionListener(new java.awt.event.ActionListener()
          {
              public void actionPerformed(ActionEvent e)
              {
                  okJButton_actionPerformed(e);
              }
          });
          cancelJButton.setMaximumSize(new Dimension(75, 23));
          cancelJButton.setMinimumSize(new Dimension(75, 23));
          cancelJButton.setPreferredSize(new Dimension(75, 23));
          cancelJButton.setMnemonic('Q');
          cancelJButton.setText("退出");
          cancelJButton.addActionListener(new java.awt.event.ActionListener()
          {
              public void actionPerformed(ActionEvent e)
              {
                  cancelJButton_actionPerformed(e);
              }
          });

          cappExAttrPanel.setBorder(BorderFactory.createEtchedBorder());
        /*
          buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                  , GridBagConstraints.EAST, GridBagConstraints.NONE,
                  new Insets(0, 0, 0, 0), 0, 0));
      buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
      */
                     add(cappExAttrPanel,
                               new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                  , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                  new Insets(10, 10, 0, 10), 0, 0));
          add(buttonJPanel,
                               new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                  , GridBagConstraints.EAST, GridBagConstraints.NONE,
                  new Insets(30, 0, 30, 10), 0, 0));

          localize();

      }


      public void setVisible(boolean flag)
      {
          if (flag)
          {
              showLocation();
              super.setVisible(flag);
          }
          else
          {
              if (cappExAttrPanel.check())
              {
                  super.setVisible(false);
              }

          }
      }


      /**
       * 设置界面的显示位置
       */
      public void showLocation()
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
       * 执行确定操作
       * @param e ActionEvent
       */
      void okJButton_actionPerformed(ActionEvent e)
      {
          if (cappExAttrPanel.check())
          {
              setVisible(false);
          }

      }


      /**
       * 执行取消操作
       * @param e ActionEvent
       */
      void cancelJButton_actionPerformed(ActionEvent e)
      {

          setVisible(false);
      }


      /**
       * 设置业务对象
       * @param info 工序（步）值对象
       */
      public void setProcedure(BaseValueIfc info)
      {
          procedureInfo = info;
          cappExAttrPanel.show((ExtendAttriedIfc) info);
      }


      /**
       * 获得业务对象
       * @return 工序（步）值对象
       */
      public BaseValueIfc getProcedure()
      {
          return procedureInfo;
      }


      /**
       * 检验录入是否正确
       * @return 录入正确，则返回true；否则返回false
       */
      public boolean check()
      {
          return cappExAttrPanel.check();
      }


      /**
       * 获得扩展内容
       * @return
       */
      public ExtendAttContainer getExtendAttributes()
      {
          ExtendAttContainer c = null;
          c = cappExAttrPanel.getExAttr();
          return c;
      }


      /**
       * 设置界面为可编辑
       */
      public void setEditMode()
      {
          cappExAttrPanel.setModel(CappExAttrPanel.EDIT_MODEL);
          okJButton.setVisible(true);
          cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                  "QuitJButton", null));
      }


      /**
       * 设置界面查看模式
       */
      public void setViewMode()
      {
          cappExAttrPanel.setModel(CappExAttrPanel.VIEW_MODEL);
          okJButton.setVisible(false);
          cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                  "QuitJButton", null));
          cappExAttrPanel.repaint();
      }

      public void clear()
      {
          cappExAttrPanel.clear();
      }

  }

/** ���ɳ���ProcedureOtherAttributeJPanel.java	  2007/08/036
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title:�����������Թ������</p>
 * <p>Description: ά���������豸��Դ </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: һ������</p>
 * @author ������
 * @version 1.0
 */

  public class ProcedureOtherAttributeJPanel extends ParentJPanel
  {
      private TitledBorder titledBorder1;
      private JPanel buttonJPanel = new JPanel();
      private JButton okJButton = new JButton();
      private JButton cancelJButton = new JButton();
      private GridBagLayout gridBagLayout1 = new GridBagLayout();


      /**ҵ����󣺹��򣨲���*/
      private BaseValueIfc procedureInfo = null;
      private static String RESOURCE =
              "com.faw_qm.cappclients.capp.util.CappLMRB";


      /**��չ����ά�����*/
      private CappExAttrPanel cappExAttrPanel;
      private GridBagLayout gridBagLayout2 = new GridBagLayout();


      /**
       * ���캯��
       * @param processType ��������ı��
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
       * �������
       * @param args
       */
      public static void main(String[] args)
      {
          ProcedureOtherAttributeJPanel f = new ProcedureOtherAttributeJPanel("QMAssembleProcedure", "����");
          f.setVisible(true);

      }


      /**
       * �����ʼ��
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
          okJButton.setText("ȷ��");
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
          cancelJButton.setText("�˳�");
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
       * ���ý������ʾλ��
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
       * ִ��ȷ������
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
       * ִ��ȡ������
       * @param e ActionEvent
       */
      void cancelJButton_actionPerformed(ActionEvent e)
      {

          setVisible(false);
      }


      /**
       * ����ҵ�����
       * @param info ���򣨲���ֵ����
       */
      public void setProcedure(BaseValueIfc info)
      {
          procedureInfo = info;
          cappExAttrPanel.show((ExtendAttriedIfc) info);
      }


      /**
       * ���ҵ�����
       * @return ���򣨲���ֵ����
       */
      public BaseValueIfc getProcedure()
      {
          return procedureInfo;
      }


      /**
       * ����¼���Ƿ���ȷ
       * @return ¼����ȷ���򷵻�true�����򷵻�false
       */
      public boolean check()
      {
          return cappExAttrPanel.check();
      }


      /**
       * �����չ����
       * @return
       */
      public ExtendAttContainer getExtendAttributes()
      {
          ExtendAttContainer c = null;
          c = cappExAttrPanel.getExAttr();
          return c;
      }


      /**
       * ���ý���Ϊ�ɱ༭
       */
      public void setEditMode()
      {
          cappExAttrPanel.setModel(CappExAttrPanel.EDIT_MODEL);
          okJButton.setVisible(true);
          cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                  "QuitJButton", null));
      }


      /**
       * ���ý���鿴ģʽ
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

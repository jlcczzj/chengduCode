package com.faw_qm.cappclients.beans.cappexattrpanel;

import java.awt.*;
import javax.swing.*;
import java.awt.Dimension;

/**
 * <p>Title: 查看属性说明界面</p>
 * <p>Description: 根据值查看该值对应的属性说明</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明</p>
 * @author 唐树涛
 * @version 1.0
 */

public class ViewIntroductionDialog
    extends JDialog {
  JLabel jLabel1 = new JLabel();
  ImageIcon personIcon;
  FlowLayout flowLayout1 = new FlowLayout();
  public ViewIntroductionDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      //pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public ViewIntroductionDialog(Frame frame, String title, boolean modal, int i) {
    this(frame, title, modal);
    setSize(i);
    try {
      jbInit();
      //pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public ViewIntroductionDialog() {
    this(null, "", false);
  }

  public void setSize(int i) {
    if (i == 0) {
      personIcon = new ImageIcon(getClass().getResource(
          "/images/yanzhongdu.gif"));
      this.setSize(840, 750);
    }
    else if (i == 1) {
      personIcon = new ImageIcon(getClass().getResource(
          "/images/pindu.gif"));
      this.setSize(470, 500);
    }
    else if (i == 2) {
      personIcon = new ImageIcon(getClass().getResource(
          "/images/tancedu.gif"));
      this.setSize(690, 600);
    }
    else if (i == 3) {
      personIcon = new ImageIcon(getClass().getResource(
          "/images/jiachi.gif"));
      this.setSize(405, 205);
    }

    else if (i == 4) {
      personIcon = new ImageIcon(getClass().getResource(
          "/images/qieduan.gif"));
      this.setSize(475, 255);
    }
    else if (i == 5) {
      personIcon = new ImageIcon(getClass().getResource(
          "/images/xiuzhengduantou.gif"));
      this.setSize(350, 270);
    }

  }

  /**
   * 设置界面的显示位置
   */
  private void setViewLocation() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    setLocation( (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

  }

  /**
   * 重载父类方法
   * @param flag
   */
  public void setVisible(boolean flag) {
    setViewLocation();
    super.setVisible(flag);
  }

  private void jbInit() throws Exception {
    this.setResizable(false);
    this.setSize(new Dimension(488, 187));
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setText("");
    jLabel1.setIcon(personIcon);
    this.getContentPane().setLayout(flowLayout1);
    this.getContentPane().add(jLabel1, null);
  }
}  //  @jve:decl-index=0:visual-constraint="-44,22"

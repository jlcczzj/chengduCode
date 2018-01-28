/**
 * 生成程序llTextField.java    1.0    2008/09/03
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.cappclients.capp.view;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.util.Vector;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.faw_qm.cappclients.util.CappTextField;
import java.awt.Component;

/**
 * <p>Title: 文本框</p>
 * <p>Description: 具备工艺文本框的功能并实现输入监听</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: QM</p>
 * @author ll
 * @version 1.0
 */
public class llTextField
    extends CappTextField
    implements DocumentListener {

  /**
   * 事件容器
   */
  private Vector listener = new Vector();

  /**
   * 默认构造器
   */
  public llTextField(Component parent, String nameDisp, int maxlength,
                     boolean nullAllowed) {
    super(parent,nameDisp,maxlength,nullAllowed);
    this.getDocument().addDocumentListener(this);
  }

  /**
   * 增加文本事件监听器
   * @see TextListener
   * @param tListener 文本监听器
   */
  public void addTextListener(TextListener tListener) {
    listener.addElement(tListener);
  }

  /**
   * 实现接口DocumentListener的方法
   * @param e  Document事件
   */
  public void changedUpdate(DocumentEvent e) {
    fireTextChanged();
  }

  /**
   * 实现接口DocumentListener的方法
   * @param e Document事件
   */
  public void insertUpdate(DocumentEvent e) {
    fireTextChanged();
  }

  /**
   * 实现接口DocumentListener的方法
   * @param e Document事件
   */
  public void removeUpdate(DocumentEvent e) {
    fireTextChanged();
  }

  /**
   * 抛出文本改变事件
   */
  protected void fireTextChanged() {
    TextEvent event = new TextEvent(this, 900);
    for (int j = 0; j < listener.size(); j++) {
      ( (TextListener) listener.elementAt(j)).textValueChanged(event);
    }
  }

  /**
   * 删除某个文本监听器
   * @see TextListener
   * @param tListener 文本监听器
   */
  public void removeTextListener(TextListener tListener) {
    listener.removeElement(tListener);
  }

}

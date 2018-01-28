/**
 * ���ɳ���llTextField.java    1.0    2008/09/03
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: �ı���</p>
 * <p>Description: �߱������ı���Ĺ��ܲ�ʵ���������</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: QM</p>
 * @author ll
 * @version 1.0
 */
public class llTextField
    extends CappTextField
    implements DocumentListener {

  /**
   * �¼�����
   */
  private Vector listener = new Vector();

  /**
   * Ĭ�Ϲ�����
   */
  public llTextField(Component parent, String nameDisp, int maxlength,
                     boolean nullAllowed) {
    super(parent,nameDisp,maxlength,nullAllowed);
    this.getDocument().addDocumentListener(this);
  }

  /**
   * �����ı��¼�������
   * @see TextListener
   * @param tListener �ı�������
   */
  public void addTextListener(TextListener tListener) {
    listener.addElement(tListener);
  }

  /**
   * ʵ�ֽӿ�DocumentListener�ķ���
   * @param e  Document�¼�
   */
  public void changedUpdate(DocumentEvent e) {
    fireTextChanged();
  }

  /**
   * ʵ�ֽӿ�DocumentListener�ķ���
   * @param e Document�¼�
   */
  public void insertUpdate(DocumentEvent e) {
    fireTextChanged();
  }

  /**
   * ʵ�ֽӿ�DocumentListener�ķ���
   * @param e Document�¼�
   */
  public void removeUpdate(DocumentEvent e) {
    fireTextChanged();
  }

  /**
   * �׳��ı��ı��¼�
   */
  protected void fireTextChanged() {
    TextEvent event = new TextEvent(this, 900);
    for (int j = 0; j < listener.size(); j++) {
      ( (TextListener) listener.elementAt(j)).textValueChanged(event);
    }
  }

  /**
   * ɾ��ĳ���ı�������
   * @see TextListener
   * @param tListener �ı�������
   */
  public void removeTextListener(TextListener tListener) {
    listener.removeElement(tListener);
  }

}

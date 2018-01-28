package com.faw_qm.cappclients.conscapproute.util;

import java.awt.event.InputEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * ��ť����ctrl��ݼ� Copyright: Copyright (c) 2004 Company: һ������
 * @author xucy
 * @version 1.0
 */
public class ButtonCtrlHotKey
{
    private ButtonCtrlHotKey()
    {}
    public static void addHotKey(JButton btn, int hotKeyCode)
    {
        if(btn.getActionListeners().length > 0)
        {

            KeyStroke ks = KeyStroke.getKeyStroke(hotKeyCode, InputEvent.CTRL_MASK, false);
            InputMap map = btn.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
            map.put(ks, "key");
            ActionAdapter a = new ActionAdapter(btn.getActionListeners()[0]);
            a.setActionCommandOld(btn.getActionCommand());
            btn.getActionMap().put("key", a);
        }
    }
}
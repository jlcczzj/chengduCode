package com.faw_qm.cappclients.conscapproute.util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
public class ActionAdapter implements Action {
    private ActionListener actionA = null;
    private String actionCommandOld = null ;
    public ActionAdapter(ActionListener x) {
        actionA = x;
    }

    public void actionPerformed(ActionEvent e) {
        ActionEvent e1 = new ActionEvent(e.getSource(),e.getID(),this.getActionCommandOld());
         System.out.println("");
        actionA.actionPerformed(e1);
    }

    /**
     * Gets one of this object's properties
     * using the associated key.
     * @see #putValue
     */
    public Object getValue(String key) {
        return "A";
    }

    /**
     * Sets one of this object's properties
     * using the associated key. If the value has
     * changed, a <code>PropertyChangeEvent</code> is sent
     * to listeners.
     *
     * @param key    a <code>String</code> containing the key
     * @param value  an <code>Object</code> value
     */
    public void putValue(String key, Object value) {}

    /**
     * Sets the enabled state of the <code>Action</code>.  When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     * If the value has changed, a <code>PropertyChangeEvent</code> is sent
     * to listeners.
     *
     * @param  b true to enable this <code>Action</code>, false to disable it
     */
    public void setEnabled(boolean b) {}

    /**
     * Returns the enabled state of the <code>Action</code>. When enabled,
     * any component associated with this object is active and
     * able to fire this object's <code>actionPerformed</code> method.
     *
     * @return true if this <code>Action</code> is enabled
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * Adds a <code>PropertyChange</code> listener. Containers and attached
     * components use these methods to register interest in this
     * <code>Action</code> object. When its enabled state or other property
     * changes, the registered listeners are informed of the change.
     *
     * @param listener  a <code>PropertyChangeListener</code> object
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {}

    /**
     * Removes a <code>PropertyChange</code> listener.
     *
     * @param listener  a <code>PropertyChangeListener</code> object
     * @see #addPropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {}

    public String getActionCommandOld() {
        return actionCommandOld;
    }

    public void setActionCommandOld(String actionCommandOld) {
        this.actionCommandOld = actionCommandOld;
    }


}

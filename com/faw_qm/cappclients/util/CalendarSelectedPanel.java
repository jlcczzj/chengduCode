package com.faw_qm.cappclients.util;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.faw_qm.cappclients.resource.view.CalendarSelectedDialog;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CalendarSelectedPanel extends JPanel
{
    private JTextField dateField = new JTextField();
    private JButton browseButton = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    CalendarSelectedDialog selectDialog;
    String date;
    public CalendarSelectedPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit()
            throws Exception
    {
        this.setLayout(gridBagLayout1);
        browseButton.setMaximumSize(new Dimension(65, 23));
        browseButton.setMinimumSize(new Dimension(65, 23));
        browseButton.setPreferredSize(new Dimension(65, 23));
        browseButton.setActionCommand("‰Ø¿¿");
        browseButton.setText("‰Ø¿¿");
        browseButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                browseButton_actionPerformed(e);
            }
        });
        dateField.setEnabled(false);
        dateField.setEditable(false);
        this.setEnabled(true);
        this.add(dateField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 4), 0, 0));
        this.add(browseButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 4, 0, 0), 0, 0));
    }

    public static void main(String args[])
    {
        JFrame f = new JFrame();
        f.getContentPane().add(new CalendarSelectedPanel());

        f.setSize(200, 200);
        f.setVisible(true);

    }

    void browseButton_actionPerformed(ActionEvent e)
    {
        selectDialog = new CalendarSelectedDialog(null, "", true);
        selectDialog.setSize(400, 300);
        selectDialog.setLocation(200, 100);
        selectDialog.setSize(650, 500);
        selectDialog.setVisible(true);
        date = selectDialog.getDate();
        this.dateField.setText(date);
    }

    public void setDate(String date)
    {
        this.dateField.setText(date);
    }

    public String getDate()
    {
        return this.dateField.getText().trim();
    }

    public void setBrowseButtonEntable(boolean bool)
    {
        this.browseButton.setEnabled(bool);
    }

}
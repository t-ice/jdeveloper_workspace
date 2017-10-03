/* $Header: MyDockable.java 20-aug-2007.14:48:56 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Some code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.dockablewindow;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;

import oracle.ide.docking.DockableWindow;
import oracle.ide.layout.ViewId;

import oracle.javatools.icons.OracleIcons;


public final class MyDockable extends DockableWindow {
    private static final String VIEW_NAME = "MyDockable";
    public static final ViewId VIEW_ID =
        new ViewId(MyDockableFactory.FAMILY, VIEW_NAME);
    private JComponent ui;

    public MyDockable() {
        super(VIEW_ID.getId());
    }

    public Component getGUI() {
        if (ui == null) {
            ui = new JButton("Hello");
        }

        return ui;
    }

    public String getTitleName() {
        return "Title of my Dockable";
    }

    public String getTabName() {
        return "Tab of MyDockable";
    }

    @Override
    public Icon getTabIcon() {
        return OracleIcons.getIcon( OracleIcons.USER );
    }
}

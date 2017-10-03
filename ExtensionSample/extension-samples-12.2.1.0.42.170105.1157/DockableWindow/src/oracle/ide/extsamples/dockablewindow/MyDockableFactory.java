/* $Header: jdev/src/esdk-samples/sample/DockableWindow/src/oracle/ide/extsamples/dockablewindow/MyDockableFactory.java /main/5 2012/10/31 14:14:36 kharezla Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Some code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.dockablewindow;

import oracle.ide.IdeConstants;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.docking.DockableFactory;
import oracle.ide.docking.DockingParam;
import oracle.ide.layout.ViewId;

public final class MyDockableFactory implements DockableFactory {
    public static final String FAMILY = "MyDockable";

    private MyDockable myDockable;

    /* This method will only be called the first time this factory is encountered in a layout.
   */

    public void install() {
        final DockStation dockStation = DockStation.getDockStation();
        DockingParam dp = new DockingParam();
        dp.setPosition(IdeConstants.SOUTH);
        dockStation.dock(getMyDockable(), dp);
    }

    /* A factory can be responsible for multiple dockables.  For example, there is only one debugger factory to control
     * the debugger windows.
     * The view ID will be MyDockableFactory.FAMILY + "." + MyDockable.VIEW_ID
     */
    public Dockable getDockable(ViewId viewId) {
        if (MyDockable.VIEW_ID.equals(viewId)) return getMyDockable();
        return null;
    }

    private MyDockable getMyDockable() {
        if (myDockable == null) myDockable = new MyDockable();
    
        return myDockable;
    }
}

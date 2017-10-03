/* $Header: jdev/src/esdk-samples/sample/DockableWindow/src/oracle/ide/extsamples/dockablewindow/ViewDockableCommand.java /main/3 2012/10/31 14:14:36 kharezla Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/20/07 - Initial revision
 */

package oracle.ide.extsamples.dockablewindow;

import oracle.ide.AddinManager;
import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.docking.DockStation;
import oracle.ide.docking.Dockable;
import oracle.ide.extension.RegisteredByExtension;

/**
 * Command handler for esdksample.exampleDockable.
 */
@RegisteredByExtension("oracle.ide.extsamples.dockablewindow")
public final class ViewDockableCommand extends Command {
    public ViewDockableCommand() {
        super(actionId());
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("esdksample.exampleDockable");
        if (cmdId == null)
            throw new IllegalStateException("Action esdksample.exampleDockable not found.");
        return cmdId;
    }

    public int doit() {
        final DockStation dockStation = DockStation.getDockStation();
        final Dockable dockable = dockStation.findDockable(MyDockable.VIEW_ID);
        dockStation.setDockableVisible(dockable, true);
        return OK;
    }

}

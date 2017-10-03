/* $Header: jdev/src/esdk-samples/sample/ProjectSettings/src/oracle/ide/extsamples/projectsettings/ProjectSettingsAddin.java /main/5 2010/09/26 16:12:13 alfarouk Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       03/28/07 - Remove programmatic registration of project
                           properties panel and action (moved to extension.xml)
    bduff       02/27/07 - Added copyright banner
 */

package oracle.ide.extsamples.projectsettings;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuManager;

final class ProjectSettingsAddin implements Addin {
    public static final int CMDID_DUMP_MYJPR_PROPS =
        Ide.findOrCreateCmdID("Samples.CMDID_DUMP_MYJPR_PROPS");


    public void initialize() {
       /*
        * Menu items are now added declaratively. Keeping old code commented
        * out for reference. Please refer to extension.xml for details. 
        */
       // JMenu toolsMenu = MenuManager.getJMenu(IdeMainWindow.MENU_TOOLS);
       //toolsMenu.add(createDumpInfoMenuItem());
    }
    
    // private final JMenuItem createDumpInfoMenuItem() {
    //     IdeAction action = IdeAction.find(CMDID_DUMP_MYJPR_PROPS);
    //     return Ide.getMenubar().createMenuItem(action);
    // }
}



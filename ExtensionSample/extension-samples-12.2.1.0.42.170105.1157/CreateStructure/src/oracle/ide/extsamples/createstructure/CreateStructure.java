/* $Header: jdev/src/esdk-samples/sample/CreateStructure/src/oracle/ide/extsamples/createstructure/CreateStructure.java /main/4 2010/09/20 12:00:49 alfarouk Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS. Fix some minor code issues.
    bduff       02/27/07 - Big rewrite for 11.1.1 ESDK
    bduff       02/27/07 - Added copyright banner
 */

package oracle.ide.extsamples.createstructure;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuConstants;
import oracle.ide.controller.MenuManager;


final class CreateStructure implements Addin {
    // This id matches the one defined in the extension manifest.
    static final int CREATE_STRUCT_CMD_ID =
        Ide.findCmdID("ExtensionSamples.CREATE_STRUCT_CMD_ID");

    public void initialize() {
        //installMenuItem(); // we now add menu items programmatically. 
                             //see extension.xml for details. 
    }

    private void installMenuItem() {
        //IdeAction action = IdeAction.find(CREATE_STRUCT_CMD_ID);
        //Ide.getMenubar().add(Ide.getMenubar().createMenuItem(action),
        //                     MenuManager.getJMenu(IdeMainWindow.MENU_TOOLS),
        //                     MenuConstants.SECTION_TOOLS_ADDINS);
    }
}

/* $Header: jdev/src/esdk-samples/sample/ConfigPanel/src/oracle/ide/extsamples/configpanel/UserInformationAddin.java /main/4 2012/09/14 14:01:19 dlane Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor code cleanup.
    bduff       02/27/07 - Big rewrite for 11.1.1 ESDK
 */
package oracle.ide.extsamples.configpanel;

import oracle.javatools.data.StructureChangeEvent;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.IdeMainWindow;
import oracle.ide.config.Preferences;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.MenuConstants;
import oracle.ide.controller.MenuManager;

import oracle.ide.log.LogManager;
import oracle.ide.log.LogPage;

import oracle.javatools.data.StructureChangeListener;

/**
 * This addin installs a menu item and demonstrates how to listen for
 * changes to a preferences object.
 */
final class UserInformationAddin implements Addin {
    // This action id (esdk.configpanel.writeToLog) matches the one defined in
    // extension.xml.
    static final int CMD_ID = Ide.findCmdID("esdk.configpanel.writeToLog");

    public void initialize() {
        listenForPreferenceChanges();
    }


    private void listenForPreferenceChanges() {
        // This is how to listen for changes to a preference model.
        UserInformation info =
            UserInformation.getInstance(Preferences.getPreferences());
        info.addStructureChangeListener(new StructureChangeListener() {
                    public void structureValuesChanged(StructureChangeEvent e) {
                        // Note:  writing to the log page via the LogManager during intialize
                        // would be dangerous as the log page UI may not yet be created or available.
                        // In this case it will be written to only after a StructureChangeEvent so
                        // it should be safe.
                        log("UserInformation preferences changed");
                    }
                });
    }

    static void log(String message) {
        LogPage msgPage = LogManager.getLogManager().getMsgPage();
        msgPage.log("(ESDK Sample - ConfigPanel) ");
        msgPage.log(message + "\n");
    }
}

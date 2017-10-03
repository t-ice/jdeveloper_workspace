/* $Header: jdev/src/esdk-samples/sample/CustomExtensionHook/src/oracle/ide/samples/customhook/WidgetController.java /main/4 2010/04/27 19:35:05 jrstephe Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

import java.util.List;

import javax.ide.extension.ExtensionRegistry;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.log.LogManager;

public class WidgetController implements Controller {
    public boolean handleEvent(IdeAction ideAction, Context context) {
        WidgetsProvider provider = WidgetsProvider.getInstance();
        List<Widget> widgets = provider.getWidgets();

        for (Widget w : widgets) {
            LogManager.getLogManager().getMsgPage().log(w.toString() + "\n");
        }
        return true;
    }

    public boolean update(IdeAction ideAction, Context context) {
        return true;
    }
}

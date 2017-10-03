/* $Header: LayoutMenuFilterController.java 21-nov-2007.19:07:22 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       11/21/07 - Bug 6624651 - fix compilation warning.
    bduff       08/20/07 - Reformat to JCS. Minor code improvement.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.layoutmenufilter;

import oracle.ide.AddinManager;
import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.layout.Layout;
import oracle.ide.layout.Layouts;

public class LayoutMenuFilterController implements Controller {
    private Layout _savedLayout;

    public boolean handleEvent(IdeAction ideAction, Context context) {
        LayoutMenuFilterAddin addin = AddinManager.getAddinManager().getAddin(LayoutMenuFilterAddin.class);

        if (addin == null) {
            throw new IllegalStateException("Addin not found!");
        }

        final Layouts layouts = Layouts.getLayouts();
        if (layouts.getActive() == addin.getLayout() && _savedLayout != null) {
            layouts.activateLayout(_savedLayout);
        } else {
            _savedLayout = layouts.getActive();
            layouts.activateLayout(addin.getLayout());
        }

        return true;
    }

    public boolean update(IdeAction ideAction, Context context) {
        return true;
    }
}

/* $Header: HelloX.java 16-aug-2007.22:14:01 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.hellox;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.wizard.Wizard;


public final class HelloX extends Wizard {
    private static final String WIZARD_NAME = "HelloX (ESDK Sample)";
    private static final String ICON_NAME = "HelloX.gif";

    private Icon image = null;

    public String getShortLabel() {
        return WIZARD_NAME;
    }

    public boolean isAvailable(Context context) {
        return true;
    }

    public boolean invoke(Context ctx) {
        if (!isAvailable(ctx))
            return false;

        String greetee = null;
        greetee =
                JOptionPane.showInputDialog(Ide.getMainWindow(), "Enter your name:",
                                            WIZARD_NAME,
                                            JOptionPane.OK_CANCEL_OPTION);

        if (greetee == null) // User canceled
            return false;

        JOptionPane.showMessageDialog(Ide.getMainWindow(),
                                      "Hello " + greetee + "!", WIZARD_NAME,
                                      JOptionPane.INFORMATION_MESSAGE);

        return true;
    }


    public Icon getIcon() {
        if (image == null) {
            image = new ImageIcon(HelloX.class.getResource(ICON_NAME));
        }
        return image;
    }

}

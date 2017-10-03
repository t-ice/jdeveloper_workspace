/* $Header: SampleBean.java 16-aug-2007.22:25:48 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/21/07 - Add copyright header. Reformat to JCS.
 */
package oracle.ide.extsamples.pageprovider;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SampleBean {
    public SampleBean(JPanel inPanel) {
        JOptionPane.showMessageDialog(inPanel, "This is a sample bean",
                                      "Sample Bean",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
}

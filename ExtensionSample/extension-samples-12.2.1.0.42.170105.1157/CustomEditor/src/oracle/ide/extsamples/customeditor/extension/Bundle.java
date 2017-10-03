/* $Header: Bundle.java 16-aug-2007.21:53:03 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */

package oracle.ide.extsamples.customeditor.extension;

import java.text.MessageFormat;

import java.util.ResourceBundle;

public final class Bundle {
    static {
        Bundle.initializeBundle("oracle.ide.extsamples.customeditor.resources.Bundle"); //$NON-NLS-1$
    }
    private static ResourceBundle rb;

    private Bundle() {
    }

    public static void initializeBundle(String bundleName) {
        rb = ResourceBundle.getBundle(bundleName);
    }

    public static ResourceBundle getBundle() {
        return rb;
    }

    public static String getString(String key) {
        return rb.getString(key);
    }

    public static String getString(String key, String... args) {
        final String value = rb.getString(key);
        return MessageFormat.format(value, args);
    }
}

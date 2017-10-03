/* $Header: WidgetListenerExample.java 16-aug-2007.22:10:43 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

/**
 * This class provides an example of a lazily loaded declaratively registered
 * class. WidgetListenerExample is registered in the widgets custom hook
 * in extension.xml and retrieved by the Widget hash structure adapter.
 */
public class WidgetListenerExample implements WidgetListener {
    public void widgetEvent(Object o) {
        // NO-OP
    }
}

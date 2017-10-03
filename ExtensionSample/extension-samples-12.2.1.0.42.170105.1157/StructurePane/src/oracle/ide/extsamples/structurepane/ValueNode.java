/* $Header: ValueNode.java 16-aug-2007.22:22:34 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.structurepane;

import oracle.ide.model.DefaultElement;

public final class ValueNode extends DefaultElement {
    private final String name;
    private final Object value;

    ValueNode(String name, Object value) {
        this.name = name;
        this.value = value;
    }


    public String getShortLabel() {
        if (name == null) {
            return String.valueOf(value);
        }
        if (value == null) {
            return "<null>";
        }
        return name + " = " + String.valueOf(value);
    }

}

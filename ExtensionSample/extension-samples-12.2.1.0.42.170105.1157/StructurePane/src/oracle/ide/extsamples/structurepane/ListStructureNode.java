/* $Header: ListStructureNode.java 16-aug-2007.22:22:24 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.structurepane;

import java.util.Iterator;

import oracle.ide.model.DefaultFolder;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.ListStructure;

public final class ListStructureNode extends DefaultFolder {
    private final String name;
    private final ListStructure listStructure;
    private boolean populated;

    public ListStructureNode(String name, ListStructure listStructure) {
        this.name = name;
        this.listStructure = listStructure;
    }

    public Iterator getChildren() {
        if (!populated) {
            populated = true;
            for (Object value : listStructure) {
                if (value instanceof HashStructure) {
                    add(new HashStructureNode(null, (HashStructure)value));
                } else if (value instanceof ListStructure) {
                    add(new ListStructureNode(null, (ListStructure)value));
                } else {
                    add(new ValueNode(null, value));
                }
            }
        }
        return super.getChildren();
    }

    public String getShortLabel() {
        if (name == null) {
            return "<list>";
        }
        return name;
    }
}

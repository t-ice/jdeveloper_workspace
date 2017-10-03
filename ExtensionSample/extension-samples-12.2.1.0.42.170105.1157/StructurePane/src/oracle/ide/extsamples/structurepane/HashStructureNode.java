/* $Header: HashStructureNode.java 16-aug-2007.22:22:20 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.structurepane;

import java.util.Iterator;
import java.util.Set;

import oracle.ide.model.DefaultFolder;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.ListStructure;


public final class HashStructureNode extends DefaultFolder {
    private final String name;
    private final HashStructure hashStructure;
    private boolean populated = false;

    public HashStructureNode(String name, HashStructure hashStructure) {
        this.name = name;
        this.hashStructure = hashStructure;
    }

    public Iterator getChildren() {
        if (!populated) {
            populated = true;
            Set<String> keys = hashStructure.keySet();
            for (String key : keys) {
                Object value = hashStructure.getObject(key);
                if (value instanceof HashStructure) {
                    add(new HashStructureNode(key, (HashStructure)value));
                } else if (value instanceof ListStructure) {
                    add(new ListStructureNode(key, (ListStructure)value));
                } else {
                    add(new ValueNode(key, value));
                }
            }
        }

        return super.getChildren();
    }

    public String getShortLabel() {
        if (name == null) {
            return "<hash>";
        }
        return name;
    }
}

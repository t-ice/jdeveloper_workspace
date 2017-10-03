/* $Header: PropertyStorageExplorer.java 16-aug-2007.22:22:31 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.structurepane;

import oracle.ide.Context;
import oracle.ide.explorer.AbstractTreeExplorer;
import oracle.ide.model.Element;

import oracle.javatools.data.PropertyStorage;

public class PropertyStorageExplorer extends AbstractTreeExplorer {
    protected Element getElementForContext(Context context) {
        Element[] selection = context.getSelection();
        if (selection != null && selection.length == 1 &&
            selection[0] instanceof PropertyStorage) {
            return new HashStructureNode(null,
                                         ((PropertyStorage)selection[0]).getProperties());
        }
        return null;
    }
}

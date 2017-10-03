/* $Header: FavoritesFolder.java 20-aug-2007.14:19:21 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/20/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.customnavigator;


import java.io.IOException;

import java.net.URL;

import javax.swing.Icon;

import javax.swing.ImageIcon;

import oracle.ide.Ide;
import oracle.ide.marshal.xml.HashStructureIO;
import oracle.ide.model.DataContainer;
import oracle.ide.model.ElementAttributes;

import oracle.ide.net.URLFactory;

import oracle.javatools.marshal.ObjectStore;

// DataContainer includes built in support for persistence of the list of
// children to a file.
// If you want a more abstract container (e.g. based on information
// from a database), you can subclass DefaultFolder.
public class FavoritesFolder extends DataContainer {
    // XML namespace and root element name for the persistent file behind this
    // node.
    public static final String NAMESPACE_URI =
        "http://xmlns.oracle.com/ide-samples/favorites"; //NOTRANS
    public static final String ROOT_QNAME = "favorites"; //NOTRANS

    private static final String FILENAME = "favorites.xml"; // NOTRANS

    private static FavoritesFolder favoritesFolder;

    private final Icon icon;

    public FavoritesFolder(URL url) {
        super(url);
        getAttributes().set(ElementAttributes.SAVEABLE | // The node can be saved.
                ElementAttributes.NAVIGABLE); // Can create new navigators on the node.
            

        icon = new ImageIcon(getClass().getResource("favorites.png"));
    }

    // We want to use HashStructureIO to persist this node to xml.

    protected final ObjectStore newObjectStore() {
        return new HashStructureIO(NAMESPACE_URI, ROOT_QNAME);
    }

    // Override this to tell the persistence mechanism in super.saveImpl() to
    // use the hash structure associated with this node instead of the node
    // itself when persisting to XML. Overriding this is necessary if you
    // also override newObjectStore() to indicate that HashStructureIO is the
    // persistence mechanism.

    protected final Object getObjectStoreTarget() {
        return getProperties();
    }


    /**
     * Gets the one and only FavoritesFolder instance.
     *
     * @return
     */
    public static final FavoritesFolder getFavoritesFolder() {
        if (favoritesFolder == null) {
            URL url =
                URLFactory.newFileURL(Ide.getSystemDirectory() + "/" + FILENAME);
            favoritesFolder = new FavoritesFolder(url);
        }
        return favoritesFolder;
    }

    // The default implementation uses the default file icon for unrecognized
    // files. This is also used for the icon of our navigator, so we want to
    // use something more user friendly.

    public Icon getIcon() {
        return icon;
    }

    // The default implementation uses the file name of the URL associated with
    // this folder (i.e. "favorites.xml"). We want something more user friendly,
    // this is also used for the tab name of our navigator by default.

    public String getShortLabel() {
        return "Favorites";
    }

    // We override markDirty to essentially shield the user from the fact that
    // the favorites node has a file behind it. Without this override, the
    // top level "Favorites" container would become dirty (indicated by italics)
    // and its state only written to disk when the user does save or save all.

    public void markDirty(boolean dirty) {
        super.markDirty(dirty);

        try {
            super.save();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

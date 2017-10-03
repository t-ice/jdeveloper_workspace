/* $Header: Widget.java 16-aug-2007.22:10:31 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

import javax.ide.util.IconDescription;

import oracle.ide.extension.LazyClassAdapter;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;

/**
 * A widget.
 *
 * @author Brian.Duff@oracle.com
 */
public final class Widget extends HashStructureAdapter {
    private WidgetListener listener;

    private Widget(HashStructure hash) {
        super(hash);
    }

    /**
     * Gets an instance of a widget adapting the specified hash structure.
     *
     * @param hash hash structure to adapt.
     * @return an instance of a widget.
     */
    public static Widget getInstance(HashStructure hash) {
        return new Widget(hash);
    }

    /**
     * Gets the id.
     *
     * @return the id.
     */
    public String getID() {
        return _hash.getString("id");
    }

    /**
     * Gets the icon.
     *
     * @return the icon.
     */
    public IconDescription getIcon() {
        return IconDescription.createInstance(_hash.getURL("icon"));
    }

    /**
     * Gets the description.
     *
     * @return the description.
     */
    public String getDescription() {
        return _hash.getString("description/#text");
    }

    /**
     * Gets the name.
     * @return the name.
     */
    public String getName() {
        return _hash.getString("name/#text");
    }

    /**
     * Gets the listener.
     * @return
     */
    public WidgetListener getListener() {
        if (listener == null) {
            // This demonstrates using LazyClassAdapter to load a class
            // lazily from a fully qualified class name in the extension
            // manifest.
            LazyClassAdapter adapter = LazyClassAdapter.getInstance(_hash);
            listener =
                    adapter.createInstance(WidgetListener.class, "listener-class/#text");
        }
        return listener;
    }

    /**
     * Gets a string representation of this widget.
     *
     * @return a string representation of this widget.
     */
    public String toString() {
        return String.format("Widget[ ID=%s, Name=%s, Description=%s, Listener=%s ]",
                             String.valueOf(getID()),
                             String.valueOf(getName()),
                             String.valueOf(getDescription()),
                             String.valueOf(getListener()));
    }
}

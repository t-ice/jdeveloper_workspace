/* $Header: ExampleFlatEditor.java 16-aug-2007.22:08:20 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.flateditor;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.UIManager;

import oracle.ide.editor.Editor;
import oracle.ide.model.UpdateMessage;

/**
 * Extension SDK Sample of a "flat" editor, similar to the "Overview" editors
 * that appear in various places in the 11 release onwards.
 */
final class ExampleFlatEditor extends Editor {
    private EditorPanel _panel = null;


    /////////////////////////////////////////////////////////////////////////////
    // Methods from oracle.ide.editor.Editor
    /////////////////////////////////////////////////////////////////////////////

    @Override
    public Object getEditorAttribute(String attribute) {
        // By convention, flat editors use the window color as their background
        // color. The window color is usually white on most systems.
        if (ATTRIBUTE_BACKGROUND_COLOR.equals(attribute))
            return UIManager.getColor("window");

        // If you want framework provided scrolling, comment out the next three
        // if conditions. You probably want to control your own scrolling if your
        // editor contains category tabs.
        if (ATTRIBUTE_SCROLLABLE.equals(attribute))
            return Boolean.FALSE;

        if (ATTRIBUTE_HORIZONTAL_SCROLLBAR_POLICY.equals(attribute))
            return JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;

        if (ATTRIBUTE_VERTICAL_SCROLLBAR_POLICY.equals(attribute))
            return JScrollPane.VERTICAL_SCROLLBAR_NEVER;

        return super.getEditorAttribute(attribute);
    }

    public void open() {
    }

    public Component getGUI() {
        if (_panel == null)
            _panel = new EditorPanel();
        return _panel;
    }

    public void update(Object object, UpdateMessage updateMessage) {
    }
}

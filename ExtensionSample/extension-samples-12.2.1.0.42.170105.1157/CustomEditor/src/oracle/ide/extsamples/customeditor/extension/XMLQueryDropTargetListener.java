/* $Header: XMLQueryDropTargetListener.java 16-aug-2007.21:53:17 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */

package oracle.ide.extsamples.customeditor.extension;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;

import java.io.IOException;

import javax.swing.JTextField;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


final class XMLQueryDropTargetListener extends DropTargetAdapter {
    XMLQueryDropTargetListener() {
    }

    public void drop(DropTargetDropEvent dtde) {
        final DataFlavor[] flavs =
            dtde.getTransferable().getTransferDataFlavors();
        for (DataFlavor f : flavs) {
            if (f.getRepresentationClass().isAssignableFrom(DocumentFragment.class)) {
                try {
                    final Object o = dtde.getTransferable().getTransferData(f);
                    if (o instanceof DocumentFragment) {
                        setTextFieldValue(dtde, (DocumentFragment)o);
                        // We tell the drag source that the DnD was not a success
                        // since we don't want anything other than the name.
                        dtde.dropComplete(false);
                        break;
                    }
                } catch (UnsupportedFlavorException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setTextFieldValue(DropTargetDropEvent dtde,
                                   DocumentFragment frag) {
        final JTextField textField =
            (JTextField)((DropTarget)dtde.getSource()).getComponent();
        final NodeList children = frag.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);
            textField.setText("//" + child.getNodeName());
        }
    }

    public void dragEnter(DropTargetDragEvent dtde) {
        final DataFlavor[] flavs =
            dtde.getTransferable().getTransferDataFlavors();
        for (DataFlavor f : flavs) {
            if (f.getRepresentationClass().isAssignableFrom(DocumentFragment.class)) {
                dtde.acceptDrag(DnDConstants.ACTION_COPY);
                return;
            }
        }
        dtde.rejectDrag();
    }
}

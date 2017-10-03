/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class NodeReferenceRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus, int row,
                                                   int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if ( !(value instanceof NodeReference ) ) return c;
        
        NodeReference ref = (NodeReference) value;
        setText( ref.shortLabel() );
        setIcon( ref.icon() );
        setToolTipText( ref.url().toString() );
        
        if ( !isSelected ) determineRowColor(table, ref);
        
        return c;
    }

    private void determineRowColor(JTable table, NodeReference ref) {
        NodeListModel model = (NodeListModel) table.getModel();
        setBackground( colorForState( table, model.state( ref ) ) );
    }
    
    private Color colorForState( JTable table, NodeListModel.NodeState state ) {
        switch ( state ) {
        case RECENTLY_CLOSED:
            return Color.RED;
        case RECENTLY_OPENED:
            return Color.GREEN;
        case NORMAL:
            return table.getBackground();
        }
        return null;
    }
}

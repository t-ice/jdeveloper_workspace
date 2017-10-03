/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import java.awt.BorderLayout;

import java.awt.Dimension;

import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import oracle.ide.model.Node;

/**
 * A panel which displays a list of nodes.
 */
final class NodeListPanel extends JPanel {
    private final JTable table = new JTable();
    
    NodeListPanel() {
        setLayout( new BorderLayout() );
        JScrollPane pane = new JScrollPane( table );
        add( pane, BorderLayout.CENTER );
        
        table.setModel( new NodeListModel() );
        table.setDefaultRenderer( NodeReference.class, new NodeReferenceRenderer() );
        table.setTableHeader( null );
        table.setRowHeight( 18 );
        table.setShowGrid( false );
        table.setIntercellSpacing( new Dimension( 0, 0 ) );
        table.setOpaque( false );
        table.setBorder( null );
        pane.setOpaque( false );
        pane.getViewport().setOpaque( false );
        pane.getViewport().setBorder( null );
        pane.setBorder( null );
        pane.setViewportBorder( null );
    }
    
    void refresh( Set<Node> openNodes ) {
        ((NodeListModel)table.getModel()).refresh( openNodes );
    }    
}

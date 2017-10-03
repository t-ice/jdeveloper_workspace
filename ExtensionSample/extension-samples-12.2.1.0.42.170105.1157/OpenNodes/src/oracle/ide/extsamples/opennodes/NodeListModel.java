/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.logging.Logger;

import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

import oracle.ide.model.Node;

/**
 * <b>Thread safety:</b> all methods on this class must be called from the 
 * event thread.
 */
final class NodeListModel extends AbstractTableModel {
    private static final Logger LOG = Logger.getLogger( NodeListModel.class.getName() );
    
    private final List<NodeReference> visibleNodes = new ArrayList<NodeReference>();
    private final Map<NodeReference,NodeState> nodeStates = new HashMap<NodeReference,NodeState>();
    private final Timer normalizeTimer = new Timer( 2000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            whenNormalizeTimerFires();
        }
    });
    
    public NodeListModel()
    {
        normalizeTimer.setRepeats( false );
    }

    public int getRowCount() {
        return visibleNodes.size();
    }

    public int getColumnCount() {
        return 1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return visibleNodes.get( rowIndex );
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return NodeReference.class;
    }


    
    NodeState state( NodeReference reference ) {
        return nodeStates.get( reference );
    }
    
    /**
     * Tells the node list model to refresh itself, given the specified
     * set of open nodes.
     */
    public void refresh( Set<Node> openNodes ) {
        stopNormalizeTimer();
        boolean changed = normalize();
        
        // Convert the set of open nodes into a set of NodeReferences.
        Set<NodeReference> refs = toNodeReferences( openNodes );
        changed = addNewNodes(refs) | changed;
        changed = removeOldNodes(refs) | changed;
        
        if ( changed ) {
            fireTableDataChanged();            
            startNormalizeTimer();
        }
    }

    private boolean removeOldNodes(Set<NodeReference> refs) {
        boolean changed = false;
        for ( NodeReference ref : visibleNodes ) {
            if ( !refs.contains( ref ) ) {
                changed = true;
                nodeStates.put( ref, NodeState.RECENTLY_CLOSED );
            }
        }
        return changed;
    }

    private boolean addNewNodes(Set<NodeReference> refs) {
        Set<NodeReference> nodesToAdd = new HashSet<NodeReference>( refs );
        nodesToAdd.removeAll( nodeStates.keySet() );
        
        if ( nodesToAdd.isEmpty() ) return false;
        
        for ( NodeReference ref : nodesToAdd ) {
            visibleNodes.add( ref );
            nodeStates.put( ref, NodeState.RECENTLY_OPENED );
        }
        return true;
    }
    
    /**
     * Move all nodes to the "normal" state, and remove from the model any
     * nodes that were in the RECENTLY_CLOSED state.
     */
    private boolean normalize() {
        LOG.fine( "Normalizing.");
        boolean changed = false;
        for ( Iterator<NodeReference> i = visibleNodes.iterator(); i.hasNext(); ) {
            NodeReference ref = i.next();
            NodeState state = nodeStates.get( ref );
            if ( state == NodeState.RECENTLY_OPENED ) {
                nodeStates.put( ref, NodeState.NORMAL );
                changed = true;
            }
            else if ( state == NodeState.RECENTLY_CLOSED ) {
                i.remove();
                nodeStates.remove( ref );
                changed = true;
            }
        }
        return changed;
    }
    
    private void startNormalizeTimer() {
        normalizeTimer.restart();
    }
    
    private void stopNormalizeTimer() {
        normalizeTimer.stop();
    }
    
    private void whenNormalizeTimerFires() {
        LOG.fine( "Normalize timer fired");
        if ( normalize() ) fireTableDataChanged();
    }
    
    private Set<NodeReference> toNodeReferences( Collection<Node> nodes ) {
        Set<NodeReference> references = new HashSet<NodeReference>( nodes.size() );
        for ( Node node : nodes ) {
            if ( node.getURL() == null ) continue;
            references.add( new NodeReference( node.getURL(), node.getIcon(), node.getShortLabel() ) );
        }
        return references;
    }

    public static enum NodeState {
        NORMAL,
        RECENTLY_OPENED,
        RECENTLY_CLOSED
    }
}

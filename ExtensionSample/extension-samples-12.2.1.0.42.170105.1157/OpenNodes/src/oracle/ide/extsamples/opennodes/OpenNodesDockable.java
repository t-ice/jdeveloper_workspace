/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Icon;

import javax.swing.Timer;

import oracle.ide.docking.DockableEvent;
import oracle.ide.docking.DockableWindow;
import oracle.ide.layout.ViewId;

import oracle.ide.model.Node;

import oracle.ide.model.NodeFactory;

import oracle.javatools.icons.OracleIcons;

final class OpenNodesDockable extends DockableWindow {
    private static final String VIEW_NAME = "openNodesDockable";
    public static final ViewId VIEW_ID = 
        new ViewId( OpenNodesDockableFactory.ID, VIEW_NAME );
    private NodeListPanel panel;
    
    private final Timer updateTimer = new Timer( 1000, new ActionListener() {
        public void actionPerformed( ActionEvent e ) {
            whenTimerFires();
        }
    });
    
    
    public OpenNodesDockable() {
        super( VIEW_ID.getId() );
    }

    @Override
    public Component getGUI() {
        return nodeListPanel();
    }
    
    private NodeListPanel nodeListPanel() {
        if ( panel == null ) panel = new NodeListPanel();
        return panel;
    }

    public String getTitleName() {
        return "Open Node Tracker";
    }
    
    public String getTabName() {
        return getTitleName();
    }

    @Override
    public Icon getTabIcon() {
        return OracleIcons.getIcon( OracleIcons.INDEX );
    }

    @Override
    protected void dockableShown(DockableEvent dockableEvent) {
        super.dockableShown(dockableEvent);
        updateTimer.start();
    }

    @Override
    protected void dockableHidden(DockableEvent dockableEvent) {
        super.dockableHidden(dockableEvent);
        updateTimer.stop();
    }
    
    private void whenTimerFires() {
        Set<Node> nodes = new HashSet<Node>();
        for ( Iterator<? extends Node> i = NodeFactory.getOpenNodes(); i.hasNext(); ) {
            nodes.add( i.next() );
        }
        nodeListPanel().refresh( nodes );
    }
}

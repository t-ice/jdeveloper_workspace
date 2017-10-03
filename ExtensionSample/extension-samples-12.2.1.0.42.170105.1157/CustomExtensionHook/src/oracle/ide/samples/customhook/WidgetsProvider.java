/* $Header: jdev/src/esdk-samples/sample/CustomExtensionHook/src/oracle/ide/samples/customhook/WidgetsProvider.java /main/4 2010/04/27 19:35:05 jrstephe Exp $ */

/* Copyright (c) 2007, 2010, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.samples.customhook;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ide.extension.ElementName;

import oracle.ide.ExtensionRegistry;
import oracle.ide.extension.HashStructureHook;
import oracle.ide.extension.HashStructureHookEvent;
import oracle.ide.extension.HashStructureHookListener;

import oracle.javatools.data.HashStructure;

/**
 * An example of how to use HashStructureHook as the
 * handler for your custom hook.
 *
 * @author Brian.Duff@oracle.com
 */
public final class WidgetsProvider {
    
    /**
     * The element name that identifies this hook. This can be used to
     * retrieve the single instance of this hook from
     * ExtensionRegistry.getHook().
     */
    public static final ElementName NAME =
        new ElementName("http://xmlns.oracle.com/ide/samples/customhooks",
                        "widgets");
    
    private static WidgetsProvider _sInstance = new WidgetsProvider();
    private List<Widget> _widgetList = new CopyOnWriteArrayList<Widget>();
    private List<Widget> _unmodifiableWidgetList = Collections.unmodifiableList(_widgetList);
    private WidgetHashStructureHookListener _listener;
    
    /**
     * Get the singleton WidgetsProvider instance
     * @return
     */
    public static WidgetsProvider getInstance() {
        return _sInstance;
    }
    
    /**
     * Returns an unmodifiable List of Widget instances
     *
     * @return the widgets
     */
    public List<Widget> getWidgets() {
        _initializeIfNeeded();
        return _unmodifiableWidgetList;
    }
    
    private synchronized void _initializeIfNeeded() {
        if (_listener == null) {
            
            ExtensionRegistry registry = ExtensionRegistry.getExtensionRegistry();
            HashStructureHook widgetsHook = (HashStructureHook) registry.getHook(NAME);
            _listener = new WidgetHashStructureHookListener();
            widgetsHook.addHashStructureHookListener(_listener);
        }
    }
    
    private WidgetsProvider() {
        super();
    }
    
    private class WidgetHashStructureHookListener implements HashStructureHookListener {
        
        public void listenerAttached(HashStructureHookEvent e) {
            _addWidgets(e.getCombinedHashStructure());
        }

        public void elementVisited(HashStructureHookEvent e){
            _addWidgets(e.getNewElementHashStructure());
        }
        
        private void _addWidgets(HashStructure structure) {
            List<HashStructure> widgetStructures = structure.getAsList("widget");
            for (HashStructure widgetStructure : widgetStructures) {
                _widgetList.add(Widget.getInstance(widgetStructure));
            }
        }        
    }
    
    
}

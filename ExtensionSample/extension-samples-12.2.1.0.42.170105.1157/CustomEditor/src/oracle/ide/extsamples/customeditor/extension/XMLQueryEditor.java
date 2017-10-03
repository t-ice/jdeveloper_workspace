/* $Header: jdev/src/esdk-samples/sample/CustomEditor/src/oracle/ide/extsamples/customeditor/extension/XMLQueryEditor.java /main/4 2013/03/04 16:18:39 svassile Exp $ */

/* Copyright (c) 2007, 2013, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */
package oracle.ide.extsamples.customeditor.extension;

import java.awt.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import oracle.bali.xml.addin.XMLSourceNode;
import oracle.bali.xml.model.XmlModel;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controls.Toolbar;
import oracle.ide.editor.Editor;
import oracle.ide.model.Node;
import oracle.ide.model.UpdateMessage;

import oracle.ide.extsamples.customeditor.model.XMLQueryModel;

import org.w3c.dom.Document;


public final class XMLQueryEditor extends Editor implements PropertyChangeListener {
    private MainPanel mainPanel;
    private XMLQueryModel model;
    private static Logger logger =
        Logger.getLogger(XMLQueryEditor.class.getName());


    public XMLQueryEditor() {
    }

    public void open() {
        model = new XMLQueryModel();
        model.addPropertyChangeListener(this);
        final Context context = getContext();
        final Node genericNode = context.getNode();
        if (genericNode instanceof XMLSourceNode) {
            final XMLSourceNode node = (XMLSourceNode)genericNode;
            final XmlModel xmlModel = XMLSourceNode.getXmlContext(context).getModel();
            try {
                xmlModel.acquireReadLock();
                final Document document = xmlModel.getDocument();
                ExecuteQueryUtils.registerDocumentNamespacesAndPrefixes(model,
                                                                        document);
                context.setProperty(XMLQueryModel.class.getName(), model);
                setContext(context);
            } finally {
                xmlModel.releaseReadLock();
            }
        }
    }

    public Component getGUI() {
        if (mainPanel == null) {
            mainPanel = new MainPanel(model);
            this.setToolbarVisible(true);
        }
        return mainPanel;
    }

    public void update(Object object,
                       UpdateMessage updateMessage) { //$NON-NLS-1$
        logger.info("XMLQueryEditor():update" + object + ":" + updateMessage);
    }

    public Toolbar getToolbar() {
        return mainPanel.getToolBar();
    }

    public Object getEditorAttribute(String name) {
        if (name.equals(Editor.ATTRIBUTE_SCROLLABLE)) {
            return Boolean.FALSE;
        }
        return super.getEditorAttribute(name);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final ResourceBundle bundle = Bundle.getBundle();
        final String propName = evt.getPropertyName();
        if (propName.equals("numberOfRowsAffected")) { //$NON-NLS-1$
            Ide.getStatusBar().setText(String.format("%s %s",
                                                     evt.getNewValue(),
                                                     bundle.getString("rows_returned"))); //$NON-NLS-1$
        }
    }

    public void close() {
        super.close();
        model.removePropertyChangeListener(this);
        model = null;
        mainPanel = null;
    }

    public Component getDefaultFocusComponent() {
        return (mainPanel != null) ? mainPanel.getQueryTextField() : null;
    }
}

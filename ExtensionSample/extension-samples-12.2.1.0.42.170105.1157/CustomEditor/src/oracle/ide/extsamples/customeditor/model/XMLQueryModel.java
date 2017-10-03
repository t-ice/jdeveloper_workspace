/* $Header: XMLQueryModel.java 16-aug-2007.21:53:30 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */
package oracle.ide.extsamples.customeditor.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;


public class XMLQueryModel implements DocumentListener {
    private String queryResults;
    private int numRowsAffected;
    private String query;
    private PropertyChangeSupport pcs;
    private String defaultNamespaceURI;
    private List<Namespace> namespaces;

    public XMLQueryModel() {
        pcs = new PropertyChangeSupport(this);
        namespaces = new ArrayList<Namespace>();
        numRowsAffected = -1;
    }


    public String getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(String queryResults) {
        String oldResults = this.queryResults;
        this.queryResults = queryResults;
        firePropertyChange("queryResults", oldResults,
                           queryResults); //$NON-NLS-1$
    }

    public int getNumberOfRowsAffected() {
        return numRowsAffected;
    }

    public void setNumberOfRowsAffected(int numRowsAffected) {
        int oldValue = this.numRowsAffected;
        this.numRowsAffected = numRowsAffected;
        firePropertyChange("numberOfRowsAffected", oldValue,
                           numRowsAffected); //$NON-NLS-1$
    }

    public void insertUpdate(DocumentEvent e) {
        final String text = getDocumentText(e.getDocument());
        setQuery(text);
    }

    public void removeUpdate(DocumentEvent e) {
        final String text = getDocumentText(e.getDocument());
        setQuery(text);
    }

    public void changedUpdate(DocumentEvent e) {
        final String text = getDocumentText(e.getDocument());
        setQuery(text);
    }

    private String getDocumentText(javax.swing.text.Document doc) {
        String txt;
        try {
            txt = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            txt = null;
        }
        return txt;
    }

    protected void firePropertyChange(String propertyName, Object oldValue,
                                      Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(String propertyName,
                                                       PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(String propertyName,
                                                          PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    public void setQuery(String query) {
        String oldValue = this.query;
        this.query = query;
        firePropertyChange("query", oldValue, query); //$NON-NLS-1$
    }

    public String getQuery() {
        return query;
    }

    public void registerPrefixAndNamespace(String prefix,
                                           String namespaceURI) {
        if (prefix == null || prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
            throw new IllegalArgumentException("prefix can not be null or the DEFAULT_NS_PREFIX"); //$NON-NLS-1$
        }
        if (namespaceURI == null) {
            throw new IllegalArgumentException("namespaceURI can not be null"); //$NON-NLS-1$
        }
        namespaces.add(new Namespace(prefix, namespaceURI));
    }

    public NamespaceContext createNamespaceContext() {
        return new NamespaceContextImpl(namespaces, defaultNamespaceURI);
    }

    public void setDefaultNamespaceURI(String defaultNamespaceURI) {
        this.defaultNamespaceURI = defaultNamespaceURI;
    }

    public String getDefaultNamespaceURI() {
        return defaultNamespaceURI;
    }

}

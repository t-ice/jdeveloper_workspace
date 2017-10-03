/* $Header: NamespaceContextImpl.java 16-aug-2007.21:53:27 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       04/04/07 - Bug 5957716 - fix compilation warnings
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */
package oracle.ide.extsamples.customeditor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

final class NamespaceContextImpl implements NamespaceContext {
    private final List<Namespace> namespaces = new ArrayList<Namespace>();

    NamespaceContextImpl(Collection<Namespace> namespaces,
                         String defaultNamespace) {
        this.namespaces.add(new Namespace(XMLConstants.XML_NS_PREFIX,
                                          XMLConstants.XML_NS_URI));
        this.namespaces.add(new Namespace(XMLConstants.XMLNS_ATTRIBUTE,
                                          XMLConstants.XMLNS_ATTRIBUTE_NS_URI));
        if (defaultNamespace != null) {
            this.namespaces.add(new Namespace(XMLConstants.DEFAULT_NS_PREFIX,
                                              defaultNamespace));
        }
        this.namespaces.addAll(namespaces);
    }

    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix can not be null"); //$NON-NLS-1$
        }
        for (Namespace ns : namespaces) {
            if (prefix.equals(ns.getPrefix())) {
                return ns.getNamespaceURI();
            }
        }
        return XMLConstants.NULL_NS_URI;
    }

    public String getPrefix(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("namespaceURI can not be null"); //$NON-NLS-1$
        }
        for (Namespace ns : namespaces) {
            if (namespaceURI.equals(ns.getNamespaceURI())) {
                return ns.getPrefix();
            }
        }
        return null;
    }

    public Iterator getPrefixes(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("namespaceURI can not be null"); //$NON-NLS-1$
        }
        final Collection<String> prefixes = new ArrayList<String>();
        for (Namespace ns : namespaces) {
            if (namespaceURI.equals(ns.getNamespaceURI())) {
                prefixes.add(ns.getPrefix());
            }
        }
        return Collections.unmodifiableCollection(prefixes).iterator();
    }
}

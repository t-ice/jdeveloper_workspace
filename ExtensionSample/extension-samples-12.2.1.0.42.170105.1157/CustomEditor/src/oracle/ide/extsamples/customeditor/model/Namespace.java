/* $Header: Namespace.java 16-aug-2007.21:53:24 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */

package oracle.ide.extsamples.customeditor.model;

public final class Namespace {
    private final String prefix;
    private final String namespaceURI;

    public Namespace(String prefix, String namespaceURI) {
        this.prefix = prefix;
        this.namespaceURI = namespaceURI;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }
}

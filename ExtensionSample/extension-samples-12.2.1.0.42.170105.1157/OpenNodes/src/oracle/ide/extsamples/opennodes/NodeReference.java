/* $Header: Harness.java 16-aug-2007.21:53:07 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/29/07 - Initial revision
 */

package oracle.ide.extsamples.opennodes;

import java.net.URL;

import javax.swing.Icon;

/**
 * A reference to a node. Note that this class intentionally does not hold on
 * to the actual instance of oracle.ide.node.Node, since this would mess with
 * the IDE's auto-closing behavior for nodes.
 */
final class NodeReference implements Comparable<NodeReference> {
    private final URL url;
    private final Icon icon;
    private final String shortLabel;
        
    public NodeReference( URL url, Icon icon, String shortLabel ) {
        this.url = url;
        this.icon = icon;
        this.shortLabel = shortLabel;
    }


    public URL url() {
        return url;
    }

    public Icon icon() {
        return icon;
    }

    public String shortLabel() {
        return shortLabel;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NodeReference)) {
            return false;
        }
        final NodeReference other = (NodeReference)object;
        if (!(url == null ? other.url == null : url.equals(other.url))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 37;
        int result = 1;
        result = PRIME * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return String.format( "NodeReference[url=%s,shortLabel=%s]", 
                              url, shortLabel );
    }

    public int compareTo(NodeReference other) {
        return url.toString().compareTo( other.url.toString() );
        
    }
}

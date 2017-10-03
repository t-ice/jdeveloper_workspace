/* $Header: jdev/src/esdk-samples/sample/ExternalToolMacros/src/oracle/ide/extsamples/externaltoolmacros/UserNameMacro.java /main/3 2011/02/28 11:50:04 dlane Exp $ */

/* Copyright (c) 2007, 2011, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/07/07 - Initial Revision
 */
package oracle.ide.extsamples.externaltoolmacros;

import oracle.ide.Context;
import oracle.ide.externaltools.macro.MacroExpander;

/**
 * An example macro for external tools that just expands to the current
 * user's user name.
 */
final class UserNameMacro extends MacroExpander {
   
    public String expand(Context context) {
        return System.getProperty("user.name");
    }


}

/* $Header: jdev/src/esdk-samples/sample/AuditRefactor/src/oracle/jdeveloper/extsamples/auditrefactor/FixMethodName.java /main/5 2014/03/11 03:00:21 ltribble Exp $ */

/* Copyright (c) 2007, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/16/07 - Code cleanup
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.auditrefactor;

import oracle.javatools.parser.java.v2.model.SourceMethod;

import oracle.jdeveloper.audit.java.JavaTransformAdapter;
import oracle.jdeveloper.audit.java.JavaTransformContext;
import oracle.jdeveloper.audit.transform.Transform;

public final class FixMethodName extends Transform {
    public FixMethodName() {
      super(new JavaTransformAdapter());
    }
    
    public void apply(JavaTransformContext context, SourceMethod method) {
        context.invokeLater(new FixMethodNameCommand(label(), context.getIdeContext(), method));
    }
}

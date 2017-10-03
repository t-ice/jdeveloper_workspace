/* $Header: jdev/src/esdk-samples/sample/AuditRefactor/src/oracle/jdeveloper/extsamples/auditrefactor/MethodNames.java /main/5 2012/07/29 20:59:38 ltribble Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/16/07 - Code cleanup.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.auditrefactor;

import oracle.javatools.parser.java.v2.model.SourceMethod;

import oracle.jdeveloper.audit.analyzer.Analyzer;
import oracle.jdeveloper.audit.analyzer.AuditContext;
import oracle.jdeveloper.audit.analyzer.Rule;
import oracle.jdeveloper.audit.analyzer.IssueReport;
import oracle.jdeveloper.audit.extension.ExtensionResource;

/**
 * Simple example of an audit analyzer which checks to see whether public
 * static final methods have a prefix "_pfs_" and raises a violation if not.
 */
public class MethodNames extends Analyzer {
    static final String PREFIX = "_pfs_";

    @ExtensionResource("oracle.jdeveloper.extsamples.auditrefactor.name-verification")  // NOTRANS
    private Rule nameVerification;
  
    public void enter(AuditContext ctx, SourceMethod method) {
        if (!(method.isPublic() && method.isStatic() && method.isFinal())) return;

        String name = method.getName();
        if (!name.startsWith(PREFIX)) {
            IssueReport vr = ctx.report(nameVerification);
            vr.addParameter("name", name);
        }
    }
}

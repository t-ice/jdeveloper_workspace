/* $Header: MessageWindow.java 03-apr-2008.01:34:33 bduff Exp $ */

/* Copyright (c) 2007, 2008, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       04/03/08 - Respond to deprecation of useStyledText parameter in
                           MessagePage constructor.
    bduff       08/17/07 - Code cleanup. Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.clickableurl;

import oracle.ide.layout.ViewId;
import oracle.ide.log.MessagePage;

final class MessageWindow extends MessagePage {
    private static final String MESSAGE_PAGE_ID = "esdksample.MessageWindow";

    public MessageWindow(String pageName) {
        super(new ViewId(MESSAGE_PAGE_ID, pageName), null, false);
    }
}

/* $Header: OverlayController.java 16-aug-2007.22:16:11 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.overlay;

import javax.swing.ImageIcon;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.explorer.IconOverlay;
import oracle.ide.explorer.IconOverlayCache;
import oracle.ide.model.Element;


/**
 * Sample code that demonstrates how to provide overlays on nodes in the
 * Applications or System navigators. Overlays can add icons, text or
 * tooltip information to existing nodes.
 */
public final class OverlayController implements Controller {
    static final int SHOW_LABEL_OVERLAY =
        Ide.findOrCreateCmdID("sample.cmd.showLabelOverlay");
    static final int SHOW_ICON_OVERLAY =
        Ide.findOrCreateCmdID("sample.cmd.showIconOverlay");
    static final int SHOW_TOOLTIP_OVERLAY =
        Ide.findOrCreateCmdID("sample.cmd.showTooltipOverlay");
    static final int SHOW_COMBO_OVERLAY =
        Ide.findOrCreateCmdID("sample.cmd.showComboOverlay");
    static final int REMOVE_OVERLAYS =
        Ide.findOrCreateCmdID("sample.cmd.removeAllOverlays");

    private static final String OVERLAY_LABEL = "sample.overlay.label";
    private static final String OVERLAY_ICON = "sample.overlay.icon";
    private static final String OVERLAY_TOOLTIP = "sample.overlay.tooltip";
    private static final String OVERLAY_COMBO = "sample.overlay.combo";

    /**
     * Menu group weight. The high value ensures that our menu items get added
     * at the end of the context menu.
     */
    static final float MENU_GROUP = 99.9f;

    private void showLabelOverlay(Element element) {
        IconOverlay labelOverlay = new IconOverlay(null, "[1.2]", null);
        IconOverlayCache overlayCache = IconOverlayCache.getInstance();

        overlayCache.putOverlay(OVERLAY_LABEL, element, labelOverlay);
        overlayCache.fireOverlaysChanged();
    }

    private void showIconOverlay(Element element) {
        ImageIcon transparentImage =
            new ImageIcon(OverlayController.class.getResource("transparent-overlay.png"));

        IconOverlay iconOverlay = new IconOverlay(transparentImage);
        IconOverlayCache overlayCache = IconOverlayCache.getInstance();

        overlayCache.putOverlay(OVERLAY_ICON, element, iconOverlay);
        overlayCache.fireOverlaysChanged();
    }

    private void showToolTipOverlay(Element element) {
        IconOverlay labelOverlay =
            new IconOverlay(null, null, "An overlay tooltip");

        IconOverlayCache overlayCache = IconOverlayCache.getInstance();
        overlayCache.putOverlay(OVERLAY_TOOLTIP, element, labelOverlay);
        overlayCache.fireOverlaysChanged();
    }

    private void showComboOverlay(Element element) {
        ImageIcon ciGif =
            new ImageIcon(OverlayController.class.getResource("status-checked-in.gif"));

        IconOverlay labelOverlay =
            new IconOverlay(ciGif, "-kb", "A tooltip from the combined overlay");

        IconOverlayCache overlayCache = IconOverlayCache.getInstance();

        overlayCache.putOverlay(OVERLAY_COMBO, element, labelOverlay);

        overlayCache.fireOverlaysChanged();

    }

    private void removeAllOverlays() {
        IconOverlayCache overlayCache = IconOverlayCache.getInstance();

        overlayCache.clearOverlays(OVERLAY_ICON);
        overlayCache.clearOverlays(OVERLAY_LABEL);
        overlayCache.clearOverlays(OVERLAY_TOOLTIP);
        overlayCache.clearOverlays(OVERLAY_COMBO);

        overlayCache.fireOverlaysChanged();
    }

    ///////////////////////////////////////////////////////////////////////////////
    // BaseController overrides
    ///////////////////////////////////////////////////////////////////////////////

    public boolean handleEvent(IdeAction action, Context context) {
        int cmdId = action.getCommandId();

        if (context.getSelection() == null ||
            context.getSelection().length == 0) {
            return false;
        }

        Element[] selectedElements = context.getSelection();

        if (cmdId == SHOW_ICON_OVERLAY) {
            for (int i = 0; i < selectedElements.length; i++) {
                showIconOverlay(selectedElements[i]);
            }
            return true;
        } else if (cmdId == SHOW_LABEL_OVERLAY) {
            for (int i = 0; i < selectedElements.length; i++) {
                showLabelOverlay(selectedElements[i]);
            }
            return true;
        } else if (cmdId == SHOW_TOOLTIP_OVERLAY) {
            for (int i = 0; i < selectedElements.length; i++) {
                showToolTipOverlay(selectedElements[i]);
            }
            return true;
        } else if (cmdId == SHOW_COMBO_OVERLAY) {
            for (int i = 0; i < selectedElements.length; i++) {
                showComboOverlay(selectedElements[i]);
            }
            return true;
        } else if (cmdId == REMOVE_OVERLAYS) {
            removeAllOverlays();
            return true;
        }


        return false;

    }

    public boolean update(IdeAction action, Context context) {
        action.setEnabled(context.getSelection() != null &&
                          context.getSelection().length > 0);
        return true;
    }

}

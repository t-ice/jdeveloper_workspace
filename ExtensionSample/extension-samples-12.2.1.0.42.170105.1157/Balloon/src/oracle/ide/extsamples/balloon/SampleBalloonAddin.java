/* $Header: jdev/src/esdk-samples/sample/Balloon/src/oracle/ide/extsamples/balloon/SampleBalloonAddin.java /main/5 2014/03/18 17:54:19 ppark Exp $ */

/* Copyright (c) 2007, 2014, Oracle and/or its affiliates. 
All rights reserved.*/

/*
   MODIFIED    (MM/DD/YY)
    sjfarrel    03/13/14 - Bug 18008728 -
                           JAVA.AWT.ILLEGALCOMPONENTSTATEEXCEPTION IN
                           O.JAVATOOLS.UI.INFOTIP.INFOTIP:263
    bduff       08/16/07 - Reformat to JCS.
    bduff       08/07/07 - Initial Revision
 */
package oracle.ide.extsamples.balloon;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

import java.text.AttributedString;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import oracle.ide.Addin;
import oracle.ide.Ide;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.hover.HoverEvent;
import oracle.ide.hover.HoverFlavor;
import oracle.ide.hover.HoverListener;

import oracle.javatools.dialogs.MessageDialog;
import oracle.javatools.icons.OracleIcons;
import oracle.javatools.ui.SuperLabel;
import oracle.javatools.ui.infotip.InfoTipHover;
import oracle.javatools.ui.infotip.InfoTipOrientation;
import oracle.javatools.ui.infotip.InfoTipStyles;
import oracle.javatools.ui.infotip.templates.BalloonTemplate;

/**
 * Demonstrates how to install a balloon notification into the IDE's status
 * bar.
 */
@RegisteredByExtension("oracle.ide.extsamples.balloon")
final class SampleBalloonAddin implements Addin {
    private JComponent statusIcon;

    public void initialize() {
      Timer timer = new Timer(4000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          SwingUtilities.invokeLater(new Runnable() {
              @Override
              public void run()
              {
                showBalloon();
              }
            });
        }
      });
      timer.setRepeats(false);
      timer.start();
    }

    private void showBalloon() {
        statusIcon = installStatusBarIcon();
        InfoTipHover balloon = createBalloon();
        balloon.addHoverListener(createBalloonAction());
        if (statusIcon.isShowing()) {
            balloon.showHover();
        }
    }

    private JComponent installStatusBarIcon() {
        JLabel icon = new JLabel();
        icon.setIcon(icon());
        ideStatusTray().add(icon, 0);
        ideStatusTray().revalidate();
        return icon;
    }

    private InfoTipHover createBalloon() {
        BalloonTemplate tooltipTemplate = getBalloonTemplate("Hello World!",
          "This is balloon was installed by the Balloon Extension SDK sample.");
        
        InfoTipHover infoTipHover = new InfoTipHover(tooltipTemplate,
                                                     InfoTipStyles.DEFAULT,
                                                     statusIcon,
                                                     new Point(130,7),
                                                     HoverFlavor.getFlavor("info")); // NOTRANS
        infoTipHover.setOrientation(InfoTipOrientation.BOTTOM_TOP);
        return infoTipHover;
    }
    
    protected static BalloonTemplate getBalloonTemplate(String titleText, String contentText) {
      BalloonTemplate result = new BalloonTemplate();
      
      AttributedString titleAttrStr = new AttributedString(titleText);
      titleAttrStr.addAttribute(TextAttribute.SIZE, 11);
      titleAttrStr.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
    
      AttributedString contentAttrStr = new AttributedString(contentText);
      contentAttrStr.addAttribute(TextAttribute.SIZE, 11);

      SuperLabel titleLabel = new SuperLabel(titleAttrStr);
      titleLabel.setPreferredSize(new Dimension(400, 12));
    
      SuperLabel contentLabel = new SuperLabel(contentAttrStr);
      contentLabel.setPreferredSize(new Dimension(400, 55));   
    
      result.setTitle(titleLabel);
      result.setContent(contentLabel);
      
      return result;
    }

    private HoverListener createBalloonAction() {
      return new HoverListener() {
          @Override
          public void hoverChange(HoverEvent he) {
            MessageDialog.information(Ide.getMainWindow(),
                                      "Thanks for clicking!",
                                      "Balloon ESDK Sample", null);
            if (statusIcon != null) {
                ideStatusTray().remove(statusIcon);
                ideStatusTray().revalidate();
            }
          }
        };
    }

    private JComponent ideStatusTray() {
        return Ide.getStatusBar().getToolbar();
    }

    private Icon icon() {
        return OracleIcons.getIcon(OracleIcons.STAR);
    }
}

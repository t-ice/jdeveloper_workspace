/* $Header: MainPanel.java 16-aug-2007.21:53:14 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS.
    bduff       04/04/07 - Bug 5957716 - fix compilation warnings
    bduff       02/27/07 - Added copyright banner (original author rcleveng)
 */
package oracle.ide.extsamples.customeditor.extension;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.dnd.DropTarget;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import oracle.ide.controller.IdeAction;
import oracle.ide.controls.Toolbar;
import oracle.ide.extsamples.customeditor.model.XMLQueryModel;


final class MainPanel extends JPanel implements PropertyChangeListener {
    private BorderLayout panelLayout = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private Toolbar toolBar = new Toolbar();
    private BorderLayout centerLayout = new BorderLayout();
    private JPanel topPanel = new JPanel();
    private JPanel resultsPanel = new JPanel();
    private BorderLayout resultsPanelLayout = new BorderLayout();
    private JTextField queryTextField = new JTextField();
    private JTextArea resultsTextArea = new JTextArea();
    private JScrollPane resultsPane = new JScrollPane();
    private JLabel topLabel = new JLabel();
    private JLabel resultsLabel = new JLabel();

    MainPanel() {
        this(null);
    }


    MainPanel(XMLQueryModel model) {
        super();
        jbInit();
        queryTextField.getDocument().addDocumentListener(model);
        queryTextField.setDropTarget(new DropTarget(queryTextField,
                                                    new XMLQueryDropTargetListener()));
        model.addPropertyChangeListener(this);
        final Action action = IdeAction.find(ExecuteQueryCommand.ID);
        toolBar.add(action);
        queryTextField.setAction(action);

    }

    private void jbInit(){
        this.setLayout(panelLayout);
        panelCenter.setLayout(centerLayout);
        resultsPanel.setLayout(resultsPanelLayout);
        this.add(toolBar, BorderLayout.NORTH);
        panelCenter.add(topPanel, BorderLayout.NORTH);
        panelCenter.add(resultsPanel, BorderLayout.CENTER);
        this.add(panelCenter, BorderLayout.CENTER);
        resultsPanel.add(resultsPane, BorderLayout.CENTER);
        resultsTextArea.setEditable(false);
        topLabel.setText(Bundle.getString("xpath_query")); // $NON-NLS-1$
        topLabel.setLabelFor(queryTextField);
        topLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        topLabel.setFont(topLabel.getFont().deriveFont(Font.BOLD));
        resultsLabel.setText(Bundle.getString("results")); // $NON-NLS-1$
        resultsLabel.setLabelFor(resultsTextArea);
        resultsLabel.setFont(resultsLabel.getFont().deriveFont(Font.BOLD));
        resultsLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        topPanel.setLayout(new BorderLayout());
        topPanel.add(topLabel, BorderLayout.CENTER);
        topPanel.add(queryTextField, BorderLayout.SOUTH);
        resultsPanel.add(resultsLabel, BorderLayout.NORTH);
        resultsPane.setViewportView(resultsTextArea);
        resultsPane.setBorder(BorderFactory.createEmptyBorder());
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final String propName = evt.getPropertyName();
        if (propName.equals("queryResults")) { //$NON-NLS-1$
            resultsTextArea.setText((String)evt.getNewValue());
        }
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public JTextField getQueryTextField() {
        return queryTextField;
    }
}

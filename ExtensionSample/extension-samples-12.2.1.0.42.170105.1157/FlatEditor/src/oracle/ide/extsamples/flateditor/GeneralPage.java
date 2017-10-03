/* $Header: jdev/src/esdk-samples/sample/FlatEditor/src/oracle/ide/extsamples/flateditor/GeneralPage.java /main/7 2012/05/08 12:39:26 dlane Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       10/23/07 - Added a button the header.
    bduff       08/16/07 - Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.ide.extsamples.flateditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import oracle.javatools.icons.OracleIcons;
import oracle.javatools.ui.ComponentWithTitlebar;
import oracle.javatools.ui.ControlBar;
import oracle.javatools.ui.Header;
import oracle.javatools.ui.HeaderPanel;
import oracle.ide.controls.FlatEditorTransparentPanel;
import oracle.javatools.ui.layout.FieldLayoutBuilder;
import oracle.javatools.ui.layout.VerticalFlowLayout;
import oracle.javatools.ui.plaf.IconicButtonUI;


final class GeneralPage extends FlatEditorTransparentPanel {
    GeneralPage() {
        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new VerticalFlowLayout());

        addPageHeader("General");

        addSubPanel("Stuff", "This is an example expandable panel",
                    new StuffPanel());
        addSubPanel("Nonsense", "This is another example panel",
                    new StuffPanel());
        addSubPanel("Tables", "Demonstrates multiple tables",
                    new PanelWithTwoTables());
    }

    private void addSubPanel(String title, String hint, JComponent component) {
        HeaderPanel stuff = new HeaderPanel();
        stuff.getHeader().setText(title);
        stuff.getHeader().setLevel(Header.Level.SUB);
        stuff.getHeader().addActionControl( createIconicAction( OracleIcons.getIcon( OracleIcons.ADD )) );
        stuff.setStaticHelpText(hint);
        stuff.setHostedComponent(component);

        add(stuff);
    }

    private void addPageHeader(String pageTitle) {
        Header pageHeader = new Header();
        pageHeader.setText(pageTitle);
        pageHeader.setLevel(Header.Level.PAGE);

        add(pageHeader);
    }

    private static AbstractButton createBrowseButton() {
        JButton b = new JButton();
        IconicButtonUI.install(b);
        b.setIcon(OracleIcons.getIcon(OracleIcons.LOV));

        return b;
    }

    private Action createIconicAction(Icon icon) {
      return new AbstractAction("", icon) {
              public void actionPerformed(ActionEvent e) {
              }
          };
    }

    private class StuffPanel extends FlatEditorTransparentPanel {
        StuffPanel() {
            FieldLayoutBuilder b = new FieldLayoutBuilder(this);

            b.add(b.field().label(new JLabel()).withText("&Name:").component(new JTextField()));

            b.add(b.field().label(new JLabel()).withText("&Email:").component(new JTextField()));

            b.add(b.field().label(new JLabel()).withText("&State:").component(new JComboBox()).button(createBrowseButton()).withText(""));
        }
    }

    private class PanelWithTwoTables extends FlatEditorTransparentPanel {
        PanelWithTwoTables() {
            setLayout(new VerticalFlowLayout());

            // First, add a table with no scroll pane. It will get bigger as more
            // rows are added. The user can scroll the whole page, so generally
            // components in flat editors should preferrably not be in a scrollpane.
            add(createTable("Things", false));

            // This time, we'll put the table in a scroll pane.
            add(createTable("More Things", true));
        }

        private ComponentWithTitlebar createTable(String label,
                                                  boolean useScrollPane) {
            JTable t = new JTable();
            t.setModel(createDummyTableModel());

            JLabel jLabel = new JLabel(label);
            jLabel.setLabelFor(t);
            ComponentWithTitlebar tbComp = new ComponentWithTitlebar();
            tbComp.setComponent(useScrollPane ? createScrollPaneTable(t) :
                                createStandaloneTable(t));
            tbComp.setLabel(jLabel);

            // Let's add a control bar for adding / editing / removing items.
            ControlBar cb = new ControlBar();
            installAddEditRemove(cb);
            tbComp.setControlBar(cb);
            return tbComp;
        }

        private JComponent createScrollPaneTable(JTable table) {
            return new JScrollPane(table);
        }

        private JComponent createStandaloneTable(JTable table) {
            // Without a scrollpane, a table doesn't have its header. Let's make
            // sure we add it.
            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            p.add(table.getTableHeader(), BorderLayout.NORTH);
            p.add(table, BorderLayout.CENTER);

            return p;
        }

        private void installAddEditRemove(ControlBar cb) {
            cb.add(createTextAction("Foo"));
            cb.add(createTextAction("Bar"));
            cb.add(createIconicAction(OracleIcons.getIcon(OracleIcons.ADD)));
            cb.add(createIconicAction(OracleIcons.getIcon(OracleIcons.EDIT)));
            cb.add(createIconicAction(OracleIcons.getIcon(OracleIcons.DELETE)));
        }

        private Action createTextAction(String text) {
            return new AbstractAction(text) {
                    public void actionPerformed(ActionEvent e) {
                    }
                };
        }


        private TableModel createDummyTableModel() {
            class DummyTableModel extends AbstractTableModel {
                public int getRowCount() {
                    return 5;
                }

                public int getColumnCount() {
                    return 2;
                }

                public String getColumnName(int columnIndex) {
                    return columnIndex == 0 ? "Name" : "Value";
                }

                public Object getValueAt(int rowIndex, int columnIndex) {
                    return String.valueOf(rowIndex) + ", " +
                        String.valueOf(columnIndex);
                }
            }

            return new DummyTableModel();
        }

    }


}

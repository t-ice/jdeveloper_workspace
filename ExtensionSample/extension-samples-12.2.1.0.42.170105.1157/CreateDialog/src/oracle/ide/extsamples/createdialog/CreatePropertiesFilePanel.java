/* $Header: CreatePropertiesFilePanel.java 16-aug-2007.21:58:25 bduff Exp $ */

/* Copyright (c) 2007, Oracle. All rights reserved.  */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Reformat to JCS. Minor cleanup.
    bduff       04/09/07 - Initial revision
 */

package oracle.ide.extsamples.createdialog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

import java.net.URL;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import oracle.bali.ewt.dialog.DialogHeader;
import oracle.bali.ewt.dialog.JEWTDialog;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.editor.EditorUtil;
import oracle.ide.model.NodeFactory;
import oracle.ide.model.Project;
import oracle.ide.net.URLFactory;
import oracle.ide.net.URLFileSystem;
import oracle.ide.net.URLTextField;

import oracle.javatools.dialogs.MessageDialog;
import oracle.javatools.dialogs.progress.IndeterminateProgressMonitor;
import oracle.javatools.icons.OracleIcons;
import oracle.javatools.ui.layout.FieldLayoutBuilder;


/**
 * User interface for the Create Properties File ESDK sample.
 */
final class CreatePropertiesFilePanel extends JPanel {
    private final URLTextField directory = new URLTextField();
    private final JButton browse = new JButton();
    private final JTextField filename = new JTextField();

    private VetoableChangeListener dialogListener;

    CreatePropertiesFilePanel() {
        layoutComponents();
        initComponents();
    }

    private void layoutComponents() {
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);
        b.add(b.field().label().withText("&File Name:").component(filename));
        b.add(b.field().label().withText("&Directory:").component(directory).button(browse).withText("&Browse..."));
    }

    private void initComponents() {
        browse.addActionListener(directory);
        directory.setDirectoryOnly(true);
    }

    /**
     * Runs the panel in a dialog.
     *
     * @param c the context.
     * @return <tt>true</tt> if the user clicked OK and the
     *    file was created. <tt>false</tt> otherwise.
     * @throws NullPointerException if <tt>c</tt> is null.
     */
    public boolean runDialog(final Context c) {
        if (c == null)
            throw new NullPointerException("c is null");
        populateDefaults(c);

        final JEWTDialog dialog =
            JEWTDialog.createDialog(Ide.getMainWindow(), "Create Properties File",
                                    JEWTDialog.BUTTON_DEFAULT);
        dialog.setContent(this);

        dialog.setDialogHeader(new DialogHeader("Enter the details of your new properties file.",
                                                OracleIcons.toImage(OracleIcons.getIcon(OracleIcons.HEADER_SIMPLEFILE))));

        // Standard idiom to do some task without hiding the dialog. This is a
        // good practice to follow, because if there is an error, the dialog remains
        // on screen and the user can correct any problems before clicking OK again.
        dialogListener = createDialogListener(c, dialog);
        dialog.addVetoableChangeListener(dialogListener);

        setupRequiredFields(dialog);

        return dialog.runDialog();
    }

    private void populateDefaults(Context c) {
        // A more complete implementation would check for a unique name.
        filename.setText("Untitled.properties");

        // Default the directory to the project directory.
        final Project project = c.getProject();
        if (project == null) {
            directory.setText("");
        } else {
            directory.setURL(URLFileSystem.getParent(project.getURL()));
        }
    }

    /**
     * Sets up required fields so that the OK button in the dialog is enabled
     * or disabled depending on whether they have a value.
     *
     * @param dialog the dialog to control the OK button of.
     */
    private void setupRequiredFields(final JEWTDialog dialog) {
        dialog.setOKButtonEnabled(isOKEnabled());
        OKButtonUpdater listener = new OKButtonUpdater(dialog);
        listener.attach(directory);
        listener.attach(filename);
    }

    /**
     * Creates a listener that will react when the user clicks on the OK
     * button to dismiss the dialog.
     */
    private VetoableChangeListener createDialogListener(final Context c,
                                                        final JEWTDialog dialog) {
        return new VetoableChangeListener() {
                public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                    if (JEWTDialog.isDialogClosingEvent(evt)) {
                        if (doValidation()) {
                            createPropertiesFile(c, dialog);
                        }
                        throw new PropertyVetoException("", evt);
                    }
                }
            };
    }

    /**
     * Starts the background worker to create the new file.
     */
    private void createPropertiesFile(final Context context,
                                      final JEWTDialog dialog) {
        // If your operation is super-fast, you can just implement it here without
        // using a worker and a progress monitor. However, using a background task
        // is generally recommended.

        final IndeterminateProgressMonitor monitor =
            new IndeterminateProgressMonitor(this,
                                             "Create Properties File Progress");

        Runnable uiTasksWhenDone = new Runnable() {
                public void run() {
                    dialog.removeVetoableChangeListener(dialogListener);
                    dialogListener = null;
                    dialog.closeDialog(false);
                    openInEditor(context, getFileURL());
                }
            };

        new CreatePropertiesFileWorker(monitor, getFileURL(),
                                       uiTasksWhenDone).start();
    }

    /**
     * Validate fields in the dialog, and display error dialogs if anything is
     * invalid.
     *
     * @return true if valid, false otherwise.
     */
    private boolean doValidation() {
        if (URLFileSystem.exists(getFileURL())) {
            MessageDialog.error(this, "The file already exists", "Error",
                                null);
            return false;
        }

        return true;
    }

    /**
     * Returns the URL of the properties file entered by the user.
     *
     * @return the url of the properties file, not null.
     */
    private URL getFileURL() {
        String name = filename.getText().trim();
        if (!name.endsWith(".properties"))
            name = name + ".properties";
        return URLFactory.newURL(directory.getURL(), name);
    }

    private void openInEditor(Context context, URL url) {
        EditorUtil.openDefaultEditorInFrame(NodeFactory.findOrCreateOrFail(url),
                                            context);
    }

    /**
     * Determine whether the OK button should be enabled.
     */
    private boolean isOKEnabled() {
        return directory.getURL() != null &&
            filename.getText().trim().length() > 0;
    }

    /**
     * Responds to keypresses in the text fields, and updates the OK button
     * of the dialog.
     */
    private class OKButtonUpdater implements DocumentListener {
        private final JEWTDialog _dialog;

        OKButtonUpdater(JEWTDialog dialog) {
            _dialog = dialog;
        }

        private void update() {
            _dialog.setOKButtonEnabled(isOKEnabled());
        }

        public void insertUpdate(DocumentEvent e) {
            update();
        }

        public void removeUpdate(DocumentEvent e) {
            update();
        }

        public void changedUpdate(DocumentEvent e) {
            update();
        }

        void attach(JTextComponent tc) {
            tc.getDocument().addDocumentListener(this);
        }
    }
}

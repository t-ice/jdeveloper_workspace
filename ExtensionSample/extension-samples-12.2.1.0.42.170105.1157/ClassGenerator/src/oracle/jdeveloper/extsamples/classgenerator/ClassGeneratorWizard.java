/* $Header: jdev/src/esdk-samples/sample/ClassGenerator/src/oracle/jdeveloper/extsamples/classgenerator/ClassGeneratorWizard.java /main/5 2012/03/28 11:37:31 svassile Exp $ */

/* Copyright (c) 2007, 2012, Oracle and/or its affiliates. 
All rights reserved. */

/*
   MODIFIED    (MM/DD/YY)
    bduff       08/16/07 - Code cleanup. Reformat to JCS.
    bduff       02/27/07 - Added copyright banner
 */
package oracle.jdeveloper.extsamples.classgenerator;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;

import java.io.IOException;

import javax.swing.ImageIcon;

import oracle.bali.ewt.wizard.WizardDialog;

import oracle.ide.dialogs.WizardLauncher;
import oracle.ide.model.Project;
import oracle.ide.panels.ApplyEvent;
import oracle.ide.panels.CommitListener;
import oracle.ide.panels.TraversableContext;
import oracle.ide.panels.TraversalException;
import oracle.ide.util.Namespace;
import oracle.ide.wizard.FSM;
import oracle.ide.wizard.FSMBuilder;
import oracle.ide.wizard.FSMInvalidException;
import oracle.ide.wizard.FSMWizard;
import oracle.ide.wizard.Step;
import oracle.ide.wizard.WelcomePanel;

import oracle.javatools.dialogs.DialogUtil;


/**
 * Demonstrates a simple wizard driven by a Finite State Machine (FSM).
 */
final class ClassGeneratorWizard {
    static final String MODEL_KEY = "model";

    static final String STATE_WELCOME = "welcome";
    static final String STATE_DETAILS = "details";
    static final String STATE_OPTIONS = "options";

    public boolean runWizard(Component parent,final Project project,
                             final ClassGeneratorCommand command) {

        FSMBuilder builder = new FSMBuilder();

        // Add all the pages to the finite state machine builder.
        ADD_PAGES_TO_BUILDER:
        {
            Step welcomeStep =
                WelcomePanel.newStep("Generate Addin Class (ESDK Sample)",
                                     "Welcome to the Generate Addin Class (ESDK Sample)",
                                     "This wizard helps you to generate an addin.\n\n" +
                    "It is implemented by the ClassGenerator Extension SDK Sample.\n\n" +
                    "Click Next to continue.", "ESDKClassGenerator.welcomeKey",
                    null); // No help topic.
            builder.newStartState(welcomeStep, STATE_DETAILS);

            Step step = new Step("Details", WizardDetailsPage.class, null);
            builder.newState(STATE_DETAILS, step, STATE_OPTIONS);

            step = new Step("Options", WizardOptionsPage.class, null);
            builder.newFinalState(STATE_OPTIONS, step);

        }

        try {
            FSM stateMachine = builder.getFSM();
            Namespace ns = new Namespace();
            ns.put(MODEL_KEY, new ClassGeneratorWizardModel());

            FSMWizard wizard = new FSMWizard(stateMachine, ns);

            // Initialize the wizard's style.
            INITIALIZE_WIZARD:
            {
                wizard.setWizardTitle("Generate Addin Class (ESDK Sample)");
                wizard.setWelcomePageAdded(true);
                wizard.setFinishPageAdded(true);
                ImageIcon imageicon =
                    new ImageIcon(getClass().getResource("genericwiz.gif"));
                wizard.updateImage(imageicon.getImage());
            }

            // Create a wizard dialog with the correct parent dialog or frame.
            WizardDialog wd;
            CREATE_WIZARD_DIALOG:
            {
                Dialog dialog = DialogUtil.getAncestorDialog(parent);
                if (parent != null) {
                    wd = wizard.getDialog(dialog);
                } else {
                    Frame f = DialogUtil.getAncestorFrame(parent);
                    wd = wizard.getDialog(f);
                }
            }


            wizard.addCommitListener(new CommitListener() {
                        public void checkCommit(ApplyEvent applyEvent) {
                        }

                        public void commit(ApplyEvent applyEvent) throws TraversalException {
                            ClassGeneratorWizard.this.commit(applyEvent.getTraversableContext(),
                                                             project, command);
                        }

                        public void rollback(ApplyEvent applyEvent) {
                        }

                        public void cancel(ApplyEvent applyEvent) {
                        }
                    });

            return WizardLauncher.runDialog(wd);

        } catch (FSMInvalidException ie) {
            ie.printStackTrace();
            return false;
        }

    }

    private void commit(TraversableContext context, Project project,
                        ClassGeneratorCommand cmd) throws TraversalException {
        try {
            ClassGeneratorWizardModel model =
                (ClassGeneratorWizardModel)context.get(MODEL_KEY);
            cmd.generate(project, model);
        } catch (IOException ioe) {
            throw new TraversalException(ioe.getMessage());
        }
    }
}

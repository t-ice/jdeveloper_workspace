package model;

import java.util.Collections;
import java.util.List;

public class StateMachineVierAugen extends StateMachine {

    public static final String STATE_IN_BEARBEITUNG = "In Bearbeitung";
    public static final String STATE_1_FREIGABE = "1. Freigabe";
    public static final String STATE_2_FREIGABE = "2. Freigabe";


    @Override
    public int getMode() {        
        return StateMachine.MODE_VIER_AUGEN;
    }

    public class StateErsteFreigabe implements IState {
        @Override
        public IState doAction(String action) {
            IState nextState;
            switch (action) {
            case "freigeben":
                validate();
                nextState = StateFactory.createState(getMode(),StateMachineVierAugen.STATE_2_FREIGABE);
                break;
            case "in Bearbeitung setzen":
                nextState = StateFactory.createState(getMode(),StateMachineVierAugen.STATE_IN_BEARBEITUNG);
                break;
            default:
                nextState = this;
            }
            return nextState;
        }

        @Override
        public String getName() {
            return "1. Freigabe";
        }

        private void validate() {
        }

        @Override
        public List<String> getValidActions() {
            // TODO Implement this method
            return Collections.emptyList();
        }
    }


    public class StateZweiteFreigabe implements IState {

        @Override
        public IState doAction(String action) {
            IState nextState;
            switch (action) {
            case "in Bearbeitung setzen":
                nextState = StateFactory.createState(StateFactory.STATE_IN_BEARBEITUNG);
                break;
            default:
                nextState = this;
            }
            return nextState;
        }

        @Override
        public String getName() {
            return StateFactory.STATE_2_FREIGABE;
        }

        private boolean validate() {
            // TODO Implement this method
            return true;
        }

        @Override
        public List<String> getValidActions() {
            // TODO Implement this method
            return Collections.emptyList();
        }
    }


    public class StateInBearbeitung implements IState {
        public StateInBearbeitung() {
            super();
        }

        @Override
        public IState doAction(String action) {
            // TODO Implement this method
            return null;
        }

        @Override
        public String getName() {
            // TODO Implement this method
            return null;
        }

        @Override
        public List<String> getValidActions() {
            // TODO Implement this method
            return Collections.emptyList();
        }
    }
}

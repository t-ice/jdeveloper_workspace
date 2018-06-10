package model;

public class StateFactory {


    public static IState createState(int mode, String state) {
        IState stateObj = null;
        switch (mode) {
        case StateMachine.MODE_VIER_AUGEN:
            stateObj = createVierAugenState(state);
            break;
        case StateMachine.MODE_ZWEI_AUGEN:
            stateObj = createZweiAugenState(state);
            break;
        case StateMachine.MODE_KEINE_FREIGABE:
            stateObj = createKeineAugenState(state);
            break;
        default:
        }
        return stateObj;
    }


    private static IState createVierAugenState(String state) {
        IState stateObj = null;
        StateMachineVierAugen machine = new StateMachineVierAugen();

        switch (state) {
        case StateMachineVierAugen.STATE_IN_BEARBEITUNG:
            stateObj = machine.new StateInBearbeitung();
            break;
        case StateMachineVierAugen.STATE_1_FREIGABE:
            stateObj = machine.new StateErsteFreigabe();
            break;
        case StateMachineVierAugen.STATE_2_FREIGABE:
            stateObj = machine.new StateZweiteFreigabe();
            break;
        default:
        }
        return stateObj;
    }


    private static IState createZweiAugenState(String state) {
        return null;
    }

    private static IState createKeineAugenState(String state) {
        return null;
    }
}

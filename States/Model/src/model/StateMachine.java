package model;


public class StateMachine {
    
    public static final int MODE_VIER_AUGEN = 0;
    public static final int MODE_ZWEI_AUGEN = 1;
    public static final int MODE_KEINE_FREIGABE = 2;
    
    private IState currentState;
    private int mode;

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public IState getCurrentState() {
        return currentState;
    }


    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}

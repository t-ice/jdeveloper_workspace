package model;

import java.util.List;


public interface IState {
    
    public String getName() ;
    
    public IState doAction(String action);
    
    public List<String> getValidActions();
}

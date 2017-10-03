package de.ikb.jdev.extension.train;


public class Step {
    public Step() {
        super();
    }
    
    private String name;
    private String escapedName;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEscapedName(String escapedName) {
        this.escapedName = escapedName;
    }

    public String getEscapedName() {
        return escapedName;
    }

    @Override
    public String toString(){
        return "[ "+name + ": ("+escapedName+") ] ";
    }
}

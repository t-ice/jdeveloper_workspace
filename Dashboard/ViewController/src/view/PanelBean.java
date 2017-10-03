package view;

public class PanelBean {
    private String panelSize = "1x2";


    public void setPanelSize(String panelSize) {
        System.out.println("setPanelSize: " + panelSize);
        this.panelSize = panelSize;
    }

    public String getPanelSize() {
        System.out.println("getPanelSize: " +panelSize);
        return panelSize;
    }

}

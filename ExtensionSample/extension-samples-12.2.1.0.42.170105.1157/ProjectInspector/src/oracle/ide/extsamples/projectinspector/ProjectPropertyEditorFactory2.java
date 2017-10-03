package oracle.ide.extsamples.projectinspector;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import oracle.bali.inspector.ActionGroup;
import oracle.bali.inspector.InspectorPropertyEditor;

import oracle.ide.model.Project;


public class ProjectPropertyEditorFactory2 extends InspectorPropertyEditor {

  private final Project project;
  private final ProjectPropertyModel.Property property;
  private Object value;

  public ProjectPropertyEditorFactory2(Project project, ProjectPropertyModel.Property property) {
    this.project = project;
    this.property = property;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Object getValue() {
    return value;
  }

  public void setAsText(String text) throws IllegalArgumentException {
  }

  public String getAsText() {
    return "";
  }

  public boolean isPaintable() {
    return false;
  }

  public void paintValue(Graphics g, Rectangle r) {
    
  }

  public boolean hasInlineEditor() {
    return false;
  }

  public Component getInlineEditor() {
    return null;
  }

  /**
   * Returns the list of <code>ActionGroups</code>s defining groups of
   * property-level actions for this property.
   *
   * @return A {@link List} of {@link ActionGroup}s containing all
   *         property-level {@link javax.swing.Action}s for this property.
   *         An empty list should be returned if no property-level actions
   *         are supported.
   */
  public List<ActionGroup> getActionGroups() {
    return new ArrayList<ActionGroup>();
  }

  public boolean hasActionGroups() {
    return true;
  }

  public String[] getTags() {
    return null;
  }

  public Component getCustomEditor() {
    return null;
  }
  
  public boolean supportsCustomEditor() {
    return false;
  }
  
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
    
  }

  public void applyValueFrom(Component c) {
  }
}


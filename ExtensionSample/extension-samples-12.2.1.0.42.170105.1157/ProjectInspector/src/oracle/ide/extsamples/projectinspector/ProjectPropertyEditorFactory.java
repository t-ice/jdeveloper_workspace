package oracle.ide.extsamples.projectinspector;

import java.awt.Component;

import java.beans.PropertyChangeListener;

import java.util.Locale;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import oracle.bali.inspector.PropertyEditorFactory;

import oracle.ide.model.Project;

public class ProjectPropertyEditorFactory implements PropertyEditorFactory {
  private final Project project;
  private final ProjectPropertyModel.Property property;
  private Object value;

  public ProjectPropertyEditorFactory(Project project, ProjectPropertyModel.Property property) {
    this.project = project;
    this.property = property;
  }

  public Object getEditorValue() { return value; }
  public void setEditorValue(Object value) { this.value = value; }

  public boolean hasValueRenderer() {
    return false;
  }

  public TableCellRenderer getValueRenderer() {
    return null;
  }

  public boolean hasInPlaceEditor() {
    return false;
  }

  public TableCellEditor getInPlaceEditor(Locale editorLocale) {
    return null;
  }

  public boolean hasTearOffEditor() {
    return false;
  }

  public Component getTearOffEditor() {
    return null;
  }

  public boolean hasAdvancedEditor() {
    return false;
  }

  public Component getAdvancedEditor() {
    return null;
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }
}

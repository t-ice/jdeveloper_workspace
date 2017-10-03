package oracle.ide.extsamples.projectinspector;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.SOUTHEAST;
import static java.awt.GridBagConstraints.WEST;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;

import javax.ide.view.GUIPanel;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import oracle.bali.inspector.PropertyModel;
import oracle.bali.inspector.multi.MultiObjectModel;
import oracle.bali.inspector.multi.SelectionModel;

import oracle.ide.inspector.PropertyCheckBox;
import oracle.ide.inspector.PropertyRadioButton;
import oracle.ide.inspector.find.SearchableContainer;
import oracle.ide.inspector.find.ContainerSearch;
import oracle.ide.inspector.find.SearchIterator;
import oracle.ide.inspector.layout.CustomGUIComponent;
import oracle.ide.model.Project;


public class ProjectDependencyComponent extends CustomGUIComponent implements SearchableContainer {

  private ProjectPropertyModel model;
  DependenciesPanel _dependenciesPanel;

  @Override public GUIPanel getGUIPanel() {
    if (_dependenciesPanel == null)
      _dependenciesPanel = new DependenciesPanel(model);
    
    return new GUIPanel(_dependenciesPanel);
  }

  @Override public void onInitialize(PropertyModel model) {
    this.model = unwrapProjectPropertyModel(model);
    _dependenciesPanel = null;
  }
  
  private ProjectPropertyModel unwrapProjectPropertyModel(PropertyModel model) {
    if (model instanceof MultiObjectModel) {
      SelectionModel sel = ((MultiObjectModel) model).getSelectionModel();
      int count = sel.getSelectedItemCount();
      for (int i = 0; i < count; i++) {
        Object item = sel.getSelectedItem(i);
        if (item instanceof ProjectPropertyModel)
          return((ProjectPropertyModel) item);
      }
      
      return null;
    }
    else if (model instanceof ProjectPropertyModel)
      return (ProjectPropertyModel) model;
    else
      return null;
  }

  @Override public void onApply(PropertyModel model) {}

  public boolean findText(String searchText, boolean searchForward, boolean searchRepeat) {
    return _dependenciesPanel.findText(searchText, searchForward, searchRepeat);
  }

  private class DependenciesPanel extends JPanel implements SearchableContainer {
    private final JRadioButton customSettingsOption = new PropertyRadioButton("Use Custom Settings");
    private final JRadioButton projectSettingOption = new PropertyRadioButton("Use Project Settings");
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);
    private final JButton applyButton = new JButton("Apply");
    
    private final ProjectPropertyModel model;

    private final ProjectDependencyPanel userDefined;
    private final ProjectDependencyPanel projectDefined;

    private boolean userDefinedDependencies;
    
    private ContainerSearch _containerSearch;

    DependenciesPanel(ProjectPropertyModel model) {
      this.model = model;
      userDefined = new ProjectDependencyPanel(model);
      projectDefined = new ProjectDependencyPanel(model);
      setUpDependencyPanels();
      setBackground(Color.WHITE);
      setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = c.gridy = 0;
      c.gridwidth = c.gridheight = 1;
      c.anchor = WEST;
      addSettingsOptions(c);
      addCardPanel(c);
      addApplyButton(c);
    }

    private void setUpDependencyPanels() {
      updateDependenciesFromProject();
      userDefined.setName("user");
      projectDefined.setName("project");
      cardPanel.add(userDefined, userDefined.getName());
      cardPanel.add(projectDefined, projectDefined.getName());
    }

    private void updateDependenciesFromProject() {
      userDefined.dependencies(model.userDefinedDependencies());
      projectDefined.dependencies(model.projectDefinedDependencies());
    }

    private void addSettingsOptions(GridBagConstraints c) {
      setUpSettingOptions();
      c.fill = HORIZONTAL;
      add(customSettingsOption, c);
      c.gridy++;
      c.insets = new Insets(0, 0, 5, 0);
      add(projectSettingOption, c);
    }

    private void setUpSettingOptions() {
      customSettingsOption.setBackground(getBackground());
      customSettingsOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              usingUserDefinedDependencies();
              enableApplyButton();
            }
          });
      projectSettingOption.setBackground(getBackground());
      projectSettingOption.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              usingProjectDefinedDependencies();
              enableApplyButton();
            }
          });
      ButtonGroup group = new ButtonGroup();
      group.add(customSettingsOption);
      group.add(projectSettingOption);
      if (model.userDefinedDependenciesStoredInProject()) usingUserDefinedDependencies();
      else usingProjectDefinedDependencies();
    }
    
    private void enableApplyButton() {
      applyButton.setEnabled(true);
    }

    private Project project() { return model.project(); }
    
    private void usingUserDefinedDependencies() {
      customSettingsOption.setSelected(true);
      userDefinedDependencies = true;
      cardLayout.show(cardPanel, userDefined.getName());
    }
    
    private void usingProjectDefinedDependencies() {
      projectSettingOption.setSelected(true);
      userDefinedDependencies = false;
      cardLayout.show(cardPanel, projectDefined.getName());
    }

    private void addCardPanel(GridBagConstraints c) {
      c.gridy++;
      c.weightx = c.weighty = 1.0;
      add(cardPanel, c);
    }
    
    private void addApplyButton(GridBagConstraints c) {
      applyButton.setEnabled(false);
      applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              applyButton.setEnabled(false);
              if (userDefinedDependencies) model.updateUserDefinedDependencies(userDefined.dependencies());
              else model.updateProjectDefinedDependencies(projectDefined.dependencies());
            }
          });
      c.gridy++;
      c.anchor = SOUTHEAST;
      c.weightx = c.weighty = 0.0;
      c.fill = NONE;
      add(applyButton, c);
    }
    
    private boolean userDefinedDependencies() { return userDefinedDependencies; }


    public boolean findText(String searchText, boolean searchForward, 
                            boolean searchRepeat)
    {
      if (_containerSearch == null) {
        _containerSearch = new ContainerSearch(this) {
          @Override public Iterator<Component> createSearchIterator(Container root, boolean forward) {
            return new SearchIterator(root, forward) {
              @Override protected boolean isSearchableContainer(Container container) {
                
                if (container == userDefined)
                  return userDefinedDependencies;
                
                if (container == projectDefined)
                  return !userDefinedDependencies;
                
                return true;
              }
            };
          }
        };
      }

      return _containerSearch.findText(searchText, searchForward, searchRepeat);      
    }
  }
}

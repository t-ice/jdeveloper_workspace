package oracle.ide.extsamples.projectinspector;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import oracle.bali.inspector.PropertyGroup;
import oracle.bali.inspector.PropertyModel;

import oracle.ide.Context;
import oracle.ide.model.DependencyConfiguration;
import oracle.ide.model.Project;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.ListStructure;
import oracle.javatools.data.PropertyStorage;

/**
 * Property model for the Java related properties of a project.
 */
public class ProjectPropertyModel extends PropertyModel {

  private static final String DEPENDENCY_CONFIGURATION_KEY = "oracle.ide.model.DependencyConfiguration";
  private static final String USER_DEFINED_DEPENDENCIES_KEY = "dependencyList";
  private static final String PROJECT_DEFINED_DEPENDENCIES_KEY = "hidden:dependencyList";


  private static class ColumnIndex {
    public static final int ID = 0;
    public static final int GROUP = 1;
    public static final int EDITOR_FACTORY = 2;
    public static final int VALUE = 3;
    public static final int DISPLAY_NAME = 4;
    public static final int WRITABLE = 5;
    public static final int READABLE = 6;
    public static final int HIDDEN = 7;
  }

  private static class Groups {
    private static final PropertyGroupImpl COMMON = new PropertyGroupImpl("Common");
    private static final PropertyGroupImpl DEPENDENCIES = new PropertyGroupImpl("Dependencies");
  }

  private static final String PROPERTY_ID_PREFIX = "oracle.ide.model.project";  
  private static final List<Property> ROWS = new ArrayList<Property>();

  static {
    ROWS.add(new Property(propertyId("outputPath"), "oracle.jdevimpl.config.JProjectPaths/outputDirectory", 
                          "Output Path", Groups.COMMON));
    ROWS.add(new Property(propertyId("defaultPackage"), "defaultPackage", "Default Package", Groups.COMMON));
    ROWS.add(new Property(propertyId("dependencies"), "oracle.ide.model.DependencyConfiguration", "Dependencies", 
                          Groups.DEPENDENCIES));
  }

  private static String propertyId(String id) {
    return PROPERTY_ID_PREFIX + "." + id;
  }

  private static final Map<Integer, Object> COLUMN_MAPPING = new LinkedHashMap<Integer, Object>();
  private static final Object[] MAPPING_ARRAY;
  static {
    COLUMN_MAPPING.put(ColumnIndex.ID, PropertyModel.COLUMN_ID);
    COLUMN_MAPPING.put(ColumnIndex.GROUP, PropertyModel.COLUMN_GROUP);
    COLUMN_MAPPING.put(ColumnIndex.EDITOR_FACTORY, PropertyModel.COLUMN_EDITOR_FACTORY_2);
    COLUMN_MAPPING.put(ColumnIndex.VALUE, PropertyModel.COLUMN_VALUE);
    COLUMN_MAPPING.put(ColumnIndex.DISPLAY_NAME, PropertyModel.COLUMN_DISPLAY_NAME);
    COLUMN_MAPPING.put(ColumnIndex.WRITABLE, PropertyModel.COLUMN_IS_WRITABLE);
    COLUMN_MAPPING.put(ColumnIndex.READABLE, PropertyModel.COLUMN_IS_READABLE);
    COLUMN_MAPPING.put(ColumnIndex.HIDDEN, PropertyModel.COLUMN_IS_HIDDEN);
    
    MAPPING_ARRAY = COLUMN_MAPPING.values().toArray();
  }

  private Context context;
  
  public ProjectPropertyModel(Context context) {
    this.context = new Context(context);
  }
  
  Context context() { return context; }

  @Override public Object[] getColumnMapping() { return MAPPING_ARRAY; }

  @Override public String getTargetDisplayName() {
    return context.getProject().getShortLabel();
  }

  @Override public void invalidateValueData() {}

  public int getRowCount() { return ROWS.size(); }

  @Override public int getColumnIndex(Object columnID) {
    Object[] columnIDs = getColumnMapping();
    int numColumns = columnIDs.length;
    for (int i = 0; i < numColumns; i++)
      if (columnID.equals(columnIDs[i])) return i;
    return PropertyModel.NONEXISTENT_COLUMN;
  }
  
  public Object getValueAt(int rowIndex, int columnIndex) {
    final Property property = ROWS.get(rowIndex);
//    printProject();
    switch(columnIndex) {
      case ColumnIndex.ID:
        return property.id;
      case ColumnIndex.GROUP:
        return property.group;
      case ColumnIndex.DISPLAY_NAME:
        return property.displayName;
      case ColumnIndex.VALUE:
        HashStructure properties = context.getProject().getProperties();
        return properties.getObject(property.name);
      case ColumnIndex.EDITOR_FACTORY:
        return new ProjectPropertyEditorFactory2(context.getProject(), property);
      case ColumnIndex.WRITABLE:
        return true;
      case ColumnIndex.READABLE:
        return true;
      case ColumnIndex.HIDDEN:
        return false;
    }
    return null;
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    final Property property = ROWS.get(rowIndex);
    switch(columnIndex) {
      case ColumnIndex.VALUE:
        String value = (aValue == null) ? null : aValue.toString();
        context.getProject().setProperty(property.name, value);
        return;
    }
  }
  
  List userDefinedDependencies() {
    List dependencies = userDefinedDependencyConfiguration().getDependencyList(); 
    if (dependencies != null && !dependencies.isEmpty()) return dependencies;
    return hiddenUserDefinedDependencies();
  }

  private List hiddenUserDefinedDependencies() {
    HashStructure userProperties = project().getUserPropertiesOnly().copyTo(null);
    if (!userProperties.containsKey(DEPENDENCY_CONFIGURATION_KEY)) return null;
    HashStructure dependencyProperties = userProperties.getHashStructure(DEPENDENCY_CONFIGURATION_KEY);
    ListStructure dependencies = dependencyProperties.getListStructure(PROJECT_DEFINED_DEPENDENCIES_KEY);
    if (dependencies == null || dependencies.size() == 0) return null;
    dependencyProperties.remove(PROJECT_DEFINED_DEPENDENCIES_KEY);
    dependencyProperties.putListStructure(USER_DEFINED_DEPENDENCIES_KEY, dependencies);
    return DependencyConfiguration.getInstance(new PropertyStorageImpl(userProperties)).getDependencyList();
  }
  
  void updateUserDefinedDependencies(List dependencies) {
    HashStructure dependencyProperties = userDependencyProperties();
    if (dependencyProperties != null && dependencyProperties.containsKey(PROJECT_DEFINED_DEPENDENCIES_KEY))
      dependencyProperties.remove(PROJECT_DEFINED_DEPENDENCIES_KEY);
    userDefinedDependencyConfiguration().setDependencyList(dependencies);
    try { 
      project().saveUserProperties();
    } catch (IOException e) {
      throw new RuntimeException("Unable to save project", e);
    }
  }
  
  private DependencyConfiguration userDefinedDependencyConfiguration() {
    HashStructure properties = project().getUserPropertiesOnly();
    return DependencyConfiguration.getInstance(new PropertyStorageImpl(properties));
  }
  
  List projectDefinedDependencies() {
    return projectDefinedDependencyConfiguration().getDependencyList(); 
  }

  void updateProjectDefinedDependencies(List dependencies) {
    HashStructure dependencyProperties = userDependencyProperties();
    if (dependencyProperties != null && dependencyProperties.containsKey(USER_DEFINED_DEPENDENCIES_KEY))
      dependencyProperties.hideValues(new String[] { USER_DEFINED_DEPENDENCIES_KEY });
    dependencyConfiguration().setDependencyList(dependencies);
    try { 
      project().save();
    } catch (IOException e) {
      throw new RuntimeException("Unable to save project", e);
    }
  }

  private DependencyConfiguration projectDefinedDependencyConfiguration() {
    HashStructure properties = project().getSharedPropertiesOnly();
    return DependencyConfiguration.getInstance(new PropertyStorageImpl(properties));
  }

  DependencyConfiguration dependencyConfiguration() {
    return DependencyConfiguration.getInstance(project()); 
  }

  Project project() { return context.getProject(); }

  private static class PropertyStorageImpl implements PropertyStorage {
    private HashStructure propertyData;

    public PropertyStorageImpl(HashStructure hash) { 
      propertyData = hash;
    }

    public HashStructure getProperties() { return propertyData; }
  }

  boolean userDefinedDependenciesStoredInProject() {
    HashStructure dependencyProperties = userDependencyProperties();
    if (dependencyProperties == null || dependencyProperties.size() == 0) return false;
    return !dependencyProperties.containsKey(PROJECT_DEFINED_DEPENDENCIES_KEY);
  }

  private HashStructure userDependencyProperties() {
    HashStructure userProperties = project().getUserPropertiesOnly();
    if (!userProperties.containsKey(DEPENDENCY_CONFIGURATION_KEY)) return null;
    return userProperties.getHashStructure(DEPENDENCY_CONFIGURATION_KEY);
  }
  
  private static class PropertyGroupImpl implements PropertyGroup {
    private String name;
    
    public PropertyGroupImpl(String name) {
      this.name = name;
    }

    public String getName(Locale locale) {
      return name;
    }
  }

  static final class Property {
    final String id;
    final String name;
    final String displayName;
    final PropertyGroupImpl group;
    
    Property(String id, String name, String displayName, PropertyGroupImpl group) {
      this.id = id;
      this.name = name;
      this.displayName = displayName;
      this.group = group;
    }
  }
}

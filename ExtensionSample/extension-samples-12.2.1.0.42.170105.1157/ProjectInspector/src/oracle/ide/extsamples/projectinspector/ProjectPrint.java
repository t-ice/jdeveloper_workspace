package oracle.ide.extsamples.projectinspector;

import java.util.Set;

import oracle.ide.model.Project;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.ListStructure;

class ProjectPrint {

  static void printProject(Project project) {
    HashStructure properties = project.getProperties();
    System.out.println("Project - Start");
    int level = 0;
    print(properties, level);
    printUserProperties(project, level);
    System.out.println("Project - End");
  }

  static void printUserProperties(Project project, int level) {
    System.out.println("User Properties - Start");
    print(project.getUserPropertiesOnly(), level);
    System.out.println("User Properties - End");
  }
  
  static private void print(HashStructure chain, int level) {
    Set keys = chain.keySet();
    for (Object key : keys) {
      System.out.print(spaces(level));
      System.out.println("key: " + key);
      if (key.equals("oracle.ide.model.DependencyConfiguration")) {
        System.out.print("");
      }
      Object value = chain.getObject(key.toString());
      System.out.print(spaces(level));
      if (value instanceof HashStructure) {
        System.out.println("value: ");
        print((HashStructure) value, level + 1);
      }
      else if (value instanceof ListStructure) {
        System.out.println("value: ");
        print((ListStructure) value, level + 1);
      } else {
        System.out.print("value: ");
        System.out.println(value);
      }
    }
  }
  
  private static void print(ListStructure list, int level) {
    System.out.println(spaces(level) + "List: ");
    int elementLevel = level + 1;
    for (int i = 0; i < list.size(); i++) {
      Object o = list.get(i);
      System.out.print(spaces(elementLevel) + " Element[" + i + "]");
      if (o instanceof HashStructure) {
        print((HashStructure) o, level + 1);         
      } else {
        System.out.println(o);
      }
    }
  }
  
  private static String spaces(int count) {
    String spaces = "";
    for (int i = 0; i < count; i++) {
      spaces += "  ";
    }
    return spaces;
  }
}

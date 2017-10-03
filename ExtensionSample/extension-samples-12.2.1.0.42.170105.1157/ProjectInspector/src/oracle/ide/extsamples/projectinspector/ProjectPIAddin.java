package oracle.ide.extsamples.projectinspector;

import oracle.ide.Addin;

public class ProjectPIAddin implements Addin
{
  public ProjectPIAddin()
  {
    System.out.println(ProjectPIAddin.class.getName() + " created!");
  }

  public void initialize()
  {
    // Initialize addin stuff here.
    System.out.println(ProjectPIAddin.class.getName() + " initialized!");
  }
 
}

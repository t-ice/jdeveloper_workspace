package oracle.ide.extsamples.externaltoolcreation;

import java.util.Collection;

import oracle.ide.Context;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.externaltools.Availability;
import oracle.ide.externaltools.ExternalProgramToolProperties;
import oracle.ide.externaltools.ExternalTool;
import oracle.ide.externaltools.ExternalToolBaseProperties;
import oracle.ide.externaltools.ExternalToolFactory;
import oracle.ide.externaltools.ExternalToolManager;
import oracle.ide.externaltools.IntegrationPoint;

public class ExternalToolCreationController
implements Controller
{
  // Use a unique identifier here, don't just copy this sample verbatim.
  private static final String TOOL_ID = "org.foo.myTool";
  
  public boolean handleEvent(IdeAction ideAction, Context context) 
  {
    createATool();
    return true;
  }
  
  public boolean update(IdeAction ideAction, Context context) 
  {
    return true;
  }
  
  private void createATool() 
  {
    if (toolExistsAlready())
        return;

    ExternalToolManager.getExternalToolManager().addExternalTool(createTool());
  }

  private boolean toolExistsAlready() {
      return ExternalToolManager.scannerTags(allTools()).contains(TOOL_ID);
  }
  
  private Collection<ExternalTool> allTools() {
      return ExternalToolManager.getExternalToolManager().tools();
  }

  private ExternalTool createTool() {
      ExternalTool tool =
          ExternalToolFactory.getInstance().createProgramTool();

      configureBaseProperties(tool);
      configureProgramProperties(tool);

      return tool;
  }
  

  private void configureBaseProperties(ExternalTool tool) {
      ExternalToolBaseProperties props =
          ExternalToolBaseProperties.getInstance(tool);

      props.setScannerTag(TOOL_ID); // Important, this is how we avoid creating duplicates.
      props.setCaption("Sample Tool (from the ESDK ExternalToolCreation sample)");
      props.setAvailability(Availability.ALWAYS);
      props.setIntegrated(IntegrationPoint.TOOLS_MENU, true);
      props.setIntegrated(IntegrationPoint.NAVIGATOR_MENU, true);
  }

  private void configureProgramProperties(ExternalTool tool) {
      ExternalProgramToolProperties props =
          ExternalProgramToolProperties.getInstance(tool);

      props.setExecutable("c:\\path\\to\\some\\program.exe");
      props.setRunDirectory("c:\\somepath");
      props.setArguments("${file.path}");
  }
}

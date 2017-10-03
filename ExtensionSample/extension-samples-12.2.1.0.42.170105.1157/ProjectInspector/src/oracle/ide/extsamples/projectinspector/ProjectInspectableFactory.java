package oracle.ide.extsamples.projectinspector;

import oracle.ide.Context;
import oracle.ide.inspector.Inspectable;
import oracle.ide.inspector.InspectableFactory;
import oracle.ide.model.Project;

/**
 * An inspectable factory is responsible for providing an implementation of the 
 * {@link oracle.ide.inspector.Inspectable} class to the property inspector.
 * <p>
 * As objects are selected in a view (while the property inspector is visible),
 * the property inspector gives available inspectable factories a chance to 
 * provide an {@link oracle.ide.inspector.Inspectable} instance that can 
 * provide a property model for the selected object.
 * <p>
 * This implementation of an <code>InspectableFactory</code> interface knows how 
 * to provide an inspectable for {@link oracle.ide.model.Project}s.
 */

public class ProjectInspectableFactory implements InspectableFactory
{
  public ProjectInspectableFactory()
  {
  }

  public Inspectable getInspectable(Context context)
  {
    if (context.getElement() != null && 
        context.getElement().getClass() == Project.class)
    {
      return new ProjectInspectable(context);
    }
    return null;
  }
}

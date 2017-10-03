package oracle.ide.extsamples.projectinspector;

import java.awt.Component;

import java.beans.Customizer;

import oracle.bali.inspector.PropertyModel;

import oracle.ide.Context;
import oracle.ide.inspector.LayoutInspectable;

/**
 * An inspectable is responsible for providing an implementation of the 
 * {@link oracle.bali.inspector.PropertyModel} class to the 
 * {@link oracle.ide.inspector.InspectableFactory}.
 * <p>
 * As objects are selected in a view (while the property inspector is visible),
 * the property inspector gives available inspectable factories a chance to 
 * provide an {@link oracle.ide.inspector.Inspectable} instance that can 
 * provide a property model for the selected object.
 * <p>
 * This implementation of the <code>Inspectable</code> interface knows how 
 * to provide a property model for {@link oracle.ide.model.Project}s.
 */
public class ProjectInspectable extends LayoutInspectable
{
  private Context _context;
  private Object _target;
  private PropertyModel _model;
  
  /**
   * Construtor. Called by the {@link oracle.ide.extsamples.projectinspector.ProjectInspectableFactory#getInspectable}
   * method.
   * @param context the current IDE context. The selected object must be an 
   * {@link oracle.ide.model.Project}.
   */
  public ProjectInspectable(Context context)
   {
    setContext(context);
  }

  public void setContext(Context context)
  {
    _context = new Context(context);
  }

  public Object getTarget()
  {
    if (_target == null)
    {
      _target = _context.getElement();
    }
    return _target;
  }

  public PropertyModel getPropertyModel()
  {
    if (_model == null)
    {
      _model = new ProjectPropertyModel(_context);
    }
    return _model;
  }

  public Customizer getCustomizer()
  {
    return null;
  }

  public Component[] getAdditionalTabPages()
  {
    return new Component[0];
  }
}

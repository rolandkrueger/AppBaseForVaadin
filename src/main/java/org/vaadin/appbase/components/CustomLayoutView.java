package org.vaadin.appbase.components;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.enums.ErrorSeverity;
import org.vaadin.appbase.event.impl.error.ErrorEvent;
import org.vaadin.appbase.view.IView;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;

public class CustomLayoutView implements IView
{
  private String       templateName;
  private CustomLayout layout;

  public CustomLayoutView (String templateName)
  {
    checkNotNull (templateName);
    this.templateName = templateName;
  }

  @Override
  public void buildLayout ()
  {
    layout = createTranslatedCustomLayout (templateName);

    if (layout != null && !VaadinService.getCurrent ().getDeploymentConfiguration ().isProductionMode ())
    {
      new ComponentHighlighterExtension (getLayout ()).setComponentDebugLabel (getClass ().getName ()
          + " (template name: " + templateName + ".html)");
    }
  }

  @Override
  public Component getContent ()
  {
    return layout;
  }

  protected CustomLayout getLayout ()
  {
    return layout;
  }

  private CustomLayout createTranslatedCustomLayout (String templateName)
  {
    CustomLayout layout = null;
    try
    {
      layout = new CustomLayout (VaadinUIServices.get ().getTemplatingService ()
          .getLayoutTemplate (VaadinUIServices.get ().getContext ().getLocale (), templateName));
    } catch (IOException ioExc)
    {
      VaadinUIServices
          .get ()
          .getEventbus ()
          .post (
              new ErrorEvent (this, ErrorSeverity.FATAL, "Error while loading CustomLayout template " + templateName,
                  ioExc));
      return null;
    }
    return layout;
  }
}

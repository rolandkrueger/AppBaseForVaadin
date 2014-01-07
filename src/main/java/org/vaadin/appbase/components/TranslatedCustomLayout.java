package org.vaadin.appbase.components;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.vaadin.appbase.VaadinUIServices.UIServices;

import java.io.IOException;

import org.vaadin.appbase.enums.ErrorSeverity;
import org.vaadin.appbase.event.impl.error.ErrorEvent;
import org.vaadin.appbase.view.IView;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;

public class TranslatedCustomLayout implements IView
{
  private String       templateName;
  private CustomLayout layout;

  public TranslatedCustomLayout (String templateName)
  {
    checkNotNull (templateName);
    this.templateName = templateName;
  }

  @Override
  public IView buildLayout ()
  {
    layout = createTranslatedCustomLayout (templateName);

    if (layout != null && !VaadinService.getCurrent ().getDeploymentConfiguration ().isProductionMode ())
    {
      new ComponentHighlighterExtension (getLayout ()).setComponentDebugLabel (getClass ().getName ()
          + " (template name: " + templateName + ".html)");
    }
    return this;
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
      layout = new CustomLayout (UIServices ().getTemplatingService ().getLayoutTemplate (
          UIServices ().getContext ().getLocale (), templateName));
    } catch (IOException ioExc)
    {
      UIServices ().getEventbus ()
          .post (
              new ErrorEvent (this, ErrorSeverity.FATAL, "Error while loading CustomLayout template " + templateName,
                  ioExc));
      return null;
    }
    return layout;
  }
}

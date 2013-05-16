package org.vaadin.appbase.event.impl.navigation;

import java.net.URI;

import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.roklib.webapps.uridispatching.IURIActionHandler.ParameterMode;
import org.vaadin.appbase.event.Event;

import com.google.common.base.Preconditions;

public class NavigateToURIEvent extends Event
{
  private final URI navigationTarget;

  public NavigateToURIEvent (Object source, AbstractURIActionHandler navigationTargetHandler)
  {
    super (source);
    Preconditions.checkNotNull (navigationTargetHandler);

    this.navigationTarget = navigationTargetHandler
        .getParameterizedActionURI (true, ParameterMode.DIRECTORY_WITH_NAMES);
  }

  public URI getNavigationTarget ()
  {
    return navigationTarget;
  }

  @Override
  protected String formatLogMessageImpl ()
  {
    return "navigationTarget=" + navigationTarget;
  }
}

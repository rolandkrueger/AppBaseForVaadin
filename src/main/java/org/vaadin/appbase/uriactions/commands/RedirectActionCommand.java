package org.vaadin.appbase.uriactions.commands;


import org.roklib.webapps.uridispatching.AbstractURIActionCommand;
import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.event.impl.navigation.NavigateToURIEvent;

import static com.google.common.base.Preconditions.*;

public class RedirectActionCommand extends AbstractURIActionCommand
{
  private final AbstractURIActionHandler redirectToHandler;
  private final IEventBus eventBus;

  public RedirectActionCommand(AbstractURIActionHandler redirectToHandler, IEventBus eventBus)
  {
    this.eventBus = checkNotNull(eventBus);
    this.redirectToHandler = checkNotNull(redirectToHandler);
  }

  @Override
  public void execute ()
  {
    eventBus.post (new NavigateToURIEvent (this, redirectToHandler));
  }

  @Override
  public String toString ()
  {
    return "RedirectActionCommand [target: " + redirectToHandler.getActionURI () + "]";
  }
}

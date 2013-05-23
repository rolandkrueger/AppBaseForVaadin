package org.vaadin.appbase.uriactions.commands;

import static org.vaadin.appbase.VaadinUIServices.UIServices;

import org.roklib.webapps.uridispatching.AbstractURIActionCommand;
import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.vaadin.appbase.event.impl.navigation.NavigateToURIEvent;

import com.google.common.base.Preconditions;

public class RedirectActionCommand extends AbstractURIActionCommand
{
  private AbstractURIActionHandler redirectToHandler;

  public RedirectActionCommand (AbstractURIActionHandler redirectToHandler)
  {
    Preconditions.checkNotNull (redirectToHandler);
    this.redirectToHandler = redirectToHandler;
  }

  @Override
  public void execute ()
  {
    UIServices ().getEventbus ().post (new NavigateToURIEvent (this, redirectToHandler));
  }

  @Override
  public String toString ()
  {
    return "RedirectActionCommand [target: " + redirectToHandler.getActionURI () + "]";
  }
}

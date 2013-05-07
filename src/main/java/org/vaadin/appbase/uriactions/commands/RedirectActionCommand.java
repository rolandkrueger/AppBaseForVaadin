package org.vaadin.appbase.uriactions.commands;

import org.roklib.webapps.uridispatching.AbstractURIActionCommand;
import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.event.impl.places.NavigationEvent;

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
    VaadinUIServices.get ().getEventbus ().post (new NavigationEvent (this, redirectToHandler));
  }

  @Override
  public String toString ()
  {
    return "RedirectActionCommand [target: " + redirectToHandler.getActionURI () + "]";
  }
}

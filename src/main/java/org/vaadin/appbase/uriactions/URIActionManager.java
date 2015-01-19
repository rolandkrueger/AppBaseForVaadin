package org.vaadin.appbase.uriactions;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.UI;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.roklib.webapps.uridispatching.AbstractURIActionCommand;
import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.roklib.webapps.uridispatching.IURIActionHandler.ParameterMode;
import org.roklib.webapps.uridispatching.URIActionDispatcher;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.event.impl.navigation.NavigateToURIEvent;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;
import org.vaadin.spring.UIScope;
import org.vaadin.uriactions.URIActionNavigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Der URL Action Manager kümmert sich um das komplette URL Handling. Hier werden die Dispatching
 * URL Handler und die jeweiligen konkreten Action Handler eingerichtet. Diese Einrichtung der
 * Handler Objekte ist hierarchisch organisiert. D.h., es wird hier für jeden Dispatching URL
 * Handler auf der obersten Ebene ein eigenes Manager-Objekt erzeugt, das dann dafür zuständig ist,
 * alle Handler unter dieser Ebene einzurichten. Z. B. gibt es für den Admin-Bereich, dessen
 * URL-Fragmente alle unter <code>.../admin/</code> liegen (<code>/admin/parameter/</code>,
 * <code>/admin/category/</code>, ...), die Klasse AdminURIActions. Alle Action Handler und
 * Dispatching URL Handler unter <i>admin</i> werden in dieser Klasse eingerichtet. Damit spiegelt
 * die Hierarchie dieser Action Manager Objekte die Hierarchie der URL Actions wieder.
 */
@Slf4j
@UIScope
@Component
public class URIActionManager extends AbstractUsesServiceProvider
{
  private URIActionNavigator          uriActionNavigator;
  @Getter private URIActionDispatcher uriActionDispatcher;

  public void initialize ()
  {
    uriActionDispatcher = new URIActionDispatcher (true);
    uriActionNavigator = new URIActionNavigator (UI.getCurrent ());
    uriActionNavigator.setURIActionDispatcher (uriActionDispatcher);
    uriActionNavigator.getURIActionDispatcher ().setParameterMode (ParameterMode.DIRECTORY_WITH_NAMES);

    if (log.isDebugEnabled ())
    {
      logActionOverview ();
    }
  }

  @Subscribe
  public void receiveNavigationEvent (NavigateToURIEvent event)
  {
    if (log.isTraceEnabled ())
    {
      log.trace ("Handling " + event);
    }
    uriActionNavigator.getNavigator ().navigateTo (event.getNavigationTarget ().toString ());
  }

  /**
   * Prints an overview of all registered URI action handlers along with their respective parameters
   * as debug log statements. This looks like the following example:
   * 
   * <pre>
   * <blockquote>
   *   /admin
   *   /admin/users
   *   /articles
   *   /articles/show ? {SingleIntegerURIParameter : articleId}
   *   /login
   * </blockquote>
   * </pre>
   */
  public void logActionOverview ()
  {
    List<String> uriOverview = new ArrayList<String> ();
    uriActionDispatcher.getRootActionHandler ().getActionURIOverview (uriOverview);
    log.debug ("Logging registered URI action handlers:");
    StringBuilder buf = new StringBuilder ();
    buf.append ('\n');
    Collections.sort (uriOverview);
    for (String url : uriOverview)
    {
      buf.append ('\t').append (url).append ('\n');
    }
    log.debug (buf.toString ());
  }

  public void setRootCommand (AbstractURIActionCommand rootActioncommand)
  {
    uriActionDispatcher.getRootActionHandler ().setRootCommand (rootActioncommand);
  }

  @Override
  protected void onServiceProviderSet ()
  {
    eventbus ().register (this);
  }

  public String getCurrentNavigationState ()
  {
    return uriActionNavigator.getNavigator ().getState ();
  }

  public final void addHandler (AbstractURIActionHandler subHandler)
  {
    uriActionDispatcher.addHandler (subHandler);
  }
}

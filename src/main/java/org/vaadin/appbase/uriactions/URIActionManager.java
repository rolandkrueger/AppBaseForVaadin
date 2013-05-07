package org.vaadin.appbase.uriactions;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.roklib.webapps.uridispatching.IURIActionHandler.ParameterMode;
import org.roklib.webapps.uridispatching.URIActionDispatcher;
import org.vaadin.appbase.event.impl.places.NavigationEvent;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;
import org.vaadin.uriactions.URIActionNavigator;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.UI;

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
      logActionOverview (uriActionDispatcher);
    }
  }

  @Subscribe
  public void receiveNavigationEvent (NavigationEvent event)
  {
    if (log.isTraceEnabled ())
    {
      log.trace ("Handling " + event);
    }
    uriActionNavigator.getNavigator ().navigateTo (event.getNavigationTarget ().toString ());
  }

  /**
   * Gibt eine Übersicht über alle registrierten URI Action Handler im Debug-Log aus.
   */
  private void logActionOverview (URIActionDispatcher dispatcher)
  {
    List<String> uriOverview = new ArrayList<String> ();
    dispatcher.getRootActionHandler ().getActionURIOverview (uriOverview);
    log.debug ("Logging registered URI action handlers:");
    StringBuilder buf = new StringBuilder ();
    buf.append ('\n');
    for (String url : uriOverview)
    {
      buf.append ('\t').append (url).append ('\n');
    }
    log.debug (buf.toString ());
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
}

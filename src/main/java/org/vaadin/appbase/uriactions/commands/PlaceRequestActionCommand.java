package org.vaadin.appbase.uriactions.commands;

import org.roklib.util.helper.CheckForNull;
import org.roklib.webapps.uridispatching.AbstractURIActionCommand;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.event.impl.places.PlaceRequestEvent;
import org.vaadin.appbase.places.AbstractPlace;
import org.vaadin.appbase.places.PlaceManager;

/**
 * URL Action Command, der verwendet wird, um einen Place Request auf den Eventbus zu schicken.
 * Diese Kommandos werden immer dann verwendet, wenn eine Navigation stattfinden soll, d.h. wenn
 * eine andere Seite dargestellt werden soll. Alle Place Requests werden zentral vom
 * {@link PlaceManager} verarbeitet. Dieser entscheidet, ob der angeforderte Place tatsächlich
 * gerendert werden soll oder nicht. Z.B. kann es notwendig sein, dass erst ein erfolgreicher Login
 * stattfinden muss, bevor ein Place gerendert wird.
 * 
 * @see PlaceRequestEvent
 */
public class PlaceRequestActionCommand extends AbstractURIActionCommand
{
  private static final long serialVersionUID = 313654855677764128L;

  private AbstractPlace     place;

  public PlaceRequestActionCommand (AbstractPlace place)
  {
    CheckForNull.check (place);
    this.place = place;
  }

  @Override
  public void execute ()
  {
    VaadinUIServices.get ().getEventbus ().post (new PlaceRequestEvent (this, place));
  }

  @Override
  public String toString ()
  {
    return "PlaceRequestActionCommand";
  }
}

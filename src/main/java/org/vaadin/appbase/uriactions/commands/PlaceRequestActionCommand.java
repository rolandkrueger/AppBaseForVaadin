package org.vaadin.appbase.uriactions.commands;

import org.roklib.urifragmentrouting.UriActionCommand;
import org.roklib.urifragmentrouting.annotation.RoutingContext;
import org.vaadin.appbase.event.impl.places.PlaceRequestEvent;
import org.vaadin.appbase.places.AbstractPlace;
import org.vaadin.appbase.places.PlaceManager;
import org.vaadin.appbase.uriactions.RoutingContextData;

import static com.google.common.base.Preconditions.checkNotNull;

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
public class PlaceRequestActionCommand implements UriActionCommand {

    private final AbstractPlace place;
    private RoutingContextData context;

    public PlaceRequestActionCommand(final AbstractPlace place) {
        this.place = checkNotNull(place);
    }

    @Override
    public void run() {
        context.getEventBus().post(new PlaceRequestEvent(this, place));
    }

    @Override
    public String toString() {
        return "PlaceRequestActionCommand: target place: " + place;
    }

    @RoutingContext
    public void setRoutingContext(RoutingContextData context) {
        this.context = context;
    }
}

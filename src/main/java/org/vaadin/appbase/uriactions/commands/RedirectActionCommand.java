package org.vaadin.appbase.uriactions.commands;


import com.google.common.base.MoreObjects;
import org.roklib.urifragmentrouting.UriActionCommand;
import org.roklib.urifragmentrouting.annotation.RoutingContext;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.vaadin.appbase.event.impl.navigation.NavigateToURIEvent;
import org.vaadin.appbase.uriactions.RoutingContextData;

import static com.google.common.base.Preconditions.checkNotNull;

public class RedirectActionCommand implements UriActionCommand {
    private final UriPathSegmentActionMapper redirectToHandler;
    private RoutingContextData context;

    public RedirectActionCommand(final UriPathSegmentActionMapper redirectToHandler) {
        this.redirectToHandler = checkNotNull(redirectToHandler);
    }

    @Override
    public void run() {
        context.getEventBus().post(new NavigateToURIEvent(this, context.getUriActionMapperTree().assembleUriFragment(redirectToHandler)));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("target", context.getUriActionMapperTree().assembleUriFragment(redirectToHandler))
            .toString();
    }

    @RoutingContext
    public void setRoutingContext(RoutingContextData context) {
        this.context = context;
    }
}

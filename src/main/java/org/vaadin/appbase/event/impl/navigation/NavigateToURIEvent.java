package org.vaadin.appbase.event.impl.navigation;

import com.google.common.base.Preconditions;
import org.vaadin.appbase.event.Event;

public class NavigateToURIEvent extends Event {
    private final String navigationTarget;

    public NavigateToURIEvent(final Object source, final String navigationTarget) {
        super(source);
        Preconditions.checkNotNull(navigationTarget);

        this.navigationTarget = navigationTarget;
    }

    public String getNavigationTarget() {
        return navigationTarget;
    }

    @Override
    protected String formatLogMessageImpl() {
        return "navigationTarget=" + navigationTarget;
    }
}

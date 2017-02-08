package org.vaadin.appbase.uriactions;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.UI;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.roklib.urifragmentrouting.UriActionMapperTree;
import org.springframework.stereotype.Component;
import org.vaadin.appbase.event.impl.navigation.NavigateToURIEvent;
import org.vaadin.appbase.service.AbstractUsesServiceProvider;
import org.vaadin.spring.UIScope;
import org.vaadin.uriactions.UriFragmentActionNavigatorWrapper;

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
public class URIActionManager extends AbstractUsesServiceProvider {
    private UriFragmentActionNavigatorWrapper uriActionNavigator;
    @Getter
    private UriActionMapperTree uriActionMapperTree;

    public void initialize(UriActionMapperTree uriActionMapperTree, RoutingContextData routingContextData) {
        this.uriActionMapperTree = uriActionMapperTree;
        uriActionNavigator = new UriFragmentActionNavigatorWrapper(UI.getCurrent());
        uriActionNavigator.setUriActionMapperTree(uriActionMapperTree);
        uriActionNavigator.setRoutingContext(routingContextData);

        logActionOverview();
    }

    @Subscribe
    public void receiveNavigationEvent(final NavigateToURIEvent event) {
        if (log.isTraceEnabled()) {
            log.trace("Handling " + event);
        }
        uriActionNavigator.getNavigator().navigateTo(event.getNavigationTarget());
    }

    /**
     * Prints an overview of all registered URI action handlers along with their respective parameters
     * as debug log statements. This looks like the following example:
     * <p>
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
    public void logActionOverview() {
        if (!log.isDebugEnabled()) {
            return;
        }
        final List<String> uriOverview = uriActionMapperTree.getMapperOverview();
        log.debug("Logging registered URI action handlers:");
        final StringBuilder buf = new StringBuilder();
        buf.append('\n');
        for (final String url : uriOverview) {
            buf.append('\t').append(url).append('\n');
        }
        log.debug(buf.toString());
    }

    @Override
    protected void onServiceProviderSet() {
        eventbus().register(this);
    }

    public String getCurrentNavigationState() {
        return uriActionNavigator.getNavigator().getState();
    }
}

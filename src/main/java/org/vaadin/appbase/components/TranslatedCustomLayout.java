package org.vaadin.appbase.components;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import org.vaadin.appbase.service.templating.TemplateData;
import org.vaadin.appbase.view.IView;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import java.io.IOException;

import static com.google.common.base.Preconditions.*;

public class TranslatedCustomLayout implements IView {
    private final TemplateData templateData;
    private CustomLayout layout;

    public TranslatedCustomLayout(TemplateData templateData) {
        this.templateData = checkNotNull(templateData);
    }

    @Override
    public IView buildLayout() {
        layout = createTranslatedCustomLayout(templateData);

        if (layout != null && ! VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode()) {
            new ComponentHighlighterExtension(getLayout()).setComponentDebugLabel(getClass().getName()
                    + " (template name: " + templateData.name() + ".html)");
        }
        return this;
    }

    @Override
    public Component getContent() {
        return layout;
    }

    protected CustomLayout getLayout() {
        return layout;
    }

    private CustomLayout createTranslatedCustomLayout(TemplateData templateData) {
        CustomLayout layout = null;
        try {
            layout = new CustomLayout(templateData.data());
        } catch (IOException ioExc) {
            throw new IllegalStateException("Error while loading CustomLayout template " + templateData, ioExc);
        }
        return layout;
    }
}

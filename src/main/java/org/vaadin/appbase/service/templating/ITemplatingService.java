package org.vaadin.appbase.service.templating;

import java.io.Serializable;

public interface ITemplatingService extends Serializable {
    public TemplateData getLayoutTemplate(String templatePath);
}
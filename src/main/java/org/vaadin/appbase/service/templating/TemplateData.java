package org.vaadin.appbase.service.templating;

import com.google.auto.value.AutoValue;

import java.io.InputStream;

@AutoValue
public abstract class TemplateData {
    public static TemplateData of(String name, InputStream data) {
        return new AutoValue_TemplateData(name, data);
    }

    public abstract String name();

    public abstract InputStream data();
}

package org.vaadin.appbase.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vaadin.spring.VaadinSessionScope;

import java.io.Serializable;
import java.util.Locale;

@VaadinSessionScope
@Component
@Slf4j
public class SessionContext implements Serializable {

  private static final long serialVersionUID = -1178538977327157062L;

  private Locale locale;

  public void setLocale(Locale locale) {
    log.debug("Setting locale: " + locale);
    this.locale = new Locale(locale.getLanguage());
  }

  public Locale getLocale() {
    return locale;
  }

  public Locale getDefaultLocale() {
    return Locale.ENGLISH;
  }

}

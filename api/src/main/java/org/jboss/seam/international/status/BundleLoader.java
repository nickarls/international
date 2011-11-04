package org.jboss.seam.international.status;

import java.util.Locale;
import java.util.ResourceBundle;

public interface BundleLoader {
    public ResourceBundle loadBundle(Locale locale, String key);
}
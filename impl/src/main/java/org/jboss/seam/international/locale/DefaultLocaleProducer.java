/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.international.locale;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

@ApplicationScoped
public class DefaultLocaleProducer implements Serializable {
    private static final long serialVersionUID = -4534087316489937649L;

    private Logger log = Logger.getLogger(DefaultLocaleProducer.class);

    @Inject
    @DefaultLocale
    private Instance<String> defaultLocaleKey;

    @Produces
    @Named
    private Locale defaultLocale;

    @PostConstruct
    public void init() {
        if (!defaultLocaleKey.isUnsatisfied()) {
            try {
                String key = defaultLocaleKey.get();
                defaultLocale = LocaleUtils.toLocale(key);
            } catch (IllegalArgumentException e) {
                log.error("DefaultLocaleProducer: Default Locale key of " + defaultLocale + " was not formatted correctly", e);
            }
        }
        if (null == defaultLocale) {
            defaultLocale = Locale.getDefault();
        }
    }
}

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.international.test.jdktimezone;

import java.util.TimeZone;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.jdktimezone.DefaultTimeZoneProducer;
import org.jboss.seam.international.jdktimezone.ForwardingTimeZone;
import org.jboss.seam.international.test.util.Deployments;
import org.jboss.seam.international.test.util.Libraries;
import org.jboss.seam.international.timezone.DefaultTimeZone;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test overriding default TimeZone
 *
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
@RunWith(Arquillian.class)
public class DefaultTimeZoneOverrideTest {
    @Deployment(name = "DefaultTimeZoneOverride")
    public static WebArchive createTestArchive() {
        return Deployments.addEmptyBeansXML(Deployments.createWebArchive())
                .addAsLibraries(Libraries.solderLibraryWithTransitives())
                .addClass(DefaultTimeZoneProducer.class)
                .addClass(DefaultTimeZoneOverrideProducerBean.class)
                .addClass(DefaultTimeZone.class)
                .addClass(ForwardingTimeZone.class);
    }

    @Inject
    TimeZone timeZone;

    @Test
    public void testDefaultTimeZoneProducerDirect() {
        Assert.assertNotNull(timeZone);
        Assert.assertEquals("America/Tijuana", timeZone.getID());
    }
}

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
package org.jboss.seam.international.test.datetimezone;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.Alter;
import org.jboss.seam.international.datetimezone.DefaultDateTimeZoneProducer;
import org.jboss.seam.international.datetimezone.UserDateTimeZoneProducer;
import org.jboss.seam.international.timezone.DefaultTimeZone;
import org.jboss.seam.solder.core.Client;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserDateTimeZoneTest {
    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar").addClass(UserDateTimeZoneProducer.class)
                .addClass(DefaultDateTimeZoneProducer.class).addClass(DefaultTimeZone.class).addClass(Alter.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        // .addManifestResource("org/jboss/seam/international/test/datetimezone/user-timezone.xml",
        // ArchivePaths.create("beans.xml"));
    }

    @Inject
    @Client
    DateTimeZone timeZone;

    @Inject
    @Alter
    @Client
    Event<DateTimeZone> timeZoneEvent;

    @Inject
    @Client
    Instance<DateTimeZone> timeZoneSource;

    @Test
    public void testUserTimeZoneProducerDirect() {
        Assert.assertNotNull(timeZone);
    }

    @Test
    public void testUserTimeZoneEvent() {
        DateTimeZone tijuana = DateTimeZone.forID("America/Tijuana");
        Assert.assertNotNull(timeZone);
        Assert.assertFalse(timeZone.equals(tijuana));
        timeZoneEvent.fire(tijuana);
        DateTimeZone tz = timeZoneSource.get();
        Assert.assertNotNull(tz);
        Assert.assertTrue(tz.equals(tijuana));
    }
}

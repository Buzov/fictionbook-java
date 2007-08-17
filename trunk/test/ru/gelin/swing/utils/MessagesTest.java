/*
 *  Swing Utils.
 *  Copyright (C) 2007  Denis Nelubin aka Gelin
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *  http://gelin.ru
 *  mailto:den@gelin.ru
 */

package ru.gelin.swing.utils;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.Locale;

public class MessagesTest {

    protected String baseName = "messages";
    protected String invalidBaseName = "invalid";
    protected Locale locale = new Locale("ru");

    @Before public void setUp() {
    }

    @Test public void testConstructor() {
        Messages msg = new Messages(baseName, locale);
        assertNotNull(msg.bundle);
        assertEquals(locale, msg.bundle.getLocale());
    }

    @Test public void testConstructorInvalidBundle() {
        Messages msg = new Messages(invalidBaseName, locale);
        assertNull(msg.bundle);
        assertEquals("test", msg.get("test"));
    }

    @Test public void testGetInstance() {
        Messages msg = Messages.getInstance(baseName);
        assertNotNull(msg.bundle);
    }

    @Test public void testGetInstanceWithLocale() {
        Messages msg = Messages.getInstance(baseName, locale);
        assertEquals(locale, msg.bundle.getLocale());
        Messages msg2 = Messages.getInstance(baseName, locale);
        assertEquals(locale, msg.bundle.getLocale());
        assertSame(msg, msg2);
    }

    @Test public void testGet() {
        Messages msg = Messages.getInstance(baseName, Locale.US);
        assertEquals("Test", msg.get("test"));
        msg = Messages.getInstance(baseName, locale);
        assertEquals("RUTest", msg.get("test"));
    }

    @Test public void testGetWithArguments() {
        Messages msg = Messages.getInstance(baseName);
        assertEquals("Test foo", msg.get("test.args", new String[] { "foo" }));
    }

    @Test public void testGetKey() {
        assertEquals("testru", Messages.getKey("test", locale));
    }

}
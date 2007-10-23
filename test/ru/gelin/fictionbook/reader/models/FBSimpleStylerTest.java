/*
 *  Fiction Book Tools.
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
 *  http://gelin.ru/project/fictionbook/
 *  mailto:den@gelin.ru
 */

package ru.gelin.fictionbook.reader.models;

import org.junit.*;
import static org.junit.Assert.*;

import javax.swing.text.StyleContext;

public class FBSimpleStylerTest {

    FBSimpleStyler styler;

    @Before public void setUp() {
        styler = new FBSimpleStyler();
    }

    @Test public void testGetStyleContext() {
        StyleContext styles = styler.getStyleContext();
        assertNotNull(styles);
        assertNotNull(styles.getStyle("default"));
    }

    @Test public void testParseLine() {
        String[] spv = styler.parseLine("style.property = value");
        assertEquals("style", spv[0]);
        assertEquals("property", spv[1]);
        assertEquals("value", spv[2]);
        spv = styler.parseLine("long.style.property = value");
        assertEquals("long.style", spv[0]);
        assertEquals("property", spv[1]);
        assertEquals("value", spv[2]);
        spv = styler.parseLine("style.property=value");
        assertEquals("style", spv[0]);
        assertEquals("property", spv[1]);
        assertEquals("value", spv[2]);
        spv = styler.parseLine("#style.property = value");
        assertNull(spv);
        spv = styler.parseLine("style.property = value=");
        assertEquals("style", spv[0]);
        assertEquals("property", spv[1]);
        assertEquals("value=", spv[2]);
    }

    @Test public void testAddXPath() {
        styler.styles.addStyle("test", null);
        styler.addXPath("test", "//fb:p");
        assertEquals(styler.styles.getStyle("test"),
            styler.xpathToStyle.get("//fb:p"));
    }

}
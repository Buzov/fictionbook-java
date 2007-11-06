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

import java.io.File;
import javax.swing.text.StyleContext;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.Element;
import org.dom4j.Node;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

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

    @Test public void testApplyStyle() throws FBException {
        FBDocument fb = new FBDocument(new File("docs/test2.1.fb2"));
        FBSimpleDocument document = new FBSimpleDocument(fb);
        Node pNode = fb.getDocument().selectSingleNode("//fb:p");
        FBSimpleElement pElement = (FBSimpleElement)document.getElement(pNode);
        styler.applyStyle(pElement);
        Style style = styler.styles.getStyle("p");
        assertEquals(style, pElement.getAttributes());
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

    @Test public void testSetXPath() {
        Style style = styler.styles.addStyle("test", null);
        styler.setXPath("test", "//fb:p");
        assertEquals(style, styler.xpathToStyle.get("//fb:p"));
    }

    @Test public void testSetAlignment() {
        Style style = styler.styles.addStyle("test", null);
        styler.setAlignment("test", "left");
        assertEquals(StyleConstants.ALIGN_LEFT,
            StyleConstants.getAlignment(style));
        styler.setAlignment("test", "right");
        assertEquals(StyleConstants.ALIGN_RIGHT,
            StyleConstants.getAlignment(style));
        styler.setAlignment("test", "center");
        assertEquals(StyleConstants.ALIGN_CENTER,
            StyleConstants.getAlignment(style));
        styler.setAlignment("test", "justified");
        assertEquals(StyleConstants.ALIGN_JUSTIFIED,
            StyleConstants.getAlignment(style));
    }

    @Test public void testSetBold() {
        Style style = styler.styles.addStyle("test", null);
        styler.setBold("test", "false");
        assertEquals(false, StyleConstants.isBold(style));
        styler.setBold("test", "true");
        assertEquals(true, StyleConstants.isBold(style));
    }

    @Test public void testSetView() {
        Style style = styler.styles.addStyle("test", null);
        styler.setView("test", "view");
        assertEquals("view", style.getAttribute(
                FBSimpleStyler.ViewAttribute));
    }

}
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
import javax.swing.text.ViewFactory;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import org.dom4j.Node;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class FBSimpleEditorKitTest {

    ViewFactory factory;
    protected FBDocument fb;
    protected FBSimpleDocument document;

    @Before public void setUp() throws FBException {
        StyledEditorKit kit = new FBSimpleEditorKit();
        factory = kit.getViewFactory();
        fb = new FBDocument(new File("docs/test2.1.fb2"));
        document = new FBSimpleDocument(fb);
    }

    @Test public void testViewFactory() {
        Node node = fb.getDocument().selectSingleNode(
            "//fb:section[@id=\"half0_2\"]/fb:p");
        Element element = (FBSimpleElement)document.getElement(node);
        assertNotNull(element);
        assertEquals("javax.swing.text.ParagraphView",
            factory.create(element).getClass().getName());
    }

    @Test public void testViewFactory2() {
        Element element = document.getDefaultRootElement();
        MutableAttributeSet attributes =
                (MutableAttributeSet)element.getAttributes();
        attributes.addAttribute(FBSimpleStyler.ViewAttribute, "paragraph");
        assertEquals("javax.swing.text.ParagraphView",
            factory.create(element).getClass().getName());
        attributes.addAttribute(FBSimpleStyler.ViewAttribute, "box");
        assertEquals("javax.swing.text.BoxView",
            factory.create(element).getClass().getName());
    }

}
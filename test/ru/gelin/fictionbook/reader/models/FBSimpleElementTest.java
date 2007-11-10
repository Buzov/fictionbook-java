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
import javax.swing.text.Element;
import org.dom4j.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class FBSimpleElementTest {

    protected Log log = LogFactory.getLog(this.getClass());

    FBDocument fb;
    FBSimpleDocument document;
    FBSimpleElement element;

    @Before public void setUp() throws FBException {
        fb = new FBDocument(new File("test/test.fb2"));
        document = new FBSimpleDocument(fb);
        Node node = fb.getDocument().
            selectSingleNode("//fb:section[@id='section1']/fb:title/fb:p");
        element = (FBSimpleElement)document.getElement(node);
        log.debug(element);
    }

    @Test public void testGetDocument() {
        assertSame(document, element.getDocument());
    }

    @Test public void testGetParentElement() {
        Node node = fb.getDocument().
            selectSingleNode("//fb:section[@id='section1']/fb:title");
        Element parent = document.getElement(node);
        assertSame(parent, element.getParentElement());
    }

    @Test public void testGetParentElementNull() {
        Node node = fb.getDocument().getRootElement();
        Element root = document.getElement(node);
        assertNull(root.getParentElement());
    }

    @Test public void testGetName() {
        assertEquals("p", element.getName());
    }

    @Test public void testGetAttributes() {
        assertEquals(document.styles.getStyle("title"), element.getAttributes());
    }

    @Test public void testGetStartOffset() {
        assertEquals("Test FictionBook".length(),
            element.getStartOffset());
        //test element is first in document after book title
        //"Test FictionBook"
    }

    @Test public void testGetEndOffset() {
        //length of book title + length of the element
        assertEquals("Test FictionBook".length() +
            "Section 1. Title.".length(), element.getEndOffset());
    }

    @Test public void testGetElementIndexLeaf() {
        assertEquals(-1, element.getElementIndex(1));  //no children
    }

    @Test public void testGetElementIndexLess() {
        Node node = fb.getDocument().selectSingleNode("//fb:body[2]");
        Element body = document.getElement(node);
        //index of the first child
        assertEquals(0, body.getElementIndex(1));
    }

    @Test public void testGetElementIndexMore() {
        Node node = fb.getDocument().selectSingleNode("//fb:body[1]");
        int children = node.selectNodes("*[node()]").size();
        Element body = document.getElement(node);
        //index of last subelement of this <body>
        assertEquals(children - 1, body.getElementIndex(document.getLength()));
    }

    @Test public void testGetElementIndex() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='section1']");
        Element section = document.getElement(node);
        node = node.selectSingleNode("fb:p");
        Element p = document.getElement(node);
        //index of <p> after <title> in <section>
        assertEquals(1, section.getElementIndex(p.getStartOffset() + 1));
    }

    @Test public void testGetElementCountLeaf() {
        assertEquals(0, element.getElementCount());
    }

    @Test public void testGetElementCount() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='section1']");
        int children = node.selectNodes("*[node()]").size();
        Element section = document.getElement(node);
        assertEquals(children, section.getElementCount());
    }

    @Test public void testGetElement() {
        Node node = fb.getDocument().
            selectSingleNode("//fb:section[@id='section1']/fb:title");
        Element parent = document.getElement(node);
        assertSame(element, parent.getElement(0));
    }

    @Test public void testIsLeaf() {
        Node node = fb.getDocument().
            selectSingleNode("//fb:section[@id='section1']/fb:title");
        Element parent = document.getElement(node);
        assertFalse(parent.isLeaf());
        assertTrue(element.isLeaf());
    }

}
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

package ru.gelin.fictionbook.viewer.models;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import javax.swing.text.Element;
import org.dom4j.Node;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class FBSimpleElementTest {

    protected FBDocument fb;
    protected FBSimpleDocument document;
    protected FBSimpleElement element;

    @Before public void setUp() throws FBException {
        fb = new FBDocument(new File("docs/test2.1.fb2"));
        document = new FBSimpleDocument(fb);
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='half0']/fb:title/fb:p");
        element = (FBSimpleElement)document.getElement(node);
    }

    @Test public void testGetDocument() {
        assertSame(document, element.getDocument());
    }

    @Test public void testGetParentElement() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='half0']/fb:title");
        Element parent = document.getElement(node);
        assertSame(parent, element.getParentElement());
    }

    @Test public void testGetParentElementNull() {
        Node node = fb.getDocument().selectSingleNode("//fb:body[1]");
        Element root = document.getElement(node);
        assertNull(root.getParentElement());
    }

    @Test public void testGetName() {
        assertEquals("p", element.getName());
    }

    @Test public void testGetAttributes() {
        //TODO more complex test
        assertEquals(document.defaultAttributeSet, element.getAttributes());
    }

    @Test public void testGetStartOffset() {
        assertEquals(0, element.getStartOffset());  //test element is first in document
    }

    @Test public void testGetEndOffset() {
        //"Часть I. Типа пролог"
        assertEquals(20, element.getEndOffset());
    }

    @Test public void testGetElementIndexLeaf() {
        assertEquals(-1, element.getElementIndex(10));  //test element is leaf
    }

    @Test public void testGetElementIndexLess() {
        Node node = fb.getDocument().selectSingleNode("//fb:body[2]");
        Element body = document.getElement(node);
        assertEquals(0, body.getElementIndex(10));
    }

    @Test public void testGetElementIndexMore() {
        Node node = fb.getDocument().selectSingleNode("//fb:body[1]");
        Element body = document.getElement(node);
        assertEquals(1, body.getElementIndex(document.getLength()));
    }

    @Test public void testGetElementIndex() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='half0']");
        Element section = document.getElement(node);
        //index of <epigraph> after <title> in <section>
        assertEquals(1, section.getElementIndex(21));
    }

    @Test public void testGetElementCountLeaf() {
        assertEquals(0, element.getElementCount());
    }

    @Test public void testGetElementCount() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='half0']");
        Element section = document.getElement(node);
        //this section contains <title>, <epigraph> and four sub<section>
        assertEquals(4, section.getElementCount());
    }

    @Test public void testGetElement() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='half0']/fb:title");
        Element parent = document.getElement(node);
        assertSame(element, parent.getElement(0));
    }

    @Test public void testIsLeaf() {
        Node node = fb.getDocument().selectSingleNode("//fb:section[@id='half0']/fb:title");
        Element parent = document.getElement(node);
        assertFalse(parent.isLeaf());
        assertTrue(element.isLeaf());
    }

}
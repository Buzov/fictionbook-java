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
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.Segment;
import javax.swing.text.Position;
import javax.swing.text.Element;
import org.dom4j.Node;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class FBSimpleDocumentTest {

    protected FBDocument fb;
    protected FBSimpleDocument document;

    @Before public void setUp() throws FBException {
        fb = new FBDocument(new File("docs/test2.1.fb2"));
        document = new FBSimpleDocument(fb);
    }

    @Test public void testConstructorPositionToElement() {
        assertNotNull(document.content);
        assertNotNull(document.positionToElement);
        assertEquals(document.content.length,
            document.positionToElement.length);
        assertNotNull(document.positionToElement[0]);
        assertNotNull(document.positionToElement
            [document.positionToElement.length - 1]);
    }

    @Test public void testGetLength() {
        assertTrue(document.getLength() > 0);
    }

    @Ignore("this is unmodifiable document")
    @Test public void testAddDocumentListener() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testRemoveDocumentListener() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testAddUndoableEditListener() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testRemoveUndoableEditListener() {
    }

    @Test public void testGetProperty() {
        //"Тестовый ознакомительный документ FictionBook 2.1"
        assertEquals("\u0422\u0435\u0441\u0442\u043e\u0432\u044b\u0439 " +
            "\u043e\u0437\u043d\u0430\u043a\u043e\u043c\u0438\u0442\u0435\u043b\u044c\u043d\u044b\u0439 " +
            "\u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442 FictionBook 2.1",
            document.getProperty(Document.TitleProperty));
    }

    @Test public void testPutProperty() {
        document.putProperty("some", "value");
        assertEquals("value", document.getProperty("some"));
    }

    @Ignore("this is unmodifiable document")
    @Test public void testRemove() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testInsertString() {
    }

    @Test public void testGetText() throws BadLocationException  {
        //"Часть I."
        assertEquals("\u0427\u0430\u0441\u0442\u044c I.",
            document.getText(49, 8));
        //"части."
        assertEquals("\u0447\u0430\u0441\u0442\u0438.",
            document.getText(document.getLength() - 6, 6));
    }

    @Test(expected = BadLocationException.class)
    public void testGetTextBadLocation1() throws BadLocationException {
        document.getText(-1, 5);
    }

    @Test(expected = BadLocationException.class)
    public void testGetTextBadLocation2() throws BadLocationException  {
        document.getText(document.getLength() - 1, 5);
    }

    @Test public void testGetTextWithSegment() throws BadLocationException {
        Segment text = new Segment();
        document.getText(49, 8, text);
        //"Часть I."
        assertEquals("\u0427\u0430\u0441\u0442\u044c I.", text.toString());
        text = new Segment();
        document.getText(document.getLength() - 6, 6, text);
        //"части."
        assertEquals("\u0447\u0430\u0441\u0442\u0438.", text.toString());
    }

    @Test public void testGetStartPosition() {
        assertEquals(0, document.getStartPosition().getOffset());
    }

    @Test public void testGetEndPosition() {
        assertEquals(document.getLength(), document.getEndPosition().getOffset());
    }

    @Test public void testCreatePosition() throws BadLocationException {
        assertEquals(5, document.createPosition(5).getOffset());
    }

    @Test(expected = BadLocationException.class)
    public void testCreatePositionBadLocation1() throws BadLocationException {
        document.createPosition(-1);
    }

    @Test(expected = BadLocationException.class)
    public void testCreatePositionBadLocation2() throws BadLocationException {
        document.createPosition(document.getLength() + 1);
    }

    @Test public void testGetRootElements() {
        Element[] roots = document.getRootElements();
        assertEquals(1, roots.length);
        Node node = fb.getDocument().getRootElement();
        Element body = document.getElement(node);
        assertNotNull(body);
        assertEquals(body, roots[0]);
    }

    @Test public void testGetDefaultRootElement() {
        Node node = fb.getDocument().getRootElement();
        Element body = document.getElement(node);
        assertNotNull(body);
        assertEquals(body, document.getDefaultRootElement());
    }

    @Ignore("this is unmodifiable document")
    @Test public void testRender() {
    }

    @Ignore("implemented by StyleContext")
    @Test public void testAddStyle() {
    }

    @Ignore("implemented by StyleContext")
    @Test public void testRemoveStyle() {
    }

    @Ignore("implemented by StyleContext")
    @Test public void testGetStyle() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testSetCharacterAttributes() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testSetParagraphAttributes() {
    }

    @Ignore("this is unmodifiable document")
    @Test public void testSetLogicalStyle() {
    }

    @Test public void testGetLogicalStyle() {
        //assertEquals(document.defaultStyle, document.getLogicalStyle(0));
        assertNull(document.getLogicalStyle(0));
        //TODO: make more smart test for more smart style hierarchy
    }

    @Test public void testGetParagraphElement() {
        //"Часть I." at offset 49
        Node pNode = fb.getDocument().selectSingleNode(
            "//fb:section[@id=\"half0\"]/fb:title/fb:p");
        Element pElement = document.getParagraphElement(49);
        assertEquals(document.getElement(pNode), pElement);
    }

    @Test public void testGetCharacterElement() {
        //"сноска 1" first <a> element
        Node aNode = fb.getDocument().selectSingleNode("//fb:a");
        Element aElement = document.getElement(aNode);
        assertEquals(aElement,
            document.getCharacterElement(aElement.getStartOffset()));
    }

    @Ignore("implemented by StyleContext")
    @Test public void testGetForeground() {
    }

    @Ignore("implemented by StyleContext")
    @Test public void testGetBackground() {
    }

    @Ignore("implemented by StyleContext")
    @Test public void testGetFont() {
    }

}
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
import javax.swing.Icon;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.Segment;
import javax.swing.text.Position;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import org.dom4j.Node;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class FBSimpleDocumentTest {

    FBDocument fb;
    FBSimpleDocument document;

    @Before public void setUp() throws FBException {
        fb = new FBDocument(new File("test/test.fb2"));
        document = new FBSimpleDocument(fb);
    }

    @Test public void printDocumentContent() {
        System.out.println("------------");
        System.out.println(document.content);
        System.out.println("------------");
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
        assertEquals("Test FictionBook",
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
        //first section name after book title "Text FictionBook"
        assertEquals("Section 1. Title.",
            document.getText("Test FictionBook".length(),
                "Section 1. Title.".length()));
        //last section content
        assertEquals("Last Section. Content.",
            document.getText(document.getLength() -
                "Last Section. Content.".length(),
                "Last Section. Content.".length()));
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
        document.getText("Test FictionBook".length(),
            "Section 1. Title.".length(), text);
        assertEquals("Section 1. Title.", text.toString());
        text = new Segment();
        document.getText(document.getLength() -
            "Last Section. Content.".length(),
            "Last Section. Content.".length(), text);
        assertEquals("Last Section. Content.", text.toString());
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
        //"Section 1. Title." after "Test FictionBook"
        Node pNode = fb.getDocument().selectSingleNode(
            "//fb:section[@id=\"section1\"]/fb:title/fb:p");
        Element pElement = document.getParagraphElement(
            "Test FictionBook".length());
        assertEquals(document.getElement(pNode), pElement);
    }

    @Test public void testGetCharacterElement() {
        //"Link" first <a> element
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

    @Test public void testEmptyElement() throws BadLocationException {
        Node node = fb.getDocument().selectSingleNode("//fb:empty-line");
        Element element = document.getElement(node);
        assertEquals(1, element.getEndOffset() - element.getStartOffset());
        assertEquals(" ", document.getText(element.getStartOffset(),
                element.getEndOffset() - element.getStartOffset()));
    }

    @Test public void testImageElement()
            throws BadLocationException, FBException {
        Node node = fb.getDocument().selectSingleNode(
                "//fb:image[@id='crow-image']");
        Element element = document.getElement(node);
        //Icon icon = fb.getImage("#crow.png");
        //assertEquals(icon, StyleConstants.getIcon(element.getAttributes()));
        //two icons are always differ :(
        assertNotNull(StyleConstants.getIcon(element.getAttributes()));
    }

    @Test public void testBrokenImageElement()
            throws BadLocationException, FBException {
        Node node = fb.getDocument().selectSingleNode(
                "//fb:image[@id='broken-image']");
        Element element = document.getElement(node);
        assertEquals("label", element.getAttributes().getAttribute(
                FBSimpleStyler.ViewAttribute));
        //assertEquals("alt text", document.getText(element.getStartOffset(),
        //        element.getEndOffset() - element.getStartOffset()));
        //TODO: fix it
    }

    @Test public void testImageAltText() {
        Node node = fb.getDocument().selectSingleNode(
                "//fb:image[@id='broken-image']/@alt");
        assertEquals("alt text", node.getText());
    }

}
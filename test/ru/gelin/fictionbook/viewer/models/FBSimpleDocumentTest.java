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
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class FBSimpleDocumentTest {

    protected FBDocument fb;
    protected FBSimpleDocument document;

    @Before public void setUp() throws FBException {
        fb = new FBDocument(new File("docs/test2.1.fb2"));
        document = new FBSimpleDocument(fb);
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
            document.getText(0, 8));
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

    @Ignore("not implemented yet")
    @Test public void testWithSegment() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetStartPosition() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetEndPosition() {
    }

    @Ignore("not implemented yet")
    @Test public void testCreatePosition() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetRootElements() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetDefaultRootElement() {
    }

    @Ignore("not implemented yet")
    @Test public void testRender() {
    }

    @Ignore("not implemented yet")
    @Test public void testAddStyle() {
    }

    @Ignore("not implemented yet")
    @Test public void testRemoveStyle() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetStyle() {
    }

    @Ignore("not implemented yet")
    @Test public void testSetCharacterAttributes() {
    }

    @Ignore("not implemented yet")
    @Test public void testSetParagraphAttributes() {
    }

    @Ignore("not implemented yet")
    @Test public void testSetLogicalStyle() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetLogicalStyle() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetParagraphElement() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetCharacterElement() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetForeground() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetBackground() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetFont() {
    }

}
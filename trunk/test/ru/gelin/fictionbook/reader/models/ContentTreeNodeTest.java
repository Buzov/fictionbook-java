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
import org.dom4j.Node;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class ContentTreeNodeTest {

    FBDocument document;

    @Before public void setUp() throws FBException {
        document = new FBDocument(new File("test/test.fb2"));
    }

    @Test public void testToStringTitle() {
        Node node = document.getDocument().
            selectSingleNode("//fb:section[@id='section1']");
        ContentTreeNode treeNode = new ContentTreeNode(document, node);
        assertEquals("Section 1. Title.", treeNode.toString());
    }

    @Test public void testToStringElement() {
        Node node = document.getDocument().
            selectSingleNode("//fb:body[1]");
        ContentTreeNode treeNode = new ContentTreeNode(document, node);
        assertEquals("<body>", treeNode.toString());
    }

    @Test public void testToStringId() {
        Node node = document.getDocument().
            selectSingleNode("//fb:section[@id='last-section']");
        ContentTreeNode treeNode = new ContentTreeNode(document, node);
        assertEquals("[last-section]", treeNode.toString());
    }

    @Test public void testToStringComplexTitle() {
        Node node = document.getDocument().
            selectSingleNode("//fb:section[@id='paragraph-test']");
        ContentTreeNode treeNode = new ContentTreeNode(document, node);
        //not meaninfull spaces must be removed.
        assertEquals("Paragraphs. In title too.", treeNode.toString());
    }

}
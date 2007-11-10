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
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class ContentTreeModelTest {

    FBDocument document;
    ContentTreeModel model;

    @Before public void setUp() throws FBException {
        document = new FBDocument(new File("test/test.fb2"));
        model = new ContentTreeModel(document);
    }

    @Test public void testGetRoot() {
        assertEquals("Test FictionBook", model.getRoot().toString());
    }

    @Test public void testGetChildOfRoot() {
        Object root = model.getRoot();
        Object child = model.getChild(root, 0);
        assertEquals("<body>", child.toString());
        child = model.getChild(root, 1);
        assertEquals("[notes]", child.toString());
    }

    @Test public void testGetChildOfNode() {
        Object root = model.getRoot();
        Object node = model.getChild(root, 0);
        Object child = model.getChild(node, 0);
        assertEquals("Section 1. Title.", child.toString());
        child = model.getChild(node, 1);
        //"Часть II. Типа текст"
        assertEquals("Section 2. Title.", child.toString());
    }

    @Test public void testGetChildCountOfRoot() {
        Object root = model.getRoot();
        int children = document.getDocument().selectNodes("//fb:body").size();
        assertEquals(children, model.getChildCount(root));
    }

    @Test public void testGetChildCountOfNode() {
        Object root = model.getRoot();
        Object node = model.getChild(root, 0);
        int children = document.getDocument().selectNodes("//fb:body[1]/fb:section").size();
        assertEquals(children, model.getChildCount(node));
    }

    @Test public void testIsLeaf() {
        Object root = model.getRoot();  //book-title
        assertEquals(false, model.isLeaf(root));
        Object node = model.getChild(root, 0);  //body[0]
        assertEquals(false, model.isLeaf(node));
        node = model.getChild(node, 0); //body[0]/section[0]
        assertEquals(true, model.isLeaf(node));
    }

    @Ignore("this model represents immutable tree, listeners are not used")
    @Test public void testPathChanged() {
    }

    @Test public void testGetIndexOfChildForNulls() {
        Object root = model.getRoot();
        Object child = model.getChild(root, 0);
        assertEquals(-1, model.getIndexOfChild(null, null));
        assertEquals(-1, model.getIndexOfChild(null, child));
        assertEquals(-1, model.getIndexOfChild(root, null));
    }

    @Test public void testGetIndexOfChildOfRoot() {
        Object root = model.getRoot();
        Object child = model.getChild(root, 0);
        assertEquals(0, model.getIndexOfChild(root, child));
        child = model.getChild(root, 1);
        assertEquals(1, model.getIndexOfChild(root, child));
        child = model.getChild(child, 0);
        assertEquals(-1, model.getIndexOfChild(root, child));
    }

    @Test public void testGetIndexOfChildOfNode() {
        Object root = model.getRoot();
        Object node = model.getChild(root, 0);
        Object child = model.getChild(node, 0);
        assertEquals(0, model.getIndexOfChild(node, child));
        child = model.getChild(node, 1);
        assertEquals(1, model.getIndexOfChild(node, child));
        assertEquals(-1, model.getIndexOfChild(root, child));
    }

    @Ignore("this model represents immutable tree, listeners are not used")
    @Test public void testAddListener() {
        model.addTreeModelListener(null);
    }

    @Ignore("this model represents immutable tree, listeners are not used")
    @Test public void testRemoveListener() {
        model.removeTreeModelListener(null);
    }

}
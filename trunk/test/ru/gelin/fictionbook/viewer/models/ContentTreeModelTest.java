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
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBException;

public class ContentTreeModelTest {

    protected FBDocument document;
    protected ContentTreeModel model;

    @Before public void setUp() throws FBException {
        document = new FBDocument(new File("docs/test2.1.fb2"));
        model = new ContentTreeModel(document);
    }

    @Test public void testGetRoot() {
        //"Тестовый ознакомительный документ FictionBook 2.1"
        assertEquals("\u0422\u0435\u0441\u0442\u043e\u0432\u044b\u0439 " +
            "\u043e\u0437\u043d\u0430\u043a\u043e\u043c\u0438\u0442\u0435\u043b\u044c\u043d\u044b\u0439 " +
            "\u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442 FictionBook 2.1",
            model.getRoot().toString());
    }

    @Test public void testGetChildOfRoot() {
    }

    @Test public void testGetChildOfNode() {
    }

    @Test public void testGetChildCountOfRoot() {
    }

    @Test public void testGetChildCountOfNode() {
    }

    @Test public void testIsLeaf() {
    }

    @Ignore("this model represents immutable tree, listeners are not used")
    @Test public void testPathChanged() {
    }

    @Test public void testGetIndexOfChildOfRoot() {
    }

    @Test public void testGetIndexOfChildOfNode() {
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
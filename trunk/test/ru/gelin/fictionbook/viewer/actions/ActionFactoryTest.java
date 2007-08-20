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

package ru.gelin.fictionbook.viewer.actions;

import org.junit.*;
import static org.junit.Assert.*;

import javax.swing.AbstractAction;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBDocumentHolder;

public class ActionFactoryTest {

    protected FBDocumentHolder documentHolder = new DocumentHolder();

    @Before public void setUp() {
    }

    @Test public void testGetInstance() {
        assertEquals(0, ActionFactory.factories.size());
        ActionFactory factory = ActionFactory.getInstance(documentHolder);
        assertEquals(1, ActionFactory.factories.size());
        ActionFactory factory2 = ActionFactory.getInstance(documentHolder);
        assertSame(factory, factory2);
    }

    @Test public void testGetExitAction() {
        ActionFactory factory = ActionFactory.getInstance(documentHolder);
        AbstractAction action = factory.getAction(ActionFactory.Type.EXIT);
        assertEquals("ru.gelin.fictionbook.viewer.actions.ExitAction",
            action.getClass().getName());
        AbstractAction action2 = factory.getAction(ActionFactory.Type.EXIT);
        assertSame(action, action2);
    }

    @Test public void testGetOpenAction() {
        ActionFactory factory = ActionFactory.getInstance(documentHolder);
        OpenAction action = (OpenAction)factory.getAction(ActionFactory.Type.OPEN);
        assertEquals("ru.gelin.fictionbook.viewer.actions.OpenAction",
            action.getClass().getName());
        assertSame(documentHolder, action.documentHolder);
        AbstractAction action2 = factory.getAction(ActionFactory.Type.OPEN);
        assertSame(action, action2);
    }

    @Test public void testGetActionDiff() {
        ActionFactory factory = ActionFactory.getInstance(documentHolder);
        AbstractAction action = factory.getAction(ActionFactory.Type.OPEN);
        AbstractAction action2 = factory.getAction(ActionFactory.Type.EXIT);
        assertNotSame(action, action2);
    }

    protected class DocumentHolder implements FBDocumentHolder {
        public FBDocument document;
        public void setFBDocument(FBDocument document) {
            this.document = document;
        }
    }

}
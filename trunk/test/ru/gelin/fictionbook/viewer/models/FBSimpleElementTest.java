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

public class FBSimpleElementTest {

    protected FBDocument fb;
    protected FBSimpleDocument document;

    @Before public void setUp() throws FBException {
        fb = new FBDocument(new File("docs/test2.1.fb2"));
    }

    @Ignore("not implemented yet")
    @Test public void testGetDocument() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetParentElement() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetName() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetAttributes() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetStartOffset() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetEndOffset() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetElementIndex() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetElementCount() {
    }

    @Ignore("not implemented yet")
    @Test public void testGetElement() {
    }

    @Ignore("not implemented yet")
    @Test public void testIsLeaf() {
    }

}
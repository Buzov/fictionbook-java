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

package ru.gelin.fictionbook.common;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import org.dom4j.Document;
import org.dom4j.Element;

public class FBDocumentTest {

    protected File file = new File("docs/test2.1.fb2");
    protected File nonExistedFile = new File("somethere/non-existed");

    @Before public void setUp() {
    }

    @Test public void testConstructorFromFile() throws FBException {
        FBDocument doc = new FBDocument(file);
        Document dom = doc.getDocument();
        assertNotNull(dom);
        Element root = dom.getRootElement();
        assertEquals("FictionBook", root.getName());
        assertEquals("http://www.gribuser.ru/xml/fictionbook/2.0", root.getNamespaceURI());
    }

    @Test(expected = FBException.class)
    public void testConstructorFileNotExists() throws FBException {
        FBDocument doc = new FBDocument(nonExistedFile);
    }

}
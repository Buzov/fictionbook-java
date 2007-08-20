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
import javax.swing.filechooser.FileFilter;

public class FBFileFilterTest {

    FileFilter filter;

    @Before public void setUp() {
        filter = new FBFileFilter();
    }

    @Test public void testAcceptDirectory() {
        File file = new File("docs");
        assertTrue(filter.accept(file));
    }

    @Test public void testAcceptFB() {
        File file = new File("docs/test2.1.fb2");
        assertTrue(filter.accept(file));
    }

    @Test public void testAcceptFBZip() {
        File file = new File("docs/test2.1.fb2.zip");
        assertTrue(filter.accept(file));
    }

}
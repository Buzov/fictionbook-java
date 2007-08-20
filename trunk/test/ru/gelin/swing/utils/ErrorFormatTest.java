/*
 *  Swing Utils.
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

package ru.gelin.swing.utils;

import org.junit.*;
import static org.junit.Assert.*;

public class ErrorFormatTest {

    @Before public void setUp() {
    }

    @Test public void testFormat() {
        String text = "Error on line 1 of document file:///home/gelin/work/java/fictionbook/LICENSE.txt : Content is not allowed in prolog. Nested exception: Content is not allowed in prolog.";
        assertEquals("Error on line 1 of document file:///home/gelin/work/java/fictionbook/LICENSE.txt :\nContent is not allowed in prolog.\nNested exception:\nContent is not allowed in prolog.",
            ErrorFormat.format(text));
    }

}
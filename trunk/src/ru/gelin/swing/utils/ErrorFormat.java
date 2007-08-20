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

import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Formats error message inserting new line characters.
 */
public class ErrorFormat {

    /** commons logging instance */
    protected static Log log = LogFactory.getLog(ErrorFormat.class);

    protected static Pattern nlPattern = Pattern.compile("(:|\\.)\\s+");

    public static String format(String message) {
        String result = nlPattern.matcher(message).replaceAll("$1\n");
        log.debug(result);
        return result;
    }

}
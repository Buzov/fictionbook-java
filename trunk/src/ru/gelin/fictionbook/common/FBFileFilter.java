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

import java.io.File;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileFilter;
import ru.gelin.swing.utils.Messages;

/**
 *  Filters Fiction Book files for Swing FileChooser.
 */
public class FBFileFilter extends FileFilter {

    static Pattern FBPattern = Pattern.compile(".*\\.fb2(\\.[^\\.]+)?");

    /** localized messages instance */
    static Messages msg =
        Messages.getInstance("ru/gelin/fictionbook/common/resources/messages");

    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return FBPattern.matcher(file.getName()).matches();
    }

    public String getDescription() {
        return msg.get("filefilter.description");
    }

}
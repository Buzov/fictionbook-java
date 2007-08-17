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

package ru.gelin.fictionbook.viewer;

import javax.swing.JFrame;
import ru.gelin.fictionbook.Version;
//import ru.gelin.fictionbook.viewer.ui.ViewerFrame;

/**
 *  This class runs Fiction Book Viewer as stanalone application.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Version.getCopyrightNotice("Fiction Book Viewer"));
        //TODO open file from command line
        //JFrame frame = new ViewerFrame();
        //frame.setContentPane(new ViewerPane());
        //frame.pack();
        //frame.show();
    }

}
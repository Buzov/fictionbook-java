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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *  Action which is performed when exiting from Viewer.
 */
public class ExitAction extends AbstractAction {

    /**
     *  This method is called when it's need to exit from Viewer.
     */
    public void actionPerformed(ActionEvent aoEvent) {
        //TODO save all what requires saving
        //TODO maybe it's not required to exit here
        System.exit(0);
    }



}

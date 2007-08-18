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
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import ru.gelin.swing.utils.Messages;

/**
 *  Action which is performed for opening new document in Viewer.
 */
public class OpenAction extends AbstractAction {

    /** localized messages instance */
    Messages msg = Messages.getInstance("ru/gelin/fictionbook/viewer/resources/messages");

    public OpenAction() {
        super();
        putValue(NAME, msg.get("menu.file.open"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
        putValue(SHORT_DESCRIPTION, msg.get("menu.file.open.tooltip"));
    }

    /**
     *  This method is called when it's need to open new document.
     */
    public void actionPerformed(ActionEvent aoEvent) {
        //TODO save all what requires saving
        //TODO maybe it's not required to exit here
        //System.exit(0);
    }



}

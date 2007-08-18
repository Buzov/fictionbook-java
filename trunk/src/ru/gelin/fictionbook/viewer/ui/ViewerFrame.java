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

package ru.gelin.fictionbook.viewer.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import ru.gelin.swing.utils.Messages;
import ru.gelin.fictionbook.viewer.actions.ActionFactory;

/**
 *  Main window if Fiction Book Viewer.
 */
public class ViewerFrame extends JFrame {

    /** localized messages instance */
    Messages msg = Messages.getInstance("ru/gelin/fictionbook/viewer/resources/messages");

    public ViewerFrame() {
        super();
        setTitle(msg.get("frame.title"));
        makeMenu();
        addWindowListener(new ViewerWindowListener());
    }

    protected void makeMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu(msg.get("menu.file"));
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem(msg.get("menu.file.open"));
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(msg.get("menu.file.exit"));
        menu.add(menuItem);
    }

    protected class ViewerWindowListener extends WindowAdapter {
        /**
         *  Saves some options and exits the application when window is closed.
         */
        public void windowClosing(WindowEvent e) {
            ActionFactory factory = ActionFactory.getInstance();
            factory.getAction(ActionFactory.Type.EXIT).actionPerformed(null);
        }
    }

}
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

package ru.gelin.fictionbook.reader.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import ru.gelin.swing.utils.Messages;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBDocumentHolder;
import ru.gelin.fictionbook.reader.actions.ActionFactory;

/**
 *  Main window if Fiction Book Reader.
 */
public class ReaderFrame extends JFrame implements FBDocumentHolder {

    /** localized messages instance */
    Messages msg = Messages.getInstance("ru/gelin/fictionbook/reader/resources/messages");

    /** actions */
    ActionFactory actions = ActionFactory.getInstance(this);

    public ReaderFrame() {
        super();
        setTitle(msg.get("frame.title"));
        makeMenu();
        setContentPane(new ReaderPane());
        addWindowListener(new ReaderWindowListener());
    }

    /**
     *  When Fiction Book document is set, title of a frame is changed and
     *  document is conveyed to ReaderPane.
     */
    public void setFBDocument(FBDocument document) {
        if (document != null) {
            setTitle(msg.get("frame.title.document", new String[] { document.getBookTitle() }));
            ((ReaderPane)getContentPane()).setFBDocument(document);
        }
    }

    void makeMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu(msg.get("menu.file"));
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem(actions.getAction(ActionFactory.Type.OPEN));
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem(actions.getAction(ActionFactory.Type.EXIT));
        menu.add(menuItem);
    }

    class ReaderWindowListener extends WindowAdapter {
        /**
         *  Saves some options and exits the application when window is closed.
         */
        public void windowClosing(WindowEvent e) {
            actions.getAction(ActionFactory.Type.EXIT).actionPerformed(null);
        }
    }

}
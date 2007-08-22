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

import java.awt.Dimension;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JTextPane;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBDocumentHolder;
import ru.gelin.fictionbook.viewer.models.ContentTreeModel;

/**
 *  Panel with content and view of Fiction Book.
 */
public class ViewerPane extends JSplitPane implements FBDocumentHolder {

    protected JTree tree;

    public ViewerPane() {
        super(JSplitPane.HORIZONTAL_SPLIT);
        tree = new JTree(new Object[] {  });    //empty tree
        tree.setEditable(false);    //read only
        tree.setMinimumSize(new Dimension(50, 100));
        add(tree);
        JTextPane text = new JTextPane();
        text.setMinimumSize(new Dimension(100, 100));
        add(text);
    }

    public void setFBDocument(FBDocument document) {
        if (document != null) {
            tree.setModel(new ContentTreeModel(document));
            tree.setRootVisible(true);
        }
    }

}
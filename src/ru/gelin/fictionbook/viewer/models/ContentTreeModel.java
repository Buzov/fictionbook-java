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

package ru.gelin.fictionbook.viewer.models;

import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;
import ru.gelin.fictionbook.common.FBDocument;

/**
 *  Tree model for Fiction Book content.
 */
public class ContentTreeModel {

    protected FBDocument document;
    protected ContentTreeRoot root;

    public ContentTreeModel(FBDocument document) {
        this.document = document;
    }

    /**
     *  Creates and returns {@link ContentTreeRoot}.
     */
    public Object getRoot() {
        if (root == null) {
            root = new ContentTreeRoot(document);
        }
        return root;
    }

    /**
     *  Child nodes in this model are presented by {@link ContentTreeNode}
     *  object.
     */
    public Object getChild(Object parent, int index) {
        //TODO implement it
        return null;
    }

    public int getChildCount(Object parent) {
        //TODO implement it
        return 0;
    }

    public boolean isLeaf(Object node) {
        //TODO implement it
        return true;
    }

    /**
     *  This model is read-only, implementation of this method is empty.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        //TODO implement it
        return 0;
    }

    /**
     *  This model is read-only, implementation of this method is empty.
     */
    public void addTreeModelListener(TreeModelListener l) {
    }

    /**
     *  This model is read-only, implementation of this method is empty.
     */
    public void removeTreeModelListener(TreeModelListener l) {
    }

}
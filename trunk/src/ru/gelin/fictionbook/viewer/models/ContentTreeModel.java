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

import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;
import org.dom4j.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.gelin.fictionbook.common.FBDocument;

/**
 *  Tree model for Fiction Book content.
 */
public class ContentTreeModel implements TreeModel {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

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
        Object result = null;
        if (parent == root) {
            Node node = document.getDocument();
            log.debug(node.getPath());
            //select sections from first body
            List nodes = document.createXPath("//fb:body[1]/fb:section").
                selectNodes(node);
            result = new ContentTreeNode(document, (Node)nodes.get(index));
        } else {
            Node node = ((ContentTreeNode)parent).getNode();
            log.debug(node.getPath());
            //select nested sections
            List nodes = document.createXPath("fb:section").selectNodes(node);
            result = new ContentTreeNode(document, (Node)nodes.get(index));
        }
        return result;
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
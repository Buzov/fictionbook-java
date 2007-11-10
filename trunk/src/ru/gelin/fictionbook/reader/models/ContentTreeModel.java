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

package ru.gelin.fictionbook.reader.models;

import java.util.List;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeModelListener;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.gelin.fictionbook.common.FBDocument;

/**
 *  Tree model for Fiction Book content.
 */
public class ContentTreeModel implements TreeModel {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    FBDocument document;
    ContentTreeRoot root;
    XPath bodyXPath;
    XPath sectionXPath;

    public ContentTreeModel(FBDocument document) {
        this.document = document;
        this.bodyXPath = document.createXPath("//fb:body");
        this.sectionXPath = document.createXPath("fb:section");
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
            //select sections from first body
            List nodes = bodyXPath.selectNodes(node);
            result = new ContentTreeNode(document, (Node)nodes.get(index));
        } else {
            Node node = ((ContentTreeNode)parent).getNode();
            //select nested sections
            List nodes = sectionXPath.selectNodes(node);
            result = new ContentTreeNode(document, (Node)nodes.get(index));
        }
        return result;
    }

    public int getChildCount(Object parent) {
        int result = 0;
        if (parent == root) {
            Node node = document.getDocument();
            //select sections from first body
            List nodes = bodyXPath.selectNodes(node);
            result = nodes.size();
        } else {
            Node node = ((ContentTreeNode)parent).getNode();
            //select nested sections
            List nodes = sectionXPath.selectNodes(node);
            result = nodes.size();
        }
        return result;
    }

    public boolean isLeaf(Object node) {
        boolean result = true;
        if (node == root) {
            Node domNode = document.getDocument();
            //select sections from first body
            List nodes = bodyXPath.selectNodes(domNode);
            result = nodes.isEmpty();
        } else {
            Node domNode = ((ContentTreeNode)node).getNode();
            //select nested sections
            List nodes = sectionXPath.selectNodes(domNode);
            result = nodes.isEmpty();
        }
        return result;
    }

    /**
     *  This model is read-only, implementation of this method is empty.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    public int getIndexOfChild(Object parent, Object child) {
        int result = -1;
        if (parent != null && child != null) {
            Node node = null;
            List nodes = null;
            if (parent == root) {
                node = document.getDocument();
                //select sections from first body
                nodes = bodyXPath.selectNodes(node);
            } else {
                node = ((ContentTreeNode)parent).getNode();
                //select nested sections
                nodes = sectionXPath.selectNodes(node);
            }
            node = ((ContentTreeNode)child).getNode();
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i) == node) {
                    result = i;
                    break;
                }
            }
        }
        return result;
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
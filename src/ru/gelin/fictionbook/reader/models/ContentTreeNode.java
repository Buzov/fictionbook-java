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

import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.XPath;
import ru.gelin.fictionbook.common.FBDocument;

/**
 *  This class represents node of tree of Fiction Book content.
 */
public class ContentTreeNode {

    FBDocument document;
    Node node;
    XPath titleXPath;

    public ContentTreeNode(FBDocument document, Node node) {
        this.document = document;
        this.node = node;
        this.titleXPath = document.createXPath("fb:title");
    }

    public Node getNode() {
        return node;
    }

    /**
     *  Returns title of a Fiction Book section for which this node is
     *  created.
     *  Returns value of element &lt;title> nested to this section.
     *  If &lt;title> is empty, value of @id or @name attribute is returned,
     *  enclosed to square brackets (for example "[notes]").
     *  If id is empty, name of this node is returned, enclosed
     *  to angle brackets (for example &lt;body>).
     */
    public String toString() {
        String result = titleXPath.valueOf(node).trim();
        if ("".equals(result)) {
            String id = ((Element)node).attributeValue("id");
            if (id == null) {
                id = ((Element)node).attributeValue("name");
            }
            if (id != null) {
                result = "[" + id + "]";
            } else {
                result = "<" + node.getName() + ">";
            }
        }
        return result;
    }

}
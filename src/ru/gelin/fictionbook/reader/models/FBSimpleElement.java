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
import javax.swing.text.Element;
import javax.swing.text.Document;
import javax.swing.text.AttributeSet;
import org.dom4j.Node;
//import org.dom4j.Element;

/**
 *  Very simple class implementing Element interface.
 */
public class FBSimpleElement implements Element {

    FBSimpleDocument document;
    Node node;
    int startOffset;
    int endOffset;
    AttributeSet attributeSet;

    /**
     *  Element creates for specified document and takes reference
     *  to DOM Node which represents part of Fiction Book.
     */
    public FBSimpleElement(Document document, Node node) {
        this.document = (FBSimpleDocument)document;
        this.node = node;
    }

    public Document getDocument() {
        return document;
    }

    /**
     *  Takes Node of this Element and tryes to find Element corresponding
     *  to parent Node by quering internal map of FBSimpleDocument.
     */
    public Element getParentElement() {
        return document.getElement(node.getParent());
    }

    /**
     *  Returns name of Node.
     */
    public String getName() {
        return node.getName();
    }

    public AttributeSet getAttributes() {
        if (attributeSet != null) {
            return attributeSet;
        }
        return document.defaultAttributeSet;
    }

    /**
     *  Sets AttributeSet (Style) for this element.
     */
    public void setAttributeSet(AttributeSet attributeSet) {
        this.attributeSet = attributeSet;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public int getElementIndex(int offset) {
        int result = -1;
        List children = ((org.dom4j.Element)node).elements();
        if (!children.isEmpty()) {
            result = 0; //has children, should return index >= 0
        }
        for (int i = 0; i < children.size(); i ++) {
            Element child = document.getElement((Node)children.get(i));
            int start = child.getStartOffset();
            if (offset >= start) {
                result = i;
            }
        }
        return result;
    }

    public int getElementCount() {
        return ((org.dom4j.Element)node).elements().size();
    }

    public Element getElement(int index) {
        List children = ((org.dom4j.Element)node).elements();
        return document.getElement((Node)children.get(index));
    }

    public boolean isLeaf() {
        List children = ((org.dom4j.Element)node).elements();
        return children.isEmpty();
    }

    /**
     *  Returns DOM Node for this Element.
     */
    public Node getNode() {
        return node;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("path: ");
        result.append(node != null ? node.getPath() : "null");
        result.append(", start: ");
        result.append(startOffset);
        result.append(", end: ");
        result.append(endOffset);
        return result.toString();
    }

}
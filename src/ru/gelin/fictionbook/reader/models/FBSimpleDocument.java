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

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.swing.text.Document;
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.Segment;
import javax.swing.text.Position;
import javax.swing.text.Element;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleContext;
import javax.swing.text.Style;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import org.dom4j.Node;
import org.dom4j.rule.Stylesheet;
import org.dom4j.rule.Rule;
import org.dom4j.rule.Pattern;
import org.dom4j.rule.Action;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.gelin.fictionbook.common.FBDocument;

/**
 *  Simple read-only styled document, which represents Fiction Book.
 */
public class FBSimpleDocument /*implements StyledDocument*/ implements Document {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    /** Fiction Book document (with DOM tree) */
    protected FBDocument fb;
    /** Document content */
    protected char[] content;
    /** Document properties */
    protected Map properties = new HashMap();
    /** DOM node to document Element map */
    protected Map<Node, Element> nodeToElement = new HashMap<Node, Element>();
    /** Document styles */
    protected StyleContext styles = new StyleContext();
    /** Default set of attributes */
    protected AttributeSet defaultAttributeSet = styles.getEmptySet();
    /** Default style */
    protected Style defaultStyle = addStyle("default", null);
    /** Array "maps" document position to closest Element */
    protected FBSimpleElement[] positionToElement;

    /** StringBuilder to build the content. Temporaly used in constructor. */
    private StringBuilder contentBuilder;
    /** Transformer to walk through DOM tree in constructor */
    private Stylesheet style;
    /** List to map document position to Elements. Temporaly used in constructor */
    private List<FBSimpleElement> positionToElementBuilder;

    public FBSimpleDocument(FBDocument fb) {
        super();
        this.fb = fb;
        putProperty(Document.TitleProperty, fb.getBookTitle());
        traverseDocument();
    }

    public int getLength() {
        return content.length;
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void addDocumentListener(DocumentListener listener) {
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void removeDocumentListener(DocumentListener listener) {
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void addUndoableEditListener(UndoableEditListener listener) {
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void removeUndoableEditListener(UndoableEditListener listener) {
    }

    public Object getProperty(Object key) {
        return properties.get(key);
    }

    public void putProperty(Object key, Object value) {
        properties.put(key, value);
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void remove(int offs, int len) throws BadLocationException {
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void insertString(int offset, String str, AttributeSet a)
        throws BadLocationException {
    }

    public String getText(int offset, int length) throws BadLocationException {
        try {
            return new String(content, offset, length);
        } catch (IndexOutOfBoundsException e) {
            throw new BadLocationException(e.getMessage(),
                //try to guess erroneous offset
                offset < 0 || offset >= getLength() ? offset : offset + length);
        }
    }

    public void getText(int offset, int length, Segment txt)
        throws BadLocationException {
        if (offset < 0) {
            throw new BadLocationException("invalid offset", offset);
        } else if (offset + length > content.length) {
            throw new BadLocationException("invalid offset", offset + length);
        } else {
            txt.array = content;
            txt.offset = offset;
            txt.count = length;
        }
    }

    public Position getStartPosition() {
        return new FBSimplePosition(0);
    }

    public Position getEndPosition() {
        return new FBSimplePosition(getLength());
    }

    /**
     *  Offset of created position is constant value because this
     *  document is unmodifiable.
     */
    public Position createPosition(int offs) throws BadLocationException {
        if (offs < 0 || offs > getLength()) {
            throw new BadLocationException("invalid offset to create position" ,offs);
        }
        return new FBSimplePosition(offs);
    }

    public Element[] getRootElements() {
        Node node = fb.getDocument().getRootElement();
        Element[] result = new Element[1];
        result[0] = getElement(node);
        return result;
    }

    public Element getDefaultRootElement() {
        Node node = fb.getDocument().getRootElement();
        return getElement(node);
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void render(Runnable r) {
    }

    public Style addStyle(String nm, Style parent) {
        return styles.addStyle(nm, parent);
    }

    public void removeStyle(String nm) {
        styles.removeStyle(nm);
    }

    public Style getStyle(String nm) {
        return styles.getStyle(nm);
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void setCharacterAttributes(int offset,
                            int length,
                            AttributeSet s,
                            boolean replace) {
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void setParagraphAttributes(int offset,
                            int length,
                            AttributeSet s,
                            boolean replace) {
    }

    /**
     *  This implementation is void, read-only document.
     */
    public void setLogicalStyle(int pos, Style s) {
    }

    public Style getLogicalStyle(int p) {
        return defaultStyle;
        //TODO: add more smart styles hierarchy
    }

    public Element getParagraphElement(int pos) {
        FBSimpleElement element = positionToElement[pos];
        Node node = element.getNode();
        while (fb.isInline(node)) { //find first not inline parent
            node = node.getParent();
        }
        return getElement(node);
    }

    /**
     *  Traverses all DOM tree of Fiction Book document
     *  and fill some internal fields.
     */
    protected void traverseDocument() {
        contentBuilder = new StringBuilder();
        positionToElementBuilder = new ArrayList<FBSimpleElement>();
        style = new Stylesheet();

        Rule textRule = new Rule(fb.createPattern("//fb:body//fb:p/text()"));
        textRule.setAction(
            new Action() {
                /**
                 *  Saves value of all text nodes to single String.
                 */
                public void run(Node node) {
                    //log.debug("visiting " + node.getPath());
                    contentBuilder.append(node.getText());
                }
            });
        style.addRule(textRule);
        style.addRule(new Rule(textRule, fb.createPattern("//fb:body//fb:p//text()")));
        style.addRule(new Rule(textRule, fb.createPattern("//fb:body//fb:v/text()")));
        style.addRule(new Rule(textRule, fb.createPattern("//fb:body//fb:v//text()")));
        style.addRule(new Rule(textRule, fb.createPattern("//fb:body//fb:td/text()")));
        style.addRule(new Rule(textRule, fb.createPattern("//fb:body//fb:td//text()")));
        style.addRule(new Rule(textRule, fb.createPattern("//fb:book-title/text()")));

        Rule elementRule = new Rule(fb.createPattern("//node()"));
        elementRule.setAction(
            new Action() {
                /**
                 *  Creates Element for every Node of Fiction Book and
                 *  puts it to map.
                 */
                public void run(Node node) throws Exception {
                    //log.debug("visiting " + node.getPath());
                    FBSimpleElement element = new FBSimpleElement(
                        FBSimpleDocument.this, node);
                    element.startOffset = contentBuilder.length();
                    nodeToElement.put(node, element);
                    style.applyTemplates(node);
                    element.endOffset = contentBuilder.length();
                    //create position to element map
                    while (positionToElementBuilder.size() <
                            contentBuilder.length()) {
                        positionToElementBuilder.add(element);
                    }
                }
            });
        style.addRule(elementRule);
        style.addRule(new Rule(elementRule, fb.createPattern("/")));
        style.addRule(new Rule(elementRule, fb.createPattern("/node()")));

        try {
            style.run(fb.getDocument());
        } catch (Exception e) {
            log.warn("can't traverse document", e);
        }

        content = new char[contentBuilder.length()];
        contentBuilder.getChars(0, contentBuilder.length(), content, 0);
        //log.debug(content);
        positionToElement = new FBSimpleElement[positionToElementBuilder.size()];
        positionToElement = positionToElementBuilder.toArray(positionToElement);
        style = null;
        contentBuilder = null;
        positionToElementBuilder = null;
    }

    protected Element getElement(Node node) {
        return nodeToElement.get(node);
    }

    /*
    protected class FBVisitor extends VisitorSupport {

        public StringBuilder contentBuilder = new StringBuilder();
        protected XPath bodyXPath = fb.createXPath("//fb:body//*");
        protected XPath textXPath = fb.createXPath("//fb:p//*|//fb:v//*|//fb:td//*");

        public void visit(Element node) {
            //log.debug("visiting " + node.getPath());
        }

        public void visit(Text node) {
            if (bodyXPath.matches(node) && bodyXPath.matches(node)) {
                //only formatted text inside body
                log.debug("visiting " + node.getPath());
                contentBuilder.append(node.getText());
            }
        }

    }
    */

}
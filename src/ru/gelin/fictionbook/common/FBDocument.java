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

package ru.gelin.fictionbook.common;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.GZIPInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.XPath;
import org.dom4j.Node;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.rule.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Document which represents Fiction Book.
 */
public class FBDocument {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    /** dom4j document instance */
    Document dom;

    /** document factory which is used to create dom4j document */
    DocumentFactory factory;

    XPath bookTitleXPath;

    /** XPath expression to detect inline elements */
    XPath inlinesXPath;

    public static final Map NS_URIS;
    static {
        Map NSUris = new HashMap();
        NSUris.put("fb", "http://www.gribuser.ru/xml/fictionbook/2.0");
        NSUris.put("fbg", "http://www.gribuser.ru/xml/fictionbook/2.0/genres");
        NSUris.put("l", "http://www.w3.org/1999/xlink");
        NS_URIS = Collections.unmodifiableMap(NSUris);
    }



    /**
     *  Constructor from file.
     *  @param  file    file with Fiction Book document
     *  @throws FBException if any error occured while reading document,
     *                      cause of error is wrapped.
     */
    public FBDocument(File file) throws FBException {
        factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(NS_URIS); //for correct working of XPath for elements
        if (log.isInfoEnabled()) {
            log.info("creating document from file " + file);
        }
        try {
            if (file.getName().endsWith(".zip")) {
                dom = readZip(file);
            } else if (file.getName().endsWith(".gz")) {
                dom =  readGZip(file);
            }
            if (dom == null) {
                dom = read(file);
            }
            prepareXPaths();
        } catch (Exception e) {
            String err = "can't create document from file " + file;
            log.error(err, e);
            throw new FBException(err, e);
        }
    }

    /**
     *  Returns internal dom4j document.
     */
    public Document getDocument() {
        return dom;
    }

    /**
     *  Creates new XPath expression with namespace prafixes set to
     *  canonical values.
     *  These prefixes should be used in created expression:<br/>
     *  fb - http://www.gribuser.ru/xml/fictionbook/2.0<br/>
     *  fbg - http://www.gribuser.ru/xml/fictionbook/2.0/genres<br/>
     *  l - http://www.w3.org/1999/xlink<br/>
     */
    public XPath createXPath(String xpathExpression) {
        XPath result = factory.createXPath(xpathExpression);
        //result.setNamespaceURIs(NS_URIS); //defined in factory
        return result;
    }

    /**
     *  Creates new XPath pattern for rule processing.
     */
    public Pattern createPattern(String xpathPattern) {
        return factory.createPattern(xpathPattern);
    }

    /**
     *  Returns value of /FictionBook/description/title-info/book-title
     *  element.
     */
    public String getBookTitle() {
        return bookTitleXPath.valueOf(dom);
    }

    /**
     *  Returns <code>true</code> if specified DOM Node is "inline" node
     *  (i.e. not forms new paragraph).
     */
    public boolean isInline(Node node) {
        return inlinesXPath.matches(node);
    }

    void prepareXPaths() {
        bookTitleXPath =
            createXPath("/fb:FictionBook/fb:description/fb:title-info/fb:book-title");
        inlinesXPath = createXPath(
            "//fb:empty-line|" +
            "//fb:strong|//fb:emphasis|" +
            "//fb:sub|//fb:sup|" +
            "//fb:strikethrough|" +
            "//fb:code|" +
            "//fb:a");
    }

    SAXReader getSAXReader() {
        return new SAXReader(factory);
    }

    /**
     *  Read Fiction Book from zip archive.
     */
    Document readZip(File file) throws IOException, DocumentException {
        Document result = null;
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String name = entry.getName();
            if (file.getName().startsWith(name) ||
                name.endsWith(".fb2")) {
                if (result != null) {
                    log.warn("Zip file " + file +
                        " contains multiple Fiction Book documents");
                    break;
                }
                if (log.isInfoEnabled()) {
                    log.info("unzipping " + name);
                }
                SAXReader xmlReader = getSAXReader();
                result = xmlReader.read(zipFile.getInputStream(entry));
            }
        }
        return result;
    }

    /**
     *  Read GZipped Fiction Book.
     */
    Document readGZip(File file) throws IOException, DocumentException {
        if (log.isInfoEnabled()) {
            log.info("ungzipping " + file);
        }
        SAXReader xmlReader = getSAXReader();
        return xmlReader.read(new GZIPInputStream(new FileInputStream(file)));
    }

    /**
     *  Read not compressed Fiction Book.
     */
    Document read(File file) throws IOException, DocumentException {
        SAXReader xmlReader = getSAXReader();
        return xmlReader.read(file);
    }

}
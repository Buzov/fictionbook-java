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
import java.io.File;
import org.dom4j.Document;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Document which represents Fiction Book.
 */
public class FBDocument {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    /** dom4j document instance */
    protected Document dom;

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
        if (log.isInfoEnabled()) {
            log.info("creating document from file " + file);
        }
        try {
            //TODO implement reading from ZIP archives
            SAXReader xmlReader = new SAXReader();
            dom = xmlReader.read(file);
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
        XPath result = dom.createXPath(xpathExpression);
        result.setNamespaceURIs(NS_URIS);
        return result;
    }

    /**
     *  Returns value of /FictionBook/description/title-info/book-title
     *  element.
     */
    public String getBookTitle() {
        //log.debug(dom.selectSingleNode("/FictionBook").getPath());
        //log.debug(dom.selectSingleNode("/*[name()='FictionBook']/*[name()='description']").getPath());
        return createXPath("/fb:FictionBook/fb:description/fb:title-info/fb:book-title").
            valueOf(dom);
    }

}
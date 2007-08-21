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

import java.io.File;
import org.dom4j.Document;
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
     *  Returns value of /FictionBook/description/title-info/book-title
     *  element.
     */
    public String getBookTitle() {
        //log.debug(dom.selectSingleNode("/FictionBook").getPath());
        //log.debug(dom.selectSingleNode("/*[name()='FictionBook']/*[name()='description']").getPath());
        return dom.valueOf("/*[name()='FictionBook']" +
            "/*[name()='description']" +
            "/*[name()='title-info']" +
            "/*[name()='book-title']");
    }

}
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

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.text.StyleContext;
import javax.swing.text.Style;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Reads configuration file and initializes internal StyleContext with
 *  styles from config. Applies styles to Element.
 *  Configuration properties are searched in ../resources/styles.properties
 */
public class FBSimpleStyler {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    protected StyleContext styles = new StyleContext();

    /** number of current reading line in the file */
    protected int line;

    protected Map<String, Style> xpathToStyle =
        new LinkedHashMap<String, Style>();   //to preserve order

    protected static final String STYLES_PROPERTIES =
        "/ru/gelin/fictionbook/reader/resources/styles.properties";
    protected static final Pattern LINE_PATTERN = Pattern.compile(
        "([\\w\\.]+)\\.(\\w+?)\\s*=\\s*(.+)\\s*");  //style.property = value

    /**
     *  Creates Styler. Reads configuration.
     */
    public FBSimpleStyler() {
        loadConfiguration();
    }

    /**
     *  Returns StyleContext filled with styles read from configuration.
     */
    public StyleContext getStyleContext() {
        return styles;
    }

    /**
     *  Loads configuration .properties file.
     */
    protected void loadConfiguration() {
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    FBSimpleStyler.class.getResourceAsStream(STYLES_PROPERTIES),
                    "ISO-8859-1"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] spv = parseLine(line); //[style, property, value]
                if (spv != null) {
                    String style = spv[0];
                    String property = spv[1];
                    String value = spv[2];
                    if (styles.getStyle(style) == null) {   //no such style
                        if (log.isDebugEnabled()) {
                            log.debug("adding style " + style);
                        }
                        Style parent = null;
                        if ("parent".equals(property)) {
                            parent = styles.getStyle(value);
                            if (parent == null) {
                                log.warn("line " + line + ": parent style " +
                                    value + " is not defined before");
                            }
                        }
                        styles.addStyle(style, parent);
                    }
                    processProperty(style, property, value);
                }
            }
        } catch (Exception e) { //it's fatal
            throw new RuntimeException(e);
        }
    }

    /**
     *  Parses line of the configuration file. Returns <code>null</code> if
     *  line is comment or not in key = value format.
     *  @return array of three elements: style name, property name and value.
     */
    protected String[] parseLine(String line) {
        String[] result = null;
        Matcher matcher = LINE_PATTERN.matcher(line);
        if (matcher.matches()) {
            result = new String[3];
            result[0] = matcher.group(1);
            result[1] = matcher.group(2);
            result[2] = matcher.group(3);
        }
        return result;
    }

    protected void processProperty(String style, String property,
            String value) {
        if ("xpath".equals(property)) {
            addXPath(style, value);
        }
    }

    protected void addXPath(String style, String xpath) {
        xpathToStyle.put(xpath, styles.getStyle(style));
    }

}
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
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.text.StyleContext;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import org.dom4j.Node;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Reads configuration file and initializes internal StyleContext with
 *  styles from config. Applies styles to Element.
 *  Configuration properties are searched in ../resources/styles.properties
 */
public class FBSimpleStyler {

    public static final Object ViewAttribute = "view";

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    StyleContext styles = new StyleContext();

    Map<String, Style> patternToStyle =
        new HashMap<String, Style>();
    List<String> patternList = new ArrayList<String>();

    static final String STYLES_PROPERTIES =
        "/ru/gelin/fictionbook/reader/resources/styles.properties";
    static final Pattern LINE_PATTERN = Pattern.compile(
        "([\\w\\._\\-]+)\\.([\\w_\\-]+?)\\s*=\\s*(.+)\\s*");  //style.property = value

    /**
     *  Creates Styler. Reads configuration.
     */
    public FBSimpleStyler() {
        new Configuration().load();
        Collections.reverse(patternList);
    }

    /**
     *  Returns StyleContext filled with styles read from configuration.
     */
    public StyleContext getStyleContext() {
        return styles;
    }

    /**
     *  Sets style for the Element. Uses XPath expressions ("pattern" style
     *  property) and XML Node corresponding to the Element.
     */
    public void applyStyle(FBSimpleElement element) {
        Node node = element.getNode();
        for (String pattern : patternList) {
            if (node.matches(pattern)) {
                element.setAttributeSet(patternToStyle.get(pattern));
                if (log.isDebugEnabled()) {
                    log.debug(node.getPath() + " <- " +
                        patternToStyle.get(pattern).getName());
                }
                break;
            }
        }
    }

    class Configuration {

        /** number of current reading line in the file */
        int lineNumber;

        /**
         *  Loads configuration .properties file.
         */
        public void load() {
            try {
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                        FBSimpleStyler.class.getResourceAsStream(STYLES_PROPERTIES),
                        "ISO-8859-1"));
                String line;
                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    String[] spv = parseLine(line); //[style, property, value]
                    if (spv != null) {
                        String style = spv[0];
                        String property = spv[1];
                        String value = spv[2];
                        if (styles.getStyle(style) == null) {   //no such style
                            if (log.isDebugEnabled()) {
                                log.debug("adding style '" + style + "'");
                            }
                            Style parent = null;
                            if ("parent".equals(property)) {
                                parent = styles.getStyle(value);
                                if (parent == null) {
                                    log.warn("line " + lineNumber + ": parent style '" +
                                        value + "' is not defined before");
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
        String[] parseLine(String line) {
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

        void processProperty(String style, String property,
                String value) {
            if ("parent".equals(property)) {
                //nothing to do, already used in style creation
            } else if ("pattern".equals(property)) {
                setPattern(style, value);
            } else if ("view".equals(property)) {
                setView(style, value);
            } else if ("alignment".equals(property)) {
                setAlignment(style, value);
            } else if ("font-size".equals(property)) {
                setFontSize(style, value);
            } else if ("font-family".equals(property)) {
                setFontFamily(style, value);
            } else if ("bold".equals(property)) {
                setBold(style, value);
            } else if ("italic".equals(property)) {
                setItalic(style, value);
            } else if ("space-above".equals(property)) {
                setSpaceAbove(style, value);
            } else if ("space-below".equals(property)) {
                setSpaceBelow(style, value);
            } else {
                log.warn("line " + lineNumber + ": unknown property '" + property + "'");
            }
        }

        void setPattern(String style, String pattern) {
            patternToStyle.put(pattern, styles.getStyle(style));
            patternList.add(pattern);
        }

        void setAlignment(String style, String align) {
            int alignment = -1;
            if ("left".equals(align)) {
                alignment = StyleConstants.ALIGN_LEFT;
            } else if ("right".equals(align)) {
                alignment = StyleConstants.ALIGN_RIGHT;
            } else if ("center".equals(align)) {
                alignment = StyleConstants.ALIGN_CENTER;
            } else if ("justified".equals(align)) {
                alignment = StyleConstants.ALIGN_JUSTIFIED;
            } else {
                log.warn("line " + lineNumber + ": unknown alignment '" + align + "'");
            }
            if (alignment != -1) {
                StyleConstants.setAlignment(styles.getStyle(style), alignment);
            }
        }

        void setFontSize(String style, String value) {
            try {
                int fontSize = Integer.parseInt(value);
                StyleConstants.setFontSize(styles.getStyle(style), fontSize);
            } catch (Exception e) {
                log.warn("line " + lineNumber + ": not an integer '" + value + "'");
            }
        }

        void setFontFamily(String style, String family) {
            StyleConstants.setFontFamily(styles.getStyle(style), family);
        }

        void setBold(String style, String bold) {
            StyleConstants.setBold(styles.getStyle(style),
                Boolean.parseBoolean(bold));
        }

        void setItalic(String style, String bold) {
            StyleConstants.setItalic(styles.getStyle(style),
                Boolean.parseBoolean(bold));
        }

        void setSpaceAbove(String style, String value) {
            try {
                float space = Float.parseFloat(value);
                StyleConstants.setSpaceAbove(styles.getStyle(style), space);
            } catch (Exception e) {
                log.warn("line " + lineNumber + ": not a float '" + value + "'");
            }
        }

        void setSpaceBelow(String style, String value) {
            try {
                float space = Float.parseFloat(value);
                StyleConstants.setSpaceBelow(styles.getStyle(style), space);
            } catch (Exception e) {
                log.warn("line " + lineNumber + ": not a float '" + value + "'");
            }
        }

        void setView(String style, String view) {
            styles.getStyle(style).addAttribute(
                ViewAttribute, view);
        }

    }

}
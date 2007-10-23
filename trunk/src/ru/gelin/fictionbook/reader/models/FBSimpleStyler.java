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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.swing.text.StyleContext;

/**
 *  Reads configuration file and initializes internal StyleContext with
 *  styles from config. Applies styles to Element.
 *  Configuration properties are searched in ../resources/styles.properties
 */
public class FBSimpleStyler {

    protected StyleContext styles;

    protected static final String STYLES_PROPERTIES =
        "../resources/styles.properties";
    protected static final Pattern LINE_PATTERN = Pattern.compile(
        "([\\w\\.]+)\\.(\\w+?)\\s*=\\s*(.+)\\s*");  //style.property = value

    /**
     *  Creates Styler. Reads configuration.
     */
    public FBSimpleStyler() {
        styles = new StyleContext();
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
                    //System.out.println(spv[0] + "." + spv[1]);     //style.property
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

}
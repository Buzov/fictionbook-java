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

import java.util.Properties;
import java.util.Enumeration;
import javax.swing.text.StyleContext;

/**
 *  Reads configuration file and initializes internal StyleContext with
 *  styles from config. Applies styles to Element.
 *  Configuration properties are searched in ../resources/styles.properties
 */
public class FBSimpleStyler {

    protected StyleContext styles;

    /**
     *  Returns StyleContext filled with styles read from configuration.
     */
    public StyleContext getStyleContext() {
        if (styles == null) {
            styles = new StyleContext();
            styles.addStyle("default", null);
            loadConfiguration();
        }
        return styles;
    }

    /**
     *  Loads configuration .properties file.
     */
    protected void loadConfiguration() {
        Properties props = new Properties();
        try {
        props.load(FBSimpleStyler.class.getResourceAsStream(
                "../resources/styles.properties"));
        } catch (Exception e) { //it's fatal
            throw new RuntimeException(e);
        }
        Enumeration keys = props.keys();

        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String name =
        }

    }

    /**
     *  Gets style name from property key name.
     */
    protected String getStyleName(String key) {
        int pos = key.lastIndexOf('.');
        if (pos >= 0) {
            return key.substring(0, pos);
        } else {
            return key;
        }
    }

    /**
     *  Gets property name from property key name.
     */
    protected String getPropertyName(String key) {
        int pos = key.lastIndexOf('.');
        if (pos >= 0) {
            return key.substring(pos + 1, key.length());
        } else {
            return "";
        }
    }

}
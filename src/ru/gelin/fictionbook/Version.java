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

package ru.gelin.fictionbook;

import java.util.Properties;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

/**
 *  Reads Tools version from properties and returns and prints it.
 */
public class Version {

    /** commons logging instance */
    //static Log log = LogFactory.getLog(Version.class);

    /** version value */
    static String version;

    /**
     *  Returns Fiction Book Tools version.
     */
    public static String getVersion() {
        if (version ==  null) {
            try {
            Properties props = new Properties();
            props.load(Version.class.getResourceAsStream("resources/version.properties"));
            version = props.getProperty("version");
            } catch (Exception e) {
                //log.warn("can't get version", e);
                System.err.println("can't get version: " + e);
                version = "<not defined>";
            }
        }
        return version;
    }

    /**
     *  Creates common copyright notice using specified programm component
     *  name.
     *  @param  name    component name, for example, "Fiction Book Tools"
     *  @return formatted copyright notice
     */
    public static String getCopyrightNotice(String name) {
        StringBuilder str = new StringBuilder();
        str.append(name);
        str.append(" v.");
        str.append(getVersion());
        str.append("  Copyright (C) 2007  Denis Nelubin aka Gelin\n");
        str.append(name);
        str.append(" comes with ABSOLUTELY NO WARRANTY\n");
        str.append("This is free software, and you are welcome to redistribute it\n");
        str.append("under certain conditions. For details see LICENSE.txt.\n");
        return str.toString();
    }

    /**
     *  Prints this Fiction Book Tools version to System.out and exits.
     */
    public static void main(String[] args) {
        System.out.println(getCopyrightNotice("Fiction Book Tools"));
    }

}
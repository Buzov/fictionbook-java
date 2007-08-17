/*
 *  Swing Utils.
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
 *  http://gelin.ru
 *  mailto:den@gelin.ru
 */

package ru.gelin.swing.utils;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  Produces UI messages.
 */
public class Messages {

    /** commons logging instance */
    protected static Log log = LogFactory.getLog(Messages.class);

    /** Produced instances map. Keys - locales, values - instances. */
    static protected Map<String, Messages> instances = new HashMap<String, Messages>();

    /** ResourceBundle for this instance */
    protected ResourceBundle bundle = null;

    /**
     *  Default protected constructor, empty.
     *  @see    #getInstance
     */
    protected Messages() {
        //we don't like default constructor
    }

    /**
     *  Protected constructor for internal use.
     *  @see    #getInstance
     */
    protected Messages(String baseName, Locale locale) {
        try {
            bundle = ResourceBundle.getBundle(baseName, locale);
        } catch (MissingResourceException e) {
            log.error("can't load resource bundle '" + baseName + "'");
        }
    }

    /**
     *  Creates new Messages instance for default locale.
     *  @param  baseName    base name of ResourceBundle
     *  @return Messages object for default locale
     */
    public static Messages getInstance(String baseName) {
        return getInstance(baseName, Locale.getDefault());
    }

    /**
     *  Creates new Messages instance for specified locale.
     *  @param  baseName    base name of ResourceBundle
     *  @param  locale    locale for creating Messages
     *  @return Messages object for specified locale
     */
    public static Messages getInstance(String baseName, Locale locale) {
        if (log.isDebugEnabled()) {
            log.debug("creating messages with base name '" + baseName +
                "' and locale " + locale);
        }
        String key = getKey(baseName, locale);
        Messages result = instances.get(locale);
        if (result == null) {
            result = new Messages(baseName, locale);
            instances.put(key, result);
        }
        return result;
    }

    /**
     *  Returns message by key. If message is not found, message key is
     *  returned.
     *  @param  msgKey   key of message to be selected
     *  @return found message string
     */
    public String get(String msgKey) {
        String result = msgKey;
        if (bundle == null) {
            log.warn("resource bundle is not loaded");
        } else {
            try {
                result = bundle.getString(msgKey);
            } catch (MissingResourceException e) {
                log.warn("message '" + msgKey + "' is not found");
            }
        }
        return result;
    }

    /**
     *  Returns formatted message by key. If message is not found, message name is
     *  returned. Message is formatted by values from second argument
     *  @param  msgKey   key of message to be selected
     *  @param  arguments values which will be inserted to "{0}", "{1}", etc. patterns
     *  @return found and formatted message string
     *  @see MessageFormat
     */
    public String get(String msgKey, Object[] arguments) {
        String result = get(msgKey);
        result = MessageFormat.format(result, arguments);
        return result;
    }

    /**
     *  Makes map key from baseName and locale.
     *  This key is used in internal map to returns the same instance
     *  of Messages for the same base name and locale.
     */
    protected static String getKey(String baseName, Locale locale) {
        return baseName + locale.toString();
    }

}


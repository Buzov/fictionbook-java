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

package ru.gelin.fictionbook.viewer.actions;

import java.util.Map;
import java.util.EnumMap;
import javax.swing.AbstractAction;

/**
 *  Creates action by name.
 */
public class ActionFactory {

    /** action types */
    public enum Type { OPEN, EXIT };

    /** map of actions */
    protected Map<Type, AbstractAction> actions =
        new EnumMap<Type, AbstractAction>(Type.class);

    /** factory instance */
    protected static ActionFactory factory;

    protected ActionFactory() {
        //not public default constructor
    }

    /**
     *  Returns ActionFactory instance.
     */
    public static ActionFactory getInstance() {
        if (factory == null) {
            factory = new ActionFactory();
        }
        return factory;
    }

    /**
     *  Returns Action with specified type
     */
    public AbstractAction getAction(Type type) {
        AbstractAction result = actions.get(type);
        if (result == null) {
            switch (type) {
            case EXIT:
                result = new ExitAction();
            break;
            }
            actions.put(type, result);
        }
        return result;
    }

}
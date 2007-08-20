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
import java.util.WeakHashMap;
import javax.swing.AbstractAction;
import ru.gelin.fictionbook.common.FBDocumentHolder;

/**
 *  Creates action by name.
 *  Action factory takes reference to FBDocumentHolder. Each created
 *  action takes this reference too. So action can create FBDocument
 *  and set it to FBDocumentHolder.
 */
public class ActionFactory {

    /** action types */
    public enum Type { OPEN, EXIT };

    /** factory instances */
    //WeakHashMap to avoid potential memory leaks (?)
    protected static Map<FBDocumentHolder, ActionFactory> factories =
        new WeakHashMap<FBDocumentHolder, ActionFactory>();

    /** map of actions */
    protected Map<Type, AbstractAction> actions =
        new EnumMap<Type, AbstractAction>(Type.class);

    /** document holder reference */
    protected FBDocumentHolder documentHolder;

    protected ActionFactory() {
        //not public default constructor
    }

    /**
     *  Creates factory for specified FBDocumentHolder.
     *  @param  documentHolder  document holder reference which will convey to
     *                          actions created by this factory
     */
    protected ActionFactory(FBDocumentHolder documentHolder) {
        this.documentHolder = documentHolder;
    }

    /**
     *  Returns ActionFactory instance.
     *  @param  documentHolder  document holder reference which will convey to
     *                          actions created by this factory
     */
    public static ActionFactory getInstance(FBDocumentHolder documentHolder) {
        ActionFactory result = factories.get(documentHolder);
        if (result == null) {
            result = new ActionFactory(documentHolder);
            factories.put(documentHolder, result);
        }
        return result;
    }

    /**
     *  Returns Action with specified type
     */
    public AbstractAction getAction(Type type) {
        AbstractAction result = actions.get(type);
        if (result == null) {
            switch (type) {
            case OPEN:
                result = new OpenAction(documentHolder);
                break;
            case EXIT:
                result = new ExitAction();
                break;
            }
            actions.put(type, result);
        }
        return result;
    }

}
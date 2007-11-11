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

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.StyleConstants;
import javax.swing.text.ParagraphView;
import javax.swing.text.LabelView;
import javax.swing.text.BoxView;

/**
 *  Extends StyledEditorKit to support read-only Fiction Book document.
 *  Only overrides getViewFactory method.
 */
public class FBSimpleEditorKit extends StyledEditorKit {

    public ViewFactory getViewFactory() {
        return new FBViewFactory();
    }

    /**
     *  View factory. Class of the view depends on
     *  {@link StyleConstants.NameAttribute} of the attribute set of
     *  the element.
     */
    class FBViewFactory implements ViewFactory {

        public View create(Element elem) {
            String view = String.valueOf(
                elem.getAttributes().getAttribute(FBSimpleStyler.ViewAttribute));
            if ("box".equals(view)) {
                return new BoxView(elem, View.Y_AXIS);
            } else if ("paragraph".equals(view)) {
                return new ParagraphView(elem);
            } else if ("label".equals(view)) {
                return new LabelView(elem);
            }
            return new LabelView(elem);
        }

    }

}
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

import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.gelin.swing.utils.Messages;
import ru.gelin.swing.utils.ErrorFormat;
import ru.gelin.fictionbook.common.FBDocument;
import ru.gelin.fictionbook.common.FBDocumentHolder;
import ru.gelin.fictionbook.common.FBFileFilter;
import ru.gelin.fictionbook.common.FBException;

/**
 *  Action which is performed for opening new document in Viewer.
 */
public class OpenAction extends AbstractAction {

    /** commons logging instance */
    protected Log log = LogFactory.getLog(this.getClass());

    /** localized messages instance */
    protected static Messages msg = Messages.getInstance("ru/gelin/fictionbook/viewer/resources/messages");

    /** document holder instance */
    FBDocumentHolder documentHolder;

    public OpenAction(FBDocumentHolder documentHolder) {
        super();
        this.documentHolder = documentHolder;
        putValue(NAME, msg.get("menu.file.open"));
        putValue(MNEMONIC_KEY, KeyEvent.VK_O);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
        putValue(SHORT_DESCRIPTION, msg.get("menu.file.open.tooltip"));
    }

    /**
     *  This method is called when it's need to open new document.
     */
    public void actionPerformed(ActionEvent aoEvent) {
        File file = chooseFile();
        if (file != null) {
            documentHolder.setFBDocument(openDocument(file));
        }
    }

    /**
     *  Shows file chosing dialog and returns choosed file or null if
     *  file was not selected.
     */
    protected File chooseFile() {
        File result = null;
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FBFileFilter());
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            result = chooser.getSelectedFile();
            if (log.isInfoEnabled()) {
                log.info(result + " file is selected");
            }
        }
        return result;
    }

    /**
     *  Tries to create FBDocument from specified file.
     *  If document can't be created error message dialog appears.
     *  @param   file   file to open
     *  @return new document or null
     */
    public static FBDocument openDocument(File file) {
        FBDocument result = null;
        try {
            result = new FBDocument(file);
        } catch (FBException e) {
            JOptionPane.showMessageDialog(null,
                msg.get("opendocument.error",
                    new String[] { file.toString(),
                                   ErrorFormat.format(e.getCause().getMessage()) }),
                msg.get("opendocument.error.title"),
                JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

}

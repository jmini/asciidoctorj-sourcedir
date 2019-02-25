/*********************************************************************
* Copyright (c) 2019 Jeremie Bresson
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package fr.jmini.asciidoctorj.sourcedir;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.Preprocessor;
import org.asciidoctor.extension.PreprocessorReader;

public class SetSourcedirPreprocessor extends Preprocessor {

    public SetSourcedirPreprocessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public void process(Document document, PreprocessorReader reader) {
        if (!document.getAttributes()
                .containsKey("sourcedir-definition")) {
            //TODO log a warning.
            return;
        }
        String sourceDirDefinition = document.getAttributes()
                .get("sourcedir-definition")
                .toString();
        if (sourceDirDefinition.isEmpty()) {
            //TODO log a warning.
            return;
        }
        Path sourceDirPath = Paths.get(sourceDirDefinition);

        if (!document.getAttributes()
                .containsKey("docfile")) {
            //TODO log a warning.
            return;
        }
        String docfile = document.getAttributes()
                .get("docfile")
                .toString();
        if (docfile.isEmpty()) {
            //TODO log a warning.
            return;
        }
        Path docdirPath = Paths.get(docfile)
                .getParent();

        String sourcedir = docdirPath.relativize(sourceDirPath)
                .toString();
        if (!sourcedir.isEmpty()) {
            sourcedir = sourcedir + "/";
        }
        document.getAttributes()
                .put("sourcedir", sourcedir);
    }
}

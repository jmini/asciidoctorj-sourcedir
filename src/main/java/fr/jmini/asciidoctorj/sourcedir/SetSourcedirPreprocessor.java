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
        System.err.println("XXX docfile: " + document.getAttributes()
                .get("docfile")
                .toString());
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
        Path sourceDir = Paths.get(sourceDirDefinition);
        System.err.println("XXX sourceDir: " + sourceDir);

        if (!document.getAttributes()
                .containsKey("docdir")) {
            //TODO log a warning.
            return;
        }
        String docdir = document.getAttributes()
                .get("docdir")
                .toString();
        if (docdir.isEmpty()) {
            //TODO log a warning.
            return;
        }
        Path docdirPath = Paths.get(docdir);
        System.err.println("XXX docdirPath: " + sourceDir);

        String sourcedir = docdirPath.relativize(sourceDir)
                .toString();
        if (!sourcedir.isEmpty()) {
            sourcedir = sourcedir + "/";
        }
        System.err.println("XXX sourcedir: " + sourcedir);

        document.getAttributes()
                .put("sourcedir", sourcedir);
    }
}

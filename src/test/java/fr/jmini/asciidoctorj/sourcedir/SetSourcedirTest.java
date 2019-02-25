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

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashMap;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Asciidoctor.Factory;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;
import org.junit.Test;

public class SetSourcedirTest {

    private static final String SRC = Paths.get("src")
            .toAbsolutePath()
            .toString();
    private static final String RESOURCES = Paths.get("src/test/resources")
            .toAbsolutePath()
            .toString();

    @Test
    public void testSourcedirDefinitionIsSrc() throws Exception {
        runTest(SRC, "<p>Lorem ipusm ../../</p>");
    }

    @Test
    public void testSourcedirDefinitionIsResources() throws Exception {
        runTest(RESOURCES, "<p>Lorem ipusm </p>");
    }

    @Test
    public void testSourcedirDefinitionIsNotDefined() throws Exception {
        runTest(null, "<p>Lorem ipusm {sourcedir}</p>");
    }

    private void runTest(String sourcedirDefinition, String expected) throws IOException, URISyntaxException {
        Asciidoctor asciidoctor = Factory.create();
        HashMap<String, Object> attributes = new java.util.HashMap<String, Object>();
        if (sourcedirDefinition != null) {
            attributes.put("sourcedir-definition", sourcedirDefinition);
        }

        String html = asciidoctor.convertFile(new File("src/test/resources/test.adoc"), OptionsBuilder.options()
                .safe(SafeMode.UNSAFE)
                .attributes(attributes)
                .toFile(false)
                .get());

        assertTrue("html does not contains the expected string. Current value:\n" + html, html.contains(expected));
    }
}

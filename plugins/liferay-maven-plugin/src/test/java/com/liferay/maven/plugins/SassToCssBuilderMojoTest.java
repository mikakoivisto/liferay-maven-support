/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.maven.plugins;

import com.liferay.maven.AbstractLiferayMojoTestCase;

import java.io.File;

import org.apache.maven.it.Verifier;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author William Newbury
 */
public class SassToCssBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testSassToCssBuilderMojo() throws Exception {
        generateSassToCssBuilderProject();

        verifier = new Verifier(getBasedir() + "/target/testproject");

        verifier.setAutoclean(false);
        assertFalse(verifier.isAutoclean());

        executeGoal("liferay:build-css");

        assertTrue(
            "cant find target/testproject-1.0-SNAPSHOT/" +
            "css/.sass-cache/main.css",
            checkExists(
                "target/testproject/target/testproject-1.0-SNAPSHOT/" +
                "css/.sass-cache/main.css"));
    }

    protected void generateSassToCssBuilderProject() throws Exception {
        generateArchetype("liferay-theme-archetype");

        File pomPath = new File(getBasedir() + "/target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File(getBasedir() + "/src/test/resources/theme/pom.xml"),
            pomPath);

        String cssDir = getBasedir() + "/target/testproject/target/" +
                        "testproject-1.0-SNAPSHOT/css/";
        File file = new File(cssDir);
        file.mkdirs();
        file = new File(cssDir + "main.css");
        file.createNewFile();
    }

}
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
public class LangBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testLangBuilderMojo() throws Exception {
        System.out.println(
            getBasedir() + "/target/testproject/src/main/resources/" +
            "content/Language_ar.properties");

        generateLangBuilderProject();

        verifier = new Verifier(getBasedir() + "/target/testproject");

        executeGoal("liferay:build-lang");

        assertTrue(
            "cant find resource/content/Language_ar.properties",
            checkExists(
                "/target/testproject/src/main/resources/" +
                "content/Language_ar.properties"));
    }

    protected void generateLangBuilderProject() throws Exception {
        generateArchetype("liferay-theme-archetype");

        File pomPath = new File(
            getBasedir() + "/target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File(
                getBasedir() + "/src/test/resources/theme/pom.xml"), pomPath);

        String contentDir = getBasedir() +
            "/target/testproject/src/main/resources/content/";
        File file = new File(contentDir);
        file.mkdirs();
        file = new File(contentDir + "language.properties");
        file.createNewFile();
    }

}



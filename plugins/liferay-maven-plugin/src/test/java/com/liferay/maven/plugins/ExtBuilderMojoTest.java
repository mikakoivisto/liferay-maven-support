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
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.it.Verifier;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author Matthew Tambara
 * @author William Newbury
 */
public class ExtBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testExtBuilderMojo() throws Exception {
        generateExtBuilderProject();

        verifier = new Verifier("target/testproject");
        verifier.setAutoclean(false);

        executeGoal("install");

        verifier = new Verifier("target/testproject/testproject-ext");
        verifier.setAutoclean(false);

        List cliOptions = new ArrayList();
        cliOptions.add(
            "-DserviceFileName=../testproject-ext-impl/src/main/" +
            "resources/service.xml" );
        verifier.setCliOptions(cliOptions);

        executeGoal("liferay:build-service");

        assertTrue(
            "cant find ext-testproject-ext.xml",
            checkExists(
                "target/testproject/testproject-ext/target/" +
                "testproject-ext-1.0-SNAPSHOT/WEB-INF/" +
                "ext-testproject-ext.xml"));

        executeGoal("liferay:build-ext");

        assertTrue(
            "Can't find ext-testproject-ext.xml",
            checkExists(
                "target/testproject/testproject-ext/target/" +
                "testproject-ext-1.0-SNAPSHOT/" +
                "WEB-INF/ext-testproject-ext.xml"));
    }

    protected void generateExtBuilderProject() throws Exception {
        generateArchetype("liferay-ext-archetype");

        String sqlDir =
            "target/testproject/testproject-ext/src/main/webapp/WEB-INF/sql";
        File file = new File(sqlDir);
        file.mkdirs();

        File servicePath =
            new File(
                "target/testproject/testproject-ext-impl/src/main/resources/" +
                "service.xml");

        FileUtils.copyFile(
            new File("src/test/resources/ext/service.xml"), servicePath);

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/ext/pom.xml"), pomPath);
    }

}



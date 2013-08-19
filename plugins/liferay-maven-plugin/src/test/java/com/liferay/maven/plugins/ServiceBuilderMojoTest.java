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
 * @author Matthew Tambara
 * @author William Newbury
 */
public class ServiceBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testServiceBuilderMojo() throws Exception {
        generateServiceBuilderProject();

        verifier = new Verifier("target/testproject");

        executeGoal("liferay:build-service");

        assertTrue(
            "Can't find src/main/java/com/liferay/maven/tests/model/impl" +
            "/FooBaseImpl.java",
            checkExists(
                "target/testproject/testproject-portlet/src/main/java/com/" +
                "liferay/maven/model/impl/FooBaseImpl.java"));
    }

    protected void generateServiceBuilderProject() throws Exception {
        generateArchetype("liferay-servicebuilder-archetype");

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/service/pom.xml"), pomPath);
    }

}



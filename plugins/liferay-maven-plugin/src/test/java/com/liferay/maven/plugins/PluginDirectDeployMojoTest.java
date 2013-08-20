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
public class PluginDirectDeployMojoTest extends AbstractLiferayMojoTestCase {

    public void testPluginDirectDeployMojo() throws Exception {
        generateDirectDeployProject();

        verifier = new Verifier(
            getBasedir() +"/target/testproject/testproject-ext-impl");

        verifier.setAutoclean(false);
        assertFalse(verifier.isAutoclean());

        executeGoal("liferay:direct-deploy");

        assertTrue(
            "cant find target/testproject/testproject-ext-impl/target/" +
            "liferay-work/appServerPortalDir/WEB-INF/lib/" +
            "ext-testproject-ext-impl.jar",
            checkExists(
                "target/testproject/testproject-ext-impl/target/" +
                "liferay-work/appServerPortalDir/WEB-INF/lib/" +
                "ext-testproject-ext-impl.jar"));
        assertTrue(
            "cant find target/testproject/testproject-ext-impl/target/" +
            "liferay-work/appServerPortalDir/WEB-INF/classes/" +
            "testproject-ext-impl-1.0-SNAPSHOT.jar",
            checkExists(
                "target/testproject/testproject-ext-impl/target/" +
                "liferay-work/appServerPortalDir/WEB-INF/classes/" +
                "testproject-ext-impl-1.0-SNAPSHOT.jar"));
    }

    protected void generateDirectDeployProject() throws Exception {
        generateArchetype("liferay-ext-archetype");

        File pomPath = new File(
            getBasedir() +"/target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File(getBasedir() + "/src/test/resources/directdeploy/pom.xml"),
            pomPath);

        String jarDir = getBasedir() +
            "/target/testproject/testproject-ext-impl/target/";
        File file = new File(jarDir);
        file.mkdirs();
        file = new File(jarDir + "classes");
        file.mkdir();
        file = new File(
            jarDir + "classes/testproject-ext-impl-1.0-SNAPSHOT.jar");
        file.createNewFile();
        file = new File(jarDir + "testproject-ext-impl-1.0-SNAPSHOT.jar");
        file.createNewFile();
    }

}
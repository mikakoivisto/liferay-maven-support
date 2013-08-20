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
public class PluginDeployerMojoTest extends AbstractLiferayMojoTestCase {

    public void testPluginDeployerMojo() throws Exception {
        generatePluginDeployerProject();

        verifier = new Verifier(getBasedir() + "/target/testproject");

        verifier.setAutoclean(false);
        assertFalse(verifier.isAutoclean());

        executeGoal("install");
        executeGoal("liferay:deploy");

        assertTrue(
            "Can't find testproject-portlet-1.0-SNAPSHOT.war",
            checkExists(
                "target/testproject/testproject-portlet/src/main/resources/" +
                "dir/testproject-portlet-1.0-SNAPSHOT.war"));
    }

    protected void generatePluginDeployerProject() throws Exception {
        generateArchetype("liferay-servicebuilder-archetype");

        File pomPath = new File(getBasedir() + "/target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File(getBasedir() + "/src/test/resources/service/pom.xml"),
            pomPath);

        File warPath = new File(
            getBasedir() + "/target/testproject/src/main/resources/" +
            "testproject-portlet-1.0-SNAPSHOT.war");
        FileUtils.copyFile(new File(
            getBasedir() + "/src/test/resources/service/" +
            "testproject-portlet-1.0-SNAPSHOT.war"), warPath);
    }

}



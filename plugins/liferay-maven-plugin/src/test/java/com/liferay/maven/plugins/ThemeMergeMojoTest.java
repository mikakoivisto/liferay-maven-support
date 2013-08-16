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
import org.apache.maven.project.MavenProject;

import java.io.File;
import org.apache.maven.it.Verifier;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author William Newbury
 */
public class ThemeMergeMojoTest extends AbstractLiferayMojoTestCase {

    public void testThemeMerge() throws Exception {
        generateThemeMergeTestProject();

        verifier = new Verifier("target/testproject");

        executeGoal("liferay:theme-merge");

        assertTrue(
            "cant find /target/testproject-1.0-SNAPSHOT",
            checkExists(
                "target/testproject/target/testproject-1.0-SNAPSHOT"));
        assertTrue(
            "cant find /target/testproject-1.0-SNAPSHOT/images/screenshot.png",
            checkExists(
                "target/testproject/target/testproject-1.0-SNAPSHOT" +
                "/images/screenshot.png"));
        assertFalse(
            "found /target/testproject-1.0-SNAPSHOT/images/thumbnail.png",
            checkExists(
                "target/testproject/target/testproject-1.0-SNAPSHOT" +
                "/images/thumbnail.png"));
    }

    protected void generateThemeMergeTestProject() throws Exception {
        generateArchetype("liferay-theme-archetype");

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/theme/pom.xml"), pomPath);
    }

}
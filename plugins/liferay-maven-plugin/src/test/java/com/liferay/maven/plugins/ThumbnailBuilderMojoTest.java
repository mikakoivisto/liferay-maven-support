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
public class ThumbnailBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testThumbnailBuilder() throws Exception {
        generateThumbnailBuilderTestProject();

        verifier = new Verifier("target/testproject");

        executeGoal("liferay:build-thumbnail");

        assertTrue(
            "cant find target/testproject/src/main/webapp" +
            "/images/thumbnail.png",
            checkExists(
                "target/testproject/src/main/webapp/images/thumbnail.png"));
    }

    protected void generateThumbnailBuilderTestProject() throws Exception {
        generateArchetype("liferay-theme-archetype");

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/theme/pom.xml"), pomPath);

        String imageDir = "target/testproject/src/main/webapp/images/";
        File file = new File(imageDir);
        file.mkdirs();

        file = new File(imageDir + "thumbnail.png");
        if(file.exists())
            file.delete();

        file = new File(imageDir + "screenshot.png");
        FileUtils.copyFile(
            new File("src/test/resources/thumbnail/screenshot.png"), file);
    }

}
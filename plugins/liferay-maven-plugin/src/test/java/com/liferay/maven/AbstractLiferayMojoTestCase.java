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

package com.liferay.maven;

import com.liferay.maven.plugins.LangBuilderMojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.maven.archetype.Archetype;
import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.it.Verifier;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;

import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;

/**
 * @author William Newbury
 */
public abstract class AbstractLiferayMojoTestCase extends AbstractMojoTestCase {
    protected boolean checkExists(String target) {
        return (getTestFile(target).exists());
    }

    protected void deleteDirectory(String targetFolder) throws Exception {
        FileUtils.deleteDirectory(getTestFile(targetFolder));
    }

    protected void generateArchetype(String archetypeType) throws Exception {
        MavenProject project = getMavenProject();
        FileUtils.deleteDirectory(
            getTestFile("target/" + project.getArtifactId()));

        Archetype archetype = (Archetype) lookup(Archetype.ROLE);

        String localRepoPath = System.getProperty("localRepoPath");

        if (StringUtils.isEmpty(localRepoPath)) {
            localRepoPath = System.getProperty("user.home") +
                            System.getProperty("file.separator") +
                            ".m2" + System.getProperty("file.separator") +
                            "repository";
        }

        String mavenRepoLocal = "file://" + localRepoPath;

        ArtifactRepositoryLayout layout =
                (ArtifactRepositoryLayout) container.lookup(
                    ArtifactRepositoryLayout.ROLE, "default");

        ArtifactRepository localRepository = new DefaultArtifactRepository(
            "local", mavenRepoLocal, layout);

        List<ArtifactRepository> remoteRepositories =
            new ArrayList<ArtifactRepository>();

        String archetypeGroupId = "com.liferay.maven.archetypes";

        ArchetypeGenerationRequest request = new ArchetypeGenerationRequest();

        request.setGroupId(project.getGroupId());
        request.setArtifactId(project.getArtifactId());
        request.setVersion("1.0-SNAPSHOT");
        request.setArchetypeGroupId(archetypeGroupId);
        request.setArchetypeArtifactId(archetypeType);
        request.setArchetypeVersion("6.2.0-SNAPSHOT");
        request.setLocalRepository(localRepository);
        request.setRemoteArtifactRepositories(remoteRepositories);
        request.setOutputDirectory(getTestFile("target").getAbsolutePath());

        archetype.generateProjectFromArchetype(request);
    }

    protected void executeGoal(String goal) throws Exception {
        verifier.deleteArtifact(
            "org.apache.maven.its.itsample", "parent", "1.0", "pom");
        verifier.deleteArtifact(
            "org.apache.maven.its.itsample", "checkstyle-test", "1.0", "jar");
        verifier.deleteArtifact(
            "org.apache.maven.its.itsample", "checkstyle-assembly", "1.0",
            "jar");

        verifier.executeGoal(goal);
    }

    protected MavenProject getMavenProject() {
        return new MavenProjectStub() {
            public String getArtifactId() {
                return "testproject";
            }

            public String getGroupId() {
                return "com.liferay.maven";
            }
        };
    }

    protected Verifier verifier;
}
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

        verifier = new Verifier("target/testproject");

        executeGoal("liferay:theme-merge");
    }
}
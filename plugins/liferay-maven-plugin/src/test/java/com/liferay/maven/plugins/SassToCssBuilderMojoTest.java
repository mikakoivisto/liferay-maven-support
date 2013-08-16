package com.liferay.maven.plugins;

import com.liferay.maven.AbstractLiferayMojoTestCase;
import org.apache.maven.project.MavenProject;

import java.io.File;
import org.apache.maven.it.Verifier;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author William Newbury
 */
public class SassToCssBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testSassToCssBuilderMojo() throws Exception {
        generateSassToCssBuilderProject();

        assertTrue(
            "cant find target/testproject-1.0-SNAPSHOT/" +
            "css/.sass-cache/main.css",
            checkExists(
                "target/testproject/target/testproject-1.0-SNAPSHOT/" +
                "css/.sass-cache/main.css"));
    }

    protected void generateSassToCssBuilderProject() throws Exception {
        generateArchetype("liferay-theme-archetype");

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/theme/pom.xml"), pomPath);

        String cssDir = "target/testproject/test/" +
                        "testproject-1.0-SNAPSHOT/css/";
        File file = new File(cssDir);
        file.mkdirs();
        file = new File(cssDir + "main.css");
        file.createNewFile();

        file = new File("target/testproject/test/");
        file.renameTo(new File("target/testproject/target"));

        verifier = new Verifier("target/testproject");

        verifier.setAutoclean(false);
        assertFalse(verifier.isAutoclean());

        executeGoal("liferay:build-css");
    }
}
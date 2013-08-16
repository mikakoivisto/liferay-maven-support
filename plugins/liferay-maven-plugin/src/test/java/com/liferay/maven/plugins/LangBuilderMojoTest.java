package com.liferay.maven.plugins;

import com.liferay.maven.AbstractLiferayMojoTestCase;
import org.apache.maven.project.MavenProject;

import java.io.File;
import org.apache.maven.it.Verifier;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author William Newbury
 */
public class LangBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testLangBuilderMojo() throws Exception {
        generateLangBuilderProject();

        assertTrue(
            "cant find resource/content/Language_ar.properties",
            checkExists(
                "target/testproject/src/main/resources/" +
                "content/Language_ar.properties"));
    }

    protected void generateLangBuilderProject() throws Exception {
        generateArchetype("liferay-theme-archetype");

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/theme/pom.xml"), pomPath);

        String contentDir = "target/testproject/src/main/resources/content/";
        File file = new File(contentDir);
        file.mkdirs();
        file = new File(contentDir + "language.properties");
        file.createNewFile();

        verifier = new Verifier("target/testproject");

        executeGoal("liferay:build-lang");
    }
}



package com.liferay.maven.plugins;

import com.liferay.maven.AbstractLiferayMojoTestCase;
import org.apache.maven.project.MavenProject;

import java.io.File;
import org.apache.maven.it.Verifier;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author William Newbury
 */
public class ThumbnailBuilderMojoTest extends AbstractLiferayMojoTestCase {

    public void testThumbnailBuilder() throws Exception {
        generateThumbnailBuilderTestProject();

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

        verifier = new Verifier("target/testproject");

        executeGoal("liferay:build-thumbnail");
    }
}
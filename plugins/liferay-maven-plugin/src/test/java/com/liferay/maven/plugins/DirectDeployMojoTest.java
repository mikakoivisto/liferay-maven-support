package com.liferay.maven.plugins;

import com.liferay.maven.AbstractLiferayMojoTestCase;
import org.apache.maven.project.MavenProject;

import java.io.File;
import org.apache.maven.it.Verifier;

import org.codehaus.plexus.util.FileUtils;

/**
 * @author William Newbury
 */
public class DirectDeployMojoTest extends AbstractLiferayMojoTestCase {

    public void testDirectDeployMojo() throws Exception {
        generateDirectDeployProject();

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

        File pomPath = new File("target/testproject/pom.xml");
        FileUtils.forceDelete(pomPath);
        FileUtils.copyFile(
            new File("src/test/resources/directdeploy/pom.xml"), pomPath);

        String jarDir = "target/testproject/testproject-ext-impl/target/";
        File file = new File(jarDir);
        file.mkdirs();
        file = new File(jarDir + "classes");
        file.mkdir();
        file = new File(
            jarDir + "classes/testproject-ext-impl-1.0-SNAPSHOT.jar");
        file.createNewFile();
        file = new File(jarDir + "testproject-ext-impl-1.0-SNAPSHOT.jar");
        file.createNewFile();

        verifier = new Verifier("target/testproject/testproject-ext-impl");

        verifier.setAutoclean(false);
        assertFalse(verifier.isAutoclean());

        executeGoal("liferay:direct-deploy");
    }
}
package com.example.boot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testClassPath() throws IOException {
        String path = "classpath:activiti.cfg.xml";
        Resource resource = new DefaultResourceLoader().getResource(path);
        System.out.println(resource.getFile().getAbsolutePath());
        System.out.println(resource.getFile().getPath());
        System.out.println(resource.getURI().getPath());
        System.out.println(resource.getURL().getPath());


        File file = new File("D:/workspace/hb-manage/freecode-core/core-admin/target/jar:file:/root/core-admin.jar!/BOOT-INF/classes!/HarmonyOS_Sans_SC_Regular.ttf");

        System.out.println(file.getPath());
    }
}

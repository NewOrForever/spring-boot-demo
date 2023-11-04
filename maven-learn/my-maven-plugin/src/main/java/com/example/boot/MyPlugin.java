package com.example.boot;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "touch")
public class MyPlugin
        extends AbstractMojo {
    @Parameter
    String sex;

    @Parameter
    String describe;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(String.format("luban sex=%s describe=%s", sex, describe));
    }
}

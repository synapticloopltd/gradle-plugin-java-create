package synapticloop.gradle.plugin.create;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

/*
 * Copyright (c) 2016 - 2017 Synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import synapticloop.templar.Parser;
import synapticloop.templar.exception.RenderException;
import synapticloop.templar.utils.TemplarContext;

public class Main {
	private static String name = null;
	private static String desc = null;
	private static String group = null;
	private static String packageName = null;
	private static String artefact = null;

	private static final Options options = new Options();
	static {
		options.addOption(new Option("n", "name", true, "The name of the gradle plugin (java class name CamelCase - __MUST__ not end in the word 'Plugin')"));
		options.addOption(new Option("d", "desc", true, "The description for the gradle plugin (free text description)"));
		options.addOption(new Option("a", "artefact", true, "The artefact that this will be published under (e.g. the github user/org name or the gradle plugins username)"));
		options.addOption(new Option("g", "group", true, "The group for the gradle plugin (the group that the plugin will be listed under for 'gradle tasks')"));
		options.addOption(new Option("p", "package", true, "The java package for the plugin (java dot '.' delimited package name)"));
	}

	private static void parseOptions(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		name = cmd.getOptionValue("n");
		desc = cmd.getOptionValue("d");
		group = cmd.getOptionValue("g");
		packageName = cmd.getOptionValue("p");
		artefact = cmd.getOptionValue("a");

		if(null == name || 
				null == desc || 
				null == group || 
				null == packageName ||
				null == artefact) {
			throw new ParseException("All options are required");
		}

		if(name.endsWith("Plugin")) {
			throw new ParseException("The name of the Plugin __MUST__ not end with Plugin");
		}
	}

	public static void main(String[] args) throws synapticloop.templar.exception.ParseException, RenderException {
		try {
			parseOptions(args);
		} catch (ParseException ex) {
			System.out.println("[FATAL] \n\t" + ex.getMessage() + "\n");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("gradle-plugin-java-create", options);
			return;
		}

		// else we are good to go

		// the first thing we are going to do is to create the directories
		File propertiesDirectory = new File("./src/main/resources/META-INF/gradle-plugins/");
		propertiesDirectory.mkdirs();

		File javaSourceDirectory = new File("./src/main/java/" + packageName.replace(".", "/"));
		javaSourceDirectory.mkdirs();

		TemplarContext templarContext = new TemplarContext();
		templarContext.add("name", name);
		templarContext.add("description", desc);
		templarContext.add("package", packageName);
		templarContext.add("group", group);
		templarContext.add("artefact", artefact);

		// now create the properties file
		render(Main.class.getResourceAsStream("/META-INF/gradle-plugins/plugin.properties.templar"), 
				new File(propertiesDirectory.getAbsolutePath() + "/" + packageName + "." + name + ".properties"), 
				templarContext);

		// now for the Plugin
		render(Main.class.getResourceAsStream("/plugin.java.templar"), 
				new File(javaSourceDirectory.getAbsolutePath() + "/" + name + "Plugin.java"), 
				templarContext);

		// now for the PluginExtension
		render(Main.class.getResourceAsStream("/pluginextension.java.templar"), 
				new File(javaSourceDirectory.getAbsolutePath() + "/" + name + "PluginExtension.java"), 
				templarContext);

		// now for the Plugin task
		render(Main.class.getResourceAsStream("/task.java.templar"), 
				new File(javaSourceDirectory.getAbsolutePath() + "/" + name + "Task.java"), 
				templarContext);

		// now for the Plugin task
		render(Main.class.getResourceAsStream("/build.plugin.gradle.templar"), 
				new File("./build.plugin.gradle"), 
				templarContext);
	}

	private static void render(InputStream inputStream, File outputFile, TemplarContext templarContext) throws synapticloop.templar.exception.ParseException, RenderException {
		Parser parser = new Parser(inputStream);
		parser.renderToFile(templarContext, outputFile);
	}
}

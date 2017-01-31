package synapticloop.gradle.plugin.create;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	private static final String OPTION_ARTEFACT = "a";
	private static final String OPTION_DESC = "d";
	private static final String OPTION_DISPLAY_GROUP = "dg";
	private static final String OPTION_DISPLAY_NAME = "dn";
	private static final String OPTION_JAVA_PACKAGE = "jp";
	private static final String OPTION_NAME = "n";
	private static final String OPTION_TAGS = "t";

	private static String artefact = null;
	private static String desc = null;
	private static String displayGroup = null;
	private static String displayName = null;
	private static String javaPackage = null;
	private static String name = null;
	private static List<String> tags = new ArrayList<String>();

	private static final Options options = new Options();
	static {
		Option artefactOpt = new Option(OPTION_ARTEFACT, "artefact", true, "The artefact that this will be published under (e.g. the github user/org name or the gradle plugins username)");
		artefactOpt.setRequired(true);
		options.addOption(artefactOpt);

		Option descOption = new Option(OPTION_DESC, "desc", true, "The description for the gradle plugin (free text description)");
		descOption.setRequired(true);
		options.addOption(descOption);

		Option displayGroupOption = new Option(OPTION_DISPLAY_GROUP, "display-group", true, "The display group for the gradle plugin (the group that the plugin will be listed under for 'gradle tasks')");
		displayGroupOption.setRequired(true);
		options.addOption(displayGroupOption);

		Option displayNameOption = new Option(OPTION_DISPLAY_NAME, "display-name", true, "The name to display on the gradle plugins site");
		displayNameOption.setRequired(true);
		options.addOption(displayNameOption);

		Option javaPackage = new Option(OPTION_JAVA_PACKAGE, "java-package", true, "The java package for the plugin (java dot '.' delimited package name)");
		javaPackage.setRequired(true);
		options.addOption(javaPackage);

		Option nameOption = new Option(OPTION_NAME, "name", true, "The name of the gradle plugin (java class name CamelCase - __MUST__ not end in the word 'Plugin')");
		nameOption.setRequired(true);
		options.addOption(nameOption);

		Option tagsOption = new Option(OPTION_TAGS, "tags", true, "A comma ',' separated list of tags for the plugin that is displayed on the gradle plugins site");
		tagsOption.setRequired(true);
		options.addOption(tagsOption);

	}

	private static void parseOptions(String[] args) throws ParseException {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		artefact = cmd.getOptionValue(OPTION_ARTEFACT);
		desc = cmd.getOptionValue(OPTION_DESC);
		displayGroup = cmd.getOptionValue(OPTION_DISPLAY_GROUP);
		displayName = cmd.getOptionValue(OPTION_DISPLAY_NAME);
		javaPackage = cmd.getOptionValue(OPTION_JAVA_PACKAGE);
		name = cmd.getOptionValue(OPTION_NAME);

		String[] splits = cmd.getOptionValue(OPTION_TAGS).split(",");
		for (String tag : splits) {
			tags.add(tag.trim());
		}

		if(name.endsWith("Plugin")) {
			throw new ParseException("The 'name' argument __MUST__ not end with 'Plugin'");
		}
	}

	public static void main(String[] args) throws synapticloop.templar.exception.ParseException, RenderException {
		try {
			parseOptions(args);
		} catch (ParseException ex) {
			System.out.println("[FATAL] \n\t" + ex.getMessage() + "\n");
			HelpFormatter formatter = new HelpFormatter();
			formatter.setWidth(120);
			formatter.printHelp("gradle-plugin-java-create", options);
			return;
		}

		// else we are good to go

		// the first thing we are going to do is to create the directories
		File propertiesDirectory = new File("./src/main/resources/META-INF/gradle-plugins/");
		propertiesDirectory.mkdirs();

		File javaSourceDirectory = new File("./src/main/java/" + javaPackage.replace(".", "/"));
		javaSourceDirectory.mkdirs();

		TemplarContext templarContext = new TemplarContext();
		templarContext.add("artefact", artefact);
		templarContext.add("description", desc);
		templarContext.add("displayGroup", displayGroup);
		templarContext.add("displayName", displayName);
		templarContext.add("javaPackage", javaPackage);

		templarContext.add("name", name);
		String lowerName = name.substring(0,1).toLowerCase() + name.substring(1);
		templarContext.add("lowerName", lowerName);

		templarContext.add("tags", tags);

		// now create the properties file
		render(Main.class.getResourceAsStream("/META-INF/gradle-plugins/plugin.properties.templar"), 
				new File(propertiesDirectory.getAbsolutePath() + "/" + artefact + "." + lowerName + ".properties"), 
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

		// now for the Plugin task
		render(Main.class.getResourceAsStream("/build.plugin.initial.gradle.templar"), 
				new File("./build.plugin.initial.gradle"), 
				templarContext);
	}

	private static void render(InputStream inputStream, File outputFile, TemplarContext templarContext) throws synapticloop.templar.exception.ParseException, RenderException {
		Parser parser = new Parser(inputStream);
		parser.renderToFile(templarContext, outputFile);
	}
}

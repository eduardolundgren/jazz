/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.scripting.sass;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.io.StreamUtil;
import jodd.io.ZipUtil;
import jodd.util.ClassLoaderUtil;
import jodd.util.StringUtil;

import com.liferay.scripting.ruby.RubyExecutor;


/**
 * @author Eduardo Lundgren
 */
public class SASSParser {

	public static String CSS_DIR = "";

	public static String GEMS_DIR =
		System.getProperty("user.dir").concat("/.ruby-gems");

	public static String SASS_FILE_PATTERN = "*.scss";

	public static List<String> LOAD_PATHS = Arrays.asList(
		new String[] {
			GEMS_DIR + "/gems/chunky_png-1.2.6/lib",
			GEMS_DIR + "/gems/compass-0.12.2/lib",
			GEMS_DIR + "/gems/fssm-0.2.9/lib",
			GEMS_DIR + "/gems/sass-3.2.1/lib"
		}
	);

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				System.out.println("You should specify a file or directory.\n" +
					"usage: jazz.jar [sassFilePattern] [cssDir] [gemsDir]");

				return;
			}

			if (args.length >= 1) {
				SASS_FILE_PATTERN = args[0];
			}

			if (args.length >= 2) {
				CSS_DIR = args[1];
			}

			if (args.length >= 3) {
				GEMS_DIR = args[2];
			}

			File gemsFile = new File(GEMS_DIR);

			if (!gemsFile.exists()) {
				_initRubyGems();
			}

			String scriptContent = new String(
				StreamUtil.readChars(ClassLoaderUtil.getResourceAsStream(
					"dependencies/compile.rb")));

			Map<String, Object> inputVariables = new HashMap<String, Object>();

			inputVariables.put("sassFilePattern", SASS_FILE_PATTERN);
			inputVariables.put("cssDir", CSS_DIR);
			inputVariables.put("gemsDir", GEMS_DIR);

			_rubyExecutor.execute(scriptContent, inputVariables);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _initRubyGems() {
		try {
			String jarPath = SASSParser.class.getProtectionDomain()
				.getCodeSource().getLocation().toURI().toString();

			jarPath = StringUtil.replaceFirst(jarPath, "file:", "");

			ZipUtil.unzip(jarPath, GEMS_DIR, "gems/**");
		}
		catch (Exception e) {
		}
	}

	private static RubyExecutor _rubyExecutor = new RubyExecutor(LOAD_PATHS);

}

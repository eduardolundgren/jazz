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

package com.liferay.scripting.ruby;

import java.util.List;
import java.util.Map;

import org.jruby.RubyInstanceConfig;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.ScriptingContainer;
import org.jruby.embed.internal.LocalContextProvider;

/**
 * @author Eduardo Lundgren
 */
public class RubyExecutor {

	public RubyExecutor() {
		this(null);
	}

	public RubyExecutor(List<String> loadPaths) {
		String basepath = System.getProperty("user.dir");

		LocalContextProvider localContextProvider =
			_scriptingContainer.getProvider();

		RubyInstanceConfig rubyInstanceConfig =
			localContextProvider.getRubyInstanceConfig();

		if (loadPaths != null) {
			rubyInstanceConfig.setLoadPaths(loadPaths);
		}

		_scriptingContainer.setCurrentDirectory(basepath);
	}

	public void execute(
		String scriptContent, Map<String, Object> inputVariables) {

		_scriptingContainer.clear();

		if (inputVariables != null) {
			for (String key : inputVariables.keySet()) {
				_scriptingContainer.put(
					DOLLAR.concat(key), inputVariables.get(key));
			}
		}

		_scriptingContainer.runScriptlet(scriptContent);
	}

	private ScriptingContainer _scriptingContainer = new ScriptingContainer(
		LocalContextScope.THREADSAFE);

	private static final String DOLLAR = "$";

}

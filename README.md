# JAZZ: A Java SASS and Compass Wrapper

## Overview

**JAZZ**, is the missing Java [SASS](http://sass-lang.com/) and [Compass](http://compass-style.org/) wrapper that you need to integrate in your **JAVA projects**.

### Installation

Download the jar file `jazz.jar` [here](#).

### Syntax

	java -jar jazz.jar [sassFilePattern] [cssDir] [gemsDir]

**sassFilePattern**

File names that matches with the specified pattern will be converted to SASS. For more information about file patterns check the [Ruby documentation for glob patterns](http://www.ruby-doc.org/core-1.9.3/Dir.html#method-c-glob)

**cssDir**

The ouput directory to place the converted files. **Note:** If not specified JAZZ will replace the original SASS file content with the converted content.


**gemsDir**

A folder where JAZZ will cache the compass ruby gem files. **Note:** If not specified JAZZ will create a directory called `.ruby-gems` in the jar directory.

### Example

#### Single file

Parse `file.scss` and replace its content with the parsed content.

	java -jar jazz.jar file.scss

#### Single file into output directory

Parse `file.scss` and replace its content with the parsed content into `/myfolder/css/file.css`.

	java -jar jazz.jar file.scss /myfolder/css/

#### Multiple files

Parse all `*.scss` files from `myfolder` and replace their content with the parsed content.

	java -jar jazz.jar /myfolder/*.scss
	
#### Multiple files into output directory

Parse all `*.scss` files from `myfolder` and place the parsed content into `/myfolder/css/` folder.

	java -jar jazz.jar /myfolder/*.scss /myfolder/css/
	
#### Advanced

Parse all `*.scss` files from `myfolder` and place the parsed content into `/myfolder/css/` folder, using `/tmp/.custom-ruby-gems-dir` custom gem directory.

	java -jar jazz.jar /myfolder/*.scss /myfolder/css/ /tmp/.custom-ruby-gems-dir
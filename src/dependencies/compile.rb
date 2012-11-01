require 'rubygems'
require 'java'
require 'compass'

Compass.add_project_configuration

load_paths = []

load_paths += Compass.configuration.sass_load_paths

files = Dir.glob($sassFilePattern)

if (files.length == 0):
	puts 'Ops, no files were found for the pattern ' + $sassFilePattern

	return
end

files.each do | file |
	fileContent = File.read(file)

	engine = Sass::Engine.new(
		fileContent,
		{
			:filename => file,
			:load_paths => load_paths,
			:style => :expanded,
			:syntax => :scss
		}
	)

	parsedContent = engine.render

	if parsedContent
		outFile = file

		unless $cssDir.empty?
			fileName = File.basename(file, File.extname(file))

			cssFullPath = File.expand_path($cssDir)

			if !File.directory?cssFullPath
				Dir.mkdir(cssFullPath)
			end

			outFile = cssFullPath + '/' + fileName + '.css'
		end

		File.open(outFile, 'w') { |f| f.write(parsedContent) }
	end
end

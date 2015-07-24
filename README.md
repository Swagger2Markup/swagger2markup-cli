# swagger2markup-cli
Command line interface (CLI) for Swagger2Markup

[![Build Status](https://travis-ci.org/mxl/swagger2markup-cli.svg)](https://travis-ci.org/mxl/swagger2markup-cli)

## Overview

This is CLI for [Swagger2Markup](https://github.com/Swagger2Markup/swagger2markup).
It converts a Swagger JSON or YAML file into several AsciiDoc or GitHub Flavored Markdown documents which can be combined with hand-written documentation. The Swagger source file can be located locally or remotely via HTTP. Swagger2Markup supports the Swagger 1.2 and 2.0 specification.

## Prerequisites

You need Java 7+.

## Building

After cloning the project you can build it from source with command:

`./gradlew build`

## Usage

`java -jar build/libs/swagger2markup-cli-x.x.x.jar help generate`

```
NAME
        swagger2markup generate - Generate

SYNOPSIS
        swagger2markup [-v] generate [-d <descriptionsPath>] [-e <examplesPath>]
                -i <inputFile> -l <language> -o <outputPath> [-p] [-s <schemasPath>]

OPTIONS
        -d <descriptionsPath>
            Include hand-written descriptions into the Paths and Definitions
            document

        -e <examplesPath>
            Include examples into the Paths document

        -i <inputFile>
            Input file

        -l <language>
            Markup language

        -o <outputPath>
            Output path

        -p
            In addition to the definitions file, also create separate definition
            files for each model definition.

        -s <schemasPath>
            Include (JSON, XML) schemas into the Definitions document

        -v
            Verbose mode
```

## License

Copyright 2015 Michael Ledin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
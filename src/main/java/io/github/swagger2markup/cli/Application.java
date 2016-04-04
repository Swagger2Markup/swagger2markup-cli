/*
 * Copyright 2016 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.swagger2markup.cli;

import io.airlift.airline.*;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.net.URI;
import java.nio.file.Paths;

@Command(name = "convert", description = "Converts a Swagger JSON or YAML file into Markup documents.")
public class Application implements Runnable{

    @Inject
    public HelpOption helpOption;

    @Option(name = {"-i", "--input"}, required = true, description = "Input. Can either be a URL or a file.")
    public String inputFile;

    @Option(name = {"-o", "--output"}, required = true, description = "Output path. Can either be a directory or a file.")
    public String outputPath;

    @Option(name = {"-c", "--config"}, description = "Config file.")
    public String configFile;

    public static void main(String[] args) {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("swagger2markup")
                    .withDescription("Converts a Swagger JSON or YAML file into Markup documents")
                    .withDefaultCommand(Help.class)
                    .withCommands(Help.class, Application.class);

        Cli<Runnable> gitParser = builder.build();

        gitParser.parse(args).run();
    }

    public void run() {
        try {
            Swagger2MarkupConfig swagger2MarkupConfig = null;
            if(StringUtils.isNotBlank(configFile)) {
                Configurations configs = new Configurations();
                Configuration config = configs.properties(configFile);
                swagger2MarkupConfig = new Swagger2MarkupConfigBuilder(config).build();
            }
            Swagger2MarkupConverter.Builder converterBuilder = Swagger2MarkupConverter.from(URI.create(inputFile));
            if(swagger2MarkupConfig != null){
                converterBuilder.withConfig(swagger2MarkupConfig);
            }
            converterBuilder.build()
                    .toPath(Paths.get(this.outputPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

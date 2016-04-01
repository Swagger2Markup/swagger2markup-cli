package io.github.robwin.swagger2markup;

import io.airlift.airline.*;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("swagger2markup")
                .withDescription("Swagger2Markup converts a Swagger JSON or YAML file into Markup documents.")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, Generate.class);

        Cli<Runnable> parser = builder.build();
        parser.parse(args).run();
    }

    @Command(name = "convertSwagger2markup", description = "Swagger2Markup converts a Swagger JSON or YAML file into Markup documents.")
    public static class Generate implements Runnable {

        @Option(name = "-i", required = true, description = "Input file")
        public String inputFile;

        @Option(name = "-o", required = true, description = "Output path")
        public String outputPath;

        @Option(name = "-c", required = true, description = "Conf file")
        public String configFile;

        @Override
        public void run() {
            try {
                Configurations configs = new Configurations();
                Configuration config = configs.properties(configFile);
                Swagger2MarkupConfig swagger2MarkupConfig = new Swagger2MarkupConfigBuilder(config).build();

                Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(Paths.get(inputFile))
                        .withConfig(swagger2MarkupConfig)
                        .build();

                converter.toFolder(Paths.get(outputPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

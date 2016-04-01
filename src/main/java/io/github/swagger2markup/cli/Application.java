package io.github.swagger2markup.cli;

import io.airlift.airline.*;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import java.nio.file.Paths;

@Command(name = "convert", description = "Converts a Swagger JSON or YAML file into Markup documents.")
public class Application implements Runnable{

    @Inject
    public HelpOption helpOption;

    @Option(name = {"-i", "--inputFile"}, required = true, description = "Input file")
    public String inputFile;

    @Option(name = {"-o", "--outputPath"}, required = true, description = "Output path")
    public String outputPath;

    @Option(name = {"-c", "--configFile"}, description = "Config file")
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
            Swagger2MarkupConverter.Builder converterBuilder = Swagger2MarkupConverter.from(Paths.get(inputFile));
            if(swagger2MarkupConfig != null){
                converterBuilder.withConfig(swagger2MarkupConfig);
            }
            converterBuilder.build()
                    .toFolder(Paths.get(outputPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

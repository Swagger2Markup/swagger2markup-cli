package me.ledin.swagger2markup;

import io.airlift.airline.*;
import io.github.robwin.markup.builder.MarkupLanguage;
import io.github.robwin.swagger2markup.Swagger2MarkupConverter;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("swagger2markup")
                .withDescription("Swagger2Markup converts a Swagger JSON or YAML file into several AsciiDoc or GitHub Flavored Markdown documents which can be combined with hand-written documentation.")
                .withDefaultCommand(Help.class)
                .withCommands(Help.class, Generate.class);

        Cli<Runnable> parser = builder.build();
        parser.parse(args).run();
    }

    public static class BaseCommand implements Runnable {
        @Option(type = OptionType.GLOBAL, name = "-v", description = "Verbose mode")
        public boolean verbose;

        public void run() {

        }
    }

    @Command(name = "generate", description = "Generate")
    public static class Generate extends BaseCommand {
        public static final String ASCIIDOC = "asciidoc";
        public static final String MARKDOWN = "markdown";

        @Option(name = "-i", required = true, description = "Input file")
        public String inputFile;

        @Option(name = "-o", required = true, description = "Output path")
        public String outputPath;

        @Option(name = "-l", required = true, allowedValues = {ASCIIDOC, MARKDOWN}, description = "Markup language")
        public String language;

        @Option(name = "-d", description = "Include hand-written descriptions into the Paths and Definitions document")
        public String descriptionsPath;

        @Option(name = "-e", description = "Include examples into the Paths document")
        public String examplesPath;

        @Option(name = "-s", description = "Include (JSON, XML) schemas into the Definitions document")
        public String schemasPath;

        @Option(name = "-p", description = "In addition to the definitions file, also create separate definition files for each model definition.")
        public boolean separateDefinitions;

        @Override
        public void run() {
            try {
                Swagger2MarkupConverter.Builder builder = Swagger2MarkupConverter.from(inputFile)
                        .withMarkupLanguage(MarkupLanguage.valueOf(language.toUpperCase()))
                        .withDescriptions(descriptionsPath)
                        .withExamples(examplesPath)
                        .withSchemas(schemasPath);
                if (separateDefinitions) {
                    builder.withSeparatedDefinitions();
                }
                builder.build().intoFolder(outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

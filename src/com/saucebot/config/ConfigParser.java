package com.saucebot.config;

import java.nio.file.Path;

public interface ConfigParser {

    public ConfigObject parse(final Path path);

}

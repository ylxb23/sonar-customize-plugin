package com.zero.sonar.customize.plugin;

import org.sonar.api.Plugin;

/**
 * Entry point of plugin.
 * @author zero
 */
public class CustomizePlugin implements Plugin {

    @Override
    public void define(Context context) {
        // server extensions -> objects are instantiated during server startup
        context.addExtension(CustomizeRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(CustomizeFileCheckRegistrar.class);
    }

}

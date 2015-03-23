package de.geofroggerfx.service;

import de.geofroggerfx.plugins.Plugin;

import java.util.List;

/**
 * TODO: class description
 *
 * @author abi
 */
public interface PluginService {

    List<Plugin> getAllPlugins();

    void executePlugin(Plugin plugin);


}

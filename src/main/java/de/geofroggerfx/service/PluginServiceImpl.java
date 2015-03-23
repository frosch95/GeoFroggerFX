package de.geofroggerfx.service;

import de.geofroggerfx.application.SessionContext;
import de.geofroggerfx.dao.jdbc.DataConfig;
import de.geofroggerfx.plugins.Plugin;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This service find, load and executes plugins based on the plugin interface.
 *
 * @author abi
 */
@Service
public class PluginServiceImpl implements PluginService {

    private final GroovyClassLoader gcl = new GroovyClassLoader();

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SessionContext sessionContext;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataConfig(DataConfig dataConfig) {
        this.jdbcTemplate = new JdbcTemplate(dataConfig.dataSource());
    }


    @Override
    public List<Plugin> getAllPlugins() {

        List<Plugin> plugins = new ArrayList<>();

        try {
            File file = new File("./plugins");
            if (!file.exists()) {
                throw new IllegalArgumentException("plugins folder does not exist");
            }

            File[] pluginFiles = file.listFiles((dir, name) -> name.endsWith("Plugin.groovy"));
            for (File pluginFile : pluginFiles) {
                Class clazz = gcl.parseClass(pluginFile);
                for (Class interf : clazz.getInterfaces()) {
                    if (interf.equals(Plugin.class)) {
                        plugins.add((Plugin) clazz.newInstance());
                        break;
                    }
                }
            }

        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return plugins;
    }

    @Override
    public void executePlugin(final Plugin plugin) {
        Map<String, Object> context = new HashMap<>();
        context.put("sessionContext", sessionContext);
        context.put("cacheService", cacheService);
        context.put("jdbcTemplate", jdbcTemplate);
        plugin.run(context);
    }
}

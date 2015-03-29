import de.geofroggerfx.plugins.Plugin
import javafx.collections.FXCollections
import javafx.scene.chart.PieChart
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox

import static javafx.scene.chart.PieChart.Data

class BasicListStatisticsPlugin implements Plugin {

    private difficultyTerrainValues = ['1', '1.5', '2', '2.5', '3', '3.5', '4', '4.5', '5']

    final String name = "Basic List Statistic"
    final String version = "0.0.2"

    @Override
    void run(final Map context) {
        showDialog(createContent(context.cacheDAO))
    }

    /**
     * creates the statistic charts to show
     * @param sessionContext context with the cache list in it
     * @return
     */
    private javafx.scene.Node createContent(cacheDAO) {

        // get the cache list from service
        def cacheList = cacheDAO.getAllCaches()

        // create a vbox as layout container
        VBox contenPane = new VBox()

        // groovy maps for selecting the statistic numbers
        def typeStats = [:]
        def difficultyStats = [:]
        def terrainStats = [:]

        // iterate over all the caches and count the data
        for (def cache in cacheList) {
            incrementStats(typeStats, cache.type)
            incrementStats(difficultyStats, cache.difficulty)
            incrementStats(terrainStats, cache.terrain)
        }

        // create javafx chart
        def typeData = FXCollections.observableArrayList()
        typeStats.each() { key, value -> typeData.add(new Data(key.toString() + ' (' + value + ')', value as double)) }
        def typeChart = new PieChart(typeData);
        typeChart.setTitle("Spreading of cache types in list.");

        // create javafx chart
        def difficultyData = FXCollections.observableArrayList()
        difficultyTerrainValues.each {
            def value = difficultyStats[it]
            if (value) difficultyData.add(new Data(it + ' (' + value + ')', value))
        }
        def difficultyChart = new PieChart(difficultyData);
        difficultyChart.setTitle("Spreading of difficulties in list.");

        // create javafx chart
        def terrainData = FXCollections.observableArrayList()
        difficultyTerrainValues.each {
            def value = terrainStats[it]
            if (value) terrainData.add(new Data(it + ' (' + value + ')', value))
        }
        def terrainChart = new PieChart(terrainData);
        terrainChart.setTitle("Spreading of terrain in list.");

        // add charts to layout container
        contenPane.children.addAll(typeChart, difficultyChart, terrainChart)

        // return the layout container
        def scrollPane = new ScrollPane(contenPane)
        scrollPane.minWidth = 600
        scrollPane.minHeight = 450
        scrollPane
    }

    private void incrementStats(map, key) {
        map[key] = map[key] ? map[key] + 1 : 1
    }

    private void showDialog(content) {
        Dialog dialog = new Dialog()
        dialog.resizableProperty().set(true)
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.setTitle(name + " (" + version + ")")
        dialog.setHeaderText("This example plugin shows some stats based on the current list.\nThese statistics are global statistics over all caches in list\nand not personalized statistics over own founds.")
        dialog.getDialogPane().setContent(content)
        dialog.setWidth(800)
        dialog.setHeight(600)
        dialog.show()
    }
}
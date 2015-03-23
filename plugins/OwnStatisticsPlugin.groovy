import de.geofroggerfx.plugins.Plugin
import javafx.collections.FXCollections
import javafx.concurrent.Service
import javafx.concurrent.Task
import javafx.concurrent.WorkerStateEvent
import javafx.event.EventHandler
import javafx.scene.chart.PieChart
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.ScrollPane
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

import static javafx.scene.chart.PieChart.Data

class OwnStatisticsPlugin implements Plugin {

    private difficultyTerrainValues = ['1', '1.5', '2', '2.5', '3', '3.5', '4', '4.5', '5']

    final String name = "Own Statistic"
    final String version = "0.0.1"
    final CalculateService service = new CalculateService();

    OwnStatisticsPlugin() {
        service.onSucceeded = {
            showDialog(createContent(it.source.value))
        } as EventHandler<WorkerStateEvent>;
    }

    @Override
    void run(final Map context) {
        calculateStats(context.cacheService)
    }

/**
 * creates the statistic charts to show
 * @param sessionContext context with the cache list in it
 * @return
 */
    private void calculateStats(cacheService) {
        // get the cache list from service
        def cacheList = cacheService.getAllCaches()
        service.cacheList = cacheList;
        service.restart();
    }

    /**
     * creates the statistic charts to show
     * @param sessionContext context with the cache list in it
     * @return
     */
    private javafx.scene.Node createContent(result) {

        // create a vbox as layout container
        VBox contenPane = new VBox()
        contenPane.prefWidth = 600
        VBox.setVgrow(contenPane, Priority.ALWAYS);

        // create javafx chart
        def typeData = FXCollections.observableArrayList()
        result.typeStats.each() { key, value -> typeData.add(new Data(key.toString() + ' (' + value + ')', value as double)) }
        def typeChart = new PieChart(typeData);
        typeChart.setTitle("Spreading of cache types found (${result.foundCount}).");

        // create javafx chart
        def difficultyData = FXCollections.observableArrayList()
        difficultyTerrainValues.each {
            def value = result.difficultyStats[it]
            if (value) difficultyData.add(new Data(it + ' (' + value + ')', value))
        }
        def difficultyChart = new PieChart(difficultyData);
        difficultyChart.setTitle("Spreading of difficulties found (${result.foundCount}).");

        // create javafx chart
        def terrainData = FXCollections.observableArrayList()
        difficultyTerrainValues.each {
            def value = result.terrainStats[it]
            if (value) terrainData.add(new Data(it + ' (' + value + ')', value))
        }
        def terrainChart = new PieChart(terrainData);
        terrainChart.setTitle("Spreading of terrain found (${result.foundCount}).");

        // add charts to layout container
        contenPane.children.addAll(typeChart, difficultyChart, terrainChart)

        // return the layout container
        def scrollPane = new ScrollPane(contenPane)
        scrollPane.minWidth = 640
        scrollPane.minHeight = 450
        scrollPane
    }

    private void showDialog(content) {
        Dialog dialog = new Dialog()
        dialog.resizableProperty().set(true)
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        dialog.setTitle(name + " (" + version + ")")
        dialog.setHeaderText("This example plugin shows some stats based on the current list.\nThese statistics are found statistics, based on all found caches in the list.")
        dialog.getDialogPane().setContent(content)
        dialog.setWidth(800)
        dialog.setHeight(600)
        dialog.show()
    }
}

class CalculateService extends Service {

    def cacheList

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected LinkedHashMap call() throws Exception {

                def typeStats = [:]
                def difficultyStats = [:]
                def terrainStats = [:]

                def foundCount = 0

                for (def cache in cacheList) {
                    if (cache.found) {
                        foundCount++
                        incrementStats(typeStats, cache.type)
                        incrementStats(difficultyStats, cache.difficulty)
                        incrementStats(terrainStats, cache.terrain)
                    }
                }
                ['foundCount': foundCount, 'typeStats': typeStats, 'difficultyStats': difficultyStats, 'terrainStats': terrainStats]
            }
        };
    }

    private void incrementStats(map, key) {
        map[key] = map[key] ? map[key] + 1 : 1
    }

}
package demo.rest;

import demo.model.GpsSimulatorRequest;
import demo.model.SimulatorInitLocations;
import demo.service.GpsSimulatorFactory;
import demo.service.PathService;
import demo.task.LocationSimulator;
import demo.task.LocationSimulatorInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class LocationSimulatorRestApi {

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @Autowired
    private PathService pathService;

    @Autowired
    private GpsSimulatorFactory gpsSimulatorFactory;

    private Map<Long, LocationSimulatorInstance> taskFutures = new HashMap<>();
    private List<LocationSimulatorInstance> instances = new ArrayList<>();

    // 1. load simulation json data polyline, speed
    // 2. transform domain model simulator request to a class that can be executed by task executor;
    // 2.1 scheduler is a class interface that can schedule a task to execute
    // 2.2 AsynTaskExecutor
    // 3. taskExecutor.submit(simulator)
    // 4. simulation starts;

    @RequestMapping("/simulation")
    public List<LocationSimulatorInstance> simulation(){
        SimulatorInitLocations fixture = this.pathService.loadSimulatorInitLocatios();

        // log first for the cancel process later.
        for (GpsSimulatorRequest gpsSimulatorRequest : fixture.getGpsSimulatorRequests()) {
            LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);

            Future<?> future = taskExecutor.submit(locationSimulator);
            LocationSimulatorInstance instance = new LocationSimulatorInstance(locationSimulator.getId(), locationSimulator, future);
            taskFutures.put(locationSimulator.getId(), instance);
            instances.add(instance);
        }

        return instances;
    }

    @RequestMapping("/cancel")
    public int cancel() {
        int numberOfCancelledTasks = 0;
        for (Map.Entry<Long, LocationSimulatorInstance> entry: taskFutures.entrySet()) {
            LocationSimulatorInstance instance = entry.getValue();
            instance.getLocationSimulator().cancel(); // set the cancel field to true.
            /*
            将LocationSimulator用LocationSimulationInstance包装起来就是为了调用future.cancel方便。
             */
            boolean wasCancelled = instance.getLocationSimulatorTask().cancel(true);

            if (wasCancelled) {
                numberOfCancelledTasks++;
            }
        }
        taskFutures.clear();
        return numberOfCancelledTasks;
    }

}

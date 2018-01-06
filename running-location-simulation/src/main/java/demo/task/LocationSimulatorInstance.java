package demo.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Future;


/*
  Wrapper class for LocatinoSimulator
 */
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LocationSimulatorInstance {
    private long instanceId;
    private LocationSimulator locationSimulator; // runnable
    private Future<?> locationSimulatorTask; // future 是 locationSimulator运行时的callback

    @Override
    public String toString() {
        return "LocationSimulatorInstance[ instanceId=" + instanceId + ", locationSimuator="+locationSimulator
                + ", locationSimulatorTask=" + locationSimulatorTask + "]";
    }
}

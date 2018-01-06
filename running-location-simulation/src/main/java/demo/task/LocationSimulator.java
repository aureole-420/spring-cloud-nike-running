package demo.task;

import demo.model.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocationSimulator implements Runnable {
    private long id;

//    private PositionService positionInforService;
    private AtomicBoolean cancel = new AtomicBoolean();

    private Double speedInMps;
    private boolean shouldMove;
    private boolean exportPositionsToMessaging = true;

    private Integer reportInterval = 500;
//    private PositionInfo positionInfo = null;
    private List<Leg> legs;
    private RunnerStatus runnerStatus = RunnerStatus.NONE;
    private String runningId;

    private Integer secondsToError = 45;
    private Point startPoint;
    private Date executionStartTime;

    private String fromAddress;
    private String toAddress;

    private MedicalInfo medicalInfo;

    public LocationSimulator(GpsSimulatorRequest gpsSimulatorRequest) {

    }

    @Override
    public void run() {

    }

    public long getId() {
        return id;
    }

    public void setSpeed(Double speed) {
        this.speedInMps = speed;
    }

    public void cancel() {
        this.cancel.set(true); // 跑的时候判断是否要cancel。标记状态为true。跑的时候发现状态为true时，标记location为空并且interrupt线程
    }
}

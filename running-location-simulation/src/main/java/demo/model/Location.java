package demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Location {

    enum GpsStatus {
        EXCELLENT, OK, UNRELIABLE, BAD, NOFIX, UNKNOWN
    }

    public enum RunnerMovementType {
        STOPPED, IN_MOTION;

        public boolean isMoving() {
            return this!= STOPPED;
        }
    }

    private Long id;
    private final UnitInfo unitInfo;

    private MedicalInfo medicalInfo;
    private double latitude;
    private double longitude;
    private String heading;
    private double gpsSpeed;

    private GpsStatus gpsStatus;
    private double odometer;
    private double totalRunningTime;
    private double totalIdleTime;
    private double totalCalorieBurnt;
    private String address;
    private Date timestamp = new Date();
    private String gearProvider;
    private RunnerMovementType runnerMovementType;
    private String serviceType;


    private int heartRate = 60;

    public Location() {
        this.unitInfo = null;
    }

    @JsonCreator
    public Location(@JsonProperty("runningId") String runningId) {
        this.unitInfo = new UnitInfo(runningId);
    }
}

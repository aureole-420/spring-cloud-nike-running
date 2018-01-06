package demo.service.impl;

import demo.model.GpsSimulatorRequest;
import demo.service.GpsSimulatorFactory;
import demo.task.LocationSimulator;
import org.springframework.stereotype.Service;


// send REST request to distribution service
// can we use postman here? NO
// RestTemplate -- 所有以template结尾的都是spring提供的简便方法，比如jdbctemplate, amqpTemplate, JmsTemplate
// 利用template来发送rest请求。



@Service
public class DefaultGpsSimulatorFactory implements GpsSimulatorFactory{

    @Override
    public LocationSimulator prepareGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest) {

        LocationSimulator locationSimulator = new LocationSimulator(gpsSimulatorRequest);

        return null;
    }
}

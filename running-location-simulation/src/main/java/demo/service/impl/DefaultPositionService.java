package demo.service.impl;

import demo.model.CurrentPosition;
import demo.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/*
微服务的调用非常复杂，一个best practice是打log
 */
@Service
@Slf4j // 等同于 protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultPositionService.class);
public class DefaultPositionService implements PositionService {
    // protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultPositionService.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${com.ross.running.location.distribution}") // 用@Value来把值注入。
    private String runningLocationDistribution;

    @Override
    public void processPositionInfo(long id, CurrentPosition currentPosition, boolean sendPositionToDistributionService) {

//        String runningLocationDistribution = "http://localhost:9006"; // 不一定要hardcode

        if (sendPositionToDistributionService) {
            log.info(String.format("Thread %d Simulator is calling distribution REST API", Thread.currentThread().getId()));
            this.restTemplate.postForLocation(runningLocationDistribution+"/api/locatins", currentPosition);
        }
    }
}

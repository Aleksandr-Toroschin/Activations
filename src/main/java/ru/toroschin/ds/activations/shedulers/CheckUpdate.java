package ru.toroschin.ds.activations.shedulers;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.toroschin.ds.activations.services.ActivationService;

@Component
@AllArgsConstructor
public class CheckUpdate {
    private final ActivationService activationService;

    @Scheduled(fixedRate = 300*1000)
    public void reportCurrentTime() {
//        activationService.updateData();
    }
}

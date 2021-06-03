package ru.toroschin.ds.activations.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.toroschin.ds.activations.error_handling.ErrorMessage;
import ru.toroschin.ds.activations.models.Activation;
import ru.toroschin.ds.activations.dtos.ActivationDTO;
import ru.toroschin.ds.activations.dtos.ActivationGroupDTO;
import ru.toroschin.ds.activations.models.ActivationLK;
import ru.toroschin.ds.activations.services.ActivationService;
import ru.toroschin.ds.activations.util.RestHandler;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/activations")
@Slf4j
public class ActivationController {
    private final ActivationService activationService;
    private final RestHandler restHandler;

    @GetMapping
    public List<ActivationDTO> showAllActivations() {
        return activationService.findForThisMonth();
    }

    @GetMapping("/group")
    public Set<ActivationGroupDTO> showAllActivationsByGroup() {
        return activationService.findForThisMonthByGroup();
    }

    @GetMapping("/act")
    public List<Activation> showAllActivationsFirst() {
        return activationService.findAll();
    }

    @GetMapping("/pdf")
    public ErrorMessage savePDF() {
        return activationService.savePDF();
    }

    @GetMapping("/close")
    public void close() {
        System.exit(0);
    }

    @GetMapping("/check")
    public String checkUpdate() {
        return activationService.updateData();
    }

    @GetMapping("/lk")
    public String checkLK() {
        List<ActivationLK> activationLKS = activationService.findAllForLK();
        return restHandler.sendPOSTRequest(activationLKS);
    }

    @GetMapping("/test")
    public ResponseEntity<?> smsTest() {
        return new ResponseEntity<>(activationService.sendTestSms(), HttpStatus.ACCEPTED);
    }

}

package br.com.victor.realmeet.controller;

import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.service.AllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @PostMapping
    public ResponseEntity<AllocationResponse> createAllocation(@RequestBody AllocationRequest allocationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(allocationService.createAllocation(allocationRequest));
    }
}

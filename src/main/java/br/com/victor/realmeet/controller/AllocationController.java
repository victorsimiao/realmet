package br.com.victor.realmeet.controller;

import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.service.AllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }


    public ResponseEntity<AllocationResponse> createAllocation(AllocationRequest allocationRequest){
        return null;
    }
}

package br.com.victor.realmeet.controller;

import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.request.UpdateAllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.service.AllocationService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAllocatiojn(@Param("id")Long id, @RequestBody @Valid UpdateAllocationRequest updateAllocationRequest){
        allocationService.updateallocation(id,updateAllocationRequest);
        return ResponseEntity.noContent().build();
    }
}

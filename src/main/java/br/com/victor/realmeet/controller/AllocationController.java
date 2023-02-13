package br.com.victor.realmeet.controller;

import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.request.UpdateAllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.service.AllocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "allocations", description = "Endpoints para gerenciamento de agendamentos")
@RestController
@RequestMapping("/allocations")
public class AllocationController {

    private final AllocationService allocationService;

    public AllocationController(AllocationService allocationService) {
        this.allocationService = allocationService;
    }

    @Operation(summary = "Realizar agendamento", description = "Recurso para criar um agendamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "403", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<AllocationResponse> createAllocation(@RequestBody @Valid AllocationRequest allocationRequest, @RequestHeader(value = "api-key", required = true) String apiKey) {
        return ResponseEntity.status(HttpStatus.CREATED).body(allocationService.createAllocation(allocationRequest));
    }

    @Operation(summary = "Deletar agendamento", description = "Recurso para deletar um agendamento pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "403", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAllocation(@PathVariable Long id, @RequestHeader(value = "api-key", required = true) String apiKey) {
        allocationService.deleteAllocation(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar agendamento ", description = "Recurso para atualizar um agendamento pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "403", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAllocatiojn(@PathVariable("id") Long id, @RequestBody @Valid UpdateAllocationRequest updateAllocationRequest,
                                                  @RequestHeader(value = "api-key", required = true) String apiKey) {
        allocationService.updateallocation(id, updateAllocationRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar agendamentos", description = "Recurso para listar os agendamentos ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
            @ApiResponse(responseCode = "403", description = "Erro de autenticação"),
            @ApiResponse(responseCode = "422", description = "Dados inválidos fornecidos")
    })
    @GetMapping
    public ResponseEntity<List<AllocationResponse>> listAllocations(
            @RequestHeader(value = "api-key", required = true) String apiKey,
            @RequestParam(required = false) String employeeEmail,
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) LocalDate startAt,
            @RequestParam(required = false) LocalDate endAt,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer page) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(allocationService.listAllocations(employeeEmail, roomId, startAt, endAt, orderBy, limit, page));
    }
}

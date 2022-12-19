package br.com.victor.realmeet.service;

import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.AllocationRepository;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import br.com.victor.realmeet.mapper.AllocationMapper;
import br.com.victor.realmeet.validator.AllocationValidator;
import org.springframework.stereotype.Service;

@Service
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private final RoomRepository roomRepository;
    private final AllocationMapper allocationMapper;
    private final AllocationValidator allocationValidator;

    public AllocationService(AllocationRepository allocationRepository, RoomRepository roomRepository, AllocationMapper allocationMapper, AllocationValidator allocationValidator) {
        this.allocationRepository = allocationRepository;
        this.roomRepository = roomRepository;
        this.allocationMapper = allocationMapper;
        this.allocationValidator = allocationValidator;
    }


    public AllocationResponse createAllocation(AllocationRequest allocationRequest) {
        Room room = roomRepository.findById(allocationRequest.getRoomId()).orElseThrow(() -> new RoomNotFoundException("Room not found: " + allocationRequest.getRoomId()));
        allocationValidator.validate(allocationRequest);
        Allocation allocation = allocationMapper.fromAllocationRequestToEntity(allocationRequest, room);
        allocationRepository.save(allocation);
        return allocationMapper.fromEntityToDto(allocation);
    }
}

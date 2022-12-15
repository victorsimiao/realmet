package br.com.victor.realmeet.service;

import br.com.victor.realmeet.domain.repository.AllocationRepository;
import br.com.victor.realmeet.domain.repository.RoomRepository;
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
}

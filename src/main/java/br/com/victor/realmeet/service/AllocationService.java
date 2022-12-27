package br.com.victor.realmeet.service;

import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.AllocationRepository;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.AllocationRequest;
import br.com.victor.realmeet.dto.request.UpdateAllocationRequest;
import br.com.victor.realmeet.dto.response.AllocationResponse;
import br.com.victor.realmeet.exception.AllocationCannotBeDeletedException;
import br.com.victor.realmeet.exception.AllocationCannotBeUpdateException;
import br.com.victor.realmeet.exception.AllocationNotFoundException;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import br.com.victor.realmeet.mapper.AllocationMapper;
import br.com.victor.realmeet.validator.AllocationValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.victor.realmeet.util.DateUtils.now;

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

    public void deleteAllocation(Long id) {
        Allocation allocation = getAllocationOrThrow(id);

        if (isAllocationInThePast(allocation)) {
            throw new AllocationCannotBeDeletedException();
        }
        allocationRepository.delete(allocation);

    }

    @Transactional
    public void updateallocation(Long id, UpdateAllocationRequest updateAllocationRequest) {
        Allocation allocation = getAllocationOrThrow(id);

        if (isAllocationInThePast(allocation)) {
            throw new AllocationCannotBeUpdateException();
        }

        allocationValidator.validate(id,updateAllocationRequest);

        allocationRepository.updateAllocation(
                id,
                updateAllocationRequest.getSubject(),
                updateAllocationRequest.getStartAt(),
                updateAllocationRequest.getEndAt()
        );
    }

    private boolean isAllocationInThePast(Allocation allocation) {
        return allocation.getStartAt().isBefore(now());
    }
    private Allocation getAllocationOrThrow(Long id) {
        return allocationRepository.findById(id).orElseThrow(() -> new AllocationNotFoundException("Allocation not found: " + id));
    }
}

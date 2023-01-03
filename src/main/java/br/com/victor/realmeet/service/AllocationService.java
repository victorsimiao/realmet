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
import br.com.victor.realmeet.util.PageUtils;
import br.com.victor.realmeet.validator.AllocationValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.victor.realmeet.domain.entity.Allocation.SORTABLE_FIELDS;
import static br.com.victor.realmeet.util.Constants.ALLOCATION_MAX_FILTER_LIMIT;
import static br.com.victor.realmeet.util.DateUtils.DEFAULT_TIMEZONE;
import static br.com.victor.realmeet.util.DateUtils.now;
import static java.util.Objects.isNull;

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

        allocationValidator.validate(id, allocation.getRoom().getId(), updateAllocationRequest);

        allocationRepository.updateAllocation(
                id,
                updateAllocationRequest.getSubject(),
                updateAllocationRequest.getStartAt(),
                updateAllocationRequest.getEndAt()
        );
    }

    public List<AllocationResponse> listAllocations(String employeeEmail, Long roomId, LocalDate startAt, LocalDate endAt, String orderBy, Integer limit, Integer page) {
        Pageable pageable = PageUtils.newPageable(page, limit, ALLOCATION_MAX_FILTER_LIMIT, orderBy, SORTABLE_FIELDS);

        Page<Allocation> allocationList = allocationRepository.findAllWithfilters(
                employeeEmail,
                roomId,
                isNull(startAt) ? null : startAt.atTime(LocalTime.MIN).atOffset(DEFAULT_TIMEZONE),
                isNull(endAt) ? null : endAt.atTime(LocalTime.MAX).atOffset(DEFAULT_TIMEZONE),
                pageable
        );

        return allocationList.stream().map(allocation -> allocationMapper.fromEntityToDto(allocation)).collect(Collectors.toList());
    }

    private boolean isAllocationInThePast(Allocation allocation) {
        return allocation.getStartAt().isBefore(now());
    }

    private Allocation getAllocationOrThrow(Long id) {
        return allocationRepository.findById(id).orElseThrow(() -> new AllocationNotFoundException("Allocation not found: " + id));
    }
}

package br.com.victor.realmeet.unit;

import br.com.victor.realmeet.core.BaseUnitTest;
import br.com.victor.realmeet.domain.entity.Allocation;
import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.AllocationRepository;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.RoomRequest;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.exception.AllocationCannotBeDeletedException;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import br.com.victor.realmeet.service.AllocationService;
import br.com.victor.realmeet.service.RoomService;
import br.com.victor.realmeet.util.DateUtils;
import br.com.victor.realmeet.utils.MapperUtils;
import br.com.victor.realmeet.utils.TestConstants;
import br.com.victor.realmeet.utils.TestDataCreator;
import br.com.victor.realmeet.validator.AllocationValidator;
import br.com.victor.realmeet.validator.RoomValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AllocationServiceUnitTest extends BaseUnitTest {

    private AllocationService allocationService;

    @Mock
    private AllocationRepository allocationRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private AllocationValidator allocationValidator;

    @BeforeEach
    public void setupEach() {
        allocationService = new AllocationService(allocationRepository, roomRepository, MapperUtils.newAllocationMapper(), allocationValidator);
    }

    @Test
    void deleteAllocationWithSuccess() {
        Room room = TestDataCreator.newRoomBuilder().build();
        Allocation allocation = TestDataCreator.newAllocationBuilder(room).build();
        Mockito.when(allocationRepository.findById(allocation.getId())).thenReturn(Optional.of(allocation));

        allocationService.deleteAllocation(allocation.getId());
    }

    @Test
    void shouldNotDeleteAllocationInThePast() {
        Room room = TestDataCreator.newRoomBuilder().build();
        Allocation allocation = TestDataCreator.newAllocationBuilder(room).startAt(DateUtils.now().plusHours(1)).endAt(DateUtils.now().minusMinutes(10)).build();
        Mockito.when(allocationRepository.findById(allocation.getId())).thenReturn(Optional.of(allocation));

        assertThrows(AllocationCannotBeDeletedException.class, () -> allocationService.deleteAllocation(allocation.getId()));
    }

}

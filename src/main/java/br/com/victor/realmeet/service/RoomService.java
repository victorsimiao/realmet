package br.com.victor.realmeet.service;

import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.RoomRequest;
import br.com.victor.realmeet.dto.request.UpdateRoomRequest;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import br.com.victor.realmeet.mapper.RoomMapper;
import br.com.victor.realmeet.util.PageUtils;
import br.com.victor.realmeet.validator.RoomValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.victor.realmeet.util.Constants.ROOM_MAX_FILTER_LIMIT;
import static java.util.Objects.requireNonNull;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final RoomValidator roomValidator;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, RoomValidator roomValidator) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomValidator = roomValidator;
    }

    public RoomResponse getRoom(Long id) {
        Room room = getActiveRoomOrThrow(id);
        return roomMapper.fromEntityToDto(room);
    }

    public RoomResponse createRoom(RoomRequest roomRequest) {
        roomValidator.validate(roomRequest);
        Room room = roomMapper.fromDtoForEntity(roomRequest);
        roomRepository.save(room);
        return roomMapper.fromEntityToDto(room);
    }

    @Transactional
    public void deleteRoom(Long id) {
        getActiveRoomOrThrow(id);
        roomRepository.deactivate(id);
    }

    @Transactional
    public void updateRoom(Long id, UpdateRoomRequest updateRoomRequest) {
        roomValidator.validate(updateRoomRequest);
        getActiveRoomOrThrow(id);
        roomRepository.updateRoom(id, updateRoomRequest.getName(), updateRoomRequest.getSeats());
    }

    public List<RoomResponse> listRooms(String name, Boolean active, String oderBy, Integer limit, Integer page) {
        Pageable pageable = PageUtils.newPageable(page, limit, ROOM_MAX_FILTER_LIMIT, oderBy, Room.SORTABLE_FIELDS);
        Page<Room> rooms = roomRepository.findAllWinthFilter(name, active, pageable);
        return rooms.stream().map(room -> roomMapper.fromEntityToDto(room)).collect(Collectors.toList());
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return roomRepository.findByIdAndActive(id, true).
                orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
    }
}

package br.com.victor.realmeet.service;

import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.request.RoomRequest;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import br.com.victor.realmeet.mapper.RoomMapper;
import br.com.victor.realmeet.validator.RoomValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return roomMapper.fromEntityForDto(room);
    }

    public RoomResponse createRoom(RoomRequest roomRequest) {
        roomValidator.validate(roomRequest);
        Room room = roomMapper.fromDtoForEntity(roomRequest);
        roomRepository.save(room);
        return roomMapper.fromEntityForDto(room);
    }

    @Transactional
    public void deleteRoom(Long id) {
        getActiveRoomOrThrow(id);
        roomRepository.deactivate(id);
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return roomRepository.findByIdAndActive(id, true).
                orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
    }
}

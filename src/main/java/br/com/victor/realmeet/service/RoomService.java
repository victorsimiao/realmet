package br.com.victor.realmeet.service;

import br.com.victor.realmeet.domain.entity.Room;
import br.com.victor.realmeet.domain.repository.RoomRepository;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.exception.RoomNotFoundException;
import br.com.victor.realmeet.mapper.RoomMapper;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomResponse getRoom(Long id) {
        requireNonNull(id);
        Room room = roomRepository.findByIdAndActive(id, true).orElseThrow(() -> new RoomNotFoundException("Room not found: " + id));
        return roomMapper.fromEntityForDto(room);
    }
}

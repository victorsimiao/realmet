package br.com.victor.realmeet.controller;

import br.com.victor.realmeet.dto.request.RoomRequest;
import br.com.victor.realmeet.dto.request.UpdateRoomRequest;
import br.com.victor.realmeet.dto.response.RoomResponse;
import br.com.victor.realmeet.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest roomRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(roomRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable(value = "id") Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable(value = "id") Long id, @Valid @RequestBody UpdateRoomRequest updateRoomRequest) {
        roomService.updateRoom(id, updateRoomRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package com.digitalinnovation.salareuniao.controller;

import com.digitalinnovation.salareuniao.exception.ResourceNotFoundException;
import com.digitalinnovation.salareuniao.model.Room;
import com.digitalinnovation.salareuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping("/rooms")
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@RequestBody @Valid Room room) {
        return roomRepository.save(room);
    }

    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public Room getRoomById(@PathVariable Long id) throws ResourceNotFoundException {
        return roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found:: " + id));
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody @Valid Room room) throws ResourceNotFoundException {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found:: " + id));
        return ResponseEntity.ok().body(roomRepository.save(room));
    }

    @DeleteMapping("/rooms/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Map<String, Boolean> deleteById(@PathVariable Long id) throws ResourceNotFoundException {
        roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room not found:: " + id));
        roomRepository.deleteById(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

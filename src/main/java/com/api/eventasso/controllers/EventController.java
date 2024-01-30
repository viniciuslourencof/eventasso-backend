package com.api.eventasso.controllers;

import com.api.eventasso.dtos.EventDto;
import com.api.eventasso.models.EventModel;
import com.api.eventasso.services.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/event")
public class EventController {

    final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<Object> saveEvent(@RequestBody @Valid com.api.eventasso.dtos.EventDto eventDto){

        var eventModel = new EventModel();
        BeanUtils.copyProperties(eventDto, eventModel);
        eventModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.save(eventModel));
    }

    @GetMapping
    public ResponseEntity<Page<com.api.eventasso.models.EventModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEvent(@PathVariable(value = "id") UUID id){
        Optional<com.api.eventasso.models.EventModel> eventModelOptional = eventService.findById(id);
        if (!eventModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(eventModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvent(@PathVariable(value = "id") UUID id){
        Optional<com.api.eventasso.models.EventModel> eventModelOptional = eventService.findById(id);
        if (!eventModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }
        eventService.delete(eventModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Event deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEvent(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid com.api.eventasso.dtos.EventDto eventDto){
        Optional<com.api.eventasso.models.EventModel> eventModelOptional = eventService.findById(id);
        if (!eventModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }
        var eventModel = new com.api.eventasso.models.EventModel();
        BeanUtils.copyProperties(eventDto, eventModel);
        eventModel.setId(eventModelOptional.get().getId());
        eventModel.setRegistrationDate(eventModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(eventService.save(eventModel));
    }



}

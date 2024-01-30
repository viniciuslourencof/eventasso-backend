package com.api.eventasso.services;

import com.api.eventasso.models.EventModel;
import com.api.eventasso.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EventModel save(EventModel eventModel) {
        return eventRepository.save(eventModel);
    }

    public Page<EventModel> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Optional<EventModel> findById(UUID id) {
        return eventRepository.findById(id);
    }

    @Transactional
    public void delete(EventModel eventModel) {
        eventRepository.delete(eventModel);
    }
}

package com.encantadaslash.javabackend.model;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class Evento {
    private String description;
    private EventDateTime start;
    private EventDateTime end;

    public Evento(Event event) {
        this.description = event.getDescription();
        this.start = event.getStart();
        this.end = event.getEnd();
    }
}

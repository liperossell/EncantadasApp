package com.encantadaslash.javabackend.controllers;

import com.encantadaslash.javabackend.exception.BusinessException;
import com.encantadaslash.javabackend.model.Calendario;
import com.google.api.client.util.DateTime;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import com.google.api.services.calendar.model.Event;
@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {
    @PostMapping
    public String saveEvent(@RequestBody Calendario calendario) throws BusinessException, IOException {
        return calendario.salvarEvento();
    }

    @GetMapping("list")
    public List<Event> list(@RequestHeader String dateTime) {
        ZonedDateTime zdt = ZonedDateTime.parse(dateTime);
        DateTime dateTime1 = new DateTime(zdt.toEpochSecond() * 1000L);
        Calendario calendario = new Calendario();
        calendario.setDateTime(dateTime1);
        return calendario.listarEventos();
    }
}

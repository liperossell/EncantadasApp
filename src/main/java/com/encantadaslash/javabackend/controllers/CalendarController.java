package com.encantadaslash.javabackend.controllers;

import com.encantadaslash.javabackend.model.Calendario;
import com.encantadaslash.javabackend.exception.BusinessException;
import com.google.api.client.util.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "http://localhost:4200/")
public class CalendarController {

    @GetMapping("list")
    public Calendario printCalendar(@RequestHeader String dateTime) {
        ZonedDateTime zdt = ZonedDateTime.parse(dateTime);
        DateTime dateTime1 = new DateTime(zdt.toEpochSecond() * 1000L);
        Calendario calendario = new Calendario();
        calendario.setDateTime(dateTime1);
        return calendario.listarEventos();
    }

    @PostMapping("/event")
    public String saveEvent(@RequestBody Calendario calendario) throws BusinessException, IOException {
        return calendario.salvarEvento();
    }
}

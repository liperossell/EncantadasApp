package com.encantadaslash.javabackend.model;

import com.encantadaslash.javabackend.beans.CalendarFactory;
import com.encantadaslash.javabackend.exception.BusinessException;
import com.encantadaslash.javabackend.util.DateTimeUtil;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class Calendario {
    public static final String CALENDAR_ID = "f6402d938e626ee512cace507d28acc8ee545f533bd5b25cbd1685fae38d99d3@group.calendar.google.com";
    private final Calendar service;
    private DateTime dateTime;
    private List<Event> items;

    public Calendario() {
        this.service = CalendarFactory.INSTANCE();
    }

    public List<Event> listarEventos() {
        try {
            this.items = getEventsOfTheDay().getItems();
            return this.items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Events getEventsOfTheDay() throws IOException {
        return getPrimaryCalendarEvents().setTimeMax(getTimeMax()).setTimeMin(getTimeMin()).setOrderBy("startTime").setSingleEvents(true).execute();
    }

    private DateTime getTimeMin() {
        return DateTimeUtil.atStartOfDay(dateTime);
    }

    private DateTime getTimeMax() {
        return DateTimeUtil.atEndOfDay(dateTime);
    }

    private Calendar.Events.List getPrimaryCalendarEvents() throws IOException {
        return getEvents().list(CALENDAR_ID);
    }

    private Calendar.Events getEvents() {
        return this.service.events();
    }

    public String salvarEvento() throws IOException, BusinessException {
        if (existEvent()) {
            throw new BusinessException("Já existe um evento nesse horário");
        }
        Event event = createEvent();
        return String.format("Event created: %s\n", event.getHtmlLink());
    }

    private boolean existEvent() throws IOException {
        return getEventsOfTheDay().getItems().stream().anyMatch(it -> this.dateTime.getValue() >= it.getStart().getDateTime().getValue() && this.dateTime.getValue() <= it.getEnd().getDateTime().getValue());
    }

    private Event createEvent() throws IOException {
        Event event = new Event().setSummary("Novo agendamento").setLocation("Rua Doralice Ramos Pinho, 664. Jardim Cidade Florianópolis, São José-SC").setDescription("Isso é um teste");
        EventDateTime start = getStartEventDateTime();
        EventDateTime end = getEndEventDateTime();
        event.setStart(start);
        event.setEnd(end);
        getEvents().insert(CALENDAR_ID, event).execute();

        return event;
    }

    private EventDateTime getEndEventDateTime() {
        return new EventDateTime().setDateTime(DateTimeUtil.addHours(dateTime, 2)).setTimeZone("America/Sao_Paulo");
    }

    private EventDateTime getStartEventDateTime() {
        return new EventDateTime().setDateTime(dateTime).setTimeZone("America/Sao_Paulo");
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public List<Event> getItems() {
        return items;
    }

    public void setItems(List<Event> items) {
        this.items = items;
    }
}

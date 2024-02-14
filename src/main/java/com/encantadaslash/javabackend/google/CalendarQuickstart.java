package com.encantadaslash.javabackend.google;

import com.encantadaslash.javabackend.beans.CalendarFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.List;

/* class to demonstrate use of Calendar events list API */
public class CalendarQuickstart {

    public static void main(String... args) throws IOException {
        listEvents();
    }

    private static void insertCalendar() throws IOException {
        Calendar service = CalendarFactory.INSTANCE();
        // List the next 10 events from the primary calendar.
//        listEvents(service);
        Event event = new Event().setSummary("Novo agendamento").setLocation("Rua Doralice Ramos Pinho, 664. Jardim Cidade Florianópolis, São José-SC").setDescription("Isso é um teste");
        EventDateTime start = new EventDateTime().setDateTime(new DateTime("2024-01-30T14:00:00-03:00")).setTimeZone("America/Sao_Paulo");
        EventDateTime end = new EventDateTime().setDateTime(new DateTime("2024-01-30T16:00:00-03:00")).setTimeZone("America/Sao_Paulo");
        event.setStart(start);
        event.setEnd(end);
        event = service.events().insert("primary", event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }

    private static void listEvents() throws IOException {
        Calendar service = CalendarFactory.INSTANCE();
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }
}
package at.fhv.itb17.s5.teamb.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ticketId;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Client client;
    @Enumerated
    private TicketStates state;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private Event bookedEvent;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private EventOccurrence bookedOccurrence;
    @ManyToOne(cascade = {CascadeType.MERGE})
    private EventCategory bookedCategory;
    @ManyToOne(cascade = {CascadeType.ALL})
    private LocationRow bookedRow;
    @ManyToOne(cascade = {CascadeType.ALL})
    private LocationSeat bookedSeat;

    public Ticket(Client client, TicketStates state, Event bookedEvent, EventOccurrence bookedOccurrence, EventCategory bookedCategory, LocationRow bookedRow, LocationSeat bookedSeat) {
        this.client = client;
        this.state = state;
        this.bookedEvent = bookedEvent;
        this.bookedOccurrence = bookedOccurrence;
        this.bookedCategory = bookedCategory;
        this.bookedRow = bookedRow;
        this.bookedSeat = bookedSeat;
    }

    public Ticket(Client client, TicketStates state, Event bookedEvent, EventOccurrence bookedOccurrence, EventCategory bookedCategory) {
        this.client = client;
        this.state = state;
        this.bookedEvent = bookedEvent;
        this.bookedOccurrence = bookedOccurrence;
        this.bookedCategory = bookedCategory;
        this.bookedRow = null;
        this.bookedSeat = null;
    }

    public Ticket() {
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TicketStates getState() {
        return state;
    }

    public void setState(TicketStates state) {
        this.state = state;
    }

    public Event getBookedEvent() {
        return bookedEvent;
    }

    public void setBookedEvent(Event bookedEvent) {
        this.bookedEvent = bookedEvent;
    }

    public EventOccurrence getBookedOccurrence() {
        return bookedOccurrence;
    }

    public void setBookedOccurrence(EventOccurrence bookedOccurrence) {
        this.bookedOccurrence = bookedOccurrence;
    }

    public EventCategory getBookedCategory() {
        return bookedCategory;
    }

    public void setBookedCategory(EventCategory bookedCategory) {
        this.bookedCategory = bookedCategory;
    }

    public LocationRow getBookedRow() {
        return bookedRow;
    }

    public void setBookedRow(LocationRow bookedRow) {
        this.bookedRow = bookedRow;
    }

    public LocationSeat getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(LocationSeat bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", client=" + client +
                ", state=" + state +
                ", bookedEvent=" + bookedEvent +
                ", bookedOccurrence=" + bookedOccurrence +
                ", bookedCategory=" + bookedCategory +
                ", bookedRow=" + bookedRow +
                ", bookedSeat=" + bookedSeat +
                "}@" + Integer.toHexString(hashCode());
    }

    public boolean isSame(Ticket that) {
        boolean b = (Objects.equals(client.getUsername(), that.client.getUsername())) && (bookedEvent.getEventId().equals(that.bookedEvent.getEventId()))
                && (bookedOccurrence.getOccurrenceId().equals(that.getBookedOccurrence().getOccurrenceId()))
                && (bookedCategory.getEventCategoryId().equals(that.getBookedCategory().getEventCategoryId()));
        if (bookedRow != null && that.bookedRow != null && bookedSeat != null && that.bookedSeat != null) {
            b = b && (bookedRow.getRowId().equals(that.bookedRow.getRowId())) && (bookedSeat.getSeatId().equals(that.bookedSeat.getSeatId()));
        }
        return b;

    }
}

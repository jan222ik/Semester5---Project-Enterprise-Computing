package at.fhv.itb17.s5.teamb.dtos;

import java.io.Serializable;

public class TicketDTO implements Serializable {
    private Long id;
    private EventDTO eventDTO;
    private EvOccurrenceDTO occ;
    private EvCategoryInterfaceDTO cat;
    private LocationRowDTO row;
    private LocationSeatDTO seat;

    public TicketDTO(Long id, EventDTO eventDTO, EvOccurrenceDTO occ, EvCategoryInterfaceDTO cat, LocationRowDTO row, LocationSeatDTO seat) {
        this.id = id;
        this.eventDTO = eventDTO;
        this.occ = occ;
        this.cat = cat;
        this.row = row;
        this.seat = seat;
    }

    public TicketDTO(EventDTO eventDTO, EvOccurrenceDTO occ, EvCategorySeatsDTO cat, LocationRowDTO row, LocationSeatDTO seat) {
        this.eventDTO = eventDTO;
        this.occ = occ;
        this.cat = cat;
        this.row = row;
        this.seat = seat;
    }

    public TicketDTO(EventDTO eventDTO, EvOccurrenceDTO occ, EvCategoryFreeDTO cat) {
        this.eventDTO = eventDTO;
        this.occ = occ;
        this.cat = cat;
        this.seat = null;
        this.row = null;
    }

    public Long getId() {
        return id;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public EvOccurrenceDTO getOcc() {
        return occ;
    }

    public EvCategoryInterfaceDTO getCat() {
        return cat;
    }

    public LocationRowDTO getRow() {
        return row;
    }

    public LocationSeatDTO getSeat() {
        return seat;
    }

    public boolean valueEqual(TicketDTO dto) {
        if (!this.eventDTO.getEventId().equals(dto.eventDTO.getEventId())) {
            return false;
        } else {
            if (!this.occ.getOccurrenceId().equals(dto.occ.getOccurrenceId())) {
                return false;
            } else {
                if (!this.cat.getEventCategoryId().equals(dto.cat.getEventCategoryId())) {
                    return false;
                } else {
                    if (this.row != null) {
                        if (!this.row.getRowId().equals(dto.row.getRowId())) {
                            return false;
                        } else {
                            return this.seat.getSeatId().equals(dto.seat.getSeatId());
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
                "id=" + id +
                ", eventDTO=" + eventDTO +
                ", occ=" + occ +
                ", cat=" + cat +
                ", row=" + row +
                ", seat=" + seat +
                '}';
    }
}

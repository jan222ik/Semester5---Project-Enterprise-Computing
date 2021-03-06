package at.fhv.itb17.s5.teamb.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class EvCategorySeatsDTO implements EvCategoryInterfaceDTO, Serializable {

    @JsonProperty("eventCategoryId")
    private Long eventCategoryId;
    @JsonProperty("categoryName")
    private String categoryName;
    @JsonProperty("priceInCents")
    private int priceInCent;
    @JsonProperty("rows")
    private List<LocationRowDTO> seatingRows;


    public EvCategorySeatsDTO(Long eventCategoryId, String categoryName, int priceInCent, List<LocationRowDTO> seatingRows) {
        this.eventCategoryId = eventCategoryId;
        this.categoryName = categoryName;
        this.priceInCent = priceInCent;
        this.seatingRows = seatingRows;
    }

    public Long getEventCategoryId() {
        return eventCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public int getTotalTickets() {
        return getSeatingRows().stream().mapToInt(e -> e.getSeats().size()).sum();
    }

    @Override
    public int getUsedTickets() {
        return (int) getSeatingRows().stream().flatMap(e -> e.getSeats().stream()).filter(seatDTO -> !seatDTO.isFree()).count();
    }

    public int getPriceInCent() {
        return priceInCent;
    }

    public List<LocationRowDTO> getSeatingRows() {
        return seatingRows;
    }
}

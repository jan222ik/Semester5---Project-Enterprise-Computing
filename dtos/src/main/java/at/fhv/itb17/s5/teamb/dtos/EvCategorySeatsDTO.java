package at.fhv.itb17.s5.teamb.dtos;

import java.util.List;

public class EvCategorySeatsDTO implements EvCategoryInterface {
    private Long eventCategoryId;
    private String categoryName;
    private int priceInCent;
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
        //TODO CALC RIGHT VALUE
        return getSeatingRows().stream().mapToInt(e -> e.getSeats().size()).sum();
    }

    public int getPriceInCent() {
        return priceInCent;
    }

    public List<LocationRowDTO> getSeatingRows() {
        return seatingRows;
    }
}
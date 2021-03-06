package at.fhv.itb17.s5.teamb.core.domain.search;

import at.fhv.itb17.s5.teamb.persistence.entities.Event;
import at.fhv.itb17.s5.teamb.persistence.entities.EventOccurrence;
import at.fhv.itb17.s5.teamb.persistence.repository.EventRepository;
import at.fhv.itb17.s5.teamb.persistence.search.Search;
import at.fhv.itb17.s5.teamb.persistence.search.SearchCategories;
import at.fhv.itb17.s5.teamb.persistence.search.SearchPair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchServiceCoreImpl implements SearchServiceCore {

    private static final Logger logger = LogManager.getLogger(SearchServiceCoreImpl.class);

    private EventRepository eventRepository;
    private SearchParser searchParser = SearchParser.INSTANCE;

    public SearchServiceCoreImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> searchFor(String queryString) {
        Search search = searchParser.parseString(queryString);//|| sp.getKey() == SearchCategories.LOCATION
        List<SearchPair> searchPairs = (search != null) ? search.retrieveSearchPairs().stream().filter(sp -> sp.getKey() == SearchCategories.EVENT_NAME || sp.getKey() == SearchCategories.GENRE || sp.getKey() == SearchCategories.ARTIST_NAME).collect(Collectors.toList()) : new LinkedList<>();
        List<Event> result = eventRepository.search(searchPairs);
        logger.info("result = {}", result.size());
        LinkedList<SearchPair> searchPairs1 = (search != null) ? search.retrieveSearchPairs() : new LinkedList<>();
        return filter(result, searchPairs1);
    }

    @NotNull
    private List<Event> filter(List<Event> all, @NotNull List<SearchPair> pairs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate fromDate = null;
        LocalDate untilDate = null;
        String artistName = null;
        String location = null;
        for (SearchPair pair : pairs) {
            switch (pair.getKey()) {
                case DATE_FROM:
                    fromDate = LocalDate.parse(pair.getValue(), formatter);
                    break;
                case DATE_UNTIL:
                    untilDate = LocalDate.parse(pair.getValue(), formatter);
                    break;
                case LOCATION:
                    location = pair.getValue();
                    break;
                case GENRE:
                case ARTIST_NAME:
                case EVENT_NAME:

            }
        }
        LinkedList<Event> modResult = new LinkedList<>();
        for (Event event : all) {
            Boolean toAdd = null;
            if (artistName != null) {
                String finalArtistName = artistName;
                toAdd = event.getArtists().stream().anyMatch(e -> e.getName().contains(finalArtistName));
            }
            if (toAdd == null || toAdd) {
                List<EventOccurrence> eventOccurrences = filterOccurrences(event.getOccurrences(), fromDate, untilDate, location);
                if (!eventOccurrences.isEmpty()) {
                    event.setOccurrences(eventOccurrences);
                    modResult.add(event);
                }
            }
        }
        return modResult;
    }

    @NotNull
    private List<EventOccurrence> filterOccurrences(@NotNull List<EventOccurrence> occurrences, LocalDate fromDate, LocalDate untilDate, String location) {
        List<EventOccurrence> result = new LinkedList<>();
        Boolean toAdd = null;
        for (EventOccurrence occurrence : occurrences) {
            if (fromDate != null && canAdd(toAdd)) {
                toAdd = occurrence.getDate().isAfter(fromDate);
            }
            if (untilDate != null && canAdd(toAdd)) {
                toAdd = occurrence.getDate().isBefore(untilDate);
            }
            if (location != null && canAdd(toAdd)) {
                toAdd = occurrence.getAddress().asComparableString().contains(location);
            }
            if (toAdd == null || toAdd) {
                result.add(occurrence);
            }
        }
        return result;
    }

    @Contract(value = "null -> true; !null -> param1", pure = true)
    private boolean canAdd(Boolean bool) {
        if (bool == null) {
            return true;
        } else {
            return bool;
        }
    }


}

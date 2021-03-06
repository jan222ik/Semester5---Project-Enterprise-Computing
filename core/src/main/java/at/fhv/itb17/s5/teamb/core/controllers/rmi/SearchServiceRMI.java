package at.fhv.itb17.s5.teamb.core.controllers.rmi;

import at.fhv.itb17.s5.teamb.core.controllers.general.EntityDTORepo;
import at.fhv.itb17.s5.teamb.core.controllers.general.SearchService;
import at.fhv.itb17.s5.teamb.core.domain.search.SearchServiceCore;
import at.fhv.itb17.s5.teamb.dtos.EventDTO;
import at.fhv.itb17.s5.teamb.persistence.entities.Event;
import at.fhv.itb17.s5.teamb.util.LogMarkers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings({"squid:S2160", "squid:S1948"})
public class SearchServiceRMI extends UnicastRemoteObject implements SearchService {

    private static final Logger logger = LogManager.getLogger(SearchServiceRMI.class);
    private SearchServiceCore coreSearch;
    private EntityDTORepo entityDTORepo;

    public SearchServiceRMI(SearchServiceCore searchServiceCore, EntityDTORepo entityDTORepo) throws RemoteException {
        this.coreSearch = searchServiceCore;
        this.entityDTORepo = entityDTORepo;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public LinkedList<EventDTO> searchFor(String queryString) throws RemoteException {
        logger.debug(LogMarkers.RMI_CONTROLLER, "Invoked SearchString: {}", queryString);
        List<Event> events = coreSearch.searchFor(queryString);
        return new LinkedList<>(entityDTORepo.toEventDTOs(events));
    }
}

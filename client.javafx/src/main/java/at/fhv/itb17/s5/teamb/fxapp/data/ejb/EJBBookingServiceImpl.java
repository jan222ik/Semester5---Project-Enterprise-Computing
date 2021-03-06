package at.fhv.itb17.s5.teamb.fxapp.data.ejb;

import at.fhv.itb17.s5.teamb.core.controllers.general.FrontEndClient;
import at.fhv.itb17.s5.teamb.core.controllers.general.IFrontEndClient;
import at.fhv.itb17.s5.teamb.dtos.TicketDTO;
import at.fhv.itb17.s5.teamb.fxapp.data.BookingService;
import at.fhv.itb17.s5.teamb.fxapp.data.rmi.RMIConnectionStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.naming.NamingException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class EJBBookingServiceImpl implements BookingService {
    private static final Logger logger = LogManager.getLogger(EJBBookingServiceImpl.class);
    private EJBController ejb;
    private static final String EJB_EXCEPTION = "EJB Exception";
    private IFrontEndClient client;
    private at.fhv.itb17.s5.teamb.core.controllers.general.BookingService remoteBookingService;

    @SuppressWarnings({"RedundantThrows", "squid:RedundantThrowsDeclarationCheck"})
    public EJBBookingServiceImpl(EJBController ejb) throws RemoteException {
        this.ejb = ejb;
    }


    @Override
    public RMIConnectionStatus doLoginBooking(String username, String password) {
        try {
            client = new FrontEndClient();
            remoteBookingService = ejb.createBookingService(client, username, password);
            if (remoteBookingService != null) {
                return RMIConnectionStatus.CONNECTED;
            } else {
                return RMIConnectionStatus.CREDENTIALS_INVALID;
            }
        } catch (NamingException | RemoteException e) {
            e.printStackTrace();
            logger.error(EJB_EXCEPTION);
        }
        return RMIConnectionStatus.NO_CONNECTION;
    }

    @Override
    public void logout() {
        remoteBookingService = null;
        try {
            UnicastRemoteObject.unexportObject(client, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Nullable
    @SuppressWarnings("squid:S1168") //Null instead of empty list
    public List<TicketDTO> book(List<TicketDTO> ticketDTOs) {
        logger.info("Booking {} tickets", ticketDTOs.size());
        if (remoteBookingService != null) {
            try {
                List<TicketDTO> bookedTickets = remoteBookingService.bookTickets(ticketDTOs);
                if(bookedTickets == null || bookedTickets.isEmpty()){
                    if(bookedTickets != null) {
                        bookedTickets.forEach(ticketDTOs::remove);
                    }
                    bookedTickets = remoteBookingService.bookTickets(ticketDTOs);
                }
                return bookedTickets;
            } catch (RemoteException e) {
                e.printStackTrace();
                logger.error(EJB_EXCEPTION);
                return null;
            }
        } else {
            logger.fatal("Not logged in! - No Service");
            return null;
        }
    }

    @Override
    @Nullable
    @SuppressWarnings("squid:S1168") //Null instead of empty list
    public List<TicketDTO> reserve(List<TicketDTO> ticketDTOs) {
        logger.info("Reserve {} tickets", ticketDTOs.size());
        if (remoteBookingService != null) {
            try {
                return remoteBookingService.reserveTickets(ticketDTOs);
            } catch (RemoteException e) {
                e.printStackTrace();
                logger.error(EJB_EXCEPTION);
                return null;
            }
        } else {
            logger.fatal("Not logged in! - No Service");
            return null;
        }
    }
}

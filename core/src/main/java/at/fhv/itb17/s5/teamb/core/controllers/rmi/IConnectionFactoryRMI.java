package at.fhv.itb17.s5.teamb.core.controllers.rmi;

import at.fhv.itb17.s5.teamb.core.controllers.general.BookingService;
import at.fhv.itb17.s5.teamb.core.controllers.general.IFrontEndClient;
import at.fhv.itb17.s5.teamb.core.controllers.general.MsgTopicService;
import at.fhv.itb17.s5.teamb.core.controllers.general.SearchService;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConnectionFactoryRMI extends Remote {
    SearchService createSearchService() throws RemoteException;

    BookingService createBookingService(IFrontEndClient feClient, String username, String password) throws RemoteException;

    MsgTopicService createTopicService(String username, String password) throws RemoteException;
}

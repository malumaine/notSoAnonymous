import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    void registerClient(ChatInterface client) throws RemoteException;
    void sendMessage(ChatMessage message) throws RemoteException;
}

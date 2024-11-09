import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {
    private List<ChatInterface> clients;

    public ChatServer() throws RemoteException {
        clients = new ArrayList<>();
    }

    @Override
    public void registerClient(ChatInterface client) throws RemoteException {
        clients.add(client);
        System.out.println("New client registered: " + client);
    }

    @Override
    public void sendMessage(ChatMessage message) throws RemoteException {
        
        broadcastMessage(message);
    }

    private void broadcastMessage(ChatMessage message) {
        for (ChatInterface client : clients) {
            try {
                client.sendMessage(message);
            } catch (RemoteException e) {
                System.err.println("Error sending message to a client:");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            ChatServer server = new ChatServer();
            Naming.rebind("//localhost/ChatServer", server);
            System.out.println("Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

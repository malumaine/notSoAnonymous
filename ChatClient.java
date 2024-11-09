import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClient extends UnicastRemoteObject implements ChatInterface {
    private String name;
    private ChatInterface server;
    private ChatUI chatUI;

    public ChatClient(String name, ChatUI chatUI) throws RemoteException {
        this.name = name;
        this.chatUI = chatUI; // Enregistrez la référence à l'instance de ChatUI
        try {
            server = (ChatInterface) Naming.lookup("//localhost/ChatServer");
            server.registerClient(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startChat() {
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter message (type 'exit' to quit): ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setSender(name);
                chatMessage.setContent("[" + name + "]: " + message);

                server.sendMessage(chatMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(ChatMessage message) throws RemoteException {
        // Appelez la méthode appendToChat de ChatUI pour ajouter le message reçu à l'interface graphique
        chatUI.appendToChat(message.getSender() + ": " + message.getContent());
    }

    public void registerClient(ChatInterface client) throws RemoteException {
        // Cette méthode n'est pas utilisée dans le client
    }

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java ChatClient <username>");
                return;
            }
            String username = args[0];
            ChatUI chatUI = new ChatUI(username);
            ChatClient client = new ChatClient(username, chatUI);
            client.startChat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

public class ChatUI extends JFrame implements ActionListener, ChatInterface {
    private JTextArea chatArea;
    private JTextField inputField;
    private ChatInterface server;
    private String name;
    JLabel retourl;
    class RetourMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == retourl) {
                ChatUI.this.dispose(); // Utilisation de ChatUI.this.dispose() pour accéder à la méthode dispose() de la classe ChatUI
                new Launcher();
            }
        }
        

@Override
public void mousePressed(MouseEvent e) {
   
}

@Override
public void mouseReleased(MouseEvent e) {
    
}

@Override
public void mouseEntered(MouseEvent e) {

   
}

@Override
public void mouseExited(MouseEvent e) {
   
  
}
    }

    public ChatUI(String name) {
        this.name = name;
        JPanel topP = new JPanel();
JPanel eastP = new JPanel();
JPanel westP = new JPanel();
JPanel bottomP = new JPanel();
JPanel centreP = new JPanel();
JPanel containerp = new JPanel();

//coloring the pannels 
topP.setBackground(new Color (87, 135, 61));
eastP.setBackground(new Color (87, 135, 61));
westP.setBackground(new Color (87, 135, 61));
bottomP.setBackground(new Color (87, 135, 61));
centreP.setBackground(new Color (25,25,25));
containerp.setBackground(new Color (87, 135, 61));


//seting size

topP.setPreferredSize(new Dimension(400,150));
eastP.setPreferredSize(new Dimension(94,100));
westP.setPreferredSize(new Dimension(94,100));
bottomP.setPreferredSize(new Dimension(105,150));
containerp.setPreferredSize(new Dimension(400,40));

//creation button retour


ImageIcon retour = new ImageIcon(new ImageIcon("—Pngtree—vector back icon_4267356.png").getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT));
retourl = new JLabel();
retourl.setIcon(retour);
 RetourMouseListener retourMouseListener = new RetourMouseListener();

        // Ajout de retourMouseListener en tant qu'écouteur de la souris pour retourl
        retourl.addMouseListener(retourMouseListener);



containerp.add(retourl);

 //creation du logo + text 


 ImageIcon imageIcon = new ImageIcon(new ImageIcon("pngegg.png").getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT));
 JLabel anon = new JLabel();
 anon.setText("one rule , don't make it boring"); 
 anon.setIcon(imageIcon);
 anon.setHorizontalTextPosition (JLabel.RIGHT);//set text left center or right of image icon
 anon.setVerticalTextPosition (JLabel.CENTER);//set TOP center or bottom
 anon.setForeground( Color.BLACK);
 anon.setFont( new Font ("Hacker",Font.PLAIN,20));

 anon.setVerticalAlignment(JLabel.BOTTOM);

 anon.setHorizontalAlignment(JLabel.CENTER);

 topP.add(anon);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setPreferredSize(new Dimension(400,390));
        chatArea.setBackground(new Color (25,25,25));
        chatArea.setFont( new Font ("Hacker",Font.PLAIN,20));
        chatArea.setForeground(new Color (32,194,14));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(412,60));
        inputField.setBackground(new Color (25,25,25));
        inputField.setFont( new Font ("Hacker",Font.PLAIN,20));
        inputField.setForeground(new Color (32,194,14));
        centreP.add(scrollPane);
        bottomP.add(inputField);
        bottomP.add(containerp);
 
        this.add(topP,BorderLayout.NORTH);
        this.add(eastP,BorderLayout.EAST);
        this.add(westP,BorderLayout.WEST);
        this.add(centreP,BorderLayout.CENTER);
        this.add(bottomP,BorderLayout.SOUTH);

        setTitle("Chat App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(615, 750);
        setLocationRelativeTo(null);

        
        //chatArea.setEditable(false);
        

        
        inputField.addActionListener(this);

        
        
        setVisible(true);
        

        try {
            server = (ChatInterface) Naming.lookup("//localhost/ChatServer");
            server.registerClient(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String message = inputField.getText();
        inputField.setText("");

        try {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSender(name);
            chatMessage.setContent(message);
            server.sendMessage(chatMessage);
            
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    public void sendMessage(ChatMessage message) throws RemoteException {
        String sender = message.getSender();
        String content = message.getContent();
        appendToChat(sender + ": " + content); // Afficher les messages reçus des autres utilisateurs
    }
  

    public void registerClient(ChatInterface client) throws RemoteException {
        // Logique pour enregistrer le client sur le serveur (si nécessaire)
    }

    public void appendToChat(String message) {
        chatArea.append(message + "\n");
    }
    


    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java ChatUI <username>");
            return;
        }
        String name = args[0];
        SwingUtilities.invokeLater(() -> new ChatUI(name));
    }
    
    





}

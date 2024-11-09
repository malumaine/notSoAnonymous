
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.RemoteException;


public class Launcher extends JFrame implements ActionListener, MouseListener{

//declaration du button

JButton login;


//declaration de jtext

JTextField nom;
JTextField prenom;

//declaration du label pour mouse listener 

JLabel signIN;

public Launcher(){
 

//declaration des pannels du border layout

JPanel topP = new JPanel();
JPanel eastP = new JPanel();
JPanel westP = new JPanel();
JPanel bottomP = new JPanel();
JPanel centreP = new JPanel();

//coloring the pannels 
topP.setBackground(new Color (87, 135, 61));
eastP.setBackground(new Color (87, 135, 61));
westP.setBackground(new Color (87, 135, 61));
bottomP.setBackground(new Color (87, 135, 61));
centreP.setBackground(new Color (87, 135, 61));



//seting size

topP.setPreferredSize(new Dimension(400,300));
eastP.setPreferredSize(new Dimension(150,100));
westP.setPreferredSize(new Dimension(150,100));


// button login

login = new JButton();
login.setText("Login");
login.addActionListener(this);
login.setPreferredSize(new Dimension(400,40));



//creating and siwing the text field

nom = new JTextField();
prenom = new JTextField();

nom.setPreferredSize(new Dimension(400,40));
prenom.setPreferredSize(new Dimension(400,40));
//creating the labels for j texts

JLabel noml = new JLabel();
noml.setText("username :");
noml.setForeground( Color.BLACK);
noml.setFont( new Font ("Hacker",Font.PLAIN,20));

JLabel prenoml = new JLabel();
prenoml.setForeground( Color.BLACK);
prenoml.setText("password :");
prenoml.setFont( new Font ("Hacker",Font.PLAIN,20));


signIN = new JLabel();
signIN.setForeground( Color.BLACK);
signIN.setText("new ? sign in");
signIN.setFont( new Font ("Hacker",Font.PLAIN,15));
signIN.addMouseListener(this);

//creating the panels for the text fields and the labels "containers"


JPanel nomP = new JPanel();
nomP.setBackground(new Color (87, 135, 61));
nomP.setLayout(new FlowLayout(FlowLayout.LEADING));
JPanel prenomP = new JPanel();

prenomP.setBackground(new Color (87, 135, 61));
prenomP.setLayout(new FlowLayout(FlowLayout.LEADING));
nomP.add(noml);
nomP.add(nom);

JPanel loginP = new JPanel();
loginP.setBackground(new Color (87, 135, 61));
loginP.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

prenomP.add(prenoml);
prenomP.add(prenom);
loginP.add(login);
loginP.add(signIN);



//creation du logo + text 


ImageIcon imageIcon = new ImageIcon(new ImageIcon("pngegg.png").getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
JLabel anon = new JLabel();
anon.setText("Not really anonymous but at least we ain't lying ;)"); 

anon.setIcon(imageIcon);
anon.setHorizontalTextPosition (JLabel.CENTER);//set text left center or right of image icon
anon.setVerticalTextPosition (JLabel.BOTTOM);//set TOP center or bottom
anon.setForeground( Color.BLACK);
anon.setFont( new Font ("Hacker",Font.PLAIN,20));

anon.setVerticalAlignment(JLabel.BOTTOM);

anon.setHorizontalAlignment(JLabel.CENTER);

topP.add(anon);



// modifier le centre 

centreP.setLayout(new GridLayout(3,1));

centreP.add(nomP);
centreP.add(prenomP);
centreP.add(loginP);




// construction du frame 

this.add(topP,BorderLayout.NORTH);
this.add(eastP,BorderLayout.EAST);
this.add(westP,BorderLayout.WEST);
this.add(centreP,BorderLayout.CENTER);
this.add(bottomP,BorderLayout.SOUTH);
this.setSize(600,700);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
this.setResizable(false);
this.setVisible(true);


    
}


@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == login) {
        String username = nom.getText(); // Récupération du nom d'utilisateur depuis le champ nom
        String password = prenom.getText(); // Récupération du mot de passe depuis le champ prénom
      

        try {
            // Connexion à la base de données
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/sid", "root", "");

            // Vérification des identifiants
            String query = "SELECT * FROM utilisateurs WHERE nom_utilisateur=?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Si l'utilisateur est trouvé dans la base de données, vérifier le mot de passe
                String savedPassword = resultSet.getString("mot_de_passe");
                if (password.equals(savedPassword)) {
                    // Mot de passe correct
                    try {
                        ChatServer server = new ChatServer();
                        ChatUI chatUI = new ChatUI(username);
                        ChatClient client = new ChatClient(username, chatUI);
                        server.registerClient(client);
            
                    } catch (RemoteException ea) {
                        
                        ea.printStackTrace();
                    }
                    
                } else {
                    // Mot de passe incorrect
                    JOptionPane.showMessageDialog(this, "Mot de passe incorrect.");
                }
            } else {
                // Si l'utilisateur n'est pas trouvé dans la base de données
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur incorrect.");
            }

            // Fermeture de la connexion
            connexion.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}






@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource()== signIN){
        this.dispose();
       new Signin();
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

    if(e.getSource()== signIN){
       
        signIN.setForeground( Color.red);
    }
   
}

@Override
public void mouseExited(MouseEvent e) {
    if(e.getSource()== signIN){
       
        signIN.setForeground( Color.black);
    }
   
  
}


}
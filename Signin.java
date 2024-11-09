
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


public class Signin extends JFrame implements ActionListener{

//declaration du button

JButton signin;


//declaration de jtext

JTextField nom;
JTextField prenom;
JTextField username;
JTextField password;


public Signin(){
 

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

topP.setPreferredSize(new Dimension(400,250));
eastP.setPreferredSize(new Dimension(150,100));
westP.setPreferredSize(new Dimension(150,100));


// button login

signin = new JButton();
signin.setText("Sign in");
signin.addActionListener(this);
signin.setPreferredSize(new Dimension(400,40));



//creating and siwing the text field

nom = new JTextField();
prenom = new JTextField();
username = new JTextField();
password = new JTextField();

nom.setPreferredSize(new Dimension(400,40));
prenom.setPreferredSize(new Dimension(400,40));
username.setPreferredSize(new Dimension(400,40));
password.setPreferredSize(new Dimension(400,40));

//creating the labels for j texts

JLabel noml = new JLabel();
noml.setText("Name :");
noml.setForeground( Color.BLACK);
noml.setFont( new Font ("Hacker",Font.PLAIN,20));

JLabel prenoml = new JLabel();
prenoml.setForeground( Color.BLACK);
prenoml.setText("Family Name :");
prenoml.setFont( new Font ("Hacker",Font.PLAIN,20));


JLabel usernamel = new JLabel();
usernamel.setForeground( Color.BLACK);
usernamel.setText("Username :");
usernamel.setFont( new Font ("Hacker",Font.PLAIN,20));



JLabel passwordl = new JLabel();
passwordl.setForeground( Color.BLACK);
passwordl.setText("Password :");
passwordl.setFont( new Font ("Hacker",Font.PLAIN,20));

//creating the panels for the text fields and the labels "containers"


JPanel nomP = new JPanel();
nomP.setBackground(new Color (87, 135, 61));
nomP.setLayout(new FlowLayout(FlowLayout.LEADING));
JPanel prenomP = new JPanel();

prenomP.setBackground(new Color (87, 135, 61));
prenomP.setLayout(new FlowLayout(FlowLayout.LEADING));
nomP.add(noml);
nomP.add(nom);

JPanel usernameP = new JPanel();
usernameP.setBackground(new Color (87, 135, 61));
usernameP.setLayout(new FlowLayout(FlowLayout.LEADING));

JPanel passwordP = new JPanel();
passwordP.setBackground(new Color (87, 135, 61));
passwordP.setLayout(new FlowLayout(FlowLayout.LEADING));


JPanel loginP = new JPanel();
loginP.setBackground(new Color (87, 135, 61));
loginP.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));

passwordP.add(passwordl);
passwordP.add(password);
usernameP.add(usernamel);
usernameP.add(username);

prenomP.add(prenoml);
prenomP.add(prenom);
loginP.add(signin);




//creation du logo + text 


ImageIcon imageIcon = new ImageIcon(new ImageIcon("pngegg.png").getImage().getScaledInstance(200,200, Image.SCALE_DEFAULT));
JLabel anon = new JLabel();
anon.setText("Your privacy matters 100% N.S.A free "); 

anon.setIcon(imageIcon);
anon.setHorizontalTextPosition (JLabel.CENTER);//set text left center or right of image icon
anon.setVerticalTextPosition (JLabel.BOTTOM);//set TOP center or bottom
anon.setForeground( Color.BLACK);
anon.setFont( new Font ("Hacker",Font.PLAIN,20));

anon.setVerticalAlignment(JLabel.BOTTOM);

anon.setHorizontalAlignment(JLabel.CENTER);

topP.add(anon);



// modifier le centre 

centreP.setLayout(new GridLayout(5,1));

centreP.add(nomP);
centreP.add(prenomP);
centreP.add(usernameP);
centreP.add(passwordP);
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

public void actionPerformed(ActionEvent e) {
    if(e.getSource() == signin) {
        String name = nom.getText();
        String prenomText = prenom.getText();
        String user = username.getText();
        String pass = password.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/sid", "root", "");

            // Requête SQL pour insérer des données dans la table utilisateurs
            String query = "INSERT INTO utilisateurs (nom, prenom, nom_utilisateur, mot_de_passe) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            
            // Remplacer les paramètres de la requête par les valeurs des champs
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, prenomText);
            preparedStatement.setString(3, user);
            preparedStatement.setString(4, pass);

            // Exécution de la requête SQL pour insérer les données
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("creation du compte avec succès !");
                // Ajoutez ici la logique pour rediriger ou afficher un message de succès
            } else {
                System.out.println("Échec de l'insertion des données.");
                // Ajoutez ici la logique pour gérer un échec d'insertion
            }

            // Fermeture de la connexion
            connexion.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            // Ajoutez ici la logique pour gérer les exceptions
        }
    }
}







}
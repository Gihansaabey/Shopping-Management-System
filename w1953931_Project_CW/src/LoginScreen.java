import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    public static WestminsterShoppingManager manager = new WestminsterShoppingManager();
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;
    private JButton signupButton;

    public LoginScreen() {
        setTitle("Login");
        setSize(400, 200);
        setLocationRelativeTo(null);// to center the frame in the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240));  // Set background color

        usernameField = new JTextField();
        passwordField = new JTextField();
        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));  // Add some padding
        panel.setBackground(new Color(240, 240, 240));  // Set background color

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));  // Add some padding
        buttonPanel.setBackground(new Color(240, 240, 240));  // Set background color
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isLogin = manager.Login(usernameField.getText(), passwordField.getText());
                if (isLogin) {
                    shoppingGUI gui = new shoppingGUI();
                    gui.drive();
                    //System.out.println("Login Successfull");
                } else
                    JOptionPane.showMessageDialog(null, "Failed Login", "Failed login", JOptionPane.WARNING_MESSAGE);
            }
        });

        signupButton.addActionListener(new ActionListener() { //from the login screen click the button to open the sign up screen
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                SignUpScreen signupScreen = new SignUpScreen();
                signupScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                signupScreen.setVisible(true);
            }
        });

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
}

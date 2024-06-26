import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpScreen extends JFrame {
    public static WestminsterShoppingManager manager = new WestminsterShoppingManager();
    private JTextField usernameField; //JTextField for username
    private JTextField passwordField;  //JTextField for password
    private JButton signupButton; //JButton for signup
    private JLabel validationLabel;  //JLabel to display validation message

    public SignUpScreen() {
        setTitle("Signup");
        setSize(400, 150);
        setLocationRelativeTo(null);// to center the frame in the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usernameField = new JTextField(20);
        passwordField = new JTextField(20);
        signupButton = new JButton("Signup");
        validationLabel = new JLabel(" ");

        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Username:"), gbc);

        gbc.gridy = 1;
        inputPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(usernameField, gbc);

        gbc.gridy = 1;
        inputPanel.add(passwordField, gbc);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(signupButton, BorderLayout.SOUTH);

        add(panel);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    switchToLoginScreen();   // If signup is successful, switch to the login screen
                }
            }
        });
    }
 // method to check whether the entered username and password are valid
    private boolean validateInput() {
        String username = usernameField.getText();
        String password = new String(passwordField.getText());

        if (username.isEmpty() || password.isEmpty()) { //if any of them are not filled
            validationLabel.setText("Please enter both username and password");
            return false;
        }
        validationLabel.setText(" ");
        return true;
    }

    private void switchToLoginScreen() {
        boolean isSignUp = manager.SignUp(usernameField.getText(), passwordField.getText());
        if (isSignUp){
            setVisible(false);
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            loginScreen.setVisible(true);
        }else
            System.out.println("Signup failed");
    }


}

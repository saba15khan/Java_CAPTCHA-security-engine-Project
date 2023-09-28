package saba;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*;

public class CaptchaGenerator extends JFrame {
    private String generatedCaptchaText;

    public CaptchaGenerator() {
        int width = 200;
        int height = 100;

        generatedCaptchaText = generateRandomText();
        BufferedImage captchaImage = generateCaptcha(width, height);

      
        JLabel label = new JLabel(new ImageIcon(captchaImage));

        JTextField userInputField = new JTextField(10);

        JButton verifyButton = new JButton("Verify");


        JButton refreshButton = new JButton("Refresh");

        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(userInputField, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(verifyButton);
        buttonPanel.add(refreshButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Generate and display the initial CAPTCHA
        generatedCaptchaText = generateRandomText();
        setTitle("CAPTCHA Verification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); 
        setVisible(true);

        
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = userInputField.getText();
                boolean isCorrect = isCaptchaCorrect(userInput);

                if (isCorrect) {
                    JOptionPane.showMessageDialog(null, "CAPTCHA is correct!");
                } else {
                    JOptionPane.showMessageDialog(null, "CAPTCHA is wrong!");
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate a new CAPTCHA and update the label
                generatedCaptchaText = generateRandomText();
                BufferedImage newCaptchaImage = generateCaptcha(width, height);
                label.setIcon(new ImageIcon(newCaptchaImage));
            }
        });
    }

    public BufferedImage generateCaptcha(int width, int height) {
        BufferedImage captchaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = captchaImage.createGraphics();

        // Generate a random background color
        Random random = new Random();
        Color backgroundColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, width, height);

        // Generate random text
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, 30));
        graphics.drawString(generatedCaptchaText, 20, 35);

        // Add noise (random dots and confetti)
        for (int i = 0; i < 200; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            graphics.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            int size = random.nextInt(4) + 1; 
            graphics.fillRect(x, y, size, size);
        }

        graphics.dispose();

        return captchaImage;
    }

    private String generateRandomText() {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captchaText = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            captchaText.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captchaText.toString();
    }

    private boolean isCaptchaCorrect(String userInput) {
        return generatedCaptchaText.equals(userInput);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CaptchaGenerator();
            }
        });
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


// Message Class
class Message {

    String sender;
    String recipient;
    String message;
    String time;

    // Constructor
    Message(String sender,
            String recipient,
            String message) {

        this.sender = sender;
        this.recipient = recipient;
        this.message = message;

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        this.time = sdf.format(new Date());
    }
}



// Backend Class
class SMSService {

    ArrayList<Message> history =
            new ArrayList<>();


    // Send Single SMS
    public void sendSMS(String sender,
                        String recipient,
                        String message) {

        Message m =
                new Message(sender,
                        recipient,
                        message);

        history.add(m);
    }


    // Send Bulk SMS
    public void sendBulkSMS(String sender,
                            String recipients[],
                            String message) {

        for(String rec : recipients){

            Message m =
                    new Message(sender,
                            rec.trim(),
                            message);

            history.add(m);
        }
    }
}



// Frontend Class
public class SMSProject extends JFrame
        implements ActionListener {

    JLabel title,
            l1,
            l2,
            l3;

    JTextField tfSender,
               tfRecipient;

    JTextArea taMessage;

    JButton btnSend,
            btnBulk,
            btnHistory,
            btnClear;

    JTable table;

    DefaultTableModel model;

    SMSService service =
            new SMSService();



    // Constructor
    SMSProject() {

        setTitle("SMS Sending System");

        setSize(1200, 750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        // Main Panel
        JPanel mainPanel =
                new JPanel();

        mainPanel.setLayout(
                new BoxLayout(mainPanel,
                        BoxLayout.Y_AXIS));

        mainPanel.setBackground(
                new Color(230,240,255));



        // Title
        title =
                new JLabel("SMS SENDING SYSTEM");

        title.setFont(
                new Font("Arial",
                        Font.BOLD,
                        30));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        20,0,20,0));



        // Form Panel
        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);

        formPanel.setMaximumSize(
                new Dimension(650,350));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                Color.BLUE,2),
                        "Send Message"));


        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(15,15,15,15);

        gbc.anchor =
                GridBagConstraints.WEST;



        // Labels
        l1 = new JLabel("Sender Name:");

        l2 = new JLabel("Recipient Number:");

        l3 = new JLabel("Message:");

        JLabel labels[] = {l1,l2,l3};

        for(JLabel lbl : labels){

            lbl.setFont(
                    new Font("Arial",
                            Font.BOLD,
                            16));
        }



        // TextFields
        tfSender =
                new JTextField(20);

        tfRecipient =
                new JTextField(20);

        tfSender.setPreferredSize(
                new Dimension(250,35));

        tfRecipient.setPreferredSize(
                new Dimension(250,35));



        // TextArea
        taMessage =
                new JTextArea(5,20);

        taMessage.setLineWrap(true);

        taMessage.setWrapStyleWord(true);

        JScrollPane msgPane =
                new JScrollPane(taMessage);



        // Add Components
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(l1, gbc);

        gbc.gridx = 1;

        formPanel.add(tfSender, gbc);



        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(l2, gbc);

        gbc.gridx = 1;

        formPanel.add(tfRecipient, gbc);



        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(l3, gbc);

        gbc.gridx = 1;

        formPanel.add(msgPane, gbc);




        // Button Panel
        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(Color.WHITE);



        btnSend =
                new JButton("Send SMS");

        btnBulk =
                new JButton("Bulk SMS");

        btnHistory =
                new JButton("Show History");

        btnClear =
                new JButton("Clear");


        JButton buttons[] = {
                btnSend,
                btnBulk,
                btnHistory,
                btnClear
        };


        for(JButton btn : buttons){

            btn.setFont(
                    new Font("Arial",
                            Font.BOLD,
                            14));

            btn.setBackground(
                    new Color(0,102,204));

            btn.setForeground(Color.WHITE);

            btn.setFocusPainted(false);

            btn.addActionListener(this);

            buttonPanel.add(btn);
        }



        gbc.gridx = 0;
        gbc.gridy = 3;

        gbc.gridwidth = 2;

        formPanel.add(buttonPanel, gbc);




        // Table
        String columns[] = {
                "Sender",
                "Recipient",
                "Message",
                "Time"
        };


        model =
                new DefaultTableModel(columns,0);

        table =
                new JTable(model);

        table.setRowHeight(28);

        table.setFont(
                new Font("Arial",
                        Font.PLAIN,
                        14));

        table.getTableHeader().setFont(
                new Font("Arial",
                        Font.BOLD,
                        15));


        JScrollPane tablePane =
                new JScrollPane(table);

        tablePane.setPreferredSize(
                new Dimension(950,220));

        tablePane.setMaximumSize(
                new Dimension(950,220));



        // Add Components to Main Panel
        mainPanel.add(title);

        mainPanel.add(Box.createVerticalStrut(10));

        formPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        mainPanel.add(formPanel);

        mainPanel.add(Box.createVerticalStrut(30));

        tablePane.setAlignmentX(
                Component.CENTER_ALIGNMENT);

        mainPanel.add(tablePane);



        add(mainPanel);

        setVisible(true);
    }



    // Show History
    public void showHistory(){

        model.setRowCount(0);

        for(Message m : service.history){

            model.addRow(new Object[]{

                    m.sender,
                    m.recipient,
                    m.message,
                    m.time
            });
        }
    }



    // Clear Fields
    public void clearFields(){

        tfSender.setText("");

        tfRecipient.setText("");

        taMessage.setText("");
    }



    // Action Events
    public void actionPerformed(ActionEvent e) {


        // Send SMS
        if(e.getSource() == btnSend){

            String sender =
                    tfSender.getText();

            String recipient =
                    tfRecipient.getText();

            String message =
                    taMessage.getText();


            if(sender.equals("") ||
                    recipient.equals("") ||
                    message.equals("")){

                JOptionPane.showMessageDialog(
                        this,
                        "Please Fill All Fields");

                return;
            }


            service.sendSMS(
                    sender,
                    recipient,
                    message);

            JOptionPane.showMessageDialog(
                    this,
                    "SMS Sent Successfully");

            clearFields();
        }



        // Bulk SMS
        if(e.getSource() == btnBulk){

            String sender =
                    tfSender.getText();

            String recipientsText =
                    tfRecipient.getText();

            String message =
                    taMessage.getText();


            if(sender.equals("") ||
                    recipientsText.equals("") ||
                    message.equals("")){

                JOptionPane.showMessageDialog(
                        this,
                        "Please Fill All Fields");

                return;
            }


            String recipients[] =
                    recipientsText.split(",");


            service.sendBulkSMS(
                    sender,
                    recipients,
                    message);

            JOptionPane.showMessageDialog(
                    this,
                    "Bulk SMS Sent Successfully");

            clearFields();
        }



        // Show History
        if(e.getSource() == btnHistory){

            showHistory();
        }



        // Clear
        if(e.getSource() == btnClear){

            clearFields();
        }
    }



    // Main Method
    public static void main(String args[]) {

        new SMSProject();
    }
}
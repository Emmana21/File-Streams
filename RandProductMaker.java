import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, recordCountField;
    private RandomAccessFile randomAccessFile;
    private final int RECORD_SIZE = 128; // Assuming each record is 128 bytes

    public RandProductMaker() {
        super("Random Product Maker");
        setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        add(nameLabel);
        add(nameField);

        JLabel descLabel = new JLabel("Description:");
        descField = new JTextField(50);
        add(descLabel);
        add(descField);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);
        add(idLabel);
        add(idField);

        JLabel costLabel = new JLabel("Cost:");
        costField = new JTextField(10);
        add(costLabel);
        add(costField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRecord();
            }
        });
        add(addButton);

        JLabel recordCountLabel = new JLabel("Record Count:");
        recordCountField = new JTextField(10);
        recordCountField.setEditable(false);
        add(recordCountLabel);
        add(recordCountField);

        setSize(400, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        openFile();
    }

    private void openFile() {
        try {
            randomAccessFile = new RandomAccessFile("products.dat", "rw");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void addRecord() {
        try {
            String name = padField(nameField.getText(), 35);
            String desc = padField(descField.getText(), 75);
            String id = padField(idField.getText(), 6);
            double cost = Double.parseDouble(costField.getText());

            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.writeUTF(name);
            randomAccessFile.writeUTF(desc);
            randomAccessFile.writeUTF(id);
            randomAccessFile.writeDouble(cost);

            updateRecordCount();
            clearFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String padField(String field, int length) {
        if (field.length() > length) {
            return field.substring(0, length);
        } else {
            StringBuilder paddedField = new StringBuilder(field);
            for (int i = field.length(); i < length; i++) {
                paddedField.append(' ');
            }
            return paddedField.toString();
        }
    }

    private void updateRecordCount() throws IOException {
        int recordCount = (int) (randomAccessFile.length() / RECORD_SIZE);
        recordCountField.setText(String.valueOf(recordCount));
    }

    private void clearFields() {
        nameField.setText("");
        descField.setText("");
        idField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}

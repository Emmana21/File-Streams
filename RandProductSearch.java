import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private RandomAccessFile randomAccessFile;
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        super("Random Product Search");
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel searchLabel = new JLabel("Search:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchProducts();
            }
        });
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        openFile();
    }

    private void openFile() {
        try {
            randomAccessFile = new RandomAccessFile("products.dat", "r");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void searchProducts() {
        try {
            String searchTerm = searchField.getText().trim();
            resultArea.setText("");

            randomAccessFile.seek(0);
            while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
                String name = randomAccessFile.readUTF().trim();
                String desc = randomAccessFile.readUTF().trim();
                String id = randomAccessFile.readUTF().trim();
                double cost = randomAccessFile.readDouble();

                System.out.println("Searching for: " + searchTerm + ", Found name: " + name); // Debugging output

                if (name.contains(searchTerm)) {
                    resultArea.append("Name: " + name + ", Description: " + desc + ", ID: " + id + ", Cost: " + cost + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while searching products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RandProductSearch();
        });
    }
}


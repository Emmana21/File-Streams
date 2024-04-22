import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Launch RandProductMaker GUI
        SwingUtilities.invokeLater(() -> {
            RandProductMaker randProductMaker = new RandProductMaker();
            randProductMaker.setVisible(true);
        });

        // Launch RandProductSearch GUI
        SwingUtilities.invokeLater(() -> {
            RandProductSearch randProductSearch = new RandProductSearch();
            randProductSearch.setVisible(true);
        });
    }
}

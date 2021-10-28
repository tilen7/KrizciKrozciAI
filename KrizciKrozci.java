import javax.swing.*;

public class KrizciKrozci {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            // stran 266 in 267
            public void run() {
                JPanel glavnaPlošča = new GlavnaPlosca();
                GraficnoOgrodje.ustvariOkno("Križci in krožci", glavnaPlošča, true);
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;

public class GraficnoOgrodje {
    public static void ustvariOkno(String naslov, JPanel plošča, boolean izrecnaVelikost) {
        Okno okno = new Okno(naslov);
        okno.add(plošča, BorderLayout.CENTER);
        if (izrecnaVelikost) {
            okno.nastaviVelikost();
        } else {
            okno.pack(); // nastavi primerno velikost glede na vsebovane komponente
            // alternativa ročni nastavitvi velikosti okna
            okno.postaviNaSredino();
        }
        okno.setVisible(true);
    }

    private static class Okno extends JFrame {
        private int šZaslon, vZaslon;

        public Okno(String naslov) {
            setTitle(naslov);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Toolkit orodja = Toolkit.getDefaultToolkit();
            Dimension zaslon = orodja.getScreenSize();
            šZaslon = zaslon.width;
            vZaslon = zaslon.height;

        }

        public void nastaviVelikost() {
            setSize(3 * šZaslon / 4, 3 * vZaslon / 4);
            setLocation(šZaslon / 8, vZaslon / 8);
        }

        public void postaviNaSredino() {
            setLocation((šZaslon - getWidth()) / 2, (vZaslon - getHeight()) / 2);
        }
    }
}

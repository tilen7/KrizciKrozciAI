import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

//stanje igre
public class IgralnaPlosca extends JPanel implements MouseListener {
    private static final int PRAZNO = 0;
    private static final int KRIŽEC = 1;
    private static final int KROŽEC = 2;
    private int[][] polja;
    private int naPotezi;
    private int štPraznih;
    private int zmaga;
    private HashMap<String, String[]> slovar;
    private String stanje;

    public IgralnaPlosca() {
        polja = new int[3][3];
        addMouseListener(this);
        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("poteze.dat")));
            slovar = (HashMap<String, String[]>)ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Prislo je do napake: " + e.getMessage());
        }
        stanje = "";
    }

    public void novaIgra(boolean pričneRač) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
                polja[i][j] = PRAZNO;
        }
        naPotezi = pričneRač ? KRIŽEC : KROŽEC;
        zmaga = -1;
        štPraznih = 9;
        stanje = "";
        if (pričneRač)
            potezaRačunalnika();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        izračunajMere();
        narišiMrežo(g);
        if (zmaga != -1)
            označiZmago(g);
        narisiStanje(g);
    }

    private static final Color B_CRTE = Color.GRAY;
    private static final Color B_POLJE = new Color(208, 224, 240);
    private int sPolje, xPovr, yPovr, wPovr, hPovr;
    // mere igralne površine
    // Spolje =stranica, ki je določena tako, da igralna
    // površina zajema
    // čimvečji del igralne plošče
    // iz tega izračunamo meje

    private void potezaRačunalnika() {
        // throw new UnsupportedOperationException("Not yet implemented");
        if (zmaga >= 0 || štPraznih == 0) {
            return;
        }
        String[] najboljšePoteze = slovar.get(stanje);
        String izbranaPoteza = najboljšePoteze[(int)(Math.random() * najboljšePoteze.length)];
        int izbranaPotezaSt = Integer.parseInt(izbranaPoteza);
        int vrstica = izbranaPotezaSt / 3;
        int stolpec = izbranaPotezaSt % 3;
        izvrsiPotezo(vrstica, stolpec);

    }

    private void izračunajMere() {
        double stX = (double) (getWidth() - 1) / 3;
        double stY = (double) (getHeight() - 1) / 3;
        sPolje = (int) Math.min(stX, stY);
        wPovr = hPovr = 3 * sPolje;
        xPovr = (getWidth() - wPovr) / 2;
        yPovr = (getHeight() - hPovr) / 2;
    }

    private void narišiMrežo(Graphics g) {
        g.setColor(B_POLJE);
        g.fillRect(xPovr, yPovr, wPovr, hPovr);
        g.setColor(B_CRTE);
        int x = xPovr;
        for (int i = 0; i <= 3; i++) {
            g.drawLine(x, yPovr, x, yPovr + hPovr); // navpične
            x += sPolje;
        }
        int y = yPovr;
        for (int i = 0; i <= 3; i++) {
            g.drawLine(xPovr, y, xPovr + wPovr, y); // vodoravne
            y += sPolje;
        }
    }

    private static final Color ZMAGA = new Color(255, 255, 224);

    private void označiZmago(Graphics g) {

        int zacVr, zacSt, korakVr, korakSt;
        if (zmaga <= 2)// vrstice
        {
            zacVr = zmaga;
            zacSt = 0;
            korakVr = 0;
            korakSt = 1;
        } else if (zmaga <= 5) // stolpci
        {
            zacVr = 0;
            zacSt = zmaga - 3;// ker smo dodali 3 pri računanju zmage
            korakVr = 1;
            korakSt = 0;
        } else// diagonale
        {
            zacVr = (zmaga == 6) ? 0 : 2;
            zacSt = 0;
            korakVr = (zmaga == 6) ? 1 : -1;
            korakSt = 1;
        }
        g.setColor(ZMAGA);
        int vr = zacVr;
        int st = zacSt;
        for (int i = 0; i < 3; i++) {
            int[] xy = koordinatiPolja(vr, st);
            g.fillRect(xy[0] + 1, xy[1] + 1, sPolje - 1, sPolje - 1);
            vr += korakVr;
            st += korakSt;
        }
    }

    private void narisiStanje(Graphics g) {

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (polja[i][j] == KRIŽEC)
                    narišiKrižec(g, i, j);
                else if (polja[i][j] == KROŽEC)
                    narišiKrožec(g, i, j);
    }

    private int[] koordinatiPolja(int vr, int st) {
        return new int[] { xPovr + st * sPolje, yPovr + vr * sPolje };
    }

    private void narišiKrižec(Graphics g, int i, int j) {
        g.setColor(Color.RED);
        int[] xy = koordinatiPolja(i, j);
        g.drawLine(xy[0] + sPolje / 8, xy[1] + sPolje / 8, xy[0] + 7 * sPolje / 8, xy[1] + 7 * sPolje / 8);
        g.drawLine(xy[0] + sPolje / 8, xy[1] + 7 * sPolje / 8, xy[0] + 7 * sPolje / 8, xy[1] + sPolje / 8);
    }

    private void narišiKrožec(Graphics g, int i, int j) {
        g.setColor(Color.BLUE);
        int[] xy = koordinatiPolja(i, j);
        g.drawOval(xy[0] + sPolje / 8, xy[1] + sPolje / 8, 3 * sPolje / 4, 3 * sPolje / 4);
    }

    public void mouseClicked(MouseEvent e) {
        if (naPotezi == KRIŽEC || zmaga >= 0 || štPraznih == 0)
            return;
        int vr = (e.getY() - yPovr) / sPolje;
        int st = (e.getX() - xPovr) / sPolje;
        if (vr >= 0 && vr <= 2 && st <= 2)
            if (polja[vr][st] == PRAZNO)
                izvrsiPotezo(vr, st);
        potezaRačunalnika();

    }

    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    private void izvrsiPotezo(int vr, int st) {
        int polje = 3 * vr + st;
        stanje += polje;
        polja[vr][st] = naPotezi;
        štPraznih--;
        zmaga = preveriZmago(vr, st);
        naPotezi = (naPotezi == KRIŽEC) ? KROŽEC : KRIŽEC;
        if (zmaga == -1) {
            int[] xy = koordinatiPolja(vr, st);
            repaint(0, xy[0], xy[1], sPolje, sPolje);
        } else
            repaint();
    }

    private int preveriZmago(int vr, int st) {
        // throw new UnsupportedOperationException("Not yet implemented");
        int število = 0;
        for (int j = 0; j < 3; j++)
            if (polja[vr][j] == naPotezi)
                število++;
        if (število == 3)
            return vr; // tris v vr vrstici
        število = 0;
        for (int j = 0; j < 3; j++)
            if (polja[j][st] == naPotezi)
                število++;
        if (število == 3)
            return st + 3; // tris v stolpcu 3,4,5
        // /glavna diagonala
        število = 0;
        if (vr == st) {
            for (int i = 0; i < 3; i++)
                if (polja[i][i] == naPotezi)
                    število++;
            if (število == 3)
                return 6;
        } // tris je v diagonali 11 22 33
        število = 0;
        if (vr + st == 2) {
            for (int i = 0; i < 3; i++)
                if (polja[2 - i][i] == naPotezi)
                    število++;
            if (število == 3)
                return 7; // tris je v diagonali 31 22 13
        }
        return -1;
    }
}

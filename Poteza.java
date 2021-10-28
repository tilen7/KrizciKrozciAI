public class Poteza {
    static char[] poteze = {'0','1','2','3','4','5','6','7','8',};
    String poteza;
    String stanje;
    int poVrsti;
    Poteza[] naslednjePoteze;
    int vrednost; 
    /*
    1 - poraz
    2 - poraz po nekaj potezah
    3 - neodločeno oz. nič še ni gotovo
    4 - zmaga po nekaj potezah
    5 - zmaga
    */
    int najvecjaNaslednjaVrednost;
    int najvecSt;

    public Poteza() {
        poteza = null;
        stanje = "";
        poVrsti = 0;
        naslednjePoteze = new Poteza[9-poVrsti];
    }

    public Poteza(String _Stanje, String _poteza, int _poVrsti) {
        poteza = _poteza;
        stanje = _Stanje + _poteza;
        poVrsti = ++_poVrsti;
    }

    public void inicializacija() {
        if (preveriZmago()) {
            naslednjePoteze = null;
            vrednost = 5;
            return;
        }
        if (9-poVrsti == 0) {
            naslednjePoteze = null;
            vrednost = 3;
            return;
        }
        naslednjePoteze = new Poteza[9-poVrsti];
        //ustvari tabelo naslednjih potez
        int n = 0;
        zunanji:
        for (char poteza : poteze) {
            for (int i = 0; i < stanje.length(); i++) {
                if (stanje.charAt(i) == poteza) {
                    continue zunanji;
                }
            }
            naslednjePoteze[n] = new Poteza(stanje, poteza+"", poVrsti);
            naslednjePoteze[n].inicializacija();
            n++;
        }
        //poglej vredosti potez
        najvecjaNaslednjaVrednost = 1;
        najvecSt = 0;
        for (int i = 0; i < naslednjePoteze.length; i++) {
            if (naslednjePoteze[i].vrednost > najvecjaNaslednjaVrednost) {
                najvecjaNaslednjaVrednost = naslednjePoteze[i].vrednost;
                najvecSt = 1;
            } else if (naslednjePoteze[i].vrednost == najvecjaNaslednjaVrednost) {
                najvecSt++;
            }
        }
        switch (najvecjaNaslednjaVrednost) {
            case 5:
                vrednost = 1;
                break;
            case 4:
                vrednost = 2;
                break;
            case 1:
                vrednost = 4;
                break;
            case 2:
                vrednost = 4;
                break;
            case 3:
                vrednost = 3;
                break;
            default:
                break;
        }
    }

    public boolean preveriZmago() {
        //če ni bilo še igranih vsaj 5 potez, zagotovo še ni zmage
        if (poVrsti < 5) {
            return false;
        }
        //napolnemo igralno ploščo
        int[][] igralnaPlosca = new int[3][3];
        for (int i = stanje.length() - 1; i >= 0; i -= 2) {
            int potezaSt = Integer.parseInt(stanje.charAt(i)+"");
            int vrstica = potezaSt / 3;
            int stolpec = potezaSt % 3;
            igralnaPlosca[vrstica][stolpec] = 1;
        }
        //preverimo vrstice in stolpce
        for (int i = 0; i < igralnaPlosca.length; i++) {
            int vrsticeZap = 0;
            int stolpciZap = 0;
            for (int j = 0; j < igralnaPlosca.length; j++) {
                if (igralnaPlosca[i][j] == 1) {
                    vrsticeZap++;
                }
                if (igralnaPlosca[j][i] == 1) {
                    stolpciZap++;
                }
            }
            if (vrsticeZap == 3 || stolpciZap == 3) {
                return true;
            }
        }
        //preverimo diagonali
        if (igralnaPlosca[0][0] == 1 && igralnaPlosca[1][1] == 1 && igralnaPlosca[2][2] == 1) {
            return true;
        }
        if (igralnaPlosca[0][2] == 1 && igralnaPlosca[1][1] == 1 && igralnaPlosca[2][0] == 1) {
            return true;
        }
        return false;
    }
}
import java.io.*;
import java.util.*;

public class Ucenje {
    static HashMap<String, String[]> slovar = new HashMap<>();
    public static void main(String[] args){
        //preračunamo vse poteze
        Poteza zacetek = new Poteza();
        zacetek.inicializacija();

        //poteze zapišemo v slovar
        zapisiVSlovar(zacetek);

        //rezultat zapišemo na datoteko
        try {
            ObjectOutputStream ous = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("poteze.dat")));
            ous.writeObject(slovar);
            ous.close();
            PrintWriter pw = new PrintWriter(new File("potezeVizualno.txt"));
            for (String name : slovar.keySet()) {
                String key = name.toString();
                String value = izpisiSeznam(slovar.get(name));
                pw.println("\"" + key + "\"" + " = " + value);
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Prišlo je do napake: " + e.getMessage());
        }
    }

    public static void zapisiVSlovar(Poteza poteza) {
        if (poteza.naslednjePoteze == null) {
            return;
        }
        int najvecjaVrednost = poteza.najvecjaNaslednjaVrednost;
        String[] najboljsePoteze = new String[poteza.najvecSt];
        int n = 0;
        for (int i = 0; i < poteza.naslednjePoteze.length; i++) {
            if (poteza.naslednjePoteze[i].vrednost == najvecjaVrednost) {
                najboljsePoteze[n] = poteza.naslednjePoteze[i].poteza;
                n++;
            }
        }
        slovar.put(poteza.stanje, najboljsePoteze);
        for (int i = 0; i < poteza.naslednjePoteze.length; i++) {
            zapisiVSlovar(poteza.naslednjePoteze[i]);
        }
    }

    public static String izpisiSeznam(String[] seznam) {
        StringBuffer sb = new StringBuffer();
        sb.append("[" + seznam[0]);
        for (int i = 1; i < seznam.length; i++) {
            sb.append("," + seznam[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}

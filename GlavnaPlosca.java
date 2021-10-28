import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GlavnaPlosca extends JPanel implements ActionListener {
    private IgralnaPlosca igralnaPlošča;
    private JCheckBox ppPricneRac;

    public GlavnaPlosca() {
        setLayout(new BorderLayout());
        igralnaPlošča = new IgralnaPlosca();
        add(igralnaPlošča, BorderLayout.CENTER);
        JPanel nadzornaPlošča = new JPanel(new FlowLayout());
        ppPricneRac = new JCheckBox("Prične računalnik");
        JButton novaigra = new JButton("Nova igra");
        nadzornaPlošča.add(ppPricneRac);
        nadzornaPlošča.add(novaigra);
        add(nadzornaPlošča, BorderLayout.SOUTH);
        novaigra.addActionListener(this);
        igralnaPlošča.novaIgra(ppPricneRac.isSelected());

    }

    public void actionPerformed(ActionEvent e) {
        igralnaPlošča.novaIgra(ppPricneRac.isSelected());
    }

}

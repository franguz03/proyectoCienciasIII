package steps;

import data.Grammar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ThirdStep extends JFrame {
    private JPanel ThirdPanel;

    public ThirdStep(List<Grammar> grammars, List<Grammar> originalGrammars) {
        setTitle("Tercer paso");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ThirdPanel = new JPanel();
        ThirdPanel.setLayout(new BoxLayout(ThirdPanel, BoxLayout.Y_AXIS));

        // Mostrar la gramática resultante del segundo paso
        JLabel thirdStepLabel = new JLabel("Resultado de Variables Alcanzables:");
        ThirdPanel.add(thirdStepLabel);
        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Grammar grammar : grammars) {
            JLabel grammarLabel = new JLabel("\n" + grammar.getName() + " --> " + String.join(" | ", grammar.getValues()));
            ThirdPanel.add(grammarLabel);
            ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        }

        // Botón "Regresar"
        JButton backButton = new JButton("Regresar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SecondStep(grammars, originalGrammars).setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });

        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        ThirdPanel.add(backButton);

        // Botón "Siguiente paso"
        JButton nextButton = new JButton("Siguiente paso");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FourStep(originalGrammars).setVisible(true);
                dispose();  // Cierra la ventana actual
            }
        });

        ThirdPanel.add(Box.createRigidArea(new Dimension(0, 10)));  
        ThirdPanel.add(nextButton);

        JScrollPane scrollPane = new JScrollPane(ThirdPanel);
        add(scrollPane);
    }
}

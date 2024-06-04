package interfaceViews;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import steps.FirstStep;
import steps.ThirdStep;

import data.Grammar;

public class generalView extends javax.swing.JFrame {
    private List<JPanel> rows;
    private List<JTextField> firstsTextFields;  // Lista para almacenar los primeros text fields
    private List<JTextField> secondsTextFields;
    public List<Grammar> grammars;

    private int indice;

    public generalView() {
        initComponents();
        rows = new ArrayList<>();
        firstsTextFields = new ArrayList<>();
        secondsTextFields = new ArrayList<>();
        grammars = new ArrayList<>();
    }

    public static ArrayList<String> splitAndClean(String input) {
        // Eliminar los espacios en blanco
        String cleanedInput = input.replaceAll("\\s+", "");

        // Dividir la cadena usando '|' como delimitador
        String[] splitValues = cleanedInput.split("\\|");

        // Convertir el array a un ArrayList y retornarlo
        return new ArrayList<>(Arrays.asList(splitValues));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        addRow = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        startSimulation = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addRow.setText("Agregar fila");
        addRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowActionPerformed(evt);
            }
        });

        panel.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane1.setViewportView(panel);

        startSimulation.setText("Iniciar Simulacion");
        startSimulation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startSimulationActionPerformed(evt);
            }
        });

        jLabel1.setText("Gramática");

        jLabel2.setText("Valores");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(addRow)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(startSimulation)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addRow)
                                        .addComponent(startSimulation))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowActionPerformed
        JPanel newRow = new JPanel();
        newRow.setPreferredSize(new Dimension(panel.getWidth(), 30)); // Fija la altura del panel a 30 píxeles

        JTextField textField1 = new JTextField("nombre");
        JTextField textField2 = new JTextField("valores");

        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.remove(newRow);
                rows.remove(newRow);
                firstsTextFields.remove(textField1);
                secondsTextFields.remove(textField2);
                panel.revalidate();
                panel.repaint();
            }
        });

        newRow.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Primer JTextField (20% de ancho)
        constraints.weightx = 0.2;
        newRow.add(textField1, constraints);

        // Segundo JTextField (75% de ancho)
        constraints.weightx = 0.75;
        newRow.add(textField2, constraints);

        // Botón Eliminar (5% de ancho)
        constraints.weightx = 0.05;
        newRow.add(deleteButton, constraints);

        panel.add(newRow);
        rows.add(newRow);
        indice++;
        panel.revalidate();
        panel.repaint();
        firstsTextFields.add(textField1);
        secondsTextFields.add(textField2);


    }//GEN-LAST:event_addRowActionPerformed

    private void startSimulationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startSimulationActionPerformed
        grammars.clear();
        for (int i = 0; i < firstsTextFields.size(); i++) {
            Grammar cd = new Grammar(firstsTextFields.get(i).getText(), splitAndClean(secondsTextFields.get(i).getText()));
            grammars.add(cd);
        }
        System.out.println("nueva simulacion ");
        for (Grammar grammar : grammars) {
            // Realizar operaciones necesarias con cada objeto Grammar
            System.out.println("Nombre: " + grammar.getName());
            System.out.println("Valores: " + grammar.getValues());
        }

        // Mostrar la ventana de resultados de la simulación
        FirstStep resultView = new FirstStep(grammars);
        resultView.setVisible(true);

    }//GEN-LAST:event_startSimulationActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(generalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(generalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(generalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(generalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new generalView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JButton startSimulation;
    // End of variables declaration//GEN-END:variables
}

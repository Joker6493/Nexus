/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nexusfx;

/**
 *
 * @author dboro
 */
public class NewClass {
/*
    package Client;

public class LoadCalc extends javax.swing.JFrame {

    public LoadCalc() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {                          

        ExitBtn = new javax.swing.JButton();
        CanopyLoad = new javax.swing.JFormattedTextField();
        CanopyS = new javax.swing.JFormattedTextField();
        WeightS = new javax.swing.JFormattedTextField();
        WeightWE = new javax.swing.JFormattedTextField();
        WeightWELabel = new javax.swing.JLabel();
        WeightSLabel = new javax.swing.JLabel();
        CanopySLabel = new javax.swing.JLabel();
        LoadCalcBtn = new javax.swing.JButton();
        ClearBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        ExitBtn.setText("Выход");
        ExitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitBtnActionPerformed(evt);
            }
        });

        CanopyLoad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        CanopyLoad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        CanopyS.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        CanopyS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        WeightS.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        WeightS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        WeightWE.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.##"))));
        WeightWE.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        WeightWELabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        WeightWELabel.setText("<html>Вес парашютиста <br>со снаряжением, кг. </html>");
        WeightWELabel.setToolTipText("");

        WeightSLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        WeightSLabel.setText("Вес системы, кг");

        CanopySLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        CanopySLabel.setText("<html>Размер купола,<br>кв. фут</html>");

        LoadCalcBtn.setText("Рассчитать загрузку");
        LoadCalcBtn.setActionCommand("");
        LoadCalcBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LoadCalcBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadCalcBtnActionPerformed(evt);
            }
        });

        ClearBtn.setText("Очистить");
        ClearBtn.setToolTipText("");
        ClearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(WeightWELabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(WeightSLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(WeightS, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(WeightWE, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CanopySLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LoadCalcBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ExitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CanopyLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CanopyS, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(WeightWELabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WeightWE, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(WeightSLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(WeightS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CanopyS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CanopySLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoadCalcBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CanopyLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ExitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ClearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                        

    private void ExitBtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Закрываем форму
        java.lang.System.exit(0);
    }                                       

    private void LoadCalcBtnActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // Рассчитываем загрузку
        // Сначала объявляем переменные
        double WeightWEnum, WeightSnum, CanopySnum, CanopyLoadRes;
        // Берем значения из полей, делаем расчет и округляем до 2х знаков после запятой
        WeightWEnum = Double.parseDouble((String.valueOf(WeightWE.getValue())));
        WeightSnum = Double.parseDouble((String.valueOf(WeightS.getValue())));
        CanopySnum = Double.parseDouble((String.valueOf(CanopyS.getValue())));
        //Выводим результат
        CanopyLoadRes = (((WeightWEnum + WeightSnum) * 2.2) / CanopySnum);
        CanopyLoad.setValue(CanopyLoadRes);
    }                                           

    private void ClearBtnActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // Очищаем поля кнопкой
        WeightWE.setValue(null);
        WeightS.setValue(null);
        CanopyS.setValue(null);
        CanopyLoad.setValue(null);
    }                                        


    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing
                                                                   .UIManager
                                                                   .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing
                         .UIManager
                         .setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util
                .logging
                .Logger
                .getLogger(LoadCalc.class.getName())
                .log(java.util
                         .logging
                         .Level
                         .SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util
                .logging
                .Logger
                .getLogger(LoadCalc.class.getName())
                .log(java.util
                         .logging
                         .Level
                         .SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util
                .logging
                .Logger
                .getLogger(LoadCalc.class.getName())
                .log(java.util
                         .logging
                         .Level
                         .SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util
                .logging
                .Logger
                .getLogger(LoadCalc.class.getName())
                .log(java.util
                         .logging
                         .Level
                         .SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt
            .EventQueue
            .invokeLater(new Runnable() {
                public void run() {
                    new LoadCalc().setVisible(true);
                }
            });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JFormattedTextField CanopyLoad;
    private javax.swing.JFormattedTextField CanopyS;
    private javax.swing.JLabel CanopySLabel;
    private javax.swing.JButton ClearBtn;
    private javax.swing.JButton ExitBtn;
    private javax.swing.JButton LoadCalcBtn;
    private javax.swing.JFormattedTextField WeightS;
    private javax.swing.JLabel WeightSLabel;
    private javax.swing.JFormattedTextField WeightWE;
    private javax.swing.JLabel WeightWELabel;
    // End of variables declaration                   

}
    */    
}

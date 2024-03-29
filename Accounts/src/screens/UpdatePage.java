/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import javax.swing.JOptionPane;
import model.dao.EntryInfoDAO;
import model.to.EntryInfoTO;
import utility.Validations;

/**
 *
 * @author lakshay
 */
public class UpdatePage extends javax.swing.JInternalFrame {

    /**
     * Creates new form UpdatePage
     */
    private int entryid;
    private EntryInfoTO ei;
    public UpdatePage(EntryInfoTO ei) {
        initComponents();
        this.ei=ei;
        this.entryid=ei.getEntryid();
        name_of_the_person.setText(name_of_the_person.getText()+" "+ei.getEntryid()+" "+"{+"+ei.getAccountid()+"}");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtaParticulars = new javax.swing.JTextArea();
        jtfAmount = new controls.FloatTextField();
        jcbEntryType = new javax.swing.JComboBox<String>();
        cdpEntryDate = new controls.CurrentDatePanel();
        btnAdd = new javax.swing.JButton();
        name_of_the_person = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);

        jtaParticulars.setColumns(20);
        jtaParticulars.setRows(5);
        jtaParticulars.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtaParticularsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtaParticulars);

        jtfAmount.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jtfAmount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfAmountActionPerformed(evt);
            }
        });

        jcbEntryType.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jcbEntryType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Debit", "Credit" }));

        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdd.setText("Update The Entry");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        name_of_the_person.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        name_of_the_person.setText("updating information for :: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbEntryType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cdpEntryDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name_of_the_person, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name_of_the_person, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jtfAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jcbEntryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(cdpEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnAdd)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtaParticularsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtaParticularsKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
            jtaParticulars.transferFocus();
            evt.consume();
        }
    }//GEN-LAST:event_jtaParticularsKeyPressed

    private void jtfAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfAmountActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String message = "";
        boolean allvalid = true;
        String particulars = jtaParticulars.getText();
        if (Validations.isEmpty(particulars)) {
            message += "Please Enter Any Particulars\n";
            allvalid = false;
        }
        String amount = jtfAmount.getText().trim();
        if (Validations.isEmpty(amount)) {
            message += "Please Enter Any Amount\n";
            allvalid = false;
        } else if (Validations.isFloat(amount)) {
            float f = Float.parseFloat(amount);
            if (f <= 0) {
                message += "Please Enter Positive Amount\n";
                allvalid = false;
            }
        } else {
            message += "Please Number in Amount\n";
            allvalid = false;
        }
        String type = jcbEntryType.getSelectedItem().toString();
        if (cdpEntryDate.getSelectedDate() == null) {
            message += "Please Choose Any Entry Date\n";
            allvalid = false;
        }
        if (allvalid) {
            EntryInfoTO eit = new EntryInfoTO();
            float amt = Float.parseFloat(amount);
            eit.setAccountid(ei.getAccountid());
            eit.setEntrydate(cdpEntryDate.getSelectedDate());
            eit.setAmount(amt);
            eit.setEntrytype(type);
            eit.setParticulars(particulars);
            eit.setEntryid(ei.getEntryid());
            EntryInfoDAO action = new EntryInfoDAO();
            if (action.updateRecord(eit)){
                message = "Entry is updated";
                if (type.equals("Debit")) {
                    amt = amt * -1;
                }
                /*ait.setCurrentbalance(ait.getCurrentbalance() + amt);
                new AccountInfoDAO().updateAmount(ait.getAccountid(), amt);
                lblCurrentBalance.setText("Current Balance : Rs. " + ait.getCurrentbalance());
                entries = new EntryInfoDAO().getAllRecord(ait.getAccountid());*/
            } else {
                message = "Failure Due to : " + action.getErrorMessage();
            }
        }
        JOptionPane.showMessageDialog(this, message);
    }//GEN-LAST:event_btnAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private controls.CurrentDatePanel cdpEntryDate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jcbEntryType;
    private javax.swing.JTextArea jtaParticulars;
    private controls.FloatTextField jtfAmount;
    private javax.swing.JLabel name_of_the_person;
    // End of variables declaration//GEN-END:variables
}

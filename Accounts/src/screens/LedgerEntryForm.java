/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import model.dao.AccountInfoDAO;
import model.dao.EntryInfoDAO;
import model.to.AccountInfoTO;
import model.to.EntryInfoTO;
import utility.Operation;
import utility.Validations;

/**
 *
 * @author LENOVO
 */
public class LedgerEntryForm extends javax.swing.JInternalFrame {

    private List<AccountInfoTO> accounts;
    private LinkedList<EntryInfoTO> entries;
    private int turn_variable;
    private int srow;
    private JPopupMenu popup;

    public LedgerEntryForm(JDesktopPane ref) {
        initComponents();
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }

        });
        populateAccountCombo();
        populate_financial_year_combobox();
        populateTableEntries();
        jtaParticulars.transferFocus();
        srow = -1;
        popup = new JPopupMenu();
        JMenuItem deleteitem = new JMenuItem("Delete Record");
        deleteitem.setFont(new Font("Arial", Font.BOLD, 18));
        JMenuItem edititem = new JMenuItem("Edit Record");
        edititem.setFont(new Font("Arial", Font.BOLD, 18));
        popup.add(deleteitem);
        popup.add(edititem);
        deleteitem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });
        edititem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                editRecord();
            }
        });
    }
    private EntryInfoTO pei;

    //Operation.openInternalFrame(deskpane, new StartNewLedger());
    private void editRecord() {
        if (srow != -1 && entries != null && srow < entries.size()) {
            pei = entries.get(srow);
            btnAdd.setText("update the entry");
            reset.setText("cancel");
            jcbAccount.setEnabled(false);
            btnNewAccount.setEnabled(false);

        }
    }

    /*private void bind_table_details_according_to_financial_year() {
     String[] col_names = {"Particulars", "Entry Date", "Db", "Cr", "Balance"};
     Object[][] records = null;
     if (entries != null && entries.size() > 0 && jcbAccount.getSelectedIndex() > 0) {
     AccountInfoTO ait = (AccountInfoTO) jcbAccount.getSelectedItem();
     if (entries != null) {
     records = new Object[entries.size()][col_names.length];
     if (records != null) {
     int index = 0;
     float balance = ait.getOpeningbalance();
     for (EntryInfoTO eit : entries) {
     records[index] = new Object[col_names.length];
     records[index][0] = eit.getParticulars();
     records[index][1] = eit.getEntrydate();
     if (eit.getEntrytype().equals("Debit")) {
     records[index][2] = eit.getAmount();
     records[index][3] = "-";
     balance -= eit.getAmount();
     } else {
     records[index][2] = "-";
     records[index][3] = eit.getAmount();
     balance += eit.getAmount();
     }
     records[index][4] = balance;

     index++;
     }
     }
     } else {
     records = new Object[1][col_names.length];
     records[0] = new Object[]{"No Entry", "No Entry", "No Entry", "No Entry", "No Entry"};
     }
     }
     DefaultTableModel model = new DefaultTableModel(records, col_names) {
     @Override
     public boolean isCellEditable(int row, int column) {
     return false;
     }
     };

     tableEntries.setModel(model);
     }*/
    private void populateTableEntries() {
        String[] col_names = {"Particulars", "Entry Date", "Db", "Cr", "Balance"};
        Object[][] records = null;
        if (entries != null && entries.size() > 0 && jcbAccount.getSelectedIndex() > 0) {
            AccountInfoTO ait = (AccountInfoTO) jcbAccount.getSelectedItem();
            switch (turn_variable) {
                case 1:
                    entries = new EntryInfoDAO().getAllRecord(ait.getAccountid());
                    break;
                case 2:
                    //entries = new EntryInfoDAO().get_records_according_to_financial_year(epoch_p, epoch_n);
                    break;
            }
            if (entries != null) {
                records = new Object[entries.size()][col_names.length];
                if (records != null) {
                    int index = 0;
                    float balance = ait.getOpeningbalance();
                    for (EntryInfoTO eit : entries) {
                        records[index] = new Object[col_names.length];
                        records[index][0] = eit.getParticulars();
                        records[index][1] = eit.getEntrydate();
                        if (eit.getEntrytype().equals("Debit")) {
                            records[index][2] = eit.getAmount();
                            records[index][3] = "-";
                            balance -= eit.getAmount();
                        } else {
                            records[index][2] = "-";
                            records[index][3] = eit.getAmount();
                            balance += eit.getAmount();
                        }
                        records[index][4] = balance;

                        index++;
                    }
                }
            } else {
                records = new Object[1][col_names.length];
                records[0] = new Object[]{"No Entry", "No Entry", "No Entry", "No Entry", "No Entry"};
            }
        }
        DefaultTableModel model = new DefaultTableModel(records, col_names) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableEntries.setModel(model);
    }

    public LedgerEntryForm(AccountInfoTO ait) {
        // this();
        if (ait != null) {
            for (int index = 1; index < jcbAccount.getItemCount(); index++) {
                AccountInfoTO at = (AccountInfoTO) jcbAccount.getItemAt(index);
                if (at.getAccountname().equals(ait.getAccountname())) {
                    jcbAccount.setSelectedIndex(index);
                    entries = new EntryInfoDAO().getAllRecord(ait.getAccountid());
                    populateTableEntries();
                    break;
                }
            }
        }
    }

    private void deleteRecord() {
        if (srow != -1 && entries != null && srow < entries.size()) {
            int result = JOptionPane.showConfirmDialog(this, "Are You Sure To Removed This Reccord?", "Message From System", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                EntryInfoTO ei = entries.get(srow);
                EntryInfoDAO action = new EntryInfoDAO();
                AccountInfoTO curr = (AccountInfoTO) jcbAccount.getSelectedItem();
                String message = "";
                if (action.deleterecord(ei.getEntryid())) {
                    message = "Record is Removed From Database";
                    populateTableEntries();
                    float newbal = action.getOverallDebitCredit(curr);
                    System.out.println("before adding open blnce  " + newbal);
                    newbal += curr.getOpeningbalance();
                    System.out.println("after open blnce  " + newbal);
                    curr.setCurrentbalance(newbal);
                    new AccountInfoDAO().updateAmountafterdelete(curr.getAccountid(), newbal);
                    lblCurrentBalance.setText("Current Balance : Rs. " + newbal);
                    switch (turn_variable) {
                        case 1:
                            entries = new EntryInfoDAO().getAllRecord(curr.getAccountid());
                            ;
                            break;
                        case 2:
                            entries = new EntryInfoDAO().get_records_according_to_financial_year(epoch_p, epoch_n);
                    }
                    populateTableEntries();
                } else {
                    message = "Record does not Remove From Database Due To : " + action.getErrorMessage();
                }
                JOptionPane.showMessageDialog(this, message);
            }
        }
        srow = -1;
    }

    private void populateAccountCombo() {
        jcbAccount.removeAllItems();
        jcbAccount.addItem("Choose Any Account");
        accounts = new AccountInfoDAO().getAllRecord();
        if (accounts != null && accounts.size() > 0) {
            for (AccountInfoTO ait : accounts) {
                jcbAccount.addItem(ait);
            }
        }
    }
    /* private final String financial_year_start = "1 april ";
     private final String financial_year_end = "31 march ";*/

    private void populate_financial_year_combobox() {
        jcb_financial_year.removeAllItems();
        jcb_financial_year.addItem("select financial year");
        String year = "";
        /* for (int i = 1950; i < 2100; i++) {
         year += financial_year_start;
         year += Integer.toString(i);
         year += " to ";
         year += financial_year_end;
         year += Integer.toString(i + 1);
         jcb_financial_year.addItem(year);
         year = "";
         }*/
        int start_year = 2016;
        int curr_year = epoch_to_year(System.currentTimeMillis());
        for (int i = start_year; i <= curr_year; i++) {
            year += Integer.toString(i);
            year += "-";
            year += Integer.toString(i + 1);
            jcb_financial_year.addItem(year);
            year = "";
        }
    }

    int epoch_to_year(long millis) {
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(millis / 1000, 0, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String formattedDate = dateTime.format(formatter);
        return Integer.parseInt(formattedDate);
    }

    String date_to_epoch(String date) {
        long epoch = 0;
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.toString(epoch);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbAccount = new javax.swing.JComboBox<java.lang.Object>();
        btnNewAccount = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaParticulars = new javax.swing.JTextArea();
        jtfAmount = new controls.FloatTextField();
        jcbEntryType = new javax.swing.JComboBox<String>();
        cdpEntryDate = new controls.CurrentDatePanel();
        btnAdd = new javax.swing.JButton();
        lblOpeningBalance = new javax.swing.JLabel();
        lblCurrentBalance = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableEntries = new javax.swing.JTable();
        reset = new javax.swing.JButton();
        jcb_financial_year = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Ledger Entry Form");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });

        jcbAccount.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jcbAccount.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbAccount.setFocusable(false);
        jcbAccount.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbAccountItemStateChanged(evt);
            }
        });
        jcbAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAccountActionPerformed(evt);
            }
        });

        btnNewAccount.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnNewAccount.setText("New Account");
        btnNewAccount.setFocusPainted(false);
        btnNewAccount.setFocusable(false);
        btnNewAccount.setRequestFocusEnabled(false);
        btnNewAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewAccountActionPerformed(evt);
            }
        });

        jtaParticulars.setColumns(20);
        jtaParticulars.setRows(5);
        jtaParticulars.setText("enter particulars here...");
        jtaParticulars.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jtaParticularsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jtaParticularsMouseExited(evt);
            }
        });
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
        btnAdd.setText("Add Ledger Entry");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblOpeningBalance.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblOpeningBalance.setText("Opening Balance :");

        lblCurrentBalance.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblCurrentBalance.setText("Current Balance :");

        tableEntries.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableEntries.setFocusCycleRoot(true);
        tableEntries.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableEntriesFocusGained(evt);
            }
        });
        tableEntries.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEntriesMouseClicked(evt);
            }
        });
        tableEntries.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tableEntriesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableEntriesKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tableEntriesKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tableEntries);

        reset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        reset.setText("reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jcb_financial_year.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jcb_financial_year.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcb_financial_year.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcb_financial_yearItemStateChanged(evt);
            }
        });
        jcb_financial_year.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcb_financial_yearMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("enter db/cr amoun here");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jcbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtfAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                                            .addComponent(jcbEntryType, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnNewAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cdpEntryDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblOpeningBalance)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCurrentBalance)
                                .addGap(171, 171, 171))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 158, Short.MAX_VALUE)
                        .addComponent(jcb_financial_year, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCurrentBalance)
                            .addComponent(lblOpeningBalance)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnNewAccount)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbEntryType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(cdpEntryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcb_financial_year, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewAccountActionPerformed
        Operation.openInternalFrame(getDesktopPane(), new StartNewLedger());
        dispose();
    }//GEN-LAST:event_btnNewAccountActionPerformed

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
        if (evt.getActionCommand().equals("Add Ledger Entry")) {
            if (allvalid) {
                EntryInfoTO eit = new EntryInfoTO();
                float amt = Float.parseFloat(amount);
                AccountInfoTO ait = (AccountInfoTO) jcbAccount.getSelectedItem();
                eit.setAccountid(ait.getAccountid());
                eit.setEntrydate(cdpEntryDate.getSelectedDate());
                eit.setAmount(amt);
                eit.setEntrytype(type);
                eit.setParticulars(particulars);
                EntryInfoDAO action = new EntryInfoDAO();
                if (action.insertRecord(eit)) {
                    message = "Entry is Added";
                    System.out.println(action.getOverallDebitCredit((AccountInfoTO) jcbAccount.getSelectedItem()));
                    if (type.equals("Debit")) {
                        amt = amt * -1;
                    }
                    ait.setCurrentbalance(ait.getCurrentbalance() + amt);
                    new AccountInfoDAO().updateAmount(ait.getAccountid(), amt);
                    lblCurrentBalance.setText("Current Balance : Rs. " + ait.getCurrentbalance());
                    entries = new EntryInfoDAO().getAllRecord(ait.getAccountid());
                    turn_variable = 1;
                    populateTableEntries();
                } else {
                    message = "Failure Due to : " + action.getErrorMessage();
                }
            }
            JOptionPane.showMessageDialog(this, message);
        }
        if (evt.getActionCommand().equals("update the entry")) {
            if (allvalid) {
                EntryInfoTO eit = new EntryInfoTO();
                float amt = Float.parseFloat(amount);
                eit.setAccountid(pei.getAccountid());
                eit.setEntrydate(cdpEntryDate.getSelectedDate());
                eit.setAmount(amt);
                eit.setEntrytype(type);
                eit.setParticulars(particulars);
                eit.setEntryid(pei.getEntryid());
                EntryInfoDAO action = new EntryInfoDAO();
                AccountInfoTO ait = (AccountInfoTO) jcbAccount.getSelectedItem();
                if (action.updateRecord(eit)) {
                    message = "Entry is updated";

                    if (type.equals("Debit")) {
                        amt = amt * -1;
                    }
                    float newbal = action.getOverallDebitCredit(ait);
                    newbal += ait.getOpeningbalance();
                    ait.setCurrentbalance(newbal);
                    new AccountInfoDAO().updateAmountafterdelete(ait.getAccountid(), newbal);
                    lblCurrentBalance.setText("Current Balance : Rs. " + ait.getCurrentbalance());
                    entries = new EntryInfoDAO().getAllRecord(ait.getAccountid());
                    turn_variable = 1;
                    populateTableEntries();
                } else {
                    message = "Failure Due to : " + action.getErrorMessage();
                }
            }
            JOptionPane.showMessageDialog(this, message);
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void jcbAccountItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbAccountItemStateChanged
        System.out.println("check check");
        if (jcbAccount.getSelectedIndex() > 0) {
            AccountInfoTO ait = (AccountInfoTO) jcbAccount.getSelectedItem();
            //EntryInfoDAO eid = new EntryInfoDAO();
            lblOpeningBalance.setText("Opening Balance : Rs. " + ait.getOpeningbalance());
            lblCurrentBalance.setText("Current Balance : Rs. " + ait.getCurrentbalance());
            entries = new EntryInfoDAO().getAllRecord(ait.getAccountid());
            turn_variable = 1;
            populateTableEntries();
        }
    }//GEN-LAST:event_jcbAccountItemStateChanged

    private void jtaParticularsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtaParticularsKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            jtaParticulars.transferFocus();

            evt.consume();
        }
    }//GEN-LAST:event_jtaParticularsKeyPressed

    private void jtfAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfAmountActionPerformed

    private void tableEntriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEntriesMouseClicked
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            int point = tableEntries.rowAtPoint(evt.getPoint());
            tableEntries.getSelectionModel().setSelectionInterval(point, point);
            popup.show(tableEntries, evt.getX(), evt.getY());
            srow = tableEntries.getSelectedRow();
            popup.show(tableEntries, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tableEntriesMouseClicked

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed

        if (evt.getActionCommand().equals("reset")) {
            jcbAccount.setSelectedIndex(0);
            jtaParticulars.setText(" ");
            jtfAmount.setText(" ");
            jcbEntryType.setSelectedIndex(0);
            cdpEntryDate.setDate(new java.sql.Date(2019, 1, 1));
        } else {
            btnAdd.setText("Add Ledger Entry");
            reset.setText("reset");
            jcbAccount.setEnabled(true);
            btnNewAccount.setEnabled(true);
        }
    }//GEN-LAST:event_resetActionPerformed

    private void tableEntriesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableEntriesKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE:
                System.out.println("delete pressed");
                srow = tableEntries.getSelectedRow();
                deleteRecord();
                break;
            case KeyEvent.VK_HOME:
                System.out.println("home pressed");
                srow = tableEntries.getSelectedRow();
                editRecord();
                break;
        }
    }//GEN-LAST:event_tableEntriesKeyPressed

    private void tableEntriesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableEntriesKeyTyped
        // System.out.println(evt.getKeyChar());
    }//GEN-LAST:event_tableEntriesKeyTyped

    private void tableEntriesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableEntriesKeyReleased
        // System.out.println(evt.getKeyChar());
    }//GEN-LAST:event_tableEntriesKeyReleased

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        //System.out.println("kkk"+evt.getKeyChar());
    }//GEN-LAST:event_formKeyPressed

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // System.out.println("lll"+evt.getKeyChar());
    }//GEN-LAST:event_formKeyTyped

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // System.out.println("hello");
    }//GEN-LAST:event_formKeyReleased
    private String yearp, yearn, financial_year;
    private String epoch_p, epoch_n;
    private void jcb_financial_yearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_financial_yearItemStateChanged
        //if (jcbAccount.getSelectedIndex() > 0) {
        if (jcb_financial_year.getSelectedIndex() > 0) {
            financial_year = (String) jcb_financial_year.getSelectedItem();
            yearp = financial_year.substring(0, 4);
            yearn = financial_year.substring(5, 9);
            yearp += "-3-31";
            yearn += "-4-1";
            epoch_p = date_to_epoch(yearp);
            epoch_n = date_to_epoch(yearn);
            System.out.println(epoch_p);
            System.out.println(epoch_n);
            entries = new EntryInfoDAO().get_records_according_to_financial_year(epoch_p, epoch_n);
            try {
                System.out.println(entries.size());
            } catch (NullPointerException nlp) {
                System.out.println("size is zero");
            }
            turn_variable = 2;
            populateTableEntries();
        }
        /*}else{
         JOptionPane.showMessageDialog(this, "please select an account holder to use this feature");
         }*/
    }//GEN-LAST:event_jcb_financial_yearItemStateChanged

    private void jcbAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAccountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbAccountActionPerformed

    private void tableEntriesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableEntriesFocusGained
        System.out.println("focus gained by table");
    }//GEN-LAST:event_tableEntriesFocusGained

    private void jcb_financial_yearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcb_financial_yearMouseClicked

    }//GEN-LAST:event_jcb_financial_yearMouseClicked

    private void jtaParticularsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtaParticularsMouseEntered
        if (jtaParticulars.getText().equals("enter particulars here...")) {
            jtaParticulars.setText("");
        }
    }//GEN-LAST:event_jtaParticularsMouseEntered

    private void jtaParticularsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtaParticularsMouseExited
        if (jtaParticulars.getText().equals("")) {
            jtaParticulars.setText("enter particulars here...");
        }
    }//GEN-LAST:event_jtaParticularsMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnNewAccount;
    private controls.CurrentDatePanel cdpEntryDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<java.lang.Object> jcbAccount;
    private javax.swing.JComboBox<String> jcbEntryType;
    private javax.swing.JComboBox jcb_financial_year;
    private javax.swing.JTextArea jtaParticulars;
    private controls.FloatTextField jtfAmount;
    private javax.swing.JLabel lblCurrentBalance;
    private javax.swing.JLabel lblOpeningBalance;
    private javax.swing.JButton reset;
    private javax.swing.JTable tableEntries;
    // End of variables declaration//GEN-END:variables

}

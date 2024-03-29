/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Grapess
 */
public class CurrentDatePanel extends javax.swing.JPanel {

    private GregorianCalendar gcal;

    public CurrentDatePanel() {
        initComponents();
        gcal = new GregorianCalendar();
        jcbYear.removeAllItems();
        jcbYear.addItem("Year");
        int current_year = gcal.get(Calendar.YEAR);
        for (int year = current_year; year >= 1950; year--) {
            jcbYear.addItem(year);
        }
        jcbMonth.removeAllItems();
        jcbMonth.addItem("Month");
        jcbDate.removeAllItems();
        jcbDate.addItem("Date");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbYear = new javax.swing.JComboBox();
        jcbMonth = new javax.swing.JComboBox();
        jcbDate = new javax.swing.JComboBox();

        jcbYear.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jcbYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbYear.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbYearItemStateChanged(evt);
            }
        });

        jcbMonth.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jcbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcbMonth.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbMonthItemStateChanged(evt);
            }
        });

        jcbDate.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jcbDate.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jcbYear, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbDate, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcbYearItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbYearItemStateChanged
        int selected_month_index = jcbMonth.getSelectedIndex();
        int selected_date_index = jcbDate.getSelectedIndex();
        jcbMonth.removeAllItems();
        jcbMonth.addItem("Month");
        if (jcbYear.getSelectedIndex() > 0) {
            int selected_year = Integer.parseInt(jcbYear.getSelectedItem().toString());
            int current_year = gcal.get(Calendar.YEAR);
            MonthList[] months = MonthList.values();
            int end_index = months.length - 1;
            if (selected_year == current_year) {
                end_index = gcal.get(Calendar.MONTH);
            }
            for (int index = 0; index <= end_index; index++) {
                jcbMonth.addItem(months[index]);
            }
            if (selected_month_index < jcbMonth.getItemCount()) {
                jcbMonth.setSelectedIndex(selected_month_index);
                if (selected_date_index < jcbDate.getItemCount()) {
                    jcbDate.setSelectedIndex(selected_date_index);
                }
            }
        }
    }//GEN-LAST:event_jcbYearItemStateChanged

    private void jcbMonthItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbMonthItemStateChanged
        int selected_date_index = jcbDate.getSelectedIndex();
        jcbDate.removeAllItems();
        jcbDate.addItem("Date");
        if (jcbMonth.getSelectedIndex() > 0 && jcbYear.getSelectedIndex() > 0) {
            int selected_year = Integer.parseInt(jcbYear.getSelectedItem().toString());
            int current_year = gcal.get(Calendar.YEAR);
            int current_month = gcal.get(Calendar.MONTH);
            MonthList selected_month = (MonthList) jcbMonth.getSelectedItem();
            int date_range = selected_month.getDays();
            if (selected_year == current_year && current_month == selected_month.ordinal()) {
                date_range = gcal.get(Calendar.DATE);
            } else if (selected_month == MonthList.February) {
                date_range = gcal.isLeapYear(selected_year) ? 29 : 28;
            }
            for (int date = 1; date <= date_range; date++) {
                jcbDate.addItem(date);
            }
            if (selected_date_index < jcbDate.getItemCount()) {
                jcbDate.setSelectedIndex(selected_date_index);
            }
        }
    }//GEN-LAST:event_jcbMonthItemStateChanged

    public Date getSelectedDate() {
        Date date_value = null;
        try {
            if (jcbYear.getSelectedIndex() > 0 && jcbMonth.getSelectedIndex() > 0 && jcbDate.getSelectedIndex() > 0) {
                int year = Integer.parseInt(jcbYear.getSelectedItem().toString());
                int month = jcbMonth.getSelectedIndex();
                int date = jcbDate.getSelectedIndex();
                String date_string = year + "-" + month + "-" + date;
                date_value = Date.valueOf(date_string);
            }
        } catch (Exception ex) {

        }
        return date_value;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jcbDate;
    private javax.swing.JComboBox jcbMonth;
    private javax.swing.JComboBox jcbYear;
    // End of variables declaration//GEN-END:variables

    public void setDate(Date date_value) {
        try {
            String[] values = date_value.toString().split("-");
            int year = Integer.parseInt(values[0]);
            int month = Integer.parseInt(values[1]);
            int date = Integer.parseInt(values[2]);
            for(int index = 1; index < jcbYear.getItemCount(); index++){
                int year_value = Integer.parseInt(jcbYear.getItemAt(index).toString());
                if(year_value == year){
                    jcbYear.setSelectedIndex(index);
                    break;
                }
            }
            jcbMonth.setSelectedIndex(month);
            jcbDate.setSelectedIndex(date);
        } catch (Exception ex) {
        }
    }
}

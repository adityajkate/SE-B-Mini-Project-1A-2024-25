/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Blob;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JOptionPane;
import patmini.usersession;

/**
 *
 * @author Varunkumar lysetti
 */
public class noteslist extends javax.swing.JFrame {

    /**
     * Creates new form noteslist
     */
    public noteslist() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "NAME", "Download", "ACTION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 91, 680, 420));

        jButton1.setText("REFRESH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(619, 56, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 153, 255));
        jLabel1.setText("NOTES SECTION");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 9, 399, 70));

        jButton2.setText("CLOSE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Picsart_24-09-01_22-01-15-634.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  String url = "jdbc:mysql://localhost:3306/pat"; // Database URL
    String user = "root"; // Database username
    String password = "pat@admin#0987"; // Database password

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Clear previous data

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement stmt = conn.prepareStatement("SELECT name FROM pdf_files");
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String fileName = rs.getString("name");
            model.addRow(new Object[]{fileName, "Download", "Delete"});
        }

    } catch (SQLException ex) {
        ex.printStackTrace(); // Log the complete stack trace
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
    }

    // Only add the mouse listener once
    if (jTable1.getMouseListeners().length == 0) {
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int column = jTable1.columnAtPoint(evt.getPoint());

                if (column == 1) { // Download column
                    // Download logic...
                } else if (column == 2) { // Delete column
                    // Delete logic...
                }
            }
        });

    }

    // Add Mouse Listener for Download and Delete
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jTable1.rowAtPoint(evt.getPoint());
            int column = jTable1.columnAtPoint(evt.getPoint());

            if (column == 1) { // Download column
                String fileName = (String) jTable1.getValueAt(row, 0);
                String filePath = "C:\\Users\\Varunkumar lysetti\\Downloads\\" + fileName;
                try (Connection downloadConn = DriverManager.getConnection(url, user, password);
                     PreparedStatement downloadStmt = downloadConn.prepareStatement("SELECT pdf_data FROM pdf_files WHERE name = ?")) {
                    downloadStmt.setString(1, fileName);
                    ResultSet downloadRs = downloadStmt.executeQuery();
                    if (downloadRs.next()) {
                        Blob blob = downloadRs.getBlob("pdf_data");
                        if (blob != null) {
                            try (InputStream inputStream = blob.getBinaryStream()) {
                                Files.createDirectories(Paths.get("C:\\Users\\Varunkumar lysetti\\Downloads"));
                                Path destinationPath = Paths.get(filePath);
                                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                                JOptionPane.showMessageDialog(null, "File downloaded successfully!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No data found for the selected file.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "File not found in the database.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error downloading file: " + ex.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "File IO error: " + ex.getMessage());
                }
            } else if (column == 2) { // Delete column
                String fileNameToDelete = (String) jTable1.getValueAt(row, 0);
                String currentUsername = UserSession.loggedInUsername; // Logged-in username
                boolean isAuthorized = false;

                // Check if the user is authorized to delete the file
                try (Connection authConn = DriverManager.getConnection(url, user, password);
                     PreparedStatement authStmt = authConn.prepareStatement(
                         "SELECT COUNT(*) FROM pdf_files p " +
                         "WHERE p.name = ? AND p.admin = ?")) { // Check both file name and admin

                    // Set parameters for the prepared statement
                    authStmt.setString(1, fileNameToDelete); // File name to check
                    authStmt.setString(2, currentUsername); // Logged-in username

                    // Execute the query
                    try (ResultSet authRs = authStmt.executeQuery()) {
                        // Check if the user is authorized
                        if (authRs.next() && authRs.getInt(1) > 0) {
                            isAuthorized = true; // User is authorized
                        }
                    }
                } catch (SQLException authEx) {
                    // Handle SQL exceptions
                    JOptionPane.showMessageDialog(null, "Error checking permissions: " + authEx.getMessage());
                }

                if (isAuthorized) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this file?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try (Connection deleteConn = DriverManager.getConnection(url, user, password);
                             PreparedStatement deleteStmt = deleteConn.prepareStatement("DELETE FROM pdf_files WHERE name = ?")) {
                            deleteStmt.setString(1, fileNameToDelete);
                            deleteStmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "File deleted successfully!");
                            model.removeRow(row); // Remove the row from the table
                        } catch (SQLException delEx) {
                            JOptionPane.showMessageDialog(null, "Error deleting file: " + delEx.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You do not have permission to delete this file.");
                }
            }
        }
    });
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(noteslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(noteslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(noteslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(noteslist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new noteslist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}

package iitrvirusremover;

import java.io.*;
import javax.swing.*;

/**
 * A simple program to remove a particular type of virus found in computers of ICC in IIT Roorkee
 * @author Dhaval Kapil
 * MIT license http://www.opensource.org/licenses/mit-license.php
 */
public class MainFrame extends javax.swing.JFrame {

    // final variables
    private final String virusFile = File.separator + "Lyrics-The Civil Wars - The One That Got Away.vbe";
    private final File to = new File("recovery" + File.separator);
    
    // instance variables
    private boolean computerVirusExists;
    private boolean usbVirusExists;
    private File usb;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }
    
    // This presently works for windows 7
    private void antivirus()
    {
        // First checking whether virus exists in computer...
        File computerVirus = new File(System.getProperty("user.home") + virusFile);
        if(computerVirus.exists())
        {
            display.append("Virus exists in computer\n");
            computerVirusExists = true;
        }
        else
        {
            display.append("Virus does not exists in computer\n");
            computerVirusExists = false;
        }
        
        // Now initializing usb drive
        String input;
        while(true)
        {
            input = JOptionPane.showInputDialog(this, "Enter drive letter for usb drive (eg. E, F, G) :");
            if(input.length()==1)
            {
                if(Character.isUpperCase(input.charAt(0)))
                {
                    usb = new File(Character.toString(input.charAt(0)) + ":" + File.separator);
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Please enter a valid drive letter");
        }
        
        // Now checking whether virus exists usb drive
        File usbVirus = new File(usb.getAbsolutePath() + virusFile);
        if(usbVirus.exists())
        {
            display.append("Virus exists in usb drive\n");
            usbVirusExists = true;
        }
        else
        {
            display.append("Virus does not exists in usb drive\n");
            usbVirusExists = false;
        }
        
        // Now removing virus from computer
        if(computerVirusExists)
        {
            JOptionPane.showMessageDialog(this, "Make sure you close all running application");
            while(true)
            {   try
                {
                    Runtime.getRuntime().exec("taskkill /IM wscript.exe /F");
                }
                catch(IOException e)
                {
                    JOptionPane.showMessageDialog(this, "Make sure that the process wscript.exe is ended.");
                }
                if(!computerVirus.exists())
                    break;
                if(computerVirus.delete())
                    break;                
            }
            display.append("Virus removed from computer\n");
        }
        
        // Now removing virus from usb
        if(usbVirusExists)
        {
            if(!usbVirus.delete())
            {
                JOptionPane.showMessageDialog(this, "Error....");
                System.exit(0);
            }
            display.append("Virus removed from usb drive");
        }
        
        // Now removing all .lnk files from usb
        File[] files = usb.listFiles();
        for(File file : files)
        {
            if(file.getName().endsWith(".lnk"))
                file.delete();
        }
        display.append("Deleted all the shortcuts from usb drive\n");
        
        // Now recovering all files
        display.append("Recovering files....\n");
        copyDir(usb, to);
        display.append("\nCompleted...\n");
    }   
    private void copyDir(File from, File to)
    {
        if(!to.exists())
            to.mkdir();
        File[] files = from.listFiles();
        for(File file : files)
        {
            if(file.isFile())
                try
                {
                    copyFile(file, new File(to.getAbsolutePath() + File.separator + file.getName()));
                    display.append(file.getName() + " recovered\n");
                }
                catch(IOException e)
                {
                    display.append("Error in recovering: " + file.getName() + "\n");
                    JOptionPane.showMessageDialog(this, e.toString());
                }
            else
            {
                copyDir(file, new File(to.getAbsolutePath() + File.separator + file.getName()));
            }
        }
    }
    
    // method to copy files
    private void copyFile(File from, File to)
    throws IOException
    {
        FileInputStream in = new FileInputStream(from);
        FileOutputStream out = new FileOutputStream(to);
        byte[] buffer = new byte[4096];
        int bytes_read;
        while((bytes_read=in.read(buffer))!=-1)
            out.write(buffer, 0, bytes_read);
        out.close();
        in.close();
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
        display = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IITR Virus Remover - Dhaval Kapil");

        display.setColumns(20);
        display.setRows(5);
        jScrollPane1.setViewportView(display);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e)
        {}
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
                mainFrame.antivirus();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea display;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

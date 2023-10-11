package com.example;

import java.io.FileOutputStream;
import java.io.IOException;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Hello world!
 */
    public class App extends JPanel implements ActionListener {
        protected JTextField textField;
        protected JTextArea textArea;
        private final static String newline = "\n";
     
        public App() {
            super(new GridBagLayout());
     
            textField = new JTextField(20);
            textField.addActionListener(this);
     
            textArea = new JTextArea(5, 20);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
     
            //Add Components to this panel.
            GridBagConstraints c = new GridBagConstraints();
            c.gridwidth = GridBagConstraints.REMAINDER;
     
            c.fill = GridBagConstraints.HORIZONTAL;
            add(textField, c);
     
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1.0;
            c.weighty = 1.0;
            add(scrollPane, c);
    }
     
    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(text + newline);
        textField.selectAll();
    
        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Java Books");
        Object[][] bookData = {
            {"1", text}};

        int rowCount = 0;
        
        for (Object[] aBook : bookData) {
            Row row = sheet.createRow(++rowCount);
            
            int columnCount = 0;
            
            for (Object field : aBook) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            } 
        }
        
        try (FileOutputStream outputStream = new FileOutputStream("JavaBooks.xlsx")) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event dispatch thread.
         */
    
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add contents to the window.
        frame.add(new App());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    } 
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
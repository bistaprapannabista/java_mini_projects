import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Notepad extends JFrame implements ActionListener {
    JMenu open, save, _new;
    JMenuItem newFile, saveFile;
    JEditorPane jep;

    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(newFile)) {
            jep.setText("");
        }

        if (e.getSource().equals(saveFile)) {
            String name = JOptionPane.showInputDialog(this, "Enter File Name");
            try {
                FileWriter fw = new FileWriter(name + ".txt");
                fw.write(jep.getText());
                fw.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                getListOfFiles();
            }
        }

        else {
            try {
                JMenuItem jmi = (JMenuItem) e.getSource();
                File myObj = new File(jmi.getText());
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    jep.setText(data);
                }
                myReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }
    }

    public void getListOfFiles() {
        File folder = new File("C:\\Users\\Dell\\Desktop\\java_practice\\project\\");
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    JMenuItem fileMenuItem = new JMenuItem(file.getName());
                    open.add(fileMenuItem);
                    fileMenuItem.addActionListener(this);
                }
            }
        }
    }

    Notepad() {
        JMenuBar mb = new JMenuBar();
        open = new JMenu("Open");
        getListOfFiles();
        save = new JMenu("Save");
        saveFile = new JMenuItem("Save File");
        saveFile.addActionListener(this);
        save.add(saveFile);
        _new = new JMenu("New");
        newFile = new JMenuItem("New File");
        _new.add(newFile);
        newFile.addActionListener(this);
        mb.add(open);
        mb.add(save);
        mb.add(_new);
        setJMenuBar(mb);
        jep = new JEditorPane();
        jep.setContentType("text/plain");
        jep.setText("");
        setContentPane(jep);
        setSize(300, 300);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        new Notepad();
    }
}
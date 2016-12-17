package skiplist.console;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

public class Console extends JFrame {

    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private final PrintStream stream;
    
    private String line = null;
    
    private final String[] commands = {"initialise()", "insert(int value)", "find(int value)", "deleteByValue(int value)", "deleteByIndex(int index)",
                                       "findNode(int index)", "count()", "findSteps(int value)", "height()", "empty()", "print()", "quit()"};

    public boolean disposed = false;
    
    public Console() {
        initComponents();

        stream = new PrintStream(new CustomOutputStream(textArea1));
        System.setOut(stream);
        System.setErr(stream);
        
        addComponentListener(new ComponentAdapter() {
            public void componentResized(final ComponentEvent event) {
                final Dimension dimension = getSize();
                // prev height - 70
                scrollPane1.setBounds(0, 0, dimension.width - 15, dimension.height - 85);
                textArea2.setBounds(0, scrollPane1.getHeight(), dimension.width - 15, dimension.height - scrollPane1.getHeight());
            }
        });
        
        System.out.println("The following commands are accepted: ");
        for (final String command : commands) {
            System.out.println(" > " + command);
        }
        System.out.println();
    }
    
    public String getLine() {
        String temp = line;
        line = null;
        return temp;
    }
    
    public void setFocus() {
        textArea2.requestFocus();
    }

    private void initComponents() {
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        textArea2 = new JTextArea("console > ");
        textArea2.setCaretPosition(textArea2.getText().length());
        
        //======== this ========
        setTitle("Console");
        setForeground(Color.black);
        setAutoRequestFocus(false);
        setBackground(Color.black);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        pack();

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("File");

                //---- menuItem1 ----
                menuItem1.setText("Save log");
                //menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("Clear All");
                menu1.add(menuItem2);
                menuItem2.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent event) {
                        textArea1.setText("");
                        System.out.println("The following commands are accepted: ");
                        for (final String command : commands) {
                            System.out.println(" > " + command);
                        }
                        System.out.println();
                    }
                });

                //---- menuItem3 ----
                menuItem3.setText("Close");
                menu1.add(menuItem3);
                menuItem3.addActionListener(new ActionListener() {
                    public void actionPerformed(final ActionEvent event) {
                        setVisible(false);
                        disposed = true;
                    }
                });
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {

            //---- textArea1 ----
            textArea1.setBackground(Color.black);
            textArea1.setForeground(Color.white);
            textArea1.setEditable(false);
            textArea1.setFocusable(false);
            scrollPane1.setViewportView(textArea1);
            
            textArea2.setBackground(Color.gray);
            textArea2.setForeground(Color.white);
            textArea2.setEditable(false);
            textArea2.getCaret().setVisible(true);
            Action action = textArea2.getActionMap().get(DefaultEditorKit.deletePrevCharAction);
            action.setEnabled(false);
            
            textArea2.addFocusListener(new FocusListener() {

                @Override
                public void focusGained(FocusEvent e) {
                    textArea2.getCaret().setVisible(true);
                }

                @Override
                public void focusLost(FocusEvent e) {
                    textArea2.getCaret().setVisible(false);
                }
                
            });
            
            textArea2.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    final int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_ENTER) {
                        line = textArea2.getText().substring(10, textArea2.getText().length());
                        textArea2.setText("console > ");
                    } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                        final String text = textArea2.getText();
                        if (text.length() > 10) {
                            textArea2.setText(text.substring(0, text.length() - 1));
                        }
                    } else {
                        if (keyCode == KeyEvent.VK_SHIFT || keyCode == KeyEvent.VK_ALT) {
                            return;
                        }
                        textArea2.append(Character.toString(e.getKeyChar()));
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {}
                
            });
            
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 0, 1020, 430);

        contentPane.add(textArea2);
        
        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(1035, 500);
        setLocationRelativeTo(getOwner());
    }

    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JTextArea textArea2;

    public class CustomOutputStream extends OutputStream {

        private JTextArea textArea;

        int last = 0;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            final String string = new String(b, off, len);
            textArea.append(string);
            //textArea.append((!string.contains("\n") ? "[" + getTime() + "] " : "") + string);
            //textArea.append("console > " + string);
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }
    }

    private String getTime() {
        return sdf.format(Calendar.getInstance().getTime());
    }
}

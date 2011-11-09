package edu.wcu.cs.cs363.team3.project02;

import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.table.TableColumn;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This is the main window of the e-mail client.  It contains a menu bar
 * at the north (top) of the window, a table in the center which contains
 * the information stored about contacts, and three buttons on the south
 * (bottom) side of the used to add contacts, edit contacts, or delete
 * a contact.
 *
 * @author Chris Blades
 * @author Don Coffin
 * @author Benny Reese
 *
 * @version 7/4/2010
 */
public class MainFrame extends JFrame {
    /**
     * The table displaying contacts
     */
    private ContactTable contactTable;
    /** The button that will add editing functionality of a contact */
    private JButton editButton;
    /** The button that will allow for deleting of contacts */
    private JButton deleteButton;
    /** The mediator for the edit and delete buttons */
    private EditDeleteMediator mediator;

    /**
     * A mediator to control the state of the edit and delete buttons
     *
     */
    private class EditDeleteMediator extends MouseAdapter {
        /**
         * Checks to see if a row in the table is selected and sets the state
         * of the edit and delete buttons based on that
         */
        private void setState() {
            boolean enabled = contactTable.getSelectedContact() != null;
            editButton.setEnabled(enabled);
            deleteButton.setEnabled(enabled);
        }
        
        /**
         * Called when the table is clicked on.
         *
         * @param e the event that triggered this.
         */
        public void mouseClicked(MouseEvent e) {
            setState();
        }
    }
    
    /**
     * Creates a windows with title <code>title</code> and composes the 
     * subcomponents.
     *
     * @param title - The text that will appear in the windows tite bar.
     */
    public MainFrame(String title) {
        super(title);

        this.setLayout(new BorderLayout());
        
        mediator = new EditDeleteMediator();
        //
        // buttons pane setup
        //
        initializeButtons();

        //    
        // contact table set up
        //
        initializeTable();

        mediator.setState();

        this.setJMenuBar(menuBar());
        //this.getJMenuBar().remove(exit);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    DataStore.get().saveState();
                } catch (IOException ioe) {
                    // what to do here? plague the user with a popup on 
                    // shutdown? write to a log file? we're not sure.
                    System.err.println(ioe.getMessage());
                }
            }
        });
    }

    /**
     * Constructs contacts table and adds the table to this frame.
     */
    private void initializeTable() {
        // construct table and add it to this frame
        contactTable = new ContactTable();
        JScrollPane contactTableScroll = new JScrollPane(contactTable);
        contactTable.setFillsViewportHeight(true);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        this.add(contactTableScroll, BorderLayout.CENTER);
        
        // add mouseListener to table
        contactTable.addMouseListener(mediator);
        contactTable.addMouseListener(new MouseAdapter() {
            /**
             * If use has double-clicked, find out what contact is selected
             * (what contact they double-clicked on), and open frame to 
             * send message to that contact.
             *
             * @param e the event that triggered this call
             */
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    EmailContact selected = contactTable.getSelectedContact();
                    if (selected != null) {
                        EmailTransmissionFrame transFrame = 
                                new EmailTransmissionFrame(MainFrame.this, 
                                    "Email");
                                
                        transFrame.setToAddress(
                              selected.getEmailAddress());
                    
                        transFrame.setMinimumSize(new Dimension(300, 300));
                        transFrame.setVisible(true);
                    }
                }
            }

        });
    }

    /**
     * Initializes and adds add, edit, and delete buttons to this frame.
     */
    private void initializeButtons() {   
        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new GridLayout(/*rows*/ 1, /*cols*/ 3));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EmailContact contact = new EmailContact();
                contactTable.addContact(contact);
                new ContactEditingFrame(MainFrame.this,
                                        contact);
            }
        });
        addButton.setToolTipText("Add a contact");

        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ContactEditingFrame(MainFrame.this, 
                            contactTable.getSelectedContact());
            }
        });
        editButton.setToolTipText("Edit the currently selected contact");

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contactTable.removeContact(contactTable.getSelectedContact());
                mediator.setState();
            }
        });
        deleteButton.setToolTipText("Delete the currently selected contact");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Lets the mainframe know that the contacts have been changed in some way
     * and that the ContactTable needs to update.
     */
    public void updateContacts() {
        AbstractTableModel model = (AbstractTableModel)contactTable.getModel();
        model.fireTableDataChanged();
    }

    /**
     * Creates the menu bar for the windows
     *
     * @return - a menu bar fully composed.
     */
    private JMenuBar menuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        menuBar.add(fileMenu());
        menuBar.add(configurationMenu());
        menuBar.add(helpMenu());

        return menuBar;
    }

    /**
     * Composes the file menu on the menu bar.
     *
     * @return - the composed file menu.
     */
    private JMenu fileMenu() {
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem exit = new JMenuItem("Exit");

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.setMnemonic('F');
        exit.setMnemonic('X');
        
        fileMenu.add(exit);

        return fileMenu;
    }

    /**
     * Composes the configuration menu on the menu bar.
     *
     * @return - the composed configuration menu.
     */
    private JMenu configurationMenu() {
        JMenu configurationMenu = new JMenu("Configuration");
        
        JMenuItem configure = new JMenuItem("Configure");

        configure.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                JDialog configFrame = new ConfigurationFrame(MainFrame.this);
                configFrame.setSize(300, 200);
                configFrame.setResizable(false);
                configFrame.setVisible(true);
            }
        });
        
        configurationMenu.setMnemonic('C');
        
        configurationMenu.add(configure);

        return configurationMenu;
    }

    /**
     * Composes the help menu on the menu bar.
     *
     * @return - the composed help menu.
     */
    private JMenu helpMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem about = new JMenuItem("About");

        about.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                SystemInformationFrame sif = 
                    new SystemInformationFrame(MainFrame.this);
                sif.setSize(300, 400);
                sif.setResizable(false);
                sif.setVisible(true);
            }
        });

        helpMenu.setMnemonic('H');
        about.setMnemonic('A');

        helpMenu.add(about);

        return helpMenu;
    }

    
    /**
     *  Makes and shows a MainFrame
     *
     *  @param args unused.
     */
    public static void main(String[] args) {
        MainFrame frame = new MainFrame("Email Client");
        frame.setSize(600, 400);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setVisible(true);
    }

}

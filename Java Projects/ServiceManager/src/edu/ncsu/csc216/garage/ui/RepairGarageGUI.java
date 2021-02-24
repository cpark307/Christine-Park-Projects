package edu.ncsu.csc216.garage.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.ncsu.csc216.garage.model.dealer.Manageable;
import edu.ncsu.csc216.garage.model.dealer.ServiceManager;
import edu.ncsu.csc216.garage.model.vehicle.Vehicle;

/**
 * Creates the Repair Garage GUI
 * 
 * @author Jo Perry
 */
public class RepairGarageGUI extends JFrame implements ActionListener {

	/**
	 * Generated serialization code
	 */
	private static final long serialVersionUID = -4388703116571003210L;
	// Window and button strings
	private final static String TITLE = "Service Garage Manager";
	private final static String ADD = "Add New Vehicle";
	private final static String EDIT = "Edit Selected Vehicle";
	private final static String REMOVE = "Remove Selected Vehicle";
	private final static String FILTER = "Display Filter: ";
	private final static String WAITING_ROOM = "Vehicles Awaiting Service";
	private final static String FILL = "Fill Service Bays";
	private final static String ADD_BAY = "Add Service Bay";
	private final static String RELEASE = "Finish Repair of Selected Vehicle";
	private final static String GARAGE = "Service Bays";
	private final static String QUIT = "Quit";

	// Size constants for the window
	private final static int FRAME_WIDTH = 730;
	private final static int FRAME_HEIGHT = 500;

	// Buttons
	private JButton btnAdd = new JButton(ADD);
	private JButton btnEdit = new JButton(EDIT);
	private JButton btnRemove = new JButton(REMOVE);
	private JButton btnFill = new JButton(FILL);
	private JButton btnRelease = new JButton(RELEASE);
	private JButton btnAddBay = new JButton(ADD_BAY);
	private JButton btnQuit = new JButton(QUIT);

	// Panels
	private JPanel pnlWaitingCars = new JPanel(new BorderLayout()); // Main left panel
	private JPanel pnlWaitTop = new JPanel(); // Top of left panel
	private JPanel pnlBays = new JPanel(new BorderLayout()); // Main right panel
	private JPanel pnlBayTop = new JPanel(); // Top of right panel
	private JPanel pnlFilter = new JPanel(); // Panel for filtering the display list

	// List model stuff
	// Default list models for the scrollable lists
	private DefaultListModel<String> dlmWaitingCars = new DefaultListModel<String>(); // Parking Lot default list model
	private DefaultListModel<String> dlmGarageBays = new DefaultListModel<String>(); // Garage default list model
	private JList<String> lstGarageBays = new JList<String>(dlmGarageBays); // Garage actual list
	private JList<String> lstWaitingCars = new JList<String>(dlmWaitingCars); // Parking Lot room actual list
	private JScrollPane scpWaitingCars = new JScrollPane(lstWaitingCars); // Scroll pane for parking lot list
	private JScrollPane scpGarageBays = new JScrollPane(lstGarageBays); // Scroll pane for garage list

	// Labels
	private JLabel lblFilter = new JLabel(FILTER); // Filter for display list

	// Fields, combos for user input
	private JTextField txtFilter = new JTextField(10); // Display filter goes here

	// Most recent filter entry
	private String filter = "";

	UserDialog pane;

	// Backend model
	private Manageable serviceMgr;

//------ Constructors and GUI set up  -------------

	/**
	 * Constructor: Creates a new GUI with an empty repair garage.
	 */
	public RepairGarageGUI() {
		serviceMgr = new ServiceManager();
		setUpGUI();
	}

	/**
	 * Creates a new GUI with a vehicle list coming from the named file. If the name
	 * is not valid the vehicle list is initialized to empty.
	 * 
	 * @param fileName name of file to use
	 * 
	 */
	public RepairGarageGUI(String fileName) {
		if (fileName == null) { // The user specifies the file from a file chooser
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(this);
			try {
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					serviceMgr = new ServiceManager(new Scanner(fc.getSelectedFile()));
				}
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(this, "Cannot initialize vehicles from " + fc.getSelectedFile(), "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				endExecution();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Vehicle file not found. ", "Error", JOptionPane.ERROR_MESSAGE);
				endExecution();
			}
		} else { // file name is command-line parameter
			try {
				serviceMgr = new ServiceManager(new Scanner(new File(fileName)));
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(this, "Cannot initialize vehicles from " + fileName, "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				endExecution();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Vehicle file not found. ", "Error", JOptionPane.ERROR_MESSAGE);
				endExecution();
			}
		}
		setUpGUI();
	}

	// ------------ Private Methods ------------

	/**
	 * Sets up the GUI framework, adds listeners.
	 */
	private void setUpGUI() {
		// Construct the main window.
		setTitle(TITLE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.LINE_AXIS));
		setUpPanels();
		loadModel(lstWaitingCars, dlmWaitingCars, serviceMgr.printWaitList(""));
		loadModel(lstGarageBays, dlmGarageBays, serviceMgr.printServiceBays());
		lstWaitingCars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstGarageBays.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Add the waiting room and ward panels to the main window
		c.add(pnlWaitingCars);
		c.add(pnlBays);

		addListeners();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Sets up the panels that comprise the GUI.
	 */
	private void setUpPanels() {
		pnlWaitTop.setLayout(new BoxLayout(pnlWaitTop, BoxLayout.PAGE_AXIS));
		pnlBayTop.setLayout(new BoxLayout(pnlBayTop, BoxLayout.PAGE_AXIS));

		btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRemove.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlWaitTop.add(btnAdd);
		pnlWaitTop.add(btnEdit);
		pnlWaitTop.add(btnRemove);
		pnlFilter.add(lblFilter);
		pnlFilter.add(txtFilter);
		pnlFilter.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlWaitTop.add(pnlFilter);

		// Put the cars/bays in scrolling text areas
		scpWaitingCars.setBorder(BorderFactory.createLineBorder(Color.black));
		scpGarageBays.setBorder(BorderFactory.createLineBorder(Color.black));
		lstWaitingCars.setFont(new Font("monospaced", Font.PLAIN, 12));
		lstGarageBays.setFont(new Font("monospaced", Font.PLAIN, 12));

		// Set up "waiting lot" panels for left side of the window
		pnlWaitingCars.add(pnlWaitTop, BorderLayout.NORTH);
		pnlWaitingCars.add(scpWaitingCars, BorderLayout.CENTER);

		// Set up "bays" buttons
		btnAddBay.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFill.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRelease.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);

		pnlBayTop.add(btnAddBay);
		pnlBayTop.add(btnFill);
		pnlBayTop.add(btnRelease);
		pnlBayTop.add(new JLabel(" "));
		pnlBays.add(pnlBayTop, BorderLayout.NORTH);
		pnlBays.add(scpGarageBays, BorderLayout.CENTER);
		pnlBays.add(btnQuit, BorderLayout.SOUTH);

		pnlWaitingCars.setBorder(BorderFactory.createTitledBorder(WAITING_ROOM));
		pnlBays.setBorder(BorderFactory.createTitledBorder(GARAGE));
	}

// ---- Methods to handle event processing ---------

	/* Adds action listeners to all the buttons. */
	private void addListeners() {
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnRemove.addActionListener(this);
		btnQuit.addActionListener(this);
		btnAddBay.addActionListener(this);
		btnFill.addActionListener(this);
		btnRelease.addActionListener(this);
		txtFilter.addActionListener(this);
	}

	/**
	 * Determines the actions for each button click.
	 * 
	 * @param e ActionEvent to perform
	 */
	public void actionPerformed(ActionEvent e) {
		filter = txtFilter.getText();
		if (filter == null)
			filter = "";
		else
			filter = filter.trim();
		if (e.getSource().equals(btnAdd)) {
			UserDialog paneTmp = new UserDialog();
			paneTmp.setVisible(true);
			Vehicle v = paneTmp.getNewVehicle();
			if (v != null)
				serviceMgr.putOnWaitingList(v);
		} else if (e.getSource().equals(btnEdit)) {
			doEdit();
		} else if (e.getSource().equals(btnRemove)) {
			doRemove();
		} else if (e.getSource().equals(btnAddBay)) {
			doAddBay();
		} else if (e.getSource().equals(btnFill)) {
			doFill();
		} else if (e.getSource().equals(btnRelease)) {
			doRelease();
		} else if (e.getSource().equals(btnQuit)) {
			System.exit(0);
		}

		loadModel(lstWaitingCars, dlmWaitingCars, serviceMgr.printWaitList(filter));
		loadModel(lstGarageBays, dlmGarageBays, serviceMgr.printServiceBays());
	}

	/**
	 * Private Method - loads a list model from a string using newline tokenizers.
	 * 
	 * @param j    the JList to refresh
	 * @param m    the default list model associated with j
	 * @param info the String whose tokens initialize the default list model
	 */
	private void loadModel(JList<String> j, DefaultListModel<String> m, String info) {
		m.clear();
		if (info == null)
			return;
		StringTokenizer st = new StringTokenizer(info, "\n");
		while (st.hasMoreTokens()) {
			m.addElement(st.nextToken());
		}
		j.ensureIndexIsVisible(0);
	}

	/**
	 * Fills the service bays with available, appropriate vehicles
	 */
	private void doFill() {
		serviceMgr.fillServiceBays();
	}

	/**
	 * Opens a new service bay
	 */
	private void doAddBay() {
		serviceMgr.addNewBay();
	}

	/**
	 * Releases the selected vehicle from the garage
	 */
	private void doRelease() {
		int index = lstGarageBays.getSelectedIndex();
		serviceMgr.releaseFromService(index);
	}

	/* Implements edit operation. */
	private void doEdit() {
		int psn = lstWaitingCars.getSelectedIndex();
		UserDialog paneTmp = new UserDialog(serviceMgr.getWaitingItem(filter, psn));
		paneTmp.setVisible(true);
		Vehicle v = paneTmp.getNewVehicle();
		if (v != null) {
			serviceMgr.remove(filter, psn);
			serviceMgr.putOnWaitingList(v);
		}
	}

	/* Implements remove operation. */
	private void doRemove() {
		int psn = lstWaitingCars.getSelectedIndex();
		serviceMgr.remove(filter, psn);
	}

	/**
	 * Private Method - Ends execution of the program.
	 */
	private void endExecution() {
		System.exit(0);
	}

// --- Start up methods ---------

	/**
	 * Startup method for the application. Creates the GUI.
	 * 
	 * @param args args[0] is the name of the input text file.
	 */
	public static void main(String[] args) {
		if (args.length > 0) {
			new RepairGarageGUI(args[0]);
		} else
			new RepairGarageGUI(null);

	}
}
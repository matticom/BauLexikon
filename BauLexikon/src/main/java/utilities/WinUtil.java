package utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.Border;

import enums.Language;

public class WinUtil {

	public static enum MenuItemType {
		ITEM_PLAIN, ITEM_CHECK, ITEM_RADIO
	}

	public static final Color DARK_WHITE = new Color(230, 230, 230);
	public static final Color ULTRA_LIGHT_GRAY = new Color(200, 200, 200);
	public static final Color DARKER_GRAY = new Color(80, 80, 80);
	public static final Color ULTRA_DARK_GRAY = new Color(50, 50, 50);
	public static final Color LIGHT_BLACK = new Color(30, 30, 30);
	public static final Color COOL_BLUE = new Color(0, 178, 238);
	public static final Color STRONG_ORANGE = new Color(255, 140, 0);
	public static final Color GRASS_GREEN = new Color(127, 255, 0);
	public static final Color HOT_RED = new Color(193, 27, 23);
	

	// Verhinder, dass eine Instanz dieser Klasse erstellt werden kann.
	// Diese Klasse enth�lt nur �ffentliche und statische Methoden.
	private WinUtil() {

	}

	/**
	 * <li><b><i>createMenu</i></b> <br>
	 * <br>
	 * public JMenu createMenu(JMenuBar menuBar, String menuText, String
	 * menuName, int shortKey) <br>
	 * <br>
	 * Erstellt einen Men�. <br>
	 * <br>
	 * 
	 * @param menuBar
	 *            - Die Men�leiste, zu dem dieses Men� geh�rt.
	 * @param menuText
	 *            - Der Text des Men�s.
	 * @param menuName
	 *            - Optionaler Name des Men�s oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturk�rzel oder <b>0</b>.
	 * @return Men�.
	 */

	public static JMenu createMenu(JMenuBar menuBar, String menuText, String menuName, int shortKey) {

		JMenu menu = null;

		// Hinzuf�gen des Men�s
		menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);
		menu.setForeground(DARK_WHITE);

		// Optionales Tastaturk�rzen hinzuf�gen
		if (shortKey > 0)
			menu.setMnemonic(shortKey);

		// Men� zur Menubar hin zuf�gen
		menuBar.add(menu);

		// R�ckgabe des Men�s.
		return menu;
	}

	/**
	 * <li><b><i>createMenuItem</i></b> <br>
	 * <br>
	 * public JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType
	 * miType, ActionListener actionListener,<br>
	 * &nbsp String miText, ImageIcon icon, int shortKey, String miToolTip) <br>
	 * <br>
	 * Erstellt einen Men�eintrag. <br>
	 * <br>
	 * 
	 * @param menu
	 *            - Das Men�, zu dem dieser Men�eintrag geh�rt.
	 * @param miName
	 *            - Optionaler Name des Men�eintrags oder <b>nulMenul</b>.
	 * @param miType
	 *            - Der Typ des Men�eintrags <b>MenuItemType</b>.
	 * @param actionListener
	 *            - Der ActionListener, der verwendet werden soll, wenn der
	 *            Men�eintrag ausgew�hlt wurde oder <b>null</b>.
	 * @param miText
	 *            - Der Text des Men�eintrags.
	 * @param icon
	 *            - Symbol, welches links vor dem Text angezeigt werden soll
	 *            oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturk�rzel oder <b>0</b>.
	 * @param miToolTip
	 *            - Optionaler Text f�r den Tooltip oder <b>null</b>.
	 * @return Men�eintrag.
	 */
	public static JMenuItem createMenuItem(JMenu menu, String miName, MenuItemType miType, ActionListener actionListener, String miText,
			ImageIcon icon, int shortKey, String miToolTip) {

		// Men� Item erstellen
		JMenuItem menuItem = new JMenuItem();

		switch (miType) {
		case ITEM_RADIO:
			menuItem = new JRadioButtonMenuItem();
			break;

		case ITEM_CHECK:
			menuItem = new JCheckBoxMenuItem();
			break;
		}

		// Name des Men�eintrags
		menuItem.setName(miName);
		
		// Men� Text hinzuf�gen
		menuItem.setText(miText);

		// Optionales Image
		menuItem.setIcon(icon);

		// Optionales Tastaturk�rzel �bernehmen
		if (shortKey > 0)
			menuItem.setMnemonic(shortKey);

		// Optionalen Tooltip hinzuf�gen
		menuItem.setToolTipText(miToolTip);

		// ActionListener hinzuf�gen
		menuItem.addActionListener(actionListener);

		// Men�eintrag zum Men� hinzuf�gen
		menu.add(menuItem);

		// R�ckgabe des Menueintrags
		return menuItem;
	}

	/**
	 * <li><b><i>createSubMenu</i></b> <br>
	 * <br>
	 * public JMenu createSubMenu(JMenu mainMenu, String menuText, String
	 * menuName, int shortKey) <br>
	 * <br>
	 * Erstellt ein Untermen�. <br>
	 * <br>
	 * 
	 * @param mainMenu
	 *            - Das Men�, zu dem das Untermen� hinzugef�gt werden soll.
	 * @param menuText
	 *            - Der Text des Men�s.
	 * @param menuName
	 *            - Optionaler Name des Untermen�s oder <b>null</b>.
	 * @param shortKey
	 *            - Optionales Tastaturk�rzel oder <b>0</b>.
	 * @return - Untermen�.
	 */
	public static JMenu createSubMenu(JMenu mainMenu, String menuText, String menuName, int shortKey) {

		// Hinzuf�gen des Men�s als Untermen� des Hauptmen�s
		JMenu menu = new JMenu();
		menu.setText(menuText);
		menu.setName(menuName);

		// Optionales Tastaturk�rzel hinzuf�gen
		if (shortKey > 0)
			menu.setMnemonic(shortKey);

		mainMenu.add(menu);

		return menu;

	}

	/**
	 * <li><b><i>createButton</i></b> <br>
	 * <br>
	 * public JButton createButton(String buttonText, int x, int y, int width,
	 * int height, Border border, Color background, <br>
	 * &nbsp ActionListener listener, String btnName, String btnToolTip, boolean
	 * setOpaque, boolean setFocusPainted, Color foreground) <br>
	 * <br>
	 * Erstellt einen Button. <br>
	 * <br>
	 * 
	 * @param buttonText
	 *            - Beschriftung des Buttons
	 * @param x
	 *            - Optionale x-Koordinate des Setzpunktes <b>0</b>.
	 * @param y
	 *            - Optionale y-Koordinate des Setzpunktes <b>0</b>.
	 * @param width
	 *            - Optionale Breite des Buttons <b>0</b>.
	 * @param height
	 *            - Optionale H�he des Buttons <b>0</b>.
	 * @param border
	 *            - Optionale Umrahmung des Buttons <b>null</b>.
	 * @param background
	 *            - Optionale Setzt Hintergrundfarbe <b>null</b>.
	 * @param shortKey
	 *            - Optionale Hintergrundfarbe des Buttons <b>null</b>.
	 * @param actionListener
	 *            - ActionListener
	 * @param btnName
	 *            - Name des Buttons
	 * @param btnToolTip
	 *            - ToolTip des Buttons
	 * @param setOpaque
	 *            - Hat der Button einen Hintergrund
	 * @param setFocusPainted
	 *            - Soll der Focus angezeigt werden
	 * @param foreground
	 *            - Optionale Setzt Schriftfarbe <b>null</b>.
	 * @return - JButton.
	 */
	public static JButton createButton(String buttonText, int x, int y, int width, int height, Border border, Color background,
			ActionListener actionListener, String btnName, String btnToolTip, boolean setOpaque, boolean setFocusPainted, Color foreground) {
		
		JButton btn = new JButton(buttonText);

		if (x != 0 && y != 0 && width != 0 && height != 0)
			btn.setBounds(x, y, width, height);

		if (border != null)
			btn.setBorder(border);

		if (background != null)
			btn.setBackground(background);

		btn.addActionListener(actionListener);
		btn.setName(btnName);
		btn.setToolTipText(btnToolTip);
		btn.setOpaque(setOpaque);
		btn.setFocusPainted(setFocusPainted);

		if (foreground != null)
			btn.setForeground(foreground);

		return btn;

	}

	public static void configButton(JButton button, int width, int height, Border border, Color foreground, Color background) {

		if (border != null)
			button.setBorder(border);

		if (background != null)
			button.setBackground(background);

		button.setOpaque(true);
		button.setFocusPainted(false);

		if (foreground != null)
			button.setForeground(foreground);

		button.setPreferredSize(new Dimension(width, height));
	}
	
	public static void configStaticButton(JButton button, int x, int y, int width, int height, Border border, Color foreground, Color background) {

		if (border != null)
			button.setBorder(border);

		if (background != null)
			button.setBackground(background);

		button.setOpaque(true);
		button.setFocusPainted(false);

		if (foreground != null)
			button.setForeground(foreground);

		button.setBounds(x, y, width, height);
	}

	
	public static JLabel createLabel(String LblText, int x, int y, int width, int height, Border border, Color background, String lblName,
			String lblToolTip, Color foreground) {

		JLabel lbl = new JLabel(LblText);

		if (x != 0 && y != 0 && width != 0 && height != 0)
			lbl.setBounds(x, y, width, height);

		if (border != null)
			lbl.setBorder(border);

		if (background != null)
			lbl.setBackground(background);

		lbl.setName(lblName);
		lbl.setToolTipText(lblToolTip);

		if (foreground != null)
			lbl.setForeground(foreground);

		return lbl;

	}

	public static void configLabel(JLabel label, int width, int height, Color foreground, Color background, int fontSize, int fontStyle) {

		if (background != null)
			label.setBackground(background);

		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(fontStyle, fontSize));

		if (foreground != null)
			label.setForeground(foreground);
		if (width != 0 && height != 0) {
			label.setPreferredSize(new Dimension(width, height));
		}
	}
	
	public static void configStaticLabel(JLabel label, int x, int y, int width, int height, Color foreground, Color background, int fontSize, int fontStyle) {

		if (background != null)
			label.setBackground(background);

		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(fontStyle, fontSize));

		if (foreground != null)
			label.setForeground(foreground);
		label.setBounds(x, y, width, height);
	}

	public static void configSeparator(JSeparator jSeparator, int width, int height, Color foreground, Color background) {

		if (background != null)
			jSeparator.setBackground(background);

		jSeparator.setOpaque(true);

		if (foreground != null)
			jSeparator.setForeground(foreground);

		jSeparator.setPreferredSize(new Dimension(width, height));
	}

	public static int relH(int Height) {
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int)(displaySize.getHeight() * Height / 1200);
	}
	
	public static int relW(int Width) {
		Dimension displaySize = Toolkit.getDefaultToolkit().getScreenSize();
		return (int)(displaySize.getWidth() * Width / 1920);
	}
	
	public static void configScrollPane(JScrollPane scrollPane, JTextArea textArea, int width, int height) {
		textArea.setMargin(new Insets(3, 3, 3, 3));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setPreferredSize(new Dimension(width, height));
	}
	
	public static void configScrollPaneExtended(JScrollPane scrollPane, JTextArea textArea, int width, int height, Color foreground, Color background, Border border, boolean editable) {
		textArea.setForeground(foreground);
		textArea.setBackground(background);	
		textArea.setEditable(editable);
		textArea.setMargin(new Insets(3, 3, 3, 3));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setPreferredSize(new Dimension(width, height));
		scrollPane.setBorder(border);
	}
	
	public static void configCheckBox(JCheckBox checkbox, int width, int height, Color foreground, ItemListener listener, int horizontalAlignment) {
		checkbox.setPreferredSize(new Dimension(width, height));
		checkbox.setOpaque(false);
		checkbox.addItemListener(listener);
		checkbox.setForeground(foreground);
		checkbox.setFocusPainted(false);
		checkbox.setHorizontalAlignment(horizontalAlignment);
	}
	
	public static void configTextField(JTextField textField, int width, int height, Color background, boolean editable) {
		textField.setBackground(background);
		textField.setPreferredSize(new Dimension(width, height));
		textField.setMargin(new Insets(0, 3, 0, 0));
		textField.setEditable(editable);
	}
	
	public static void configTextFieldExtended(JTextField textField, int width, int height, Color foreground, Color background, Border border, boolean editable) {
		textField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(0, 3, 0, 0)));
		textField.setForeground(foreground);
		textField.setBackground(background);
		textField.setPreferredSize(new Dimension(width, height));
		textField.setEditable(editable);
	}
	
	
	public static int getLanguageId(ResourceBundle languageBundle) {
				
		final int GERMAN = 1;
		final int SPANISH = 2;
		
		if (languageBundle.getLocale().equals(new Locale("de"))) {
			return GERMAN;
		} else {
			return SPANISH;
		}
	}
	
	public static Language getLanguage(ResourceBundle languageBundle) {
		
		if (languageBundle.getLocale().equals(new Locale("de"))) {
			return Language.GERMAN;
		} else {
			return Language.SPANISH;
		}
	}

	
	// deutsche Inhaltstextarea
//	taTextDE = new JTextArea();
//	taTextDE.setMargin(new Insets(3, 3, 3, 3));
//	taTextDE.setLineWrap(true);
//	taTextDE.setWrapStyleWord(true);
//
//	// deutsche ScrollPane f�r deutsche Inhaltstextarea
//	spDE = new JScrollPane(taTextDE);
//	spDE.setBounds(50, 280, 350, 360);
//	this.getContentPane().add(spDE);
}

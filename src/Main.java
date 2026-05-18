import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//Custom exception thrown when a required input field is empty or null.
class EmptyFieldException extends Exception {
    public EmptyFieldException(String message) {
        super(message);
    }
}

class InvalidRollNumberException extends Exception {
    public InvalidRollNumberException(String message) {
        super(message);
    }
}

class InvalidDateException extends Exception {
    public InvalidDateException(String message) {
        super(message);
    }
}

class NullSelectionException extends Exception {
    public NullSelectionException(String message) {
        super(message);
    }
}

class LibraryBookIssueSystem extends JFrame {

    JLabel lblName, lblRoll, lblBookTitle, lblCategory, lblIssueDate, lblReturnDate, lblRemarks, lblEdition;
    JTextField txtName, txtRoll, txtBookTitle, txtIssueDate, txtReturnDate;
    JTextArea txtRemarks;
    JComboBox<String> comboCategory;
    JRadioButton rbNew, rbOld;
    ButtonGroup editionGroup;
    JButton btnIssue, btnReset, btnExit;

    public LibraryBookIssueSystem() {

        setTitle("Library Book Issue System");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10));

        //  Creating and initializing all the components of the GUI
        lblName = new JLabel("Student Name:");
        lblRoll = new JLabel("Roll Number:");
        lblBookTitle = new JLabel("Book Title:");
        lblCategory = new JLabel("Book Category:");
        lblIssueDate = new JLabel("Issue Date (YYYY-MM-DD):");
        lblReturnDate = new JLabel("Return Date (YYYY-MM-DD):");
        lblRemarks = new JLabel("Remarks:");
        lblEdition = new JLabel("Book Edition:");

        //  Initializing text fields, text area, combo box, radio buttons, and buttons
        txtName = new JTextField();
        txtRoll = new JTextField();
        txtBookTitle = new JTextField();
        txtIssueDate = new JTextField();
        txtReturnDate = new JTextField();

        txtRemarks = new JTextArea(3, 20);

        String[] categories = {"--Select Category--", "Programming", "AI", "Databases", "Networking"};
        comboCategory = new JComboBox<>(categories);

        rbNew = new JRadioButton("New Edition");
        rbOld = new JRadioButton("Old Edition");

        editionGroup = new ButtonGroup();
        editionGroup.add(rbNew);
        editionGroup.add(rbOld);

        //  Panel to hold the radio buttons for book edition selection
        JPanel radioPanel = new JPanel();
        radioPanel.add(rbNew);
        radioPanel.add(rbOld);

        //  Initializing buttons and adding action listeners for their respective functionalities
        btnIssue = new JButton("Issue Book");
        btnReset = new JButton("Reset");
        btnExit = new JButton("Exit");

        btnIssue.addActionListener(e -> {
            try {
                String name = txtName.getText().trim();
                String roll = txtRoll.getText().trim();
                String bookTitle = txtBookTitle.getText().trim();
                String issueDateStr = txtIssueDate.getText().trim();
                String returnDateStr = txtReturnDate.getText().trim();
                //  Validating that all required fields are filled and contain valid data
                if (name.isEmpty() || roll.isEmpty() || bookTitle.isEmpty() || issueDateStr.isEmpty() || returnDateStr.isEmpty()) {
                    throw new EmptyFieldException("All text fields are required and cannot be empty!");
                }

                if (comboCategory.getSelectedIndex() == 0) {
                    throw new NullSelectionException("Please select a valid Book Category!");
                }
                if (!rbNew.isSelected() && !rbOld.isSelected()) {
                    throw new NullSelectionException("Please select a Book Edition (New or Old)!");
                }

                if (roll.matches(".*[a-zA-Z]+.*")) {
                    throw new InvalidRollNumberException("Roll number cannot contain alphabets!");
                }

                int rollNum;
                try {
                    rollNum = Integer.parseInt(roll);
                    if (rollNum <= 0) {
                        throw new InvalidRollNumberException("Roll number must be a positive integer!");
                    }
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("The roll number format is invalid or too large to process.");
                }

                LocalDate issueDate;
                LocalDate returnDate;
                try {
                    issueDate = LocalDate.parse(issueDateStr);
                    returnDate = LocalDate.parse(returnDateStr);

                    if (returnDate.isBefore(issueDate)) {
                        throw new InvalidDateException("Return date cannot be earlier than the issue date!");
                    }
                } catch (DateTimeParseException dtpe) {
                    throw new InvalidDateException("Dates must be in the valid format: YYYY-MM-DD.");
                }

                String edition = rbNew.isSelected() ? "New Edition" : "Old Edition";
                String category = comboCategory.getSelectedItem().toString();
                //  If all validations pass, display a success message with the entered details
                JOptionPane.showMessageDialog(this,
                        "Book Issued Successfully!\n\n"
                                + "Student Name: " + name + "\n"
                                + "Roll Number: " + rollNum + "\n"
                                + "Book Title: " + bookTitle + "\n"
                                + "Category: " + category + "\n"
                                + "Edition: " + edition + "\n"
                                + "Issue Date: " + issueDateStr + "\n"
                                + "Return Date: " + returnDateStr,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            }//  Catching specific exceptions related to validation and displaying appropriate error messages to the user
            catch (EmptyFieldException | NullSelectionException | InvalidRollNumberException | InvalidDateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Numeric Input Error: " + ex.getMessage(), "Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Critical Error", JOptionPane.ERROR_MESSAGE);
            }
            finally {
                JOptionPane.showMessageDialog(this, "Operation Completed: Data validation phase finished.", "System Status", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        //  Action listener for the reset button to clear all input fields and reset selections
        btnReset.addActionListener(e -> {
            txtName.setText("");
            txtRoll.setText("");
            txtBookTitle.setText("");
            txtIssueDate.setText("");
            txtReturnDate.setText("");
            txtRemarks.setText("");

            comboCategory.setSelectedIndex(0);
            editionGroup.clearSelection();
        });

        btnExit.addActionListener(e -> System.exit(0));

        add(lblName);
        add(txtName);

        add(lblRoll);
        add(txtRoll);

        add(lblBookTitle);
        add(txtBookTitle);

        add(lblCategory);
        add(comboCategory);

        add(lblEdition);
        add(radioPanel);

        add(lblIssueDate);
        add(txtIssueDate);

        add(lblReturnDate);
        add(txtReturnDate);

        add(lblRemarks);
        add(new JScrollPane(txtRemarks));

        add(btnIssue);
        add(btnReset);

        add(new JLabel());
        add(btnExit);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryBookIssueSystem());
    }
}
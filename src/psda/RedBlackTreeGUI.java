package psda;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RedBlackTreeGUI {

    private RedBlackTree redBlackTree;
    private JFrame frame; // frame ul aplicatiei
    private RedBlackTreePanel treePanel; // afisare rbt
    private JLabel statusLabel; 

    public RedBlackTreeGUI() {
        redBlackTree = new RedBlackTree();
        initializeUI();
    }
    
    // initializare frame, status, meniul cu butoane pt fiecare operatie
    
    private void initializeUI() {
        frame = new JFrame("Red-Black Tree GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        statusLabel = new JLabel("Status: Ready");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        treePanel = new RedBlackTreePanel(redBlackTree);
        JScrollPane scrollPane = new JScrollPane(treePanel);

        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        addButton(buttonPanel, "Insert", e -> showInputDialog("Insert"));
        addButton(buttonPanel, "Delete", e -> showInputDialog("Delete"));
        addButton(buttonPanel, "Search", e -> showInputDialog("Search"));
        addButton(buttonPanel, "Inorder Traversal", e -> performOperation("Inorder"));
        addButton(buttonPanel, "Preorder Traversal", e -> performOperation("Preorder"));
        addButton(buttonPanel, "Postorder Traversal", e -> performOperation("Postorder"));
        addButton(buttonPanel, "Print Tree", e -> performOperation("Print"));

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.WEST);
        frame.add(statusLabel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // adaugare butoane in frame
    
    private void addButton(JPanel panel, String title, ActionListener listener) {
        JButton button = new JButton(title);
        button.addActionListener(listener);
        panel.add(button);
    }
    
     // pt input prompt
    
    private void showInputDialog(String operation) {
        String inputValue = JOptionPane.showInputDialog(frame, "Enter value:");
        if (inputValue != null) {
            performOperation(operation, inputValue);
        }
    }
    
    // dialogul pt insert, delete, search 
    private void performOperation(String operation) {
        if (operation.equals("Insert") || operation.equals("Delete") || operation.equals("Search")) {
            showInputDialog(operation);
        } else {
            performOperation(operation, null);
        }
    }
    
    // realizatorul operatiilor din meniu
    private void performOperation(String operation, String input) {
        try {
            if (operation.equals("Insert") || operation.equals("Delete") || operation.equals("Search")) {
                int value = Integer.parseInt(input);
                switch (operation) {
                    case "Insert":
                        redBlackTree.insert(value);
                        statusLabel.setText("Inserted: " + value);
                        break;
                    case "Delete":
                        redBlackTree.deleteNode(value);
                        statusLabel.setText("Deleted: " + value);
                        break;
                    case "Search":
                        Node searchResult = redBlackTree.searchTree(value);
                        if (searchResult != redBlackTree.getTNULL()) {
                            statusLabel.setText("Found " + value + " in the tree.");
                        } else {
                            statusLabel.setText(value + " not found in the tree.");
                        }
                        break;
                }
            } else {
                switch (operation) {
                    case "Inorder":
                        statusLabel.setText("Inorder Traversal:\n" + redBlackTree.inorder());
                        break;
                    case "Preorder":
                        statusLabel.setText("Preorder Traversal:\n" + redBlackTree.preorder());
                        break;
                    case "Postorder":
                        statusLabel.setText("Postorder Traversal:\n" + redBlackTree.postorder());
                        break;
                    case "Print":
                        statusLabel.setText("Red-Black Tree Structure:\n" + redBlackTree.printTree());
                        break;
                }
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid Input. Please enter a valid integer.");
        } catch (Exception e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
        treePanel.repaint();
    }
    
    // creaza gui-ul si il reactualizeaza
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RedBlackTreeGUI::new);
    }
}
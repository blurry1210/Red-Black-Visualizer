package psda;

import javax.swing.*;
import java.awt.*;

public class RedBlackTreePanel extends JPanel {
    private RedBlackTree redBlackTree;

    public RedBlackTreePanel(RedBlackTree redBlackTree) {
        this.redBlackTree = redBlackTree;
        setPreferredSize(new Dimension(800, 600)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (redBlackTree.getRoot() != null) {
            drawTree(g, redBlackTree.getRoot(), getWidth() / 2, 30, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int horizGap) {
        if (node == null || node == redBlackTree.getTNULL()) {
            return;
        }

        // afisare nod stanga
        
        if (node.left != null && node.left != redBlackTree.getTNULL()) {
            g.drawLine(x, y, x - horizGap, y + 50);
            drawTree(g, node.left, x - horizGap, y + 50, horizGap / 2);
        }

        // afisare nod dreapta
        if (node.right != null && node.right != redBlackTree.getTNULL()) {
            g.drawLine(x, y, x + horizGap, y + 50);
            drawTree(g, node.right, x + horizGap, y + 50, horizGap / 2);
        }
 
        // afisare nod
        g.setColor(node.color == 1 ? Color.BLACK : Color.RED);
        g.fillOval(x - 15, y - 15, 30, 30);
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(node.data), x - 5, y + 5);
    }
}
package psda;


// structura pentru noduri

class Node {
    int data;
    Node parent;
    Node left;
    Node right;
    int color;

    public Node(int data) {
        this.data = data;
        this.color = 0;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
}
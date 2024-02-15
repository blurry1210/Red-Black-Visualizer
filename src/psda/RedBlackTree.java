package psda;

public class RedBlackTree {
    private Node root;
    private Node TNULL;

    public RedBlackTree() {
        TNULL = new Node(-1);
        TNULL.color = 1;
        TNULL.left = null;
        TNULL.right = null;
        root = TNULL;
    }

    public void insert(int key) {
        Node node = new Node(key);
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = 0;

        Node y = null;
        Node x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.data < y.data) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null){
            node.color = 1;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        fixInsert(node);
    }

    private void fixInsert(Node k){
        Node u;
        while (k.parent.color == 0) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.color == 0) {
                    u.color = 1;
                    k.parent.color = 1;
                    k.parent.parent.color = 0;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rotateRight(k);
                    }
                    k.parent.color = 1;
                    k.parent.parent.color = 0;
                    rotateLeft(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;

                if (u.color == 0) {
                    u.color = 1;
                    k.parent.color = 1;
                    k.parent.parent.color = 0;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        rotateLeft(k);
                    }
                    k.parent.color = 1;
                    k.parent.parent.color = 0;
                    rotateRight(k.parent.parent);
                }
            }
            if (k == root) {
                break;
            }
        }
        root.color = 1;
    }


    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }
    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;

        if (y.left != TNULL) {
            y.left.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }
    public Node searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    private Node searchTreeHelper(Node node, int key) {
        if (node == TNULL || key == node.data) {
            return node;
        }

        if (key < node.data) {
            return searchTreeHelper(node.left, key);
        }
        return searchTreeHelper(node.right, key);
    }
    public void deleteNode(int data) {
        deleteNodeHelper(this.root, data);
    }
    private void deleteNodeHelper(Node node, int key) {
        Node z = TNULL;
        Node x, y;

        while (node != TNULL){
            if (node.data == key) {
                z = node;
            }

            if (node.data <= key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }

        if (z == TNULL) {
            System.out.println("Nu s-a găsit un nod cu cheia: " + key);
            return;
        }

        y = z;
        int yOriginalColor = y.color;
        if (z.left == TNULL) {
            x = z.right;
            rbTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            rbTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                rbTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            rbTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == 1){
            fixDelete(x);
        }
    }
    private void rbTransplant(Node u, Node v){
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left){
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }
    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }
    private void fixDelete(Node x) {
        Node s;
        while (x != root && x.color == 1) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.color == 0) {
                    s.color = 1;
                    x.parent.color = 0;
                    rotateLeft(x.parent);
                    s = x.parent.right;
                }

                if (s.left.color == 1 && s.right.color == 1) {
                    s.color = 0;
                    x = x.parent;
                } else {
                    if (s.right.color == 1) {
                        s.left.color = 1;
                        s.color = 0;
                        rotateRight(s);
                        s = x.parent.right;
                    }

                    s.color = x.parent.color;
                    x.parent.color = 1;
                    s.right.color = 1;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.color == 0) {
                    s.color = 1;
                    x.parent.color = 0;
                    rotateRight(x.parent);
                    s = x.parent.left;
                }

                if (s.right.color == 1 && s.left.color == 1) {
                    s.color = 0;
                    x = x.parent;
                } else {
                    if (s.left.color == 1) {
                        s.right.color = 1;
                        s.color = 0;
                        rotateLeft(s);
                        s = x.parent.left;
                    }

                    s.color = x.parent.color;
                    x.parent.color = 1;
                    s.left.color = 1;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = 1;
    }
    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorderHelper(this.root, sb);
        return sb.toString();
    }

    private void inorderHelper(Node node, StringBuilder sb) {
        if (node != TNULL) {
            inorderHelper(node.left, sb);
            sb.append(node.data).append(" ");
            inorderHelper(node.right, sb);
        }
    }

    public String preorder() {
        StringBuilder sb = new StringBuilder();
        preorderHelper(this.root, sb);
        return sb.toString();
    }

    private void preorderHelper(Node node, StringBuilder sb) {
        if (node != TNULL) {
            sb.append(node.data).append(" ");
            preorderHelper(node.left, sb);
            preorderHelper(node.right, sb);
        }
    }

    public String postorder() {
        StringBuilder sb = new StringBuilder();
        postorderHelper(this.root, sb);
        return sb.toString();
    }

    private void postorderHelper(Node node, StringBuilder sb) {
        if (node != TNULL) {
            postorderHelper(node.left, sb);
            postorderHelper(node.right, sb);
            sb.append(node.data).append(" ");
        }
    }

    public String printTree() {
        StringBuilder sb = new StringBuilder();
        inorderHelper(this.root, sb);
        return sb.toString();
    }

    public Node getRoot() {
        return this.root;
    }
    public Node getTNULL() {
        return this.TNULL;
    }
}
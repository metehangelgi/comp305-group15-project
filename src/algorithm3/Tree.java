package algorithm3;

import java.util.ArrayList;
import java.util.List;

class Tree {

    class Node {
        int index;

        String key;
        int parentIndex;

        Node parent = null;
        List<Node> children;

        public Node(int index, String key, int parentIndex) {
            this.index = index;
            this.key = key;
            this.parentIndex = parentIndex;

            children = new ArrayList<>();
        }

        public void AddChild(Node child) {
            children.add(child);
        }
    }

    Node root;
    List<Node> nodes;

    Tree() {
        nodes = new ArrayList<>();

        // Imaginary father, first father
        root = new Node(0, "-", -1);
        nodes.add(root);
    }

    // O(n)
    void AddNode(int index, String key, int parentIndex) {
        Node node = new Node(index, key, parentIndex);
        nodes.add(node);
    }

    // O(n)
    void Create() {
        // O(n)
        for (Node node : nodes) {
            // Assign parent to nodes
            if (node.parentIndex != -1) {
                //System.out.print("Node: " + node.key + "  ");

                node.parent = nodes.get(node.parentIndex); // O(1)
                //System.out.println("Parent: " + node.parent.key);

                nodes.get(node.parentIndex).AddChild(node); // O(1)
            }
        }
    }

    void print() {
        System.out.println("Children Tree");
        for (Node node : nodes) {
            // Assign parent to nodes
            if (node.parentIndex != -1) {
                System.out.print("Node: " + node.key + "  ");

                System.out.print("Children: ");
                for (Node n : node.children) {
                    System.out.print(" " + n.key);
                }
                System.out.println();
            }
        }
    }

    int FindQuery(String[] query) {
        if (query.length == 1) {
            return FindSingleQuery(query[0]);
        } else {
            return Helper(query, nodes);
        }
    }

    private int FindSingleQuery(String q) {
        int result = 0;
        for (Node node : nodes) {
            if (node.key.equals(q)) {
                result++;
            }
        }
        return result;
    }

    private int Helper(String[] query, List<Node> nodes) {
        if (query.length == 0) {
            return 0;
        }
        int counter = 0;
        for (Node node : nodes) {
            if (node.key.equals(query[0])) {
                //System.out.println("Found " + node.key + " at " + node.index);
                if (CheckParent(query, node.parent)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private boolean CheckParent(String[] query, Node node) {
        if (query.length - 1 == 0 || node == null)
            return false;

        String[] newQuery = new String[query.length - 1];
        for (int i = 0; i < newQuery.length; i++) {
            newQuery[i] = query[i + 1];
        }

        if (node.key.equals(newQuery[0])) {
            if (newQuery.length == 1) {
                //System.out.println("Found " + node.key + " at " + node.index);
                return true;
            } else if (newQuery.length > 1) {
                return CheckParent(newQuery, node.parent);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

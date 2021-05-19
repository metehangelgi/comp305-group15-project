package algorithm4;

import java.util.ArrayList;
import java.util.List;

public class GenericTree {

    class Node {
        int index;

        String key;
        int parentIndex;
        Letter letter;
        Node parent = null;
        List<Node> children;

        public Node(int index, String key, int parentIndex) {
            this.index = index;
            this.key = key;
            letter=new Letter(key.toCharArray()[0]);
            this.parentIndex = parentIndex;

            children = new ArrayList<>();
        }

        public Letter getLetter(){
            return letter;
        }

        public void AddChild(Node child) {
            children.add(child);
        }
    }


    Node root;
    List<Node> nodes;
    DataStructure dataStructure;
    GenericTree(DataStructure dataStructure) {
        nodes = new ArrayList<>();
        this.dataStructure=dataStructure;
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
                node.parent = nodes.get(node.parentIndex); // O(1)
                nodes.get(node.parentIndex).AddChild(node); // O(1)
                if(node.parentIndex==0){
                    dataStructure.addElement(node.letter,null);
                }else {
                    dataStructure.addElement(node.letter,node.parent.letter);
                }
            }
        }
    }
}

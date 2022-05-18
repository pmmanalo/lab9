import java.util.ArrayList;
import java.util.List;

/**
 * Lab09: Binary Binary Tree
 *
 * @author [Paulo Manalo]
 * @date []
 */

public class BBTree {

    TreeNode root;
    int size;

    /**
     * Nested inner TreeNode that is used to construct our BBTree
     */
    class TreeNode {
        int data;
        TreeNode right;
        TreeNode left;

        public TreeNode(int elementsToAdd) {
            this.data = elementsToAdd; //adding to tree
            right = left = null;
        }
    }

    /**
     * Constructor that initializes the Binary Binary Tree that takes no parameters.
     */
    public BBTree() {
        root = null; //setting to null
    }

    /**
     * Adds all elements within the given int array into the Binary Binary Tree by utilizing the add method above.
     *
     * @param int array of 0s and 1s to be added into the tree
     * @return boolean true if the construction of the tree was successful, false otherwise.
     * @throws NullPointerException when the int array is an empty array
     */
    public boolean addAll(int[] elementsToAdd) {
        root=new TreeNode(0);
        return addAll(elementsToAdd, root, 0);
    }

    /**
     * Helper method to add all elements within the given int array into the Binary Binary Tree by utilizing the add method above.
     *
     * @param int array of 0s and 1s to be added into the tree
     * @return boolean true if the construction of the tree was successful, false otherwise.
     * @throws NullPointerException when the int array is an empty array
     */
    private boolean addAll(int[] elementsToAdd, TreeNode root, int level) {
        if (elementsToAdd == null || elementsToAdd.length == 0) {
            throw new NullPointerException(); //checking for validation
        }

        TreeNode[] treeNodes = new TreeNode[elementsToAdd.length];

        for (int i = 0; i < elementsToAdd.length; i++) { //looping 
            treeNodes[i] = new TreeNode(elementsToAdd[i]);
        }

        for (int i = 0; i < treeNodes.length; i++) { 
            if (2 * i + 1 < treeNodes.length) { //if statement to set left of tree
                treeNodes[i].left = treeNodes[2 * i + 1];
            }

            if (2 * i + 2 < treeNodes.length) {  //if statement to set right of tree
                treeNodes[i].right = treeNodes[2 * i + 2];
            }
        }

        root.data = treeNodes[0].data;
        root.left = treeNodes[0].left;
        root.right = treeNodes[0].right;

        size=treeNodes.length;

        return true;
    }


    /**
     * This method returns lowest depth of the tree.
     *
     * @return int of the lowest depth of the tree
     */
    public int depthOfTree() {
        return depthOfTree(root);
    }

    /**
     * Helper method returns lowest depth of the tree.
     *
     * @return int of the lowest depth of the tree
     */
    public int depthOfTree(TreeNode root) {
        int depth = -1;
        TreeNode curr = root;
        while (curr != null) { //looping while curr  is not equal to null we set traverse while counting the loops and return the value
            depth++;
            curr = curr.left;
        }

        return depth;
    }

    /**
     * This method determines if a given TreeNode is a leaf node.
     *
     * @param node to evaluate
     * @return boolean true if node is a leaf node, false otherwise
     */
    public boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }

    /**
     * This method determines if a given number is a valid number within the Binary Binary Tree.
     *
     * @param integer of type string to compare to
     * @return boolean true if valid single digit int exists, false otherwise
     */
    public boolean isValidNumber(String integer) {
        TreeNode curr=root; //setting current to root

        if(integer.charAt(0)-'0'!= root.data){ //comparison of string to node data
            return false;
        }

        for (int i = 1; i < integer.length(); i++) { //looping to compare values in tree
            if(integer.charAt(0)-'0'== curr.left.data){
                curr=curr.left;
            }else if(integer.charAt(0)-'0'== curr.right.data){
                curr=curr.right;
            }else{
                return false;
            }
        }

        return true;
    }

    /**
     * This public method returns a list of all strings representing valid binary numbers from the tree.
     *
     * @param root of the tree
     * @return boolean true if valid single digit int exists, false otherwise
     */
    public List<String> allValidNums() {
        return allValidNums(root);
    }

    /**
     * Helper method returns a list of all strings representing valid binary numbers from the tree.
     *
     * @param root of the tree
     * @return boolean true if valid single digit int exists, false otherwise
     */
    private List<String> allValidNums(TreeNode root) {
        List<String> allNums = new ArrayList<>();

        dfs(allNums, root, String.valueOf(root.data));

        return allNums;
    }

    /**
     * Dfs to get all valid nums
     *
     * @param list where all valid nums will be stored
     * @param node starting node
     * @param num got so far
     */
    private void dfs(List<String> list, TreeNode node, String num) {
        if (isLeaf(node)) {
            list.add(num);
        } else {
            if (node.left != null) dfs(list, node.left, num + node.left.data);
            if (node.right != null) dfs(list, node.right, num + node.right.data);
        }
    }

    /**
     * This public method returns the full secret integer that was encoded in the int array.
     * This is the method that will be called in main.
     *
     * @param TreeNode that is the root of the Binary Binary Tree
     * @return int secret integer in the tree
     * @see Integer.valueOf(String s, int radix) to convert from a binary number
     */
    public int revealSecretNumber() {
        return revealSecretNumber(root);
    }

    /**
     * This private method returns the full secret integer that was encoded in the int array.
     *
     * @param TreeNode that is the root of the Binary Binary Tree
     * @return int secret integer in the tree
     * @see Integer.valueOf(String s, int radix) to convert from a binary number
     */
    private int revealSecretNumber(TreeNode root) {
        List<String> list=allValidNums(root);

        int secret=0;

        for (String s : list) {
            int decimal=Integer.parseInt(s,2);
            secret+=decimal;
        }

        return secret;
    }

    /**
     * This method prints a readable binary binary tree by level; calls printCurrentLevel().
     * Warning: will not work until you implement depthOfTree() correctly.
     */
    public void printByLevel() {
        int h = depthOfTree(root);
        int i;
        for (i = 1; i <= h; i++) { //loop to print
            printCurrentLevel(root, i);
            System.out.println();
        }
    }

    /**
     * This method is called by printByLevel() and prints each level in the binary tree.
     *
     * @param TreeNode that is the root of the Binary Binary Tree
     * @param int      level of the tree current at.
     */
    public void printCurrentLevel(TreeNode root, int level) {
        if (root == null)
            return;
        if (level == 1) System.out.print(root.data + " ");
        else if (level > 1) {
            printCurrentLevel(root.left, level - 1);
            printCurrentLevel(root.right, level - 1);
        }
    }

    /**
     * Main method for testing your TreeNode and BBTree class
     */
    public static void main(String[] args) {
        BBTree bt = new BBTree();
        int[] arr = {1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1};
        bt.addAll(arr);
        bt.printByLevel();
        System.out.println("----------------------------");
        System.out.println("Actual Tree Node Count: " + bt.size);
        System.out.println("Expected Tree Node Count: " + 13);
        System.out.println("----------------------------");
        System.out.println("Actual Tree Depth Count: " + bt.depthOfTree());
        System.out.println("Expected Tree Depth Count: " + 3);
        System.out.println("----------------------------");
        System.out.println("Actual Secret Number: " + bt.revealSecretNumber());
        System.out.println("Expected Secret Number: " + 81);
        System.out.println("----------------------------");
    }
}

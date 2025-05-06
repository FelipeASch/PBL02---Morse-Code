package com.example.morsecoder1;

public class Node {
    public char letter;
    public Node left;
    public Node right;

    public Node(char letter) {
        this.letter = letter;
        this.left = null;
        this.right = null;
    }
}

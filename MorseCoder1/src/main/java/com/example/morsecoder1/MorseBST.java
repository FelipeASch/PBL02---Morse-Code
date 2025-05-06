package com.example.morsecoder1;

public class MorseBST {
    private Node root;

    public MorseBST() {
        root = new Node(' ');
    }

    public void insert(char letter, String morseCode) {
        Node current = root;
        for (char c : morseCode.toCharArray()) {
            if (c == '.') {
                if (current.left == null) {
                    current.left = new Node(' ');
                }
                current = current.left;
            } else if (c == '-') {
                if (current.right == null) {
                    current.right = new Node(' ');
                }
                current = current.right;
            }
        }
        current.letter = Character.toUpperCase(letter);
    }

    public String encode(char letter) {
        return encodeHelper(root, Character.toUpperCase(letter), "");
    }

    private String encodeHelper(Node node, char target, String path) {
        if (node == null) return null;
        if (node.letter == target) return path;

        String leftPath = encodeHelper(node.left, target, path + ".");
        if (leftPath != null) return leftPath;

        return encodeHelper(node.right, target, path + "-");
    }

    public String encodeMessage(String message) {
        StringBuilder encoded = new StringBuilder();
        for (char c : message.toUpperCase().toCharArray()) {
            if (c == ' ') {
                encoded.append(" / "); // separador de palavras
            } else {
                String morse = encode(c);
                encoded.append(morse != null ? morse : "?").append(" ");
            }
        }
        return encoded.toString().trim();
    }

    public char decode(String morseCode) {
        Node current = root;
        for (char c : morseCode.toCharArray()) {
            if (c == '.') {
                if (current.left != null) {
                    current = current.left;
                } else {
                    return '?';
                }
            } else if (c == '-') {
                if (current.right != null) {
                    current = current.right;
                } else {
                    return '?';
                }
            }
        }
        return current.letter != ' ' ? current.letter : '?';
    }

    public String decodeMessage(String message) {
        StringBuilder decoded = new StringBuilder();
        String[] words = message.trim().split(" / ");
        for (String word : words) {
            for (String symbol : word.trim().split(" ")) {
                if (!symbol.isEmpty()) {
                    decoded.append(decode(symbol));
                }
            }
            decoded.append(" ");
        }
        return decoded.toString().trim();
    }

    public Node getRoot() {
        return root;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}

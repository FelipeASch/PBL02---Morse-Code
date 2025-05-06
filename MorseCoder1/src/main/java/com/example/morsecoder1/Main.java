package com.example.morsecoder1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private MorseBST bst = new MorseBST();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Inicial - MorseCoder");

        Button viewTreeBtn = new Button("Ver Árvore / Codificar / Decodificar");
        Button manageTreeBtn = new Button("Adicionar Caracteres");

        VBox menuLayout = new VBox(15, viewTreeBtn, manageTreeBtn);
        menuLayout.setPadding(new Insets(20));
        Scene menuScene = new Scene(menuLayout, 400, 150);

        // Botão "Ver árvore"
        viewTreeBtn.setOnAction(e -> showTreeScene(primaryStage, menuScene));

        // Botão "Adicionar caracteres"
        manageTreeBtn.setOnAction(e -> showAddCharScene(primaryStage, menuScene));

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void showTreeScene(Stage stage, Scene menuScene) {
        int height = bst.getHeight();
        int canvasHeight = 100 + height * 100;
        int canvasWidth = (int) Math.pow(2, height) * 40;
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        drawTree(canvas.getGraphicsContext2D(), bst.getRoot(), canvasWidth / 2, 40, canvasWidth / 4, 1);

        TextField textToEncode = new TextField();
        textToEncode.setPromptText("Digite o texto para codificar");

        Button encodeButton = new Button("Codificar");
        TextArea encodedOutput = new TextArea();
        encodedOutput.setEditable(false);
        encodedOutput.setWrapText(true);
        encodedOutput.setPromptText("Código morse aparecerá aqui");

        encodeButton.setOnAction(e -> {
            String input = textToEncode.getText().toUpperCase();
            String morse = bst.encodeMessage(input);
            encodedOutput.setText(morse);
        });

        TextField input = new TextField();
        input.setPromptText("Digite o código morse (com espaços)");
        Label output = new Label();
        Button decodeBtn = new Button("Decodificar");
        decodeBtn.setOnAction(e -> output.setText(bst.decodeMessage(input.getText())));

        Button backBtn = new Button("Voltar ao Menu");
        backBtn.setOnAction(e -> stage.setScene(menuScene));

        VBox layout = new VBox(10, canvas, textToEncode, encodeButton, encodedOutput, input, decodeBtn, output, backBtn);
        layout.setPadding(new Insets(10));
        layout.setPrefWidth(canvasWidth);

        stage.setScene(new Scene(new ScrollPane(layout), 800, 600));
    }

    private void showAddCharScene(Stage stage, Scene menuScene) {
        TextField letterInput = new TextField();
        letterInput.setPromptText("Letras (A-Z) ou Números (0-9)");

        TextField morseInput = new TextField();
        morseInput.setPromptText("Código Morse (. e -)");

        Button addButton = new Button("Adicionar caractere");
        Label status = new Label();

        addButton.setOnAction(e -> {
            String letter = letterInput.getText().toUpperCase();
            String morse = morseInput.getText();
            if (letter.length() == 1 && morse.matches("[.-]+")) {
                bst.insert(letter.charAt(0), morse);
                status.setText("Caractere adicionado: " + letter + " = " + morse);
                letterInput.clear();
                morseInput.clear();
            } else {
                status.setText("Entrada inválida.");
            }
        });

        Button populateBtn = new Button("Popular com tabela padrão");
        populateBtn.setOnAction(e -> {
            String[][] morseTable = {
                    {"A", ".-"}, {"B", "-..."}, {"C", "-.-."}, {"D", "-.."},
                    {"E", "."}, {"F", "..-."}, {"G", "--."}, {"H", "...."},
                    {"I", ".."}, {"J", ".---"}, {"K", "-.-"}, {"L", ".-.."},
                    {"M", "--"}, {"N", "-."}, {"O", "---"}, {"P", ".--."},
                    {"Q", "--.-"}, {"R", ".-."}, {"S", "..."}, {"T", "-"},
                    {"U", "..-"}, {"V", "...-"}, {"W", ".--"}, {"X", "-..-"},
                    {"Y", "-.--"}, {"Z", "--.."}
            };
            for (String[] pair : morseTable) {
                bst.insert(pair[0].charAt(0), pair[1]);
            }
            status.setText("Árvore populada com tabela padrão.");
        });

        Button backBtn = new Button("Voltar ao Menu");
        backBtn.setOnAction(e -> stage.setScene(menuScene));

        VBox layout = new VBox(10, letterInput, morseInput, addButton, populateBtn, status, backBtn);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 400, 300));
    }

    private void drawTree(GraphicsContext gc, Node node, double x, double y, double xOffset, int level) {
        if (node == null) return;

        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 15, y - 15, 30, 30);
        gc.strokeText(String.valueOf(node.letter == ' ' ? ' ' : node.letter), x - 5, y + 5);

        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + 120;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawTree(gc, node.left, newX, newY, xOffset / 2, level + 1);
        }

        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + 120;
            gc.strokeLine(x, y + 15, newX, newY - 15);
            drawTree(gc, node.right, newX, newY, xOffset / 2, level + 1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

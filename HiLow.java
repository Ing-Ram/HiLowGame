import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HiLow extends Application {

		    private static final String CARD_CSS =
		        "-fx-background-color: purple;" +
		        "-fx-background-radius: 20;" +
		        "-fx-border-color: rgba(255,255,255,0.12);" +
		        "-fx-border-radius: 20;" +
		        "-fx-border-width: 1;"+
		        "-fx-alignment: center;";
		    private static final String FIELD_CSS =
		        "-fx-background-color: gray;" +
		        "-fx-background-radius: 10;" +
		        "-fx-border-color: orange;" +
		        "-fx-border-radius: 10;" +
		        "-fx-border-width: 2;" +
		        "-fx-text-fill: white;" +
		        "-fx-font-size: 22px;" +
		        "-fx-font-weight: bold;" +
		        "-fx-alignment: center;" +
		        "-fx-prompt-text-fill: rgba(255,255,255,0.35);";
		    private static final String BTN_PRIMARY =
		        "-fx-background-color: green;" +
		        "-fx-background-radius: 10;" +
		        "-fx-text-fill: white;" +
		        "-fx-font-size: 15px;" +
		        "-fx-font-weight: bold;" +
		        "-fx-cursor: hand;";
		    private static final String BTN_SUCCESS =
		        "-fx-background-color: yellow;" +
		        "-fx-background-radius: 10;" +
		        "-fx-text-fill: green;" +
		        "-fx-font-size: 15px;" +
		        "-fx-font-weight: bold;" +
		        "-fx-cursor: hand;";

	private int randomNum;
	private int attempts;
	private final int LOWER_BOUND = 1;
	private final int UPPER_BOUND = 100;
	private final Random random = new Random();
	
	
	private Label feedbackLoopLabel;
	private Label attemptLabel;
	private Label rangeLabel;
	private TextField guessField;
	private Button guessingButton;
	private Button playAgainButton;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	 	Label title = new Label("Welcome to the Java 211s Guessing Game");

	 	title.setFont(Font.font(30));

	 	title.setTextFill(rainbowGradient());
        title.setTextAlignment(TextAlignment.CENTER);
        

        Label subtitle = new Label("I'm thinking of a number between 1 and 100...");
        subtitle.setFont(Font.font(23));
        subtitle.setTextFill(rainbowGradient());
        
       

        VBox titleBox = new VBox(23, title, subtitle);
        titleBox.setAlignment(Pos.CENTER);
        
        rangeLabel = new Label("The Range: 1 -100");
        rangeLabel.setFont(Font.font(16));
        
        attemptLabel = new Label("Attempts: 0");
        attemptLabel.setFont(Font.font(14));
       
        feedbackLoopLabel = new Label("Can you guess my number?");
        feedbackLoopLabel.setFont(Font.font(18));
        feedbackLoopLabel.setTextFill(Color.BLACK);
        feedbackLoopLabel.setWrapText(true);
        feedbackLoopLabel.setTextAlignment(TextAlignment.CENTER);
        
        guessField = new TextField();
        guessField.setPromptText("Enter guess…");
        guessField.setStyle(FIELD_CSS);
        guessField.setPrefWidth(200);
        guessField.setPrefHeight(50);
        guessField.setMaxWidth(200);
        guessField.setOnAction(actionEvent -> processGuess());
        
        
        guessingButton = new Button("Submit Guess");
        guessingButton.setStyle(BTN_PRIMARY);
        guessingButton.setPrefWidth(200);
        guessingButton.setPrefHeight(45);
        guessingButton.setOnAction(actionEvent -> processGuess());
        
        
        playAgainButton = new Button("Play Again");
        playAgainButton.setStyle(BTN_SUCCESS);
        playAgainButton.setPrefWidth(200);
        playAgainButton.setPrefHeight(45);
        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);
        
        playAgainButton.setOnMouseEntered(e ->
	        playAgainButton.setStyle(BTN_SUCCESS.replace("#22c55e", "#16a34a"))
	    );
        playAgainButton.setOnMouseExited(e ->
	        playAgainButton.setStyle(BTN_SUCCESS)
	    );
	        
        playAgainButton.setOnAction(actionEvent -> startNewGame());
        
        Label hint = new Label("Press Enter  or  click Submit Guess");

        hint.setTextFill(Color.ORANGE);

        
        VBox card = new VBox(20, attemptLabel, rangeLabel,feedbackLoopLabel,guessField, guessingButton, playAgainButton);
        
        
        card.setPadding(new Insets(35,40,35,40));
        card.setStyle(CARD_CSS);
        card.setMaxWidth(400);
        card.setAlignment(Pos.CENTER);

        
        
        
        VBox rootBox = new VBox(28, titleBox, card);
        rootBox.setAlignment(Pos.CENTER);
        rootBox.setPadding(new Insets(30));
        rootBox.setStyle("-fx-background-color: #1e293b;");
	
		Scene scene = new Scene(rootBox, 800, 500);
		
		startNewGame();
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
    private void startNewGame() {
    	randomNum = random.nextInt(UPPER_BOUND - LOWER_BOUND + 1) + LOWER_BOUND;
        //System.out.println("Rando Num: " + randomNum );
        attempts  = 0;
        
        attemptLabel.setText("Attempts: 0");
        rangeLabel.setText("Range:1-100");
        rangeLabel.setTextFill(Color.rgb(99, 102, 241));
        feedbackLoopLabel.setText("Can you guess my number?");
        feedbackLoopLabel.setTextFill(Color.WHITE);

        guessField.clear();
        guessField.setDisable(false);
        guessingButton.setDisable(false);
        playAgainButton.setVisible(false);
        playAgainButton.setManaged(false);
        guessField.requestFocus();
    }
	
    

	
    private void processGuess() {
        String input = guessField.getText().trim();
        int guess;

        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            showFeedback("Please enter a valid number!", Color.ALICEBLUE);
            roll(guessField);
            guessField.selectAll();
            return;
        }

        if (guess < 1 || guess > 100) {
            showFeedback("Number must be between 1 and 100!", Color.RED);
            roll(guessField);
            guessField.selectAll();
            return;
        }

        attempts++;
        attemptLabel.setText("Attempts: " + attempts);
        attemptLabel.setTextFill(Color.AQUAMARINE);
        
        //System.out.println("Guess:" + guess + " Rando num " + randomNum);

        if (guess < randomNum) {
            
            showFeedback("Too Low!  Try higher.", Color.RED);
            rangeLabel.setText("Range:  " + LOWER_BOUND + " — " + UPPER_BOUND);
            rangeLabel.setTextFill(Color.rgb(239, 68, 68));
            guessField.selectAll();
            guessField.requestFocus();
        } else if (guess > randomNum) {
            
            showFeedback("Too High!  Try lower.", Color.RED);
            rangeLabel.setText("Range:  " + LOWER_BOUND + " -- " + UPPER_BOUND);
            rangeLabel.setTextFill(Color.rgb(234, 179, 8));
            guessField.selectAll();
            guessField.requestFocus();
        } else {
            // ── Correct ───────────────────────────────────────────
            showFeedback("Correct! The number was " + randomNum + "!", Color.WHITE);
            rangeLabel.setText("Solved in " + attempts + (attempts == 1 ? " try!" : " tries!"));
            rangeLabel.setTextFill(Color.rgb(34, 197, 94));
            guessField.setDisable(true);
            guessingButton.setDisable(true);
            playAgainButton.setVisible(true);
            playAgainButton.setManaged(true);
            pulse(playAgainButton);
        }
    }

    // ── Helpers ───────────────────────────────────────────────────

    private void showFeedback(String text, Color color) {
        feedbackLoopLabel.setText(text);


        FadeTransition ft = new FadeTransition(Duration.millis(600), feedbackLoopLabel);
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.play();
    }

    private void roll(javafx.scene.Node node) {
        TranslateTransition tt = new TranslateTransition(Duration.millis(600), node);
        tt.setByX(10);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
        tt.setOnFinished(actionEvent -> node.setTranslateX(.80));
        tt.play();
    }

    private void pulse(javafx.scene.Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(600), node);
        st.setFromX(1.0); st.setFromY(1.0);
        st.setToX(1.06);  st.setToY(1.06);
        st.setCycleCount(6);
        st.setAutoReverse(true);
        st.play();
    }
    
    private LinearGradient rainbowGradient() {
        return new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
            new Stop(0.0, Color.RED),
            new Stop(0.2, Color.ORANGE),
            new Stop(0.4, Color.YELLOW),
            new Stop(0.6, Color.GREEN),
            new Stop(1.0, Color.BLUE),
            new Stop(1.0, Color.VIOLET)
        );
    }

    public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}

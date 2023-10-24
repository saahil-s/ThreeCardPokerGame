import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ThreeCardPokerGame extends Application {

	Player playerOne, playerTwo;
	Dealer theDealer;
	HashMap<String, Scene> sceneMap;
	BorderPane gameScreen;
	VBox resultsScreen = new VBox(); // results screen VBox, placeholder for now
	PauseTransition pause_p1c1 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_p1c2 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_p1c3 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_p2c1 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_p2c2 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_p2c3 = new PauseTransition(Duration.seconds(1));
	PauseTransition pauseButtons = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_dc1 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_dc2 = new PauseTransition(Duration.seconds(1));
	PauseTransition pause_dc3 = new PauseTransition(Duration.seconds(1));
	PauseTransition pauseResults = new PauseTransition(Duration.seconds(1));
	SequentialTransition showPlayerCards; // reveals players' cards
	SequentialTransition showDealerCards; // reveals dealer's cards
	TextField p1_score, p2_score, anteWager, anteWager2, ppWager, ppWager2, playWager, playWager2;
	ImageView v1, v2, v3, v4, v5, v6, v7, v8, v9; // cards in hand
	Button deal, fold1, play1, fold2, play2, start, seeResults; // buttons for game screen
	TextField ppField1, anteField1, ppField2, anteField2;
	Boolean p1_folded, p2_folded; // tracks whether p1 or p2 folded
	Boolean antePushed1 = false; // tracks whether ante was pushed for p1
	Boolean antePushed2 = false; // tracks whether ante was pushed for p2
	Boolean newLook = false; // tracks which look the game screen has
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Project #2: 3 Card Poker");
		playerOne = new Player();
		playerOne.totalWinnings = 100; // starting cash
		playerTwo = new Player();
		playerTwo.totalWinnings = 100; // starting cash
		theDealer = new Dealer();
		
		pause_p1c1.setOnFinished(e->v4.setImage(new Image("deckImages/" + 
				playerOne.hand.get(0).toString() + ".png")));
		pause_p1c2.setOnFinished(e->v5.setImage(new Image("deckImages/" + 
				playerOne.hand.get(1).toString() + ".png")));
		pause_p1c3.setOnFinished(e->v6.setImage(new Image("deckImages/" + 
				playerOne.hand.get(2).toString() + ".png")));
		pause_p2c1.setOnFinished(e->v7.setImage(new Image("deckImages/" + 
				playerTwo.hand.get(0).toString() + ".png")));
		pause_p2c2.setOnFinished(e->v8.setImage(new Image("deckImages/" + 
				playerTwo.hand.get(1).toString() + ".png")));
		pause_p2c3.setOnFinished(e->v9.setImage(new Image("deckImages/" + 
				playerTwo.hand.get(2).toString() + ".png")));
		pauseButtons.setOnFinished(e-> {
			fold1.setDisable(false);
			play1.setDisable(false);
			fold2.setDisable(false);
			play2.setDisable(false);
		});
		showPlayerCards = new SequentialTransition(pause_p1c1, pause_p1c2, pause_p1c3, pause_p2c1, 
				pause_p2c2, pause_p2c3, pauseButtons);
		
		pause_dc1.setOnFinished(e->v1.setImage(new Image("deckImages/" + 
				theDealer.dealersHand.get(0).toString() + ".png")));
		pause_dc2.setOnFinished(e->v2.setImage(new Image("deckImages/" + 
				theDealer.dealersHand.get(1).toString() + ".png")));
		pause_dc3.setOnFinished(e->v3.setImage(new Image("deckImages/" + 
				theDealer.dealersHand.get(2).toString() + ".png")));
		pauseResults.setOnFinished(e->seeResults.setDisable(false));
		showDealerCards = new SequentialTransition(pause_dc1, pause_dc2, pause_dc3, pauseResults);
		
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("home", createHomeScene(primaryStage));
		sceneMap.put("game", createGameScene(primaryStage));
		sceneMap.put("results", new Scene(new BorderPane(), 700, 700)); // placeholder scene for now
		
		
		primaryStage.setScene(sceneMap.get("home"));
		primaryStage.show();
	}
	
	public Scene createHomeScene(Stage primaryStage) {
		Label l = new Label("Welcome to 3 Card Poker!");
		l.setStyle("-fx-font-size: 25;" + "-fx-font-weight: bold;");

		Button b = new Button("Play");
		b.setStyle("-fx-font-size: 18;" + "-fx-font-weight: bold;" + "-fx-border-color: black;" 
				+ "-fx-border-width: 2;");// + "-fx-background-color: antiquewhite;");
		b.setMinHeight(20);
		b.setMinWidth(50);
		b.setOnAction(e->primaryStage.setScene(sceneMap.get("game")));
		
		Image i = new Image("poker.png");
		ImageView iv = new ImageView(i);
		iv.setFitHeight(300);
		iv.setFitWidth(300);
		iv.setPreserveRatio(true);
		
		VBox box = new VBox(l, iv, b);
		box.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, 
				CornerRadii.EMPTY, Insets.EMPTY)));
		box.setAlignment(Pos.CENTER);
		box.setSpacing(70);
		box.setStyle("-fx-font-family: 'courier'");
		
		Scene home = new Scene(box, 700, 700);
		return home;
	}
	
	public Scene createGameScene(Stage primaryStage) {
		MenuBar menuBar = createMenu(primaryStage);
	    VBox playerInfo = createPlayerDisplay();
	    VBox defaultCards = createDefaultHand();
	    HBox allButtons = createButtons(primaryStage);
	    VBox cards_Buttons = new VBox(defaultCards, allButtons);
	    gameScreen = new BorderPane();
	    gameScreen.setTop(menuBar);
	    gameScreen.setLeft(playerInfo);
	    gameScreen.setCenter(cards_Buttons);
	    if (!newLook) {
	    	gameScreen.setStyle("-fx-font-family: 'courier';" + "-fx-background-color: antiquewhite;");
	    } else {
	    	gameScreen.setStyle("-fx-font-family: 'courier';" 
	    			+ "-fx-background-color: lightskyblue;" + "-fx-font-weight: bold;");
	    }
	    Scene game = new Scene(gameScreen, 1000, 700);
		return game;
	}
	
	public MenuBar createMenu(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
	    Menu menu = new Menu("Options");
	    
	    MenuItem i1 = new MenuItem("Fresh Start");
	    i1.setOnAction(e-> {
	    	playerOne = new Player();
			playerOne.totalWinnings = 100; // starting cash
			playerTwo = new Player();
			playerTwo.totalWinnings = 100; // starting cash
			theDealer = new Dealer();
			sceneMap.replace("game", createGameScene(primaryStage));
			primaryStage.setScene(sceneMap.get("game"));
	    });
	    
	    MenuItem i2 = new MenuItem("New Look");
	    if (newLook) {
	    	i2.setText("Old Look");
	    }
	    i2.setOnAction(e-> {
	    	if (!newLook) {
	    		gameScreen.setStyle("-fx-font-family: 'courier';" + "-fx-background-color: lightskyblue;"
	    				+ "-fx-font-weight: bold;");
	    		resultsScreen.setStyle("-fx-font-family: 'courier';" + 
	    				"-fx-background-color: lightskyblue;" + "-fx-font-weight: bold;");
	    		i2.setText("Old Look");
	    		newLook = true;
	    	} else {
	    		gameScreen.setStyle("-fx-font-family: 'courier';" + "-fx-background-color: antiquewhite;");
	    		resultsScreen.setStyle("-fx-font-family: 'courier';" + "-fx-background-color: antiquewhite;");
	    		i2.setText("New Look");
	    		newLook = false;
	    	}
	    });

	    MenuItem i3 = new MenuItem("Exit");
	    i3.setOnAction(e->Platform.exit());
	    
	    menu.getItems().addAll(i1, i2, i3);
	    menuBar.getMenus().add(menu);
	    return menuBar;
	}
	
	public VBox createPlayerDisplay() {
		p1_score = new TextField("$" + playerOne.totalWinnings);
	    p1_score.setEditable(false);
	    Label l1_score = new Label("Player 1 Winnings");
	    l1_score.setLabelFor(p1_score);
	    VBox box1 = new VBox(l1_score, p1_score);
	    box1.setPadding(new Insets(0, 0, 7, 0));
	    
	    p2_score = new TextField("$" + playerTwo.totalWinnings);
	    p2_score.setEditable(false);
	    Label l2_score = new Label("Player 2 Winnings");
	    l2_score.setLabelFor(p2_score);
	    VBox box2 = new VBox(l2_score, p2_score);
	    box2.setPadding(new Insets(0, 0, 7, 0));
	    
	    anteWager = new TextField("$" + playerOne.anteBet);
	    anteWager.setEditable(false);
	    Label anteLabel = new Label("Player 1 Ante Wager");
	    anteLabel.setLabelFor(anteWager);
	    VBox box3 = new VBox(anteLabel, anteWager);
	    box3.setPadding(new Insets(0, 0, 7, 0));
	    
	    ppWager = new TextField("$" + playerOne.pairPlusBet);
	    ppWager.setEditable(false);
	    Label ppLabel = new Label("Player 1 Pair Plus Wager");
	    ppLabel.setLabelFor(ppWager);
	    VBox box4 = new VBox(ppLabel, ppWager);
	    box4.setPadding(new Insets(0, 0, 7, 0));
	    
	    playWager = new TextField("$" + playerOne.playBet);
	    playWager.setEditable(false);
	    Label playLabel = new Label("Player 1 Play Wager");
	    playLabel.setLabelFor(playWager);
	    VBox box5 = new VBox(playLabel, playWager);
	    box5.setPadding(new Insets(0, 0, 7, 0));
	    
	    anteWager2 = new TextField("$" + playerTwo.anteBet);
	    anteWager2.setEditable(false);
	    Label anteLabel2 = new Label("Player 2 Ante Wager");
	    anteLabel2.setLabelFor(anteWager2);
	    VBox box6 = new VBox(anteLabel2, anteWager2);
	    box6.setPadding(new Insets(0, 0, 7, 0));
	    
	    ppWager2 = new TextField("$" + playerOne.pairPlusBet);
	    ppWager2.setEditable(false);
	    Label ppLabel2 = new Label("Player 2 Pair Plus Wager");
	    ppLabel2.setLabelFor(ppWager2);
	    VBox box7 = new VBox(ppLabel2, ppWager2);
	    box7.setPadding(new Insets(0, 0, 7, 0));
	    
	    playWager2 = new TextField("$" + playerOne.playBet);
	    playWager2.setEditable(false);
	    Label playLabel2 = new Label("Player 2 Play Wager");
	    playLabel2.setLabelFor(playWager2);
	    VBox box8 = new VBox(playLabel2, playWager2);
	    box8.setPadding(new Insets(0, 0, 7, 0));
	    
	    VBox p1_info = new VBox(box1, box3, box4, box5);
	    p1_info.setPadding(new Insets(10, 0, 0, 5));
	    
	    VBox p2_info = new VBox(box2, box6, box7, box8);
	    p2_info.setPadding(new Insets(0, 0, 0, 5));
	    
	    VBox playerInfo = new VBox(p1_info, p2_info);
	    playerInfo.setSpacing(20);
	    return playerInfo;
	}
	
	public VBox createDefaultHand() {
		Image dealerCard1 = new Image("deckImages/gray_back.png");
		v1 = new ImageView(dealerCard1);
		v1.setFitHeight(100);
		v1.setFitWidth(100);
		v1.setPreserveRatio(true);
		
		Image dealerCard2 = new Image("deckImages/gray_back.png");
		v2 = new ImageView(dealerCard2);
		v2.setFitHeight(100);
		v2.setFitWidth(100);
		v2.setPreserveRatio(true);
		
		Image dealerCard3 = new Image("deckImages/gray_back.png");
		v3 = new ImageView(dealerCard3);
		v3.setFitHeight(100);
		v3.setFitWidth(100);
		v3.setPreserveRatio(true);
		
		HBox dHand = new HBox(v1, v2, v3);
		dHand.setSpacing(10);
		dHand.setPadding(new Insets(0, 0, 0, 195));
		
		Image player1Card1 = new Image("deckImages/gray_back.png");
		v4 = new ImageView(player1Card1);
		v4.setFitHeight(100);
		v4.setFitWidth(100);
		v4.setPreserveRatio(true);
		
		Image player1Card2 = new Image("deckImages/gray_back.png");
		v5 = new ImageView(player1Card2);
		v5.setFitHeight(100);
		v5.setFitWidth(100);
		v5.setPreserveRatio(true);
		
		Image player1Card3 = new Image("deckImages/gray_back.png");
		v6 = new ImageView(player1Card3);
		v6.setFitHeight(100);
		v6.setFitWidth(100);
		v6.setPreserveRatio(true);
		
		HBox p1Hand = new HBox(v4, v5, v6);
		p1Hand.setSpacing(10);
		
		Image player2Card1 = new Image("deckImages/gray_back.png");
		v7 = new ImageView(player2Card1);
		v7.setFitHeight(100);
		v7.setFitWidth(100);
		v7.setPreserveRatio(true);
		
		Image player2Card2 = new Image("deckImages/gray_back.png");
		v8 = new ImageView(player2Card2);
		v8.setFitHeight(100);
		v8.setFitWidth(100);
		v8.setPreserveRatio(true);
		
		Image player2Card3 = new Image("deckImages/gray_back.png");
		v9 = new ImageView(player2Card3);
		v9.setFitHeight(100);
		v9.setFitWidth(100);
		v9.setPreserveRatio(true);
		
		HBox p2Hand = new HBox(v7, v8, v9);
		p2Hand.setSpacing(10);
		
		HBox pHands = new HBox(p1Hand, p2Hand);
		pHands.setSpacing(170);
		
		Label dealer = new Label("Dealer's Hand");
		dealer.setPadding(new Insets(20, 0, 0, 250));
		Label p1 = new Label("Player 1's Hand");
		Label p2 = new Label("Player 2's Hand");
		HBox pLabels = new HBox(p1, p2);
		pLabels.setSpacing(265);
		pLabels.setPadding(new Insets(20, 0, 0, 50));
		
		VBox allHands  = new VBox(dealer, dHand, pLabels, pHands);
		allHands.setPadding(new Insets(0, 0, 0, 100));
		return allHands;
	}
	
	public HBox createButtons(Stage primaryStage) {
		fold1 = new Button("Fold");
		fold1.setDisable(true);
		fold1.setOnAction(e-> {
	    	fold1.setDisable(true);
	    	play1.setDisable(true);
	    	if (play2.isDisabled()) {
	    		start.setDisable(false);
	    	}
	    	p1_folded = true;
	    });
		play1 = new Button("Play");
		play1.setDisable(true);
		play1.setOnAction(e-> {
	    	playerOne.playBet = playerOne.anteBet;
	    	playWager.setText("$" + playerOne.playBet);
	    	playerOne.totalWinnings -= playerOne.playBet;
	    	play1.setDisable(true);
	    	fold1.setDisable(true);
	    	if (play2.isDisabled()) {
	    		start.setDisable(false);
	    	}
	    	p1_folded = false;
	    });
		ppField1 = new TextField();
		ppField1.setPromptText("Click enter to submit");
		ppField1.setStyle("-fx-font-family: 'timesnewroman'");
		ppField1.setOnKeyPressed(e-> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				try {
					playerOne.pairPlusBet = Integer.parseInt(ppField1.getText());
				} catch(NumberFormatException exc) {
					playerOne.pairPlusBet = 0;
				}
				if (playerOne.pairPlusBet > 25 || playerOne.pairPlusBet < 5) {
					playerOne.pairPlusBet = 0;
				}
				ppWager.setText("$" + Integer.toString(playerOne.pairPlusBet));
				playerOne.totalWinnings -= playerOne.pairPlusBet;
				ppField1.setDisable(true);
			}
	    });
		anteField1 = new TextField();
		anteField1.setPromptText("Click enter to submit");
		anteField1.setStyle("-fx-font-family: 'timesnewroman'");
		if (antePushed1) {
			anteField1.setDisable(true);
		}
		anteField1.setOnKeyPressed(e-> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				try {
					playerOne.anteBet = Integer.parseInt(anteField1.getText());
				} catch(NumberFormatException exc) {
					playerOne.anteBet = 5;
				}
				if (playerOne.anteBet > 25 || playerOne.anteBet < 5) {
					playerOne.anteBet = 5;
				}
				anteWager.setText("$" + Integer.toString(playerOne.anteBet));
				playerOne.totalWinnings -= playerOne.anteBet;
				anteField1.setDisable(true);
			}
	    });
		
		Label ppLabel1 = new Label("Pair Plus Wager");
		ppLabel1.setLabelFor(ppField1);
		Label anteLabel1 = new Label("Ante Wager");
		anteLabel1.setLabelFor(anteField1);
		
		HBox buttons1 = new HBox(fold1, play1);
		buttons1.setSpacing(5);
		VBox fields1 = new VBox(ppLabel1, ppField1);
		VBox fields2 = new VBox(anteLabel1, anteField1);
		VBox p1_fields = new VBox(fields2, fields1);
		p1_fields.setSpacing(5);
		VBox p1_buttons = new VBox(buttons1, p1_fields);
		p1_buttons.setSpacing(5);
		
		fold2 = new Button("Fold");
		fold2.setDisable(true);
		fold2.setOnAction(e-> {
	    	fold2.setDisable(true);
	    	play2.setDisable(true);
	    	if (play1.isDisabled()) {
	    		start.setDisable(false); // if both users clicked play/fold
	    	}
	    	p2_folded = true;
	    });
		play2 = new Button("Play");
		play2.setDisable(true);
		play2.setOnAction(e-> {
			playerTwo.playBet = playerTwo.anteBet;
			playWager2.setText("$" + playerTwo.playBet);
			playerTwo.totalWinnings -= playerTwo.playBet;
	    	play2.setDisable(true);
	    	fold2.setDisable(true);
	    	if (play1.isDisabled()) {
	    		start.setDisable(false);
	    	}
	    	p2_folded = false;
	    });
		ppField2 = new TextField();
		ppField2.setPromptText("Click enter to submit");
		ppField2.setStyle("-fx-font-family: 'timesnewroman'");
		ppField2.setOnKeyPressed(e-> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				try {
					playerTwo.pairPlusBet = Integer.parseInt(ppField2.getText());
				} catch(NumberFormatException exc) {
					playerTwo.pairPlusBet = 0;
				}
				if (playerTwo.pairPlusBet > 25 || playerTwo.pairPlusBet < 5) {
					playerTwo.pairPlusBet = 0;
				}
				ppWager2.setText("$" + Integer.toString(playerTwo.pairPlusBet));
				playerTwo.totalWinnings -= playerTwo.pairPlusBet;
		    	ppField2.setDisable(true);
			}
	    });
		anteField2 = new TextField();
		anteField2.setPromptText("Click enter to submit");
		anteField2.setStyle("-fx-font-family: 'timesnewroman'");
		if (antePushed2) {
			anteField2.setDisable(true);
		}
		anteField2.setOnKeyPressed(e-> {
			if(e.getCode().equals(KeyCode.ENTER)) {
				try {
					playerTwo.anteBet = Integer.parseInt(anteField2.getText());
				} catch(NumberFormatException exc) {
					playerTwo.anteBet = 5;
				}
				if (playerTwo.anteBet > 25 || playerTwo.anteBet < 5) {
					playerTwo.anteBet = 5;
				}
				anteWager2.setText("$" + Integer.toString(playerTwo.anteBet));
				playerTwo.totalWinnings -= playerTwo.anteBet;
				anteField2.setDisable(true);
			}
	    });
		
		Label ppLabel2 = new Label("Pair Plus Wager");
		ppLabel2.setLabelFor(ppField2);
		Label anteLabel2 = new Label("Ante Wager");
		anteLabel2.setLabelFor(anteField2);
		
		HBox buttons2 = new HBox(fold2, play2);
		buttons2.setSpacing(5);
		VBox fields3 = new VBox(ppLabel2, ppField2);
		VBox fields4 = new VBox(anteLabel2, anteField2);
		VBox p2_fields = new VBox(fields4, fields3);
		p2_fields.setSpacing(5);
		VBox p2_buttons = new VBox(buttons2, p2_fields);
		p2_buttons.setSpacing(5);
		
		deal = new Button("Deal");
		deal.setOnAction(e-> {
			if (playerOne.anteBet == 0) {
				playerOne.anteBet = 5;
				anteWager.setText("$" + playerOne.anteBet);
				playerOne.totalWinnings -= playerOne.anteBet;
				
			}
			if (playerTwo.anteBet == 0) {
				playerTwo.anteBet = 5;
				anteWager2.setText("$" + playerTwo.anteBet);
				playerTwo.totalWinnings -= playerTwo.anteBet;
			}
			p1_score.setText("$" + playerOne.totalWinnings);
			p2_score.setText("$" + playerTwo.totalWinnings);
	    	playerOne.hand = theDealer.dealHand();
	    	playerTwo.hand = theDealer.dealHand();
	    	theDealer.dealersHand = theDealer.dealHand();
	    	deal.setDisable(true);
	    	ppField1.setDisable(true);
	    	anteField1.setDisable(true);
	    	ppField2.setDisable(true);
	    	anteField2.setDisable(true);
	    	showPlayerCards.play();
	    });
		start = new Button("Start Round");
		start.setDisable(true);
		start.setOnAction(e-> {
			p1_score.setText("$" + playerOne.totalWinnings);
			p2_score.setText("$" + playerTwo.totalWinnings);
			start.setDisable(true);
			sceneMap.replace("results", createResultsScene(primaryStage));
	    	showDealerCards.play();
	    });
		
		seeResults = new Button("See Results");
		seeResults.setDisable(true);
		seeResults.setOnAction(e->primaryStage.setScene(sceneMap.get("results")));
		
		VBox controlButtons = new VBox(deal, start, seeResults);
		controlButtons.setPadding(new Insets(20, 0, 0, 0));
		controlButtons.setSpacing(5);
		
		HBox allButtons = new HBox(p1_buttons, controlButtons, p2_buttons);
		allButtons.setPadding(new Insets(20, 0, 0, 130));
		allButtons.setSpacing(75);
		return allButtons;
	}

	public Scene createResultsScene(Stage primaryStage) {
		int evalDealerHand = ThreeCardLogic.evalHand(theDealer.dealersHand);
		int evalWinnerP1;
		int evalWinnerP2;
		int p1_evalPP, p2_evalPP;
		
		ObservableList<String> list = FXCollections.observableArrayList();
		ListView<String> displayList = new ListView<String>();
		Button goBack = new Button("Return to game");
		goBack.setOnAction(e-> {
			if (!antePushed1) {
				playerOne.anteBet = 0;
			}
			if (!antePushed2) {
				playerTwo.anteBet = 0;
			}
			playerOne.pairPlusBet = 0;
			playerOne.playBet = 0;
			playerOne.hand.clear();
			playerTwo.pairPlusBet = 0;
			playerTwo.playBet = 0;
			playerTwo.hand.clear();
			theDealer.dealersHand.clear();
			sceneMap.replace("game", createGameScene(primaryStage));
			primaryStage.setScene(sceneMap.get("game"));
		});
		
		// evaluate pair plus bet
		if (playerOne.pairPlusBet != 0) {
			p1_evalPP = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet);
			if (p1_evalPP != 0) {
				list.add("Player one wins Pair Plus");
				playerOne.totalWinnings += p1_evalPP;
			} else {
				list.add("Player one loses Pair Plus");
			}
		}
		if (playerTwo.pairPlusBet != 0) {
			p2_evalPP = ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet);
			if (p2_evalPP != 0) {
				list.add("Player two wins Pair Plus");
				playerTwo.totalWinnings += p2_evalPP;
			} else {
				list.add("Player two loses Pair Plus");
			}
		}
		
		// dealer does not have at least queen high
		if (evalDealerHand == 0 && theDealer.dealersHand.get(0).value < 12 &&
				theDealer.dealersHand.get(1).value < 12 && theDealer.dealersHand.get(2).value < 12) {
			list.add("Dealer does not have at least Queen high; ante wager is pushed");
			if (!p1_folded) {
				playerOne.totalWinnings += playerOne.playBet;
				antePushed1 = true;
			} else {
				antePushed1 = false;
				list.add("Player one folded");
			}
			if (!p2_folded) {
				playerTwo.totalWinnings += playerTwo.playBet;
				antePushed2 = true;
			} else {
				antePushed2 = false;
				list.add("Player two folded");
			}
		} else { // evaluate hands, find winner
			if (!p1_folded) {
				evalWinnerP1 = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);
				if (evalWinnerP1 == 1) {
					list.add("Player one loses to dealer");
					antePushed1 = false;
				} else if (evalWinnerP1 == 2) {
					list.add("Player one beats dealer");
					playerOne.totalWinnings += (2 * playerOne.anteBet);
					playerOne.totalWinnings += (2 * playerOne.playBet);
					antePushed1 = false;
				} else {
					list.add("Player one ties dealer; ante wager is pushed");
					antePushed1 = true;
				}
			} else {
				list.add("Player one folded");
				antePushed1 = false;
			}
			if (!p2_folded) {
				evalWinnerP2 = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);
				if (evalWinnerP2 == 1) {
					list.add("Player two loses to dealer");
					antePushed2 = false;
				} else if (evalWinnerP2 == 2) {
					list.add("Player two beats dealer");
					playerTwo.totalWinnings += (2 * playerTwo.anteBet);
					playerTwo.totalWinnings += (2 * playerTwo.playBet);
					antePushed2 = false;
				} else {
					list.add("Player two ties dealer; ante wager is pushed");
					antePushed2 = true;
				}
			} else {
				list.add("Player two folded");
				antePushed2 = false;
			}
		}
		displayList.setItems(list);
		displayList.setEditable(false);
		displayList.setMaxHeight(300);
		displayList.setMaxWidth(600);
		Label title = new Label("Round Results");
		title.setStyle("-fx-font-size: 20;");
		resultsScreen = new VBox(title, displayList, goBack);
		resultsScreen.setAlignment(Pos.CENTER);
		resultsScreen.setSpacing(20);
		if (!newLook) {
			resultsScreen.setStyle("-fx-font-family: 'courier';" + "-fx-background-color: antiquewhite;");
		} else {
			resultsScreen.setStyle("-fx-font-family: 'courier';" + "-fx-background-color: lightskyblue;" 
					+ "-fx-font-weight: bold;");
		}
		Scene results = new Scene(resultsScreen, 700, 700);
		return results;
	}
}

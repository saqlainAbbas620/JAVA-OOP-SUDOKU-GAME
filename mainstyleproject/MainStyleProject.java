
package mainstyleproject;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainStyleProject extends Application {
    GridPane grid = new GridPane();
    private int attempts=0;
    ObjectProperty<TextField> selectedCell = new SimpleObjectProperty<>();
    Stage primaryStage=new Stage();
    VBox levelsLayout = new VBox(20);
    Scene LEVEL = new Scene(levelsLayout);
    VBox  gameLayout = new VBox(20);
    
//    StackPane gamePane=new StackPane();
//    Scene GAME = new Scene(gameLayout,500,600);
    Scene GAME = new Scene(gameLayout,500,600);

    
    int Score=1;
    int liveScore;
    boolean TrueValue=true;
    int YourPoint;
    boolean[] isPaused = {false};
    
    public void level(){
        primaryStage.setScene(LEVEL);
        primaryStage.setTitle(STYLESHEET_MODENA);
        Button Easy = new Button("Easy");
        Button Medium = new Button("Medium");
        Button Hard = new Button("Hard");
        
        Label levelhead=new Label("SUDOKU");
//        HBox levelHead=new HBox(levelhead);
        levelsLayout.getChildren().addAll(levelhead,Easy, Medium, Hard);
        levelsLayout.setAlignment(Pos.CENTER);
        Easy.setOnAction(event -> {
//            EasySudoku();
            
            EasyGame();
            scoreManage(14,1);
                    });
        Medium.setOnAction(event -> {
            scoreManage(96,2);
            ModerateGame();
        });
        Hard.setOnAction(event -> {
            scoreManage(340,3);
            HardGame();
        });
        
//        STYLING
        levelhead.getStyleClass().add("levelHead");
        Easy.getStyleClass().add("EasyButton");
        Medium.getStyleClass().add("MediumButton");
        Hard.getStyleClass().add("HardButton");
        levelsLayout.getStyleClass().add("LevelLayout");
        LEVEL.getStylesheets().add(getClass().getResource("/Style/LevelLayout.css").toExternalForm());
        
        
        primaryStage.setTitle("Making of SUDOKU");
        primaryStage.show();
    }
    public void Game() {
    primaryStage.setScene(GAME);
    HBox numberButtons = new HBox(5);
    numberButtons.setAlignment(Pos.BOTTOM_CENTER);
    gameLayout.setAlignment(Pos.CENTER);
//    gamePane.setAlignment(Pos.CENTER);

    
    Image icon=new Image("Image/restart1.png");
        ImageView ViewIcon=new ImageView(icon);
        ViewIcon.setFitHeight(50);
        ViewIcon.setFitWidth(50);
    Button restartButton = new Button("",ViewIcon);
    
    Image icon1=new Image("Image/close1.png");
        ImageView ViewIcon1=new ImageView(icon1);
        ViewIcon1.setFitHeight(50);
        ViewIcon1.setFitWidth(50);
    Button exitButton = new Button("",ViewIcon1);
//    exitButton.setAlignment(Pos.TOP_RIGHT);
    HBox ExitAndRestart=new HBox(10,restartButton,exitButton);
    ExitAndRestart.setAlignment(Pos.TOP_CENTER);
    
    Buttons(restartButton,exitButton,primaryStage);

    // Timer label
    Label timerLabel = new Label("Time: 0 seconds");
    timerLabel.setStyle("-fx-font-size: 16;");

    
    
    
    // Pause/Resume button
    Image icon2=new Image("Image/pause.png");
        ImageView ViewIcon2=new ImageView(icon2);
        ViewIcon2.setFitHeight(50);
        ViewIcon2.setFitWidth(50);
    Button pauseButton = new Button("",ViewIcon2);
//    Button pauseButton;
//    pauseButton.setPrefWidth(100);
//    pauseButton.setPrefHeight(30);

    // Timer variables
    final int[] elapsedTime = {0};
    isPaused[0]= false;

    // Timeline for the timer
    Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        if (!isPaused[0]) {
            Score=elapsedTime[0]++;
            timerLabel.setText("Time: " + elapsedTime[0] + " s");
        }
    }));
    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();
    
    pauseButton.setOnAction(event -> {
    if (!isPaused[0]) {  // If the game is NOT paused
        isPaused[0] = true;  // Pause the game
        pauseButton.setText("Resume");
        grid.setDisable(true);  // Disable the game grid
        
        Image icon3=new Image("Image/Resume.png");
        ImageView ViewIcon3=new ImageView(icon3);
        ViewIcon3.setFitHeight(60);
        ViewIcon3.setFitWidth(60);
        pauseButton.setGraphic(ViewIcon3);
    } else {  // If the game is paused
        isPaused[0] = false;  // Resume the game
        grid.setDisable(false);  // Enable the game grid
        pauseButton.setText("Pause");
        pauseButton.setGraphic(ViewIcon2);
    }
});

    // Layout for the timer and pause button
    HBox topBar = new HBox(50, timerLabel, pauseButton);
    topBar.setAlignment(Pos.CENTER);
    topBar.setStyle("-fx-padding: 10;");
//    gamePane.getChildren().addAll(ExitAndRestart,topBar, grid, numberButtons);

    gameLayout.getChildren().addAll(ExitAndRestart,topBar, grid, numberButtons);
//    grid.setHgap(5); // Horizontal gap between TextFields
//    grid.setVgap(5); // Vertical gap between TextFields
    grid.setAlignment(Pos.CENTER);

    for (int i = 1; i <= 9; i++) {
        Button numberButton = new Button(Integer.toString(i));
        numberButton.setPrefWidth(20);
        numberButton.setPrefHeight(30);

        int value = i; // Capture the current value for the button
        numberButton.setOnAction(event -> {
            TextField cell = selectedCell.get();
            if (cell != null) {
                if(cell.isEditable()){
                cell.setText(String.valueOf(value)); // Set the selected number in the cell
                }
            }
        });
//        numberButtons.
        numberButtons.getChildren().add(numberButton);
        
        numberButton.getStyleClass().add("numberButton");
    }

//    STYLING
    gameLayout.getStyleClass().add("Game");
//    gamePane.getStyleClass().add("Game");

    timerLabel.getStyleClass().add("timeLabel");
    pauseButton.getStyleClass().add("pauseButton");
    restartButton.getStyleClass().add("restartButton");
    exitButton.getStyleClass().add("closeButton");
//    gameLayout.getStyleClass().add("Game");
    GAME.getStylesheets().add(getClass().getResource("/Style/GameLayOut.css").toExternalForm());
    
    
    
    
    primaryStage.setTitle("Making of SUDOKU");
    primaryStage.show();
}
    
//    BUTTON RESTART AND EXIT
    public void Buttons(Button restartButton,Button exitButton,Stage stage){
        restartButton.setOnAction(e -> {
            stage.close();
    // Clear the game layout and grid for a fresh start
    grid.getChildren().clear();
    gameLayout.getChildren().clear();
//    hide.hiddenNum=0;
//    gamePane.getChildren().clear();
    
    generateSudoku.Grid=new int[9][9];

    // Switch the scene back to the LEVEL scene
    primaryStage.setScene(LEVEL);

    // Reset attempts or any other game-specific state, if necessary
    attempts = 0;

    // Optionally, clear any previously selected level
    levelsLayout.getChildren().clear();
    level(); // Reinitialize the level selection scene
    });


    exitButton.setOnAction(e -> {
        primaryStage.close(); // Close the popup
        stage.close();
//        primaryStage.close(); // Exit the application
    });
    }
    public void scoreManage(int HS,int level){
        
        liveScore=(100*HS)/Score;
//        EASY
        if(liveScore<3&&level==1){
            liveScore=5;
        }else if(liveScore>15&&level==1){
            liveScore=15;
        }
//        MODERATE
        if(liveScore<10&&level==2){
            liveScore=12;
        }else if(liveScore>40&&level==2){
            liveScore=40;
        }
//        HARD
        if(liveScore<20&&level==3){
            liveScore=20;
        }else if(liveScore>80&&level==3){
            liveScore=80;
        }
        
    }
    
    
    
//    public void pause(boolean[] Paused){
//        
//        VBox pausebox= new VBox(20);
////        pausebox.setVisible(false);
////        gamePane.getChildren().add(pausebox);
//        gameLayout.getChildren().add(pausebox);
////        gameLayout.setAlignment(pausebox,Pos.CENTER);
//        Image icon=new Image("Image/icon.png");
//        ImageView ViewIcon=new ImageView(icon);
//        ViewIcon.setFitHeight(30);
//        ViewIcon.setFitWidth(30);
//        pausebox.setAlignment(Pos.CENTER);
//        Button pauseBtn=new Button("",ViewIcon);
//        pausebox.getChildren().add(pauseBtn);
//        pauseBtn.setOnAction(event ->{
//            Paused[0]=false;
//            grid.setDisable(false);
//            pausebox.setVisible(false);
////            G.setVisible(true);
////            primaryStage.setScene(GAME);
//        });
//        pauseBtn.set
        
//        pausebox.getChildren().add(ViewIcon);
//        Scene Pause = new Scene(pausebox,400,400);
//        primaryStage.setScene(Pause);
//        primaryStage.show();
//        Scene pause =new Scene(pauseBox;);
//    }
    
    
    
    
    
    private void EasyGame(){
        EasySudoku();
        Game();
//         primaryStage.close();
    }
    private void ModerateGame(){
        ModerateSudoku();
        Game();
    }
    private void HardGame(){
        HardSudoku();
        Game();
    }

    @Override
//    PLAYLAYOUT
    public void start(Stage primaryStage) {
        // Create the first scene with a Button
        VBox playLayout = new VBox(20); // 20 is the spacing between buttons
        Button play = new Button("PLAY!");
        
        Image head=new Image("Image/Label3.png");
        ImageView headImage=new ImageView(head);
        headImage.setFitHeight(350);
        headImage.setFitWidth(350);
        HBox header=new HBox(headImage);
        playLayout.getChildren().add(header);
        
        playLayout.getChildren().add(play);
        playLayout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(playLayout);
        
        // Switch scenes on button press
        play.setOnAction(event -> {
//            primaryStage.setScene(LEVEL);
                level();
                primaryStage.close();
            
        });
        Button Score = new Button("Score");
        playLayout.getChildren().add(Score);
        Score.setAlignment(Pos.CENTER);
        
        Score.setOnAction(Event->{
            ScoreLayOut();
        });
        
//        STYLE
        
        playLayout.getStyleClass().add("playLayout");
        play.getStyleClass().add("play");
        Score.getStyleClass().add("Score");
        header.getStyleClass().add("Header");
        scene1.getStylesheets().add(getClass().getResource("/Style/PlayLevel.css").toExternalForm());
                
        // Set the initial scene and show the stage
        
        primaryStage.setScene(scene1);
//        primaryStage.setTitle("Making of SUDOKU");
//        showCong.show();
        primaryStage.show();
        
        
    }
    public void ScoreLayOut(){
        
      
        ReadFile();
        Stage howCong = new Stage();
        howCong.initStyle(StageStyle.UNDECORATED);
        howCong.initModality(Modality.APPLICATION_MODAL);
//        showCong.initModality(Modality.NONE);
        Label TotalScore=new Label("You Have Points");
        Label Score=new Label(""+YourPoint);
        VBox ScoreLayout=new VBox();
        ScoreLayout.setAlignment(Pos.CENTER);
        
        
        Image Back=new Image("Image/Back.png");
        ImageView Backbtn=new ImageView(Back);
        Backbtn.setFitHeight(60);
        Backbtn.setFitWidth(60);
        Button BackButton=new Button("",Backbtn);
        BackButton.setAlignment(Pos.TOP_LEFT);
        BackButton.setOnAction(event->{
//            closeScene(showCong);
            howCong.close();
//            showCong.initModality(Modality.NONE);
        });
        ScoreLayout.getChildren().add(BackButton);
        ScoreLayout.getChildren().add(TotalScore);
        ScoreLayout.getChildren().add(Score);
        
        Scene ScoreScene=new Scene(ScoreLayout);
        ScoreScene.getStylesheets().add(getClass().getResource("/Style/GameLayOut.css").toExternalForm());
        ScoreLayout.getStyleClass().add("ScoreLayout");
        TotalScore.getStyleClass().add("TotalScore");
        Score.getStyleClass().add("Score");
        BackButton.getStyleClass().add("BackButton");
        
        howCong.setScene(ScoreScene);
        howCong.show();
    }
    
    
    
    public void ModerateSudoku(){
        generateSudoku.Moderate();
        generateCode();
    }
    public void EasySudoku(){
        generateSudoku.Easy();
        generateCode();
    }
    public void HardSudoku(){
        generateSudoku.Hard();
        generateCode();
    }
    private void generateCode(){
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                
                TextField cell = new TextField();
                cell.setPrefWidth(30);
                cell.setPrefHeight(30);
                cell.setStyle("-fx-border:0.5;-fx-display-caret: false;");
//               cell.set
                cell.setAlignment(Pos.CENTER);
//                for (int row = 0; row < 3; row++) {  // 3 rows of 3x3 subgrids
//            for (int col = 0; col < 3; col++) {  // 3 columns of 3x3 subgrids
//                // Create a 3x3 subgrid
//                GridPane subGrid = new GridPane();
//                subGrid.setHgap(5);  // Horizontal gap within subgrid
//                subGrid.setVgap(5);  // Vertical gap within subgrid
//                // Add the 3x3 subgrid to the main 9x9 grid
//                grid.add(subGrid, col, row);
//            }}
                 // Set cell borders
//                BorderStrokeStyle strokeStyle = BorderStrokeStyle.SOLID;
                BorderWidths borderWidths = new BorderWidths( 
                        i % 3 == 0 ? 2 : 0.4,  // Top border width (thicker for subgrid)
                        j % 3 == 2 ? 2 : 0.4,  // Right border width (thicker for subgrid)
                        i % 3 == 2 ? 2 : 0.4,  // Bottom border width (thicker for subgrid)
                        j % 3 == 0 ? 2 : 0.4   // Left border width (thicker for subgrid)
                );
               
                 cell.setBorder(new Border(new BorderStroke(
                        Color.SADDLEBROWN,  // Border color
                        BorderStrokeStyle.SOLID,  // Border style
                        CornerRadii.EMPTY,       // Corner radii
                        borderWidths             // Border widths
                )));
                // Add event to track selected cell
                
//                Styling Grid
                cell.getStyleClass().add("cells");
                
                
                int value=generateSudoku.Grid[i][j];
                
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    
                    cell.setEditable(false);
                } else {
                    
                    cell.setEditable(true);
                    int row = i;
                    int col = j;
                    cell.textProperty().addListener((observable, oldValue, newValue) -> {
                        validateInput(cell, newValue, row, col);
                        checkIfGridIsComplete();
                    });
                    cell.setOnMouseClicked(event -> {
                    if (cell.isEditable()) {
                        selectedCell.set(cell);
                    }
                    });
                }
                
                
                grid.add(cell, j, i);
            }
        }
        }

    
    
    // Check if the grid is fully and correctly filled
    private void checkIfGridIsComplete() {
        System.out.println("hello");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = generateSudoku.Grid[i][j];
//                if (value == 0 || !generateSudoku.isSafe(i, j, value)) {
                    if (value == 0) {
                    return; // Grid is incomplete or incorrect
                }
            }
        }
        // If all cells are correctly filled, show a success prompt
        showCongratulationsPopup();
    }
    // Display an alert dialog
    private void showLost() {
        Stage showCong=new Stage();
        isPaused[0] = true;
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        primaryStage.setTitle("LOST");
        liveScore=0;
    // Create the popup layout
    VBox popupContent = new VBox(10);
    popupContent.setAlignment(Pos.CENTER);
//    popupContent.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black;");
    showCong.initStyle(StageStyle.UNDECORATED);
    showCong.initModality(Modality.APPLICATION_MODAL);
    // Add congratulatory message
    Label congratsLabel = new Label("Congratulations! You've LOst Hii Hii Hii!");
//    congratsLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: green;");

    // Create buttons
    Image restart=new Image("Image/restart1.png");
        ImageView restartBtn=new ImageView(restart);
        restartBtn.setFitHeight(70);
        restartBtn.setFitWidth(70);
    Button restartButton = new Button("",restartBtn);
    Image close=new Image("Image/close1.png");
        ImageView closebtn=new ImageView(close);
        closebtn.setFitHeight(70);
        closebtn.setFitWidth(70);
    Button exitButton = new Button("",closebtn);
    
    Buttons(restartButton,exitButton,showCong);  
    HBox ExitRestart=new HBox(10,restartButton,exitButton);
    ExitRestart.setAlignment(Pos.CENTER);
    Buttons(restartButton,exitButton,showCong);
    // Add content to the popup
    popupContent.getChildren().addAll(congratsLabel,ExitRestart );
    
    // Create a scene for the popup
    Scene popupScene = new Scene(popupContent);
//    showCong.initStyle(StageStyle.UNDECORATED);
    popupScene.getStylesheets().add(getClass().getResource("/Style/GameLayOut.css").toExternalForm());
    popupContent.getStyleClass().add("CongratsMessage");
    congratsLabel.getStyleClass().add("congratsLabel");
    restartButton.getStyleClass().add("restartButton");
    exitButton.getStyleClass().add("ExitButton");
    
    
    
    
    showCong.setScene(popupScene);
    
    
    
    
//    showCong.setOnCloseRequest(event -> event.consume());
    // Show the popup as a modal dialog
    showCong.show();
    }
     // Increment incorrect attempts and show prompt if limit is reached
    
    private void showCongratulationsPopup() {
//        primaryStage.setDisable(true);
        ReadFile();
        
        WriteFile(YourPoint);
        Stage showCong=new Stage();
        isPaused[0] = true;
//    primaryStage.setTitle("Congratulations");
//    Stage showCong=new Stage();
    // Create the popup layout
    VBox popupContent = new VBox(10);
    popupContent.setAlignment(Pos.CENTER);
//    popupContent.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black;");

    // Add congratulatory message
    Label congratsLabel = new Label("Congratulations! You've won!");
//    scoreManage(14,1);
    Label SC=new Label("Your Score is\n"+liveScore);
//    congratsLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: green;");

    // Create buttons
    Image restart=new Image("Image/restart1.png");
        ImageView restartBtn=new ImageView(restart);
        restartBtn.setFitHeight(70);
        restartBtn.setFitWidth(70);
    Button restartButton = new Button("",restartBtn);
    Image close=new Image("Image/close1.png");
        ImageView closebtn=new ImageView(close);
        closebtn.setFitHeight(70);
        closebtn.setFitWidth(70);
    Button exitButton = new Button("",closebtn);
    Buttons(restartButton,exitButton,showCong);
    HBox ExitRestart=new HBox(10,restartButton,exitButton);
    ExitRestart.setAlignment(Pos.CENTER);
    // Add content to the popup
    popupContent.getChildren().addAll(congratsLabel,SC, ExitRestart);
//    Message dialog= new Message();
    // Create a scene for the popup
    Scene popupScene = new Scene(popupContent);
    popupScene.getStylesheets().add(getClass().getResource("/Style/GameLayOut.css").toExternalForm());
    popupContent.getStyleClass().add("CongratsMessage");
    congratsLabel.getStyleClass().add("congratsLabel");
    restartButton.getStyleClass().add("restartButton");
    exitButton.getStyleClass().add("ExitButton");
    SC.getStyleClass().add("SC");
    
    showCong.initModality(Modality.APPLICATION_MODAL);
//    showCong.initOwner(showCong);
    
    showCong.initStyle(StageStyle.UNDECORATED);
    
//    showCong.initModality(Modality.WINDOW_MODAL);
//    primaryStage.initModality(Modality.WINDOW_MODAL);
        // Set the owner of the secondary stage
//    showCong.initOwner(showCong);
//    primaryStage.initOwner(showCong);
    
    
    
//    showCong.initModality(Modality.NONE);
    showCong.setScene(popupScene);
   
//    Alert alert = new Alert(Alert.AlertType.NONE);
//    alert.getDialogPane().setContent(popupContent);
//    alert.show();
//    primaryStage.setScene(popupScene);

    // Show the popup as a modal dialog
    showCong.show();
}


    
    
   private void validateInput(TextField cell, String input, int row, int col) {
    // Check if the input is a single digit
    if (input.matches("[1-9]")) {
        int value = Integer.parseInt(input);
        if (generateSudoku.isSafe(row, col, value)) {
           TrueValue=false;
            // Correct value
            generateSudoku.Grid[row][col] = value; // Update the grid
            cell.setEditable(false); // Lock the cell
            cell.setStyle("-fx-background-color: lightgreen; -fx-alignment: center;"); // Mark as correct
        } else {
            TrueValue=true;
            // Incorrect value
            cell.setStyle("-fx-background-color: red; -fx-alignment: center;");

            // Increment incorrect attempt counter
            attempts++;
            // Show prompt if 3 incorrect attempts
            if (attempts >= 3) {
//                System.out.println("Too many incorrect attempts!");
                showLost();
                // You can implement a losing condition or a retry logic here.
            }

            // Allow the user to enter a new value
            cell.setEditable(true);
//            cell.setText(""); // Clear incorrect input
        }
    } else {
        // Clear invalid input
        cell.setText("");
        cell.setStyle("-fx-background-color: white; -fx-alignment: center;");
        cell.setEditable(true); // Make sure the cell is still editable
    }
    }
        
    static class generateSudoku{
    public static int[][] Grid=new int[9][9]; 
    
    public static void Moderate(){
        generateSudoku.Display();
        int value=ModerateHide();
        hide.hideValue(value);
        hide.Display();
    }
    public static int ModerateHide(){
        Random H=new Random();
        return H.nextInt(4, 5);
    }
    public static void Easy(){
        generateSudoku.Display();
        int value=Easyhide();
        hide.hideValue(value);
        hide.Display();
    }
    public static int Easyhide(){
        Random H=new Random();
        return H.nextInt(2, 3);
    }
    public static void Hard(){
        generateSudoku.Display();
        int value=Hardhide();
        hide.hideValue(value);
        hide.Display();
    }
    public static int Hardhide(){
        Random H=new Random();
        return H.nextInt(5, 10);
    }
    //TO CHECK THE NUMBER
    public static boolean isSafe(int Row, int Col, int NUM){
        
        //CHECK ROWS
        for(int i=0; i<9; i++){
            if(Grid[Row][i]==NUM)
                return false;
        }
        
        //CHECK COLUMNS
        for(int i=0; i<9; i++){
            if(Grid[i][Col]==NUM)
                return false;
        }
        
        //CHECK SUBGRID
        int subRow=Row/3*3;
        int subCol=Col/3*3;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(Grid[subRow+i][subCol+j]==NUM){
                    return false;
                }
            }
        }
        return true;
    }
    
    private static boolean FillBox(int Row, int Col){
        if(Row==9)
           return true;
        
        
        int NRow=(Col==8)? Row+1:Row;
        int NCol=(Col+1)%9;
        

        for(int i=1; i<=9;i++){
        int NUM=RandomNumber.random();
        if(isSafe(Row,Col,NUM)){
            Grid[Row][Col]=NUM;
            if(FillBox(NRow,NCol)){
                return true;                                    //BACKTRACKING
            }
            Grid[Row][Col]=0;
        }
        }
        return false;
        
    }

    private static boolean fillSudokuGrid(){    //Check ALL BOX is FILL
        return FillBox(0,0);
    }
    public static void Display(){
        if (fillSudokuGrid()) {
//            printGrid();
        } else {
            System.out.println("Failed to generate a valid Sudoku grid.");
        }
    }    
}



//HIDDEN VALUES
class hide{
    public static int hiddenNum;
//    public Easy easyGame;
    public static void hideValue(int value){
        
        hiddenNum=value;
        int i=0;
        while(i<hiddenNum){
            int row=RandomNumber.random()-1;
            int col=RandomNumber.random()-1;
            
            if(generateSudoku.Grid[row][col]!=0){
                generateSudoku.Grid[row][col]=0;
                i++;
            }
        }
    }
    
    public static void Display(){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){               
                System.out.print((generateSudoku.Grid[i][j]==0)?"  ":generateSudoku.Grid[i][j]+" ");
            }
            System.out.println();
        }
    } 
    
//    public void hidden(){
//        
//    }
    
}
//GENERATE RANDOMNUMBER
class RandomNumber{
    public static int random(){
        Random R=new Random();
        
        return R.nextInt(9)+1;
    }
    public int hidden(){
        Random H=new Random();
        return H.nextInt(2, 3);
    }
}



//FILE HANDLING
public void ReadFile(){
    try{
        FileInputStream OutputFile=new FileInputStream("Score");
        YourPoint=OutputFile.read();
        YourPoint+=liveScore;        
        OutputFile.close();       
    }catch(Exception e){
        System.out.println(e.toString());
    }
}
public void WriteFile(int writeScore){
    try{
        
        FileOutputStream InputFile=new FileOutputStream("Score");
//        int AllScore=liveScore;
        InputFile.write(writeScore);
        InputFile.close();      
    }catch(Exception e){
        System.out.println(e.toString());
    }
}

     
        



    
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
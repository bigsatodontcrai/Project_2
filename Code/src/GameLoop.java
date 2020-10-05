import java.util.Scanner;

public class GameLoop {
    private Board player1Board;
    private BoardPrinterWrapper player1Printer;
    private Board player2Board;
    private BoardPrinterWrapper player2Printer;
    private getUserInput player1UI;
    private getUserInput player2UI;
    private Scanner consoleInput = new Scanner(System.in);
    private safelyGetCoordinates getCoor;
    private boolean[] playerWon = {false, false};
    private PlaceShip player1place;
    private PlaceShip player2place; 
    
    
    public GameLoop() {
        this.player1UI = new getUserInput(1);
        this.player2UI = new getUserInput(2);
        this.getCoor = new safelyGetCoordinates();
    }

    public gameLogicInterface Init(){
        Utility.printStart();
        AIEasy Easy;
        AIMedium Medium;
        AIHard Hard;
        System.out.println("Type 1 for player, 2 for AI");
        int AI = 0;
        boolean isNotAI = false;

        boolean loop1 = true;
        boolean loop2 = true;
        
        while(loop1) 
        	{
        	
        	if (!consoleInput.hasNextInt() || !consoleInput.hasNext())
        	{
        		System.out.println("Um.. 1 or 2.. its not that hard");
        		consoleInput.nextLine();
        	}
        	else
        	{
        		AI = consoleInput.nextInt();
        		
        		if(AI <= 2 && AI >=1)
        		{
        			loop1 = false;
        		}
        		else {
        			System.out.println("you are stupid please enter 1 or 2");
        			consoleInput.nextLine();
        		}
        	}
        }


        String yourChoice = "";
        switch(AI){
            case 1:
            isNotAI = true;
            break;
            case 2:
            isNotAI = false;
            System.out.println("Type easy, medium, or hard.");
            yourChoice = consoleInput.next();
        }
        
        int num1 = 0;
        int num2 = 0;
        System.out.println("Player 1 place ships:");
        num1 = player1UI.runInterface(player1Board, consoleInput);
        player1Board = new Board(9, 9, '~', num1, "player1Board");
        player1Printer = new BoardPrinterWrapper(player1Board, 's', '~', true);
        player1place = new PlaceShip(player1Board);
        getCoor.placeShipLoop(player1Board, player1Printer, player1place);
        

        if(isNotAI){
            System.out.println("Player 2 place ships:");
            num2 = player2UI.runInterface(player2Board, consoleInput);
            player2place = new PlaceShip(player2Board);
            getCoor.placeShipLoop(player2Board, player2Printer, player2place);
            getCoor.setRadar(player1Board, player2Board);
            player2Board = new Board(9, 9, '~', num2, "player2Board");
            player2Printer = new BoardPrinterWrapper(player2Board, 's', '~', true);
        } else {
            num2 = PlaceAIShips.determineShips();
            player2Board = new Board(9, 9, '~', num2, "player2Board");
            player2Printer = new BoardPrinterWrapper(player2Board, 's', '~', true);
            switch(yourChoice){
                case "easy":
                Easy = new AIEasy(player2Board);
                Easy.placeShipLoop(player2Board, player2Printer, player2place);
                getCoor.setRadar(player2Board);
                return Easy;
                case "medium":
                Medium = new AIMedium(player1Board);
                Medium.placeShipLoop(player2Board, player2Printer, player2place);
                getCoor.setRadar(player2Board);
                return Medium;
                case "hard":
                Hard = new AIHard(player1Board);
                Hard.placeShipLoop(player2Board, player2Printer, player2place);
                getCoor.setRadar(player2Board);
                return Hard;
            }
        } return getCoor;
        
    } 

    

    public void Play(gameLogicInterface getCoorPlayer, gameLogicInterface getCoorOpponent){
        do
        {
            System.out.println("Player 1 turn");
            if(!getCoorPlayer.Loop(player1Board, player2Board, player1UI, player1Printer, player2Printer)){
                playerWon[1] = true;//for forfeit
            }
            System.out.println("Player 2 turn");
            if (!getCoorOpponent.Loop(player2Board, player1Board, player1UI, player1Printer, player2Printer)) {
                playerWon[0] = true;
            }
        } while (!playerWon[0] && !playerWon[1]);
        if (playerWon[0]) {
            System.out.println("Congratulations! Player 1 has won.");
        } else if (playerWon[1]) {
            System.out.println("Congratulations! Player 2 has won.");
        }
    }

    public void Game() {
        Play(getCoor, Init());
    }
}

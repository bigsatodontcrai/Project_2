/**
 * <h1> Run </h1>
 * <p>Begins running the game </p>
 *
 */
public class Run {
    private GameLoop ShipsBattling = new GameLoop();
    /**
     * Calls GameLoop function Game()
     * @param none
     * @return void
     */
    public void letsPlay() {
        ShipsBattling.Game();
    }
}
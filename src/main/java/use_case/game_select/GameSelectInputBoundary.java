package use_case.game_select;

/**
 * The Logged In Use Case.
 */
public interface GameSelectInputBoundary {

    /**
     * Execute the game library Use Case.
     * @param gameSelectInputData the input data for this use case
     */
    void execute(GameSelectInputData gameSelectInputData);

//    /**
//     * Executes the "search" Use Case.
//     * @param info search input
//     */
//    void search(String info);
}

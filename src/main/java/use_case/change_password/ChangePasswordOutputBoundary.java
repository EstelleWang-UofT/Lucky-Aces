package use_case.change_password;

import interface_adapter.gamelibrary.GameLibraryState;

/**
 * The output boundary for the Change Password Use Case.
 */
public interface ChangePasswordOutputBoundary {
    /**
     * Prepares the success view for the Change Password Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChangePasswordOutputData outputData);

    /**
     * Prepares the failure view for the Change Password Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to the Login View.
     */
    void switchToLoginView();

    /**
     * Switch to game library view use case.
     * @param gameLibraryState current player
     */
    void switchToGameLibraryView(GameLibraryState gameLibraryState);
}

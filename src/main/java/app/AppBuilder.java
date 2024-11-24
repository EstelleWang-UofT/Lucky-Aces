package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.GameInfoDataAccessObject;
import entity.player.CommonPlayerFactory;
import entity.player.PlayerFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.game_setup.GameSetupController;
import interface_adapter.game_setup.GameSetupPresenter;
import interface_adapter.game_setup.GameSetupViewModel;
import interface_adapter.gamelibrary.*;
import use_case.game_setup.GameSetupInputBoundary;
import use_case.game_setup.GameSetupInteractor;
import use_case.game_setup.GameSetupOutputBoundary;
import use_case.gamelibrary.GameLibraryInputBoundary;
import use_case.gamelibrary.GameLibraryInteractor;
import use_case.gamelibrary.GameLibraryOutputBoundary;
import use_case.initialization.InitializationInputBoundary;
import use_case.initialization.InitializationInteractor;
import use_case.initialization.InitializationOutputBoundary;
import view.GameLibraryView;
import view.GameSetupView;
import view.ViewManager;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel mainPanel = new JPanel();
    private final CardLayout mainLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final PlayerFactory playerFactory = new CommonPlayerFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(mainPanel, mainLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final GameInfoDataAccessObject gameInfoDataAccessObject = new GameInfoDataAccessObject("game_info.json");

    private GameLibraryView gameLibraryView;
    private GameLibraryViewModel gameLibraryViewModel;
    private GameSetupView gameSetupView;
    private GameSetupViewModel gameSetupViewModel;

    public AppBuilder() {
        mainPanel.setLayout(mainLayout);
    }

    /**
     * Adds the Game Library View to the application.
     * @return this builder
     */
    public AppBuilder addGameLibraryView() {
        gameLibraryViewModel = new GameLibraryViewModel();
        gameLibraryView = new GameLibraryView(gameLibraryViewModel);
        mainPanel.add(gameLibraryView, gameLibraryView.getViewName());
        return this;
    }

    /**
     * Adds the Game Setup View to the application.
     * @return this builder
     */
    public AppBuilder addGameSetupView() {
        gameSetupViewModel = new GameSetupViewModel();
        gameSetupView = new GameSetupView(gameSetupViewModel);
        mainPanel.add(gameSetupView, gameSetupView.getViewName());
        return this;
    }

    /**
     * Adds the Initialization Use Case to the application.
     * @return this builder
     */
    public AppBuilder addInitializationUseCase() {
        final InitializationOutputBoundary initializationPresenter = new InitializationPresenter(
                gameLibraryViewModel,
                viewManagerModel);
        final InitializationInputBoundary initializationInteractor = new InitializationInteractor(
                gameInfoDataAccessObject,
                initializationPresenter);
        final InitializationController initializationController = new InitializationController(
                initializationInteractor);
        gameLibraryView.setInitializationController(initializationController);
        return this;
    }

    /**
     * Adds the Game Library Case to the application.
     * @return this builder
     */
    public AppBuilder addGameLibraryUseCase() {
        final GameLibraryOutputBoundary gameLibraryPresenter = new GameLibraryPresenter(viewManagerModel,
                gameSetupViewModel,
                gameLibraryViewModel);
        final GameLibraryInputBoundary gameLibraryInteractor = new GameLibraryInteractor(gameLibraryPresenter,
                gameInfoDataAccessObject);

        final GameLibraryController gameLibraryController = new GameLibraryController(gameLibraryInteractor);
        gameLibraryView.setGameLibraryController(gameLibraryController);
        return this;
    }

    /**
     * Adds the Game Setup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addGameSetupUseCase() {
        final GameSetupOutputBoundary gameSetupPresenter = new GameSetupPresenter(viewManagerModel,
                gameLibraryViewModel);
        final GameSetupInputBoundary gameSetupInteractor = new GameSetupInteractor(gameSetupPresenter);
        final GameSetupController gameSetupController = new GameSetupController(gameSetupInteractor);

        gameSetupView.setGameSetupController(gameSetupController);

        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Lucky Aces");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(mainPanel);

        viewManagerModel.setState(gameLibraryView.getViewName());
        // viewManagerModel.firePropertyChanged("");
        // TODO: Is it valid to do this instead?
        gameLibraryViewModel.firePropertyChanged("initialization");

        return application;
    }
}

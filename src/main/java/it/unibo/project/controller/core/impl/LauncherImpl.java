package it.unibo.project.controller.core.impl;

import java.util.List;

import it.unibo.project.controller.core.api.Difficulty;
import it.unibo.project.controller.core.api.Launcher;
import it.unibo.project.controller.core.api.Loader;
import it.unibo.project.controller.core.api.SceneType;
import it.unibo.project.game.model.api.BackgroundCell;
import it.unibo.project.game.model.api.Collectable;
import it.unibo.project.game.model.api.GameWorld;
import it.unibo.project.game.model.api.Obstacle;
import it.unibo.project.game.model.api.Player;
import it.unibo.project.game.model.impl.GameWorldFactoryImpl;
import it.unibo.project.input.api.InputHandler;
import it.unibo.project.view.api.Scene;
import it.unibo.project.view.api.Window;
import it.unibo.project.view.impl.SceneFactoryImpl;
import it.unibo.project.view.impl.WindowFactoryImpl;

/**
 * class {@linkplain LauncherImpl}, implements {@linkplain Launcher}, and it's a
 * {@code singleton} ({@link #LAUNCHER}).
 */
public final class LauncherImpl implements Launcher {
    /**
     * {@code singleton} of {@linkplain Launcher} class.
     */
    public static final Launcher LAUNCHER = new LauncherImpl();

    private final Window window = new WindowFactoryImpl().createWindow();
    // private final GameEngine gameEngine = new
    // GameEngineFactoryImpl().createGameEngine();
    private final GameWorld gameWorld = new GameWorldFactoryImpl().createGameWorld();
    private final Loader loader = new LoaderImpl();

    private SceneType sceneType = SceneType.MENU;
    private Difficulty difficulty = Difficulty.NORMAL;

    private LauncherImpl() {
    }

    @Override
    public synchronized SceneType getSceneType() {
        return this.sceneType;
    }

    @Override
    public synchronized Scene getScene() {
        return this.window.getScene();
    }

    @Override
    public synchronized void setScene(final SceneType sceneType) {
        this.sceneType = sceneType;
        this.window.setScene(new SceneFactoryImpl().createScene(this.sceneType));
    }

    @Override
    public synchronized Difficulty getDifficulty() {
        return this.difficulty;
    }

    @Override
    public synchronized void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public synchronized void start() {
        setScene(this.sceneType);
    }

    @Override
    public synchronized void closeWindow() {
        this.window.close();
    }

    @Override
    public synchronized InputHandler geInputHandler(SceneType sceneType) {
        return getScene().getInputHandler(sceneType);
    }

    @Override
    public synchronized Player getPlayer() {
        return this.gameWorld.getPlayer();
    }

    @Override
    public synchronized List<BackgroundCell> getBackgroundCells() {
        return this.gameWorld.getBackgroundCells();
    }

    @Override
    public synchronized List<Collectable> getCollectables() {
        return this.gameWorld.getCollectables();
    }

    @Override
    public synchronized List<Obstacle> getObstacles() {
        return this.gameWorld.getObstacles();
    }

    @Override
    public synchronized Loader getLoader() {
        return this.loader;
    }

    @Override
    public void saveAndCloseWindow() {
        this.loader.saveStatOnFile(this.gameWorld.getGameStat());
        this.closeWindow();
    }

}

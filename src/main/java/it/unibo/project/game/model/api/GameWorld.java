package it.unibo.project.game.model.api;

import java.util.List;

import it.unibo.project.game.logic.api.GameLogic;

public interface GameWorld {
    Player getPlayer();

    List<Obstacle> getObstacles();

    List<Collectable> getCollectables();

    List<BackgroundCell> getBackgroundCells();

    GameLogic getGameLogic();

    void clearMap();

    void loadMap(String mapName);
}

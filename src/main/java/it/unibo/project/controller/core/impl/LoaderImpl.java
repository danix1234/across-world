package it.unibo.project.controller.core.impl;

import java.awt.Image;
import java.util.List;
import java.util.Random;

import it.unibo.project.controller.core.api.Difficulty;
import it.unibo.project.game.model.api.BackgroundCell;
import it.unibo.project.game.model.api.BackgroundCellType;
import it.unibo.project.game.model.api.Collectable;
import it.unibo.project.game.model.api.CollectableType;
import it.unibo.project.game.model.api.GameStat;
import it.unibo.project.game.model.api.Obstacle;
import it.unibo.project.game.model.api.ObstacleType;
import it.unibo.project.game.model.api.Player;

/**
 * implementation of {@linkplain Loader} class.
 */
public class LoaderImpl extends AbstractImageLoader {

    @Override
    public final GameStat getGameStat() {
        return getGameStatOpt().orElseGet(() -> {
            loadStats();
            return getGameStatOpt().orElseThrow();
        });
    }

    @Override
    public final List<Image> getBackgroundCellSprites(final BackgroundCellType backgroundCellType) {
        return getBackgroundCellImagesOpt().orElseGet(() -> {
            loadImages();
            return getBackgroundCellImagesOpt().orElseThrow();
        }).get(backgroundCellType);
    }

    @Override
    public final List<Image> getCollectablesSprites(final CollectableType collectableType) {
        return getCollectableImagesOpt().orElseGet(() -> {
            loadImages();
            return getCollectableImagesOpt().orElseThrow();
        }).get(collectableType);
    }

    @Override
    public final List<Image> getObstacleSprites(final ObstacleType obstacleType) {
        return getObstacleImagesOpt().orElseGet(() -> {
            loadImages();
            return getObstacleImagesOpt().orElseThrow();
        }).get(obstacleType);
    }

    @Override
    public final List<Image> getPlayerSprites() {
        return getPlayerImagesOpt().orElseGet(() -> {
            loadImages();
            return getPlayerImagesOpt().orElseThrow();
        });
    }

    @Override
    public final Image getElementRandom(final List<Image> collection) {
        return collection.get(new Random().nextInt(collection.size()));
    }

    @Override
    public final List<BackgroundCell> getBackgroundCells(final Difficulty difficulty) {
        return getBackgroundCellsOpt().orElseGet(() -> {
            loadMaps();
            return getBackgroundCellsOpt().orElseThrow();
        }).get(difficulty);
    }

    @Override
    public final List<Collectable> getCollectables(final Difficulty difficulty) {
        return getCollectablesOpt().orElseGet(() -> {
            loadMaps();
            return getCollectablesOpt().orElseThrow();
        }).get(difficulty);
    }

    @Override
    public final List<Obstacle> getObstacles(final Difficulty difficulty) {
        return getObstaclesOpt().orElseGet(() -> {
            loadMaps();
            return getObstaclesOpt().orElseThrow();
        }).get(difficulty);
    }

    @Override
    public final Player getPlayerCell(final Difficulty difficulty) {
        return getPlayerCellOpt().orElseGet(() ->{
            loadMaps();
            return getPlayerCellOpt().orElseThrow();
        }).get(difficulty);
    }
}

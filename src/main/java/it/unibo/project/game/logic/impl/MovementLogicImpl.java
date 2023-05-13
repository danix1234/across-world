package it.unibo.project.game.logic.impl;

import it.unibo.project.controller.core.api.SceneType;
import it.unibo.project.controller.core.impl.LauncherImpl;
import it.unibo.project.game.logic.api.MovementLogic;
import it.unibo.project.game.model.api.Obstacle;
import it.unibo.project.utility.RandomizeLine;
import it.unibo.project.game.model.api.ObstacleType;
import it.unibo.project.utility.Vector2D;

public class MovementLogicImpl implements MovementLogic {
    private final CheckCollisionImpl checker = new CheckCollisionImpl();
    private final HandlePowerupImpl powerupHandler = new HandlePowerupImpl();
    private final RandomizeLine randomizeLine = new RandomizeLine();

    @Override
    public void movePlayer(final int x, final int y) {
        final Vector2D nextPlayerPosition = new Vector2D(x, y);
        final var checkDynamicCollision = checker.checkDynamicObstacleCollision(nextPlayerPosition);

        if (checkDynamicCollision.isPresent()
                && (checkDynamicCollision.map(obstacle -> obstacle.getType()).get().equals(ObstacleType.TRUNK_DX)
                        || checkDynamicCollision.map(obstacle -> obstacle.getType()).get()
                                .equals(ObstacleType.TRUNK_SX))
                && !checker.checkWallCollision(nextPlayerPosition)) {
            LauncherImpl.LAUNCHER.getPlayer().move(x, y);
        } else if (!checkDynamicCollision.isPresent()
                && checker.checkStaticObstacleCollision(nextPlayerPosition).isEmpty()
                && !checker.checkWallCollision(nextPlayerPosition)
                && !checker.checkFinishLineCollision(LauncherImpl.LAUNCHER.getPlayer().getPosition())) {
            checker.checkCollectableCollision(nextPlayerPosition).ifPresent(collectable -> {
                switch (collectable.getType()) {
                    case COIN:
                        LauncherImpl.LAUNCHER.getCollectables().remove(collectable);
                        LauncherImpl.LAUNCHER.getGameStat().addCoins(1);
                        break;
                    default:
                        LauncherImpl.LAUNCHER.getCollectables().remove(collectable);
                        powerupHandler.addPowerUp(collectable.getType());
                }
            });
            LauncherImpl.LAUNCHER.getPlayer().move(x, y);
        } else if (checker.checkFinishLineCollision(LauncherImpl.LAUNCHER.getPlayer().getPosition())) {
            LauncherImpl.LAUNCHER.setScene(SceneType.MENU);
        } else if (checkDynamicCollision.isPresent()) {
            LauncherImpl.LAUNCHER.setScene(SceneType.OVER);
        }
    }

    @Override
    public void moveObstacle() {
        final Vector2D playerPos = LauncherImpl.LAUNCHER.getPlayer().getPosition();
        final var checkDynamicCollision = checker.checkDynamicObstacleCollision(playerPos);

        LauncherImpl.LAUNCHER.getObstacles()
                .stream()
                .filter(Obstacle::isMovable)
                .forEach(obstacle -> {
                    final var type = obstacle.getType();
                    final var pixelX = obstacle.getPixelPosition();
                    final var cellY = obstacle.getPosition().getY();
                    final var wrapAround = type.getWrapAroundDim() * LauncherImpl.CELL_DIM;
                    var speed = type.getSpeed();
                    if (LauncherImpl.RANDOMIZE_SPEED) {
                        speed = this.randomizeLine.getLineRandomNumber(
                                type.getMinSpeed(),
                                type.getMaxSpeed(),
                                cellY);
                    }
                    obstacle.movePixelPosition((pixelX + wrapAround + speed) % wrapAround);
                    final var cellPos = LauncherImpl.LAUNCHER.convertPixelToCellPos(pixelX, cellY);
                    obstacle.move(cellPos.getX(), cellPos.getY());
                });

        if (checkDynamicCollision.isPresent()
                && (checkDynamicCollision.map(obstacle -> obstacle.getType()).get()
                        .equals(ObstacleType.TRUNK_DX)
                        || checkDynamicCollision.map(obstacle -> obstacle.getType()).get()
                                .equals(ObstacleType.TRUNK_DX))
                && !checker.checkWallCollision(playerPos)) {
            if(LauncherImpl.REMAIN_PLAYER_ON_TRUNK){
                LauncherImpl.LAUNCHER.movePlayerIfPossible(checkDynamicCollision.get().getPosition().getX(),
                    checkDynamicCollision.get().getPosition().getY());
            }
        } else if (checkDynamicCollision.isPresent()) {
            LauncherImpl.LAUNCHER.setScene(SceneType.OVER);
        }
    }
}

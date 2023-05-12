package it.unibo.project.game.logic.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import it.unibo.project.controller.core.api.SceneType;
import it.unibo.project.controller.core.impl.LauncherImpl;
import it.unibo.project.game.logic.api.MovementLogic;
import it.unibo.project.game.model.api.Obstacle;
import it.unibo.project.utility.Vector2D;

public class MovementLogicImpl implements MovementLogic {
    private final CheckCollisionImpl checker = new CheckCollisionImpl();
    private final Map<Integer, Integer> lineRandomValue = new HashMap<>();
    private final Random random = new Random();

    @Override
    public void movePlayer(final int x, final int y) {
        final Vector2D nextPlayerPosition = new Vector2D(x, y);

        if (!checker.checkDynamicObstacleCollision(nextPlayerPosition)
                && checker.checkStaticObstacleCollision(nextPlayerPosition).isEmpty()
                && !checker.checkWallCollision(nextPlayerPosition)
                && !checker.checkFinishLineCollision(LauncherImpl.LAUNCHER.getPlayer().getPosition())) {

            LauncherImpl.LAUNCHER.getPlayer().move(x, y);
        } else if (checker.checkFinishLineCollision(LauncherImpl.LAUNCHER.getPlayer().getPosition())) {
            LauncherImpl.LAUNCHER.setScene(SceneType.MENU);
        } else if (checker.checkDynamicObstacleCollision(nextPlayerPosition)) {
            LauncherImpl.LAUNCHER.setScene(SceneType.OVER);
        }
    }

    @Override
    public void moveObstacle() {
        final Vector2D playerPos = LauncherImpl.LAUNCHER.getPlayer().getPosition();

        LauncherImpl.LAUNCHER.getObstacles()
                .stream()
                .filter(Obstacle::isMovable)
                .forEach(obstacle -> {
                    final var type = obstacle.getType();
                    final var pixelX = obstacle.getPixelPosition();
                    final var wrapAround = type.getWrapAroundDim() * LauncherImpl.CELL_DIM;
                    final var cellY = obstacle.getPosition().getY();
                    final var minSpeed = Double.min(type.getMinSpeed(), type.getMaxSpeed());
                    final var maxSpeed = Double.max(type.getMinSpeed(), type.getMaxSpeed());
                    var speed = type.getSpeed();
                    if (LauncherImpl.RANDOMIZE_SPEED) {
                        calculateLineRandomValue(cellY);
                        speed = new Random(this.lineRandomValue.get(cellY)).nextDouble(minSpeed, maxSpeed);
                    }
                    obstacle.movePixelPosition((pixelX + wrapAround + speed) % wrapAround);
                    final var cellPos = LauncherImpl.LAUNCHER.convertPixelToCellPos(pixelX, cellY);
                    obstacle.move(cellPos.getX(), cellPos.getY());
                });

        if (checker.checkDynamicObstacleCollision(playerPos)) {
            LauncherImpl.LAUNCHER.setScene(SceneType.OVER);
        }
    }

    private void calculateLineRandomValue(final int line) {
        if (!lineRandomValue.containsKey(line)) {
            lineRandomValue.put(line, random.nextInt(0, Integer.MAX_VALUE));
        }
    }
}

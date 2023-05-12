package it.unibo.project.game.model.impl;

import it.unibo.project.game.model.api.Obstacle;
import it.unibo.project.game.model.api.ObstacleType;
import it.unibo.project.utility.Vector2D;

public class ObstacleImpl extends EntityImpl implements Obstacle {
	private final ObstacleType type;
	private final double speed;
	private double pixelX = 50;

	public ObstacleImpl(final Vector2D initialPos, final ObstacleType type) {
		super(initialPos, type.isMoveable());
		this.type = type;
		this.speed = type.getSpeed();
	}

	@Override
	public ObstacleType getType() {
		return this.type;
	}

	@Override
	public double getPixelPosition() {
		return this.pixelX;
	}

	@Override
	public void movePixelPosition(final double x) {
		this.pixelX = x;
	}

	@Override
	public double speed() {
		return this.speed;
	}


}

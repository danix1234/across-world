package it.unibo.project.game.model.impl;

import it.unibo.project.game.model.api.Player;
import it.unibo.project.utility.Vector2D;

public class PlayerImpl extends EntityImpl implements Player {

	private int maxDistance = 0;

	public PlayerImpl(Vector2D initialPos) {
		super(initialPos, true);
	}

	@Override
	public int getMaxDistance() {
		return this.maxDistance;
	}

	@Override
	public void move(final int x, final int y) {
		super.move(x, y);
		maxDistance = Integer.max(maxDistance, y);
	}

}

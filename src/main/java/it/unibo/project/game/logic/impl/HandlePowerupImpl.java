package it.unibo.project.game.logic.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import it.unibo.project.controller.core.impl.LauncherImpl;
import it.unibo.project.game.logic.api.HandlePowerup;
import it.unibo.project.game.model.api.CollectableType;

/**
 * class {@code HandlePowerupImpl} implements {@linkplain HandlePowerup}.
 */
public class HandlePowerupImpl implements HandlePowerup {
    private List<CollectableType> powerupTypeList = new LinkedList<>();

    @Override
    public synchronized void addPowerUp(final CollectableType type) {
        this.powerupTypeList.add(type);
        TimerTask timerPowerUp = new TimerTask() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    SwingUtilities.invokeLater(() -> {
                        if (!powerupTypeList.isEmpty()) {
                            powerupTypeList.remove(0);
                        }
                    });
                } catch (final InterruptedException e) {
                    LauncherImpl.LAUNCHER.closeWindow();
                }
            }
        };

        new Thread(timerPowerUp).start();
    }

    @Override
    public synchronized List<CollectableType> getCurrentPowerUp() {
        return this.powerupTypeList;
    }

    @Override
    public synchronized void clearPowerUp() {
        powerupTypeList.clear();
    }
}

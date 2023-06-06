package it.unibo.project.view.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import it.unibo.project.controller.core.api.Difficulty;
import it.unibo.project.controller.core.api.SceneType;
import it.unibo.project.controller.core.impl.LauncherImpl;
import it.unibo.project.input.api.Action;

public class MenuScene extends AbstractScene {
    private JPanel panel;
    private JButton startButton;
    private JComboBox<String> difficultyComboBox;
    private JButton exitButton;
    private JButton shopButton;
    private JButton clearProgressButton;
    private JLabel titleLabel;
    private static final int PANEL_BACKGROUND_RED = 40;
    private static final int PANEL_BACKGROUND_GREEN = 40;
    private static final int PANEL_BACKGROUND_BLUE = 40;
    private static final int TITLE_FONT_SIZE = 50;
    private static final int TITLE_HEIGHT = 50;
    private static final int WHITE_RED = 255;
    private static final int WHITE_GREEN = 255;
    private static final int WHITE_BLUE = 255;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 70;
    private static final int FONT_SIZE = 20;
    private static final int GRID_Y_VALUE = 5;

    public MenuScene() {
        // pannello principale
        this.panel = new JPanel();
        this.panel.setLayout(new GridBagLayout());
        this.panel.setBackground(new Color(PANEL_BACKGROUND_RED, PANEL_BACKGROUND_GREEN, PANEL_BACKGROUND_BLUE));

        // creazione del titolo del gioco
        titleLabel = new JLabel("Across the world");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        titleLabel.setBounds(0, 10, panel.getWidth(), TITLE_HEIGHT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // creazione dei buttons del menu
        startButton = new JButton("START");
        var difficulties = Arrays
                .stream(Difficulty.values())
                .map(Enum::name)
                .toArray(String[]::new);
        difficultyComboBox = new JComboBox<>(difficulties);
        shopButton = new JButton("SHOP");
        clearProgressButton = new JButton("CLEAR PROGRESS");
        exitButton = new JButton("SAVE AND EXIT");
        // bug fix: difficolty didn't change to what is shown
        LauncherImpl.LAUNCHER.setDifficulty(Difficulty.valueOf(difficultyComboBox.getSelectedItem() + ""));

        // elimino effetto testo evidenziato
        startButton.setFocusPainted(false);
        difficultyComboBox.setFocusable(false);
        shopButton.setFocusPainted(false);
        clearProgressButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        // estetica dei buttons
        startButton.setBackground(new Color(WHITE_RED, WHITE_GREEN, WHITE_BLUE));
        startButton.setForeground(Color.BLACK);
        startButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        startButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        difficultyComboBox.setBackground(new Color(WHITE_RED, WHITE_GREEN, WHITE_BLUE));
        difficultyComboBox.setForeground(Color.BLACK);
        difficultyComboBox.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        difficultyComboBox.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        shopButton.setBackground(new Color(WHITE_RED, WHITE_GREEN, WHITE_BLUE));
        shopButton.setForeground(Color.BLACK);
        shopButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        shopButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        clearProgressButton.setBackground(new Color(WHITE_RED, WHITE_GREEN, WHITE_BLUE));
        clearProgressButton.setForeground(Color.BLACK);
        clearProgressButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        clearProgressButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        exitButton.setBackground(new Color(WHITE_RED, WHITE_GREEN, WHITE_BLUE));
        exitButton.setForeground(Color.RED);
        exitButton.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        exitButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        // aggiunta i pulsanti al pannello principale
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        this.panel.add(titleLabel);
        gbc.gridy = 1;
        this.panel.add(startButton, gbc);
        gbc.gridy = 2;
        this.panel.add(difficultyComboBox, gbc);
        gbc.gridy = 3;
        this.panel.add(shopButton, gbc);
        gbc.gridy = 4;
        this.panel.add(clearProgressButton, gbc);
        gbc.gridy = GRID_Y_VALUE;
        this.panel.add(exitButton, gbc);

        // aggiungo gli ActionListener
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                getInputHandler(SceneType.MENU).executeAction(Action.CHANGE_SCENE_TO_GAME);
            }
        });

        difficultyComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                LauncherImpl.LAUNCHER.setDifficulty(Difficulty.valueOf(selectedDifficulty));
            }
        });

        shopButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                getInputHandler(SceneType.MENU).executeAction(Action.CHANGE_SCENE_TO_SHOP);
            }
        });

        clearProgressButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                //LauncherImpl.LAUNCHER.deleteStatFile();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                getInputHandler(SceneType.MENU).executeAction(Action.EXIT_APP);
            }
        });
        setPanel(this.panel);
    }

    @Override
    public void update() {
    }
}

package ru.eltech.ahocorasick.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class ControlArea extends JPanel {

    public ControlArea(GraphicAlgorithmProcessor processor){
        super(null);
        this.processor = processor;
        initPanes();
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new TitledBorder("Control components"));
        Box openBox = createOpenBox();
        Box setupBox = createSetupBox();
        Box stepsBox = createStepsBox();
        Box clearAndExitBox = createClearAndExitBox();
        mainBox.add(openBox);
        mainBox.add(setupBox);
        mainBox.add(stepsBox);
        mainBox.add(clearAndExitBox);
        add(mainBox);
    }

    /**
     * Initialize In and Out panes
     */
    private void initPanes() {
        JScrollPane srcPane = new JScrollPane(srcArea);
        srcPane.setPreferredSize(new Dimension(100, 200));
        srcPane.setBorder(new TitledBorder("Source text"));
        JScrollPane outPane = new JScrollPane(outArea);
        outPane.setPreferredSize(new Dimension(100, 200));
        outPane.setBorder(new TitledBorder("Output text"));
        add(srcPane);
        add(outPane);
    }

    /**
     * Create Initial Box
     * @return Box
     */
    private Box createOpenBox() {
        Box openBox = Box.createHorizontalBox();
        openBox.setBorder((new TitledBorder("Initial actions")));
        JButton fileText = new JButton("Open text");
        JButton fileStrings = new JButton("Open strings");
        niceAddButtons(openBox, fileText, fileStrings);
        fileText.addActionListener(processor::openFileAction);
        return openBox;
    }

    /**
     * This box contains add string button
     * @return Box
     */
    private Box createSetupBox(){
        Box setupBox = Box.createHorizontalBox();
        setupBox.setBorder(new TitledBorder("Algorithm setup"));
        JButton addStringButton = new JButton("Add string");
        niceAddButtons(setupBox, addStringButton);
        addStringButton.addActionListener(processor::addStringAction);
        return setupBox;
    }

    /**
     * Create steps control Box
     * @return Box
     */
    private Box createStepsBox() {
        Box stepsBox = Box.createHorizontalBox();
        stepsBox.setBorder((new TitledBorder("Steps control")));
        JButton stepButton = new JButton("Step");
        JButton finishButton = new JButton("Finish");
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        niceAddButtons(stepsBox, stepButton, finishButton, undoButton, redoButton);
        return stepsBox;
    }

    /**
     * Create Clear and Exit control Box
     * @return Box
     */
    private Box createClearAndExitBox() {
        Box clearAndExitBox = Box.createHorizontalBox();
        clearAndExitBox.setBorder((new TitledBorder("Clear and exit")));
        JButton clear = new JButton("Clear");
        JButton exit = new JButton("Exit");
        niceAddButtons(clearAndExitBox, clear, exit);
        clear.addActionListener(processor::clearAction);
        exit.addActionListener(processor::exitAction);
        return clearAndExitBox;
    }

    private void niceAddButtons(Box box, JButton ... buttons){
        for (JButton button : buttons){
            box.add(Box.createHorizontalGlue());
            box.add(button);
        }
        box.add(Box.createHorizontalGlue());
    }

    private static JTextArea createArea(){
        /*JLabel label = new JLabel(areaName);
        label.setBounds(x, y, 290, 20);
        add(label);*/ //labels
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static void writeToSrcArea(File file) throws FileNotFoundException {
        String str = file.toString();
        FileReader fr = new FileReader(str);
        Scanner scanner = new Scanner(fr);
        String flContent = "";
        while(scanner.hasNextLine()){
            flContent += scanner.nextLine();
        }
        srcArea.append(flContent);
    }

    public static void writeToOutArea(String str){
        outArea.append(str);
    }

    public static int widht = 700;
    public static int height = 500;

    private static final JTextArea srcArea = createArea();
    private static final JTextArea outArea = createArea();
    private final GraphicAlgorithmProcessor processor;
}

package ru.eltech.ahocorasick.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ControlArea extends JPanel {

    public ControlArea(GraphicAlgorithmProcessor processor){
        super(null);
        this.processor = processor;
        initPanes();
        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new TitledBorder ( new LineBorder(Color.lightGray, 1, false), "Control components", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, new Font ( "Georgia", Font.BOLD, 12 ), Color.BLACK));
        Box openBox = createOpenBox();
        Box setupBox = createSetupBox();
        Box stepsBox = createStepsBox();
        Box clearAndExitBox = createClearAndExitBox();
        mainBox.add(openBox);
        mainBox.add(setupBox);
        mainBox.add(stepsBox);
        mainBox.add(clearAndExitBox);
        mainBox.setMaximumSize(new Dimension(300, 100));
        add(mainBox);
        this.setMaximumSize(new Dimension(300, 1024));
    }

    /**
     * Initialize In and Out panes
     */
    private void initPanes() {
        JScrollPane srcPane = new JScrollPane(srcArea);
        srcPane.setPreferredSize(new Dimension(100, 200));
        srcPane.setBorder(new TitledBorder ( new LineBorder(Color.lightGray, 1, false), "Source text", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, new Font ( "Georgia", Font.BOLD, 12 ), Color.BLACK ));
        JScrollPane outPane = new JScrollPane(outArea);
        outPane.setPreferredSize(new Dimension(100, 200));
        outPane.setBorder(new TitledBorder ( new LineBorder(Color.lightGray, 1, false), "Output text", TitledBorder.CENTER,
                TitledBorder.DEFAULT_POSITION, new Font ( "Georgia", Font.BOLD, 12 ), Color.BLACK ));
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
        fileStrings.addActionListener(processor::openStringsAction);
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
        stepButton.addActionListener(processor::stepAction);
        finishButton.addActionListener(processor::finishAction);
        undoButton.addActionListener(processor::undoAction);
        redoButton.addActionListener(processor::redoAction);
        return stepsBox;
    }

    /**
     * Create Clear and Exit control Box
     * @return Box
     */
    private Box createClearAndExitBox() {
        Box clearAndExitBox = Box.createHorizontalBox();
        clearAndExitBox.setBorder((new TitledBorder("Clear and exit")));
        JButton restart = new JButton("Restart");
        JButton clear = new JButton("Clear");
        JButton exit = new JButton("Exit");
        niceAddButtons(clearAndExitBox, restart, clear, exit);
        clear.addActionListener(processor::clearAction);
        exit.addActionListener(processor::exitAction);
        restart.addActionListener(processor::restartAction);
        return clearAndExitBox;
    }

    /**
     * Adding buttons method
     * @param box required box
     * @param buttons required buttons
     */
    private void niceAddButtons(Box box, JButton ... buttons){
        for (JButton button : buttons){
            box.add(Box.createHorizontalGlue());
            box.add(button);
        }
        box.add(Box.createHorizontalGlue());
    }

    /**
     * Initializer for JTextArea
     * @return JTextArea
     */
    private static JTextArea createArea(){
        JTextArea area = new JTextArea();
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    /**
     * Writes file to SrcArea
     * @param file input file
     * @throws IOException in case of in/out problem
     */
    public static void writeToSrcArea(File file) throws IOException {

        String str = file.toString();
        FileReader fr = new FileReader(str);
        Scanner scanner = new Scanner(fr);
        StringBuilder flContent = new StringBuilder();
        while(scanner.hasNextLine()){
            flContent.append(scanner.nextLine());
        }
        srcArea.append(flContent.toString());
        fr.close();
    }

    /**
     * Saves into file from SrcArea and OutArea
     * @param file input file
     * @throws IOException in case of in/out problem
     */
    public static void saveFromOutArea(File file) throws IOException{
        FileWriter fw = new FileWriter(file);
        fw.write("Source text:\n" + srcArea.getText() + "\n\n");
        fw.write("Results:\n" + outArea.getText());
        fw.close();
    }

    /**
     * Getter for srcAre
     * @return srcAre
     */
    public static JTextArea getSrcArea() {
        return srcArea;

    }

    /**
     * Getter for outArea
     * @return outArea
     */
    public static JTextArea getOutArea() {
        return outArea;
    }

    private static final JTextArea srcArea = createArea();
    private static final JTextArea outArea = createArea();
    private final GraphicAlgorithmProcessor processor;
}

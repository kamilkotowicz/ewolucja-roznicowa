package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiffEvoFrame extends JFrame implements ActionListener {
    //Definiowanie elementów JFrame
    JFrame frame;
    JTextField populationSizeTextField;
    JTextField maxIterTextField;
    JTextField dimensionsTextField;
    JPanel restrictionsPanel;
    JButton ConfirmButton;
    JTextField restrictionsMinTextField;
    JTextField restrictionsMaxTextField;
    JLabel resultLabel;
    JPanel resultPanel;
    int dimensions = 3;
    int populationSize = 15;
    int maxIter = 1000;
    double[] minimumRestrictions = new double[]{-1.2, -3.4, -5.6};
    double[] maximumRestrictions = new double[]{1.2, 3.4, 5.6};
    JComboBox MutationStrategyComboBox;
    JComboBox CrossoverStrategyComboBox;
    JComboBox FunctionComboBox;
    Main.MutationStrategy MutStrat = Main.MutationStrategy.CURRENT_1;
    Main.CrossoverStrategy CrossStrat = Main.CrossoverStrategy.BIN;

    DiffEvoFrame() {

        JLabel populationSizeLabel = new JLabel();
        populationSizeLabel.setText("Enter size of population");
        populationSizeLabel.setVerticalAlignment(JLabel.TOP);
        populationSizeLabel.setBounds(0, 0, 250, 100);

        populationSizeTextField = new JTextField();
        populationSizeTextField.setBounds(0,25,150,25);
        populationSizeTextField.setText("15");

        JPanel populationSizePanel = new JPanel();
        populationSizePanel.setBounds(0, 0, 250, 50);
        populationSizePanel.setLayout(null);
        populationSizePanel.add(populationSizeLabel);
        populationSizePanel.add(populationSizeTextField);

        JLabel MutationStrategyLabel = new JLabel();
        MutationStrategyLabel.setText("Choose mutation strategy");
        MutationStrategyLabel.setVerticalAlignment(JLabel.TOP);
        MutationStrategyLabel.setBounds(0, 0, 250, 100);

        MutationStrategyComboBox = new JComboBox(
                new String[]{"RAND_1", "BEST_1", "CURRENT_1", "CURRENT_TO_BEST_1", "RAND_2", "BEST_2", "CURRENT_2"});
        MutationStrategyComboBox.setBounds(0,25,150,25);
        MutationStrategyComboBox.addActionListener(this);

        JPanel MutationStrategyPanel = new JPanel();
        MutationStrategyPanel.setBounds(300, 0, 150, 50);
        MutationStrategyPanel.setLayout(null);
        MutationStrategyPanel.add(MutationStrategyLabel);
        MutationStrategyPanel.add(MutationStrategyComboBox);

        JLabel CrossoverStrategyLabel = new JLabel();
        CrossoverStrategyLabel.setText("Choose crossover strategy");
        CrossoverStrategyLabel.setVerticalAlignment(JLabel.TOP);
        CrossoverStrategyLabel.setBounds(0, 0, 250, 100);

        CrossoverStrategyComboBox = new JComboBox(
                new String[]{"BIN", "EXP"});
        CrossoverStrategyComboBox.setBounds(0,25,150,25);
        CrossoverStrategyComboBox.addActionListener(this);

        JPanel CrossoverStrategyPanel = new JPanel();
        CrossoverStrategyPanel.setBounds(300, 75, 250, 50);
        CrossoverStrategyPanel.setLayout(null);
        CrossoverStrategyPanel.add(CrossoverStrategyLabel);
        CrossoverStrategyPanel.add(CrossoverStrategyComboBox);

        JLabel FunctionLabel = new JLabel();
        FunctionLabel.setText("Choose function");
        FunctionLabel.setVerticalAlignment(JLabel.TOP);
        FunctionLabel.setBounds(0, 0, 250, 100);

        FunctionComboBox = new JComboBox(
                new String[]{"Ackley", "Griewanka", "Linear", "Michalewicz", "Rastrigin", "RosenbrockBanana",
                        "Schwefel", "Shubert", "Sphere", "Square", "Zakharov1", "Zakharov"});
        FunctionComboBox.setBounds(0,25,150,25);
        FunctionComboBox.addActionListener(this);

        JPanel FunctionPanel = new JPanel();
        FunctionPanel.setBounds(300, 150, 250, 50);
        FunctionPanel.setLayout(null);
        FunctionPanel.add(FunctionLabel);
        FunctionPanel.add(FunctionComboBox);

        JLabel maxIterLabel = new JLabel();
        maxIterLabel.setText("Enter maximum number of iteration");
        maxIterLabel.setVerticalAlignment(JLabel.TOP);
        maxIterLabel.setBounds(0, 0, 250, 100);

        maxIterTextField = new JTextField();
        maxIterTextField.setBounds(0,25,150,25);
        maxIterTextField.setText("1000");

        JPanel maxIterPanel = new JPanel();
        maxIterPanel.setBounds(0, 50, 250, 50);
        maxIterPanel.setLayout(null);
        maxIterPanel.add(maxIterLabel);
        maxIterPanel.add(maxIterTextField);

        JLabel dimensionsLabel = new JLabel();
        dimensionsLabel.setText("Enter number of dimensions");
        dimensionsLabel.setVerticalAlignment(JLabel.TOP);
        dimensionsLabel.setBounds(0, 0, 250, 100);

        dimensionsTextField = new JTextField();
        dimensionsTextField.setBounds(0,25,150,25);
        dimensionsTextField.setText("3");

        JPanel dimensionsPanel = new JPanel();
        dimensionsPanel.setBounds(0, 100, 250, 50);
        dimensionsPanel.setLayout(null);
        dimensionsPanel.add(dimensionsLabel);
        dimensionsPanel.add(dimensionsTextField);

        JLabel restrictionsMinLabel = new JLabel();
        restrictionsMinLabel.setText("Enter minimum restrictions");
        restrictionsMinLabel.setVerticalAlignment(JLabel.TOP);
        restrictionsMinLabel.setBounds(0, 0, 250, 25);

        restrictionsMinTextField = new JTextField();
        restrictionsMinTextField.setBounds(0,25,300,25);
        restrictionsMinTextField.setText("-1.2, -3.4, -5.6");

        JLabel restrictionsMaxLabel = new JLabel();
        restrictionsMaxLabel.setText("Enter maximum restrictions");
        restrictionsMaxLabel.setVerticalAlignment(JLabel.TOP);
        restrictionsMaxLabel.setBounds(0, 75, 250, 25);

        restrictionsMaxTextField = new JTextField();
        restrictionsMaxTextField.setBounds(0,100,300,25);
        restrictionsMaxTextField.setText("1.2, 3.4, 5.6");

        ConfirmButton = new JButton();
        ConfirmButton.setBounds(300, 250, 100, 50);
        ConfirmButton.addActionListener(this);
        ConfirmButton.setText("Confirm");

        restrictionsPanel = new JPanel();
        restrictionsPanel.setBounds(0, 150, 300, 150);
        restrictionsPanel.setLayout(null);
        restrictionsPanel.add(restrictionsMinLabel);
        restrictionsPanel.add(restrictionsMaxLabel);
        restrictionsPanel.add(restrictionsMinTextField);
        restrictionsPanel.add(restrictionsMaxTextField);

        resultLabel = new JLabel();
        resultLabel.setText("");
        resultLabel.setVerticalAlignment(JLabel.TOP);
        resultLabel.setBounds(0, 0, 300, 500);
        resultPanel = new JPanel();
        resultPanel.setBounds(0, 300, 300, 500);
        resultPanel.setLayout(null);
        resultPanel.add(resultLabel);

        frame = new JFrame();

        frame.setTitle("Differential Evolution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setSize(800, 800);
        frame.add(populationSizePanel);
        frame.add(maxIterPanel);
        frame.add(dimensionsPanel);
        frame.add(restrictionsPanel);
        frame.add(resultPanel);
        frame.add(MutationStrategyPanel);
        frame.add(CrossoverStrategyPanel);
        frame.add(FunctionPanel);
        frame.add(ConfirmButton);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==ConfirmButton){
            populationSize = Integer.parseInt(populationSizeTextField.getText());
            System.out.printf("Population Size %d\n", populationSize);
            maxIter = Integer.parseInt(maxIterTextField.getText());
            System.out.printf("Maximum number of iterations %d\n", maxIter);
            dimensions = Integer.parseInt(dimensionsTextField.getText());
            System.out.printf("Dimensions %d\n", dimensions);
            minimumRestrictions = new double[dimensions];
            maximumRestrictions = new double[dimensions];

            String[] numbersArray = restrictionsMinTextField.getText().split(",");
            for (int i = 0; i < numbersArray.length; i++) {
                minimumRestrictions[i] = Double.parseDouble(numbersArray[i]);
            }
            numbersArray = restrictionsMaxTextField.getText().split(",");
            for (int i = 0; i < numbersArray.length; i++) {
                maximumRestrictions[i] = Double.parseDouble(numbersArray[i]);
            }

            DifferentialEvolution de = new DifferentialEvolution(populationSize, maxIter);
            switch (MutationStrategyComboBox.getSelectedIndex()){
                case 0:
                    MutStrat = Main.MutationStrategy.RAND_1;
                    break;
                case 1:
                    MutStrat = Main.MutationStrategy.BEST_1;
                    break;
                case 2:
                    MutStrat = Main.MutationStrategy.CURRENT_1;
                    break;
                case 3:
                    MutStrat = Main.MutationStrategy.CURRENT_TO_BEST_1;
                    break;
                case 4:
                    MutStrat = Main.MutationStrategy.RAND_2;
                    break;
                case 5:
                    MutStrat = Main.MutationStrategy.BEST_2;
                    break;
                case 6:
                    MutStrat = Main.MutationStrategy.CURRENT_2;
                    break;
            }
            switch (CrossoverStrategyComboBox.getSelectedIndex()){
                case 0:
                    CrossStrat = Main.CrossoverStrategy.BIN;
                    break;
                case 1:
                    CrossStrat = Main.CrossoverStrategy.EXP;
                    break;
            }
            TestFunction TFunction = new RastriginFunction();
            switch (FunctionComboBox.getSelectedIndex()){
                case 0:
                    TFunction = new AckleyFunction();
                    break;
                case 1:
                    TFunction = new GriewankaFunction();
                    break;
                case 2:
                    TFunction = new LinearFunction();
                    break;
                case 3:
                    TFunction = new MichalewiczFunction();
                    break;
                case 4:
                    TFunction = new RastriginFunction();
                    break;
                case 5:
                    TFunction = new RosenbrockBananaFunction();
                    break;
                case 6:
                    TFunction = new SchwefelFunction();
                    break;
                case 7:
                    TFunction = new ShubertFunction();
                    break;
                case 8:
                    TFunction = new SphereFunction();
                    break;
                case 9:
                    TFunction = new SquareFunction();
                    break;
                case 10:
                    TFunction = new Zakharov1Function();
                    break;
                case 11:
                    TFunction = new ZakharovFunction();
                    break;
            }
            try {
                resultLabel.setText(de.evolve(dimensions, minimumRestrictions, maximumRestrictions, TFunction, MutStrat, CrossStrat));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
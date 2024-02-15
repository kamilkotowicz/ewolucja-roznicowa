package org.example;

import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    enum MutationStrategy{
        RAND_1,
        BEST_1,
        CURRENT_1,
        CURRENT_TO_BEST_1,
        RAND_2,
        BEST_2,
        CURRENT_2
    }

    enum CrossoverStrategy{
        BIN,
        EXP
    }

    public static void main(String[] args) throws Exception {

        JFrame frame = new DiffEvoFrame();}
}
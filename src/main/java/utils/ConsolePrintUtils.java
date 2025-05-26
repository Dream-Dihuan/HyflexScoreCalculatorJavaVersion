package utils;

import static config.FontColorConfig.ANSI_GREEN;
import static config.FontColorConfig.ANSI_RESET;

public class ConsolePrintUtils {
    public static void print(String... lines) {
        System.out.println(ANSI_GREEN + "------------------------------------------------------------------------" + ANSI_RESET);

        for (String line : lines) {
            System.out.printf(ANSI_GREEN + line + ANSI_RESET + "\n");
        }

        System.out.println(ANSI_GREEN + "------------------------------------------------------------------------" + ANSI_RESET + "\n");
    }
}

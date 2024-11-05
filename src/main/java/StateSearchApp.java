import java.util.Scanner;

public class StateSearchApp {
    private static final String[] states = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California",
            "Colorado", "Connecticut", "Delaware", "Florida", "Georgia",
            "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
            "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland",
            "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
            "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
            "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont",
            "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nMenu:");
            System.out.println("1) Display the text (States)");
            System.out.println("2) Search");
            System.out.println("3) Exit program");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    displayStates();
                    break;
                case 2:
                    System.out.print("Enter a part of the name of a state to search: ");
                    String pattern = scanner.nextLine();
                    searchPattern(pattern);
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        } while (option != 3);

        scanner.close();
    }

    private static void displayStates() {
        System.out.println("List of States:");
        for (String state : states) {
            System.out.println(state);
        }
    }

    private static void searchPattern(String pattern) {
        int[] badCharTable = createBadCharTable(pattern);
        for (String state : states) {
            System.out.println("Searching in: " + state);
            int index = boyerMoore(state, pattern, badCharTable);
            if (index != -1) {
                System.out.println("Pattern found in '" + state + "' at index: " + index);
            } else {
                System.out.println("Pattern not found in '" + state + "'");
            }
        }
    }

    private static int[] createBadCharTable(String pattern) {
        final int ALPHABET_SIZE = 256; // Extended ASCII
        int[] badCharTable = new int[ALPHABET_SIZE];
        for (int i = 0; i < badCharTable.length; i++) {
            badCharTable[i] = -1; // Initialize with -1
        }
        for (int i = 0; i < pattern.length(); i++) {
            badCharTable[(int) pattern.charAt(i)] = i; // Store the last occurrence of each character
        }
        return badCharTable;
    }

    private static int boyerMoore(String text, String pattern, int[] badCharTable) {
        int m = pattern.length();
        int n = text.length();
        int skip;

        for (int i = 0; i <= n - m; ) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    skip = Math.max(1, j - badCharTable[text.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) {
                return i; // Match found
            }
            i += skip;
        }
        return -1; // No match found
    }
}

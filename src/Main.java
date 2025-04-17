import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // ANSI color and style codes
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BOLD = "\u001B[1m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";

    static String[][] stocks;
    static int numStocks;
    static int[] catalogSizes;
    static ArrayList<String> insertHistory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static boolean isStockSetup = false;

    public static void main(String[] args) {
        displayWelcomeBanner();

        int choice;
        while (true) {
            displayMainMenu();
            System.out.print("\nEnter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Invalid input! Please enter a number." + ANSI_RESET);
                pressEnterToContinue();
                continue;
            }

            switch (choice) {
                case 1:
                    setupStock();
                    break;
                case 2:
                    if (!isStockSetup) {
                        System.out.println(ANSI_RED + "You need to set up stock first (Option 1)!" + ANSI_RESET);
                        pressEnterToContinue();
                        break;
                    }
                    viewStock();
                    pressEnterToContinue();
                    break;
                case 3:
                    if (!isStockSetup) {
                        System.out.println(ANSI_RED + "You need to set up stock first (Option 1)!" + ANSI_RESET);
                        pressEnterToContinue();
                        break;
                    }
                    insertProduct();
                    pressEnterToContinue();
                    break;
                case 4:
                    if (!isStockSetup) {
                        System.out.println(ANSI_RED + "You need to set up stock first (Option 1)!" + ANSI_RESET);
                        pressEnterToContinue();
                        break;
                    }
                    updateProduct();
                    pressEnterToContinue();
                    break;
                case 5:
                    if (!isStockSetup) {
                        System.out.println(ANSI_RED + "You need to set up stock first (Option 1)!" + ANSI_RESET);
                        pressEnterToContinue();
                        break;
                    }
                    deleteProduct();
                    pressEnterToContinue();
                    break;
                case 6:
                    if (!isStockSetup) {
                        System.out.println(ANSI_RED + "You need to set up stock first (Option 1)!" + ANSI_RESET);
                        pressEnterToContinue();
                        break;
                    }
                    viewInsertHistory();
                    pressEnterToContinue();
                    break;
                case 7:
                    System.out.println(ANSI_GREEN + "Exiting program. Goodbye!" + ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(ANSI_RED + "Invalid choice. Please try again." + ANSI_RESET);
                    pressEnterToContinue();
            }
        }
    }

    static void displayWelcomeBanner() {
        clearScreen();

        System.out.print(ANSI_BLUE + ANSI_BOLD + """
    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
    ║                                                                                                                                                                                                           ║
    """ + ANSI_RESET);

        String[] bigText = {
                "║ ██████╗ ██████╗  ██████╗ ██████╗ ██╗   ██╗ ██████╗████████╗    ███████╗████████╗ ██████╗  ██████╗██╗  ██╗    ███╗   ███╗ █████╗ ███╗   ██╗ █████╗  ██████╗ ███████╗███╗   ███╗███████╗███╗   ██╗████████╗ ║\n",
                "║ ██╔══██╗██╔══██╗██╔═══██╗██╔══██╗██║   ██║██╔════╝╚══██╔══╝    ██╔════╝╚══██╔══╝██╔═══██╗██╔════╝██║ ██╔╝    ████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝ ██╔════╝████╗ ████║██╔════╝████╗  ██║╚══██╔══╝ ║\n",
                "║ ██████╔╝██████╔╝██║   ██║██║  ██║██║   ██║██║        ██║       ███████╗   ██║   ██║   ██║██║     █████╔╝     ██╔████╔██║███████║██╔██╗ ██║███████║██║  ███╗█████╗  ██╔████╔██║█████╗  ██╔██╗ ██║   ██║    ║\n",
                "║ ██╔═══╝ ██╔══██╗██║   ██║██║  ██║██║   ██║██║        ██║       ╚════██║   ██║   ██║   ██║██║     ██╔═██╗     ██║╚██╔╝██║██╔══██║██║╚██╗██║██╔══██║██║   ██║██╔══╝  ██║╚██╔╝██║██╔══╝  ██║╚██╗██║   ██║    ║\n",
                "║ ██║     ██║  ██║╚██████╔╝██████╔╝╚██████╔╝╚██████╗   ██║       ███████║   ██║   ╚██████╔╝╚██████╗██║  ██╗    ██║ ╚═╝ ██║██║  ██║██║ ╚████║██║  ██║╚██████╔╝███████╗██║ ╚═╝ ██║███████╗██║ ╚████║   ██║    ║\n",
                "║ ╚═╝     ╚═╝  ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝  ╚═════╝   ╚═╝       ╚══════╝   ╚═╝    ╚═════╝  ╚═════╝╚═╝  ╚═╝    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝ ╚═════╝ ╚══════╝╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝   ╚═╝    ║\n"
        };

        // Print each line of the bigText with a delay
        for (String line : bigText) {
            System.out.print(ANSI_BLUE + ANSI_BOLD + line + ANSI_RESET);
            try {
                Thread.sleep(400); // Sleep for 1 second between lines
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.print(ANSI_BLUE + ANSI_BOLD + """
    ║                                                                                                                                                                                                           ║
    ║                                                                             PRODUCT STOCK MANAGEMENT SYSTEM                                                                                               ║
    ║                                                                             ───────────────────────────────                                                                                               ║
    ║                                                                                 ORGANIZE • TRACK • MANAGE                                                                                                 ║
    ║                                                                                                                                                                                                           ║
    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
    """ + ANSI_RESET);

        pressEnterToContinue();
    }




    static void displayMainMenu() {
        clearScreen();
        System.out.println(ANSI_CYAN + "╔" + "═".repeat(48) + "╗" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║" + ANSI_YELLOW + ANSI_BOLD + "          PRODUCT STOCK MANAGEMENT MENU         " + ANSI_RESET + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "╠" + "═".repeat(48) + "╣" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "1. Set up Stock." + (isStockSetup ? ANSI_GREEN + " (✓)" + ANSI_RESET : "") + " ".repeat(30 - (isStockSetup ? 4 : 0)) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "2. View Stock." + " ".repeat(32) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "3. Insert Product to Stock." + " ".repeat(19) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "4. Update Product to Stock." + " ".repeat(19) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "5. Delete Product from Stock." + " ".repeat(17) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "6. View insertion history." + " ".repeat(20) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "║  " + ANSI_RESET + "7. Exit program..." + " ".repeat(28) + ANSI_CYAN + "║" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "╚" + "═".repeat(48) + "╝" + ANSI_RESET);
    }

    static void setupStock() {
        clearScreen();
        displaySectionHeader("SETUP STOCK");

        numStocks = getIntInput("[+] Insert number of Stock: ");
        catalogSizes = new int[numStocks];

        for (int i = 0; i < numStocks; i++) {
            System.out.println(ANSI_YELLOW + "'Insert number of catalogue for each stock.'" + ANSI_RESET);
            catalogSizes[i] = getIntInput("[+] Insert number of catalogue on stock [" + (i+1) + "]: ");
        }

        stocks = new String[numStocks][];
        for (int i = 0; i < numStocks; i++) {
            stocks[i] = new String[catalogSizes[i]];
            for (int j = 0; j < catalogSizes[i]; j++) {
                stocks[i][j] = "EMPTY";
            }
        }

        System.out.println(ANSI_GREEN + "===== Setup Stock is Successfully =====" + ANSI_RESET);
        isStockSetup = true;
        viewStock();
    }

    static void viewStock() {
        clearScreen();
        displaySectionHeader("VIEW STOCK");

        for (int i = 0; i < numStocks; i++) {
            System.out.print(ANSI_BLUE + "Stock [" + (i+1) + "] -> " + ANSI_RESET);
            for (int j = 0; j < catalogSizes[i]; j++) {
                String itemColor = stocks[i][j].equals("EMPTY") ? ANSI_RED : ANSI_GREEN;
                System.out.print("[ " + (j+1) + " - " + itemColor + stocks[i][j] + ANSI_RESET + " ] ");
            }
            System.out.println();
        }
    }

    static void insertProduct() {
        clearScreen();
        displaySectionHeader("INSERT PRODUCT");

        viewStock();

        int stockIndex = getIntInput("[+] Insert stock number: ") - 1;
        if (stockIndex < 0 || stockIndex >= numStocks) {
            System.out.println(ANSI_RED + "Invalid stock number!" + ANSI_RESET);
            return;
        }

        // to check empty slot
        boolean hasEmptySlot = false;
        for (int j = 0; j < catalogSizes[stockIndex]; j++) {
            if (stocks[stockIndex][j].equals("EMPTY")) {
                hasEmptySlot = true;
                break;
            }
        }

        if (!hasEmptySlot) {
            System.out.println(ANSI_RED + "This stock is full! Cannot insert more products." + ANSI_RESET);
            return;
        }

        int catalogIndex = getIntInput("[+] Insert catalog number: ") - 1;
        if (catalogIndex < 0 || catalogIndex >= catalogSizes[stockIndex]) {
            System.out.println(ANSI_RED + "Invalid catalog number!" + ANSI_RESET);
            return;
        }

        if (!stocks[stockIndex][catalogIndex].equals("EMPTY")) {
            System.out.println(ANSI_RED + "This slot is already occupied by: " + stocks[stockIndex][catalogIndex] + ANSI_RESET);
            return;
        }

        System.out.print("[+] Insert product name: ");
        String productName = scanner.nextLine();

        if (productName.trim().isEmpty()) {
            System.out.println(ANSI_RED + "Product name cannot be empty!" + ANSI_RESET);
            return;
        }

        stocks[stockIndex][catalogIndex] = productName;

        // Add to history
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        insertHistory.add(timestamp + " - Inserted: " + productName + " to Stock " + (stockIndex+1) + ", Catalog " + (catalogIndex+1));

        System.out.println(ANSI_GREEN + "Product successfully inserted!" + ANSI_RESET);
        viewStock();
    }

    static void updateProduct() {
        clearScreen();
        displaySectionHeader("UPDATE PRODUCT");

        viewStock();

        int stockIndex = getIntInput("[+] Insert stock number to update: ") - 1;
        if (stockIndex < 0 || stockIndex >= numStocks) {
            System.out.println(ANSI_RED + "Invalid stock number!" + ANSI_RESET);
            return;
        }

        int catalogIndex = getIntInput("[+] Insert catalog number to update: ") - 1;
        if (catalogIndex < 0 || catalogIndex >= catalogSizes[stockIndex]) {
            System.out.println(ANSI_RED + "Invalid catalog number!" + ANSI_RESET);
            return;
        }

        if (stocks[stockIndex][catalogIndex].equals("EMPTY")) {
            System.out.println(ANSI_RED + "This slot is empty! Nothing to update." + ANSI_RESET);
            return;
        }

        System.out.println("Current product: " + ANSI_CYAN + stocks[stockIndex][catalogIndex] + ANSI_RESET);
        System.out.print("[+] Insert new product name: ");
        String newProductName = scanner.nextLine();

        if (newProductName.trim().isEmpty()) {
            System.out.println(ANSI_RED + "Product name cannot be empty!" + ANSI_RESET);
            return;
        }

        String oldProductName = stocks[stockIndex][catalogIndex];
        stocks[stockIndex][catalogIndex] = newProductName;

        // Add to history
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        insertHistory.add(timestamp + " - Updated: " + oldProductName + " to " + newProductName +
                " in Stock " + (stockIndex+1) + ", Catalog " + (catalogIndex+1));

        System.out.println(ANSI_GREEN + "Product successfully updated!" + ANSI_RESET);
        viewStock();
    }

    static void deleteProduct() {
        clearScreen();
        displaySectionHeader("DELETE PRODUCT");

        viewStock();

        int stockIndex = getIntInput("[+] Insert stock number to delete from: ") - 1;
        if (stockIndex < 0 || stockIndex >= numStocks) {
            System.out.println(ANSI_RED + "Invalid stock number!" + ANSI_RESET);
            return;
        }

        System.out.print("[+] Insert product name to delete: ");
        String productName = scanner.nextLine();

        if (productName.trim().isEmpty()) {
            System.out.println(ANSI_RED + "Product name cannot be empty!" + ANSI_RESET);
            return;
        }

        // Check multi and ask user to choose which one to delete
        ArrayList<Integer> matchingPositions = new ArrayList<>();
        for (int j = 0; j < catalogSizes[stockIndex]; j++) {
            if (!stocks[stockIndex][j].equals("EMPTY") &&
                    stocks[stockIndex][j].toLowerCase().equals(productName.toLowerCase())) {
                matchingPositions.add(j);
            }
        }

        if (matchingPositions.isEmpty()) {
            System.out.println(ANSI_RED + "Product not found in Stock [" + (stockIndex+1) + "]!" + ANSI_RESET);
            return;
        }

        if (matchingPositions.size() == 1) {
            int positionToDelete = matchingPositions.get(0);
            deleteProductFromPosition(stockIndex, positionToDelete);
            return;
        }

        System.out.println(ANSI_YELLOW + "Multiple instances of '" + productName + "' found in Stock [" + (stockIndex+1) + "]:" + ANSI_RESET);
        for (int i = 0; i < matchingPositions.size(); i++) {
            int catalogPos = matchingPositions.get(i);
            System.out.println((i+1) + ". Catalog position " + (catalogPos+1));
        }

        int selectionIndex = getIntInput("[+] Select which one to delete (1-" + matchingPositions.size() + "): ") - 1;

        if (selectionIndex < 0 || selectionIndex >= matchingPositions.size()) {
            System.out.println(ANSI_RED + "Invalid selection!" + ANSI_RESET);
            return;
        }

        int positionToDelete = matchingPositions.get(selectionIndex);
        deleteProductFromPosition(stockIndex, positionToDelete);
    }

    static void deleteProductFromPosition(int stockIndex, int catalogIndex) {
        // Add to history
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        insertHistory.add(timestamp + " - Deleted: " + stocks[stockIndex][catalogIndex] +
                " from Stock " + (stockIndex+1) + ", Catalog " + (catalogIndex+1));

        stocks[stockIndex][catalogIndex] = "EMPTY";
        System.out.println(ANSI_GREEN + "Product deleted successfully!" + ANSI_RESET);
        viewStock();
    }

    static void viewInsertHistory() {
        clearScreen();
        displaySectionHeader("INSERT HISTORY");

        if (insertHistory.isEmpty()) {
            System.out.println(ANSI_YELLOW + "No history available." + ANSI_RESET);
        } else {
            System.out.println(ANSI_CYAN + "┌" + "─".repeat(75) + "┐" + ANSI_RESET);
            for (String entry : insertHistory) {
                System.out.println(ANSI_CYAN + "│ " + ANSI_RESET + entry + " ".repeat(Math.max(0, 74 - entry.length())) + ANSI_CYAN + "│" + ANSI_RESET);
            }
            System.out.println(ANSI_CYAN + "└" + "─".repeat(75) + "┘" + ANSI_RESET);
        }
    }

    static void displaySectionHeader(String title) {
        System.out.println(ANSI_PURPLE + "╔" + "═".repeat(48) + "╗" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "║" + ANSI_YELLOW + ANSI_BOLD + centerText(title, 48) + ANSI_RESET + ANSI_PURPLE + "║" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "╚" + "═".repeat(48) + "╝" + ANSI_RESET);
    }

    static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

    static int getIntInput(String prompt) {
        int input = 0;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Please enter a valid number!" + ANSI_RESET);
            }
        }

        return input;
    }

    static void pressEnterToContinue() {
        System.out.println(ANSI_YELLOW + "\nPress Enter to continue..." + ANSI_RESET);
        scanner.nextLine();
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
import java.util.Scanner;

public class QuickChat {

    private static final String USERNAME = "student";
    private static final String PASSWORD = "pass123";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        if (!login(scanner)) {
            System.out.println("Login failed. Exiting QuickChat.");
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages would you like to send? ");
        int numMessages = Integer.parseInt(scanner.nextLine().trim());

        boolean running = true;

        while (running) {
            System.out.println("\n--- Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    sendMessages(scanner, numMessages);
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("\nTotal messages sent: " + Message.returnTotalMessagess());
        System.out.println("\nAll sent messages:\n" + Message.printMessages());

        scanner.close();
    }

    private static boolean login(Scanner scanner) {
        System.out.print("Enter username: ");
        String user = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String pass = scanner.nextLine().trim();

        return user.equals(USERNAME) && pass.equals(PASSWORD);
    }

    private static void sendMessages(Scanner scanner, int numMessages) {

        for (int i = 1; i <= numMessages; i++) {

            System.out.println("\n--- Message " + i + " ---");

            System.out.print("Enter recipient cell (+27...): ");
            String recipient = scanner.nextLine().trim();

            System.out.print("Enter message (max 250 chars): ");
            String text = scanner.nextLine().trim();

            Message msg = new Message(recipient, text, i);

            if (!msg.checkMessageID()) {
                System.out.println("Invalid Message ID");
                i--;
                continue;
            }

            System.out.println("Message ID: " + msg.getMessageID());

            String cellCheck = msg.checkRecipientCell();
            System.out.println(cellCheck);
            if (!cellCheck.contains("successfully")) {
                i--;
                continue;
            }

            String lengthCheck = msg.checkMessageLength();
            System.out.println(lengthCheck);
            if (!lengthCheck.equals("Message ready to send.")) {
                i--;
                continue;
            }

            System.out.println("Message Hash: " + msg.createMessageHash());

            System.out.println("\n1) Send");
            System.out.println("2) Disregard");
            System.out.println("3) Store");
            System.out.print("Choice: ");

            int sendChoice = Integer.parseInt(scanner.nextLine().trim());

            System.out.println(msg.SentMessage(sendChoice));

            if (sendChoice == 1) {
                System.out.println("\n--- SENT ---");
                System.out.println("ID: " + msg.getMessageID());
                System.out.println("Hash: " + msg.getMessageHash());
                System.out.println("Recipient: " + msg.getRecipientCell());
                System.out.println("Message: " + msg.getMessageText());
            }
        }

        System.out.println("\nMessages sent so far: " + Message.returnTotalMessagess());
    }
}


import java.util.ArrayList;
import java.util.Random;

public class Message {

    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;

    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public Message(String recipientCell, String messageText, int messageNumber) {
        this.recipientCell = recipientCell;
        this.messageText = messageText;
        this.messageNumber = messageNumber;

        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random rand = new Random();

        // FIX: ensure 10-digit string consistently
        long id = 1_000_000_000L + (long)(rand.nextDouble() * 9_000_000_000L);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public String checkRecipientCell() {
        // FIX: stricter validation (must start with + and be digits after)
        if (recipientCell.matches("\\+\\d{10,13}")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);

        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");

        messageHash = (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(
                        "Message ID: " + messageID +
                        "\nMessage Hash: " + messageHash +
                        "\nRecipient: " + recipientCell +
                        "\nMessage: " + messageText
                );
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                storeMessage();
                return "Message successfully stored.";

            default:
                return "Invalid option selected.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent.";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("--- Message ").append(i + 1).append(" ---\n");
            sb.append(sentMessages.get(i)).append("\n");
        }

        return sb.toString();
    }

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

  public void storeMessage() {
    System.out.println("Message stored successfully.");
}

    public static void resetMessages() {
        sentMessages.clear();
        totalMessagesSent = 0;
    }

    // getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipientCell() { return recipientCell; }
    public String getMessageText() { return messageText; }
    public int getMessageNumber() { return messageNumber; }
}

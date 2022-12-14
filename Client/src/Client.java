import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Client {
    public static void main(String[] args) throws IOException {

        String host = "localhost";
        int port = 2525;

        /* Mails */

        BufferedReader is = new BufferedReader(new FileReader(
                ".\\CONFIG\\mail.config", StandardCharsets.UTF_8));
        LinkedList<Mail> mails = new LinkedList<>();

        try {
            while (is.ready()) {
                String s = is.readLine();
                String[] ss = s.split(", ");
                mails.add(new Mail(Integer.parseInt(ss[0]), ss[1]));
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

        if (mails.size() < 3) {
            throw new RuntimeException("There must be at least 3 mails to create a group!");
        }

        /* Groups */

        is = new BufferedReader(new FileReader(
                ".\\CONFIG\\group.config", StandardCharsets.UTF_8));
        LinkedList<Group> groups = new LinkedList<>();

        try {
            while (is.ready()) {
                String s = is.readLine();
                String[] ss = s.split(", ");
                LinkedList<Mail> receivers = new LinkedList<>();
                for (int i = 0; i < ss.length - 1; ++i) {
                    Mail m = Mail.getInstance(Integer.parseInt(ss[i + 1]));
                    if (m != null) {
                        receivers.add(m);
                    } else {
                        throw new RuntimeException("There is no mail with id " + ss[i + 1]);
                    }
                }
                groups.add(new Group(Mail.getInstance(Integer.parseInt(ss[0])), receivers));
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

        if (groups.size() < 1) {
            throw new RuntimeException("There must be at least 1 group for the Client to run correctly!");
        }

        /* Messages */

        is = new BufferedReader(new FileReader(
                ".\\CONFIG\\message.config", StandardCharsets.UTF_8));
        LinkedList<Message> messages = new LinkedList<>();

        try {
            int i = 0;
            String header = "";
            String message = "";
            while (is.ready()) {
                switch (i) {
                    case 0 -> {
                        header = is.readLine();
                        message = "";
                        i = 1;
                    }
                    case 1 -> {
                        String s = is.readLine();
                        if (s.equals(".")) {
                            i = 0;
                            messages.add(new Message(header, message.substring(0, message.length() - 1)));
                        } else {
                            message += s + "\n";
                        }
                    }
                    default ->
                            throw new RuntimeException("i has been put to something else than 0 and 1 in message reading.");
                }
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

        if (messages.size() < 1) {
            throw new RuntimeException("There must be at least 1 message for the Client to run correctly!");
        }

        /* Groups selection */

        for (int i = 0; i < groups.size(); ++i) {
            System.out.println("Group " + (i + 1) + " :");
            System.out.println(groups.get(i));
        }
        System.out.print("""

                Please select which groups shall send a message
                Input groups numbers separated by ',' and no spaces :\s""");

        LinkedList<Group> selectedGroups = null;
        boolean invalid = true;
        while (invalid) {
            try {
                selectedGroups = new LinkedList<>();
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                String s = input.readLine();

                String[] ss = s.split(",");
                invalid = false;

                for (String g : ss) {
                    try {
                        selectedGroups.add(groups.get(Integer.parseInt(g) - 1));
                    } catch (Exception ex) {
                        System.out.print("\n" + g + " is not a valid Group number, please try again : ");
                        invalid = true;
                        break;
                    }
                }
            } catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }
        }

        /* Messages selection */

        final String CRLF = "\r\n";

        for (Group g : selectedGroups) {
            System.out.println("\nPreparing to send prank for the following group : ");
            System.out.println(g);

            for (int i = 0; i < messages.size(); ++i) {
                System.out.println("\nMessage " + (i + 1) + " :");
                System.out.println(messages.get(i));
            }
            System.out.print("""

                    Please select which messages shall be sent for that group
                    Input messages numbers separated by ',' and no spaces :\s""");

            LinkedList<Message> selectedMessages = null;
            invalid = true;
            while (invalid) {
                try {
                    selectedMessages = new LinkedList<>();
                    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                    String s = input.readLine();

                    String[] ss = s.split(",");
                    invalid = false;

                    for (String m : ss) {
                        try {
                            selectedMessages.add(messages.get(Integer.parseInt(m) - 1));
                        } catch (Exception ex) {
                            System.out.print("\n" + m + " is not a valid Message number, please try again : ");
                            invalid = true;
                            break;
                        }
                    }
                } catch (IOException ioEx) {
                    System.out.println(ioEx.getMessage());
                }
            }

            /* Message Sending */

            PrintWriter out;
            Socket servSocket = null;

            for (Message msg : selectedMessages) {
                try {
                    servSocket = new Socket(host, port);
                } catch (FileNotFoundException fnfEx) {
                    System.out.println("A problem occured oppening the socket : " + fnfEx.getMessage());
                }

                if (servSocket != null) {
                    out = new PrintWriter(
                            new OutputStreamWriter(
                                    servSocket.getOutputStream(), StandardCharsets.UTF_8), true);
                    out.write("EHLO leo" + CRLF);
                    out.write("MAIL FROM:" + g.getSender().getMail() + CRLF);
                    for (Mail receivers : g.getReceivers()) {
                        out.write("RCPT TO:" + receivers.getMail() + CRLF);
                    }
                    out.write("DATA" + CRLF);

                    out.write("Content-Type: text/plain; charset=utf-8" + CRLF);
                    out.write("From: " + g.getSender().getMail() + CRLF);
                    out.write("To: " + g.getReceivers().getFirst().getMail());

                    for (int i = 1; i < g.getReceivers().size(); i++) {
                        out.write(", " + g.getReceivers().get(i).getMail());
                    }

                    out.write(CRLF);
                    out.write("Subject: " + msg.getHeader() + CRLF + CRLF);
                    out.write(msg.getBody());
                    out.write(CRLF + "." + CRLF);
                    System.out.println("\nMail written.");

                    out.write("QUIT\r\n");
                    out.close();
                }
            }
        }
    }
}

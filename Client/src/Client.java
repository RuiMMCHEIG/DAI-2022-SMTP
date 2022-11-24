import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Client {
    public static void main(String[] args) {
        try {

            /* Mails */

            BufferedReader is = new BufferedReader(new FileReader(".\\CONFIG\\mail.config"));
            LinkedList<Mail> mails = new LinkedList<>();

            try {
                while(is.ready()) {
                    String s = is.readLine();
                    String[] ss = s.split(", ");
                    mails.add(new Mail(Integer.parseInt(ss[0]), ss[1]));
                }
            }
            catch(IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }

            if (mails.size() < 3) {
                throw new RuntimeException("There must be at least 3 mails to create a group!");
            }

            /* Groups */

            is = new BufferedReader(new FileReader(".\\CONFIG\\group.config"));
            LinkedList<Group> groups = new LinkedList<>();

            try {
                while(is.ready()) {
                    String s = is.readLine();
                    String[] ss = s.split(", ");
                    LinkedList<Mail> receivers = new LinkedList<>();
                    for (int i = 0; i < ss.length - 1; ++i) {
                        Mail m = Mail.getInstance(Integer.parseInt(ss[i + 1]));
                        if (m != null) {
                            receivers.add(m);
                        }
                        else {
                            throw new RuntimeException("There is no mail with id " + ss[i + 1]);
                        }
                    }
                    groups.add(new Group(Mail.getInstance(Integer.parseInt(ss[0])), receivers));
                }
            }
            catch(IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }

            if (groups.size() < 1) {
                throw new RuntimeException("There must be at least 1 group for the Client to run correctly!");
            }

            /* Messages */

            is = new BufferedReader(new FileReader(".\\CONFIG\\message.config"));
            LinkedList<Message> messages = new LinkedList<>();

            try {
                int i = 0;
                String header = "";
                String message = "";
                while(is.ready()) {
                    switch (i) {
                        case 0 -> {
                            header = is.readLine();
                            message = "";
                            i = 1;
                        }
                        case 1 -> {
                            String s = is.readLine();
                            if (s.isEmpty()) {
                                i = 0;
                                messages.add(new Message(header, message));
                            } else {
                                message += s + "\n";
                            }
                        }
                        default ->
                                throw new RuntimeException("i has been put to something else than 0 and 1 in message reading.");
                    }
                }
                if (!header.isEmpty() && !message.isEmpty()) {
                    messages.add(new Message(header, message));
                }
            }
            catch(IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }

            if (messages.size() < 1) {
                throw new RuntimeException("There must be at least 1 message for the Client to run correctly!");
            }

            /* Links */

            Random r = new Random();
            for (Group g : groups) {
                g.setMessage(messages.get(r.nextInt(messages.size())));
            }
        }
        catch(FileNotFoundException fnfEx) {
            System.out.println(fnfEx.getMessage());
        }
    }
}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

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

            /* Groups */
            is = new BufferedReader(new FileReader(".\\CONFIG\\group.config"));
            LinkedList<Group> groups = new LinkedList<>();

            try {
                while(is.ready()) {
                    String s = is.readLine();
                    String[] ss = s.split(", ");
                    Integer[] in = new Integer[ss.length - 1];
                    for (int i = 0; i < in.length; ++i) {
                        in[i] = Integer.parseInt(ss[i + 1]);
                    }
                    groups.add(new Group(Integer.parseInt(ss[0]), in));
                }
            }
            catch(IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }

            /* Messages */
            is = new BufferedReader(new FileReader(".\\CONFIG\\message.config"));
            LinkedList<Message> messages = new LinkedList<>();

            try {
                int i = 0;
                while(is.ready()) {
                    String header = "";
                    String message = "";
                    switch (i) {
                        case 0 -> {
                            header = is.readLine();
                            message = "";
                            i = 1;
                        }
                        case 1 -> {
                            String s = is.readLine();
                            if (s.equals("\n")) {
                                i = 0;
                                messages.add(new Message(header, message));
                            } else {
                                message += s;
                            }
                        }
                        default ->
                                throw new RuntimeException("i has been put to something else than 0 and 1 in message reading.");
                    }
                }
            }
            catch(IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }
            System.out.println(messages);
        }
        catch(FileNotFoundException fnfEx) {
            System.out.println(fnfEx.getMessage());
        }
    }
}

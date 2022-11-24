import java.util.LinkedList;
import java.util.regex.Pattern;

public class Mail {
    private final int id;
    private final String mail;

    static LinkedList<Integer> ids;
    static LinkedList<String> mails;
    static LinkedList<Mail> instances;

    static String emailRegex =
            "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    static {
        ids = new LinkedList<>();
        mails = new LinkedList<>();
        instances = new LinkedList<>();
    }

    public Mail(int id, String mail) {
        if (ids.contains(id)) {
            throw new IllegalArgumentException("An e-mail already has this id!");
        }
        if (mails.contains(mail)) {
            throw new IllegalArgumentException("This e-mail already exists!");
        }

        // Mail check
        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(mail).matches()) {
            throw new IllegalArgumentException(mail + " is not a valid e-mail!");
        }

        // Valid mail
        ids.add(id);
        mails.add(mail);
        this.id = id;
        this.mail = mail;
        instances.add(this);
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    @Override
    public String toString() {
        return "Mail id : " + id + ", mail : " + mail;
    }

    public static Mail getInstance(int id) {
        for (Mail m : instances) {
            if (m.id == id) {
                return m;
            }
        }
        return null;
    }
}

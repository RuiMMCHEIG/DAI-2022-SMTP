import java.util.LinkedList;

public class Mail {
    private final int id;
    private final String mail;

    static LinkedList<Integer> ids;
    static LinkedList<String> mails;

    static {
        ids = new LinkedList<>();
        mails = new LinkedList<>();
    }

    public Mail(int id, String mail) {
        if (ids.contains(id)) {
            throw new IllegalArgumentException("An e-mail already has this id!");
        }
        if (mails.contains(mail)) {
            throw new IllegalArgumentException("This e-mail already exists!");
        }
        ids.add(id);
        mails.add(mail);
        this.id = id;
        this.mail = mail;
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }
}

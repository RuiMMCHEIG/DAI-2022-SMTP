import java.util.LinkedList;

public class Group {
    private final Mail sender;
    private final LinkedList<Mail> receivers;

    {
        receivers = new LinkedList<>();
    }

    public Group(Mail sender, LinkedList<Mail> receivers) {
        if (receivers.size() < 2) {
            throw new IllegalArgumentException("There must be at least 2 receivers in a group!");
        }
        this.sender = sender;
        this.receivers.addAll(receivers);
    }

    public Mail getSender() {
        return sender;
    }

    public LinkedList<Mail> getReceivers() {
        return (LinkedList<Mail>) receivers.clone();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Group sender : " + sender.getMail() + "\nGroup receivers :");
        for (Mail m : receivers) {
            s.append("\n-").append(m.getMail());
        }
        return s.toString();
    }
}

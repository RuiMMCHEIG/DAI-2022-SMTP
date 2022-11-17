import java.util.LinkedList;
import java.util.List;

public class Group {
    private final int sender;
    private final LinkedList<Integer> receivers;

    {
        receivers = new LinkedList<>();
    }

    public Group(int sender, Integer[] receivers) {
        if (receivers.length < 2) {
            throw new IllegalArgumentException("There must be at least 2 receivers in a group!");
        }
        this.sender = sender;
        this.receivers.addAll(List.of(receivers));
    }

    public int getSender() {
        return sender;
    }

    public Integer[] getReceivers() {
        return (Integer[]) receivers.toArray();
    }
}

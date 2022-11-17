public class Group {
    private final int sender;
    private final Integer[] receivers;

    public Group(int sender, Integer[] receivers) {
        if (receivers.length < 2) {
            throw new IllegalArgumentException("There must be at least 2 receivers in a group!");
        }
        this.sender = sender;
        this.receivers = receivers;
    }

    public int getSender() {
        return sender;
    }

    public Integer[] getReceivers() {
        return receivers.clone();
    }
}

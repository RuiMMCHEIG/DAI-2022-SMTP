public class Message {
    private final String header;
    private final String body;

    public Message(String header, String body) {
        if (header.isEmpty()) {
            throw new RuntimeException("A message's header can't be empty!");
        }
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}

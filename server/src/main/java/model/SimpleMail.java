package model;

public class SimpleMail {
    private final String source;
    private final String destinations;
    private final String object;
    private final String content;
    //TODO USE THEM
    /*private int id;
    private long timestamp;*/

    public SimpleMail(String source, String destinations, String object, String content) {
        this.source = source;
        this.destinations = destinations;
        this.object = object;
        this.content = content;
    }

    public String getSource() { return source; }
    public String getDestinations() { return destinations; }
    public String getObject() { return object; }
    public String getContent() { return content; }
}

package CVScreening.message;

import CVScreening.model.CV;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Message implements Serializable {

	private static final long serialVersionUID = -3686472195559526951L;
	private String from;
    private CV to;
    private String title, body;

    public String getFrom() {
        return from;
    }

    public Message setFrom(String from) {
        this.from = from;
        return this;
    }

    public CV getTo() {
        return to;
    }

    public Message setTo(CV to) {
        this.to = to;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Message setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Message setBody(String body) {
        this.body = body;
        return this;
    }
    
    @Override
    public String toString() {
    	String to = getTo().getInfo().getLastName().toLowerCase().trim() +
                new Random().nextInt(100) +
                "@gmail.com";

    	return "\n\tSEND MESSAGE:" + "\n" +
    			"\tFrom: " + getFrom() + "\n" +
    			"\tTo: " + to + "\n" +
    			"\tTitle: " + getTitle() + "\n" +
    			"\tBody: " + getBody() + "\n";
    }
}
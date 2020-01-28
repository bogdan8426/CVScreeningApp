package CVScreening.email;

import CVScreening.DataModel.CV;

import java.io.Serializable;
import java.util.ArrayList;

public class Email implements Serializable {

	private static final long serialVersionUID = -3686472195559526951L;
	private String from;
    private ArrayList<CV> to;
    private String title, body;

    public String getFrom() {
        return from;
    }

    public Email setFrom(String from) {
        this.from = from;
        return this;
    }

    public ArrayList<CV> getTo() {
        return to;
    }

    public Email setTo(ArrayList<CV> to) {
        this.to = to;
        return this;
    }

    public Email setTo(CV to) {
    	ArrayList<CV> toList = new ArrayList<CV>();
    	toList.add(to);
        setTo(toList);
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Email setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Email setBody(String body) {
        this.body = body;
        return this;
    }
    
    @Override
    public String toString() {
    	ArrayList<CV> clients = getTo();
    	StringBuilder clientsTo = new StringBuilder();
    	for (CV c: clients) {
    		clientsTo.append(c.getInfo().getFirstName());
    	}

    	
    	return "SEND EMAIL:" + "\n" + 
    			"From: " + getFrom() +
    			"To: " + clientsTo +
    			"Title: " + getTitle() + "\n" +
    			"Body: " + getBody() + "\n";
    }
}
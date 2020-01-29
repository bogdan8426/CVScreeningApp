package CVScreening.message;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queue implements Serializable {

	private static final long serialVersionUID = -367534955230149744L;
	
	private List<Message> messages = Collections.synchronizedList(new LinkedList<>());
	
    public void add(Message message) {
        messages.add(message);
    }

    public Message get() {
        if (messages.size() > 0) {
            return messages.remove(messages.size() - 1);
        }
        return null;
    }
    
}

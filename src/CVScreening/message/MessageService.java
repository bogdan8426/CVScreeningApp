package CVScreening.message;

import CVScreening.CVGenerator.CVGenerator;
import CVScreening.exceptions.CVFilesReadException;
import CVScreening.model.CV;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageService implements Runnable, Serializable {
	
	private static final long serialVersionUID = -6872857384878095572L;
	private Queue queue = new Queue();
	private boolean closed;
    private int sentMessages = 0;
    private static Logger log = Logger.getLogger(MessageService.class.getName());
    
	public MessageService() {
    	new Thread(this).start();
    }
	
    @Override
    public void run() {
        Message message;
        while (true) {
        	if(closed) {
            	return;
            }
            if ((message = queue.get()) != null) {
                sendMessage(message);
            }
            try {
            	synchronized(queue) {
                    log.info("Candidates waiting for messages..");
            		queue.wait();
            	}
            } catch (InterruptedException e) {
                log.warning("Message failed, thread interrupted!");
                return;
            }
        }
    }
    
    public int getSentMessages() {
		return sentMessages;
	}
    
    private void sendMessage(Message message) {
        log.info(message.getTo().getInfo().getFirstName() + "  successfully received the message:");
        log.info(message.toString());
        sentMessages++;
    }

    public void sendNotification(Message message) throws MessageException {
        if (!closed) {
            queue.add(message);
            synchronized(queue) {
                log.info("Message to " + message.getTo().getInfo().getFirstName() + " on its way...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.warning("Message failed to reach destination!");
                }
        		queue.notify();
        	}
        } else
            throw new MessageException("Mailbox is closed!");
    }

    public void close() {
	    log.info("Closing mailbox...");
    	closed = true;
    	synchronized(queue) {
    		queue.notify();
	    }
    }
    
}

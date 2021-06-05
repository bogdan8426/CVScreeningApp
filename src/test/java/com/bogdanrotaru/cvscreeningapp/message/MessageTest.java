package com.bogdanrotaru.cvscreeningapp.message;

import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.Education;
import com.bogdanrotaru.cvscreeningapp.model.Experience;
import com.bogdanrotaru.cvscreeningapp.model.PersonalInfo;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.TimeInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageTest {

    private static CV cv;
    private MessageService messageService = new MessageService();

    @BeforeAll
    public static void init() throws CVFilesReadException {
        Education education = new Education(TimeInterval.parse("From 2019-08-12 to 2020-10-12 (1 years and 2 months)"),
                "name",
                Domain.SCIENCE);
        Experience experience = new Experience(TimeInterval.parse("From 2019-02-12 to 2020-10-12 (1 years and 8 months)"),
                "pos","des","com",
                Domain.SCIENCE);
        Experience experience2 = new Experience(TimeInterval.parse("From 2015-05-12 to 2019-10-12 (3 years and 5 months)"),
                "pos2","des2","com2",
                Domain.SCIENCE);

        cv = new CV(new PersonalInfo("ION", "POPESCU","321", LocalDate.now(),null),
                Collections.singletonList(education),
                Arrays.asList(experience,experience2));
    }


    @Test
    public void testSendMails() throws InterruptedException {

        for (int i=0; i<5; i++) {
            try {
                messageService.
                        sendNotification(new Message()
                                .setFrom("Recruiter")
                                .setTo(cv)
                                .setTitle("Hi, " + "TEST" + "!")
                                .setBody("TEST BODY"));
            } catch (MessageException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000);
        }

        assertEquals(5, messageService.getSentMessages());

    }

    @Test
    public void testMessageException(){
        messageService.close();

        Throwable throwable = assertThrows(MessageException.class, this::sendOne);
        assertEquals("Mailbox is closed!", throwable.getMessage());
    }

    private void sendOne() throws MessageException {
        messageService.
                sendNotification(new Message()
                        .setFrom("Recruiter")
                        .setTo(cv)
                        .setTitle("Hi, " + "TEST" + "!")
                        .setBody("TEST BODY"));
    }
}

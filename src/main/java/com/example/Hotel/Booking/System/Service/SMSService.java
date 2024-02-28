package com.example.Hotel.Booking.System.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SMSService {


    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;


    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTwilioPhoneNumber() {
        return twilioPhoneNumber;
    }

    public void setTwilioPhoneNumber(String twilioPhoneNumber) {
        this.twilioPhoneNumber = twilioPhoneNumber;
    }

    public void sendSMS(String to, String body) {
        try {
            Twilio.init(accountSid, authToken);

            System.out.println("in sms service " + accountSid + " token " + authToken + " phone " + twilioPhoneNumber);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                    body
            ).create();

            System.out.println("after sms service " + accountSid + " token " + authToken + " phone " + twilioPhoneNumber);
            System.out.println("SMS sent with SID: " + message.getSid());
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
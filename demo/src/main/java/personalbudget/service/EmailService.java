package personalbudget.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.Method;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;





@Service
public class EmailService {
	@Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public void sendResetToken(String toEmail, String token) throws IOException {

        Email from = new Email(fromEmail);
        String subject = "Your Password Reset Token";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Your token is: " + token);

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        System.out.println("Status Code: " + response.getStatusCode());
    }
	
	
	

}


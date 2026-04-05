package personalbudget.service;





import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.Method;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
@Service
public class EmailService {
	
	 @Value("${sendgrid.api.key}")
	    private String apiKey;

	    @Value("${sendgrid.from.email}")
	    private String fromEmail;

	    public void sendResetToken(String toEmail, String link) throws IOException {

	        Email from = new Email(fromEmail);
	        String subject = "Password Reset";
	        Email to = new Email(toEmail);

	        Content content = new Content("text/plain",
	                "Click this link to reset your password:\n" + link);

	        Mail mail = new Mail(from, subject, to, content);

	        SendGrid sg = new SendGrid(apiKey);
	        Request request = new Request();

	        request.setMethod(Method.POST);
	        request.setEndpoint("mail/send");
	        request.setBody(mail.build());

	        Response response = sg.api(request);

	        System.out.println("Status Code: " + response.getStatusCode());
	    }
	    
	    public void sendEmail(String to, String subject, String text) {
	        System.out.println("Sending email to: " + to);
	        System.out.println("Subject: " + subject);
	        System.out.println("Text: " + text);
	    }
		
	

}

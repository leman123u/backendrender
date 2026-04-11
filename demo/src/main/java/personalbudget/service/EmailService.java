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
	
	@Value("${SENDGRID_API_KEY}")
	private String apiKey;

	    @Value("${sendgrid.from.email}")
	    private String fromEmail;

	      private void sendPlainText(String toEmail, String subject, String body) throws IOException {
	        Email from = new Email(fromEmail);
	        Email to = new Email(toEmail);
	        Content content = new Content("text/plain", body);
	        Mail mail = new Mail(from, subject, to, content);

	        SendGrid sg = new SendGrid(apiKey);
	        Request request = new Request();
	        request.setMethod(Method.POST);
	        request.setEndpoint("mail/send");
	        request.setBody(mail.build());

	        Response response = sg.api(request);
	        int code = response.getStatusCode();
	        System.out.println("SendGrid status: " + code);
	        if (code >= 300) {
	            throw new IOException("SendGrid HTTP " + code + ": " + response.getBody());
	        }
	    }

	    public void sendResetToken(String toEmail, String link) throws IOException {
	        String body = "Click this link to reset your password:\n" + link;
	        sendPlainText(toEmail, "Password Reset", body);
	    }
	    
	    public void sendEmail(String to, String subject, String text) throws IOException {
	        sendPlainText(to, subject, text);
	    }
		
	

}

package com.gfashion.restclient;

import com.gfashion.restclient.magento.exception.CustomerException;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class SendGridEmailClient {

    @Autowired
    private SendGrid sendGridClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${sendgrid.api.length}")
    private String codeLength;

    @Value("${sendgrid.api.project}")
    private String project;

    @Value("${sendgrid.api.type}")
    private String type;

    @Value("${sendgrid.api.senderEmail}")
    private String senderEmail;

    public String sendVerificationCode(String to) throws CustomerException {
        String code = RandomStringUtils.random(Integer.valueOf(codeLength), false, true);
        Context ctx = new Context();
        ctx.setVariable("project", project);
        ctx.setVariable("type", type);
        ctx.setVariable("code", code);
        String body = templateEngine.process("VerificationCodeEmail", ctx);

        try {
            Response response = sendEmail(senderEmail, to, "Test Email", new Content("text/html", body));
            System.out.println("Status Code: " + response.getStatusCode() + ", Body: " + response.getBody() + ", Headers: "
                    + response.getHeaders());
            if (response.getStatusCode() == 403) {
                throw new CustomerException(HttpStatus.FORBIDDEN, response.getBody());
            }
        } catch (CustomerException e) {
            throw e;
        } catch (IOException e) {
            throw new CustomerException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        return "We send verification code to " + to;
    }

    private Response sendEmail(String from, String to, String subject, Content content) throws IOException {
        Mail mail = new Mail(new Email(from), subject, new Email(to), content);
        Request request = new Request();
        Response response = null;
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = this.sendGridClient.api(request);
        } catch (IOException e) {
            throw e;
        }
        return response;
    }
}

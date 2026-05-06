package com.seriesxevents.services;

import com.seriesxevents.models.ContactSubmission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${app.admin.email}")
    private String adminEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAdminNotification(ContactSubmission s) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(msg, true, "UTF-8");
            h.setTo(adminEmail);
            h.setSubject("New Inquiry: " + (s.getEventType() != null ? s.getEventType() : "Event") + " — SeriesX Events");
            h.setText(adminHtml(s), true);
            mailSender.send(msg);
            log.info("Admin notification sent for submission #{}", s.getId());
        } catch (Exception e) {
            log.error("Admin email failed: {}", e.getMessage());
        }
    }

    public void sendUserConfirmation(ContactSubmission s) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(msg, true, "UTF-8");
            h.setTo(s.getEmail());
            h.setSubject("We received your message — SeriesX Events");
            h.setText(userHtml(s), true);
            mailSender.send(msg);
            log.info("Confirmation sent to {}", s.getEmail());
        } catch (Exception e) {
            log.error("User email failed: {}", e.getMessage());
        }
    }

    public void sendNewsletterWelcome(String email) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper h = new MimeMessageHelper(msg, true, "UTF-8");
            h.setTo(email);
            h.setSubject("Welcome to SeriesX Events Newsletter!");
            h.setText(newsletterHtml(email), true);
            mailSender.send(msg);
            log.info("Welcome email sent to {}", email);
        } catch (Exception e) {
            log.error("Newsletter email failed: {}", e.getMessage());
        }
    }

    private String adminHtml(ContactSubmission s) {
        return "<html><body style='font-family:Arial,sans-serif;background:#f5f5f5;padding:20px;'>"
            + "<div style='max-width:600px;margin:0 auto;background:#fff;border-radius:8px;overflow:hidden;'>"
            + "<div style='background:#D4A853;padding:24px 32px;'>"
            + "<h2 style='margin:0;color:#0A0A0A;'>New Event Inquiry — SeriesX Events</h2></div>"
            + "<div style='padding:32px;'><table style='width:100%;border-collapse:collapse;font-size:14px;'>"
            + "<tr><td style='padding:10px 0;border-bottom:1px solid #eee;color:#888;width:130px;'>Name</td>"
            + "<td style='padding:10px 0;border-bottom:1px solid #eee;font-weight:600;'>" + s.getFirstName() + " " + s.getLastName() + "</td></tr>"
            + "<tr><td style='padding:10px 0;border-bottom:1px solid #eee;color:#888;'>Email</td>"
            + "<td style='padding:10px 0;border-bottom:1px solid #eee;'>" + s.getEmail() + "</td></tr>"
            + "<tr><td style='padding:10px 0;border-bottom:1px solid #eee;color:#888;'>Phone</td>"
            + "<td style='padding:10px 0;border-bottom:1px solid #eee;'>" + (s.getPhone() != null ? s.getPhone() : "Not provided") + "</td></tr>"
            + "<tr><td style='padding:10px 0;border-bottom:1px solid #eee;color:#888;'>Event Type</td>"
            + "<td style='padding:10px 0;border-bottom:1px solid #eee;'>" + (s.getEventType() != null ? s.getEventType() : "Not specified") + "</td></tr>"
            + "<tr><td style='padding:10px 0;color:#888;vertical-align:top;'>Message</td>"
            + "<td style='padding:10px 0;'>" + s.getMessage() + "</td></tr>"
            + "</table></div>"
            + "<div style='background:#f9f9f9;padding:16px 32px;text-align:center;color:#aaa;font-size:12px;'>SeriesX Events</div>"
            + "</div></body></html>";
    }

    private String userHtml(ContactSubmission s) {
        return "<html><body style='font-family:Arial,sans-serif;background:#f5f5f5;padding:20px;'>"
            + "<div style='max-width:600px;margin:0 auto;background:#0A0A0A;border-radius:8px;overflow:hidden;'>"
            + "<div style='background:#D4A853;padding:24px 32px;text-align:center;'>"
            + "<h1 style='margin:0;color:#0A0A0A;letter-spacing:3px;'>SERIESX EVENTS</h1></div>"
            + "<div style='padding:40px 32px;color:#fff;'>"
            + "<h2 style='color:#D4A853;margin-top:0;'>Hi " + s.getFirstName() + ", we got your message!</h2>"
            + "<p style='color:rgba(255,255,255,0.7);line-height:1.8;'>Your inquiry about a <strong style='color:#D4A853;'>"
            + (s.getEventType() != null ? s.getEventType() : "event")
            + "</strong> has been received. We will contact you within <strong style='color:#fff;'>24–48 hours</strong>.</p>"
            + "<div style='background:#1a1a1a;border-left:3px solid #D4A853;padding:18px;border-radius:4px;margin:24px 0;'>"
            + "<p style='color:rgba(255,255,255,0.45);margin:0 0 8px;font-size:11px;text-transform:uppercase;'>Your Message</p>"
            + "<p style='color:#fff;margin:0;'>" + s.getMessage() + "</p></div>"
            + "<p style='color:rgba(255,255,255,0.45);font-size:13px;'>Call us: <a href='tel:+91 8929830925' style='color:#D4A853;'>+91 8929830925 </a></p>"
            + "</div><div style='background:#111;padding:16px 32px;text-align:center;color:#555;font-size:12px;'>© 2026 SeriesX Events</div>"
            + "</div></body></html>";
    }

    private String newsletterHtml(String email) {
        return "<html><body style='font-family:Arial,sans-serif;background:#f5f5f5;padding:20px;'>"
            + "<div style='max-width:600px;margin:0 auto;background:#0A0A0A;border-radius:8px;overflow:hidden;'>"
            + "<div style='background:#D4A853;padding:24px 32px;text-align:center;'>"
            + "<h1 style='margin:0;color:#0A0A0A;letter-spacing:3px;'>SERIESX EVENTS</h1></div>"
            + "<div style='padding:40px 32px;color:#fff;text-align:center;'>"
            + "<h2 style='color:#D4A853;'>Welcome to the Family!</h2>"
            + "<p style='color:rgba(255,255,255,0.7);'>You're now subscribed. Expect exclusive highlights and special offers.</p>"
            + "<p style='color:rgba(255,255,255,0.35);font-size:12px;margin-top:20px;'>Subscribed as: " + email + "</p>"
            + "</div><div style='background:#111;padding:16px 32px;text-align:center;color:#555;font-size:12px;'>© 2024 SeriesX Events</div>"
            + "</div></body></html>";
    }
}

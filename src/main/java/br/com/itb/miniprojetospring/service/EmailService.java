package br.com.itb.miniprojetospring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailRecuperacao(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("salamagicafieb@gmail.com       ");
        // Inserir email do sala magica na linha acima
        message.setTo(email);
        message.setSubject("Recuperação de Senha - Sala Mágica");
        message.setText("Olá!\n\n" +
                       "Você solicitou a recuperação de senha do sistema Sala Mágica.\n\n" +
                       "Use o código abaixo para redefinir sua senha:\n\n" +
                       "Código: " + token + "\n\n" +
                       "Este código expira em 15 minutos.\n\n" +
                       "Se você não solicitou esta recuperação, ignore este e-mail.\n\n" +
                       "Atenciosamente,\n" +
                       "Equipe Sala Mágica");
        
        mailSender.send(message);
    }
}
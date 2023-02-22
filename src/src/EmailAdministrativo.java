import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailAdministrativo {
    public static void main(String[] args) {
        System.out.println("Bem vindo ao seu email administrativo!");
        Scanner sc = new Scanner(System.in);
        try {
            int escolha;
            System.out.println("Bem vindo!");
            System.out.printf("Para Envio de mensagens 01 \n" +
                    "Para sair 02 \n" +
                    "Escolha:");
            escolha = sc.nextInt();
            switch (escolha) {
                case 1:
                    String login;
                    String password;
                    String tituloMensagem;
                    String email;
                    String mensagem;
                    String mensagemEdit;
                    System.out.println("Digite seu login:");
                    sc.nextLine();
                    login = sc.nextLine();
                    System.out.println("Digite sua senha:");
                    password = sc.nextLine();
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");

                    Session session = Session.getInstance(props, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(login, password);
                        }
                    });

                    System.out.printf("Digite o Titulo da sua mensagem:");
                    sc.nextLine();
                    tituloMensagem = sc.nextLine();
                    System.out.printf("Digite o email para enviar:");
                    email = sc.nextLine();
                    System.out.print("Digite a mensagem:");
                    mensagem = sc.nextLine();
                    System.out.printf("Deseja editar sua mensagem?:");
                    mensagemEdit = sc.nextLine();
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(login));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject(tituloMensagem);
                    message.setText(mensagem);
                    Transport.send(message);
                    System.out.println("E-mail enviado com sucesso!");
                    if(mensagemEdit.equals("Sim") || mensagemEdit.equals("sim") || mensagemEdit.equals("SIM")){
                        System.out.printf("Reescreva por favor:");
                        mensagem = sc.nextLine();
                        System.out.println("Mensagem enviada para:" + email + ", com o titulo:" + tituloMensagem + "com a seguinte mensagem:" + mensagem);
                    }else {
                        if (eValido(email)) {
                            String Continuar;
                            System.out.println("Mensagem enviada para:" + email + ", com o titulo:" + tituloMensagem + "com a seguinte mensagem:" + mensagem);
                            System.out.printf("Deseja enviar outro email?: (Sim ou nao)");
                            Continuar = sc.nextLine();
                            if (Continuar.equals("Sim")) {
                                System.out.println("Digite o Titulo da sua mensagem:");
                                tituloMensagem = sc.nextLine();
                                System.out.println("Digite o email para enviar:");
                                email = sc.nextLine();
                                System.out.print("Digite a mensagem:");
                                mensagem = sc.nextLine();
                                sc.close();
                            } else {
                                System.out.println("Finalizando o programa");
                                break;
                            }
                        } else {
                            System.out.println("Email invalido! Finalizando...");
                        }
                    }
                    break;
            }
        } catch(RuntimeException e) {
            System.out.println(e);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static boolean eValido(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }}

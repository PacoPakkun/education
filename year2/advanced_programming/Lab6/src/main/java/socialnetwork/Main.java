package socialnetwork;

//import com.sun.org.apache.xpath.internal.operations.String;
//import com.sun.org.apache.xpath.internal.operations.String;
import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.MessageValidator;
import socialnetwork.domain.validators.PrietenieValidator;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.Repository0;
import socialnetwork.repository.database.MessageDbRepository;
import socialnetwork.repository.database.PrietenieDbRepository;
import socialnetwork.repository.database.RequestDbRepository;
import socialnetwork.repository.database.UtilizatorDbRepository;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.repository.file.UtilizatorFile0;
import socialnetwork.service.UtilizatorService;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        MainApp.main(args);

        /*final String url = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        final String username = ApplicationContext.getPROPERTIES().getProperty("databse.socialnetwork.username");
        final String pasword = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.pasword");
        UtilizatorDbRepository userRepository = new UtilizatorDbRepository(url, username, pasword, new UtilizatorValidator());
        PrietenieDbRepository friendshipFileRepository = new PrietenieDbRepository(url, username, pasword, new PrietenieValidator());
        MessageDbRepository msgFileRepository = new MessageDbRepository(url, username, pasword, new MessageValidator());
        RequestDbRepository requestFileRepository = new RequestDbRepository(url, username, pasword, new PrietenieValidator());

        UtilizatorService srv = new UtilizatorService(userRepository, friendshipFileRepository, msgFileRepository, requestFileRepository);
        while (true) {
            System.out.println("insert username");
            Scanner scanner = new Scanner(System.in);
            String usr = scanner.nextLine();
            System.out.println("insert password");
            String pass = scanner.nextLine();
            if (usr.equals("admin") && pass.equals("admin")) {
                System.out.println("logged in as admin");
                System.out.println("insert command add/del/show/find/nrcom/bestcom/logout/exit");
                adminloop:
                while (true) {
                    try {
                        String input = scanner.nextLine();
                        switch (input) {
                            case "add":
                                System.out.println("select user/friend");
                                input = scanner.nextLine();
                                switch (input) {
                                    case "user":
                                        System.out.println("give id/name/name");
                                        input = scanner.nextLine();
                                        List<String> attr = Arrays.asList(input.split(" "));
                                        Utilizator user = new Utilizator(attr.get(1), attr.get(2));
                                        user.setId(Long.parseLong(attr.get(0)));
                                        Optional<Utilizator> o = srv.addUtilizator(user);
                                        if (!o.isPresent())
                                            System.out.println("added");
                                        else
                                            System.out.println("user exists!");
                                        break;
                                    case "friend":
                                        System.out.println("give id/id");
                                        input = scanner.nextLine();
                                        attr = Arrays.asList(input.split(" "));
                                        Optional<Prietenie> op = srv.addPrietenie(new Prietenie(new Tuple(Long.parseLong(attr.get(0)), Long.parseLong(attr.get(1)))));
                                        if (!op.isPresent())
                                            System.out.println("added");
                                        else
                                            System.out.println("friendship exists!");
                                        break;
                                    default:
                                        System.out.println("unknown command");
                                }
                                break;
                            case "del":
                                System.out.println("select user/friend");
                                input = scanner.nextLine();
                                switch (input) {
                                    case "user":
                                        System.out.println("give id");
                                        input = scanner.nextLine();
                                        Optional<Utilizator> o = srv.deleteUtilizator(Long.parseLong(input));
                                        if (o.isPresent())
                                            System.out.println("removed");
                                        else
                                            System.out.println("user doesn't exist!");
                                        break;
                                    case "friend":
                                        System.out.println("give id/id");
                                        input = scanner.nextLine();
                                        List<String> attr = Arrays.asList(input.split(" "));
                                        Optional<Prietenie> op = srv.deletePrietenie(new Tuple(Long.parseLong(attr.get(0)), Long.parseLong(attr.get(1))));
                                        if (op.isPresent())
                                            System.out.println("removed");
                                        else
                                            System.out.println("friendship doesn't exist!");
                                        break;
                                    default:
                                        System.out.println("unknown command");
                                }
                                break;
                            case "find":
                                System.out.println("select user/friends/from");
                                input = scanner.nextLine();
                                switch (input) {
                                    case "user":
                                        System.out.println("give id");
                                        input = scanner.nextLine();
                                        Optional<Utilizator> o = srv.findUtilizator(Long.parseLong(input));
                                        if (o.isPresent()) {
                                            System.out.println(o.get().toString());
                                        } else
                                            System.out.println("user doesn't exist!");
                                        break;
                                    case "friends":
                                        System.out.println("give id");
                                        input = scanner.nextLine();
                                        Optional<Utilizator> op = srv.findUtilizator(Long.parseLong(input));
                                        if (op.isPresent())
                                            srv.findPrieteni(Long.parseLong(input)).forEach(System.out::println);
                                        else
                                            System.out.println("user doesn't exist!");
                                        break;
                                    case "from":
                                        System.out.println("give id");
                                        input = scanner.nextLine();
                                        op = srv.findUtilizator(Long.parseLong(input));
                                        if (op.isPresent()) {
                                            System.out.println("give month");
                                            String month = scanner.nextLine();
                                            System.out.println("give year");
                                            String year = scanner.nextLine();
                                            srv.findPrieteniByDate(op.get().getId(), Integer.parseInt(month), Integer.parseInt(year)).forEach(System.out::println);
                                        } else
                                            System.out.println("user doesn't exist!");
                                        break;
                                    default:
                                        System.out.println("unknown command");
                                }
                                break;
                            case "show":
                                System.out.println("select users/friends");
                                input = scanner.nextLine();
                                switch (input) {
                                    case "users":
                                        srv.getAllUsers().forEach(System.out::println);
                                        break;
                                    case "friends":
                                        srv.getAllFriendships().forEach(System.out::println);
                                        break;
                                }
                                break;
                            case "nrcom":
                                System.out.println(srv.nrComunitati());
                                break;
                            case "bestcom":
                                List<Utilizator> l = srv.comunitateMax();
                                for (Utilizator user : l)
                                    System.out.println(user);
                                break;
                            case "exit":
                                return;
                            case "logout":
                                break adminloop;
                            default:
                                System.out.println("unknown command");
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            } else {
                if (srv.findByName(usr).isPresent()) {
                    System.out.println("logged in as " + usr + " " + srv.findByName(usr).get().getFirstName());
                    System.out.println("insert command friends/find/chat/msg/request/logout/exit");
                    userloop:
                    while (true) {
                        try {
                            String input = scanner.nextLine();
                            switch (input) {
                                case "friends":
                                    srv.findPrieteni(srv.findByName(usr).get().getId()).forEach(System.out::println);
                                    break;
                                case "find":
                                    System.out.println("give month");
                                    String month = scanner.nextLine();
                                    System.out.println("give year");
                                    String year = scanner.nextLine();
                                    srv.findPrieteniByDate(srv.findByName(usr).get().getId(), Integer.parseInt(month), Integer.parseInt(year)).forEach(System.out::println);
                                    break;
                                case "chat":
                                    System.out.println("give id");
                                    input = scanner.nextLine();
                                    srv.getConversation(srv.findByName(usr).get().getId(), Long.parseLong(input)).forEach(System.out::println);
                                    break;
                                case "msg":
                                    System.out.println("give id/message/recipients/reply");
                                    String id = scanner.nextLine();
                                    String msg = scanner.nextLine();
                                    String to = scanner.nextLine();
                                    String reply = scanner.nextLine();
                                    List<String> attr = Arrays.asList(to.split(" "));
                                    List<Utilizator> list = new ArrayList<>();
                                    for (String s : attr) {
                                        Utilizator u = srv.findUtilizator(Long.parseLong(s)).get();
                                        list.add(u);
                                    }
                                    Message m;
                                    if (Long.parseLong(reply) == -1)
                                        m = new Message(Long.parseLong(id), srv.findByName(usr).get(), list, msg);
                                    else
                                        m = new Message(Long.parseLong(id), srv.findByName(usr).get(), list, msg, Long.parseLong(reply));
                                    srv.sendMessage(m);
                                    System.out.println("sent");
                                    break;
                                case "request":
                                    System.out.println("give id");
                                    id = scanner.nextLine();
                                    System.out.println(srv.addRequest(new Request(new Tuple<Long, Long>(srv.findByName(usr).get().getId(), Long.parseLong(id)), "")));
                                    break;
                                case "logout":
                                    break userloop;
                                case "exit":
                                    return;
                                default:
                                    System.out.println("unknown command");
                            }
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                } else {
                    System.out.println("invalid login");
                }
            }
        }
         */
    }
}



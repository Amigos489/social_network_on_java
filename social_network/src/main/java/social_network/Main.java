package social_network;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import social_network.repository.dao.*;
import social_network.repository.entity.*;
import social_network.service.ServiceFacade;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(User.class)
                .addAnnotatedClass(Message.class)
                    .addAnnotatedClass(Chat.class)
                        .addAnnotatedClass(Profile.class)
                            .addAnnotatedClass(Post.class);;

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        User user1 = new User("Костин", "Дмитрий", "Сергеевич", 19, true);
        User user2 = new User("Мраков", "Виталий", "Александрович", 20, true);
        session.persist(user1);
        session.persist(user2);

//        User user1 = session.find(User.class, 1);
//        User user2 = session.find(User.class, 2);


//        Chat chat = session.find(Chat.class, 1);

//        Message message = new Message("Привет!", LocalDate.now(), LocalTime.now(), user2, chat);
//        chat.getMessages().add(message);
//
//        session.persist(message);
//
//        Message message_2 = new Message("Дарова!", LocalDate.now(), LocalTime.now(), user2, chat);
//        chat.getMessages().add(message_2);
//
//        session.persist(message_2);
//
//        Message message_3 = new Message("Как оно?", LocalDate.now(), LocalTime.now(), user1, chat);
//        chat.getMessages().add(message_3);
//
//        session.persist(message_3);

        ProfileDao profileDao = new ProfileDao(session);

        Profile profile1 = profileDao.createNewProfile(user1);
        Profile profile2 = profileDao.createNewProfile(user2);

        PostDao postDao = new PostDao(session);

        Post post1 = postDao.createNewPost("Я в отпуск!");

        List<Post> posts = profileDao.addNewPostInProfile(post1, profile1);

        for (Post post : posts) {
            System.out.println(post.getContent());
        }

        session.getTransaction().commit();
    }
}

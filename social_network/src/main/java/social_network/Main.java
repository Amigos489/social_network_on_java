package social_network;

import social_network.Service.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import social_network.entity.*;
import social_network.repository.*;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Profile.class)
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(FriendInvitation.class)
                .addAnnotatedClass(Message.class)
                .addAnnotatedClass(PersonalChat.class)
                .addAnnotatedClass(GroupChat.class)
                .addAnnotatedClass(Community.class)
                .addAnnotatedClass(CreatorCommunity.class)
                .addAnnotatedClass(CreatorGroupChat.class)
                .addAnnotatedClass(AuthorPostInCommunity.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        UserRepository userRepository = new UserRepository(session);

        UserService userService = new UserService(userRepository);

        ProfileRepository profileRepository = new ProfileRepository(session);

        ProfileService profileService = new ProfileService(profileRepository);

        MessageService messageService = new MessageService(new MessageRepository(session));

        ChatService chatService = new ChatService(new PersonalChatRepository(session), new GroupChatRepository(session), new CreatorGroupChatRepository(session));

        FriendInvitationService friendInvitationService = new FriendInvitationService(new FriendInvitationRepository(session));

        PostRepository postRepository = new PostRepository(session);

        PostService postService = new PostService(postRepository);

        CommunityService communityService = new CommunityService(new CommunityRepository(session),
                new CreatorCommunityRepository(session),
                new UserPostRepository(session), postRepository);

        ServiceFacade serviceFacade = new ServiceFacade(userService, profileService, messageService, chatService, friendInvitationService, postService, communityService);

        session.beginTransaction();

//        serviceFacade.registerUser("Григорий", "Хайбернетов", "grigoriy", "kolyanpassword");
//
//        serviceFacade.registerUser("Иван", "Ентерпрайсов", "ivan", "vitalikpassword");
//
//        serviceFacade.registerUser("Дмитрий", "Костин", "dimon", "dimonpassword");
//
//        serviceFacade.registerUser("Василий", "Полиморфный", "vasya", "dimonpassword");
//
//        serviceFacade.registerUser("Николай", "Лямбдов", "kolya", "kolyapassword");

        session.getTransaction().commit();
    }
}

package new_social_network;

import new_social_network.Service.*;
import new_social_network.repository.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import new_social_network.entity.*;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Profile.class)
                .addAnnotatedClass(Post.class)
                .addAnnotatedClass(FriendInvitation.class)
                .addAnnotatedClass(UsersFriends.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        UserRepository userRepository = new UserRepository(session);

        PostRepository postRepository = new PostRepository(session);

        ProfileRepository profileRepository = new ProfileRepository(session);

        FriendInvitationRepository friendInvitationRepository = new FriendInvitationRepository(session);

        UsersFriendsRepository usersFriendsRepository = new UsersFriendsRepository(session);

        UserService userService = new UserService(userRepository);

        ProfileService profileService = new ProfileService(profileRepository);

        PostService postService = new PostService(postRepository);

        FriendInvitationService friendInvitationService = new FriendInvitationService(friendInvitationRepository);

        UsersFriendsService usersFriendsService = new UsersFriendsService(usersFriendsRepository);

        ServiceFacade serviceFacade =
                new ServiceFacade(userService, profileService,
                        postService, friendInvitationService,
                        usersFriendsService);

        //serviceFacade.registerUser("Николай", "Наумов", "kolyan", "kolyanpassword");

        //serviceFacade.registerUser("Виталий", "Мраков", "vitalik", "vitalikpassword");

        //serviceFacade.placePostInProfile(1, "Это первый пост в профиле");
        //serviceFacade.placePostInProfile(1, "Это второй пост в профиле");

        //serviceFacade.sendFriendInvitation(2, 2);

        //serviceFacade.acceptFriendInvitationById(1);

//        List<FriendInvitation> friendInvitations = serviceFacade.findFriendInvitationUserById(2);
//
//        for (FriendInvitation friendInvitation : friendInvitations) {
//
//            System.out.println(friendInvitation.getSender().getName() + " хочет добавить вас в друзья " + friendInvitation.getDateSending());
//        }
//
 //        serviceFacade.acceptFriendInvitationById(2);
//
//        List<User> friends = serviceFacade.findFriendsUserById(2);
//
//        for (User user : friends) {
//
//            System.out.println(user.getName() + ' ' + user.getSurname());
//        }

        //serviceFacade.setBirthdayInProfileById(1, LocalDate.of(1999, 12, 4));

        //serviceFacade.sendFriendInvitation(1, 2);

        //serviceFacade.acceptFriendInvitationById(1);

        //serviceFacade.sendFriendInvitation(2, 5);
        //serviceFacade.sendFriendInvitation(2, 6);

        //serviceFacade.acceptFriendInvitationById(2);
        //serviceFacade.declineFriendInvitationById(3);

        //serviceFacade.deleteUserFromFriends(1, 5);

        serviceFacade.acceptFriendInvitationById(1);

        session.getTransaction().commit();
    }
}

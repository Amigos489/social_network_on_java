package social_network;

import social_network.entity.*;
import social_network.enums.Role;

import java.lang.reflect.Field;

public final class TestData {
    private TestData() {}

    public static User user(Integer id) {
        User user = new User("Name" + id, "Surname" + id, "user" + id + "@mail.com", "password", Role.USER);
        setField(user, "id", id);
        return user;
    }

    public static User admin(Integer id) {
        User user = new User("Admin", "Adminov", "admin@mail.com", "password", Role.ADMIN);
        setField(user, "id", id);
        return user;
    }

    public static User blockedUser(Integer id) {
        User user = user(id);
        setField(user, "isBlocked", true);
        return user;
    }

    public static Community community(Integer id, User creator) {
        Community community = new Community("Community", "Description", creator);
        setField(community, "id", id);
        return community;
    }

    public static GroupChat groupChat(Integer id, java.util.Set<User> users, User creator) {
        GroupChat chat = new GroupChat(users, creator);
        setField(chat, "id", id);
        return chat;
    }

    public static Profile profile(User user) {
        return new Profile(user);
    }

    public static void setField(Object target, String fieldName, Object value) {
        Class<?> type = target.getClass();
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(target, value);
                return;
            } catch (NoSuchFieldException ignored) {
                type = type.getSuperclass();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalArgumentException("Field not found: " + fieldName);
    }
}

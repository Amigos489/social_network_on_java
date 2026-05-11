package social_network.dto;

import social_network.entity.Post;

import java.time.LocalDate;
import java.util.List;

public class ProfileDto {

    private String name;

    private String surname;

    private LocalDate birthday;

    private String status;

    private List<Post> posts;
}

package social_network.primarykey;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import social_network.entity.Community;
import social_network.entity.User;

import java.io.Serializable;

@Embeddable
public class CreatorCommunityPrimaryKey implements Serializable {

    @ManyToOne
    private User creator;

    @OneToOne
    private Community community;

    public CreatorCommunityPrimaryKey() {
    }

    public CreatorCommunityPrimaryKey(User creator, Community community) {
        this.creator = creator;
        this.community = community;
    }

    public User getCreator() {
        return creator;
    }

    public Community getCommunity() {
        return community;
    }
}

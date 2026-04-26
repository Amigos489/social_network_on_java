package social_network.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import social_network.primarykey.CreatorCommunityPrimaryKey;

@Entity
@Table(name = "creator_community")
public class CreatorCommunity {

    @EmbeddedId
    private CreatorCommunityPrimaryKey primaryKey;

    public CreatorCommunity() {
    }

    public CreatorCommunity(CreatorCommunityPrimaryKey primaryKey) {
        this.primaryKey = primaryKey;
    }

    public CreatorCommunityPrimaryKey getPrimaryKey() {
        return primaryKey;
    }
}

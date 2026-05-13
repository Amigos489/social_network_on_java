package social_network.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import social_network.dto.FindProfileCriteriaDto;
import social_network.entity.Post;
import social_network.entity.Profile;
import org.hibernate.Session;
import social_network.enums.Gender;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProfileRepository extends AbstractRepository<Profile, Integer> {

    public ProfileRepository(Session session) {
        super(Profile.class, session);
    }

    public List<Post> getAllPostById(Integer id) {

        Profile profile = findById(id);

        return profile.getPosts();
    }

    public Profile setStatusById(Profile profile, String status) {

        profile.setStatus(status);

        super.update(profile);

        return profile;
    }

    public Profile setAgeById(Profile profile, Integer age) {

        profile.setAge(age);

        super.update(profile);

        return profile;
    }

    public Profile setGenderById(Profile profile, Gender gender) {

        profile.setGender(gender);

        super.update(profile);

        return profile;
    }

    @Override
    public Profile findById(Integer id) {

        String hql = "FROM Profile p LEFT JOIN FETCH p.posts WHERE p.user.id = :id";

        return session.createQuery(hql, Profile.class).setParameter("id", id).getSingleResultOrNull();

    }

    public List<Profile> findByCriteria(FindProfileCriteriaDto findProfileCriteriaDto) {

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Profile> criteriaQuery = builder.createQuery(Profile.class);

        Root<Profile> root = criteriaQuery.from(Profile.class);

        List<Predicate> predicates = new ArrayList<>();

        if (findProfileCriteriaDto.name() != null) {

            predicates.add(builder.like(root.get("user").get("name"), findProfileCriteriaDto.name() + "%"));
        }

        if (findProfileCriteriaDto.surname() != null) {

            predicates.add(builder.like(root.get("user").get("surname"), findProfileCriteriaDto.surname() + "%"));
        }

        if (findProfileCriteriaDto.minAge() != null) {

            predicates.add(builder.gt(root.get("age"), findProfileCriteriaDto.minAge()));
        }

        if (findProfileCriteriaDto.maxAge() != null) {

            predicates.add(builder.lt(root.get("age"), findProfileCriteriaDto.maxAge()));
        }

        if (findProfileCriteriaDto.gender() != null) {

            predicates.add(builder.equal(root.get("gender"), findProfileCriteriaDto.gender()));
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates);
        }

        return session.createQuery(criteriaQuery).list();
    }
}

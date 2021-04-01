package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Utilizator extends Entity<Long> {
    private String firstName;
    private String lastName;
    private List<Utilizator> friends;
    private String hash;
    private String pic;

    public Utilizator(Long id, String firstName, String lastName) {
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        friends = new ArrayList<Utilizator>();
    }

    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        friends = new ArrayList<Utilizator>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Optional<Utilizator> addFriend(Utilizator user) {
        for (Utilizator u : friends)
            if (u == user)
                return Optional.of(u);
        friends.add(user);
        return Optional.empty();
    }

    public Optional<Utilizator> deleteFriend(Long id) {
        for (Utilizator user : friends)
            if (user.getId() == id) {
                friends.remove(user);
                return Optional.of(user);
            }
        return Optional.empty();
    }

    public List<Utilizator> getFriends() {
        return friends;
    }

    @Override
    public String toString() {
        return "(" + super.getId() +
                ") " + firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) &&
                getFriends().equals(that.getFriends());
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}
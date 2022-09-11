package ro.ubb.model;

public class Photo {
    private int id;
    private User user;
    private String source;
    private int votes;

    public Photo() {
        id = -1;
    }

    public Photo(int id, User user, String source, int votes) {
        this.id = id;
        this.user = user;
        this.source = source;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", user=" + user +
                ", source='" + source + '\'' +
                ", votes=" + votes +
                '}';
    }
}

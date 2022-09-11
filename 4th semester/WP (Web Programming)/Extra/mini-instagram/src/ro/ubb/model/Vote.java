package ro.ubb.model;

public class Vote {
    private User user;
    private Photo photo;
    private int rank;

    public Vote() {
    }

    public Vote(User user, Photo photo, int rank) {
        this.user = user;
        this.photo = photo;
        this.rank = rank;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", photo=" + photo +
                ", rank=" + rank +
                '}';
    }
}

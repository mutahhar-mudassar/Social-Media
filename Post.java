import java.util.ArrayList;
import java.util.List;

public class Post {
    private String txt;
    private String imagePath;
    private int likes;
    private List<String> comments;

    public Post() {
        this.txt = "";
        this.imagePath = null;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    // Getters and Setters
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
import java.util.ArrayList;
import java.util.List;

public class BaseUser {
    protected String userId;
    protected String username;
    protected String email;
    protected String password;
    protected String bio;
    protected String name;
    protected String city;

    protected List<BaseUser> friends;
    protected List<BaseUser> suggestedFriends;
    protected List<Post> posts;

    public BaseUser(String userId, String username, String email, String password, String name, String city, String bio) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.city = city;
        this.bio = bio;
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<BaseUser> getFriends() {
        return friends;
    }

    public List<Post> getPosts() {
        return posts;
    }

    // Setters with simple validation
    public void setUserId(String userId) {
        if (isEmpty(userId)) {
            System.out.println("User ID cannot be empty.");
            return;
        }
        this.userId = userId;
    }

    public void setUsername(String username) {
        if (isEmpty(username)) {
            System.out.println("Username cannot be empty.");
            return;
        }
        if (!username.matches("[\\w._]+")) {
            System.out.println("Username can only have letters, numbers, dots, and underscores.");
            return;
        }
        this.username = username;
    }

    public void setEmail(String email) {
        if (isEmpty(email)) {
            System.out.println("Email cannot be empty.");
            return;
        }
        if (!email.matches("\\w+@\\w+\\.\\w{3}")) {
            System.out.println("Invalid email format.");
            return;
        }
        this.email = email;
    }

    public void setPassword(String password) {
        if (isEmpty(password)) {
            System.out.println("Password cannot be empty.");
            return;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&]).{8,20}$")) {
            System.out.println("Password must have a small letter, a capital letter, a number, a special character, and be 8–20 characters long.");
            return;
        }
        this.password = password;
    }

    public void setBio(String bio) {
        if (isEmpty(bio)) {
            System.out.println("Bio cannot be empty.");
            return;
        }
        this.bio = bio;
    }

    public void setName(String name) {
        if (isEmpty(name)) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (!name.matches("[A-Za-z ]+")) {
            System.out.println("Name can only have letters and spaces.");
            return;
        }
        this.name = name;
    }

    public void setCity(String city) {
        if (isEmpty(city)) {
            System.out.println("City cannot be empty.");
            return;
        }
        if (!city.matches("[A-Za-z ]+")) {
            System.out.println("City can only have letters.");
            return;
        }
        this.city = city;
    }

    public void setFriends(List<BaseUser> friends) {
        this.friends = friends;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Behavior methods
    public void addFriend(BaseUser user) {
        if (!friends.contains(user)) {
            friends.add(user);
        }
    }


    public void addPost(Post post) {
        posts.add(post);
    }

    public String getRole() {
        return "User";
    }

    // Helper method to check if a string is empty
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public List<BaseUser> getSuggestedFriends() {
        return suggestedFriends;
    }

    public void setSuggestedFriends(List<BaseUser> suggestedFriends) {
        this.suggestedFriends = suggestedFriends;
    }
}


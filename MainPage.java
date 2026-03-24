import javafx.application.Application;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPage extends Application {
    private List<BaseUser> users = new ArrayList<>();
    private static final String[] SAMPLE_POST_TEXTS = {
            "Feeling great today!",
            "Just posted a new photo!",
            "Hanging out at the beach!",
            "Chasing dreams one step at a time.",
            "Today's goal: Learn something new.",
            "Enjoying a cup of coffee.",
            "Working on a new project.",
            "Traveling to a new city.",
            "Reading a good book.",
            "Watching a movie."
    };

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Starting MainPage...");
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/data.txt"), StandardCharsets.UTF_8))) {
            System.out.println("Reading data.txt...");
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String everything = sb.toString();
            System.out.println("Data read: " + everything.length() + " characters");

            String[] parts = everything.split("&&&");
            String postAndNews = parts.length > 0 ? parts[0] : "";
            String userPart = parts.length > 1 ? parts[1] : "";
            System.out.println("Parts: " + parts.length);

            String[] postNewsSplit = postAndNews.split("\\*\\*\\*");
            String postsSection = postNewsSplit.length > 0 ? postNewsSplit[0] : "";
            String[] postEntries = postsSection.split(",");
            System.out.println("Post entries: " + Arrays.toString(postEntries));

            String[] usersData = userPart.split("##");
            users = readUsers(usersData, postEntries);
            System.out.println("Users loaded: " + users.size());
        } catch (IOException e) {
            System.err.println("Failed to load data file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load data file", e);
        }

        primaryStage.setTitle("Main");
        BaseScene loginScene = new LoginScene();
        SceneDetailData sceneDetailData = new SceneDetailData(null, null, users, primaryStage);
        primaryStage.setScene(loginScene.createScene(primaryStage, sceneDetailData));
        primaryStage.setWidth(800);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    private List<BaseUser> readUsers(String[] usersData, String[] postEntries) {
        List<BaseUser> allUsers = new ArrayList<>();
        for (String userStr : usersData) {
            if (userStr.trim().isEmpty()) continue;
            String[] userData = userStr.split(",");
            if (userData.length < 6) continue;

            String id = userData[0];
            String username = userData[1];
            String email = userData[2];
            String password = userData[3];
            String name = userData[4];
            String city = userData[5];
            String bio = userData.length > 6 ? userData[6] : "";
            String friendsStr = userData.length > 7 ? userData[7] : "none";
            String suggestedFriendsStr = userData.length > 8 ? userData[8] : "none";

            BaseUser user = "admin".equalsIgnoreCase(username)
                    ? new AdminUser(id, username, email, password, name, city, bio)
                    : new RegularUser(id, username, email, password, name, city, bio);

            allUsers.add(user);
        }

        for (String postEntry : postEntries) {
            if (postEntry.trim().isEmpty()) continue;
            String[] postParts = postEntry.split(":", 2);
            if (postParts.length == 2) {
                String userId = postParts[0];
                String postText = postParts[1].trim();
                if (!postText.isEmpty()) {
                    BaseUser user = allUsers.stream()
                            .filter(u -> u.getUserId().equals(userId))
                            .findFirst().orElse(null);
                    if (user != null) {
                        Post post = new Post();
                        post.setTxt(postText);
                        user.getPosts().add(post);
                    }
                }
            } else {
                BaseUser admin = allUsers.stream()
                        .filter(u -> u.getUserId().equals("1"))
                        .findFirst().orElse(null);
                if (admin != null && !postEntry.trim().isEmpty()) {
                    Post post = new Post();
                    post.setTxt(postEntry.trim());
                    admin.getPosts().add(post);
                }
            }
        }

        for (String userStr : usersData) {
            if (userStr.trim().isEmpty()) continue;
            String[] userData = userStr.split(",");
            if (userData.length < 6) continue;

            String id = userData[0];
            String friendsStr = userData.length > 7 ? userData[7] : "none";
            String suggestedFriendsStr = userData.length > 8 ? userData[8] : "none";

            BaseUser user = allUsers.stream()
                    .filter(u -> u.getUserId().equals(id))
                    .findFirst().orElse(null);
            if (user == null) continue;

            List<BaseUser> friends = new ArrayList<>();
            if (!friendsStr.equals("none")) {
                String[] friendIds = friendsStr.split(":");
                for (String friendId : friendIds) {
                    allUsers.stream()
                            .filter(u -> u.getUserId().equals(friendId))
                            .findFirst().ifPresent(friends::add);
                }
            }
            user.setFriends(friends);

            List<BaseUser> suggestedFriends = new ArrayList<>();
            if (!suggestedFriendsStr.equals("none")) {
                String[] suggestedIds = suggestedFriendsStr.split(":");
                for (String suggestedId : suggestedIds) {
                    allUsers.stream()
                            .filter(u -> u.getUserId().equals(suggestedId))
                            .findFirst().ifPresent(suggestedFriends::add);
                }
            }
            user.setSuggestedFriends(suggestedFriends);
        }

        for (BaseUser user : allUsers) {
            while (user.getPosts().size() < 3) {
                String randomText = SAMPLE_POST_TEXTS[(int) (Math.random() * SAMPLE_POST_TEXTS.length)];
                Post newPost = new Post();
                newPost.setTxt(randomText);
                user.addPost(newPost);
            }

            List<BaseUser> potentialFriends = new ArrayList<>(allUsers);
            potentialFriends.remove(user);
            potentialFriends.removeAll(user.getFriends());
            while (user.getFriends().size() < 3 && !potentialFriends.isEmpty()) {
                BaseUser newFriend = potentialFriends.remove((int) (Math.random() * potentialFriends.size()));
                user.addFriend(newFriend);
                newFriend.addFriend(user);
            }

            List<BaseUser> potentialSuggested = new ArrayList<>(allUsers);
            potentialSuggested.remove(user);
            potentialSuggested.removeAll(user.getFriends());
            potentialSuggested.removeAll(user.getSuggestedFriends());
            while (user.getSuggestedFriends().size() < 4 && !potentialSuggested.isEmpty()) {
                BaseUser newSuggested = potentialSuggested.remove((int) (Math.random() * potentialSuggested.size()));
                user.getSuggestedFriends().add(newSuggested);
            }
        }

        return allUsers;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DataPersistence {
    private static final String DATA_FILE_PATH = "C:\\Users\\IT\\IdeaProjects\\Social Media App\\src\\data.txt";

    public static void saveData(List<BaseUser> allUsers) {
        if (allUsers == null || allUsers.isEmpty()) {
            System.err.println("Users list is null or empty. Cannot save data.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE_PATH, StandardCharsets.UTF_8))) {
            // Write posts with userId prefix
            StringBuilder postsBuilder = new StringBuilder();
            System.out.println("Saving posts for " + allUsers.size() + " users...");
            for (BaseUser user : allUsers) {
                for (Post post : user.getPosts()) {
                    if (post.getTxt() != null && !post.getTxt().isEmpty()) {
                        postsBuilder.append(user.getUserId())
                                .append(":")
                                .append(post.getTxt())
                                .append(",");
                        System.out.println("Saved post for user " + user.getUserId() + ": " + post.getTxt());
                    }
                }
            }
            if (postsBuilder.length() > 0) {
                postsBuilder.setLength(postsBuilder.length() - 1); // Remove trailing comma
            }
            writer.write(postsBuilder.toString());
            writer.write("***"); // Separator for news (not implemented)
            writer.write("&&&"); // Separator for users

            // Write users
            System.out.println("Saving user data...");
            for (BaseUser user : allUsers) {
                String friends = user.getFriends().isEmpty() ? "none" : String.join(":", user.getFriends().stream().map(BaseUser::getUserId).toArray(String[]::new));
                String suggested = user.getSuggestedFriends().isEmpty() ? "none" : String.join(":", user.getSuggestedFriends().stream().map(BaseUser::getUserId).toArray(String[]::new));
                String userLine = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s##",
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getName(),
                        user.getCity(),
                        user.getBio(),
                        friends,
                        suggested);
                writer.write(userLine);
                System.out.println("Saved user: " + user.getUsername());
            }
            System.out.println("Data saved successfully to " + DATA_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
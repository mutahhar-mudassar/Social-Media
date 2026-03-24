public class AdminUser extends BaseUser {
    public AdminUser(String userId, String username, String email, String password, String name, String city, String bio) {
        super(userId, username, email, password, name, city, bio);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
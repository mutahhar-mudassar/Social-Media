import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import javafx.scene.Node;

public class ProfileScene extends BaseScene {
    @Override
    public Scene createScene(Stage stage, SceneDetailData detailData) {
        BaseUser user = detailData.getUser();
        if (user == null) {
            System.err.println("Error: Current user is null in ProfileScene");
            return new Scene(new Label("Error: User not found"), 400, 700);
        }

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        // Profile Picture
        ImageView profilePic;
        try {
            Image image = new Image(getClass().getResourceAsStream("/default_profile.png"), 100, 100, true, true);
            profilePic = new ImageView(image);
            profilePic.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");
        } catch (Exception e) {
            profilePic = new ImageView();
            Label placeholder = new Label("No profile picture");
            placeholder.setStyle(temp.isDarkMode ? "-fx-text-fill: white;" : "-fx-text-fill: darkgray;");
            layout.getChildren().add(placeholder);
        }

        // User Details
        Label nameLabel = new Label("Name: " + user.getName());
        Label emailLabel = new Label("Email: " + user.getEmail());
        Label bioLabel = new Label("Bio: " + user.getBio());
        Button editProfileBtn = new Button("Edit Profile");

        // TabPane for Friends, Suggested Friends, and Posts
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Friends Tab
        Tab friendsTab = new Tab("Friends");
        VBox friendsBox = new VBox(5);
        TextField searchFriends = new TextField();
        searchFriends.setPromptText("Search friends...");
        ListView<String> friendsList = new ListView<>();
        for (BaseUser p : user.getFriends()) {
            friendsList.getItems().add(p.getName() + " (" + p.getEmail() + ")");
        }
        List<BaseUser> allUsers = detailData.getUsers() != null ? detailData.getUsers() : new ArrayList<>();
        friendsList.setCellFactory(lv -> new FriendCell(user, friendsList, null, true, allUsers));
        searchFriends.textProperty().addListener((obs, oldVal, newVal) -> {
            friendsList.getItems().clear();
            user.getFriends().stream()
                    .filter(f -> f.getName().toLowerCase().contains(newVal.toLowerCase()) || f.getEmail().toLowerCase().contains(newVal.toLowerCase()))
                    .forEach(f -> friendsList.getItems().add(f.getName() + " (" + f.getEmail() + ")"));
        });
        friendsBox.getChildren().addAll(searchFriends, friendsList);
        friendsTab.setContent(friendsBox);

        // Suggested Friends Tab
        Tab suggestedTab = new Tab("Suggested Friends");
        ListView<String> suggestedfriendsList = new ListView<>();
        for (BaseUser p : user.getSuggestedFriends()) {
            suggestedfriendsList.getItems().add(p.getName() + " (" + p.getEmail() + ")");
        }
        suggestedfriendsList.setCellFactory(lv -> new FriendCell(user, suggestedfriendsList, friendsList, false, allUsers));
        suggestedTab.setContent(suggestedfriendsList);

        // Posts Tab
        Tab postsTab = new Tab("Posts");
        ListView<Post> postsList = new ListView<>();
        postsList.getItems().addAll(user.getPosts());
        postsList.setCellFactory(lv -> new HomeScene().new PostCell(user));
        postsTab.setContent(postsList);

        tabPane.getTabs().addAll(friendsTab, suggestedTab, postsTab);

        // Edit Profile Pane
        VBox editPane = new VBox(10);
        TextField nameField = new TextField(user.getName());
        TextField emailField = new TextField(user.getEmail());
        TextField bioField = new TextField(user.getBio());
        Button saveBtn = new Button("Save");
        editPane.getChildren().addAll(new Label("Edit Profile"), nameField, emailField, bioField, saveBtn);
        editPane.setVisible(false);

        // Back Button
        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> stage.setScene(detailData.getPreviousScene()));

        // Edit Profile Action
        editProfileBtn.setOnAction(e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(300), editPane);
            tt.setFromX(400);
            tt.setToX(0);
            tt.play();
            editPane.setVisible(true);
            editPane.setOpacity(1);
        });

        saveBtn.setOnAction(e -> {
            user.setName(nameField.getText().trim());
            user.setEmail(emailField.getText().trim());
            user.setBio(bioField.getText().trim());
            nameLabel.setText("Name: " + user.getName());
            emailLabel.setText("Email: " + user.getEmail());
            bioLabel.setText("Bio: " + user.getBio());
            DataPersistence.saveData(detailData.getUsers());
            FadeTransition ft = new FadeTransition(Duration.millis(300), editPane);
            ft.setToValue(0);
            ft.setOnFinished(ev -> editPane.setVisible(false));
            ft.play();
        });

        layout.getChildren().addAll(profilePic, nameLabel, emailLabel, bioLabel, editProfileBtn, tabPane, backBtn, editPane);

        // Apply theme
        applyTheme(layout);

        return new Scene(layout, 400, 700);
    }

    private void applyTheme(VBox layout) {
        ThemeUtils.applyProfileTheme(layout, temp.isDarkMode);
        for (Node node : layout.getChildren()) {
            if (node instanceof TabPane) {
                ((TabPane) node).setStyle(temp.isDarkMode ?
                        "-fx-background-color: #3C3C3C; -fx-pref-height: 300;" :
                        "-fx-background-color: white; -fx-pref-height: 300;");
            } else if (node instanceof ListView) {
                ((ListView<?>) node).refresh();
            } else if (node instanceof VBox && !node.isVisible()) {
                ThemeUtils.applyEditPaneTheme((VBox) node, temp.isDarkMode);
            }
        }
    }

    private class FriendCell extends ListCell<String> {
        private final BaseUser currentUser;
        private final ListView<String> users;
        private final ListView<String> removedUserList;
        private final boolean isFromFriends;
        private final List<BaseUser> allUsers;
        private final HBox hbox = new HBox(10);
        private final Label friendLabel = new Label();
        private final Button actionBtn = new Button();

        public FriendCell(BaseUser user, ListView<String> users, ListView<String> removedUserList, boolean isFromFriends, List<BaseUser> allUsers) {
            this.currentUser = user;
            this.users = users;
            this.removedUserList = removedUserList;
            this.isFromFriends = isFromFriends;
            this.allUsers = allUsers != null ? allUsers : new ArrayList<>();
            System.out.println("FriendCell initialized - allUsers size: " + this.allUsers.size());
            actionBtn.setText(isFromFriends ? "Remove" : "Add Friend");
            applyButtonStyle(actionBtn);
            hbox.getChildren().addAll(friendLabel, actionBtn);
            hbox.setStyle("-fx-padding: 5;");

            actionBtn.setOnAction(e -> {
                String value = getItem();
                if (value == null) {
                    System.err.println("Error: FriendCell item is null");
                    return;
                }
                users.getItems().remove(value);
                if (removedUserList != null) removedUserList.getItems().add(value);
                String email = value.substring(value.indexOf("(") + 1, value.indexOf(")"));
                if (allUsers.isEmpty()) {
                    System.err.println("Error: allUsers is empty in FriendCell action");
                    new Alert(Alert.AlertType.ERROR, "Cannot modify friends: User list is empty.").show();
                    return;
                }
                Optional<BaseUser> target = allUsers.stream()
                        .filter(u -> u.getEmail().equals(email))
                        .findFirst();
                if (target.isPresent()) {
                    BaseUser t = target.get();
                    if (isFromFriends) {
                        currentUser.getFriends().remove(t);
                        currentUser.getSuggestedFriends().add(t);
                        new Alert(Alert.AlertType.INFORMATION, "Removed " + t.getName() + " from friends.").show();
                    } else {
                        currentUser.getSuggestedFriends().remove(t);
                        currentUser.getFriends().add(t);
                        new Alert(Alert.AlertType.INFORMATION, "Added " + t.getName() + " as a friend.").show();
                    }
                    DataPersistence.saveData(allUsers);
                    users.refresh();
                    if (removedUserList != null) removedUserList.refresh();
                } else {
                    System.err.println("Error: Target user with email " + email + " not found");
                    new Alert(Alert.AlertType.ERROR, "User with email " + email + " not found.").show();
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                friendLabel.setText(item);
                applyLabelStyle(friendLabel);
                setGraphic(hbox);
            }
        }

        private void applyButtonStyle(Button btn) {
            btn.setStyle(temp.isDarkMode ?
                    "-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5;" :
                    "-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5;");
        }

        private void applyLabelStyle(Label label) {
            label.setStyle(temp.isDarkMode ?
                    "-fx-text-fill: white;" :
                    "-fx-text-fill: darkgray;");
        }
    }
}
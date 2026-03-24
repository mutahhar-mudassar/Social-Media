import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class temp {
    public static final Map<Post, Like> postLikes = new HashMap<>();
    public static boolean isDarkMode = false;

    public static Scene createHomeScene(Stage stage, SceneDetailData detailData) {
        BorderPane root = new BorderPane();
        BaseUser user = detailData.getUser();

        // Top Header
        HBox top = new HBox(15);
        top.setPadding(new Insets(15));
        Label welcome = new Label("Welcome, " + user.getName());
        ToggleButton themeToggle = new ToggleButton("Dark Mode");
        Button profileBtn = new Button("Profile");
        Button logoutBtn = new Button("Logout");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        top.getChildren().addAll(welcome, spacer, themeToggle, profileBtn, logoutBtn);

        profileBtn.setOnAction(e -> {
            detailData.setPreviousScene(stage.getScene());
            stage.setScene(new ProfileScene().createScene(stage, detailData));
        });
        logoutBtn.setOnAction(e -> stage.setScene(detailData.getLoginScene()));

        // Sidebar
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(15));
        Button homeBtn = new Button("Home");
        Button friendsBtn = new Button("Friends");
        sidebar.getChildren().addAll(homeBtn, friendsBtn);

        friendsBtn.setOnAction(e -> {
            detailData.setPreviousScene(stage.getScene());
            stage.setScene(new ProfileScene().createScene(stage, detailData));
        });

        // Center Content
        VBox center = new VBox(15);
        center.setPadding(new Insets(20));
        center.setAlignment(Pos.TOP_CENTER);

        // Post Input with Image Selection
        TextField postInput = new TextField();
        postInput.setPromptText("What's on your mind?");
        Button chooseImage = new Button("Choose Image");
        Label chosenImageLabel = new Label("No image selected");
        Button postBtn = new Button("Post");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        final File[] selectedFile = {null};

        chooseImage.setOnAction(e -> {
            File f = fileChooser.showOpenDialog(stage);
            if (f != null) {
                selectedFile[0] = f;
                chosenImageLabel.setText(f.getName());
            }
        });

        HBox postBox = new HBox(10, postInput, chooseImage, chosenImageLabel, postBtn);
        postBox.setAlignment(Pos.CENTER);

        // Post List
        ListView<Post> postList = new ListView<>();
        postList.getItems().addAll(user.getPosts());

        postBtn.setOnAction(e -> {
            String txt = postInput.getText().trim();
            if (!txt.isEmpty()) {
                Post newPost = new Post();
                newPost.setTxt(txt);
                if (selectedFile[0] != null) {
                    newPost.setImagePath(selectedFile[0].toURI().toString());
                }
                user.addPost(newPost);
                postList.getItems().add(0, newPost);
                postInput.clear();
                selectedFile[0] = null;
                chosenImageLabel.setText("No image selected");
            }
        });

        postList.setCellFactory(listView -> new ListCell<>() {
            private final VBox cellContainer = new VBox(10);
            private final HBox postRow = new HBox(15);
            private final Label postLabel = new Label();
            private final Button likeButton = new Button("Like");
            private final Label likeCountLabel = new Label();
            private final Button expandButton = new Button("More");
            private final VBox hiddenArea = new VBox(10);
            private final ListView<String> commentListView = new ListView<>();
            private final TextField commentInput = new TextField();
            private final Button addCommentBtn = new Button("Comment");
            private boolean isExpanded = false;

            {
                postRow.getChildren().addAll(postLabel, likeButton, likeCountLabel, expandButton);
                HBox.setHgrow(postLabel, Priority.ALWAYS);
                HBox commentBox = new HBox(10, commentInput, addCommentBtn);
                hiddenArea.getChildren().addAll(new Label("Comments:"), commentListView, commentBox);
                hiddenArea.setVisible(false);
                cellContainer.getChildren().addAll(postRow, hiddenArea);

                expandButton.setOnAction(e -> {
                    isExpanded = !isExpanded;
                    hiddenArea.setVisible(isExpanded);
                    expandButton.setText(isExpanded ? "Less" : "More");
                });

                addCommentBtn.setOnAction(e -> {
                    Post post = getItem();
                    if (post != null) {
                        String comment = commentInput.getText().trim();
                        if (!comment.isEmpty()) {
                            post.getComments().add(comment);
                            commentListView.getItems().setAll(post.getComments());
                            commentInput.clear();
                        }
                    }
                });

                likeButton.setOnAction(e -> {
                    Post post = getItem();
                    if (post != null) {
                        Like like = postLikes.computeIfAbsent(post, k -> new Like());
                        like.toggleLike(user.getUserId());
                        updateLikeDisplay(like);
                    }
                });
            }

            @Override
            protected void updateItem(Post post, boolean empty) {
                super.updateItem(post, empty);
                if (empty || post == null) {
                    setGraphic(null);
                } else {
                    cellContainer.getChildren().clear();
                    postLabel.setText(post.getTxt());
                    if (post.getImagePath() != null) {
                        try {
                            ImageView iv = new ImageView(new Image(post.getImagePath(), 100, 0, true, true));
                            cellContainer.getChildren().add(iv);
                        } catch (Exception e) {
                            Label errorLabel = new Label("Image not available");
                            cellContainer.getChildren().add(errorLabel);
                        }
                    }
                    cellContainer.getChildren().addAll(postRow, hiddenArea);
                    commentListView.getItems().setAll(post.getComments());
                    Like like = postLikes.computeIfAbsent(post, k -> new Like());
                    updateLikeDisplay(like);
                    ThemeUtils.applyCellTheme(this, isDarkMode);
                    setGraphic(cellContainer);
                }
            }

            private void updateLikeDisplay(Like like) {
                boolean liked = like.isLikedBy(user.getUserId());
                likeButton.setText(liked ? "Unlike" : "Like");
                int count = like.count();
                likeCountLabel.setText(count + (count == 1 ? " Like" : " Likes"));
            }
        });

        center.getChildren().addAll(postBox, postList);

        // Set regions
        root.setTop(top);
        root.setLeft(sidebar);
        root.setCenter(center);

        // Apply initial theme and handle toggle
        ThemeUtils.applyHomeTheme(root, isDarkMode);
        themeToggle.setOnAction(e -> {
            isDarkMode = !isDarkMode;
            ThemeUtils.applyHomeTheme(root, isDarkMode);
            themeToggle.setText(isDarkMode ? "Light Mode" : "Dark Mode");
            postList.refresh();
        });

        return new Scene(root, 1000, 700);
    }
}
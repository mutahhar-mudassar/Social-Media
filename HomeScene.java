import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;

public class HomeScene extends BaseScene {
    private static final int POSTS_PER_PAGE = 10;
    private int currentPostIndex = 0;

    @Override
    public Scene createScene(Stage stage, SceneDetailData detailData) {
        BaseUser user = detailData.getUser();
        BorderPane root = new BorderPane();
        applyTheme(root);

        // Top Header
        HBox top = new HBox(15);
        top.setPadding(new Insets(15));
        ImageView avatar;
        try {
            avatar = new ImageView(new Image(getClass().getResourceAsStream("/default_avatar.png"), 40, 40, true, true));
        } catch (Exception e) {
            avatar = new ImageView();
        }
        Label welcome = new Label("Welcome, " + user.getName());
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search posts...");
        ToggleButton themeToggle = new ToggleButton("Dark Mode");
        Button profileBtn = new Button("Profile");
        Button logoutBtn = new Button("Logout");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        top.getChildren().addAll(avatar, welcome, searchBar, spacer, themeToggle, profileBtn, logoutBtn);

        profileBtn.setOnAction(e -> {
            detailData.setPreviousScene(stage.getScene());
            stage.setScene(new ProfileScene().createScene(stage, detailData));
        });
        logoutBtn.setOnAction(e -> stage.setScene(detailData.getLoginScene()));

        // Only Posts Tab
        TabPane tabPane = new TabPane();
        Tab postsTab = new Tab("Posts");
        postsTab.setClosable(false);
        tabPane.getTabs().add(postsTab);

        // Posts Tab Content
        VBox postsContent = new VBox(15);
        postsContent.setPadding(new Insets(20));
        postsContent.setAlignment(Pos.TOP_CENTER);

        // Post Input
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
        loadPosts(postList, user.getPosts(), currentPostIndex);
        Button loadMoreBtn = new Button("Load More");
        loadMoreBtn.setOnAction(e -> {
            currentPostIndex += POSTS_PER_PAGE;
            loadPosts(postList, user.getPosts(), currentPostIndex);
        });

        postBtn.setOnAction(e -> {
            String txt = postInput.getText().trim();
            if (!txt.isEmpty()) {
                Post newPost = new Post();
                newPost.setTxt(txt);
                if (selectedFile[0] != null) {
                    newPost.setImagePath(selectedFile[0].toURI().toString());
                }
                newPost.setComments(new ArrayList<>());
                user.addPost(newPost);
                postList.getItems().add(0, newPost);
                postInput.clear();
                selectedFile[0] = null;
                chosenImageLabel.setText("No image selected");
                DataPersistence.saveData(detailData.getUsers());
            }
        });
        

        postList.setCellFactory(listView -> new PostCell(user));

        postsContent.getChildren().addAll(postBox, postList, loadMoreBtn);
        postsTab.setContent(postsContent);

        // Footer
        HBox footer = new HBox();
        footer.setPadding(new Insets(10));
        footer.setAlignment(Pos.CENTER);
        Label footerText = new Label("© 2025 Objecteers Connect");
        footer.getChildren().add(footerText);

        root.setTop(top);
        root.setCenter(tabPane);
        root.setBottom(footer);

        themeToggle.setOnAction(e -> {
            temp.isDarkMode = !temp.isDarkMode;
            applyTheme(root);
            themeToggle.setText(temp.isDarkMode ? "Light Mode" : "Dark Mode");
            postList.refresh();
        });

        return new Scene(root, 1000, 700);
    }

    private void loadPosts(ListView<Post> postList, List<Post> posts, int startIndex) {
        int endIndex = Math.min(startIndex + POSTS_PER_PAGE, posts.size());
        if (startIndex < posts.size()) {
            postList.getItems().addAll(posts.subList(startIndex, endIndex));
        }
    }

    private void applyTheme(BorderPane root) {
        HBox top = (HBox) root.getTop();
        TabPane tabPane = (TabPane) root.getCenter();
        HBox footer = (HBox) root.getBottom();

        if (temp.isDarkMode) {
            root.setStyle("-fx-background-color: #2C2C2C;");
            top.setStyle("-fx-background-color: #1C1C1C; -fx-alignment: center-left;");
            footer.setStyle("-fx-background-color: #1C1C1C;");
            for (Node node : top.getChildren()) {
                if (node instanceof Label) node.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
                else if (node instanceof TextField) node.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-border-color: #555555;");
                else if (node instanceof Button || node instanceof ToggleButton)
                    node.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-background-radius: 5;");
            }
            for (Node node : footer.getChildren()) {
                if (node instanceof Label) node.setStyle("-fx-text-fill: white;");
            }
            if (tabPane != null) {
                tabPane.setStyle("-fx-background-color: #2C2C2C;");
                for (Tab tab : tabPane.getTabs()) {
                    tab.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white;");
                }
            }
        } else {
            root.setStyle("-fx-background-color: #F5F5F5;");
            top.setStyle("-fx-background-color: #4B0082; -fx-alignment: center-left;");
            footer.setStyle("-fx-background-color: #E6E6FA;");
            for (Node node : top.getChildren()) {
                if (node instanceof Label) node.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
                else if (node instanceof TextField) node.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3;");
                else if (node instanceof Button || node instanceof ToggleButton)
                    node.setStyle("-fx-background-color: white; -fx-text-fill: #4B0082; -fx-background-radius: 5;");
            }
            for (Node node : footer.getChildren()) {
                if (node instanceof Label) node.setStyle("-fx-text-fill: #4B0082;");
            }
            if (tabPane != null) {
                tabPane.setStyle("-fx-background-color: #F5F5F5;");
                for (Tab tab : tabPane.getTabs()) {
                    tab.setStyle("-fx-background-color: #E6E6FA; -fx-text-fill: #4B0082;");
                }
            }
        }
    }

    public class PostCell extends ListCell<Post> {
        private final BaseUser user;
        private final VBox content = new VBox(5);
        private final Label textLabel = new Label();
        private final ImageView imageView = new ImageView();
        private final HBox actions = new HBox(10);
        private final Button likeBtn = new Button("Like");
        private final Button commentBtn = new Button("Comment");
        private final TextField commentField = new TextField();
        private final ListView<String> commentsList = new ListView<>();

        public PostCell(BaseUser user) {
            this.user = user;
            content.setPadding(new Insets(10));
            content.setStyle(temp.isDarkMode ?
                    "-fx-background-color: #3C3C3C; -fx-background-radius: 5;" :
                    "-fx-background-color: white; -fx-background-radius: 5; -fx-border-color: #D3D3D3; -fx-border-width: 1;");
            textLabel.setWrapText(true);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            commentField.setPromptText("Write a comment...");
            commentField.setMaxWidth(200);
            commentsList.setPrefHeight(100);
            actions.getChildren().addAll(likeBtn, commentBtn, commentField);
            content.getChildren().addAll(textLabel, imageView, actions, commentsList);

            likeBtn.setOnAction(e -> {
                Post post = getItem();
                if (post != null) {
                    post.setLikes(post.getLikes() + 1);
                    likeBtn.setText("Like (" + post.getLikes() + ")");
                    DataPersistence.saveData(user.getFriends());
                }
            });

            commentBtn.setOnAction(e -> {
                Post post = getItem();
                String comment = commentField.getText().trim();
                if (post != null && !comment.isEmpty()) {
                    post.getComments().add(comment);
                    commentsList.getItems().add(user.getName() + ": " + comment);
                    commentField.clear();
                    DataPersistence.saveData(user.getFriends());
                }
            });
        }

        @Override
        protected void updateItem(Post post, boolean empty) {
            super.updateItem(post, empty);
            if (empty || post == null) {
                setGraphic(null);
            } else {
                textLabel.setText(post.getTxt());
                textLabel.setStyle(temp.isDarkMode ? "-fx-text-fill: white;" : "-fx-text-fill: black;");
                likeBtn.setText("Like (" + post.getLikes() + ")");
                commentsList.getItems().clear();
                commentsList.getItems().addAll(post.getComments().stream()
                        .map(c -> user.getName() + ": " + c)
                        .collect(Collectors.toList()));
                if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
                    try {
                        Image image = new Image(post.getImagePath(), 200, 0, true, true);
                        imageView.setImage(image);
                    } catch (Exception e) {
                        imageView.setImage(null);
                    }
                } else {
                    imageView.setImage(null);
                }
                applyButtonStyle(likeBtn);
                applyButtonStyle(commentBtn);
                setGraphic(content);
            }
        }

        private void applyButtonStyle(Button btn) {
            btn.setStyle(temp.isDarkMode ?
                    "-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5;" :
                    "-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5;");
        }
    }
}

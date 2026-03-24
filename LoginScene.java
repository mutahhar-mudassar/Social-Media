import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class LoginScene extends BaseScene {

    @Override
    public Scene createScene(Stage stage, SceneDetailData detailData) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #3A006F, #7B68EE);");

        BorderPane mainLayout = new BorderPane();
        HBox mainHBox = new HBox(50);
        mainHBox.setPadding(new Insets(20));
        mainHBox.setAlignment(Pos.CENTER);

        VBox leftSection = new VBox(25);
        leftSection.setPadding(new Insets(50));
        leftSection.setAlignment(Pos.CENTER);

        Image iconImage = new Image(getClass().getResourceAsStream("/Social.png"));
        ImageView logo = new ImageView(iconImage);
        logo.setFitWidth(320);
        logo.setFitHeight(320);
        logo.setPreserveRatio(true);
        logo.setStyle("-fx-background-color: #3A006F; -fx-padding: 15; -fx-background-radius: 50%; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 8);");

        ScaleTransition pulse = new ScaleTransition(Duration.millis(2000), logo);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.05);
        pulse.setToY(1.05);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();

        Label title = new Label("Objecteers Connect");
        title.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 3);");
        Label tagline = new Label("Connect, share, and inspire.");
        tagline.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: #D8BFD8; -fx-wrap-text: true; -fx-max-width: 250;");
        leftSection.getChildren().addAll(logo, title, tagline);

        TabPane tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: transparent; -fx-pref-width: 450;");

        Tab loginTab = new Tab("Login");
        VBox loginForm = createLoginForm(stage, detailData, tabPane, root);
        loginForm.setOpacity(0);
        loginTab.setContent(loginForm);
        loginTab.setClosable(false);

        Tab signUpTab = new Tab("Sign Up");
        VBox signUpForm = createSignUpForm(stage, detailData, tabPane, root);
        signUpForm.setOpacity(0);
        ScrollPane signUpScroll = new ScrollPane(signUpForm);
        signUpScroll.setFitToWidth(true);
        signUpScroll.setStyle("-fx-background-color: transparent;");
        signUpTab.setContent(signUpScroll);
        signUpTab.setClosable(false);

        tabPane.getTabs().addAll(loginTab, signUpTab);

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            for (Tab tab : tabPane.getTabs()) {
                tab.setStyle(tab == newTab
                        ? "-fx-background-color: #7B68EE; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-pref-height: 45;"
                        : "-fx-background-color: #B0C4DE; -fx-text-fill: #3A006F; -fx-font-family: 'Arial'; -fx-pref-height: 45;");
            }
            if (newTab != null) {
                Node content = newTab.getContent();
                if (content instanceof ScrollPane) {
                    content = ((ScrollPane) content).getContent();
                }
                FadeTransition ft = new FadeTransition(Duration.millis(300), content);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                TranslateTransition tt = new TranslateTransition(Duration.millis(300), content);
                tt.setFromX(newTab == loginTab ? 50 : -50);
                tt.setToX(0);
                ft.play();
                tt.play();
            }
        });
        loginTab.setStyle("-fx-background-color: #7B68EE; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-pref-height: 45;");
        signUpTab.setStyle("-fx-background-color: #B0C4DE; -fx-text-fill: #3A006F; -fx-font-family: 'Arial'; -fx-pref-height: 45;");

        VBox card = new VBox(tabPane);
        card.setStyle("-fx-background-color: #F8F8FF; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 10); -fx-padding: 25;");
        card.setMaxWidth(500);
        card.setAlignment(Pos.CENTER);

        Label footer = new Label("© 2025 Objecteers Connect | Version 1.0.0");
        footer.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 12px; -fx-text-fill: #D8BFD8; -fx-padding: 10;");

        VBox rightSection = new VBox(20, card, footer);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPadding(new Insets(0, 20, 0, 0));
        VBox.setVgrow(rightSection, Priority.ALWAYS);

        mainHBox.getChildren().addAll(leftSection, rightSection);
        HBox.setHgrow(rightSection, Priority.ALWAYS);

        mainLayout.setCenter(mainHBox);

        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 1000, 700);
        detailData.setLoginScene(scene);

        FadeTransition ft = new FadeTransition(Duration.millis(300), loginForm);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        return scene;
    }

    private VBox createLoginForm(Stage stage, SceneDetailData detailData, TabPane tabPane, StackPane root) {
        VBox form = new VBox(15);
        form.setPadding(new Insets(25));
        form.setAlignment(Pos.CENTER);

        Label welcome = new Label("Welcome Back");
        welcome.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #3A006F;");

        Label subtitle = new Label("Sign in to continue");
        subtitle.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #696969;");

        TextField userField = new TextField();
        userField.setPromptText("Username or Email");
        userField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        userField.setTooltip(new Tooltip("Enter your username or email address."));

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        passField.setTooltip(new Tooltip("Password must be 8-20 characters, include uppercase, lowercase, number, and special character."));

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");
        passwordTextField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        passwordTextField.textProperty().bindBidirectional(passField.textProperty());
        passwordTextField.setVisible(false);

        StackPane passwordStack = new StackPane(passField, passwordTextField);

        CheckBox showPassword = new CheckBox("Show Password");
        showPassword.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #3A006F;");
        showPassword.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                int caretPos = passField.getCaretPosition();
                passField.setVisible(false);
                passwordTextField.setVisible(true);
                passwordTextField.requestFocus();
                passwordTextField.positionCaret(caretPos);
            } else {
                int caretPos = passwordTextField.getCaretPosition();
                passwordTextField.setVisible(false);
                passField.setVisible(true);
                passField.requestFocus();
                passField.positionCaret(caretPos);
            }
        });

        CheckBox rememberMe = new CheckBox("Remember Me");
        rememberMe.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: #3A006F;");

        HBox optionsBox = new HBox(20, showPassword, rememberMe);
        optionsBox.setAlignment(Pos.CENTER_LEFT);

        Button loginBtn = new Button("Log In");
        loginBtn.setStyle("-fx-background-color: #3A006F; -fx-text-fill: white; -fx-background-radius: 8; -fx-pref-width: 320; -fx-font-size: 16px; -fx-padding: 12; -fx-font-family: 'Arial';");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-font-family: 'Arial'; -fx-text-fill: #FF4500; -fx-font-size: 12px;");

        loginBtn.setOnAction(e -> {
            List<String> errors = new ArrayList<>();
            String usernameOrEmail = userField.getText().trim();
            if (usernameOrEmail.isEmpty()) {
                errors.add("Username or Email is required.");
            }
            String password = passField.getText().trim();
            if (password.isEmpty()) {
                errors.add("Password is required.");
            }
            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct the following errors:");
                alert.setContentText(String.join("\n", errors));
                alert.showAndWait();
                return;
            }
            List<BaseUser> users = detailData.getUsers();
            if (users == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error: User list is null.");
                alert.showAndWait();
                System.err.println("Users list is null in LoginScene");
                return;
            }
            BaseUser matched = users.stream()
                    .filter(u -> (u.getUsername().equals(usernameOrEmail) || u.getEmail().equals(usernameOrEmail)) && u.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);
            if (matched == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username/email or password.");
                alert.showAndWait();
            } else {
                detailData.setUser(matched);
                detailData.setPreviousScene(stage.getScene());
                showIntegratedGreeting(root, matched.getName(), () -> {
                    Scene homeScene = new temp().createHomeScene(stage, detailData);
                    stage.setScene(homeScene);
                });
            }
        });

        Hyperlink signUpLink = new Hyperlink("Don't have an account? Sign up");
        signUpLink.setStyle("-fx-font-family: 'Arial'; -fx-text-fill: #3A006F; -fx-font-size: 14px; -fx-border-width: 0;");
        signUpLink.setOnAction(e -> tabPane.getSelectionModel().select(1));

        form.getChildren().addAll(welcome, subtitle, userField, passwordStack, optionsBox, loginBtn, errorLabel, signUpLink);
        return form;
    }

    private VBox createSignUpForm(Stage stage, SceneDetailData detailData, TabPane tabPane, StackPane root) {
        VBox form = new VBox(15);
        form.setPadding(new Insets(25));
        form.setAlignment(Pos.CENTER);

        Label title = new Label("Join Us");
        title.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #3A006F;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        usernameField.setTooltip(new Tooltip("Username can only have letters, numbers, dots, and underscores."));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        emailField.setTooltip(new Tooltip("Enter a valid email address, e.g., user@example.com"));

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        passwordField.setTooltip(new Tooltip("Password must be 8-20 characters, include uppercase, lowercase, number, and special character."));

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");
        nameField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        nameField.setTooltip(new Tooltip("Enter your full name, letters and spaces only."));

        TextField cityField = new TextField();
        cityField.setPromptText("City");
        cityField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        cityField.setTooltip(new Tooltip("Enter your city name, letters only."));

        TextField bioField = new TextField();
        bioField.setPromptText("Bio");
        bioField.setStyle("-fx-background-color: #F0F0F0; -fx-border-color: #B0C4DE; -fx-border-radius: 8; -fx-background-radius: 8; -fx-pref-width: 320; -fx-padding: 12; -fx-font-family: 'Arial';");
        bioField.setTooltip(new Tooltip("Tell us something about yourself."));

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setStyle("-fx-background-color: #3A006F; -fx-text-fill: white; -fx-background-radius: 8; -fx-pref-width: 320; -fx-font-size: 16px; -fx-padding: 12; -fx-font-family: 'Arial';");

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-family: 'Arial'; -fx-text-fill: #FF4500; -fx-font-size: 12px;");

        signUpBtn.setOnAction(e -> {
            List<String> errors = new ArrayList<>();
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                errors.add("Username is required.");
            } else if (!username.matches("[\\w._]+")) {
                errors.add("Username can only have letters, numbers, dots, and underscores.");
            }
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                errors.add("Email is required.");
            } else if (!email.matches("\\w+@\\w+\\.\\w{3}")) {
                errors.add("Invalid email format.");
            }
            String password = passwordField.getText().trim();
            if (password.isEmpty()) {
                errors.add("Password is required.");
            } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&]).{8,20}$")) {
                errors.add("Password must have a small letter, capital letter, number, special character, and be 8–20 characters.");
            }
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                errors.add("Full Name is required.");
            } else if (!name.matches("[A-Za-z ]+")) {
                errors.add("Name can only have letters and spaces.");
            }
            String city = cityField.getText().trim();
            if (city.isEmpty()) {
                errors.add("City is required.");
            } else if (!city.matches("[A-Za-z ]+")) {
                errors.add("City can only have letters.");
            }
            String bio = bioField.getText().trim();
            if (bio.isEmpty()) {
                errors.add("Bio is required.");
            }
            if (!username.isEmpty() && detailData.getUsers().stream().anyMatch(u -> u.getUsername().equals(username))) {
                errors.add("Username already exists.");
            }
            if (!email.isEmpty() && detailData.getUsers().stream().anyMatch(u -> u.getEmail().equals(email))) {
                errors.add("Email already exists.");
            }
            if (!errors.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please correct the following errors:");
                alert.setContentText(String.join("\n", errors));
                alert.showAndWait();
                return;
            }
            int maxId = detailData.getUsers().stream()
                    .mapToInt(u -> Integer.parseInt(u.getUserId()))
                    .max().orElse(0);
            String newId = String.valueOf(maxId + 1);
            RegularUser newUser = new RegularUser(newId, username, email, password, name, city, bio);
            List<BaseUser> allUsers = detailData.getUsers();
            List<BaseUser> potentialFriends = new ArrayList<>(allUsers);
            potentialFriends.remove(newUser);
            for (int i = 0; i < 3 && !potentialFriends.isEmpty(); i++) {
                BaseUser friend = potentialFriends.remove((int) (Math.random() * potentialFriends.size()));
                newUser.addFriend(friend);
            }
            List<BaseUser> suggestedFriends = new ArrayList<>();
            for (int i = 0; i < 4 && !potentialFriends.isEmpty(); i++) {
                BaseUser suggested = potentialFriends.remove((int) (Math.random() * potentialFriends.size()));
                suggestedFriends.add(suggested);
            }
            newUser.setSuggestedFriends(suggestedFriends);
            detailData.getUsers().add(newUser);
            detailData.setUser(newUser);
            DataPersistence.saveData(detailData.getUsers());
            showIntegratedGreeting(root, newUser.getName(), () -> {
                Scene homeScene = new temp().createHomeScene(stage, detailData);
                stage.setScene(homeScene);
            });
        });

        Hyperlink loginLink = new Hyperlink("Already have an account? Log in");
        loginLink.setStyle("-fx-font-family: 'Arial'; -fx-text-fill: #3A006F; -fx-font-size: 14px; -fx-border-width: 0;");
        loginLink.setOnAction(e -> tabPane.getSelectionModel().select(0));

        form.getChildren().addAll(title, usernameField, emailField, passwordField, nameField, cityField, bioField, signUpBtn, messageLabel, loginLink);
        return form;
    }

    private void showIntegratedGreeting(StackPane root, String userName, Runnable onClose) {
        VBox greetingContent = new VBox(10);
        greetingContent.setAlignment(Pos.CENTER);
        greetingContent.setStyle("-fx-background-color: purple;");

        Circle circle1 = new Circle(10, Color.WHITE);
        Circle circle2 = new Circle(10, Color.WHITE);
        Circle circle3 = new Circle(10, Color.WHITE);

        circle1.setTranslateX(-100);
        circle2.setTranslateX(0);
        circle3.setTranslateX(100);

        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(2), circle1);
        tt1.setByY(-50);
        tt1.setCycleCount(TranslateTransition.INDEFINITE);
        tt1.setAutoReverse(true);

        TranslateTransition tt2 = new TranslateTransition(Duration.seconds(2), circle2);
        tt2.setByY(-50);
        tt2.setCycleCount(TranslateTransition.INDEFINITE);
        tt2.setAutoReverse(true);

        TranslateTransition tt3 = new TranslateTransition(Duration.seconds(2), circle3);
        tt3.setByY(-50);
        tt3.setCycleCount(TranslateTransition.INDEFINITE);
        tt3.setAutoReverse(true);

        tt1.play();
        tt2.play();
        tt3.play();

        Label greeting = new Label();
        greeting.setStyle("-fx-font-family: 'Comic Sans MS'; -fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white; -fx-wrap-text: true; -fx-max-width: 800;");
        greeting.setAlignment(Pos.CENTER);

        Label subtitle = new Label("Join the community and start sharing!");
        subtitle.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: white;");
        subtitle.setAlignment(Pos.CENTER);

        Image connectIcon = new Image(getClass().getResourceAsStream("/connect.png"));
        ImageView connectView = new ImageView(connectIcon);
        connectView.setFitWidth(50);
        connectView.setFitHeight(50);

        Image shareIcon = new Image(getClass().getResourceAsStream("/share.png"));
        ImageView shareView = new ImageView(shareIcon);
        shareView.setFitWidth(50);
        shareView.setFitHeight(50);

        Image inspireIcon = new Image(getClass().getResourceAsStream("/inspire.png"));
        ImageView inspireView = new ImageView(inspireIcon);
        inspireView.setFitWidth(50);
        inspireView.setFitHeight(50);

        HBox iconsBox = new HBox(20, connectView, shareView, inspireView);
        iconsBox.setAlignment(Pos.CENTER);

        FadeTransition ftIcon1 = new FadeTransition(Duration.millis(500), connectView);
        ftIcon1.setFromValue(0.0);
        ftIcon1.setToValue(1.0);
        ftIcon1.setDelay(Duration.millis(500));
        ftIcon1.play();

        FadeTransition ftIcon2 = new FadeTransition(Duration.millis(500), shareView);
        ftIcon2.setFromValue(0.0);
        ftIcon2.setToValue(1.0);
        ftIcon2.setDelay(Duration.millis(700));
        ftIcon2.play();

        FadeTransition ftIcon3 = new FadeTransition(Duration.millis(500), inspireView);
        ftIcon3.setFromValue(0.0);
        ftIcon3.setToValue(1.0);
        ftIcon3.setDelay(Duration.millis(900));
        ftIcon3.play();

        greetingContent.getChildren().addAll(circle1, circle2, circle3, greeting, subtitle, iconsBox);
        root.getChildren().add(greetingContent);

        String message = "Welcome back, " + userName + "!";
        Timeline typewriter = new Timeline();
        for (int i = 0; i <= message.length(); i++) {
            final int index = i;
            typewriter.getKeyFrames().add(new KeyFrame(
                    Duration.millis(100 * i),
                    e -> greeting.setText(message.substring(0, index))
            ));
        }

        greetingContent.setOpacity(0);
        greetingContent.setScaleX(0.8);
        greetingContent.setScaleY(0.8);

        FadeTransition ft = new FadeTransition(Duration.millis(500), greetingContent);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ScaleTransition st = new ScaleTransition(Duration.millis(500), greetingContent);
        st.setFromX(0.8);
        st.setFromY(0.8);
        st.setToX(1.0);
        st.setToY(1.0);

        ft.play();
        st.play();
        typewriter.play();

        typewriter.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(ev -> {
                root.getChildren().remove(greetingContent);
                onClose.run();
            });
            pause.play();
        });
    }
}
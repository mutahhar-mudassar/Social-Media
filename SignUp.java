import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUp extends BaseScene {
    @Override
    public Scene createScene(Stage stage, SceneDetailData detailData) {
        VBox form = new VBox(15);
        form.setPadding(new Insets(30));
        form.setAlignment(Pos.CENTER);

        Label title = new Label("Sign Up");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(250);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(250);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setMaxWidth(250);

        TextField cityField = new TextField();
        cityField.setPromptText("City");
        cityField.setMaxWidth(250);

        TextField bioField = new TextField();
        bioField.setPromptText("Bio");
        bioField.setMaxWidth(250);

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setDefaultButton(true);
        signUpBtn.setStyle(
                "-fx-background-color: #3897f0; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-pref-width: 250;"
        );

        signUpBtn.setOnAction(e -> {
            messageLabel.setText("");
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String name = nameField.getText().trim();
            String city = cityField.getText().trim();
            String bio = bioField.getText().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || name.isEmpty() || city.isEmpty() || bio.isEmpty()) {
                messageLabel.setText("All fields are required.");
                return;
            }

            if (!username.matches("[\\w._]+")) {
                messageLabel.setText("Username can only have letters, numbers, dots, and underscores.");
                return;
            }
            if (detailData.getUsers().stream().anyMatch(u -> u.getUsername().equals(username))) {
                messageLabel.setText("Username already exists.");
                return;
            }

            if (!email.matches("\\w+@\\w+\\.\\w{3}")) {
                messageLabel.setText("Invalid email format.");
                return;
            }
            if (detailData.getUsers().stream().anyMatch(u -> u.getEmail().equals(email))) {
                messageLabel.setText("Email already exists.");
                return;
            }

            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&]).{8,20}$")) {
                messageLabel.setText("Password must have a small letter, a capital letter, a number, a special character, and be 8–20 characters long.");
                return;
            }

            if (!name.matches("[A-Za-z ]+")) {
                messageLabel.setText("Name can only have letters and spaces.");
                return;
            }

            if (!city.matches("[A-Za-z ]+")) {
                messageLabel.setText("City can only have letters.");
                return;
            }

            int maxId = detailData.getUsers().stream()
                    .mapToInt(u -> Integer.parseInt(u.getUserId()))
                    .max().orElse(0);
            String newId = String.valueOf(maxId + 1);
            RegularUser newUser = new RegularUser(newId, username, email, password, name, city, bio);
            detailData.getUsers().add(newUser);
            detailData.setUser(newUser);
            DataPersistence.saveData(detailData.getUsers());
            stage.setScene(new HomeScene().createScene(stage, detailData));
        });

        form.getChildren().addAll(
                title, usernameField, emailField, passwordField, nameField,
                cityField, bioField, signUpBtn, messageLabel
        );

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: white;");

        return new Scene(scrollPane, 350, 550);
    }
}
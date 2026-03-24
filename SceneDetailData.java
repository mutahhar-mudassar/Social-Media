import javafx.scene.Scene;
import java.util.List;
import javafx.stage.Stage;

public class SceneDetailData {
    private BaseUser user;
    private Scene previousScene;
    private Scene loginScene;
    private List<BaseUser> users;
    private Stage stage;

    public SceneDetailData(Scene previousScene, Scene loginScene, List<BaseUser> users, Stage stage) {
        this.previousScene = previousScene;
        this.loginScene = loginScene;
        this.users = users;
        this.stage = stage;
    }

    public BaseUser getUser() {
        return user;
    }

    public void setUser(BaseUser user) {
        this.user = user;
    }

    public Scene getPreviousScene() {
        return previousScene;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public Scene getLoginScene() {
        return loginScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public List<BaseUser> getUsers() {
        return users;
    }

    public void setUsers(List<BaseUser> users) {
        this.users = users;
    }

    public Stage getStage() {
        return stage;
    }
}
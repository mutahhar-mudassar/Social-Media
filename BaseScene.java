import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseScene {
    protected boolean isDarkMode = false; // Theme state

    public abstract Scene createScene(Stage stage, SceneDetailData detailData);
}
    import javafx.scene.Node;
    import javafx.scene.control.*;
    import javafx.scene.layout.*;

    public class ThemeUtils {
        public static void applyHomeTheme(BorderPane root, boolean isDarkMode) {
            HBox top = (HBox) root.getTop();
            VBox sidebar = (VBox) root.getLeft();
            VBox center = (VBox) root.getCenter();
            HBox postBox = (HBox) center.getChildren().get(0);
            TextField postInput = (TextField) postBox.getChildren().get(0);
            Button chooseImage = (Button) postBox.getChildren().get(1);
            Label chosenImageLabel = (Label) postBox.getChildren().get(2);
            Button postBtn = (Button) postBox.getChildren().get(3);

            if (isDarkMode) {
                root.setStyle("-fx-background-color: #2C2C2C;");
                top.setStyle("-fx-background-color: #1C1C1C; -fx-alignment: center-left;");
                ((Label) top.getChildren().get(0)).setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
                ((ToggleButton) top.getChildren().get(2)).setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 8;");
                ((Button) top.getChildren().get(3)).setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 8;");
                ((Button) top.getChildren().get(4)).setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 8;");
                sidebar.setStyle("-fx-background-color: #3C3C3C; -fx-pref-width: 200;");
                for (Node btn : sidebar.getChildren()) {
                    ((Button) btn).setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-pref-width: 170; -fx-padding: 10;");
                }
                center.setStyle("-fx-background-color: #2C2C2C;");
                postBox.setStyle("-fx-background-color: #3C3C3C; -fx-border-color: #555555; -fx-border-radius: 10; -fx-padding: 10;");
                postInput.setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-border-color: #555555; -fx-border-radius: 5; -fx-pref-width: 500; -fx-padding: 10;");
                chooseImage.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10;");
                chosenImageLabel.setStyle("-fx-text-fill: white;");
                postBtn.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10;");
            } else {
                root.setStyle("-fx-background-color: #F5F5F5;");
                top.setStyle("-fx-background-color: #4B0082; -fx-alignment: center-left;");
                ((Label) top.getChildren().get(0)).setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
                ((ToggleButton) top.getChildren().get(2)).setStyle("-fx-background-color: white; -fx-text-fill: #4B0082; -fx-background-radius: 5; -fx-padding: 8;");
                ((Button) top.getChildren().get(3)).setStyle("-fx-background-color: white; -fx-text-fill: #4B0082; -fx-background-radius: 5; -fx-padding: 8;");
                ((Button) top.getChildren().get(4)).setStyle("-fx-background-color: white; -fx-text-fill: #4B0082; -fx-background-radius: 5; -fx-padding: 8;");
                sidebar.setStyle("-fx-background-color: #E6E6FA; -fx-pref-width: 200;");
                for (Node btn : sidebar.getChildren()) {
                    ((Button) btn).setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-pref-width: 170; -fx-padding: 10;");
                }
                center.setStyle("-fx-background-color: #F5F5F5;");
                postBox.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-radius: 10; -fx-padding: 10;");
                postInput.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-radius: 5; -fx-pref-width: 500; -fx-padding: 10;");
                chooseImage.setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10;");
                chosenImageLabel.setStyle("-fx-text-fill: black;");
                postBtn.setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10;");
            }
        }

        public static void applyProfileTheme(VBox layout, boolean isDarkMode) {
            if (isDarkMode) {
                layout.setStyle("-fx-background-color: #2C2C2C;");
                for (Node node : layout.getChildren()) {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
                    } else if (node instanceof TextField) {
                        ((TextField) node).setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-border-color: #555555; -fx-border-radius: 5;");
                    } else if (node instanceof Button) {
                        ((Button) node).setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                    } else if (node instanceof ListView) {
                        ((ListView<?>) node).setStyle("-fx-background-color: #3C3C3C; -fx-control-inner-background: #3C3C3C; -fx-border-color: #555555; -fx-pref-height: 100;");
                    }
                }
            } else {
                layout.setStyle("-fx-background-color: white;");
                for (Node node : layout.getChildren()) {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: darkgray;");
                    } else if (node instanceof TextField) {
                        ((TextField) node).setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: lightgray; -fx-border-radius: 5;");
                    } else if (node instanceof Button) {
                        ((Button) node).setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                    } else if (node instanceof ListView) {
                        ((ListView<?>) node).setStyle("-fx-background-color: white; -fx-control-inner-background: white; -fx-border-color: lightgray; -fx-pref-height: 100;");
                    }
                }
            }
        }

        public static void applyEditPaneTheme(VBox editPane, boolean isDarkMode) {
            if (isDarkMode) {
                editPane.setStyle("-fx-background-color: #3C3C3C; -fx-padding: 10;");
                for (Node node : editPane.getChildren()) {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
                    } else if (node instanceof TextField) {
                        ((TextField) node).setStyle("-fx-background-color: #3C3C3C; -fx-text-fill: white; -fx-border-color: #555555; -fx-border-radius: 5;");
                    } else if (node instanceof Button) {
                        ((Button) node).setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                    }
                }
            } else {
                editPane.setStyle("-fx-background-color: white; -fx-padding: 10;");
                for (Node node : editPane.getChildren()) {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-text-fill: darkgray; -fx-font-weight: bold;");
                    } else if (node instanceof TextField) {
                        ((TextField) node).setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: lightgray; -fx-border-radius: 5;");
                    } else if (node instanceof Button) {
                        ((Button) node).setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                    }
                }
            }
        }

        public static void applyCellTheme(ListCell<?> cell, boolean isDarkMode) {
            if (cell.getGraphic() instanceof VBox) {
                VBox container = (VBox) cell.getGraphic();
                for (Node node : container.getChildren()) {
                    if (node instanceof HBox) {
                        HBox row = (HBox) node;
                        if (isDarkMode) {
                            row.setStyle("-fx-background-color: #3C3C3C; -fx-border-color: #555555; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 15;");
                            for (Node child : row.getChildren()) {
                                if (child instanceof Label) {
                                    ((Label) child).setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                                } else if (child instanceof Button) {
                                    ((Button) child).setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                                }
                            }
                        } else {
                            row.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 15;");
                            for (Node child : row.getChildren()) {
                                if (child instanceof Label) {
                                    ((Label) child).setStyle("-fx-text-fill: #333333; -fx-font-size: 14px;");
                                } else if (child instanceof Button) {
                                    ((Button) child).setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                                }
                            }
                        }
                    } else if (node instanceof Label) {
                        ((Label) node).setStyle(isDarkMode ? "-fx-text-fill: white;" : "-fx-text-fill: black;");
                    }
                }
            } else if (cell.getGraphic() instanceof HBox) {
                HBox row = (HBox) cell.getGraphic();
                if (isDarkMode) {
                    row.setStyle("-fx-background-color: #3C3C3C; -fx-border-color: #555555; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 15;");
                    for (Node child : row.getChildren()) {
                        if (child instanceof Label) {
                            ((Label) child).setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
                        } else if (child instanceof Button) {
                            ((Button) child).setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                        }
                    }
                } else {
                    row.setStyle("-fx-background-color: white; -fx-border-color: #D3D3D3; -fx-border-width: 1; -fx-border-radius: 10; -fx-padding: 15;");
                    for (Node child : row.getChildren()) {
                        if (child instanceof Label) {
                            ((Label) child).setStyle("-fx-text-fill: #333333; -fx-font-size: 14px;");
                        } else if (child instanceof Button) {
                            ((Button) child).setStyle("-fx-background-color: #4B0082; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 5 10;");
                        }
                    }
                }
            }
            cell.setStyle(isDarkMode ? "-fx-background-color: #2C2C2C;" : "-fx-background-color: #F5F5F5;");
        }
    }
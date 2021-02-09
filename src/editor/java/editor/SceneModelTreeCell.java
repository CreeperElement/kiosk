package editor;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kiosk.models.SceneModel;

public class SceneModelTreeCell extends TreeCell<SceneModel> {
    private Controller controller;
    private TextField textField;
    private final ContextMenu editMenu = new ContextMenu();
    private final MenuItem rootMenuItem = new MenuItem("Make This Scene the Root");
    private final MenuItem deleteMenuItem = new MenuItem("Delete This Scene");
    Tooltip orphanInfo = new Tooltip("This scene cannot be reached "
            + "by any other scenes");

    /**
     * Creates a new TreeCell used for displaying SceneModels.
     *
     * @param controller the editor's controller
     */
    public SceneModelTreeCell(Controller controller) {
        this.controller = controller;

        // Creating ContextMenu Actions
        rootMenuItem.setOnAction(t -> {
            controller.setRootScene(getItem());
        });
        deleteMenuItem.setOnAction(t -> {
            controller.deleteScene(getItem());
        });

        editMenu.getItems().addAll(rootMenuItem, deleteMenuItem);

        orphanInfo.setHideDelay(new Duration(.5));
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }

        textField.setText(getName());
        setText(getName());
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem().getName());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(SceneModel item, boolean empty) {
        super.updateItem(item, empty);
        setCache(false);

        if (empty || isEmpty()) {
            setText(null);
            setGraphic(null);
            // Remove the ContextMenu
            setContextMenu(null);
            // Remove the ToolTip
            setTooltip(null);
        } else {
            // Determine if scene is the root; if so, disable some options
            // This is more indicative than just not adding the items in the first place
            if (getItem().getId().equals(Controller.sceneGraph.getRootSceneModel().getId())) {
                rootMenuItem.setDisable(true);
                deleteMenuItem.setDisable(true);
            } else {
                // This is used if the root scene is changed; it re-enables the options
                rootMenuItem.setDisable(false);
                deleteMenuItem.setDisable(false);
            }
            // Add the ContextMenu if it's not there
            if (getContextMenu() == null) {
                setContextMenu(editMenu);
            }
            // Add the ToolTip if it's not there already and if it's needed
            if(getTooltip() == null && getItem().getName().contains("⇱")) {
                setTooltip(orphanInfo);
            }

            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getName());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getName());
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getName());
        textField.setOnKeyReleased(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                // todo call Seth's SceneGraph's EvaluateName()
                getItem().setName(textField.getText());
                controller.rebuildToolbar(getItem());
                controller.rebuildSceneGraphTreeView();
                commitEdit(getItem());
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    // We want the SceneModel's actual name, not the toString()
    private String getName() {
        return getItem() == null ? "" : getItem().getName();
    }
}

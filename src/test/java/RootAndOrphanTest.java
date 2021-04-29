import editor.ChildIdentifiers;
import editor.Controller;
import editor.Editor;
import javafx.scene.control.TreeItem;
import kiosk.SceneGraph;
import kiosk.Settings;
import kiosk.models.ButtonModel;
import kiosk.models.LoadedSurveyModel;
import kiosk.models.PromptSceneModel;
import kiosk.models.SceneModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sun.reflect.generics.tree.Tree;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RootAndOrphanTest {

    static Settings oldSettings;

    @BeforeAll
    static void initialize() {
        oldSettings = Settings.readSettings();
        Editor.applySettings(new Settings());
    }

    @AfterAll
    static void restoreSettings() {
        oldSettings.writeSettings();
    }

    Controller createController(SceneModel ...scenes) {
        LoadedSurveyModel surveyModel = new LoadedSurveyModel(Arrays.asList(scenes));
        Controller.sceneGraph = new SceneGraph(surveyModel);
        return new Controller();
    }

    private TreeItem<SceneModel> getRoot(SceneModel ...sceneModels) {
        return createController(sceneModels).buildSceneGraphTreeView();
    }

    private List<TreeItem<SceneModel>> assertChildCountAndGet(int expected, TreeItem<SceneModel> item) {
        assertEquals(expected, item.getChildren().size());
        return item.getChildren();
    }

    private void assertStartsWithSymbol(TreeItem<SceneModel> item, String identifier) {
        assertTrue(item.getValue().getName().startsWith(identifier));
    }

    @Test
    void singleItemIsRoot() {
        TreeItem<SceneModel> hiddenRoot = getRoot(new PromptSceneModel());
        List<TreeItem<SceneModel>> rootSceneModel = assertChildCountAndGet(1, hiddenRoot);
        assertStartsWithSymbol(rootSceneModel.get(0), ChildIdentifiers.ROOT);
    }

    @Test
    void savedRootHasOneSymbol() {
        SceneModel root = new PromptSceneModel();
        root.setName(ChildIdentifiers.ROOT + "Rest Of Name");
        TreeItem<SceneModel> hiddenRoot = getRoot(root);
        List<TreeItem<SceneModel>> rootSceneModel = assertChildCountAndGet(1, hiddenRoot);
        assertStartsWithSymbol(rootSceneModel.get(0), ChildIdentifiers.ROOT);
        assertTrue(rootSceneModel.get(0).getValue().getName().charAt(1) != ChildIdentifiers.ROOT.charAt(0));
    }

    @Test
    void secondElementSetToRoot() {
        PromptSceneModel a = new PromptSceneModel();
        a.setName("A");
        PromptSceneModel b = new PromptSceneModel();
        b.setName("B");
        a.answers = new ButtonModel[] { new ButtonModel("To B", b.getId())};
        Controller controller = createController(a, b);

        TreeItem<SceneModel> scenes = controller.buildSceneGraphTreeView();
        TreeItem<SceneModel> aTi = assertChildCountAndGet(1, scenes).get(0);
        assertStartsWithSymbol(aTi, ChildIdentifiers.ROOT);
        assertEquals("A", aTi.getValue().getName().substring(1));


        // Rebuild with B as the root.
        Controller.sceneGraph.setRootSceneModel(b);
        scenes = controller.buildSceneGraphTreeView();
        aTi = assertChildCountAndGet(1, scenes).get(0);
        assertEquals("A", aTi.getValue().getName());

        TreeItem<SceneModel> bTi = assertChildCountAndGet(1, aTi).get(0);
        assertStartsWithSymbol(bTi, ChildIdentifiers.ROOT);
        assertEquals("B", bTi.getValue().getName().substring(1));
    }

}
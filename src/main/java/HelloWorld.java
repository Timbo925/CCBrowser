import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.frame.Frame;
import com.teamdev.jxbrowser.os.Environment;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static com.teamdev.jxbrowser.engine.EngineOptions.newBuilder;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class HelloWorld extends Application {

    @Override
    public void start(Stage primaryStage) {
        Engine engine = Engine.newInstance(
                newBuilder(HARDWARE_ACCELERATED).build());


        Browser browser = engine.newBrowser();

        BrowserView view = BrowserView.newInstance(browser);
        Scene scene = new Scene(new StackPane(view), 500, 400);

        // Close the engine when stage is about to close.
        primaryStage.setOnCloseRequest(event -> close(engine));
        primaryStage.setTitle("JavaFX - Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();

        Frame frame = browser.frames().get(0);
        if (frame != null) {
            frame.loadHtml("<html><body><h1>Hallo World</h1><select><option>1</option><option>2</option><option>3</option></select></body></html>");
        }
    }

    private void close(Engine engine) {
        if (Environment.isWindows()) {
            new Thread(engine::close).start();
        } else {
            engine.close();
        }
    }
}
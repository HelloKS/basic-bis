package kumoh.basicbis.util;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import kumoh.basicbis.persistence.RouteInfo;

import java.util.ArrayList;
import java.util.List;

public class ImageLoadTask extends Task<Image> {
    String imageURL = "";

    public ImageLoadTask() {}

    public ImageLoadTask(String imageURL) { this.imageURL = imageURL;}

    @Override
    protected Image call() throws Exception {
        Image img = new Image(imageURL);

        return img;
    }

    @Override
    protected void running() {
        super.running();
        System.out.println("RouteTask: Running...");
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        System.out.println("RouteTask: OK");
    }

    @Override
    protected void failed() {
        super.failed();
        System.out.println("RouteTask: FAILED!");
    }
}

package hufs.ces.grimpan.javafx;

import javafx.scene.input.MouseEvent;

public interface ShapeBuilder {
	void performMousePressed(MouseEvent event);
	void performMouseReleased(MouseEvent event);
	void performMouseDragged(MouseEvent event);
	
}

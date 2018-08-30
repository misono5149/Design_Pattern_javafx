/**
 * misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.sb;

import javafx.scene.input.MouseEvent;


public interface ShapeBuilder {

	void performMousePressed(MouseEvent e);
	void performMouseReleased(MouseEvent e);
	void performMouseDragged(MouseEvent e);
}

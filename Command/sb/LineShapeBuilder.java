/**
* misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.sb;

import hufs.ces.grimpan.core.GrimPanController;
import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.ShapeFactory;
import hufs.ces.grimpan.svg.SVGGrimLine;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
/**
 * @author cskim
 *
 */
public class LineShapeBuilder implements ShapeBuilder {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public LineShapeBuilder(GrimPanModel model, ShapeFactory sf){
		this.model = model;
		this.sf = sf;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMousePressed(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setStartMousePosition(p1);
		model.setCurrMousePosition(p1);
		model.setPrevMousePosition(p1);		
		
		model.curDrawShape = new SVGGrimLine((Line)(sf.createMousePointedLine()));
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseReleased(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);

		model.curDrawShape = new SVGGrimLine((Line)(sf.createMousePointedLine()));
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
			model.control.addShapeAction();
		}
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.strategy.ShapeBuilder#performMouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void performMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);

		model.curDrawShape = new SVGGrimLine((Line)(sf.createMousePointedLine()));
	}

}

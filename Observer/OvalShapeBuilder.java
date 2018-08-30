package hufs.ces.grimpan.javafx;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Shape;
import javafx.scene.shape.VLineTo;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OvalShapeBuilder implements ShapeBuilder {
	GrimPanModel model = null;
	DrawPane drawPane;
	public OvalShapeBuilder(GrimPanModel model)
	{
		this.model = model;
	}
	Shape createPaintedShape(Shape shape) {

		if (model.isShapeFill()){
			shape.setFill(model.getShapeFillColor());
		}
		else {
			shape.setFill(Color.TRANSPARENT);
		}

		if (model.isShapeStroke()){
			shape.setStrokeWidth(model.getShapeStrokeWidth());
			shape.setStroke(model.getShapeStrokeColor());
		}
		else {
			shape.setStroke(Color.TRANSPARENT);
		}
		return shape;
	}
	Shape createMousePointedLine() {
		Point2D pstart = model.getStartMousePosition();
		Point2D pend = model.getCurrMousePosition();
		return createPaintedShape(new Line(pstart.getX(), pstart.getY(), pend.getX(), pend.getY()));

	}
	Shape createMousePointedEllipse(){

		Point2D topleft = model.getStartMousePosition();
		Point2D pcurr = model.getCurrMousePosition();

		if (pcurr.distance(topleft) <= Utils.MINPOLYDIST)
			return null;
		double radiusX = (pcurr.getX() - topleft.getX()) / 2;
		double radiusY = (pcurr.getY() - topleft.getY()) / 2;
		double centerX = topleft.getX() + radiusX;
		double centerY = topleft.getY() + radiusY;
		return createPaintedShape(new Ellipse(centerX, centerY, radiusX, radiusY));
	}
	@Override
	public void performMousePressed(MouseEvent event) {

		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setStartMousePosition(p1);
		model.setCurrMousePosition(p1);
		model.setPrevMousePosition(p1);				
		model.curDrawShape = createMousePointedLine();
	} 
	@Override
	public void performMouseReleased(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);
		model.curDrawShape = createMousePointedEllipse();
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
		}
		//model.notifyListeners();
	}
	@Override
	public void performMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);
		model.curDrawShape = createMousePointedEllipse();
		}
}

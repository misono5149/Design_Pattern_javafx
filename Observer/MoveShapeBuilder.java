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

public class MoveShapeBuilder implements ShapeBuilder {
	GrimPanModel model = null;
	DrawPane drawPane;
	public MoveShapeBuilder(GrimPanModel model)
	{
		this.model = model;
	}
	
	@Override
	public void performMousePressed(MouseEvent event) {

		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setStartMousePosition(p1);
		model.setCurrMousePosition(p1);
		model.setPrevMousePosition(p1);				
		getSelectedShape();
	}    
	@Override
	public void performMouseReleased(MouseEvent event) {

		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);
		model.notifyListeners();
		endShapeMove();
	}
	@Override
	public void performMouseDragged(MouseEvent event) {
		Point2D p1 = new Point2D(Math.max(0, event.getX()), Math.max(0, event.getY()));
		model.setPrevMousePosition(model.getCurrMousePosition());
		model.setCurrMousePosition(p1);
		moveShapeByMouse();
	}
	private void getSelectedShape(){
		int selIndex = -1;
		Shape shape = null;
		for (int i=model.shapeList.size()-1; i >= 0; --i){
			shape = model.shapeList.get(i);
			if (shape.contains(model.getStartMousePosition().getX(), model.getStartMousePosition().getY())){
				selIndex = i;
				break;
			}
		}
		if (selIndex != -1){
			Color scolor = (Color)shape.getStroke();
			Color fcolor = (Color)shape.getFill();
			if (shape.getStroke()!=Color.TRANSPARENT){
				shape.setStroke(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 0.5));
			}
			if (shape.getFill()!=Color.TRANSPARENT){
				shape.setFill(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 0.5));
			}
		}
		model.setSelectedShape(selIndex);
	}
	private void moveShapeByMouse(){
		int selIndex = model.getSelectedShape();
		Shape shape = null;
		if (selIndex != -1){
			shape = model.shapeList.get(selIndex);
			double dx = model.getCurrMousePosition().getX() - model.getPrevMousePosition().getX();
			double dy = model.getCurrMousePosition().getY() - model.getPrevMousePosition().getY();

			translateShape(shape, dx, dy);
		}
	}
	private void endShapeMove(){
		int selIndex = model.getSelectedShape();
		Shape shape = null;
		if (selIndex != -1){
			shape = model.shapeList.get(selIndex);
			Color scolor = (Color)shape.getStroke();
			Color fcolor = (Color)shape.getFill();
			if (shape.getStroke()!=Color.TRANSPARENT){
				shape.setStroke(new Color (scolor.getRed(), scolor.getGreen(), scolor.getBlue(), 1));
			}
			if (shape.getFill()!=Color.TRANSPARENT){
				shape.setFill(new Color (fcolor.getRed(), fcolor.getGreen(), fcolor.getBlue(), 1));
			}
			double dx = model.getCurrMousePosition().getX() - model.getPrevMousePosition().getX();
			double dy = model.getCurrMousePosition().getY() - model.getPrevMousePosition().getY();

			translateShape(shape, dx, dy);
		}
	}
	static private void translateShape(Shape shape, double dx, double dy) {

		if (shape instanceof Line) {
			Line line = (Line) shape;
			line.setStartX(line.getStartX()+dx);
			line.setStartY(line.getStartY()+dy);
			line.setEndX(line.getEndX()+dx);
			line.setEndY(line.getEndY()+dy);
		}
		else if (shape instanceof Ellipse) {
			Ellipse ellipse = (Ellipse) shape;
			ellipse.setCenterX(ellipse.getCenterX()+dx);
			ellipse.setCenterY(ellipse.getCenterY()+dy);
		}
		else if (shape instanceof Path) {
			Path path =(Path) shape;
			for (PathElement el:path.getElements()) {
				if (el instanceof MoveTo) {
					MoveTo pel = (MoveTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof LineTo) {
					LineTo pel = (LineTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof ArcTo) {
					ArcTo pel = (ArcTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof HLineTo) {
					HLineTo pel = (HLineTo)el;
					pel.setX(pel.getX() + dx);
				}
				else if (el instanceof VLineTo) {
					VLineTo pel = (VLineTo)el;
					pel.setY(pel.getY() + dy);
				}
				else if (el instanceof CubicCurveTo) {
					CubicCurveTo pel = (CubicCurveTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
					pel.setControlX1(pel.getControlX1() + dx);
					pel.setControlY1(pel.getControlY1() + dy);
					pel.setControlX2(pel.getControlX2() + dx);
					pel.setControlY2(pel.getControlY2() + dy);
				}
				else if (el instanceof QuadCurveTo) {
					QuadCurveTo pel = (QuadCurveTo)el;
					pel.setX(pel.getX() + dx);
					pel.setY(pel.getY() + dy);
					pel.setControlX(pel.getControlX() + dx);
					pel.setControlY(pel.getControlY() + dy);
				}
			}
		}
		else if (shape instanceof Polygon) {
			Polygon pol =(Polygon) shape;
			ObservableList<Double> points = pol.getPoints();
			for (int i=0; i<points.size(); i+=2) {
				points.set(i, points.get(i)+dx);
				points.set(i+1, points.get(i+1)+dy);
			}
		}
		else if (shape instanceof Polyline) {
			Polyline pol =(Polyline) shape;
			ObservableList<Double> points = pol.getPoints();
			for (int i=0; i<points.size(); i+=2) {
				points.set(i, points.get(i)+dx);
				points.set(i+1, points.get(i+1)+dy);
			}
		}
	}
}

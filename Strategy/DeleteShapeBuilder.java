package hufs.ces.grimpan.javafx;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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

public class DeleteShapeBuilder implements ShapeBuilder {
	GrimPanModel model = null;
	DrawPane drawPane;
	public DeleteShapeBuilder(GrimPanModel model)
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
		int dialogButton = JOptionPane.showConfirmDialog(null, "선택한 도형을 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
		if(dialogButton == JOptionPane.YES_OPTION)
			removeShape();
		else;
	}
	@Override
	public void performMouseReleased(MouseEvent event) {
	}
	@Override
	public void performMouseDragged(MouseEvent event) {
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
	private void removeShape()
	{
		int selectedIndex = model.getSelectedShape();
		if(selectedIndex != -1)
			model.shapeList.remove(selectedIndex);
	}
}

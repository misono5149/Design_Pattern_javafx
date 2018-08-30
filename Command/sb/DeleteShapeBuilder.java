package hufs.ces.grimpan.sb;

import javax.swing.JOptionPane;

import hufs.ces.grimpan.command.DelCommand;
import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.ShapeFactory;
import hufs.ces.grimpan.svg.SVGGrimLine;
import hufs.ces.grimpan.svg.SVGGrimShape;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class DeleteShapeBuilder implements ShapeBuilder {

	ShapeFactory sf = null;	
	GrimPanModel model = null;
	
	public DeleteShapeBuilder(GrimPanModel model, ShapeFactory sf){
		this.model = model;
		this.sf = sf;
	}
	@Override
	public void performMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		Point2D p1 = new Point2D(Math.max(0, e.getX()), Math.max(0, e.getY()));
		model.setStartMousePosition(p1);
		model.setCurrMousePosition(p1);
		model.setPrevMousePosition(p1);		
		model.getSelectedShape();
		if(model.getSelectedShapeIndex() >=0) {
			model.control.delShapeAction();
			int dialogButton = JOptionPane.showConfirmDialog(null, "도형을 정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
			if(dialogButton == JOptionPane.YES_OPTION) {
				removeShape();
			}
		else
		{
			JOptionPane.showMessageDialog(null, "올바른 도형을 선택해주세요");
		}
		}
	}
	
	@Override
	public void performMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void performMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	private void removeShape()
	{
		int selectedIndex = model.getSelectedShapeIndex();
		if(selectedIndex != -1)
			model.shapeList.remove(selectedIndex);
	}
}

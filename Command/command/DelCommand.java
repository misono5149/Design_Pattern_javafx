/**
* misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.command;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.ShapeFactory;
import hufs.ces.grimpan.svg.SVGGrimShape;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class DelCommand implements Command {

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#execute()
	 */
	GrimPanModel model = null;
	SVGGrimShape saveDeletedShape = null; //������ ����
	Color fillColor = null;
	Color StrokeColor = null;
	int removedShape = 0; //������ ���� �ε��� ����
	public DelCommand(GrimPanModel model){
		this.model = model; 
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		saveDeletedShape = model.shapeList.get(model.getSelectedShapeIndex());
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#undo()
	 */
	@Override
	public void undo() {
		// TODO Auto-generated method stub
			model.shapeList.add(saveDeletedShape);
	}
}
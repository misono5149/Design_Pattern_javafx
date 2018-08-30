/**
* misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.command;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.core.ShapeFactory;
import hufs.ces.grimpan.svg.SVGGrimShape;
import javafx.geometry.Point2D;


public class MoveCommand implements Command {

	GrimPanModel model = null;
	Point2D movedPos = null;
	SVGGrimShape movedShape = null;
	public MoveCommand(GrimPanModel model, Point2D moved){
		this.model = model;
		this.movedPos = moved;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#execute()
	 */
	@Override
	public void execute() {
		movedShape = model.shapeList.get(model.getSelectedShapeIndex());
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#undo()
	 */
	@Override
	public void undo() {
		int selIndex = model.shapeList.indexOf(movedShape);
		if (selIndex != -1){
			ShapeFactory.translateShape(movedShape.getShape(), -movedPos.getX(), -movedPos.getY());
			//model.shapeList.set(selIndex, movedShape);
		}
		else {
			System.out.println("undo moved GrimShape not found!!");
		}
	}

}

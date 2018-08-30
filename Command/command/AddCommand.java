/**
* misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.command;

import hufs.ces.grimpan.core.GrimPanModel;
import hufs.ces.grimpan.svg.SVGGrimShape;


public class AddCommand implements Command {

	GrimPanModel model = null;
	SVGGrimShape grimShape = null;
	public AddCommand(GrimPanModel model, SVGGrimShape grimShape){
		this.model = model;
		this.grimShape = grimShape;
	}
	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#execute()
	 */
	@Override
	public void execute() {
		if (model.curDrawShape != null){
			model.shapeList.add(grimShape);
			model.curDrawShape = null;
		}
	}

	/* (non-Javadoc)
	 * @see hufs.cse.grimpan.command.Command#undo()
	 */
	@Override
	public void undo() {
		model.shapeList.remove(model.shapeList.size()-1);
	}

}

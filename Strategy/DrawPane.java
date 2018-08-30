package hufs.ces.grimpan.javafx;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DrawPane extends Pane {

	private GrimPanModel model;

	public DrawPane(GrimPanModel model) {
		this.model = model;
	}
	public DrawPane(GrimPanModel model, double width, double height) {
		this.model = model;
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public void clear() {
		this.getChildren().clear();
	}
	public void redraw() {
		clear();
		//System.out.println("Shape Count="+model.shapeList.size());
		for (Shape sh:model.shapeList){
			this.getChildren().add(sh);
		}
		if (model.curDrawShape!=null) {
			this.getChildren().add(model.curDrawShape);
		}
	}
}

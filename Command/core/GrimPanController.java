package hufs.ces.grimpan.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import hufs.ces.grimpan.command.AddCommand;
import hufs.ces.grimpan.command.Command;
import hufs.ces.grimpan.command.DelCommand;
import hufs.ces.grimpan.command.MoveCommand;
import hufs.ces.grimpan.svg.SVGGrimPath;
import hufs.ces.grimpan.svg.SVGGrimShape;
import hufs.ces.grimpan.svg.SVGUtils;
import hufs.ces.grimpan.svg.SaxSVGPathParseHandler;
import javafx.collections.ObservableList;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class GrimPanController {

	public GrimPanModel model = null;
	public GrimPanPaneView view = null;

	public GrimPanController(){		
		this.model = GrimPanModel.getInstance(this);
	}
	void openAction() {

		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.setTitle("Open Saved GrimPan");
		fileChooser1.setInitialDirectory(new File(Utils.DEFAULT_DIR));
		fileChooser1.getExtensionFilters().add(new ExtensionFilter("SVG File (*.svg)", "*.svg", "*.SVG"));
		File selFile = fileChooser1.showOpenDialog(view.parentStage);

		if (selFile == null) return;

		String fileName = selFile.getName();

		model.setSaveFile(selFile);
		readShapeFromSVGSaveFile(selFile);
		view.parentStage.setTitle("GrimPan - "+fileName);
		view.drawPane.redraw();
	}

	void readShapeFromSVGSaveFile(File saveFile) {

		SaxSVGPathParseHandler saxTreeHandler = new SaxSVGPathParseHandler(); 

		try {
			SAXParserFactory saxf = SAXParserFactory.newInstance();
			
			SAXParser saxParser = saxf.newSAXParser();
			saxParser.parse(new InputSource(new FileInputStream(saveFile)), saxTreeHandler);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		view.initDrawPane();
		
		ObservableList<SVGGrimShape> gshapeList = saxTreeHandler.getPathList();
		for (SVGGrimShape gsh:gshapeList) {
			model.shapeList.add(gsh);
		}
	}
	void saveAction() {
		if (model.getSaveFile()==null){
			model.setSaveFile(new File(Utils.DEFAULT_DIR+"noname.svg"));
			view.parentStage.setTitle("GrimPan - "+Utils.DEFAULT_DIR+"noname.svg");
		}
		File selFile = model.getSaveFile();
		saveGrimPanSVGShapes(selFile);	
	}
	
	void saveAsAction() {
		FileChooser fileChooser2 = new FileChooser();
		fileChooser2.setTitle("Save As ...");
		fileChooser2.setInitialDirectory(new File(Utils.DEFAULT_DIR));
		fileChooser2.getExtensionFilters().add(new ExtensionFilter("SVG File (*.svg)", "*.svg", "*.SVG"));
		File selFile = fileChooser2.showSaveDialog(view.parentStage);
		
		model.setSaveFile(selFile);
		view.parentStage.setTitle("GrimPan - "+selFile.getName());

		saveGrimPanSVGShapes(selFile);	

	}

	void saveGrimPanSVGShapes(File saveFile){
		PrintWriter svgout = null;
		try {
			svgout = new PrintWriter(saveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		svgout.println("<?xml version='1.0' encoding='utf-8' standalone='no'?>");
		//svgout.println("<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.0//EN' 'http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd'>");
		svgout.print("<svg xmlns:svg='http://www.w3.org/2000/svg' ");
		svgout.println("     xmlns='http://www.w3.org/2000/svg' ");
		svgout.print("width='"+view.getWidth()+"' ");
		svgout.print("height='"+view.getHeight()+"' ");
		svgout.println("overflow='visible' xml:space='preserve'>");
		for (SVGGrimShape gs:model.shapeList){
			svgout.println("    "+gs.getSVGShapeString());
		}
		svgout.println("</svg>");
		svgout.close();
	}
	public void addShapeAction() {
		Command addCommand = new AddCommand(model, model.curDrawShape);
		model.undoCommandStack.push(addCommand);// save for undo
		addCommand.execute();

	}

	/**
	 * 
	 */
	public void moveShapeAction() {
		Command moveCommand = new MoveCommand(model, model.getMovedPos());
		model.undoCommandStack.push(moveCommand);// save for undo
		moveCommand.execute();
	}
	public void setMoveShapeState() {
		model.setEditState(Utils.EDIT_MOVE);
	}
	public void undoAction() {
		if (model.undoCommandStack.isEmpty())
			return;
		Command comm = model.undoCommandStack.pop();
		comm.undo();
		
		model.notifyListeners();
	}
	public void delShapeAction() {
		Command delCommand = new DelCommand(model);
		model.undoCommandStack.push(delCommand);// save for undo
		delCommand.execute();
	}
	public void setDelShapeState() {
		model.setEditState(Utils.EDIT_DEL);
	}
}

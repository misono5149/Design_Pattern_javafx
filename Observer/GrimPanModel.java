/**
 * misono_hufs_CSE_designPattern
 */
package hufs.ces.grimpan.javafx;

import java.io.File;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import hufs.ces.grimpan.javafx.LineShapeBuilder;
import hufs.ces.grimpan.javafx.MoveShapeBuilder;
import hufs.ces.grimpan.javafx.OvalShapeBuilder;
import hufs.ces.grimpan.javafx.PencilShapeBuilder;
import hufs.ces.grimpan.javafx.PolygonShapeBuilder;
import hufs.ces.grimpan.javafx.RegularShapeBuilder;
import hufs.ces.grimpan.javafx.ShapeBuilder;
//import hufs.ces.grimpan.observerfx.Utils;
//import hufs.ces.grimpan.observerfx.GrimPanModel;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class GrimPanModel implements Observable {
	
	private volatile static GrimPanModel uniqueModelInstance;
	private int editState = Utils.SHAPE_PENCIL;
	public final ShapeBuilder[] SHAPE_BUILDERS = {
			new RegularShapeBuilder(this),
			new OvalShapeBuilder(this),
			new PolygonShapeBuilder(this),
			new LineShapeBuilder(this),
			new PencilShapeBuilder(this),
			new MoveShapeBuilder(this),
			new DeleteShapeBuilder(this),
		};
	public ShapeBuilder sb = null;
	private float shapeStrokeWidth = 10f;
	private Color shapeStrokeColor = Color.BLACK;
	private boolean shapeStroke = true;
	private boolean shapeFill = false;
	private Color shapeFillColor = null;
	
	public ObservableList<Shape> shapeList = null;
	
	private Point2D startMousePosition = null;
	private Point2D currMousePosition = null;
	private Point2D prevMousePosition = null;
	
	public Shape curDrawShape = null;
	public ArrayList<Point2D> polygonPoints = null;
	private int selectedShape = -1;
	
	private double paneWidth = 0;
	private double paneHeight = 0;
	private int count = 0;
	
	private int nPolygon = 3;
	
	private File saveFile = null;

	private ArrayList<InvalidationListener> listenerList = null;
	public static GrimPanModel getInstance() {
		if (uniqueModelInstance == null) {
			synchronized (GrimPanModel.class) {
				if (uniqueModelInstance == null) {
					uniqueModelInstance = new GrimPanModel();
				}
			}
		}
		return uniqueModelInstance;
	}
	public GrimPanModel(){
		this.shapeList = FXCollections.observableArrayList();
		this.shapeStrokeColor = Color.BLACK;
		this.shapeFillColor = Color.TRANSPARENT;
		this.polygonPoints = new ArrayList<Point2D>();
		this.listenerList = new ArrayList<InvalidationListener>();
	}

	public int getEditState() {
		return editState;
	}

	public void setEditState(int editState) {
		this.editState = editState;
		this.sb= SHAPE_BUILDERS[this.getEditState()];
		this.notifyListeners();
	}
	public Point2D getStartMousePosition() {
		return startMousePosition;
	}

	public void setStartMousePosition(Point2D mousePosition) {
		this.startMousePosition = mousePosition;
	}
	public Point2D getPrevMousePosition() {
		return prevMousePosition;
	}

	public void setPrevMousePosition(Point2D mousePosition) {
		this.prevMousePosition = mousePosition;
	}

	public Point2D getCurrMousePosition() {
		return currMousePosition;
	}

	public void setCurrMousePosition(Point2D clickedMousePosition) {
		this.currMousePosition = clickedMousePosition;
	}
	public void readShapeFromSaveFile(File saveFile) {
	/*	this.saveFile = saveFile;
		ObjectInputStream input;
		try {
			input =
				new ObjectInputStream(new FileInputStream(this.saveFile));
			this.shapeList = (ArrayList<Shape>) input.readObject();
			input.close();

		} catch (ClassNotFoundException e) {
			System.err.println("Class not Found");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}*/
	}
	public void saveGrimPanData(File saveFile){
		/*ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(new FileOutputStream(saveFile));
			output.writeObject(this.shapeList);
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}*/
	}
	/**
	 * @return the saveFile
	 */
	public File getSaveFile() {
		return saveFile;
	}

	/**
	 * @param saveFile the saveFile to set
	 */
	public void setSaveFile(File saveFile) {
		this.saveFile = saveFile;
		//mainFrame.setTitle("�׸��� - "+saveFile.getPath());
	}
	/**
	 * @return the nPolygon
	 */
	public int getNPolygon() {
		return nPolygon;
	}

	/**
	 * @param nPolygon the nPolygon to set
	 */
	public void setNPolygon(int nPolygon) {
		this.nPolygon = nPolygon;
	}

	/**
	 * @return the selectedShape
	 */
	public int getSelectedShape() {
		return selectedShape;
	}

	/**
	 * @param selectedShape the selectedShape to set
	 */
	public void setSelectedShape(int selectedShape) {
		this.selectedShape = selectedShape;
	}

	/**
	 * @return the shapeStrokeColor
	 */
	public Color getShapeStrokeColor() {
		return shapeStrokeColor;
	}

	/**
	 * @param shapeStrokeColor the shapeStrokeColor to set
	 */
	public void setShapeStrokeColor(Color shapeStrokeColor) {
		this.shapeStrokeColor = shapeStrokeColor;
	}

	/**
	 * @return the shapeFill
	 */
	public boolean isShapeFill() {
		return shapeFill;
	}

	/**
	 * @param shapeFill the shapeFill to set
	 */
	public void setShapeFill(boolean shapeFill) {
		this.shapeFill = shapeFill;
	}

	/**
	 * @return the shapeFillColor
	 */
	public Color getShapeFillColor() {
		return shapeFillColor;
	}

	/**
	 * @param shapeFillColor the shapeFillColor to set
	 */
	public void setShapeFillColor(Color shapeFillColor) {
		this.shapeFillColor = shapeFillColor;
	}

	/**
	 * @return the shapeStrokeWidth
	 */
	public float getShapeStrokeWidth() {
		return shapeStrokeWidth;
	}

	/**
	 * @param shapeStrokeWidth the shapeStrokeWidth to set
	 */
	public void setShapeStrokeWidth(float shapeStrokeWidth) {
		this.shapeStrokeWidth = shapeStrokeWidth;
	}

	public boolean isShapeStroke() {
		return shapeStroke;
	}

	public void setShapeStroke(boolean shapeStroke) {
		this.shapeStroke = shapeStroke;
	}

	@Override
	public void addListener(InvalidationListener li) {
		this.listenerList.add(li);
	}
	@Override
	public void removeListener(InvalidationListener li) {
		this.listenerList.remove(li);
		
	}
	public void notifyListeners() {
		for (InvalidationListener lis : this.listenerList) {
			lis.invalidated(this);
		}
	}
	public int getCount() {
		return (shapeList.size()-1);
	}
	public void setCount(int count){
		this.count = count;
		this.notifyListeners();
	}
	public double getPaneWidth() {
		return paneWidth;
	}
	public double getPaneHeight() {
		return paneHeight;
	}
	public void setPaneHeight(double paneHeight) {
		this.paneHeight = paneHeight;
		this.notifyListeners();
	}
	public void setPaneWidth(double paneWidth) {
		this.paneWidth = paneWidth;
		this.notifyListeners();
	}
	public String getSizeText() {
		StringBuilder text = new StringBuilder("Size:");
		if (paneWidth==0 || paneHeight==0)
			return text.toString();
		
		text.append(String.valueOf((int)paneWidth));
		text.append('x');
		text.append(String.valueOf((int)paneHeight));

		return text.toString();
	}
	
}

/**
 * misono_hufs_CSE_designPattern
 */
package hufs.ces.grimpan.javafx;
/* Observer Pattern�� �̿��� �׸��� ����� 
1) GrimPanObserverFX ���
2) �̹� ���� �ڽ��� Strategy Pattern �׸����� Ȯ��
3) �Ʒ� Status Label�� ����� ���� �ٷ� �߰� ��.*/
import javax.swing.JColorChooser;

import javax.swing.JOptionPane;

import org.controlsfx.tools.Borders;

import hufs.ces.grimpan.javafx.Utils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import hufs.ces.grimpan.javafx.ShapeBuilder;
//import hufs.ces.grimpan.observerfx.GrimPanModel;
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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableDoubleValue;
public class GrimPanPane extends VBox implements InvalidationListener {

	private GrimPanModel model;
	//private ShapeBuilder sb;
	
	private MenuItem menuNew;
	private MenuItem menuOpen;    
	private MenuItem menuSave;    
	private MenuItem menuSaveAs;    
	private MenuItem menuExit;    
	private RadioMenuItem menuLine;    
	private ToggleGroup shapeGroup;    
	private RadioMenuItem menuPencil;    
	private RadioMenuItem menuPolygon;    
	private RadioMenuItem menuRegular;    
	private RadioMenuItem menuOval;    
	private MenuItem menuMove;    
	private MenuItem menuDelete;    
	private MenuItem menuStrokeWidth;    
	private MenuItem menuStrokeColor;    
	private MenuItem menuFillColor;    
	private CheckMenuItem menuCheckStroke;    
	private CheckMenuItem menuCheckFill;    
	private MenuItem menuAbout;    
	private DrawPane drawPane; 

	private Node borderedPane;
	private FlowPane statusPane;
	private Label shapeLbl;
	private Label sizeLbl;
	private Label messageLbl;
	private Label modeLbl;
	private Label countLbl;
	
	ColorPicker strokeColorPicker = new ColorPicker(Color.BLACK);
	ColorPicker fillColorPicker = new ColorPicker();

	public GrimPanPane() {

		model = GrimPanModel.getInstance();
		model.addListener(this);
	//	sb = this.model.sb; 
		initialize();
	}

	void initialize() { //�޴����� �� �غ� .
		MenuBar menuBar = new MenuBar();
		Menu mFile = new Menu("File");
		Menu mShape = new Menu("Shape");
		Menu mEdit = new Menu("Edit");
		Menu mSetting = new Menu("Setting");
		Menu mHelp = new Menu("Help");

		menuBar.getMenus().addAll(mFile, mShape, mEdit, mSetting, mHelp);

		menuNew = new MenuItem("New");
		menuNew.setOnAction(e->handleMenuNew(e));
		menuOpen = new MenuItem("Open");
		menuSave = new MenuItem("Save");
		menuSaveAs = new MenuItem("Save As ...");
		menuExit = new MenuItem("Exit");
		menuExit.setOnAction(e->handleMenuExit(e));
		mFile.getItems().addAll(menuNew, menuOpen, menuSave, menuSaveAs, new SeparatorMenuItem(), menuExit);

		ToggleGroup shapeGroup = new ToggleGroup();
		menuLine = new RadioMenuItem("Line");
		menuLine.setOnAction(e->handleMenuLine(e));
		menuPencil = new RadioMenuItem("Pencil");
		menuPencil.setOnAction(e->handleMenuPencil(e));
		menuPolygon = new RadioMenuItem("Polygon");
		menuPolygon.setOnAction(e->handleMenuPolygon(e));
		menuRegular = new RadioMenuItem("Regular");
		menuRegular.setOnAction(e->handleMenuRegular(e));
		menuOval = new RadioMenuItem("Oval");
		menuOval.setOnAction(e->handleMenuOval(e));

		menuLine.setToggleGroup(shapeGroup);
		menuPencil.setToggleGroup(shapeGroup);
		menuPolygon.setToggleGroup(shapeGroup);
		menuRegular.setToggleGroup(shapeGroup);
		menuOval.setToggleGroup(shapeGroup);

		menuPencil.setSelected(true);
		mShape.getItems().addAll(menuLine, menuPencil, menuPolygon, menuRegular, menuOval);

		menuMove = new MenuItem("Move");
		menuMove.setOnAction(e->handleMenuMove(e));

		menuDelete = new MenuItem("Delete");
		menuDelete.setOnAction(e->handleMenuDelete(e)); //delete�� �޴��� �߰� ����������, �޴��� �̺�Ʈ�� setting���ش�.
		mEdit.getItems().addAll(menuMove, menuDelete);

		menuStrokeWidth = new MenuItem("Stroke Width");
		menuStrokeWidth.setOnAction(e->handleMenuStrokeWidth(e));

		//menuStrokeColor = new MenuItem("Stroke Color", strokeColorPicker);
		menuStrokeColor = new MenuItem("Stroke Color");
		menuStrokeColor.setOnAction(e->handleMenuStrokeColor(e));

		menuCheckStroke = new CheckMenuItem("Stroke");
		menuCheckStroke.setSelected(true);
		menuCheckStroke.setOnAction(e->handleMenuCheckStroke(e));

		//menuFillColor = new MenuItem("Fill Color", fillColorPicker);   
		menuFillColor = new MenuItem("Fill Color");   
		menuFillColor.setOnAction(e->handleMenuFillColor(e));

		menuCheckFill = new CheckMenuItem("Fill");
		menuCheckFill.setOnAction(e->handleMenuCheckFill(e));

		mSetting.getItems().addAll(menuStrokeWidth, menuStrokeColor, menuFillColor, menuCheckStroke, menuCheckFill);

		menuAbout = new MenuItem("About");
		menuAbout.setOnAction(e->handleMenuAbout(e));
		mHelp.getItems().addAll(menuAbout);

		drawPane = new DrawPane();
		drawPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		drawPane.setOnMousePressed(e->handleMousePressed(e));
		drawPane.setOnMouseReleased(e->handleMouseReleased(e));
		drawPane.setOnMouseDragged(e->handleMouseDragged(e));

		statusPane = new FlowPane();
		statusPane.setHgap(15);
		statusPane.setPadding(new Insets(0));
		statusPane.setPrefHeight(80);
		
		sizeLbl = new Label(model.getSizeText()); //Size:
		statusPane.getChildren().add(sizeLbl);
		drawPane.widthProperty().addListener(obs -> {
			double val = ((ObservableDoubleValue)obs).get();
		    model.setPaneWidth(val);
		    //sizeLbl.setText(model.getSizeText());
		});
		drawPane.heightProperty().addListener((obs, oldVal, newVal) -> {
			double val = ((ObservableDoubleValue)obs).get();
		    model.setPaneHeight(val);
		    //sizeLbl.setText(model.getSizeText());
		});
		
		shapeLbl = new Label("Shape:              ");
		statusPane.getChildren().add(shapeLbl);

		modeLbl = new Label("Mode:               ");
		statusPane.getChildren().add(modeLbl);

		countLbl = new Label("Count:               ");		
		statusPane.getChildren().add(countLbl);
		
		messageLbl = new Label("  ");
		statusPane.getChildren().add(messageLbl);
		
		borderedPane = Borders.wrap(statusPane)
				.etchedBorder().innerPadding(3).outerPadding(2).buildAll();
		this.getChildren().addAll(menuBar, drawPane, borderedPane);
		//System.out.println("DrawPane w="+drawPane.getWidth()+" h="+drawPane.getHeight());

		initDrawPane();

	}
	@Override
	public void invalidated(Observable obs)
	{
		GrimPanModel mo = (GrimPanModel)obs;
		sizeLbl.setText(mo.getSizeText());
		if(mo.getEditState() < 5)
		{
			shapeLbl.setText(String.format("Shape : %s", (Utils.SHAPE_NAME[mo.getEditState()])));
			modeLbl.setText("Mode : �߰�");
		}
		else
		{
			shapeLbl.setText("�������");
			modeLbl.setText(String.format("Mode : %s", (Utils.SHAPE_NAME[mo.getEditState()])));
		}
		countLbl.setText("Count :" + mo.getCount());
	}
	private void handleMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseButton.PRIMARY){
			model.sb.performMouseDragged(e);
		}
		drawPane.redraw();
	}

	private void handleMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton()==MouseButton.PRIMARY) {
			model.sb.performMouseReleased(e);
			model.notifyListeners();
			}
	}

	private void handleMousePressed(MouseEvent e) {
		if (e.getButton()==MouseButton.PRIMARY) {
			model.sb.performMousePressed(e);
			model.notifyListeners();
		}
		drawPane.redraw();
		
	}
	void initDrawPane() {
		model.setEditState(Utils.SHAPE_PENCIL);
		model.shapeList.clear();
		model.curDrawShape = null;
		model.polygonPoints.clear();
		// Add Dummy Shape for Mouse Event occur
		model.shapeList.add(new Circle(2000,2000,3,Color.TRANSPARENT));
		drawPane.redraw();
	}

	// File Menu handler
	void handleMenuNew(ActionEvent event) {
		initDrawPane();
	}    
	void handleMenuOpen(ActionEvent event) {

	}
	void handleMenuSave(ActionEvent event) {

	}
	void handleMenuSaveAs(ActionEvent event) {

	}
	void handleMenuExit(ActionEvent event) {
		System.exit(0);
	}

	// Shape Menu handler
	void handleMenuLine(ActionEvent event) {
		model.setEditState(Utils.SHAPE_LINE);
		model.setCount(model.shapeList.size());
		drawPane.redraw();
	}
	void handleMenuPencil(ActionEvent event) {
		model.setEditState(Utils.SHAPE_PENCIL);
		model.setCount(model.shapeList.size());
		drawPane.redraw();
	}
	void handleMenuOval(ActionEvent event) {
		model.setEditState(Utils.SHAPE_OVAL);
		model.setCount(model.shapeList.size());
		drawPane.redraw();
	}
	void handleMenuRegular(ActionEvent event) {
		model.setEditState(Utils.SHAPE_REGULAR);
		Object[] possibleValues = { 
				"3", "4", "5", "6", "7",
				"8", "9", "10", "11", "12"
		};
		Object selectedValue = JOptionPane.showInputDialog(null,
				"Choose one", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,
				possibleValues, possibleValues[0]);
		model.setNPolygon(Integer.parseInt((String)selectedValue));
		model.setCount(model.shapeList.size());
		drawPane.redraw();
	}

	void handleMenuPolygon(ActionEvent event) {
		model.setEditState(Utils.SHAPE_POLYGON);
		model.setCount(model.shapeList.size());
		drawPane.redraw();
	}    

	// Edit Menu handler
	void handleMenuMove(ActionEvent event) {

		model.setEditState(Utils.EDIT_MOVE);
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
		}
		drawPane.redraw();
	}
	void handleMenuDelete(ActionEvent event) {
		model.setEditState(Utils.EDIT_DEL); // delete ���¸�
		if (model.curDrawShape != null){
			model.shapeList.add(model.curDrawShape);
			model.curDrawShape = null;
		}
		model.setCount(model.shapeList.size());
		drawPane.redraw(); // �ٽ� ��ȭ�� ���·� redraw
	}    

	// Setting Menu handler
	void handleMenuStrokeColor(ActionEvent event) {
		//model.setShapeStrokeColor(strokeColorPicker.getValue());
		//System.out.println("stroke color="+strokeColorPicker.getValue());
		java.awt.Color awtColor = 
				JColorChooser.showDialog(null, "Choose a color", java.awt.Color.BLACK);
		Color jxColor = Color.BLACK;
		if (awtColor!=null){
			jxColor = new Color(awtColor.getRed()/255.0, awtColor.getGreen()/255.0, awtColor.getBlue()/255.0, 1);
		}
		model.setShapeStrokeColor(jxColor);

	}    
	void handleMenuStrokeWidth(ActionEvent event) {

		String inputVal = JOptionPane.showInputDialog("Stroke Width", "1");
		if (inputVal!=null){
			model.setShapeStrokeWidth(Float.parseFloat(inputVal));
		}
		else {
			model.setShapeStrokeWidth(1f);
		}
	}
	void handleMenuCheckStroke(ActionEvent event) {
		CheckMenuItem checkStroke = (CheckMenuItem)event.getSource();
		if (checkStroke.isSelected())
			model.setShapeStroke(true);
		else
			model.setShapeStroke(false);
	}

	void handleMenuFillColor(ActionEvent event) {
		//model.setShapeFillColor(fillColorPicker.getValue());
		java.awt.Color awtColor = 
				JColorChooser.showDialog(null, "Choose a color", java.awt.Color.BLACK);
		Color jxColor = Color.BLACK;
		if (awtColor!=null){
			jxColor = new Color(awtColor.getRed()/255.0, awtColor.getGreen()/255.0, awtColor.getBlue()/255.0, 1);
		}
		model.setShapeFillColor(jxColor);
	}
	void handleMenuCheckFill(ActionEvent event) {
		CheckMenuItem checkFill = (CheckMenuItem)event.getSource();
		if (checkFill.isSelected())
			model.setShapeFill(true);
		else
			model.setShapeFill(false);
	}

	// Help Menu handler
	void handleMenuAbout(ActionEvent event) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About");
		alert.setHeaderText("GrimPan Ver 0.3.2");
		alert.setContentText("Programmed by cskim, ces, hufs.ac.kr");

		alert.showAndWait();
	}
	Ellipse createPaintedEllipse() {
		Ellipse shape = new Ellipse();

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
	Line createPaintedLine() {
		Line shape = new Line();

		shape.setStrokeWidth(model.getShapeStrokeWidth());
		shape.setStroke(model.getShapeStrokeColor());
		return shape;
	}
	Path createPaintedPath() {
		Path shape = new Path();

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

}

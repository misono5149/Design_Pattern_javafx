/**
* misono_hufs_CSE_designPatterns
 */
package hufs.ces.grimpan.core;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;


public class Utils {

	public static final String DEFAULT_DIR = "C:/Temp/";
	
	public static final int MINPOLYDIST = 7;
	
	public static final int SHAPE_REGULAR = 0;
	public static final int SHAPE_OVAL = 1;
	public static final int SHAPE_POLYGON = 2;
	public static final int SHAPE_LINE = 3;
	public static final int SHAPE_PENCIL = 4;
	public static final int EDIT_MOVE = 5;
	public static final int EDIT_DEL = 6;
	static public String getExtension(String fileName) {
		String ext = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			ext = fileName.substring(i+1);
		}
		return ext;
	}
	public static final String[] SHAPE_NAME = {
			"���ٰ���", "Ÿ��", "�ٰ���", "����", "����", "�̵�", "����"
		};
}

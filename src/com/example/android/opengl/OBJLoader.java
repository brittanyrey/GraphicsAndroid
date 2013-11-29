package com.example.android.opengl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OBJLoader {
	private int PolyCount = 0; // The Models Polygon Count
	private ArrayList vData = new ArrayList(); // List of Vertex Coordinates
	private ArrayList vtData = new ArrayList(); // List of Texture Coordinates
	private ArrayList vnData = new ArrayList(); // List of Normal Coordinates
	private ArrayList fv = new ArrayList(); // Face Vertex Indices
	private ArrayList ft = new ArrayList(); // Face Texture Indices
	private ArrayList fn = new ArrayList(); // Face Normal Indices

	public List<ArrayList> LoadOBJModel(String ModelPath) {
		List<ArrayList> combination = new ArrayList<ArrayList>();//vertex, texture, norm
		ArrayList vData = new ArrayList(); // List of Vertex Coordinates
		ArrayList vtData = new ArrayList(); // List of Texture Coordinates
		ArrayList vnData = new ArrayList(); // List of Normal Coordinates
		try {
			// Open a file handle and read the models data
			BufferedReader br = new BufferedReader(new FileReader(ModelPath));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) { // Read Any Descriptor Data in the
											// File
					// System.out.println("Descriptor: "+line); //Uncomment to
					// print out file descriptor data
				} else if (line.equals("")) {
					// Ignore whitespace data
				} else if (line.startsWith("v ")) { // Read in Vertex Data
					vData.add(ProcessData(line));
				} else if (line.startsWith("vt ")) { // Read Texture Coordinates
					vtData.add(ProcessData(line));
				} else if (line.startsWith("vn ")) { // Read Normal Coordinates
					vnData.add(ProcessData(line));
				} else if (line.startsWith("f ")) { // Read Face Data
					ProcessfData(line);
				}
			}
			br.close();
			combination.add(vData);
			combination.add(vtData);
			combination.add(vnData);
			return combination;
		} catch (IOException e) {
			System.out.println("Failed to find or read OBJ: " + ModelPath);
			System.err.println(e);
		}
		return combination;
	}

	private float[] ProcessFloatData(String sdata[]) {
		float data[] = new float[sdata.length - 1];
		for (int loop = 0; loop < data.length; loop++) {
			data[loop] = Float.parseFloat(sdata[loop + 1]);
		}
		return data; // return an array of floats
	}

	private float[] ProcessData(String read) {
		final String s[] = read.split("\\s+");
		return (ProcessFloatData(s)); // returns an array of processed float
										// data
	}

	private void ProcessfData(String fread) {
		PolyCount++;
		String s[] = fread.split("\\s+");
		if (fread.contains("//")) { // Pattern is present if obj has only v and
									// vn in face data
			for (int loop = 1; loop < s.length; loop++) {
				s[loop] = s[loop].replaceAll("//", "/0/"); // insert a zero for
															// missing vt data
			}
		}
		ProcessfIntData(s); // Pass in face data
	}

	private void ProcessfIntData(String sdata[]) {
		int vdata[] = new int[sdata.length - 1];
		int vtdata[] = new int[sdata.length - 1];
		int vndata[] = new int[sdata.length - 1];
		for (int loop = 1; loop < sdata.length; loop++) {
			String s = sdata[loop];
			String[] temp = s.split("/");
			vdata[loop - 1] = Integer.valueOf(temp[0]); // always add vertex
														// indices
			if (temp.length > 1) { // we have v and vt data
				vtdata[loop - 1] = Integer.valueOf(temp[1]); // add in vt
																// indices
			} else {
				vtdata[loop - 1] = 0; // if no vt data is present fill in zeros
			}
			if (temp.length > 2) { // we have v, vt, and vn data
				vndata[loop - 1] = Integer.valueOf(temp[2]); // add in vn
																// indices
			} else {
				vndata[loop - 1] = 0; // if no vn data is present fill in zeros
			}
		}
		fv.add(vdata);
		ft.add(vtdata);
		fn.add(vndata);
	}

}

package com.example.android.opengl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.AssetManager;

public class ResourceLoader {
	Activity stuff;

	ResourceLoader(Activity activity) {
		stuff = activity;
	}

	public Mesh loadMesh(String fileName) {
		BufferedReader br = null;
		AssetManager assetMgr = stuff.getAssets();
		try {
			assetMgr.list("shaders");
			for (int i = 0; i < assetMgr.list("shaders").length; i++) {
				System.out.println(assetMgr.list("shaders")[i]);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			InputStream iS = assetMgr.open(fileName,
					AssetManager.ACCESS_STREAMING);
			String line;
			br = new BufferedReader(new InputStreamReader(iS));
			List<Float> vertices = new ArrayList<Float>();
			List<Float> vertexNormals = new ArrayList<Float>();
			List<Short> faces = new ArrayList<Short>();

			while ((line = br.readLine()) != null) {
				String[] words;
				words = line.split(" ");

				if (words[0].equals("v")) {
					float x = Float.parseFloat(words[1]);
					float y = Float.parseFloat(words[2]);
					float z = Float.parseFloat(words[3]);
					vertices.add(x);
					vertices.add(y);
					vertices.add(z);
				} else if (words[0].equals("f")) {
					List<Short> indices = parseFace(words);
					faces.addAll(indices);
				} else if (words[0].equals("vn")) {
					float x = Float.parseFloat(words[1]);
					float y = Float.parseFloat(words[2]);
					float z = Float.parseFloat(words[3]);
					vertexNormals.add(x);
					vertexNormals.add(y);
					vertexNormals.add(z);
				}

				int i = 0;
				float[] v = new float[vertices.size()];
				for (Float vertex : vertices) {
					v[i++] = vertex;
				}
				i = 0;
				short[] f = new short[faces.size()];
				for (Short face : faces) {
					f[i++] = face;
				}

				// i = 0;
				// float[] vn = new float[vertexNormals.size()];
				// for(Float n : vertexNormals){
				// vn[i++] = n;
				// }
			}
			return new Mesh(vertices, faces);
			// meshes.put(meshName, mesh);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static List<Short> parseFace(String[] words) {
		List<Short> indices = new ArrayList<Short>();
		String[] parts;
		int i = 1;
		for (i = 1; i < 4; i++) {
			parts = words[i].split("/");
			short s = Short.parseShort(parts[0]);
			s--;
			indices.add(s);
		}
		return indices;
	}

	public String readTextFile(String filename) {
		BufferedReader br = null;
		String line = "";
		AssetManager assetMgr = stuff.getAssets();
		try {
			InputStream is = assetMgr.open(filename);
			StringBuilder builder = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(is));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}
}

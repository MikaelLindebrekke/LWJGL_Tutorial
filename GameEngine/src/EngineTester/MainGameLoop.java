package EngineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		//Loads in a model from a OBJ file.
		ModelData data = OBJFileLoader.loadOBJ("tree");
		
		//Converts the data to a RawModel
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		
		//Loads in the texture for a given model
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));
		
		//Adds the texture to a given model
		TexturedModel tree = new TexturedModel(treeModel, treeTexture);
		
		//Condensed version of the above lines of code
		data = OBJFileLoader.loadOBJ("grassModel");
		TexturedModel grass = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("grassTexture")));
		
		//Settings for texture in regards to lighting and transparency
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		data = OBJFileLoader.loadOBJ("fern");
		TexturedModel fern = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("fern")));
		
	// Example section	
//		ModelTexture texture = staticModel.getTexture();
//		texture.setShineDamper(100);
//		texture.setReflectivity(0.5f);
//		
//		Vector3f entityPosition = new Vector3f(0,0,-250);
//		Entity entity = new Entity(staticModel, entityPosition,0,0,0,1);
//		
		Vector3f lightColour = new Vector3f(1,1,1);
		Vector3f lightSource = new Vector3f(3000,2000,3000);
		Light light = new Light(lightSource, lightColour);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i<400; i++) {
			entities.add(new Entity(tree, 
					new Vector3f(random.nextFloat()*600-400, 0, random.nextFloat() * -600),0,0,0,3));
			entities.add(new Entity(grass, 
					new Vector3f(random.nextFloat()*600-400, 0, random.nextFloat() * -600),0,0,0,1));
			entities.add(new Entity(fern, 
					new Vector3f(random.nextFloat()*600-400, 0, random.nextFloat() * -600),0,0,0,0.6f));
		
		}
		List<Terrain> terrains = new ArrayList<Terrain>();
		for (int j = -8; j <= 3; j++) {
			for (int i = -12; i <= 0; i++) {
				terrains.add(new Terrain(j, i,loader,new ModelTexture(loader.loadTexture("grass"))));
			}
		}
	
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
//			entity.increasePosition(0, 0, -0.01f);
			camera.setPitch(20);
			camera.move();
			//Every entity that needs to be rendered has to be put in to this kind of line. 
			// renderer.processEntity(entity);
			for (Terrain terrain : terrains) {
				renderer.processTerrain(terrain);
			}
			
			
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			
			//Game logic
			
			
			//Render
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}

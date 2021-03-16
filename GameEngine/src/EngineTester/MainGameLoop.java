package EngineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import models.RawModel;
import models.TexturedModel;
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
		
	
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("white")));
		
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(100);
		texture.setReflectivity(0.5f);
		
		Vector3f entityPosition = new Vector3f(0,0,-250);
		Entity entity = new Entity(staticModel, entityPosition,0,0,0,1);
		
		Vector3f lightColour = new Vector3f(1,1,1);
		Vector3f lightSource = new Vector3f(3000,2000,3000);
		Light light = new Light(lightSource, lightColour);
		
		Terrain terrain = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
//			entity.increasePosition(0, 0, -0.01f);
			camera.move();
			//Every entity that needs to be rendered has to be put in to this kind of line. 
			// renderer.processEntity(entity);
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
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

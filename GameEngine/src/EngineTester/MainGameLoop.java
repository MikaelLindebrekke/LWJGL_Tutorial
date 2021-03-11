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
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);
	
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("white"));
		
		TexturedModel staticModel = new TexturedModel(model, texture);
		
		Vector3f entityPosition = new Vector3f(0,-5,-30);
		Entity entity = new Entity(staticModel, entityPosition,0,0,0,1);
		
		Vector3f lightColour = new Vector3f(1,1,1);
		Vector3f lightDirection = new Vector3f(20,20,0);
		Light light = new Light(lightDirection, lightColour);
		
		Camera camera = new Camera();
	
		while (!Display.isCloseRequested()) {
			entity.increaseRotation(0, 0, 0);
//			entity.increasePosition(0, 0, -0.01f);
			
			camera.move();
			
			renderer.prepare();
			
			shader.start();
			shader.loadLight(light);
			shader.loadviewMatrix(camera);
			
			//Game logic
			//Render
			renderer.render(entity, shader);
			
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}

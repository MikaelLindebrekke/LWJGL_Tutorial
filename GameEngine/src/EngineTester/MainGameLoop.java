package EngineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

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
		
		data = OBJFileLoader.loadOBJ("fern");
		TexturedModel fern = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("fern")));
		
		data = OBJFileLoader.loadOBJ("grassModel");
		TexturedModel flower = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("flower")));
		
		data = OBJFileLoader.loadOBJ("lowPolyTree");
		TexturedModel bobble = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		//Settings for texture in regards to lighting and transparency
				grass.getTexture().setHasTransparency(true);
				grass.getTexture().setUseFakeLighting(true);
				flower.getTexture().setHasTransparency(true);
				flower.getTexture().setUseFakeLighting(true);
				fern.getTexture().setHasTransparency(true);
				
		
		
		// *********** TERRAINS *************
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		// *********** TERRAINS *************
		
		
		
		// *********** PLAYER *************
		data = OBJFileLoader.loadOBJ("stanfordBunny");
		TexturedModel stanfordBunny = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("white")));
		
		Player player = new Player(stanfordBunny, new Vector3f(100, 0, -50),0,0,0,.5f);
		
		// *********** PLAYER *************
		
	// Example section	
//		ModelTexture texture = staticModel.getTexture();
//		texture.setShineDamper(100);
//		texture.setReflectivity(0.5f);
//		
//		Vector3f entityPosition = new Vector3f(0,0,-250);
//		Entity entity = new Entity(staticModel, entityPosition,0,0,0,1);
//		
		Vector3f lightColour = new Vector3f(1,1,1);
		Vector3f lightSource = new Vector3f(20000,40000,20000);
		Light light = new Light(lightSource, lightColour);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random(676452);
		for (int i = 0; i<400; i++) {
			
			if ( i % 7 == 0) {
				entities.add(new Entity(flower, 
						new Vector3f(random.nextFloat()*400-200, 0, random.nextFloat() * -400),0,0,0,1.8f));
				entities.add(new Entity(grass, 
						new Vector3f(random.nextFloat()*400-200, 0, random.nextFloat() * -400),0,0,0,2.3f));
			}
			if (i % 3 == 0) {
				entities.add(new Entity(tree, 
						new Vector3f(random.nextFloat()*800-400, 0, random.nextFloat() * -600),0,0,0,random.nextFloat() * 1 + 4));
				entities.add(new Entity(fern, 
						new Vector3f(random.nextFloat()*400-200, 0, random.nextFloat() * -400),0,0,0,0.9f));
				entities.add(new Entity(bobble, 
						new Vector3f(random.nextFloat()*800-400, 0, random.nextFloat() * -600),0,0,0,random.nextFloat() * 0.1f + 0.6f));
			}
		
		}
//		List<Terrain> terrains = new ArrayList<Terrain>();
//		for (int j = -8; j <= 3; j++) {
//			for (int i = -12; i <= 0; i++) {
//				terrains.add(new Terrain(j, i,loader,texturePack, blendMap));
//			}
//		}
		
		Terrain terrain = new Terrain(0, -1,loader,texturePack, blendMap); 
		Terrain terrain2 = new Terrain(-1, -1,loader,texturePack, blendMap); 
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while (!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 0.5f, 0);
//			entity.increasePosition(0, 0, -0.01f);
			camera.setPitch(20);
			camera.move();
			//Every entity that needs to be rendered has to be put in to this kind of line. 
			// renderer.processEntity(entity);
//			for (Terrain terrain : terrains) {
//				renderer.processTerrain(terrain);
//			}
			player.move();
			renderer.processEntity(player);
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
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

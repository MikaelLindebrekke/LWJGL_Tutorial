package EngineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
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
import toolbox.MousePicker;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		MasterRenderer renderer = new MasterRenderer(loader);
		
		// *********** EXAMPLE FOR LOADING IN A TEXTURED MODEL *************
		
		//Loads in a model from a OBJ file.
		ModelData data = OBJFileLoader.loadOBJ("tree");
		
		//Converts the data to a RawModel
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		
		//Loads in the texture for a given model
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));
		
		//Adds the texture to a given model
		TexturedModel tree = new TexturedModel(treeModel, treeTexture);
		
		// *********** EXAMPLE FOR LOADING IN A TEXTURED MODEL *************
		
		
		
		
		// *********** ENTITIES *************
		
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
		
		data = OBJFileLoader.loadOBJ("lamp");
		TexturedModel lamp = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("lamp")));
		
		// *********** ENTITIES *************
		
		// *********** FAKE LIGHTING AND TRANSPARENCY *************
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		fern.getTexture().setHasTransparency(true);
		lamp.getTexture().setUseFakeLighting(true);
		
		// *********** LIGHTING AND TRANSPARENCY *************
		
		// *********** SHINE AND DAMP ON MODEL *************
		
//		texture.setShineDamper(100);
//		texture.setReflectivity(0.5f);
		
		// *********** SHINE AND DAMP ON MODEL *************
		
		// *********** TERRAINS *************
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("mossPath256"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, -1,loader,texturePack, blendMap, "heightmap");
		
		// *********** TERRAINS *************
		
		
		// *********** PLAYER *************
		
		data = OBJFileLoader.loadOBJ("person");
		TexturedModel playerModel = new TexturedModel(loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices()),
							new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(playerModel, new Vector3f(100, 5, -150),0,0,0,.5f);
		
		// *********** PLAYER *************
		
		// *********** LIGHT *************
		
		List<Light> lights = new ArrayList<Light>();
		//The sun
		Vector3f lightColour = new Vector3f(0.6f,0.6f,0.6f);
		Vector3f lightSource = new Vector3f(0,1000,-7000);
		Light light = new Light(lightSource, lightColour);
		lights.add(light);
		
		
		lights.add(new Light(new Vector3f(185,10,-293), new Vector3f(2,0,0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370,17,-300), new Vector3f(0,2,2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293,7,-305), new Vector3f(2,2,0), new Vector3f(1, 0.01f, 0.002f)));

		// *********** LIGHT *************
		
		// *********** CAMERA *************
		
		Camera camera = new Camera(player);
		
		// *********** CAMERA *************
		
		// *********** ENTITY PLACEMENT *************
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random(676452);
		for (int i = 0; i<400; i++) {
			
			if ( i % 7 == 0) {
				
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightofTerrain(x, z);
				
				entities.add(new Entity(flower, new Vector3f(x, y, z),0,random.nextFloat() * 360,0,1.8f));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightofTerrain(x, z);
				entities.add(new Entity(grass, new Vector3f(x, y, z),0,0,0,2.3f));
			}
			if (i % 3 == 0) {
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightofTerrain(x, z);
				
				entities.add(new Entity(tree, new Vector3f(x, y, z),0,0,0,random.nextFloat() * 1 + 4));
				
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightofTerrain(x, z);
				entities.add(new Entity(fern, new Vector3f(x, y, z),0,0,0,0.9f));
				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightofTerrain(x, z);
				entities.add(new Entity(bobble, new Vector3f(x, y, z),0,0,0,random.nextFloat() * 0.1f + 0.6f));
			}
		
		}
		
		entities.add(new Entity(lamp, new Vector3f(185, -4.7f,-293), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(370, 4.2f,-300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, -6.8f,-305), 0, 0, 0, 1));
		
		// *********** ENTITY PLACEMENT *************
		
		
		// *********** TERRAIN PLACEMENT *************
		
//		List<Terrain> terrains = new ArrayList<Terrain>();
//		for (int j = -2; j <= 0; j++) {
//			for (int i = -2; i <= 0; i++) {
//				terrains.add(new Terrain(j, i,loader,texturePack, blendMap, "heightmap"));
//			}
//		}
		
		// *********** TERRAIN PLACEMENT *************
		
		// *********** GUIS *************
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		//guis.add(gui);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		// *********** GUIS *************
		
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix());
		
		// *********** MAIN GAME LOOP *************
		
		while (!Display.isCloseRequested()) {
			
			
			camera.move();
			
			
			
			player.move(terrain);
			
			//Game logic
			
			
			//Render
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
//			for(Terrain terrain : terrains) {
//				
//				renderer.processTerrain(terrain);
//			}
			
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			
			renderer.render(lights, camera);
			guiRenderer.render(guis);
			DisplayManager.updateDisplay();
			
		}
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}

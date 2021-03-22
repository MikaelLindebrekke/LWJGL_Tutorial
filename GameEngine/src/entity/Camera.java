package entity;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private float distanceFromPlayer = 50; //Zoom
	private float angleAroundPlayer = 0;  //Swivle around the player with the mouse
	
	private Vector3f position = new Vector3f (100,30,0);
	private float pitch = 20;  	// How high or low the camera is aimed
	private float yaw;		// How much left or right the camera is aimed
	private float roll;		// How much the camera is tilted to one side. 180 degrees is upside down. 
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
	}
	
	public void move() {
		calculateZoom();
		calculatePitchAndAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calcucalteCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calcucalteCameraPosition(float horizDist, float vertiDist) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDist * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDist * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + vertiDist;
	}
	
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitchAndAngleAroundPlayer() {
		if(Mouse.isButtonDown(1)) {
			float pitchChange = Mouse.getDY() * 0.09f;
			pitch -= pitchChange;
			float angleChange = Mouse.getDX() * 0.2f;
			angleAroundPlayer -= angleChange;
		}
	}
	
	
	
}

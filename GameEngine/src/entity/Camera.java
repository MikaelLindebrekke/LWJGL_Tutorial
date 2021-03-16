package entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position = new Vector3f (0,10,0);
	private float pitch;  	// How high or low the camera is aimed
	private float yaw;		// How much left or right the camera is aimed
	private float roll;		// How much the camera is tilted to one side. 180 degrees is upside down. 
	
	private float speed = .3f;
	
	public Camera() {}
	
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z-=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z+=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x+=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x-=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			position.y+=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			position.y-=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			yaw-=speed;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			yaw+=speed;
		}
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	
}

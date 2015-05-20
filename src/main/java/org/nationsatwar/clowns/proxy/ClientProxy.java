package org.nationsatwar.clowns.proxy;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;
import org.nationsatwar.palette.KeyBindings;

public class ClientProxy extends CommonProxy {

	public static KeyBinding clownKey;
	
	@Override
	public void registerKeybindings() {
		
		clownKey = KeyBindings.registerKey(Keyboard.KEY_C, "clownKey");
	}
}
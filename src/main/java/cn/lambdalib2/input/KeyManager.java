package cn.lambdalib2.input;

import cn.academy.ability.ctrl.ClientHandler;
import cn.academy.terminal.app.settings.KeybindElement;
import cn.academy.terminal.app.settings.PropertyElements;
import cn.academy.terminal.app.settings.SettingsUI;
import cn.lambdalib2.util.ClientUtils;
import cn.lambdalib2.util.Debug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Predicate;

import static cn.academy.GUIContext.IN_GAME_UNPAUSED;
import static net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
import static net.minecraftforge.client.settings.KeyConflictContext.UNIVERSAL;

/**
 * The instance of this class handles a set of KeyHandlers, and restore their key bindings
 * from a configuration. (If any)
 * @author WeAthFolD
 */
@SideOnly(Side.CLIENT)
public class KeyManager {

    /**
     * The most commonly used KeyManager. Use this if you don't want to use any config on keys.
     */
    public static final KeyManager dynamic = new KeyManager();
    
    public static final int 
        MOUSE_LEFT = -100, MOUSE_MIDDLE = -98, MOUSE_RIGHT = -99,
        MWHEELDOWN = -50, MWHEELUP = -49;

    public static String getKeyName(int keyid) {
        if(keyid >= 0) {
            String ret = Keyboard.getKeyName(keyid);
            return ret == null ? "undefined" : ret;
        } else {
            String ret = Mouse.getButtonName(keyid + 100);
            return ret == null ? "undefined" : ret;
        }
    }

    public static boolean getKeyDown(int keyID) {
        if(keyID > 0) {
            return Keyboard.isKeyDown(keyID);
        }

        return Mouse.isButtonDown(keyID + 100);
    }


    private boolean active = true;

    private int _anonymousHandlerCount = 0;

    private final Map<String, KeyHandlerState> _bindingMap = new HashMap<>();

    public KeyManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getKeyID(KeyHandler handler) {
        KeyHandlerState kb = getKeyBinding(handler);
        return kb == null ? -1 : kb.getKeyCode();
    }

    public void addKeyHandler(int keyID, KeyHandler handler, IKeyConflictContext context) {
        Debug.require(getConfig() == null, "You can't use anonymous key handlers on KeyHandler with config!");

        String name = "_anonymous_" + _anonymousHandlerCount;
        addKeyHandler(name, "", keyID, handler, context);
        ++_anonymousHandlerCount;
    }

    public void addKeyHandler(String name, int defKeyID, KeyHandler handler, IKeyConflictContext context) {
        addKeyHandler(name, "", defKeyID, handler, context);
    }

    /**
     * Add a key handler.
     * @param keyDesc Description of the key in the configuration file
     * @param defKeyID Default key ID in config file
     * @param context key conflict context
     */
    public void addKeyHandler(String name, String keyDesc, int defKeyID, KeyHandler handler, IKeyConflictContext context) {
        if(_bindingMap.containsKey(name))
            throw new RuntimeException("Duplicate key: " + name + " of object " + handler);

        Configuration conf = getConfig();
        int keyID = defKeyID;
        if(conf != null) {
            keyID = conf.getInt(name, "keys", defKeyID, -1000, 1000, keyDesc);
        }
        KeyHandlerState kb = new KeyHandlerState(name, handler, keyID, context);
        _bindingMap.put(name, kb);
        SettingsUI.addProperty(new KeybindElement(kb), "keys", name, defKeyID, false);
    }

    /**
     * Removes a key handler from map, if exists.
     */
    public void removeKeyHandler(String name) {
        KeyHandlerState kb = _bindingMap.get(name);
        if(kb != null)
            kb.dead = true;
    }

    public void resetBindingKey(String name, int newKey) {
        KeyHandlerState kb = _bindingMap.get(name);
        if(kb != null) {
            kb.setKeyCode(newKey);
            if(kb.keyDown)
                kb.handler.onKeyAbort();
            
            kb.keyDown = false;
        }
    }

    protected Configuration getConfig() {
        return null;
    }

    private void tick() {
        Iterator< Entry<String, KeyHandlerState> > iter = _bindingMap.entrySet().iterator();
        boolean shouldAbort = !ClientUtils.isPlayerInGame();

        while(iter.hasNext()) {
            Entry<String, KeyHandlerState> entry = iter.next();
            KeyHandlerState kb = entry.getValue();
            if(kb.dead) {
                iter.remove();
            } else {

                boolean down = getKeyDown(kb.getKeyCode());

                if (kb.keyDown && shouldAbort) {
                    kb.keyDown = false;
                    kb.keyAborted = true;
                    kb.handler.onKeyAbort();
                } else if (!kb.keyDown && down && !shouldAbort && !kb.keyAborted) {
                    kb.keyDown = true;
                    kb.handler.onKeyDown();
                } else if (kb.keyDown && !down) {
                    kb.keyDown = false;
                    kb.handler.onKeyUp();
                } else if (kb.keyDown) {
                    kb.handler.onKeyTick();
                }

                if (!down) {
                    kb.keyAborted = false;
                }

                kb.keyDown = down;
            }
        }
    }

    private KeyHandlerState getKeyBinding(KeyHandler handler) {
        for(KeyHandlerState kb : _bindingMap.values()) {
            if(kb.handler == handler)
                return kb;
        }
        return null;
    }

    @SubscribeEvent
    public void _onEvent(ClientTickEvent event) {
        if(event.phase == Phase.START && active) {
            tick();
        }
    }

    public void registerKeys() {
        for(KeyHandlerState kb : _bindingMap.values()) {
            ClientRegistry.registerKeyBinding(kb);
        }
    }

    private class KeyHandlerState extends KeyBinding {
        KeyHandler handler;

        boolean keyAborted = false;
        boolean keyDown = false;

        boolean dead;

        private KeyHandlerState(String name, KeyHandler h, int defaultkey, IKeyConflictContext context) {
            super("ac.settings.prop."+name, context, defaultkey, "key.categories.academycraft");
            handler = h;
        }

        @Override
        public void setKeyCode(int keyCode) {
            super.setKeyCode(keyCode);
        }
    }
}

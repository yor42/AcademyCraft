package cn.academy.ability.ctrl;

import cn.academy.Main;
import cn.academy.ability.context.ClientRuntime;
import cn.academy.client.auxgui.CPBar;
import cn.academy.client.auxgui.PresetEditUI;
import cn.academy.datapart.AbilityData;
import cn.academy.datapart.CPData;
import cn.academy.datapart.PresetData;
import cn.academy.event.ConfigModifyEvent;
import cn.academy.event.ability.AbilityActivateEvent;
import cn.academy.event.ability.AbilityDeactivateEvent;
import cn.academy.event.ability.FlushControlEvent;
import cn.academy.event.ability.PresetSwitchEvent;
import cn.academy.terminal.app.settings.KeybindElement;
import cn.academy.terminal.app.settings.PropertyElements;
import cn.academy.terminal.app.settings.SettingsUI;
import cn.academy.util.RegACKeyHandler;
import cn.lambdalib2.input.KeyHandler;
import cn.lambdalib2.input.KeyManager;
import cn.lambdalib2.registry.StateEventCallback;
import cn.lambdalib2.registry.mc.RegEventHandler;
import cn.lambdalib2.util.GameTimer;
import cn.lambdalib2.util.SideUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static cn.academy.GUIContext.ABILITY_ACTIVE;
import static net.minecraftforge.client.settings.KeyConflictContext.IN_GAME;
import static net.minecraftforge.fml.common.eventhandler.EventPriority.HIGHEST;

/**
 * Misc key event listener for skill events.
 */
@SideOnly(Side.CLIENT)
public final class ClientHandler {

    // Name constants for looking up keys in ACKeyHandler.
    public static final String
        KEY_SWITCH_PRESET = "switch_preset",
        KEY_EDIT_PRESET = "edit_preset",
        KEY_ACTIVATE_ABILITY = "ability_activation";

    private static final int[] keyIDsInit = new int[] {
            KeyManager.MOUSE_LEFT,
            KeyManager.MOUSE_RIGHT,
            Keyboard.KEY_R,
            Keyboard.KEY_F
    };

    public static boolean isAbilityActive = false;

    private static final ArrayList<SkillKeyBind> SKILL_KEY_BINDS = new ArrayList<>();

    @StateEventCallback
    private static void init(FMLInitializationEvent ev) {
        for (int i = 0; i < keyIDsInit.length; ++i) {
            SkillKeyBind keyBind = new SkillKeyBind(i, keyIDsInit[i]);
            SKILL_KEY_BINDS.add(keyBind);
            ClientRegistry.registerKeyBinding(keyBind);
            SettingsUI.addProperty(new KeybindElement(keyBind), "keys", "ability_" + i, keyIDsInit[i], false);
        }
        updateAbilityKeys();
    }

    public static void updateAbilityKeys() {
        MinecraftForge.EVENT_BUS.post(new FlushControlEvent());
    }

    public static int getKeyMapping(int id) {
        return SKILL_KEY_BINDS.get(id).getKeyCode();
    }

    public static int getKeyCount() {
        return keyIDsInit.length;
    }
    
    /**
     * The key to activate and deactivate the ability, might have other uses in certain circumstances,
     *  e.g. quit charging when using ability.
     */
    @RegACKeyHandler(name = KEY_ACTIVATE_ABILITY, keyID = Keyboard.KEY_V)
    public static KeyHandler keyActivate = new KeyHandler() {

        double lastKeyDown;

        @Override
        public void onKeyUp() {
            double delta = GameTimer.getTime() - lastKeyDown;
            if (delta < 0.300) {
                EntityPlayer player = getPlayer();
                AbilityData aData = AbilityData.get(player);

                if(aData.hasCategory()) {
                    ClientRuntime.instance().getActivateHandler().onKeyDown(player);
                }
            }

            CPBar.instance.stopDisplayNumbers();
        }

        @Override
        public void onKeyDown() {
            lastKeyDown = GameTimer.getTime();
            CPBar.instance.startDisplayNumbers();
        }
        
    };
    
    @RegACKeyHandler(name = KEY_EDIT_PRESET, keyID = Keyboard.KEY_N)
    public static KeyHandler keyEditPreset = new KeyHandler() {
        @Override
        public void onKeyDown() {
            if(AbilityData.get(getPlayer()).hasCategory()) {
                Minecraft.getMinecraft().displayGuiScreen(new PresetEditUI());
            }
        }
    };
    
    @RegACKeyHandler(name = KEY_SWITCH_PRESET, keyID = Keyboard.KEY_C)
    public static KeyHandler keySwitchPreset = new KeyHandler() {
        @Override
        public void onKeyDown() {
            PresetData data = PresetData.get(getPlayer());
            CPData cpData = CPData.get(getPlayer());
            
            if(cpData.isActivated()) {
                int next = (data.getCurrentID() + 1) % PresetData.MAX_PRESETS;
                data.switchFromClient(next);
                MinecraftForge.EVENT_BUS.post(new PresetSwitchEvent(data.getEntity()));
            }
        }
    };


    @SideOnly(Side.CLIENT)
    public enum ConfigHandler {
        @RegEventHandler()
        instance;

        @SubscribeEvent
        public void onConfigModify(ConfigModifyEvent evt) {
            updateAbilityKeys();
        }

        @SubscribeEvent
        public void activateAbility(AbilityActivateEvent evt) {
            isAbilityActive = true;
        }

        @SubscribeEvent
        public void deactivateAbility(AbilityDeactivateEvent evt) {
            isAbilityActive = false;
        }
    }


    private static class SkillKeyBind extends KeyBinding{

        public SkillKeyBind(int index, int keycode) {
            super("ac.settings.prop.ability_"+index, ABILITY_ACTIVE, keycode, "key.categories.academycraft");
        }

        @Override
        public void setKeyCode(int keyCode) {
            super.setKeyCode(keyCode);
        }
    }

}
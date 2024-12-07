package cn.academy;

import cn.academy.client.auxgui.TerminalUI;
import cn.academy.datapart.CPData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.IKeyConflictContext;

import javax.annotation.Nullable;

import static cn.academy.ability.ctrl.ClientHandler.isAbilityActive;
import static cn.academy.client.auxgui.TerminalUI.getCurrentTerminal;

public enum GUIContext implements IKeyConflictContext {
    IN_GAME_UNPAUSED{

        @Override
        public boolean isActive() {

            if(Minecraft.getMinecraft().currentScreen != null){
                return false;
            }
            else return !Minecraft.getMinecraft().isGamePaused();
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return this == other;
        }
    },
    HAS_OPEN_TERMINAL{

        @Override
        public boolean isActive() {
            return getCurrentTerminal() instanceof TerminalUI;
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return this == other;
        }
    },
    ABILITY_ACTIVE{

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return other == this;
        }
    };

    @Nullable
    public static GUIContext fromName(String name){
        for(GUIContext context : values()) {
            if (context.name().equals(name)) {
                return context;
            }
        }
        return null;
    }
}

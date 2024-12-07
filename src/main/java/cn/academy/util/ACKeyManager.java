package cn.academy.util;

import cn.academy.Main;
import cn.academy.event.ConfigModifyEvent;
import cn.academy.terminal.app.settings.PropertyElements;
import cn.academy.terminal.app.settings.SettingsUI;
import cn.lambdalib2.input.KeyHandler;
import cn.lambdalib2.input.KeyManager;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ACKeyManager extends KeyManager {

    public static KeyManager instance = new ACKeyManager();

    @Override
    protected Configuration getConfig() {
        return Main.config;
    }

    @SubscribeEvent
    public void onConfigModified(ConfigModifyEvent event) {
        if (event.property.isIntValue())
            resetBindingKey(event.property.getName(), event.property.getInt());
    }

    @Override
    public void addKeyHandler(String name, String keyDesc, int defKeyID, KeyHandler handler, IKeyConflictContext context) {
        super.addKeyHandler(name, keyDesc, defKeyID, handler, context);
    }

    private ACKeyManager() {}
}
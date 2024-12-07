package cn.academy.terminal.app.settings;

import cn.lambdalib2.cgui.Widget;
import cn.lambdalib2.cgui.component.TextBox;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class KeybindElement extends IPropertyElement<UIProperty.Config>{

    private final KeyBinding binding;
    public KeybindElement(KeyBinding binding){
        this.binding = binding;
    }

    @Override
    public Widget getWidget(UIProperty.Config prop) {
        Configuration cfg = getConfig();
        Property p = cfg.get(prop.category, prop.id, (int) prop.defValue);

        Widget ret = SettingsUI.document.getWidget("t_key").copy();
        TextBox.get(ret.getWidget("text")).setContent(prop.getDisplayID());

        Widget key = ret.getWidget("key");
        key.addComponent(new EditKeyComponent(p, this.binding));

        return ret;
    }
}

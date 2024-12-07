package cn.academy.terminal.app.settings;

import cn.academy.event.ConfigModifyEvent;
import cn.lambdalib2.cgui.component.Component;
import cn.lambdalib2.cgui.component.TextBox;
import cn.lambdalib2.cgui.event.GainFocusEvent;
import cn.lambdalib2.cgui.event.IGuiEventHandler;
import cn.lambdalib2.cgui.event.KeyEvent;
import cn.lambdalib2.cgui.event.MouseClickEvent;
import cn.lambdalib2.input.KeyManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Property;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.Color;

public class EditKeyComponent extends Component {

    static final Color
            CRL_NORMAL = new Color(200, 200, 200, 200),
            CRL_EDIT = new Color(251, 133, 37, 200);

    IGuiEventHandler<MouseClickEvent> gMouseHandler;

    private final KeyBinding keyBinding;
    public boolean editing;
    final Property prop;

    TextBox textBox;

    public EditKeyComponent(Property _prop, KeyBinding keybind) {
        super("EditKey");

        keyBinding=keybind;
        prop = _prop;

        listen(KeyEvent.class, (w, event) ->
        {
            if(editing) {
                endEditing(event.keyCode);
            }
        });

        listen(GainFocusEvent.class, (w, e) ->
        {
            startEditing();
        });
    }

    @Override
    public void onAdded() {
        super.onAdded();

        textBox = TextBox.get(widget);
        widget.transform.doesListenKey = true;
        updateKeyName();
    }

    private void updateKeyName() {
        textBox.setContent(KeyManager.getKeyName(keyBinding.getKeyCode()));
    }

    private void startEditing() {
        editing = true;
        textBox.setContent("PRESS");
        textBox.option.color = CRL_EDIT;

        widget.getGui().listen(MouseClickEvent.class,
                gMouseHandler = (w, event) -> {
                    endEditing(event.button - 100);
                });
    }

    private void endEditing(int key) {
        editing = false;
        textBox.option.color = CRL_NORMAL;
        widget.getGui().removeFocus();

        if (key != Keyboard.KEY_ESCAPE) {
            keyBinding.setKeyCode(key);
            prop.set(key);
        }

        updateKeyName();
        widget.getGui().unlisten(MouseClickEvent.class, gMouseHandler);
        MinecraftForge.EVENT_BUS.post(new ConfigModifyEvent(prop));
    }

}

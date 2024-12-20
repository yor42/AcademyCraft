package cn.academy.crafting;

import cn.academy.block.tileentity.TileMetalFormer.Mode;
import cn.lambdalib2.registry.StateEventCallback;
import cn.lambdalib2.s11n.network.NetworkS11n;
import cn.lambdalib2.s11n.network.NetworkS11n.ContextException;
import cn.lambdalib2.s11n.network.NetworkS11n.NetS11nAdaptor;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WeAthFolD
 */
public enum MetalFormerRecipes {
    INSTANCE;

    public static class RecipeObject {
        private int id = -1;
        
        public final Mode mode;
        public final Ingredient input;
        public final int count;
        public final ItemStack output;
        
        public RecipeObject(Ingredient _input, int _count, ItemStack _output, Mode _mode) {
            input = _input;
            output = _output;
            count = _count;
            mode = _mode;
        }

        public RecipeObject(ItemStack _input, ItemStack _output, Mode _mode) {
            this(Ingredient.fromStacks(_input), _input.getCount(), _output, _mode);
        }
        
        public boolean accepts(ItemStack stack, Mode mode2) {
            return  stack != null &&
                    mode == mode2 &&
                    input.test(stack) &&
                    count <= stack.getCount();
        }

        public int getCount() {
            return count;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
    
    final List<RecipeObject> objects = new ArrayList<>();
    
    public void add(ItemStack in, ItemStack out, Mode mode) {
        RecipeObject add = new RecipeObject(in, out, mode);
        add.id = objects.size();
        objects.add(add);
    }

    public void remove(RecipeObject recipe) {
        objects.remove(recipe);
    }

    public void add(RecipeObject recipe) {
        recipe.id = objects.size();
        objects.add(recipe);
    }

    @Nullable
    public RecipeObject removebyInput(ItemStack stack, Mode mode) {
        for(RecipeObject recipe: objects){
            if(recipe.accepts(stack, mode)){
                objects.remove(recipe);
                return recipe;
            }
        }
        return null;
    }
    
    public RecipeObject getRecipe(ItemStack input, Mode mode) {
        for(RecipeObject recipe : objects) {
            if(recipe.accepts(input, mode))
                return recipe;
        }
        return null;
    }
    
    public List<RecipeObject> getAllRecipes() {
        return objects;
    }

    @StateEventCallback
    private static void _init(FMLInitializationEvent ev) {
        NetworkS11n.addDirect(RecipeObject.class, new NetS11nAdaptor<RecipeObject>() {
            @Override
            public void write(ByteBuf buf, RecipeObject obj) {
                buf.writeByte(obj.id);
            }

            @Override
            public RecipeObject read(ByteBuf buf) throws ContextException {
                return INSTANCE.objects.get(buf.readByte());
            }
        });
    }
    
}
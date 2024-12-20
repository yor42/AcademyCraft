package cn.academy.crafting;

import cn.lambdalib2.s11n.network.NetworkS11n;
import cn.lambdalib2.s11n.network.NetworkS11n.ContextException;
import cn.lambdalib2.s11n.network.NetworkS11n.NetS11nAdaptor;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Recipe holder of ImagFusor.
 * @author WeAthFolD
 */
public class ImagFusorRecipes {

    public static ImagFusorRecipes INSTANCE = new ImagFusorRecipes();
    
    private final List<IFRecipe> recipeList = new ArrayList<>();
    
    public void addRecipe(ItemStack consume, int liquid, ItemStack output) {
        addRecipe(new IFRecipe(consume, liquid, output));
    }
    
    public void addRecipe(IFRecipe recipe) {
        for(IFRecipe r : recipeList) {

            if(r.consumeType == recipe.consumeType) {
                throw new RuntimeException("Can't register multiple recipes for same item " + recipe.consumeType.toString()+"!!");
            }
        }
        
        recipeList.add(recipe);
    }
    
    public IFRecipe getRecipe(ItemStack input) {
        for(IFRecipe r : recipeList) {
            if(r.matches(input))
                return r;
        }
        return null;
    }
    
    public List<IFRecipe> getAllRecipe() {
        return recipeList;
    }

    public static class IFRecipe {

        public final Ingredient consumeType;
        public final int inputAmount;
        public final int consumeLiquid;
        public final ItemStack output;
        
        public IFRecipe(Ingredient stack, int _inputAmount, int liq, ItemStack _output) {
            consumeType = stack;
            inputAmount = _inputAmount;
            consumeLiquid = liq;
            output = _output;
        }

        public IFRecipe(ItemStack stack, int liq, ItemStack _output) {
            this(Ingredient.fromStacks(stack), stack.getCount(), liq, _output);
        }
        
        public boolean matches(ItemStack input) {
            return consumeType.test(input) && inputAmount<=input.getCount();
        }

        public int getInputAmount() {
            return inputAmount;
        }

        public int getID() {
            return ImagFusorRecipes.INSTANCE.recipeList.indexOf(this);
        }
        
    }
    
    static {
        NetworkS11n.addDirect(IFRecipe.class, new NetS11nAdaptor<IFRecipe>() {
            @Override
            public void write(ByteBuf buf, IFRecipe obj) {
                buf.writeInt(obj.getID());
            }
            @Override
            public IFRecipe read(ByteBuf buf) throws ContextException {
                return INSTANCE.recipeList.get(buf.readInt());
            }
        });
    }
    
}
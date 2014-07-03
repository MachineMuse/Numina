package net.machinemuse.numina.network.message;

import net.machinemuse.numina.recipe.JSONRecipeList;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MusePacketRecipeUpdate implements IMessage, IMessageHandler<MusePacketRecipeUpdate, IMessage> {
    private String recipe;

    private MusePacketRecipeUpdate() {}

    public MusePacketRecipeUpdate(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int length = buf.readInt();
        recipe = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(recipe.length());
        buf.writeBytes(recipe.getBytes());
    }

    @Override
    public IMessage onMessage(MusePacketRecipeUpdate message, MessageContext ctx) {
        JSONRecipeList.loadRecipesFromString(recipe);
        return null;
    }
}

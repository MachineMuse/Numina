package net.machinemuse.numina.network.message;

import net.machinemuse.numina.recipe.JSONRecipeList;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MusePacketRecipeUpdate implements IMessage, IMessageHandler<MusePacketRecipeUpdate, IMessage> {
    private String recipe;

    public MusePacketRecipeUpdate() {}

    public MusePacketRecipeUpdate(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        recipe = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, recipe);
    }

    @Override
    public IMessage onMessage(MusePacketRecipeUpdate message, MessageContext ctx) {
        JSONRecipeList.loadRecipesFromString(message.recipe);
        return null;
    }
}

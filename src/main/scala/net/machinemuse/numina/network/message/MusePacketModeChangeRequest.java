package net.machinemuse.numina.network.message;

import net.machinemuse.numina.item.ModeChangingItem;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MusePacketModeChangeRequest implements IMessage, IMessageHandler<MusePacketModeChangeRequest, IMessage> {
    private int slot;
    private String mode;

    public MusePacketModeChangeRequest() {}

    public MusePacketModeChangeRequest(int slot, String mode) {
        this.slot = slot;
        this.mode = mode;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readInt();
        mode = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
        ByteBufUtils.writeUTF8String(buf, mode);
    }

    @Override
    public IMessage onMessage(MusePacketModeChangeRequest message, MessageContext ctx) {
        if (message.slot > -1 && message.slot < 9) {
            ItemStack stack = ctx.getServerHandler().playerEntity.inventory.mainInventory[message.slot];
            ModeChangingItem item = (ModeChangingItem)stack.getItem();
            if(item.getValidModes(stack, ctx.getServerHandler().playerEntity).contains(message.mode)) {
                item.setActiveMode(stack, message.mode);
            }
        }
        return null;
    }
}

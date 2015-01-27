package net.machinemuse.numina.gui

import net.minecraft.client.gui.{GuiErrorScreen, FontRenderer}
import cpw.mods.fml.client.CustomModLoadingErrorDisplayException

class OggAudioDataRequiredDisplayException extends CustomModLoadingErrorDisplayException {
  override def initGui(errorScreen: GuiErrorScreen, fontRenderer: FontRenderer) {
  }

  override def drawScreen(errorScreen: GuiErrorScreen, fontRenderer: FontRenderer, mouseRelX: Int, mouseRelY: Int, tickTime: Float) {
    errorScreen.drawDefaultBackground
    var offset: Int = 75
    errorScreen.drawCenteredString(fontRenderer, "A required library is missing. Minecraft cannot continue loading.", errorScreen.width / 2, offset, 0xFFFFFF)
    offset += 25
    errorScreen.drawCenteredString(fontRenderer, "The mod: \"Numina\"", errorScreen.width / 2, offset, 0xEEEEEE)
    offset += 15
    errorScreen.drawCenteredString(fontRenderer, "requires an additional library: \"OggAudioData\".", errorScreen.width / 2, offset, 0xEEEEEE)
    offset += 25
    errorScreen.drawCenteredString(fontRenderer, "Please install \"OggAudioData\" to continue.", errorScreen.width / 2, offset, 0xFFFFFF)
  }

}
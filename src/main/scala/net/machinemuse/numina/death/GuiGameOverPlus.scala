package net.machinemuse.numina.death

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiMainMenu
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fml.relauncher.{Side, SideOnly}
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT) class GuiGameOverPlus extends GuiScreen {
  /**
   * Adds the buttons (and other controls) to the screen in question.
   */
  override def initGui() {
    this.buttonList.clear()
    if (this.mc.theWorld.getWorldInfo.isHardcoreModeEnabled) {
      if (this.mc.isIntegratedServerRunning) {
        addButton(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.deleteWorld")))
      } else {
        addButton(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.leaveServer")))
      }
    } else {
      addButton(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72, I18n.format("deathScreen.respawn")))
      addButton(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96, I18n.format("deathScreen.titleScreen")))
      addButton(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 120, "Revive"))
      if (this.mc.getSession == null) {
        this.buttonList.get(1).asInstanceOf[GuiButton].enabled = false
      }
    }
    import scala.collection.JavaConverters._
    for (b: GuiButton <- this.buttonList.asInstanceOf[java.util.List[GuiButton]].asScala) {
      b.enabled = false
    }
  }

  private def addButton(b: GuiButton) {
    this.buttonList.asInstanceOf[java.util.List[GuiButton]].add(b)
  }

  /**
   * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
   */
  protected override def keyTyped(par1: Char, par2: Int) {
  }

  /**
   * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
   */
  protected override def actionPerformed(par1GuiButton: GuiButton) {
    par1GuiButton.id match {
      case 1 =>
        this.mc.thePlayer.respawnPlayer()
        this.mc.displayGuiScreen(null.asInstanceOf[GuiScreen])
      case 2 =>
        this.mc.theWorld.sendQuittingDisconnectingPacket()
        this.mc.loadWorld(null.asInstanceOf[WorldClient])
        this.mc.displayGuiScreen(new GuiMainMenu)
      case 3 =>
        this.mc.thePlayer.setHealth(10f)
        this.mc.thePlayer.isDead = false
        this.mc.displayGuiScreen(null.asInstanceOf[GuiScreen])
    }
  }

  /**
   * Draws the screen and all the components in it.
   */
  override def drawScreen(par1: Int, par2: Int, par3: Float) {
    this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792)
    GL11.glPushMatrix()
    GL11.glScalef(2.0F, 2.0F, 2.0F)
    val flag: Boolean = this.mc.theWorld.getWorldInfo.isHardcoreModeEnabled
    val s: String = if (flag) I18n.format("deathScreen.title.hardcore") else I18n.format("deathScreen.title")
    this.drawCenteredString(this.fontRendererObj, s, this.width / 2 / 2, 30, 16777215)
    GL11.glPopMatrix()
    if (flag) {
      this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.hardcoreInfo"), this.width / 2, 144, 16777215)
    }
    this.drawCenteredString(this.fontRendererObj, I18n.format("deathScreen.score") + ": " + TextFormatting.YELLOW + this.mc.thePlayer.getScore, this.width / 2, 100, 16777215)
    super.drawScreen(par1, par2, par3)
  }

  /**
   * Returns true if this GUI should pause the game when it is displayed in single-player
   */
  override def doesGuiPauseGame: Boolean = false

  /**
   * Called from the main game loop to update the screen.
   */
  override def updateScreen() {
    super.updateScreen()
    this.cooldownTimer += 1
    if (this.cooldownTimer == 20) {
      import scala.collection.JavaConverters._
      for (b: GuiButton <- this.buttonList.asInstanceOf[java.util.List[GuiButton]].asScala) {
        b.enabled = true
      }
    }
  }

  /**
   * The cooldown timer for the buttons, increases every tick and enables all buttons when reaching 20.
   */
  private var cooldownTimer: Int = 0
}